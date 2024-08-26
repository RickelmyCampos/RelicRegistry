package com.gilbersoncampos.relicregistry.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.gilbersoncampos.relicregistry.screen.editRecord.editRecordScreen
import com.gilbersoncampos.relicregistry.screen.editRecord.navigateToEditRecord
import com.gilbersoncampos.relicregistry.screen.home.HomeScreen
import com.gilbersoncampos.relicregistry.screen.home.homeScreen
import com.gilbersoncampos.relicregistry.screen.recordList.recordListScreen

@Composable
fun NavGraphHost(navHostController: NavHostController) {
    NavHost(startDestination = Destination.ListRecord.route, navController = navHostController) {
        homeScreen()
        editRecordScreen()
        recordListScreen(navigateToEditRecord = {navHostController.navigateToEditRecord(it)})
    }
}