package com.vegh.mvisample.utils

import android.content.Context
import android.graphics.pdf.LoadParams
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.vegh.mvisample.data.MediaRepository
import com.vegh.mvisample.domain.MediaItem

class MediaPagingSource(
    private val context: Context,
    private val repository: MediaRepository
) : PagingSource<Long, MediaItem>() {

override suspend fun load(params: LoadParams<Long>): LoadResult<Long, MediaItem> {
    return try {
        val maxDate = params.key ?: Long.MAX_VALUE
        val media = repository.loadMediaBeforeDate(
            context,
            maxDate,
            params.loadSize.coerceAtLeast(60).coerceAtMost(60)
        )

        LoadResult.Page(
            data = media,
            prevKey = null,
            nextKey = media.lastOrNull()?.dateAdded?.minus(1)
        )
    } catch (e: Exception) {
        LoadResult.Error(e)
    }
}

    override fun getRefreshKey(state: PagingState<Long, MediaItem>): Long? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestItemToPosition(anchorPosition)?.dateAdded
        }
    }
}
