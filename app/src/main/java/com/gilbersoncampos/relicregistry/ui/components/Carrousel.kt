package com.gilbersoncampos.relicregistry.ui.components

import android.graphics.Bitmap
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gilbersoncampos.relicregistry.ui.theme.RelicRegistryTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ImageCarrousel(images: List<Bitmap>, modifier: Modifier = Modifier,onClickImage:(Bitmap)->Unit) {
    val pagerState = rememberPagerState(pageCount = { images.size })
    Box(modifier = modifier
       , contentAlignment = Alignment.BottomCenter) {
        HorizontalPager(
            state = pagerState,
            verticalAlignment = Alignment.CenterVertically
        ) { page ->
            val image = images[page]
            Row(modifier = Modifier.fillMaxSize().clickable { onClickImage(image) }, horizontalArrangement = Arrangement.Center) {
                Image(
                    bitmap = image.asImageBitmap(),
                    contentDescription = "Image",
                    contentScale = ContentScale.Fit
                )
            }
        }
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        ) {
            repeat(images.size) { index ->
                val color = if (pagerState.currentPage == index) Color.Gray else Color.LightGray
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .padding(2.dp)
                        .background(color, CircleShape)
                )
            }
        }
    }


}

@Preview
@Composable
fun PreviewImageCarousel() {
    val dummyImages = listOf(
        Bitmap.createBitmap(200, 300, Bitmap.Config.ARGB_8888)
            .apply { eraseColor(Color.Red.toArgb()) },
        Bitmap.createBitmap(200, 300, Bitmap.Config.ARGB_8888)
            .apply { eraseColor(Color.Green.toArgb()) },
        Bitmap.createBitmap(200, 300, Bitmap.Config.ARGB_8888)
            .apply { eraseColor(Color.Blue.toArgb()) }
    )
    RelicRegistryTheme {
        ImageCarrousel(images = dummyImages,Modifier
            .border(0.dp, Color.Black, shape = RoundedCornerShape(12.dp))
            .background(Color.White, shape = RoundedCornerShape(12.dp))
            .padding(16.dp)){}
    }
}