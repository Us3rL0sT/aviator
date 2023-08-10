package com.goldmanco.aviator.sharedpreferences

import android.content.Context
import android.content.SharedPreferences
import com.goldmanco.aviator.bestPlayerScore
import com.goldmanco.aviator.playerHealth
import com.goldmanco.aviator.playerScore


// Player Score
class PlayerScorePreferenceManager(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("PlayerScore", Context.MODE_PRIVATE)

    fun saveScore(playerScore: Int) {
        sharedPreferences.edit().putInt("playerScore", playerScore).apply()
    }

    fun getScore(): Int {
        return sharedPreferences.getInt("playerScore", playerScore)
    }
}


// Best Player Score
class BestPlayerScorePreferenceManager(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("BestPlayerScore", Context.MODE_PRIVATE)

    fun saveBestScore(bestPlayerScore: Int) {
        sharedPreferences.edit().putInt("bestPlayerScore", bestPlayerScore).apply()
    }

    fun getBestScore(): Int {
        return sharedPreferences.getInt("bestPlayerScore", bestPlayerScore)
    }
}


// Player Health
class PlayerHealthPreferenceManager(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("PlayerHealth", Context.MODE_PRIVATE)

    fun savePlayerHealth(playerHealth: Int) {
        sharedPreferences.edit().putInt("playerHealth", playerHealth).apply()
    }

    fun getPlayerHealth(): Int {
        return sharedPreferences.getInt("playerHealth", playerHealth)
    }
}