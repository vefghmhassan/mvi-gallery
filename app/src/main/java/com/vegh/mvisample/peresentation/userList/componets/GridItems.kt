package com.vegh.mvisample.peresentation.userList.componets

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.ThumbnailUtils
import android.provider.MediaStore
import android.net.Uri
import android.os.Build
import android.util.Size
import android.widget.ImageView
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest
import coil3.request.crossfade
import coil3.size.Scale
import coil3.size.ViewSizeResolver
import com.vegh.mvisample.domain.MediaItem
import com.vegh.mvisample.utils.generateThumbnailFromUri
import com.vegh.mvisample.utils.generateVideoThumbnailFromUri
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

//@Composable
//fun GridItem(item: MediaItem, onClick: () -> Unit) {
//    val context = LocalContext.current
//    var thumbnail by remember { mutableStateOf<Bitmap?>(null) }
//    val isLoading = remember { mutableStateOf(true) }
//
//    LaunchedEffect(item.uri) {
//        isLoading.value = true
//        thumbnail = withContext(Dispatchers.IO) {
//            generateThumbnailFromUri(context,item.uri,item.isVideo)
//        }
//        isLoading.value = false
//    }
//
//    Box(
//        modifier = Modifier
//            .fillMaxWidth()
//            .height(108.dp)
//            .border(1.dp, Color.Gray)
//            .clickable { onClick() }
//            .background(Color.LightGray),
//        contentAlignment = Alignment.Center
//    ) {
//        when {
//            isLoading.value -> {
//                CircularProgressIndicator(modifier = Modifier.size(29.dp), color = Color.Blue)
//            }
//
//            thumbnail != null -> {
//                Image(
//                    bitmap = thumbnail!!.asImageBitmap(),
//                    contentDescription = null,
//                    modifier = Modifier.fillMaxSize(),
//                    contentScale = ContentScale.Crop
//                )
//            }
//        }
//
//        if (item.isVideo) {
//            Icon(
//                imageVector = Icons.Default.PlayArrow,
//                contentDescription = null,
//                tint = Color.White,
//                modifier = Modifier.size(29.dp)
//            )
//        }
//    }
//}

//@Composable
//fun GridItem(item: MediaItem, onClick: () -> Unit) {
//    val context = LocalContext.current
//    val view = LocalView.current
//    val painter = rememberAsyncImagePainter(
//        model = ImageRequest.Builder(context)
//            .data(item.uri)
//            .crossfade(true)
//            .size(200) // دقیق‌تر از عدد ثابت
//            .build()
//    )
//    Box(
//        modifier = Modifier
//            .fillMaxWidth()
//            .aspectRatio(1f)
//            .border(1.dp, Color.Gray)
//            .clickable { onClick() }
//            .background(Color.LightGray),
//        contentAlignment = Alignment.Center
//    ) {
//
//        Image(
//            painter = painter,
//            contentDescription = null,
//            contentScale = ContentScale.Crop,
//            modifier = Modifier.fillMaxSize()
//        )
//
//        if (item.isVideo) {
//            Icon(
//                imageVector = Icons.Default.PlayArrow,
//                contentDescription = null,
//                tint = Color.White,
//                modifier = Modifier.size(29.dp)
//            )
//        }
//    }

//
//    @Composable
//    fun GridItem(
//        item: MediaItem,
//        onClick: () -> Unit
//    ) {
//        Box(
//            modifier = Modifier
//                .aspectRatio(1f) // مربع
//                .clip(RoundedCornerShape(4.dp))
//                .clickable { onClick() }
//                .background(Color.LightGray),
//            contentAlignment = Alignment.Center
//        ) {
//            AsyncImage(
//                model = ImageRequest.Builder(LocalContext.current)
//                    .data(item.uri)
//
//                    .crossfade(true)
//                    .build(),
//                contentDescription = null,
//                contentScale = ContentScale.Crop,
//                modifier = Modifier.fillMaxSize()
//            )
//
//            if (item.isVideo) {
//                Icon(
//                    imageVector = Icons.Default.PlayArrow,
//                    contentDescription = null,
//                    tint = Color.White,
//                    modifier = Modifier
//                        .size(28.dp)
//                        .align(Alignment.Center)
//                )
//            }
//        }
//    }


//@Composable
//fun GridItem(
//    item: MediaItem,
//    onClick: () -> Unit
//) {
//    val context = LocalContext.current
//
//    Box(
//        modifier = Modifier
//            .aspectRatio(1f)
//            .clip(RoundedCornerShape(4.dp))
//            .clickable { onClick() }
//            .background(Color(0xFFEEEEEE)), // رنگ ساده‌تر
//        contentAlignment = Alignment.Center
//    ) {
//        // استفاده از rememberImagePainter به جای ImageRequest Builder
//        AsyncImage(
//            model = item.uri,
//            contentDescription = null,
//            contentScale = ContentScale.Crop,
//            modifier = Modifier.fillMaxSize()
//        )
//
//        if (item.isVideo) {
//            // استفاده از Icon بدون انیمیشن یا اندازه‌گیری مجدد
//            Icon(
//                imageVector = Icons.Default.PlayArrow,
//                contentDescription = null,
//                tint = Color.White,
//                modifier = Modifier
//                    .size(26.dp)
//                    .align(Alignment.Center)
//            )
//        }
//    }
//}

@Composable
fun GridItem(item: MediaItem, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .clip(RoundedCornerShape(4.dp))
            .clickable { onClick() }
            .background(Color(0xFFEEEEEE)),
        contentAlignment = Alignment.Center
    ) {
        item.bitmap?.let {
            Image(
                bitmap = it.asImageBitmap(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }

        if (item.isVideo) {
            Icon(
                imageVector = Icons.Default.PlayArrow,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(26.dp).align(Alignment.Center)
            )
        }
    }
}



