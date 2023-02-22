package com.example.viewholder

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.viewholder.Database.getRecipes
import com.example.viewholder.databinding.ActivityMainBinding
import com.example.viewholder.databinding.ItemRecetaBinding
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query

class MainActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityMainBinding
    private lateinit var mAdapter: RecipesAdapter
    private lateinit var mLayoutManager: LayoutManager
    private lateinit var mContext: Context
    private lateinit var mContextFirebase: Context
    private lateinit var mAdapterFirebase: FirebaseRecyclerAdapter<Recipe, ViewHolderFirebase>
    private lateinit var mLayoutManagerFirebase: LinearLayoutManager
    private lateinit var query: Query
    override fun onCreate(savedInstanceState: Bundle?) {
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)
        mContext = this
        var mRecipes = getRecipes()

        mAdapter = RecipesAdapter(mRecipes)
        mLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        mLayoutManagerFirebase = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        mBinding.recyclerview.apply {
            setHasFixedSize(true)
            adapter = mAdapter
            layoutManager = mLayoutManager
        }

        query = FirebaseDatabase.getInstance().reference.child("Recipe")

        var option =
            FirebaseRecyclerOptions.Builder<Recipe>().setQuery(query, Recipe::class.java).build()

        mAdapterFirebase =
            object : FirebaseRecyclerAdapter<Recipe, ViewHolderFirebase>(option) {
                override fun onCreateViewHolder(
                    parent: ViewGroup,
                    viewType: Int
                ): ViewHolderFirebase {
                    mContextFirebase = parent.context
                    var view = layoutInflater.inflate(R.layout.item_receta, parent, false)

                    return ViewHolderFirebase(view)
                }

                override fun onBindViewHolder(
                    holder: ViewHolderFirebase,
                    position: Int,
                    model: Recipe
                ) {
                    var recipe = getItem(position)

                    with(holder) {
                        var recipeKey =
                            FirebaseDatabase.getInstance().getReference().child("Recipe")
                                .child(model.id.toString())
                        bindingFirebase.tvTitle.text = recipe.name
                        bindingFirebase.tvIngredient.text = recipe.concatIngredient()
                        Glide.with(mContextFirebase)
                            .load(recipe.urlPhoto)
                            .centerCrop()
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(bindingFirebase.imPhoto)
                    }
                    
                    holder.bindingFirebase.root.setOnClickListener {
                        getRef(position).removeValue()
                        mBinding.recyclerviewFirebase.adapter = mAdapterFirebase
                    }

                }

                override fun onDataChanged() {
                    super.onDataChanged()
                    query = FirebaseDatabase.getInstance().reference.child("Recipe")
                }
            }

        mBinding.recyclerviewFirebase.apply {
            setHasFixedSize(true)
            adapter = mAdapterFirebase
            layoutManager = mLayoutManagerFirebase
        }

    }

    override fun onStart() {
        super.onStart()
        mAdapterFirebase.startListening()
    }

    override fun onStop() {
        super.onStop()
        mAdapterFirebase.stopListening()
    }

    class ViewHolderFirebase(view: View) : RecyclerView.ViewHolder(view) {
        var bindingFirebase = ItemRecetaBinding.bind(view)


    }
}


