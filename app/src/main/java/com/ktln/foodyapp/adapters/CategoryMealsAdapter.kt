package com.ktln.foodyapp.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ktln.foodyapp.activities.MealActivity
import com.ktln.foodyapp.databinding.ActivityMealBinding
import com.ktln.foodyapp.databinding.MealItemBinding
import com.ktln.foodyapp.fragments.HomeFragment
import com.ktln.foodyapp.pojo.Meal
import com.ktln.foodyapp.pojo.PopularMeals

class CategoryMealsAdapter : RecyclerView.Adapter<CategoryMealsAdapter.CategoryMealsViewModel>(){

    private lateinit var binding: MealItemBinding
    private lateinit var context: Context

    private var mealsList= ArrayList<PopularMeals>()

    fun setMealsList(mealList: List<PopularMeals>){
       this.mealsList=mealList as ArrayList<PopularMeals>
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryMealsViewModel {
        val inflater=LayoutInflater.from(parent.context)
        binding= MealItemBinding.inflate(inflater,parent,false)
        context=parent.context
        return CategoryMealsViewModel()
    }


    override fun getItemCount(): Int {
        return mealsList.size
    }


    override fun onBindViewHolder(holder: CategoryMealsViewModel, position: Int) {
        holder.bind(mealsList[position])
    }
    inner class CategoryMealsViewModel(): RecyclerView.ViewHolder(binding.root){
        fun bind(item: PopularMeals){
            binding.apply {
                Glide.with(itemView)
                    .load(mealsList[position].strMealThumb)
                    .into(binding.imgItem)
                binding.mealName.text=mealsList[position].strMeal
            }

            binding.imgCardContainer.setOnClickListener {
                var mealName: String?=item.strMeal
                var mealThumb:String?=item.strMealThumb
                var mealId: String? = item.idMeal
                if (mealName != null && mealThumb != null){
                    val intent=Intent(context,MealActivity::class.java)
                    intent.apply {
                        putExtra(HomeFragment.MEAL_ID,mealId)
                        putExtra(HomeFragment.MEAL_NAME,mealName)
                        putExtra(HomeFragment.MEAL_THUMB,mealThumb)
                    }

                    context.startActivity(intent)
                }
            }
        }
    }






}