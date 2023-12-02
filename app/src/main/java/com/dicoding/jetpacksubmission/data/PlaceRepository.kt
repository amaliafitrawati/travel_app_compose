package com.dicoding.jetpacksubmission.data

import android.util.Log
import com.dicoding.jetpacksubmission.model.Place
import com.dicoding.jetpacksubmission.model.PlaceData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf

class PlaceRepository {
    private val listPlace = mutableListOf<Place>()

    init {
        if (listPlace.isEmpty()) {
            PlaceData.places.forEach {
                listPlace.add(it)
            }
        }
    }

    fun getPlace(): List<Place> {
        return PlaceData.places
    }

    fun getPlaceById(id: String): Place {
        return listPlace.first {
            it.id == id
        }
    }

    fun searchPlaces(query: String) = flow {
        val place = PlaceData.places.filter {
            it.name.contains(query, ignoreCase = true)
        }
        emit(place)
    }

    fun updateFavorite(id: String): Flow<Boolean> {
        val index = listPlace.indexOfFirst { it.id == id }
        val result =
            if (index >= 0) {
                val place = listPlace[index]
                listPlace[index] = place.copy(isFavorite = !place.isFavorite)
                Log.e("FAV TEST", "name ${listPlace[index].name} fav ${listPlace[index].isFavorite}")
                true
            }else{
                false
            }
        return flowOf(result)
    }

    fun getFavoritePlace() : Flow<List<Place>> {
        return flowOf(listPlace.filter { it.isFavorite })
    }

    companion object {
        @Volatile
        private var instance: PlaceRepository? = null
        fun getInstance(): PlaceRepository =
            instance ?: synchronized(this) {
                PlaceRepository().apply {
                    instance = this
                }
            }
    }
}