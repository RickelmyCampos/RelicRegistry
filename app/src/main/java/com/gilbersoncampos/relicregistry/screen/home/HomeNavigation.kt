package com.gilbersoncampos.relicregistry.screen.home

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.gilbersoncampos.relicregistry.navigation.Destination

val ROUTE = Destination.Home.route
fun NavGraphBuilder.homeScreen() {
    composable(ROUTE) {
        HomeScreen()
    }
}

fun NavHostController.navigateToHome(navOptions: NavOptions?=null) {
    navigate(route = ROUTE, navOptions = navOptions)

}