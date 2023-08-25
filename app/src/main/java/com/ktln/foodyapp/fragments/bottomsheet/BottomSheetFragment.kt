package com.ktln.foodyapp.fragments.bottomsheet

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.ktln.foodyapp.R
import com.ktln.foodyapp.activities.MainActivity
import com.ktln.foodyapp.activities.MealActivity
import com.ktln.foodyapp.databinding.FragmentBottomSheetBinding
import com.ktln.foodyapp.fragments.HomeFragment
import com.ktln.foodyapp.viewModel.HomeViewModel

private const val MEAL_ID = "param1"


class BottomSheetFragment : BottomSheetDialogFragment() {

    private var mealId: String? = null
    private lateinit var binding:FragmentBottomSheetBinding
    private lateinit var viewModel:HomeViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            mealId = it.getString(MEAL_ID)
        }
        viewModel = (activity as MainActivity).viewModel
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding= FragmentBottomSheetBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mealId?.let { viewModel.getMealById(it) }

        observeBottomSheetMeal()
        bottomSheetDialogClick()

    }

    private fun bottomSheetDialogClick() {
        binding.containerBottomSheet.setOnClickListener {
            if (mealName != null && mealThumb != null){
                val intent= Intent(activity,MealActivity::class.java)
                intent.apply {
                    putExtra(HomeFragment.MEAL_ID,mealId)
                    putExtra(HomeFragment.MEAL_NAME,mealName)
                    putExtra(HomeFragment.MEAL_THUMB,mealThumb)
                }
                startActivity(intent)
            }
        }
    }

    private var mealName: String?=null
    private var mealThumb:String?=null
    private fun observeBottomSheetMeal() {
        viewModel.observeBottomSheetMeal().observe(viewLifecycleOwner, Observer {meal->
            Glide.with(this)
                .load(meal.strMealThumb)
                .into(binding.imgBottomSheet)
            binding.areaBottomSheetText.text=meal.strArea
            binding.categoryBottomSheetText.text=meal.strCategory
            binding.mealNameBottomSheet.text=meal.strMeal

            mealName=meal.strMeal
            mealThumb=meal.strMealThumb
        })
    }

    companion object {

        @JvmStatic fun newInstance(param1: String) =
                BottomSheetFragment().apply {
                    arguments = Bundle().apply {
                        putString(MEAL_ID, param1)
                    }
                }
    }
}