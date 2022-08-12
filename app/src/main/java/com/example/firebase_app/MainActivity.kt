package com.example.firebase_app

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.firebase_app.cloudFirestore.RecipeForm
import com.example.firebase_app.googleSignInAuth.AuthScreen
import com.example.firebase_app.googleSignInAuth.AuthViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class MainActivity : ComponentActivity() {

    private val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            Navigation()
//            RecipeForm()
//            AuthScreen(authViewModel = AuthViewModel())

        }
    }
}


@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "screen") {

        composable("screen") { FirstScreen(navController) }

        composable("settings") { SecondScreen(navController) }

        composable("recipeform") { RecipeForm(navController) }

        composable("authscreen") { AuthScreen(navController, AuthViewModel()) }
    }
}


@Composable
fun FirstScreen(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(onClick = {

            navController.navigate("recipeform")

        }) {
            Text(text = "Cloud Firestore")
        }

        Button(onClick = {

            navController.navigate("authscreen")

        }) {
            Text(text = "Google SignIn Auth")
        }

    }
}


@Composable
fun SecondScreen(navController: NavController) {

}



























