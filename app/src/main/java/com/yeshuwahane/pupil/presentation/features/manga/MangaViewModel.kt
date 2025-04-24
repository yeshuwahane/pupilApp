package com.yeshuwahane.pupil.presentation.features.manga

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yeshuwahane.pupil.data.utils.DataResource
import com.yeshuwahane.pupil.data.utils.NetworkMonitor
import com.yeshuwahane.pupil.domain.usecase.MangaUseCase
import com.yeshuwahane.pupil.presentation.features.manga.manga_details.MangaDetailUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MangaViewModel @Inject constructor(
    private val useCase: MangaUseCase,
    private val networkMonitor: NetworkMonitor // assume this is injected
) : ViewModel() {

    private val _uiState = MutableStateFlow(MangaUiState())
    val uiState = _uiState.asStateFlow()

    private val _detailUiState = MutableStateFlow(MangaDetailUiState())
    val detailUiState = _detailUiState.asStateFlow()

    private var currentPage = 1
    private var isFetching = false
    private var endReached = false

    init {
        observeNetwork()
    }

    fun loadManga() {
        if (isFetching || endReached) return
        isFetching = true

        viewModelScope.launch {
            val isConnected = networkMonitor.isConnected.value

            if (!isConnected && currentPage == 1) {
                val cached = useCase.fetchCachedManga()
                _uiState.update {
                    it.copy(mangaModelResourceState = DataResource.success(cached))
                }
                isFetching = false
                return@launch
            }

            if (currentPage == 1) {
                _uiState.update { it.copy(mangaModelResourceState = DataResource.loading()) }
            }

            val result = useCase.fetchManga(currentPage)

            if (result.isSuccess()) {
                val list = _uiState.value.mangaModelResourceState.data.orEmpty() + result.data.orEmpty()
                _uiState.update { it.copy(mangaModelResourceState = DataResource.success(list)) }
                if (result.data.isNullOrEmpty()) endReached = true
                else currentPage++
            } else {
                _uiState.update {
                    it.copy(
                        mangaModelResourceState = DataResource.error(result.error, it.mangaModelResourceState.data)
                    )
                }
            }

            isFetching = false
        }
    }

    fun retryLoad() {
        loadManga()
    }

    fun loadMangaDetail(id: String) {

        _detailUiState.value = MangaDetailUiState(
            mangaDetailResourceState = DataResource.loading()
        )

        viewModelScope.launch {

            val result = useCase.fetchMangaDetail(id)
            _detailUiState.value = MangaDetailUiState(mangaDetailResourceState = result)
        }
    }

    private fun observeNetwork() {
        viewModelScope.launch {
            networkMonitor.isConnected.collect { isOnline ->
                if (isOnline && uiState.value.mangaModelResourceState.data.isNullOrEmpty()) {
                    loadManga()
                }
            }
        }
    }
}
