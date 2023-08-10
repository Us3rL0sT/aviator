package com.goldmanco.aviator.game_end

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.goldmanco.aviator.R
import com.goldmanco.aviator.bestPlayerScore
import com.goldmanco.aviator.playerHealth
import com.goldmanco.aviator.playerScore
import com.goldmanco.aviator.sharedpreferences.BestPlayerScorePreferenceManager
import com.goldmanco.aviator.sharedpreferences.PlayerHealthPreferenceManager
import com.goldmanco.aviator.sharedpreferences.PlayerScorePreferenceManager


@Composable
fun PlaneCrashed(navController: NavHostController) {

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
    )
    {
        Image(
            painter = painterResource(id = R.drawable.background05),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Transparent),
            contentScale = ContentScale.Crop
        )


        Box(
            modifier = Modifier
                .height(75.dp)
                .fillMaxWidth()
                .padding(end = 0.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize(),


                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                TextButton(
                    onClick = {
                        navController.navigate("start_screen")
                    },
                    shape = RoundedCornerShape(4.dp),

                    ) {
                    Image(
                        painter = painterResource(id = R.drawable.menu),
                        contentDescription = null,
                        modifier = Modifier
                            .size(61.dp, 45.dp)
                            .background(Color.Transparent),
                        contentScale = ContentScale.Crop
                    )
                }


            }
        }


        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 100.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center


        ) {
            Box(
                modifier = Modifier
                    .size(350.dp, 424.dp)


            ) {

                Image(
                    painter = painterResource(id = R.drawable.text_box01),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Transparent),
                    contentScale = ContentScale.Crop
                )

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) {

                    Image(
                        painter = painterResource(id = R.drawable.text_plane_crashed),
                        contentDescription = null,
                        modifier = Modifier
                            .padding(top = 65.dp)
                            .size(250.dp, 17.dp)
                            .background(Color.Transparent),
                        contentScale = ContentScale.Crop
                    )


                    Image(
                        painter = painterResource(id = R.drawable.text_score02),
                        contentDescription = null,
                        modifier = Modifier
                            .padding(top = 65.dp)
                            .size(93.dp, 17.dp)
                            .background(Color.Transparent),
                        contentScale = ContentScale.Crop
                    )


                    Box(
                        modifier = Modifier.height(70.dp)
                    ) {

                        Image(
                            painter = painterResource(id = R.drawable.score),
                            contentDescription = null,
                            modifier = Modifier
                                .align(Alignment.Center)
                                .padding()
                                .size(120.dp, 36.dp)
                                .background(Color.Transparent),
                            contentScale = ContentScale.Crop
                        )

                        Text(
                            text = "$playerScore",
                            color = Color.White,
                            fontSize = 25.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .fillMaxSize()
                                .wrapContentSize(Alignment.Center) // Размещаем текст по центру
                        )
                    }


                    Image(
                        painter = painterResource(id = R.drawable.text_play_again),
                        contentDescription = null,
                        modifier = Modifier
                            .padding(top = 20.dp)
                            .size(125.dp, 25.dp)
                            .background(Color.Transparent),
                        contentScale = ContentScale.Crop
                    )


                    Row(
                        modifier = Modifier
                            .padding(top = 80.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly


                    ) {


                        TextButton(
                            onClick = {
                                navController.navigate("game")
                                playerHealth = 10
                                playerScore = 0
                                playerHealthPreferenceManager.savePlayerHealth(playerHealth)
                                playerScorePreferenceManager.saveScore(playerScore)
                            },
                            shape = RoundedCornerShape(4.dp),

                            ) {
                            Image(
                                painter = painterResource(id = R.drawable.ok),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(54.dp, 40.dp)
                                    .background(Color.Transparent),
                                contentScale = ContentScale.Crop
                            )
                        }

                        TextButton(
                            onClick = {
                                navController.navigate("start_screen")
                                playerHealth = 10
                                playerScore = 0
                                playerHealthPreferenceManager.savePlayerHealth(playerHealth)
                                playerScorePreferenceManager.saveScore(playerScore)
                            },
                            shape = RoundedCornerShape(4.dp),

                            ) {
                            Image(
                                painter = painterResource(id = R.drawable.close),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(54.dp, 40.dp)

                                    .background(Color.Transparent),
                                contentScale = ContentScale.Crop
                            )
                        }


                    }

                }

            }










            Column(
                modifier = Modifier.padding(top = 50.dp),

                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {

                Image(
                    painter = painterResource(id = R.drawable.text_best),
                    contentDescription = null,
                    modifier = Modifier
                        .size(82.dp, 15.dp)
                        .padding()
                        .background(Color.Transparent),
                    contentScale = ContentScale.Crop
                )



                Box(
                    modifier = Modifier
                        .padding(top = 5.dp)
                        .height(40.dp)


                ) {

                    Image(
                        painter = painterResource(id = R.drawable.score),
                        contentDescription = null,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding()
                            .size(120.dp, 36.dp)
                            .background(Color.Transparent),
                        contentScale = ContentScale.Crop
                    )


                    Text(
                        text = "$bestPlayerScore",
                        color = Color.White,
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .fillMaxSize()
                            .wrapContentSize(Alignment.Center) // Размещаем текст по центру
                    )

                }


            }


        }


    }
}