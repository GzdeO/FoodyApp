package com.ktln.foodyapp.adapters



import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ktln.foodyapp.activities.MealActivity
import com.ktln.foodyapp.databinding.MealItemBinding
import com.ktln.foodyapp.fragments.HomeFragment
import com.ktln.foodyapp.pojo.Meal



class MealsAdapter: RecyclerView.Adapter<MealsAdapter.FavoritesMealsAdapterViewHolder>() {

    private lateinit var binding: MealItemBinding
    private lateinit var context: Context



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesMealsAdapterViewHolder {

        val inflater=LayoutInflater.from(parent.context)
        binding= MealItemBinding.inflate(inflater,parent,false)
        context=parent.context
        return FavoritesMealsAdapterViewHolder()
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: FavoritesMealsAdapterViewHolder, position: Int) {
        holder.bind(differ.currentList[position])

    }

    inner class FavoritesMealsAdapterViewHolder(): RecyclerView.ViewHolder(binding.root){
        fun bind(item: Meal){
         binding.apply {
             Glide.with(itemView)
                 .load(differ.currentList[position].strMealThumb)
                 .into(imgItem)

             binding.mealName.text=item.strMeal



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




    private val diffUtil = object : DiffUtil.ItemCallback<Meal>(){
        override fun areItemsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem.idMeal==newItem.idMeal
        }

        override fun areContentsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem==newItem
        }

    }

    val differ = AsyncListDiffer(this,diffUtil)











}