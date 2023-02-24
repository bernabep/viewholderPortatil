package com.example.viewholder.Ui

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.viewholder.Model.Recipe
import com.example.viewholder.databinding.FragmentAddRecipeBinding
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class AddRecipe : Fragment() {
    private val RC_GALLERY = 25
    private val PATH_STORAGE = "imageRecipe"
    private val PATH_DATABASE = "Recipe"
    private lateinit var mBinding: FragmentAddRecipeBinding

    private var photoSelect: Uri? = null


    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        mBinding = FragmentAddRecipeBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding.bnAdd.setOnClickListener {
            openGalery()
        }

        mBinding.btSend.setOnClickListener {
            uploadRecipe()
        }
    }

    private fun uploadRecipe() {

        var title = mBinding.etTitle.text.toString().trim()
        var url = ""
        var ingredient: MutableList<String> =
            mBinding.etIngredient.text.toString().trim().split(",").toMutableList()
        var storageReference = FirebaseStorage.getInstance().reference.child("$PATH_STORAGE/$title")

        photoSelect?.let {
            storageReference.putFile(it).addOnSuccessListener {
                it.storage.downloadUrl.addOnSuccessListener {
                    url = it.toString()
                    var firebaseDatabase =
                        FirebaseDatabase.getInstance().reference.child(PATH_DATABASE)
                    var key = firebaseDatabase.push().key
                    firebaseDatabase.push().setValue(Recipe(id = key, title, ingredient, url))
                        .addOnSuccessListener {

                        }

                }
            }
        }


    }

    private fun openGalery() {
        var intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, RC_GALLERY)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == RC_GALLERY) {
            if (data != null) {
                photoSelect = data.data
                mBinding.imgPhoto.setImageURI(photoSelect)
                mBinding.tilTitle.requestFocus()
            }
        }
    }

}