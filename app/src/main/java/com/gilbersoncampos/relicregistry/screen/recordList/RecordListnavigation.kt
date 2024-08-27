package com.gilbersoncampos.relicregistry.screen.recordList

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.gilbersoncampos.relicregistry.navigation.Destination
import com.gilbersoncampos.relicregistry.screen.home.HomeScreen

val ROUTE = Destination.ListRecord.route
fun NavGraphBuilder.recordListScreen(navigateToEditRecord:(Long)->Unit) {
    composable(ROUTE) {
        RecordListScreen(navigateToEditRecord=navigateToEditRecord)
    }
}

fun NavHostController.navigateToRecordList(navOptions: NavOptions?=null) {
    navigate(route = ROUTE, navOptions = navOptions)

}