package com.gilbersoncampos.relicregistry.screen.chart

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.gilbersoncampos.relicregistry.navigation.Destination

val ROUTE = Destination.Charts.route
fun NavGraphBuilder.chartsScreen() {
    composable(route = ROUTE) {
        ChartsScreen()
    }
}
fun NavHostController.navigateToCharts(navOptions: NavOptions? = null) {
    navigate(route = ROUTE, navOptions = navOptions)

}