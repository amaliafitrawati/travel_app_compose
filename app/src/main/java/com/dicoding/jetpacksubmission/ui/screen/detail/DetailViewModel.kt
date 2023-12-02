package com.dicoding.jetpacksubmission.ui.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.jetpacksubmission.data.PlaceRepository
import com.dicoding.jetpacksubmission.model.Place
import com.dicoding.jetpacksubmission.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailViewModel(private val repository : PlaceRepository) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<Place>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<Place>> get() = _uiState

    fun getPlaceById(id: String) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            _uiState.value = UiState.Success(repository.getPlaceById(id))
        }
    }

    fun updateFavorite(id: String) {
        viewModelScope.launch {
            repository.updateFavorite(id)
        }
    }
}