package com.dicoding.jetpacksubmission.ui.screen.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.jetpacksubmission.data.PlaceRepository
import com.dicoding.jetpacksubmission.model.Place
import com.dicoding.jetpacksubmission.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class FavoriteViewModel (private val repository : PlaceRepository) : ViewModel(){
    private val _uiState: MutableStateFlow<UiState<List<Place>>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<List<Place>>> get() = _uiState

    fun getFavoritePlace() = viewModelScope.launch {
        repository.getFavoritePlace()
            .catch {
                _uiState.value = UiState.Error(it.message.toString())
            }
            .collect {
                _uiState.value = UiState.Success(it)
            }
    }

}