package com.example.viewholder.Model

class Recipe(
    var id: String? = null,
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