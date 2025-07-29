package com.gilbersoncampos.relicregistry.screen.config

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.gilbersoncampos.relicregistry.navigation.Destination

val ROUTE = Destination.Settings.route
fun NavGraphBuilder.settingsScreen(navHostController: NavHostController,onVerifyUpdate:()->Unit) {
    composable(route = ROUTE) {
        SettingsScreen(navHostController=navHostController, onVerifyUpdate =onVerifyUpdate )
    }
}

fun NavHostController.navigateToSettings( navOptions: NavOptions? = null) {
    navigate(route = ROUTE, navOptions = navOptions)

}