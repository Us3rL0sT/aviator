package com.goldmanco.aviator

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
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
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.goldmanco.aviator.data_classes.BigHealthPack
import com.goldmanco.aviator.data_classes.Bullet
import com.goldmanco.aviator.data_classes.EnemyBullet
import com.goldmanco.aviator.data_classes.EnemyPlane
import com.goldmanco.aviator.data_classes.HealthPack
import com.goldmanco.aviator.sharedpreferences.BestPlayerScorePreferenceManager
import com.goldmanco.aviator.sharedpreferences.PlayerHealthPreferenceManager
import com.goldmanco.aviator.sharedpreferences.PlayerScorePreferenceManager
import kotlinx.coroutines.delay
import kotlin.math.roundToInt


data class IntRect(val left: Int, val top: Int, val right: Int, val bottom: Int) {
    fun intersects(other: IntRect): Boolean {
        return left < other.right && right > other.left && top < other.bottom && bottom > other.top
    }
}

fun isBulletCollidingWithPlayer(
    bullet: EnemyBullet,
    playerX: Float,
    playerY: Float,
    playerWidth: Float,
    playerHeight: Float,
    bulletSize: Float
): Boolean {
    val bulletX = bullet.x
    val bulletY = bullet.y

    // Проверяем пересечение по осям X и Y
    val isXCollision = (bulletX + bulletSize > playerX && bulletX < playerX + playerWidth)
    val isYCollision = (bulletY + bulletSize > playerY && bulletY < playerY + playerHeight)


    return isXCollision && isYCollision
}




private fun isBulletCollidingWithEnemy(
    bullet: Bullet,
    enemy: EnemyPlane,
    bulletSize: Float,
    planeWidthEnemy: Float,
    planeHeightEnemy: Float
): Boolean {
    val bulletX = bullet.x
    val bulletY = bullet.y
    val enemyX = enemy.x
    val enemyY = enemy.y

    val bulletBounds = IntRect(
        left = bulletX.roundToInt(),
        top = bulletY.roundToInt(),
        right = (bulletX + bulletSize).roundToInt(),
        bottom = (bulletY + bulletSize).roundToInt()
    )

    val enemyBounds = IntRect(
        left = enemyX.roundToInt(),
        top = enemyY.roundToInt(),
        right = (enemyX).roundToInt(),
        bottom = (enemyY).roundToInt()


    )


    return bulletBounds.intersects(enemyBounds)
}




fun isPlayerCollidingWithHealthPack(
    healthPack: HealthPack,
    playerX: Float,
    playerY: Float,
    playerWidth: Float,
    playerHeight: Float,
    playerHealth: Int,
): Boolean {
    val healthPackX = healthPack.x
    val healthPackY = healthPack.y
    val healthPackSize = healthPack.size

    val isXCollision = (healthPackX + healthPackSize > playerX && healthPackX < playerX + playerWidth)
    val isYCollision = (healthPackY + healthPackSize > playerY && healthPackY < playerY + playerHeight)

    return isXCollision && isYCollision && playerHealth < 10 // Проверяем, что здоровье игрока не максимальное
}


fun isPlayerCollidingWithBigHealthPack(
    bigHealthPack: BigHealthPack,
    playerX: Float,
    playerY: Float,
    playerWidth: Float,
    playerHeight: Float,
    playerHealth: Int,
): Boolean {
    val healthPackX = bigHealthPack.x
    val healthPackY = bigHealthPack.y
    val healthPackSize = bigHealthPack.size

    val isXCollision = (healthPackX + healthPackSize > playerX && healthPackX < playerX + playerWidth)
    val isYCollision = (healthPackY + healthPackSize > playerY && healthPackY < playerY + playerHeight)

    return isXCollision && isYCollision && playerHealth < 10 // Проверяем, что здоровье игрока не максимальное
}









