package com.ktln.foodyapp.pojo


import com.google.gson.annotations.SerializedName

data class PopularMeals(
    @SerializedName("idMeal")
    val idMeal: String,
    @SerializedName("strMeal")
    val strMeal: String,
    @SerializedName("strMealThumb")
    val strMealThumb: String
)