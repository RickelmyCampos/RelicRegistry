package com.gilbersoncampos.relicregistry.service

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import android.os.Environment
import android.util.Log
import com.gilbersoncampos.relicregistry.data.services.ImageStoreService
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException

class ExternalPrivateImageStoreService(private val appContext: Context) : ImageStoreService {

    override fun saveCache(bitmap: Bitmap, imageName: String): String {
        return saveImage(bitmap = bitmap, nameImage = imageName, isCached = true)
    }

    override fun saveUriCache(uri: Uri, imageName: String): String {
        val bitmap = convertUriToBitmap(uri)
        return saveCache(bitmap, imageName)
    }

    override fun copyToExternalStoreFromCache(imageName: String) {
        try {
        val imageCachedFile = getImageFile(nameImage = imageName, fromCache = true)
        val imageExternalStoreFile = createImageFile(imageName)
        imageCachedFile.copyTo(imageExternalStoreFile, true)
        }catch (_:NoSuchFileException){

        }catch (_:FileAlreadyExistsException){

        }
    }

    override fun copyToCacheFromExternalStore(imageName: String) {
        try {
        val imageExternalStoreFile = getImageFile(imageName, fromCache = false)
        val imageCachedFile = createImageFile(nameImage = imageName, isCached = true)
        imageExternalStoreFile.copyTo(imageCachedFile, true)
        }catch (_:NoSuchFileException){

        }catch (_:FileAlreadyExistsException){

        }
    }

    override fun deleteCache(imageName: String) {
        val imageCachedFile = getImageFile(nameImage = imageName, fromCache = true)
        imageCachedFile.delete()
    }

    override fun listAllImageCached(): List<String> {
        val appDirectory = File(
            appContext.externalCacheDir,
            FILE_FOLDER
        )
        return appDirectory.listFiles()?.map { it.name.replace(".jpg","") } ?: listOf()

    }

    override fun getImage(imageName: String, isCache: Boolean): Bitmap {
        validateImageName(imageName)
        val imageFile = getImageFile(imageName, isCache)
        return try {
            FileInputStream(imageFile).use { stream ->
                BitmapFactory.decodeStream(stream)
                    ?: throw FileNotFoundException("Falha ao decodificar a imagem.")
            }
        } catch (e: FileNotFoundException) {
            Log.e(LOG_TAG, "Arquivo não encontrado: $imageName", e)
            throw FileNotFoundException("Arquivo não encontrado: $imageName")
        }
    }

    private fun saveImage(bitmap: Bitmap, nameImage: String, isCached: Boolean): String {
        validateImageName(nameImage)
        val imageFile = createImageFile(nameImage, isCached)
        return try {
            FileOutputStream(imageFile).use { stream ->
                if (!bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)) {
                    throw IOException("Falha ao comprimir a imagem.")
                }
                stream.flush()
            }
            Uri.fromFile(imageFile).path
                ?: throw IOException("Não foi possível obter o caminho da imagem.")
        } catch (e: IOException) {
            Log.e(LOG_TAG, "Erro ao salvar a imagem: ${e.message}", e)
            throw IOException("Não foi possível salvar a imagem.", e)
        }
    }

    @SuppressLint("Recycle")
    override fun convertUriToBitmap(uri: Uri): Bitmap {
        val inputStream = appContext.contentResolver.openInputStream(uri)
        val bitmap = BitmapFactory.decodeStream(inputStream)
            ?: throw IOException("Falha ao converter URI para Bitmap.")
        return correctBitmapOrientation(bitmap, uri)
    }

    private fun createImageFile(nameImage: String, isCached: Boolean = false): File {
        val appDirectory = File(
            if (isCached) appContext.externalCacheDir else appContext.getExternalFilesDir(
                Environment.DIRECTORY_PICTURES
            ),
            FILE_FOLDER
        )
        if (!appDirectory.exists() && !appDirectory.mkdirs()) {
            Log.e(LOG_TAG, "Falha ao criar diretório: ${appDirectory.absolutePath}")
            throw IOException("Falha ao criar diretório.")
        }
        return File(appDirectory, "$nameImage.jpg")
    }

    private fun getImageFile(nameImage: String, fromCache: Boolean) = File(
        if (fromCache) appContext.externalCacheDir else appContext.getExternalFilesDir(Environment.DIRECTORY_PICTURES),
        "$FILE_FOLDER/$nameImage.jpg"
    )

    private fun validateImageName(nameImage: String) {
        if (nameImage.isBlank()) {
            throw IllegalArgumentException("O nome da imagem não pode ser vazio ou nulo.")
        }
    }

    fun isExternalStorageWritable(): Boolean {
        return Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED
    }

    fun isExternalStorageReadable(): Boolean {
        return Environment.getExternalStorageState() in
                setOf(Environment.MEDIA_MOUNTED, Environment.MEDIA_MOUNTED_READ_ONLY)
    }

    private fun correctBitmapOrientation(bitmap: Bitmap, uri: Uri): Bitmap {
        val inputStream = appContext.contentResolver.openInputStream(uri)
        val exif = inputStream?.let { ExifInterface(it) }

        val orientation = exif?.getAttributeInt(
            ExifInterface.TAG_ORIENTATION,
            ExifInterface.ORIENTATION_UNDEFINED
        )

        val matrix = Matrix()
        when (orientation) {
            ExifInterface.ORIENTATION_ROTATE_90 -> matrix.postRotate(90f)
            ExifInterface.ORIENTATION_ROTATE_180 -> matrix.postRotate(180f)
            ExifInterface.ORIENTATION_ROTATE_270 -> matrix.postRotate(270f)
            ExifInterface.ORIENTATION_FLIP_HORIZONTAL -> matrix.postScale(-1f, 1f)
            ExifInterface.ORIENTATION_FLIP_VERTICAL -> matrix.postScale(1f, -1f)
            // Add more cases if needed
        }

        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
    }

    companion object {
        private const val LOG_TAG = "ExternalPrivateImageStoreService"
        private const val FILE_FOLDER = "RecordImages"
    }
}
