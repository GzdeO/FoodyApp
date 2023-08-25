package com.ktln.foodyapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.ktln.foodyapp.R
import com.ktln.foodyapp.adapters.CategoriesAdapter
import com.ktln.foodyapp.adapters.CategoryMealsAdapter
import com.ktln.foodyapp.databinding.ActivityCategoryMealsBinding
import com.ktln.foodyapp.fragments.HomeFragment
import com.ktln.foodyapp.viewModel.CategoryMealsViewModel

class CategoryMealsActivity : AppCompatActivity() {
    lateinit var binding:ActivityCategoryMealsBinding
    lateinit var categoryMealsViewModel:CategoryMealsViewModel

    lateinit var categoryMealsAdapter: CategoryMealsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        binding=ActivityCategoryMealsBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        prepareRecyclerView()

        categoryMealsViewModel=ViewModelProvider(this)[CategoryMealsViewModel::class.java]

        categoryMealsViewModel.getMealsByCategory(intent.getStringExtra(HomeFragment.CATEGORY_NAME)!!)


        categoryMealsViewModel.observeMealsLiveData().observe(this, Observer {mealsList->
            binding.categoryCountText.text=mealsList.size.toString()
            categoryMealsAdapter.setMealsList(mealsList)
        })

        backMainActivity()

    }




    private fun prepareRecyclerView() {
        categoryMealsAdapter= CategoryMealsAdapter()
        binding.rvCategoryMeals.apply {
            layoutManager=GridLayoutManager(context,2,GridLayoutManager.VERTICAL,false)
            adapter=categoryMealsAdapter
        }
    }

    private fun backMainActivity() {
        binding.backButton.setOnClickListener {
            val intent=Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
    }
}