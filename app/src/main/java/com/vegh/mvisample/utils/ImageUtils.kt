package com.vegh.mvisample.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Size
import android.media.ThumbnailUtils

fun getThumbnail(context: Context, uri: Uri): Bitmap? {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        context.contentResolver.loadThumbnail(
            uri,
            Size(200, 200),  // thumbnail size
            null
        )
    } else {
        null
    }
}
fun generateVideoThumbnailFromUri(context: Context, uri: Uri): Bitmap? {
    return try {
        val retriever = android.media.MediaMetadataRetriever()
        context.contentResolver.openFileDescriptor(uri, "r")?.use { pfd ->
            retriever.setDataSource(pfd.fileDescriptor)
            retriever.getFrameAtTime(0, MediaMetadataRetriever.OPTION_CLOSEST)
        }
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}
//fun generateThumbnailFromUri(context: Context, uri: Uri, isVideo: Boolean): Bitmap? {
//    return try {
//        if (isVideo) {
//            generateVideoThumbnailFromUri(context, uri)
//        } else {
//            val inputStream = context.contentResolver.openInputStream(uri)
//            inputStream?.use {
//                val options = BitmapFactory.Options().apply {
//                    inJustDecodeBounds = true
//                }
//                BitmapFactory.decodeStream(it, null, options)
//
//                // دوباره باز کردن stream چون decodeStream قبلی اونو بسته
//                val inputStream2 = context.contentResolver.openInputStream(uri)
//                inputStream2?.use { secondStream ->
//                    val scaleFactor = calculateInSampleSize(options, 100, 100)
//
//                    val realOptions = BitmapFactory.Options().apply {
//                        inSampleSize = scaleFactor
//                    }
//
//                    val decodedBitmap = BitmapFactory.decodeStream(secondStream, null, realOptions)
//                    ThumbnailUtils.extractThumbnail(decodedBitmap, 100, 100)
//                }
//            }
//        }
//    } catch (e: Exception) {
//        e.printStackTrace()
//        null
//    }
//}

fun generateThumbnailFromUri(context: Context, uri: Uri, isVideo: Boolean): Bitmap? {
    return try {
        if (isVideo) {
            generateVideoThumbnailFromUri(context, uri)
        } else {
            val inputStream = context.contentResolver.openInputStream(uri)
            inputStream?.use {
                val options = BitmapFactory.Options().apply {
                    inJustDecodeBounds = false  // No need to just decode bounds
                    inSampleSize = calculateInSampleSize(this, 100, 100)  // Apply sample size directly
                }
                BitmapFactory.decodeStream(it, null, options)?.let { decodedBitmap ->
                    ThumbnailUtils.extractThumbnail(decodedBitmap, 100, 100)
                }
            }
        }
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

fun calculateInSampleSize(options: BitmapFactory.Options, reqWidth: Int, reqHeight: Int): Int {
    val (height: Int, width: Int) = options.outHeight to options.outWidth
    var inSampleSize = 1

    if (height > reqHeight || width > reqWidth) {
        val halfHeight = height / 2
        val halfWidth = width / 2

        while ((halfHeight / inSampleSize) >= reqHeight && (halfWidth / inSampleSize) >= reqWidth) {
            inSampleSize *= 2
        }
    }
    return inSampleSize
}

