package com.example.firebase_app.cloudFirestore

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.firebase_app.googleSignInAuth.GoogleSignInButtonUi


@Composable
fun RecipeForm(navController: NavController) {

    val context = LocalContext.current
    val titleValue = remember { mutableStateOf(TextFieldValue()) }
    val ingredientsValue = remember { mutableStateOf(TextFieldValue()) }
    val processValue = remember { mutableStateOf(TextFieldValue()) }

    val recipes = remember { mutableStateListOf(Recipe()) }
    val isSaved = remember { mutableStateOf(false) }

    RecipeService.getRecipes(recipes)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        DetailInput(fieldValue = titleValue, label = "Title")
        DetailInput(fieldValue = ingredientsValue, label = "Ingredients", singleLine = false)
        DetailInput(fieldValue = processValue, label = "Process", singleLine = false)

        Spacer(modifier = Modifier.padding(5.dp))

        GoogleSignInButtonUi(
            text = " Account Sign Up With Google ",
            loadingText = "Signing In ...",
            onClicked = {}
        )

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {

                val recipe = Recipe(
                    title = titleValue.value.text,
                    ingredients = ingredientsValue.value.text,
                    process = processValue.value.text
                )

                RecipeService.db.collection("recipes")
                    .add(recipe)
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            showToast(context, "saved !")
                            isSaved.value = true
                            processValue.value = TextFieldValue()
                            titleValue.value = TextFieldValue()
                            ingredientsValue.value = TextFieldValue()
                        } else {
                            showToast(context, "Errrr saving..")
                        }
                    }.addOnFailureListener {
                        showToast(context, "Error : ${it.message}")
                    }


            }) {
            Text(text = "SAVE")
        }

        if (isSaved.value) {
            RecipeService.getRecipes(recipes)
            isSaved.value = false
        }

        Spacer(modifier = Modifier.padding(8.dp))
        LazyRow {
            items(recipes) { item: Recipe ->
                RecipeListItem(recipe = item)
            }
        }

    }


}

@Composable
fun RecipeListItem(recipe: Recipe) {
    Card(
        modifier = Modifier.padding(end = 8.dp),
        shape = RoundedCornerShape(4.dp),
        elevation = 2.dp
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Text(text = recipe.title)
            Spacer(modifier = Modifier.padding(8.dp))
            Text(text = recipe.ingredients)
            Spacer(modifier = Modifier.padding(8.dp))
            Text(text = recipe.process)
        }

    }
}

private fun showToast(context: Context, msg: String) {
    Toast.makeText(
        context,
        msg,
        Toast.LENGTH_SHORT
    ).show()
}


@Composable
fun DetailInput(
    fieldValue: MutableState<TextFieldValue>,
    label: String,
    singleLine: Boolean = true
) {
    OutlinedTextField(
        label = { Text(text = label) },
        modifier = if (singleLine) {
            Modifier.fillMaxWidth()
        } else {
            Modifier
                .fillMaxWidth()
                .height(140.dp)
        },
        singleLine = singleLine,
        value = fieldValue.value,
        onValueChange = { fieldValue.value = it }
    )

}
