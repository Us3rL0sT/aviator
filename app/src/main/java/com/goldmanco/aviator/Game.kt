package com.goldmanco.aviator

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import kotlinx.coroutines.delay
import kotlin.math.roundToInt

@Composable
fun Game(navController: NavHostController) {


    var offsetX by remember { mutableStateOf(0f) }
    var offsetY by remember { mutableStateOf(0f) }
    var scale by remember { mutableStateOf(1f) }


    val planeWidth = with(LocalDensity.current) { 80.dp.toPx() }
    val planeHeight = with(LocalDensity.current) { 80.dp.toPx() }

    val bulletOffsetX = with(LocalDensity.current) { (planeWidth.toDp() - 32.dp).toPx() / 2 }
    val bulletOffsetY = with(LocalDensity.current) { (planeHeight.toDp() - 32.dp).toPx() / 2 }


    var bullets by remember {
        mutableStateOf(
            listOf(
                Bullet(
                    x = bulletOffsetX,
                    y = bulletOffsetY,
                    speed = 5f
                )
            )
        )
    }


    LaunchedEffect(Unit) {
        while (true) {
            // Создать новую коллекцию пуль, обновив их координаты на основе скорости
            val updatedBullets = bullets.map { bullet ->
                bullet.copy(y = bullet.y - bullet.speed)
            }
            bullets = updatedBullets

            delay(16) // Обновление координат каждые 16 миллисекунд (приближенно к 60 FPS)
        }
    }







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



                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(start = 31.dp, top = 7.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(end = 20.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                        ) {
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

        Box(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectTransformGestures { _, pan, zoom, _ ->
                        offsetX += pan.x
                        offsetY += pan.y
                        scale *= zoom
                    }
                }
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 550.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.plane),
                    contentDescription = null,
                    modifier = Modifier
                        .size(80.dp)
                        .align(Alignment.Center)
                        .graphicsLayer(
                            translationX = offsetX,
                            translationY = offsetY,
                            scaleX = scale,
                            scaleY = scale
                        )
                )

                bullets.forEach { bullet ->
                    Image(
                        painter = painterResource(id = R.drawable.bullet),
                        contentDescription = null,
                        modifier = Modifier
                            .size(32.dp)
                            .offset(bullet.x.dp, bullet.y.dp)
                            .graphicsLayer(
                                translationX = bullet.x,
                                translationY = bullet.y
                            )
                    )
                }
            }
        }


    }


}


