package com.gilbersoncampos.relicregistry.screen.historic

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.gilbersoncampos.relicregistry.navigation.Destination

val ROUTE = Destination.HitoricSync.route
fun NavGraphBuilder.historicScreen() {
    composable(route = ROUTE) {
        HistoricScreen()
    }
}

fun NavHostController.navigateToHistoric( navOptions: NavOptions? = null) {
    navigate(route = ROUTE, navOptions = navOptions)

}