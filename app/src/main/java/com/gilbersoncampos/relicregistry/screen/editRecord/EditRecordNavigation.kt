package com.gilbersoncampos.relicregistry.screen.editRecord

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.gilbersoncampos.relicregistry.navigation.Destination
import com.gilbersoncampos.relicregistry.screen.home.HomeScreen


val ROUTE = Destination.EditRecord.route
fun NavGraphBuilder.editRecordScreen() {
    composable(route = ROUTE, arguments  = listOf(navArgument("id_record") { type = NavType.StringType })) {
        backStackEntry ->
        val idBackStack=backStackEntry.arguments?.getString("id_record")
        val idRecord: Long = idBackStack?.toLong() ?: 0
        EditRecord(idRecord = idRecord)
    }
}

fun NavHostController.navigateToEditRecord(id: Long, navOptions: NavOptions? = null) {
    val routeWithId = ROUTE.replace("{id_record}", id.toString())
    navigate(route = routeWithId, navOptions = navOptions)

}