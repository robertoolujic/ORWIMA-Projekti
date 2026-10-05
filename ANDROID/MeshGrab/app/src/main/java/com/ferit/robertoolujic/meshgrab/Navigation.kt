package com.ferit.robertoolujic.meshgrab

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.ferit.robertoolujic.meshgrab.data.CollectionViewModel
import com.ferit.robertoolujic.meshgrab.data.ModelViewModel
import com.ferit.robertoolujic.meshgrab.data.UserViewModel

object Routes{
    val SCREEN_HOME = "home"
    val SCREEN_PROFILE = "profile/{userId}"
    val SCREEN_UPLOAD = "upload"
    val SCREEN_MODEL = "model/{modelId}"
    val SCREEN_USER = "user/{userId}"
    val SCREEN_COLLECTION = "collection/{collectionId}"

    fun getModelPath(modelId: String?) : String {
        if (modelId != null) {
            return "model/$modelId"
        }
        return "model/0"
    }
    fun getProfilePath(userId: String?) : String {
        if (userId != null) {
            return "profile/$userId"
        }
        return "user/0"
    }
    fun getUserPath(userId: String?) : String {
        if (userId != null) {
            return "user/$userId"
        }
        return "user/0"
    }
    fun getCollectionPath(collectionId: String?) : String {
        if (collectionId != null) {
            return "collection/$collectionId"
        }
        return "collection/0"
    }
}

@Composable
fun Navigation(
    navController : NavHostController,
    modelViewModel : ModelViewModel,
    userViewModel : UserViewModel,
    collectionViewModel : CollectionViewModel
){
    NavHost(
        navController = navController,
        startDestination = com.ferit.robertoolujic.meshgrab.Routes.SCREEN_HOME
    ){
        composable(com.ferit.robertoolujic.meshgrab.Routes.SCREEN_HOME){
            HomeScreen(
                navigation = navController,
                modelViewModel = modelViewModel,
                userViewModel = userViewModel,
                collectionViewModel = collectionViewModel,
                "aqecIWECEbTSH1odriz9"
            )
        }
        composable(com.ferit.robertoolujic.meshgrab.Routes.SCREEN_UPLOAD){
            UploadScreen(
                modelViewModel,
                "aqecIWECEbTSH1odriz9",
                navigation = navController
            )
        }
        composable(com.ferit.robertoolujic.meshgrab.Routes.SCREEN_PROFILE,
            arguments = listOf(
                navArgument("userId"){
                    type= NavType.StringType
                }
            )
            ) { backStackEntry ->
            backStackEntry.arguments?.getString("userId")?.let {
                ProfileScreen(
                    navigation = navController,
                    modelViewModel,
                    userViewModel,
                    collectionViewModel,
                    userId = it
                )
            }
        }
        composable(
            com.ferit.robertoolujic.meshgrab.Routes.SCREEN_MODEL,
            arguments = listOf(
                navArgument("modelId") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            backStackEntry.arguments?.getString("modelId")?.let {
                ModelScreen(
                    navigation = navController,
                    modelViewModel,
                    userViewModel,
                    collectionViewModel,
                    modelId = it
                )
            }
        }
        composable(
            com.ferit.robertoolujic.meshgrab.Routes.SCREEN_USER,
            arguments = listOf(
                navArgument("userId") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            backStackEntry.arguments?.getString("userId")?.let {
                UserScreen(
                    navigation = navController,
                    modelViewModel,
                    userViewModel,
                    collectionViewModel,
                    userId = it
                )
            }
        }
        composable(
            com.ferit.robertoolujic.meshgrab.Routes.SCREEN_COLLECTION,
            arguments = listOf(
                navArgument("collectionId") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            backStackEntry.arguments?.getString("collectionId")?.let {
                CollectionScreen(
                    navigation = navController,
                    modelViewModel,
                    userViewModel,
                    collectionViewModel,
                    collectionId = it
                )
            }
        }
    }
}