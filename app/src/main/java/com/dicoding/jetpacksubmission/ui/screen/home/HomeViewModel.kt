package com.dicoding.jetpacksubmission.ui.screen.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.dicoding.jetpacksubmission.data.PlaceRepository
import com.dicoding.jetpacksubmission.model.Place
import com.dicoding.jetpacksubmission.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.catch

class HomeViewModel(private val repository : PlaceRepository) : ViewModel(){
    private val _uiState: MutableStateFlow<UiState<List<Place>>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<List<Place>>> get() = _uiState

    private val _query = mutableStateOf("")
    val query: State<String> get() = _query

    fun search(newQuery : String) = viewModelScope.launch {
        _query.value = newQuery
        repository.searchPlaces(_query.value)
            .catch{
                _uiState.value = UiState.Error(it.message.toString())
            }
            .collect{
                _uiState.value = UiState.Success(it)
            }
    }
}

