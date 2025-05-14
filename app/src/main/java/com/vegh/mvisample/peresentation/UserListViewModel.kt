package com.vegh.mvisample.peresentation

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.vegh.mvisample.data.MediaRepository
import com.vegh.mvisample.domain.MediaItem
import com.vegh.mvisample.peresentation.userList.GalleryIntent
import com.vegh.mvisample.peresentation.userList.GalleryState

import com.vegh.mvisample.utils.MediaPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

import javax.inject.Inject

@HiltViewModel
class UserListViewModel @Inject constructor(
    @ApplicationContext private val appContext: Context,
     private val mediaRepository:MediaRepository
) : ViewModel() {


    private val _state = MutableStateFlow(GalleryState())
    val state: StateFlow<GalleryState> = _state
    private val _pagingData = MutableStateFlow<PagingData<MediaItem>>(PagingData.empty())
    val pagingData: StateFlow<PagingData<MediaItem>> = _pagingData
    val intentChannel = Channel<GalleryIntent>(Channel.UNLIMITED)

    init {
        handleIntents()
    }

    private fun handleIntents() {
        viewModelScope.launch {
            intentChannel.consumeAsFlow().collect { intent ->
                when (intent) {
                    is GalleryIntent.LoadGallery -> {
                        loadMedia()
                    }
                }
            }
        }
    }
    fun sendIntent(intent: GalleryIntent) {
        viewModelScope.launch {
            intentChannel.send(intent)
        }
    }
    private fun loadMedia() {
        _state.update { it.copy(isLoading = true, error = null) }

        viewModelScope.launch {
            Pager(
                config = PagingConfig(pageSize = 30, prefetchDistance = 10),
                pagingSourceFactory = {
                    MediaPagingSource(appContext, mediaRepository)
                }
            ).flow.cachedIn(viewModelScope)
                .catch { e ->
                    _state.update { it.copy(error = e.message, isLoading = false) }
                }
                .collect { paging ->
                    _pagingData.value = paging
                    _state.update { it.copy(isLoading = false) }
                }
        }
    }


    val pagingFlow = Pager(
        config = PagingConfig(
            pageSize = 30,
            prefetchDistance = 10,
            enablePlaceholders = false
        ),
        pagingSourceFactory = {
            MediaPagingSource(appContext, mediaRepository)
        }
    ).flow.cachedIn(viewModelScope)
}
//}