var playerScore: Int = 0
var bestPlayerScore: Int = 0
var playerHealth: Int = 10
@Composable
fun Game(navController: NavHostController) {

    val context = LocalContext.current
    val playerScorePreferenceManager = remember { PlayerScorePreferenceManager(context) }
    val bestPlayerScorePreferenceManager = remember { BestPlayerScorePreferenceManager(context) }
    val playerHealthPreferenceManager = remember { PlayerHealthPreferenceManager(context) }


    val density = LocalDensity.current.density
    val screenHeight = (500f * density)


    val screenWidth: Dp = LocalConfiguration.current.screenWidthDp.dp
    val screenHeightt: Dp = LocalConfiguration.current.screenHeightDp.dp

    playerScore = playerScorePreferenceManager.getScore()
    bestPlayerScore = bestPlayerScorePreferenceManager.getBestScore()
    playerHealth = playerHealthPreferenceManager.getPlayerHealth()


    var offsetX by remember { mutableStateOf(150f) } // Измените значение offsetX
    var offsetY by remember { mutableStateOf(800f) }
    var scale by remember { mutableStateOf(1f) }


    val planeWidth = with(LocalDensity.current) { 30.dp.toPx() }
    val planeHeight = with(LocalDensity.current) { 30.dp.toPx() }

    val planeWidthEnemy = with(LocalDensity.current) { 20.dp.toPx() }
    val planeHeightEnemy = with(LocalDensity.current) { 20.dp.toPx() }
    val initialEnemyX = offsetX + (planeWidth + 50) / scale


    val bulletSize = with(LocalDensity.current) { 16.dp.toPx() }
    val bulletSizeEnemy = with(LocalDensity.current) { 16.dp.toPx() }

    var bullets by remember { mutableStateOf(emptyList<Bullet>()) }
    var enemyBullets by remember { mutableStateOf(emptyList<EnemyBullet>()) }


    var playerHealth by remember { mutableStateOf(12) } // Начальное здоровье игрока
    var healthPacks by remember { mutableStateOf(emptyList<HealthPack>()) }
    var bigHealthPacks by remember { mutableStateOf(emptyList<BigHealthPack>()) }
    val healthPackHealAmount = 2

    val enemyBulletDamage = 1 // Урон от пули врага
    playerScore = playerScorePreferenceManager.getScore()

    var enemies by remember { mutableStateOf(emptyList<EnemyPlane>()) }


    // ПУЛЯ ИГРОКА
    LaunchedEffect(Unit) {
        while (true) {
            val newBullet = Bullet(
                x = (offsetX / 5 + (planeWidth - bulletSize + 10)),
                y = (offsetY / 4 + (planeHeight - bulletSize + 40)),
                speed = 1f
            )

            if (newBullet.y > 0 && newBullet.y < offsetY + 1000f) {
                bullets = bullets + newBullet
            } else {
            }

            delay(4000)
        }
    }

    // координаты летящей пули
    LaunchedEffect(Unit) {
        while (true) {
            // Обновить координаты пуль в списке на основе скорости
            val updatedBullets = bullets.map { bullet ->
                bullet.copy(y = bullet.y - bullet.speed)
            }
            bullets = updatedBullets

            delay(16) // Обновление координат каждые 16 миллисекунд (приближенно к 60 FPS)
        }
    }

/// Движение врагов и удаление вышедших за экран
    LaunchedEffect(Unit) {
        while (true) {
            val updatedEnemies = mutableListOf<EnemyPlane>()

            for (enemy in enemies) {
                val newY = enemy.y + enemy.speed
                if (newY < screenHeight) {
                    updatedEnemies.add(enemy.copy(y = newY))
                }
            }

            enemies = updatedEnemies

//            println("Enemies, y = ${enemies.size}")
            delay(32)
        }
    }


// Появление новых врагов
    LaunchedEffect(Unit) {
        while (true) {
            val randomNumber = (-100..100).random()
            val newEnemy = EnemyPlane(
                x = (planeWidth + 50) / scale + randomNumber,
                y = 0f,
                speed = 5f
            )

            enemies = enemies + newEnemy

            delay(3000)
        }
    }

// Появление пуль врагов
    LaunchedEffect(Unit) {
        while (true) {
            val newEnemyBullets = enemies.flatMap { enemy ->
                listOf(
                    EnemyBullet(
                        x = enemy.x + (planeWidthEnemy / 2) - (bulletSizeEnemy / 2),
                        y = enemy.y + (planeHeightEnemy / 2) - (bulletSizeEnemy / 2),
                        speed = 4f
                    )
                )
            }
            enemyBullets = enemyBullets + newEnemyBullets

            delay(2000)
        }
    }

// Движение пуль врагов
    LaunchedEffect(Unit) {
        while (true) {
            val updatedEnemyBullets = enemyBullets.map { enemyBullet ->
                enemyBullet.copy(y = enemyBullet.y + enemyBullet.speed)
            }
            enemyBullets = updatedEnemyBullets

            delay(16)
        }
    }





    // ДАМАГ ИГРОКУ
    LaunchedEffect(Unit) {
        while (true) {
            val updatedEnemyBullets = enemyBullets.map { enemyBullet ->
                enemyBullet.copy(y = enemyBullet.y + enemyBullet.speed)
            }
            enemyBullets = updatedEnemyBullets

            // Проверка на столкновение пули врага с игроком и уменьшение здоровья
            val collidedEnemyBullets = enemyBullets.filter { enemyBullet ->
                val isColliding = isBulletCollidingWithPlayer(
                    enemyBullet,
                    offsetX,
                    offsetY,
                    planeWidth,
                    planeHeight,
                    bulletSizeEnemy
                )

                if (isColliding) {

                    playerHealth -= enemyBulletDamage
                    if (playerHealth <= 0) {

                        if (playerScore > bestPlayerScore) {
                            navController.navigate("new_best_score")
                            bestPlayerScore = playerScore
                            playerHealth = 10
                            bestPlayerScorePreferenceManager.saveBestScore(bestPlayerScore)
                            playerScorePreferenceManager.saveScore(playerScore)
                            playerHealthPreferenceManager.savePlayerHealth(playerHealth)

                        } else {
                            navController.navigate("plane_crashed")
                            playerHealth = 10
                            playerHealthPreferenceManager.savePlayerHealth(playerHealth)
                            playerScorePreferenceManager.saveScore(playerScore)
                        }
                    }
                }

                // Возвращаем true, если пуля не столкнулась с игроком
                !isColliding
            }

            // Удаляем столкнувшиеся пули из списка
            enemyBullets = collidedEnemyBullets

            delay(50)
        }
    }


    // АПТЕЧКА
    LaunchedEffect(Unit) {
        while (true) {
            val randomNumber = (-100..100).random()
            val newHealthPack = HealthPack(
                x = (offsetX + planeWidth) / scale + randomNumber,
                y = 0f,
                size = 40f, // Размер аптечки
                speed = 3f // Скорость падения аптечки
            )

            healthPacks = healthPacks + newHealthPack

            delay(15000) // Появление аптечки раз в 15 секунд
        }
    }

// Обновление координат аптечек вниз по экрану
    LaunchedEffect(Unit) {
        while (true) {
            val updatedHealthPacks = healthPacks.map { healthPack ->
                healthPack.copy(y = healthPack.y + healthPack.speed)
            }
            healthPacks = updatedHealthPacks

            delay(16) // Интервал обновления координат аптечек
        }
    }

// В цикле проверки столкновений с аптечками
    LaunchedEffect(Unit) {
        while (true) {
            val collidedHealthPacks = healthPacks.filter { healthPack ->
                val isColliding = isPlayerCollidingWithHealthPack(
                    healthPack,
                    offsetX,
                    offsetY,
                    planeWidth,
                    planeHeight,
                    playerHealth,
                )

                if (isColliding) {
                    playerHealth += healthPackHealAmount // Увеличиваем здоровье игрока на значение аптечки
                }

                // Возвращаем true, если аптечка не столкнулась с игроком
                !isColliding
            }

            // Удаляем собранные аптечки из списка
            healthPacks = collidedHealthPacks

            delay(100) // Интервал проверки столкновений с аптечками
        }
    }




    // БОЛЬШАЯ АПТЕЧКА
    LaunchedEffect(Unit) {
        while (true) {
            val randomNumber = (-100..100).random()
            val newHealthPack = BigHealthPack(
                x = (offsetX + planeWidth) / scale + randomNumber,
                y = 0f,
                size = 60f, // Размер аптечки
                speed = 3f // Скорость падения аптечки
            )

            bigHealthPacks = bigHealthPacks + newHealthPack

            delay(30000) // Появление аптечки раз в 30 секунд
        }
    }

// Обновление координат аптечек вниз по экрану
    LaunchedEffect(Unit) {
        while (true) {
            val updatedHealthPacks = bigHealthPacks.map { bigHealthPack ->
                bigHealthPack.copy(y = bigHealthPack.y + bigHealthPack.speed)
            }
            bigHealthPacks = updatedHealthPacks

            delay(16) // Интервал обновления координат аптечек
        }
    }

// В цикле проверки столкновений с аптечками
    LaunchedEffect(Unit) {
        while (true) {
            val collidedHealthPacks = bigHealthPacks.filter { bigHealthPack ->
                val isColliding = isPlayerCollidingWithBigHealthPack(
                    bigHealthPack,
                    offsetX,
                    offsetY,
                    planeWidth,
                    planeHeight,
                    playerHealth,
                )

                if (isColliding) {
                    playerHealth = 12 // Увеличиваем здоровье игрока на значение аптечки
                }

                // Возвращаем true, если аптечка не столкнулась с игроком
                !isColliding
            }

            // Удаляем собранные аптечки из списка
            bigHealthPacks = collidedHealthPacks

            delay(100) // Интервал проверки столкновений с аптечками
        }
    }












    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Gray) // Устанавливаем цвет фона
    ) {
        Image(
            painter = painterResource(id = R.drawable.background04),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Transparent),
            contentScale = ContentScale.Crop
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


    Column() {


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

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxHeight()
                ) {


                    Image(
                        painter = painterResource(id = R.drawable.text_score01),
                        contentDescription = null,
                        modifier = Modifier
                            .size(93.dp, 17.dp)
                            .background(Color.Transparent),
                        contentScale = ContentScale.Crop
                    )

                    Box(
                        modifier = Modifier
                            .size(110.dp, 40.dp)
                            .padding(top = 5.dp)
                            .background(Color.Transparent),

                        ) {

                        Image(
                            painter = painterResource(id = R.drawable.score),
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize(),


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


                }


                Box(
                    modifier = Modifier
                        .size(118.dp, 30.dp)
                ) {

                    Image(
                        painter = painterResource(id = R.drawable.health),
                        contentDescription = null,
                        modifier = Modifier
                            .size(118.dp, 30.dp)
                            .background(Color.Transparent),
                        contentScale = ContentScale.Crop
                    )



                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(start = 31.dp, top = 7.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()

                                .padding(end = 5.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                        ) {
                            for (i in 0..playerHealth) {
                                if (i in 1..4) {
                                    Image(
                                        painter = painterResource(id = R.drawable.health_point01),
                                        contentDescription = null,
                                        modifier = Modifier
                                            .size(4.dp, 16.dp)
                                            .fillMaxSize()
                                            .padding()
                                            .background(Color.Transparent),
                                        contentScale = ContentScale.Crop
                                    )
                                }

                                if (i in 5..8) {
                                    Image(
                                        painter = painterResource(id = R.drawable.health_point02),
                                        contentDescription = null,
                                        modifier = Modifier
                                            .size(4.dp, 16.dp)
                                            .fillMaxSize()

                                            .background(Color.Transparent),
                                        contentScale = ContentScale.Crop
                                    )
                                }

                                if (i in 9..12) {
                                    Image(
                                        painter = painterResource(id = R.drawable.health_point03),
                                        contentDescription = null,
                                        modifier = Modifier
                                            .size(4.dp, 16.dp)
                                            .fillMaxSize()

                                            .background(Color.Transparent),
                                        contentScale = ContentScale.Crop
                                    )
                                }
                            }


                        }


                    }


                }


            }
        }

        Box(
            modifier = Modifier.fillMaxSize()

                .pointerInput(Unit) {
                    detectTransformGestures { _, pan, zoom, _ ->
                        val newOffsetX = offsetX + pan.x
                        val newOffsetY = offsetY + pan.y
                        val newScale = scale * zoom

                        val maxOffsetX = (screenWidth.toPx() / scale) - planeWidth - 100
                        val maxOffsetY = (screenHeightt.toPx() / scale) - planeHeight - 260

                        offsetX = newOffsetX.coerceIn(0f, maxOffsetX)
                        offsetY = newOffsetY.coerceIn(0f, maxOffsetY)
                        scale = newScale
                    }
                }

        ) {
            Box(
                modifier = Modifier

                    .padding()
            ) {
                // Проверка на столкновение пули врага с игроком и рисование обводки при коллизии
                val isPlayerHit = enemyBullets.any { enemyBullet ->
                    isBulletCollidingWithPlayer(
                        enemyBullet,
                        offsetX,
                        offsetY,
                        planeWidth,
                        planeHeight,
                        bulletSizeEnemy
                    )
                }

                // Игрок мертв, добавьте анимацию смерти или другую обработку при смерти игрока
                val isPlayerDead = playerHealth <= 0

                // Проверяем здоровье игрока и рисуем изображение самолета с обводкой при необходимости
                if (!isPlayerDead) {
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .align(Alignment.Center)
                            .graphicsLayer(
                                translationX = offsetX,
                                translationY = offsetY,
                                scaleX = scale,
                                scaleY = scale
                            )
                            .border(
                                width = if (isPlayerHit) 2.dp else 0.dp,
                                color = Color.Red,
                                shape = RectangleShape
                            )
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.plane),
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }
            }
        }
    }






    enemies.forEach { enemy ->
        // Проверяем столкновения пуль с врагом
        val isColliding = bullets.any { bullet ->
            isBulletCollidingWithEnemy(bullet, enemy, bulletSize, planeWidthEnemy, planeHeightEnemy)
        }

        Image(
            painter = painterResource(id = R.drawable.plane_enemy),
            contentDescription = null,
            modifier = Modifier
                .size(80.dp)
                .offset(x = enemy.x.dp, y = enemy.y.dp)
                .background(if (isColliding) Color.Red else Color.Transparent) // Изменение фона в зависимости от столкновения
        )

        // Удаляем пули и врага при столкновении
        if (isColliding) {
            bullets = bullets.filter { bullet ->
                !isBulletCollidingWithEnemy(
                    bullet,
                    enemy,
                    bulletSize,
                    planeWidth,
                    planeHeight
                )
            }
            enemies = enemies.filter { it != enemy }
            playerScore += 10
            playerScorePreferenceManager.saveScore(playerScore)
        }
    }


    enemyBullets.forEach { bullet ->
        Image(
            painter = painterResource(id = R.drawable.bullet_enemy), // Используйте свой ресурс для вражеской пули
            contentDescription = null,
            modifier = Modifier
                .size(32.dp)
                .offset(x = bullet.x.dp, y = bullet.y.dp)
                .graphicsLayer(
                    translationX = bullet.x,
                    translationY = bullet.y
                )
        )
    }


    healthPacks.forEach { healthPack ->
        Image(
            painter = painterResource(id = R.drawable.first_aid_kit_small), // Используйте свой ресурс для аптечки
            contentDescription = null,
            modifier = Modifier
                .size(healthPack.size.dp)
                .offset(x = healthPack.x.dp, y = healthPack.y.dp)
                .graphicsLayer(
                    translationX = healthPack.x,
                    translationY = healthPack.y
                )
        )
    }

    bigHealthPacks.forEach { bigHealthPack ->
        Image(
            painter = painterResource(id = R.drawable.first_aid_kit_big), // Используйте свой ресурс для аптечки
            contentDescription = null,
            modifier = Modifier
                .size(bigHealthPack.size.dp)
                .offset(x = bigHealthPack.x.dp, y = bigHealthPack.y.dp)
                .graphicsLayer(
                    translationX = bigHealthPack.x,
                    translationY = bigHealthPack.y
                )
        )
    }

}

