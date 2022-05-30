package com.mahdi.moneymanagemant.feature_management.presentation.util

sealed class Screen(val route: String) {
     object AddMoneyActionDecreaseScreen : Screen("add_money_action_decrease_screen")
     object AddMoneyActionIncreaseScreen : Screen("add_money_action_increase_screen")
     object SplashScreen:Screen("splash_screen")

}

