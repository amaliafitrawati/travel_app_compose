package com.dicoding.jetpacksubmission.model

data class Place(
    val id: String,
    val name: String,
    val photoUrl: Int,
    val description: String,
    val price: String,
    val location: String,
    val region : String,
    var isFavorite: Boolean = false
)