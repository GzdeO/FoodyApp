package com.ktln.foodyapp.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ktln.foodyapp.pojo.PopularMeals
import com.ktln.foodyapp.pojo.PopularMealsList
import com.ktln.foodyapp.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryMealsViewModel : ViewModel() {

    val mealsLiveData = MutableLiveData<List<PopularMeals>>()

    fun getMealsByCategory(categoryName:String){
        RetrofitInstance.api.getMealsByCategory(categoryName).enqueue(object : Callback<PopularMealsList>{
            override fun onResponse(
                call: Call<PopularMealsList>,
                response: Response<PopularMealsList>
            ) {
                response.body()?.let {mealsList->
                    mealsLiveData.postValue(mealsList.meals)
                }
            }

            override fun onFailure(call: Call<PopularMealsList>, t: Throwable) {
                Log.e("CategoryMealsViewModel: ", t.message.toString())
            }

        })
    }

    fun observeMealsLiveData():LiveData<List<PopularMeals>>{
        return mealsLiveData
    }
}