package com.goldmanco.aviator


import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.goldmanco.aviator.game_end.NewBestScore
import com.goldmanco.aviator.game_end.PlaneCrashed

@Composable
fun NavGraph() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "game") {
        composable("start_screen") { StartScreen(navController) }
        composable("game") { Game(navController) }
        composable("new_best_score") { NewBestScore(navController) }
        composable("plane_crashed") { PlaneCrashed(navController) }
        // Добавьте больше композируемых пунктов назначения по мере необходимости
    }
}

