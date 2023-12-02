package com.dicoding.jetpacksubmission

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.dicoding.jetpacksubmission.ui.component.BottomBar
import com.dicoding.jetpacksubmission.ui.navigation.Screen
import com.dicoding.jetpacksubmission.ui.screen.about.AboutScreen
import com.dicoding.jetpacksubmission.ui.screen.detail.DetailScreen
import com.dicoding.jetpacksubmission.ui.screen.favorite.FavoriteScreen
import com.dicoding.jetpacksubmission.ui.screen.home.HomeScreen

@Composable
fun TravelApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),){
    Scaffold(
        bottomBar = {
            BottomBar(navController)
        },
        modifier = modifier
    ){ innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) {
                HomeScreen(
                    navigateToDetail = { id ->
                        navController.navigate(Screen.Detail.createRoute(id))
                    }
                )
            }
            composable(Screen.Favorite.route) {
                FavoriteScreen(
                    navigateToDetail = { id ->
                        navController.navigate(Screen.Detail.createRoute(id))
                    })
            }
            composable(Screen.Profile.route) {
                AboutScreen()
            }
            composable(
                route = Screen.Detail.route,
                arguments = listOf(navArgument("id") { type = NavType.StringType }),
            ) {
                val id = it.arguments?.getString("id") ?: "1"
                DetailScreen(
                    id = id,
                    navigateBack = {
                        navController.navigateUp()
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TravelAppPreview(){
    TravelApp()
}