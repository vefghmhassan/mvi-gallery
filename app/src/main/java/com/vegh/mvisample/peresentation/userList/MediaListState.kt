package com.vegh.mvisample.peresentation.userList

import androidx.paging.PagingData
import com.vegh.mvisample.domain.MediaItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

data class MediaListState(val mediaFlow: Flow<PagingData<MediaItem>> = emptyFlow())
data class MediaUiState(
    val mediaFlow: Flow<PagingData<MediaItem>> = emptyFlow()
)