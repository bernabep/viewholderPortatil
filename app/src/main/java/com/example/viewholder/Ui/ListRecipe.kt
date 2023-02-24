package com.example.viewholder.Ui

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.viewholder.Adapter.RecipesAdapter
import com.example.viewholder.Model.Database
import com.example.viewholder.Model.Recipe
import com.example.viewholder.R
import com.example.viewholder.databinding.FragmentListRecipeBinding
import com.example.viewholder.databinding.ItemRecetaBinding
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ListRecipe.newInstance] factory method to
 * create an instance of this fragment.
 */
class ListRecipe : Fragment() {

    private lateinit var mBinding: FragmentListRecipeBinding
    private lateinit var mAdapter: RecipesAdapter
    private lateinit var mLayoutManager: RecyclerView.LayoutManager
    private lateinit var mAdapterFirebase: FirebaseRecyclerAdapter<Recipe, ViewHolderFirebase>
    private lateinit var mLayoutManagerFirebase: LinearLayoutManager
    private lateinit var query: Query

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
/*
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }*/

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        mBinding = FragmentListRecipeBinding.inflate(inflater, container, false)
        return mBinding.root

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        var mRecipes = Database.getRecipes()

        mAdapter = RecipesAdapter(mRecipes)
        mLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        mLayoutManagerFirebase = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
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
                private lateinit var mContextFirebase: Context
                override fun onCreateViewHolder(
                    parent: ViewGroup,
                    viewType: Int,
                ): ViewHolderFirebase {
                    mContextFirebase = parent.context
                    var view = layoutInflater.inflate(R.layout.item_receta, parent, false)

                    return ViewHolderFirebase(view)
                }

                override fun onBindViewHolder(
                    holder: ViewHolderFirebase,
                    position: Int,
                    model: Recipe,
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
                    notifyDataSetChanged()
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

