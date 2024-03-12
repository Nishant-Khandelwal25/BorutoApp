package com.example.borutoapp.navigation

import com.example.borutoapp.util.Constants.DETAILS_ARGUMENT_KEY

sealed class Screen(val route: String) {
    data object Splash : Screen("splash_screen")
    data object Welcome : Screen("welcome_screen")
    data object Home : Screen("home_screen")
    data object Details : Screen("details_screen/{$DETAILS_ARGUMENT_KEY}") {
        fun passHeroId(heroId: Int): String {
            return "details_screen/$heroId"
        }
    }

    data object Search : Screen("search_screen")
}