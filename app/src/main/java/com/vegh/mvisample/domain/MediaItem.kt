package com.vegh.mvisample.domain

import android.net.Uri
import androidx.compose.runtime.Immutable
import coil3.Bitmap

@Immutable
data class MediaItem(
    val uri: Uri,
    val isVideo: Boolean,
    val bitmap: Bitmap?,
    val dateAdded: Long
)
