package com.example.viewholder

import android.view.autofill.AutofillId

class Recipe(
    var id:Int? = null,
    var name:String = "",
    var ingredient:MutableList<String> = mutableListOf(),
    var urlPhoto:String = ""
) {

    fun concatIngredient():String{
        var strIngredients:String = "Los ingredientes son:"
        for (item in ingredient) {
            strIngredients = "$strIngredients $item"
        }
        return strIngredients
    }
}