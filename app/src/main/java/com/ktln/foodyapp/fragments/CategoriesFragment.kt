package com.ktln.foodyapp.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.ktln.foodyapp.R
import com.ktln.foodyapp.activities.MainActivity
import com.ktln.foodyapp.adapters.CategoriesAdapter
import com.ktln.foodyapp.databinding.FragmentCategoriesBinding
import com.ktln.foodyapp.viewModel.HomeViewModel


class CategoriesFragment : Fragment() {

    private lateinit var binding:FragmentCategoriesBinding
    private lateinit var categoriesAdapter:CategoriesAdapter
    private lateinit var viewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = (activity as MainActivity).viewModel

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentCategoriesBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prepareRecyclerView()

        observeCategories()

        backToMainMenu()
    }



    private fun observeCategories() {
        viewModel.observeCategoriesLiveData().observe(viewLifecycleOwner, Observer {categories->
            categoriesAdapter.setCategoryList(categories)
        })
    }

    private fun prepareRecyclerView() {
        categoriesAdapter= CategoriesAdapter()
        binding.rvCategories.apply {
            layoutManager=GridLayoutManager(context,3,GridLayoutManager.VERTICAL,false)
            adapter=categoriesAdapter
        }
    }
    private fun backToMainMenu() {
        binding.backMainMenu.setOnClickListener {
            val intent=Intent(context,MainActivity::class.java)
            startActivity(intent)
        }
    }

}