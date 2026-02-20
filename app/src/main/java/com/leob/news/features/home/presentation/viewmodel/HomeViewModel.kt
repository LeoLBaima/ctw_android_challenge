package com.leob.news.features.home.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leob.news.features.home.domain.models.News
import com.leob.news.features.home.domain.repository.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@HiltViewModel
class HomeViewModel @Inject constructor (
    private val repository: HomeRepository
) : ViewModel() {
    private val state = MutableLiveData<UiState>(UiState.Initial)

    val uiState: LiveData<UiState>
        get() = state

    init {
        getNews()
    }

    fun getNews() {
        try {
            state.value = UiState.Loading

            viewModelScope.launch(Dispatchers.IO) {
                repository.getNews().let {
                    state.postValue(UiState.Success(it))
                }
            }

        } catch (e: Exception) {
            state.value = UiState.Error
        }
    }

}

sealed class UiState {
    data object Initial : UiState()
    data class Success(val news: News) : UiState()
    data object Loading : UiState()
    data object Error : UiState()
}