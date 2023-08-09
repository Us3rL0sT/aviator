package com.goldmanco.aviator


import androidx.compose.runtime.Composable
import androidx.navigation.NavGraph
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun NavGraph() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "game") {
        composable("start_screen") { StartScreen(navController) }
        composable("game") { Game(navController) }
        // Добавьте больше композируемых пунктов назначения по мере необходимости
    }
}

