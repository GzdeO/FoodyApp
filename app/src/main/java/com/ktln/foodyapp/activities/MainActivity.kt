package com.ktln.foodyapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.ktln.foodyapp.R
import com.ktln.foodyapp.db.MealDatabase
import com.ktln.foodyapp.viewModel.HomeViewModel
import com.ktln.foodyapp.viewModel.HomeViewModelFactory

class MainActivity : AppCompatActivity() {

    val viewModel:HomeViewModel by lazy {
        val mealDatabase = MealDatabase.getInstance(this)
        val homeViewModelProviderFactory = HomeViewModelFactory(mealDatabase)
        ViewModelProvider(this,homeViewModelProviderFactory)[HomeViewModel::class.java]
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigation=findViewById<BottomNavigationView>(R.id.btmNav)
        val navController=Navigation.findNavController(this, R.id.hostFragment)

        NavigationUI.setupWithNavController(bottomNavigation,navController)
    }
}