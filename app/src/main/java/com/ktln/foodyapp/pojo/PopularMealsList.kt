package com.ktln.foodyapp.pojo


import com.google.gson.annotations.SerializedName

data class PopularMealsList(
    @SerializedName("meals")
    val meals: List<PopularMeals>
)