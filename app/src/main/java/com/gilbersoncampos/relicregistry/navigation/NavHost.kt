package com.gilbersoncampos.relicregistry.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.slideIn
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.gilbersoncampos.relicregistry.screen.chart.chartsScreen
import com.gilbersoncampos.relicregistry.screen.config.settingsScreen
import com.gilbersoncampos.relicregistry.screen.editRecord.editRecordScreen
import com.gilbersoncampos.relicregistry.screen.editRecord.navigateToEditRecord
import com.gilbersoncampos.relicregistry.screen.form.formScreen
import com.gilbersoncampos.relicregistry.screen.home.HomeScreen
import com.gilbersoncampos.relicregistry.screen.home.homeScreen
import com.gilbersoncampos.relicregistry.screen.recordList.recordListScreen

@Composable
fun NavGraphHost(navHostController: NavHostController) {

    NavHost(
        enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left) },
        exitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Left) },
        popExitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right) },
        popEnterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Right) },
        startDestination = Destination.ListRecord.route,
        navController = navHostController
    ) {
        //homeScreen()
        settingsScreen()
        editRecordScreen(onBack = navHostController::navigateUp)
        recordListScreen(navigateToEditRecord = { navHostController.navigateToEditRecord(it) })
        formScreen()
        chartsScreen()
    }
}