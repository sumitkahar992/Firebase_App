package com.example.firebase_app.cloudFirestore

import androidx.compose.runtime.snapshots.SnapshotStateList
import com.google.firebase.firestore.FirebaseFirestore



object RecipeService {

    val db = FirebaseFirestore.getInstance()

    fun getRecipes(recipes: SnapshotStateList<Recipe>){
        db.collection("recipes").get().addOnSuccessListener {

            recipes.updateList(it.toObjects(Recipe::class.java))

        }.addOnFailureListener {

            recipes.updateList(listOf())

        }
    }
}





private fun <T> SnapshotStateList<T>.updateList(newList: List<T>) {
    clear()
    addAll(newList)
}
