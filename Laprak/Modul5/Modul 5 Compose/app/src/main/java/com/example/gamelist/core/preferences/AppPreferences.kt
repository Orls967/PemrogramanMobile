package com.example.gamelist.core.preferences

import android.content.Context
import android.content.SharedPreferences

class AppPreferences(context: Context) {

    private val preferences: SharedPreferences = context.getSharedPreferences(
        PREFS_NAME,
        Context.MODE_PRIVATE
    )

    fun saveLastOpenedGame(gameName: String) {
        preferences.edit().putString(KEY_LAST_OPENED_GAME, gameName).apply()
    }

    fun getLastOpenedGame(): String? {
        return preferences.getString(KEY_LAST_OPENED_GAME, null)
    }

    companion object {
        private const val PREFS_NAME = "game_list_prefs"
        private const val KEY_LAST_OPENED_GAME = "last_opened_game"
    }
}