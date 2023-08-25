package com.ktln.foodyapp.activities


import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.ktln.foodyapp.R
import com.ktln.foodyapp.databinding.ActivityMealBinding
import com.ktln.foodyapp.db.MealDatabase
import com.ktln.foodyapp.fragments.HomeFragment
import com.ktln.foodyapp.pojo.Meal
import com.ktln.foodyapp.viewModel.MealViewModel
import com.ktln.foodyapp.viewModel.MealViewModelFactory

class MealActivity : AppCompatActivity() {

    private lateinit var mealId:String
    private lateinit var mealName:String
    private lateinit var mealThumb:String

    private lateinit var binding: ActivityMealBinding


    private lateinit var youtubeLink:String
    private lateinit var mealMvvm:MealViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        binding=ActivityMealBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val mealDatabase=MealDatabase.getInstance(this)
        val viewModelFactory=MealViewModelFactory(mealDatabase)

        mealMvvm=ViewModelProvider(this,viewModelFactory)[MealViewModel::class.java]

        getMealInformationFromIntent()

        setInformationInViews()

        loadingCase()

        mealMvvm.getMealDetail(mealId)

        observerMealDetailsLiveData()

        onYoutubeImageClick()
        onFavoriteClick()

        backMainActivity()
    }

    private fun onFavoriteClick() {
        binding.btnFavorites.setOnClickListener {
            mealToSave?.let {
                mealMvvm.insertMeal(it)
                Toast.makeText(this,"Meal Saved",Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun backMainActivity() {
        binding.backArrow.setOnClickListener {
            val intent=Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun onYoutubeImageClick() {
        binding.youtubeImg.setOnClickListener {
            val intent=Intent(Intent.ACTION_VIEW, Uri.parse(youtubeLink))
            startActivity(intent)
        }
    }

    private var mealToSave:Meal?=null
    private fun observerMealDetailsLiveData() {
        mealMvvm.observerMealDetailsLiveData().observe(this,object : Observer<Meal>{
            override fun onChanged(t: Meal?) {
                onResponseCase()
                val meal=t
                mealToSave=meal

                binding.apply {
                    categoryText.text=meal!!.strCategory
                    locationText.text=meal.strArea
                    instructionsText.text=meal.strInstructions

                    youtubeLink=meal.strYoutube
                }
            }

        })
    }

    private fun setInformationInViews() {
        binding.apply {
            Glide.with(applicationContext)
                .load(mealThumb)
                .into(imgMealDetail)
            collapsingToolbar.title=mealName
            collapsingToolbar.setCollapsedTitleTextColor(resources.getColor(R.color.black))
            collapsingToolbar.setExpandedTitleColor(resources.getColor(R.color.white))
        }

    }

    private fun getMealInformationFromIntent() {
        val intent=intent
        mealId=intent.getStringExtra(HomeFragment.MEAL_ID)!!
        mealName=intent.getStringExtra(HomeFragment.MEAL_NAME)!!
        mealThumb=intent.getStringExtra(HomeFragment.MEAL_THUMB)!!
    }

    private fun loadingCase(){
        binding.apply {
            prgBar.visibility=View.VISIBLE
            btnFavorites.visibility=View.INVISIBLE
            instructionsTitle.visibility=View.INVISIBLE
            categoryText.visibility=View.INVISIBLE
            locationText.visibility=View.INVISIBLE
            youtubeImg.visibility=View.INVISIBLE
        }

    }

    private fun onResponseCase(){
        binding.apply {
            prgBar.visibility=View.INVISIBLE
            btnFavorites.visibility=View.VISIBLE
            instructionsTitle.visibility=View.VISIBLE
            categoryText.visibility=View.VISIBLE
            locationText.visibility=View.VISIBLE
            youtubeImg.visibility=View.VISIBLE
        }

    }
}