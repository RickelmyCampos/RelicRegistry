package com.gilbersoncampos.relicregistry.screen.form

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.gilbersoncampos.relicregistry.navigation.Destination
import com.gilbersoncampos.relicregistry.screen.editRecord.EditRecord

val ROUTE = Destination.Form.route
fun NavGraphBuilder.formScreen() {
    composable(route = ROUTE) {
            FormScreen()
    }
}

fun NavHostController.navigateToForm( navOptions: NavOptions? = null) {

    navigate(route = ROUTE, navOptions = navOptions)

}