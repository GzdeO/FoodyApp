package com.ktln.foodyapp.retrofit

import com.ktln.foodyapp.pojo.CategoryList
import com.ktln.foodyapp.pojo.PopularMealsList
import com.ktln.foodyapp.pojo.MealList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MealApi {

    @GET("random.php")
    fun getRandomMeal(): Call<MealList>

    @GET("lookup.php")
    fun getMealDetail(
        @Query("i") id:String
    ): Call<MealList>

    @GET("filter.php")
    fun getPopularItems(
        @Query("c") categoryName: String
    ) :Call<PopularMealsList>

    @GET("categories.php")
    fun getCategories(): Call<CategoryList>

    @GET("filter.php")
    fun getMealsByCategory(
        @Query("c") categoryName: String
    ): Call<PopularMealsList>

    @GET("search.php")
    fun searchMeals(
        @Query("s") searchQuery:String
    ): Call<MealList>
}