package com.example.viewholder.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.viewholder.Model.Recipe
import com.example.viewholder.R
import com.example.viewholder.databinding.ItemRecetaBinding

class RecipesAdapter(var recipes:MutableList<Recipe>): RecyclerView.Adapter<RecipesAdapter.ViewHolderRecipes>() {
    private lateinit var mContext:Context

    inner class ViewHolderRecipes(view: View):RecyclerView.ViewHolder(view){
        var mBinding = ItemRecetaBinding.bind(view)
         init {
             mBinding.root.setOnClickListener {
                 Toast.makeText(mContext,"${mBinding.tvTitle.text}",Toast.LENGTH_LONG).show()

             }
         }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderRecipes {
        mContext = parent.context
        var view = LayoutInflater.from(mContext).inflate(R.layout.item_receta,parent,false)

        return ViewHolderRecipes(view)
    }

    override fun onBindViewHolder(holder: ViewHolderRecipes, position: Int) {
        var recipe = recipes[position]

        with(holder){

            mBinding.tvTitle.text = recipe.name
            mBinding.tvIngredient.text = recipe.concatIngredient()
            Glide.with(mContext)
                .load(recipe.urlPhoto)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(mBinding.imPhoto)
        }
    }

    override fun getItemCount(): Int {
        return recipes.size
    }
}