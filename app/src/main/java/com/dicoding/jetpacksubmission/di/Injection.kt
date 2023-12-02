package com.dicoding.jetpacksubmission.di

import com.dicoding.jetpacksubmission.data.PlaceRepository

object Injection {
    fun provideRepository() : PlaceRepository{
        return PlaceRepository.getInstance()
    }
}