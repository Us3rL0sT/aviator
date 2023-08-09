package com.goldmanco.aviator

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

class MainActivity : ComponentActivity() {
    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val window = window


        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        window.apply {
            // Сделать панель уведомлений прозрачной

            // Изменить цвет панели уведомлений
        }


        setContent {
            NavGraph()
        }
    }
}

