package com.gilbersoncampos.relicregistry.screen.home

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.gilbersoncampos.relicregistry.R
import com.gilbersoncampos.relicregistry.ui.theme.RelicRegistryTheme

@Composable
fun HomeScreen(viewModel: HomeViewModel = hiltViewModel()) {
    HomeUi(viewModel::saveImage, viewModel::getImage)
}

@Composable
fun HomeUi(saveImage: (Bitmap) -> Unit, getImage: () -> Bitmap) {
    val listMenu = listOf("criar", "listar")
    Column(modifier = Modifier.fillMaxSize()) {
        val context = LocalContext.current
        Button(onClick = {
            val bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.example)
            saveImage(bitmap)
        }) {
            Text(text = "Selecionar foto")
        }
        val imageUri = remember { mutableStateOf<Uri?>(null) }
        val pickerMedia =
            rememberLauncherForActivityResult(ActivityResultContracts.PickMultipleVisualMedia(5)) { uris ->
                if (uris.isNotEmpty()) {
                    Log.d("PhotoPicker", "Number of items selected: ${uris.size} ${uris.first()}")
                } else {
                    Log.d("PhotoPicker", "No media selected")
                }
            }
        Button(onClick = { pickerMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)) }) {
            Text("Selecionar Imagem")
        }

        imageUri.value?.let { uri ->
//            Image(
//                bitmap= (uri),
//                contentDescription = null,
//                modifier = Modifier.size(128.dp)
//            )
        }
        var bitmap by remember {
            mutableStateOf<Bitmap?>(null)
        }
        Button(onClick = { bitmap = getImage() }) {
            Text(text = "Pegar imagem")
        }
        bitmap?.let {
            Image(bitmap = it.asImageBitmap(), contentDescription = "image")
        }


    }
}

@Composable
fun MenuItem(name: String, onClick: () -> Unit) {

    Card(
        modifier = Modifier
            .padding(8.dp)
            .size(100.dp), onClick = onClick
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Text(text = name)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeUiPreview() {
    RelicRegistryTheme {
        // HomeUi(saveImage = {}){}
    }
}