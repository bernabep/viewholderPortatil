package com.example.viewholder.Ui

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.viewholder.R
import com.example.viewholder.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityMainBinding
    private lateinit var mContext: Context
    private lateinit var fragmentManager: FragmentManager
    private lateinit var mActiveFragment: Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)
        mContext = this

        setupNavigationButton()


    }

    private fun setupNavigationButton() {
        fragmentManager = supportFragmentManager
        var mFragmentListRecipe = ListRecipe()
        var mFragmentAddRecipe = AddRecipe()
        var mFragmentConfigRecipe = ConfigRecipe()

        mActiveFragment = mFragmentListRecipe

        fragmentManager.beginTransaction()
            .add(R.id.hostFragment, mFragmentConfigRecipe, ConfigRecipe::class.java.name)
            .hide(mFragmentConfigRecipe)
            .commit()

        fragmentManager.beginTransaction()
            .add(R.id.hostFragment, mFragmentAddRecipe, mFragmentAddRecipe::class.java.name)
            .hide(mFragmentAddRecipe)
            .commit()

        fragmentManager.beginTransaction()
            .add(R.id.hostFragment, mFragmentListRecipe, ListRecipe::class.java.name)
            .commit()


        mBinding.btNavegation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home -> {
                    fragmentManager.beginTransaction()
                        .hide(mActiveFragment)
                        .show(mFragmentListRecipe)
                        .commit()
                    mActiveFragment = mFragmentListRecipe
                    true
                }
                R.id.add -> {
                    fragmentManager.beginTransaction()
                        .hide(mActiveFragment)
                        .show(mFragmentAddRecipe)
                        .commit()
                    mActiveFragment = mFragmentAddRecipe
                    true

                }
                R.id.option -> {
                    fragmentManager.beginTransaction()
                        .hide(mActiveFragment)
                        .show(mFragmentConfigRecipe)
                        .commit()
                    mActiveFragment = mFragmentConfigRecipe
                    true
                }
                else -> {
                    false
                }
            }
       }
    }
}


