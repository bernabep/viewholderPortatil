package com.example.viewholder.Model

object Database {
fun deleteRecipes(id:Int,lista:MutableList<Recipe>){
    lista.removeAt(id)
}

    fun getRecipes() = mutableListOf<Recipe>(
        Recipe(
            id = "0",
            name = "Pure de papa",
            ingredient = mutableListOf("patatas", "leche", "sal", "mantequilla"),
            urlPhoto = "https://www.recetasderechupete.com/wp-content/uploads/2020/10/Pure-de-patatas-sueco-768x530.jpg"
        ),
        Recipe(
            id = "1",
            name = "Arroz con carne",
            ingredient = mutableListOf("arroz", "carne", "ajo", "zanahoria", "pimiento"),
            urlPhoto = "https://cdn0.recetasgratis.net/es/posts/6/2/2/arroz_de_carnes_34226_orig.jpg"
        ),
        Recipe(
            id ="2",
            name = "Pure de papa",
            ingredient = mutableListOf("patatas", "leche", "sal", "mantequilla"),
            urlPhoto = "https://www.recetasderechupete.com/wp-content/uploads/2020/10/Pure-de-patatas-sueco-768x530.jpg"
        ),
        Recipe(
            id = "3",
            name = "Arroz con carne",
            ingredient = mutableListOf("arroz", "carne", "ajo", "zanahoria", "pimiento"),
            urlPhoto = "https://cdn0.recetasgratis.net/es/posts/6/2/2/arroz_de_carnes_34226_orig.jpg"
        ),
        Recipe(
            id = "4",
            name = "Pure de papa",
            ingredient = mutableListOf("patatas", "leche", "sal", "mantequilla"),
            urlPhoto = "https://www.recetasderechupete.com/wp-content/uploads/2020/10/Pure-de-patatas-sueco-768x530.jpg"
        ),
        Recipe(
            id = "5",
            name = "Arroz con carne",
            ingredient = mutableListOf("arroz", "carne", "ajo", "zanahoria", "pimiento"),
            urlPhoto = "https://cdn0.recetasgratis.net/es/posts/6/2/2/arroz_de_carnes_34226_orig.jpg"
        ),
        Recipe(
            id = "6",
            name = "Pure de papa",
            ingredient = mutableListOf("patatas", "leche", "sal", "mantequilla"),
            urlPhoto = "https://www.recetasderechupete.com/wp-content/uploads/2020/10/Pure-de-patatas-sueco-768x530.jpg"
        ),
        Recipe(
            id = "7",
            name = "Arroz con carne",
            ingredient = mutableListOf("arroz", "carne", "ajo", "zanahoria", "pimiento"),
            urlPhoto = "https://cdn0.recetasgratis.net/es/posts/6/2/2/arroz_de_carnes_34226_orig.jpg"
        ),
        Recipe(
            id = "8",
            name = "Pure de papa",
            ingredient = mutableListOf("patatas", "leche", "sal", "mantequilla"),
            urlPhoto = "https://www.recetasderechupete.com/wp-content/uploads/2020/10/Pure-de-patatas-sueco-768x530.jpg"
        )
    )
}