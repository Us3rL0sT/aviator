package com.goldmanco.aviator

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun Game(navController: NavHostController) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Gray) // Устанавливаем цвет фона
    ) {
        Image(
            painter = painterResource(id = R.drawable.background04), // Указываем ресурс картинки
            contentDescription = null, // Описание контента, можно оставить пустым, если не требуется
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Transparent), // Чтобы картинка не перекрывала цвет фона
            contentScale = ContentScale.Crop // Масштабируем картинку
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
                        /*TODO*/
                    },
                    shape = RoundedCornerShape(4.dp),

                    ) {
                    Image(
                        painter = painterResource(id = R.drawable.menu), // Указываем ресурс картинки
                        contentDescription = null, // Описание контента, можно оставить пустым, если не требуется
                        modifier = Modifier
                            .size(61.dp, 45.dp)
                            .background(Color.Transparent), // Чтобы картинка не перекрывала цвет фона
                        contentScale = ContentScale.Crop // Масштабируем картинку
                    )
                }

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxHeight()
                ) {


                    Image(
                        painter = painterResource(id = R.drawable.text_score01), // Указываем ресурс картинки
                        contentDescription = null, // Описание контента, можно оставить пустым, если не требуется
                        modifier = Modifier
                            .size(93.dp, 17.dp)
                            .background(Color.Transparent), // Чтобы картинка не перекрывала цвет фона
                        contentScale = ContentScale.Crop // Масштабируем картинку
                    )

                    Image(
                        painter = painterResource(id = R.drawable.score), // Указываем ресурс картинки
                        contentDescription = null, // Описание контента, можно оставить пустым, если не требуется
                        modifier = Modifier
                            .size(133.dp, 40.dp)
                            .padding(top = 5.dp)
                            .background(Color.Transparent), // Чтобы картинка не перекрывала цвет фона
                        contentScale = ContentScale.Crop // Масштабируем картинку
                    )


                }


                Box(
                    modifier = Modifier
                        .size(118.dp, 30.dp)
                ) {

                    Image(
                        painter = painterResource(id = R.drawable.health), // Указываем ресурс картинки
                        contentDescription = null, // Описание контента, можно оставить пустым, если не требуется
                        modifier = Modifier
                            .size(118.dp, 30.dp)
                            .background(Color.Transparent), // Чтобы картинка не перекрывала цвет фона
                        contentScale = ContentScale.Crop // Масштабируем картинку
                    )



                    Box(modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 31.dp, top = 7.dp)) {
                        Row(modifier = Modifier.fillMaxWidth().padding(end = 20.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,) {
                            Image(
                                painter = painterResource(id = R.drawable.health_point01), // Указываем ресурс картинки
                                contentDescription = null, // Описание контента, можно оставить пустым, если не требуется
                                modifier = Modifier
                                    .size(4.dp, 16.dp)
                                    .fillMaxSize()

                                    .background(Color.Transparent), // Чтобы картинка не перекрывала цвет фона
                                contentScale = ContentScale.Crop // Масштабируем картинку
                            )

                            Image(
                                painter = painterResource(id = R.drawable.health_point01), // Указываем ресурс картинки
                                contentDescription = null, // Описание контента, можно оставить пустым, если не требуется
                                modifier = Modifier
                                    .size(4.dp, 16.dp)
                                    .fillMaxSize()

                                    .background(Color.Transparent), // Чтобы картинка не перекрывала цвет фона
                                contentScale = ContentScale.Crop // Масштабируем картинку
                            )

                            Image(
                                painter = painterResource(id = R.drawable.health_point01), // Указываем ресурс картинки
                                contentDescription = null, // Описание контента, можно оставить пустым, если не требуется
                                modifier = Modifier
                                    .size(4.dp, 16.dp)
                                    .fillMaxSize()

                                    .background(Color.Transparent), // Чтобы картинка не перекрывала цвет фона
                                contentScale = ContentScale.Crop // Масштабируем картинку
                            )

                            Image(
                                painter = painterResource(id = R.drawable.health_point01), // Указываем ресурс картинки
                                contentDescription = null, // Описание контента, можно оставить пустым, если не требуется
                                modifier = Modifier
                                    .size(4.dp, 16.dp)
                                    .fillMaxSize()

                                    .background(Color.Transparent), // Чтобы картинка не перекрывала цвет фона
                                contentScale = ContentScale.Crop // Масштабируем картинку
                            )

                            Image(
                                painter = painterResource(id = R.drawable.health_point02), // Указываем ресурс картинки
                                contentDescription = null, // Описание контента, можно оставить пустым, если не требуется
                                modifier = Modifier
                                    .size(4.dp, 16.dp)
                                    .fillMaxSize()

                                    .background(Color.Transparent), // Чтобы картинка не перекрывала цвет фона
                                contentScale = ContentScale.Crop // Масштабируем картинку
                            )

                            Image(
                                painter = painterResource(id = R.drawable.health_point02), // Указываем ресурс картинки
                                contentDescription = null, // Описание контента, можно оставить пустым, если не требуется
                                modifier = Modifier
                                    .size(4.dp, 16.dp)
                                    .fillMaxSize()

                                    .background(Color.Transparent), // Чтобы картинка не перекрывала цвет фона
                                contentScale = ContentScale.Crop // Масштабируем картинку
                            )

                            Image(
                                painter = painterResource(id = R.drawable.health_point02), // Указываем ресурс картинки
                                contentDescription = null, // Описание контента, можно оставить пустым, если не требуется
                                modifier = Modifier
                                    .size(4.dp, 16.dp)
                                    .fillMaxSize()

                                    .background(Color.Transparent), // Чтобы картинка не перекрывала цвет фона
                                contentScale = ContentScale.Crop // Масштабируем картинку
                            )

                            Image(
                                painter = painterResource(id = R.drawable.health_point02), // Указываем ресурс картинки
                                contentDescription = null, // Описание контента, можно оставить пустым, если не требуется
                                modifier = Modifier
                                    .size(4.dp, 16.dp)
                                    .fillMaxSize()

                                    .background(Color.Transparent), // Чтобы картинка не перекрывала цвет фона
                                contentScale = ContentScale.Crop // Масштабируем картинку
                            )

                            Image(
                                painter = painterResource(id = R.drawable.health_point03), // Указываем ресурс картинки
                                contentDescription = null, // Описание контента, можно оставить пустым, если не требуется
                                modifier = Modifier
                                    .size(4.dp, 16.dp)
                                    .fillMaxSize()

                                    .background(Color.Transparent), // Чтобы картинка не перекрывала цвет фона
                                contentScale = ContentScale.Crop // Масштабируем картинку
                            )

                            Image(
                                painter = painterResource(id = R.drawable.health_point03), // Указываем ресурс картинки
                                contentDescription = null, // Описание контента, можно оставить пустым, если не требуется
                                modifier = Modifier
                                    .size(4.dp, 16.dp)
                                    .fillMaxSize()

                                    .background(Color.Transparent), // Чтобы картинка не перекрывала цвет фона
                                contentScale = ContentScale.Crop // Масштабируем картинку
                            )
                        }


                    }




                }


            }
        }


    }
}

