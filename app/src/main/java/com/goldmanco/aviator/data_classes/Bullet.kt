package com.goldmanco.aviator.data_classes

data class Bullet(
    var x: Float,
    var y: Float,
    val speed: Float
)

data class EnemyPlane(
    val x: Float,
    val y: Float,
    val speed: Float
)
