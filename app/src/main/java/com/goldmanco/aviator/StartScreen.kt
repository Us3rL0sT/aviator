package com.goldmanco.aviator

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.goldmanco.aviator.sharedpreferences.BestPlayerScorePreferenceManager
import com.goldmanco.aviator.sharedpreferences.PlayerHealthPreferenceManager
import com.goldmanco.aviator.sharedpreferences.PlayerScorePreferenceManager

@Composable
fun StartScreen(navController: NavHostController) {


    val context = LocalContext.current
    val playerScorePreferenceManager = remember { PlayerScorePreferenceManager(context) }
    val bestPlayerScorePreferenceManager = remember { BestPlayerScorePreferenceManager(context) }
    val playerHealthPreferenceManager = remember { PlayerHealthPreferenceManager(context) }

    playerScore = playerScorePreferenceManager.getScore()
    bestPlayerScore = bestPlayerScorePreferenceManager.getBestScore()
    playerHealth = playerHealthPreferenceManager.getPlayerHealth()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Gray) // Устанавливаем цвет фона
    ) {
        Image(
            painter = painterResource(id = R.drawable.background01), // Указываем ресурс картинки
            contentDescription = null, // Описание контента, можно оставить пустым, если не требуется
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Transparent), // Чтобы картинка не перекрывала цвет фона
            contentScale = ContentScale.Crop // Масштабируем картинку
        )


        Column(
            modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            TextButton(
                onClick = {
                    navController.navigate("game")

                    playerScore = 0
                    bestPlayerScore = 0
                    playerScorePreferenceManager.saveScore(playerScore)
                    bestPlayerScorePreferenceManager.saveBestScore(bestPlayerScore)

                },

                shape = RoundedCornerShape(4.dp),
                modifier = Modifier.padding(bottom = 20.dp)


            ) {

                Image(
                    painter = painterResource(id = R.drawable.go), // Ресурс картинки
                    contentDescription = null, // Описание контента
                    contentScale = ContentScale.Crop, // Масштабирование картинки
                    modifier = Modifier

                )

            }

            val activity = (LocalContext.current as? Activity)

            TextButton(
                onClick = {
                    activity?.finish()
                },

                shape = RoundedCornerShape(4.dp),



            ) {

                Image(
                    painter = painterResource(id = R.drawable.exit), // Ресурс картинки
                    contentDescription = null, // Описание контента
                    contentScale = ContentScale.Crop, // Масштабирование картинки
                    modifier = Modifier

                )

            }
        }
    }
}
