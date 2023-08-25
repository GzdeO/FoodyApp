package com.ktln.foodyapp.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ktln.foodyapp.db.MealDatabase
import com.ktln.foodyapp.pojo.Category
import com.ktln.foodyapp.pojo.CategoryList
import com.ktln.foodyapp.pojo.PopularMealsList
import com.ktln.foodyapp.pojo.PopularMeals
import com.ktln.foodyapp.pojo.Meal
import com.ktln.foodyapp.pojo.MealList
import com.ktln.foodyapp.retrofit.RetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel(
    private val mealDatabase: MealDatabase
):ViewModel() {

    private var randomMealLiveData = MutableLiveData<Meal>()
    private var popularItemsLiveData=MutableLiveData<List<PopularMeals>>()
    private var categoryItemLiveData=MutableLiveData<List<Category>>()
    private var favoritesMealsLiveData=mealDatabase.mealDao().getAllMeals()
    private var bottomSheetMealLiveData=MutableLiveData<Meal>()
    private val searchedMealsLiveData=MutableLiveData<List<Meal>>()


    private var saveStateRandomMeal: Meal?=null


    fun getRandomMeal(){
        saveStateRandomMeal?.let {randomMeal->
            randomMealLiveData.postValue(randomMeal)
            return

        }
        RetrofitInstance.api.getRandomMeal().enqueue(object : Callback<MealList> {
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                if (response.body() != null){
                    val randomMeal: Meal = response.body()!!.meals[0]
                    randomMealLiveData.value=randomMeal
                    saveStateRandomMeal=randomMeal

                }else{
                    return
                }
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.d("Home Fragment: ", t.message.toString())
            }

        })
    }

    fun getPopularItems(){
        RetrofitInstance.api.getPopularItems("Side").enqueue(object : Callback<PopularMealsList>{
            override fun onResponse(call: Call<PopularMealsList>, response: Response<PopularMealsList>) {
                if (response.body() != null){
                    popularItemsLiveData.value=response.body()!!.meals
                }else{
                    return
                }
            }

            override fun onFailure(call: Call<PopularMealsList>, t: Throwable) {
                Log.d("Home Fragment", t.message.toString())
            }

        })
    }


    fun getCategories(){
        RetrofitInstance.api.getCategories().enqueue(object : Callback<CategoryList>{
            override fun onResponse(call: Call<CategoryList>, response: Response<CategoryList>) {
               response.body()?.let {categoryList->
                   categoryItemLiveData.postValue(categoryList.categories)
               }
            }

            override fun onFailure(call: Call<CategoryList>, t: Throwable) {
                Log.e("HomeViewModel", t.message.toString())
            }
        })
    }

    fun getMealById(id:String){
        RetrofitInstance.api.getMealDetail(id).enqueue(object : Callback<MealList>{
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                val meal=response.body()?.meals?.first()
                meal?.let {meal->
                    bottomSheetMealLiveData.postValue(meal)
                }
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.e("HomeViewModel", t.message.toString())
            }

        })
    }

    fun deleteMeal(meal: Meal){
        viewModelScope.launch {
            mealDatabase.mealDao().delete(meal)
        }
    }

    fun insertMeal(meal:Meal){
        viewModelScope.launch {
            mealDatabase.mealDao().upsert(meal)
        }
    }

    fun searchMeals(searchQuery : String) = RetrofitInstance.api.searchMeals(searchQuery).enqueue(object : Callback<MealList>{
        override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
            val mealsList=response.body()?.meals
            mealsList?.let {
                searchedMealsLiveData.postValue(it)
            }
        }

        override fun onFailure(call: Call<MealList>, t: Throwable) {
            Log.e("HomeViewModel", t.message.toString())
        }

    })


    fun observeSearchedMealsLiveData(): LiveData<List<Meal>> = searchedMealsLiveData

    fun observeRandomMealLiveData() : LiveData<Meal>{
        return randomMealLiveData
    }

    fun observePopularItemsLiveData():LiveData<List<PopularMeals>>{
        return popularItemsLiveData
    }

    fun observeCategoriesLiveData(): LiveData<List<Category>>{
        return categoryItemLiveData
    }

    fun observeFavoritesMealsLiveData():LiveData<List<Meal>>{
        return favoritesMealsLiveData
    }

    fun observeBottomSheetMeal() : LiveData<Meal> =  bottomSheetMealLiveData
}