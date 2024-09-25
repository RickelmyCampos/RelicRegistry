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
import java.io.OutputStream

class ExternalPrivateImageStoreService(val appContext: Context) : ImageStoreService {
    //TODO adicionar feature para apagar as imagens que não não mais usadas
    override fun saveImage(bitmap: Bitmap, nameImage: String): String {
        validateImageName(nameImage)
        val imageFile = createImageFile(nameImage)
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

    override fun saveImageByUri(uri: Uri, nameImage: String): String {
        val bitmap = convertUriToBitmap(uri)
        return saveImage(bitmap, nameImage)
    }

    override fun deleteImageByNameImage(nameImage: String) {
        val image = getImageFile(nameImage)
        image.delete()
    }


    override fun getImage(nameImage: String): Bitmap {
        validateImageName(nameImage)
        val imageFile = getImageFile(nameImage)
        return try {
            FileInputStream(imageFile).use { stream ->
                BitmapFactory.decodeStream(stream)
                    ?: throw FileNotFoundException("Falha ao decodificar a imagem.")
            }
        } catch (e: FileNotFoundException) {
            Log.e(LOG_TAG, "Arquivo não encontrado: $nameImage", e)
            throw FileNotFoundException("Arquivo não encontrado: $nameImage")
        }
    }

    private fun getImageFile(nameImage: String) = File(
        appContext.getExternalFilesDir(Environment.DIRECTORY_PICTURES),
        "$FILE_FOLDER/$nameImage.jpg"
    )

    @SuppressLint("Recycle")
    private fun convertUriToBitmap(uri: Uri): Bitmap {
        val inputStream = appContext.contentResolver.openInputStream(uri)
        val bitmap = BitmapFactory.decodeStream(inputStream)
            ?: throw IOException("Falha ao converter URI para Bitmap.")
        return correctBitmapOrientation(bitmap, uri)
    }

    private fun createImageFile(nameImage: String): File {
        val appDirectory = File(
            appContext.getExternalFilesDir(Environment.DIRECTORY_PICTURES),
            FILE_FOLDER
        )
        if (!appDirectory.exists() && !appDirectory.mkdirs()) {
            Log.e(LOG_TAG, "Falha ao criar diretório: ${appDirectory.absolutePath}")
            throw IOException("Falha ao criar diretório.")
        }
        return File(appDirectory, "$nameImage.jpg")
    }

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
