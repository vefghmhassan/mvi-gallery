package com.vegh.mvisample.peresentation.userList

import com.vegh.mvisample.domain.MediaItem

data class GalleryState(
    val users: List<MediaItem> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
