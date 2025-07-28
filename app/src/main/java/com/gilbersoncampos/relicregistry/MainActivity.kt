package com.gilbersoncampos.relicregistry

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.gilbersoncampos.relicregistry.data.model.CatalogRecordModel
import com.gilbersoncampos.relicregistry.navigation.Destination
import com.gilbersoncampos.relicregistry.navigation.NavGraphHost
import com.gilbersoncampos.relicregistry.navigation.getIcon
import com.gilbersoncampos.relicregistry.navigation.listBottomNavigation
import com.gilbersoncampos.relicregistry.screen.chart.navigateToCharts
import com.gilbersoncampos.relicregistry.screen.config.navigateToSettings
import com.gilbersoncampos.relicregistry.screen.editRecord.navigateToEditRecord
import com.gilbersoncampos.relicregistry.screen.form.navigateToForm
import com.gilbersoncampos.relicregistry.screen.recordList.navigateToRecordList
import com.gilbersoncampos.relicregistry.ui.theme.RelicRegistryTheme
import com.gilbersoncampos.relicregistry.worker.SyncWorker
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    companion object {
        const val UNIQUE_PERIODIC_WORK_NAME = "RelicRegistryPeriodicSync"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        createNotificationChannel()
        schedulePeriodicWork()

        setContent {
            RelicRegistryTheme {
                val viewModel: MainViewModel = hiltViewModel()
                var showPopUp by remember { mutableStateOf(false) }
                val navController = rememberNavController()
                val route =
                    navController.currentBackStackEntryAsState().value?.destination?.route ?: ""

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    floatingActionButton = {
                        if (route == Destination.ListRecord.route) {
                            FloatingActionButton(onClick = { showPopUp = true }) {
                                Icon(
                                    imageVector = Icons.Default.Add,
                                    contentDescription = "Add new register"
                                )
                            }
                        }

                    },
                    bottomBar = {
                        NavigationBar {
                            listBottomNavigation.forEach {
                                NavigationBarItem(
                                    selected = route == it.route,
                                    onClick = {
                                        when (it) {
                                            Destination.ListRecord -> {
                                                navController.navigateToRecordList(navOptions = navOptions {
                                                    popUpTo(navController.graph.findStartDestination().id)
                                                    launchSingleTop = true
                                                })
                                            }

                                            Destination.Settings -> {
                                                navController.navigateToSettings(navOptions = navOptions {
                                                    popUpTo(navController.graph.findStartDestination().id)
                                                    launchSingleTop = true
                                                })
                                            }

                                            Destination.Form -> {
                                                navController.navigateToForm(navOptions = navOptions {
                                                    popUpTo(navController.graph.findStartDestination().id)
                                                    launchSingleTop = true
                                                })
                                            }

                                            Destination.Charts -> {
                                                navController.navigateToCharts(navOptions = navOptions {
                                                    popUpTo(navController.graph.findStartDestination().id)
                                                    launchSingleTop = true
                                                })
                                            }

                                            else -> {}
                                        }
                                    },
                                    icon = {
                                        Icon(
                                            imageVector = it.getIcon(),
                                            contentDescription = it.route
                                        )
                                    },
                                    label = { Text(text = it.name) })
                            }
                        }
                    }
                ) { innerPadding ->
                    if (showPopUp) {
                        Popup(onCreate = { numbering, place, shelf, group ->
                            val initialRecord = CatalogRecordModel(
                                0,
                                identification = numbering,
                                archaeologicalSite = place,
                                shelfLocation = shelf,
                                group = group,
                                observations = "",
                                classification = ""
                            )
                            viewModel.createRecord(initialRecord) { id ->
                                navController.navigateToEditRecord(id)
                                showPopUp = false
                            }
                        }) {
                            showPopUp = false
                        }
                    }
                    App(
                        modifier = Modifier.background(MaterialTheme.colorScheme.background).padding(innerPadding),
                        navHostController = navController
                    )
                }
            }
        }
    }
 private fun createNotificationChannel() {
    // Create the NotificationChannel, but only on API 26+ because
    // the NotificationChannel class is not in the Support Library.
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val name ="meu canal"
        val descriptionText = "descricao"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel("MeuCanal", name, importance).apply {
            description = descriptionText

        }
        // Register the channel with the system.
        val notificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}
    private fun schedulePeriodicWork() {
        val repeatIntervalMinutes: Long = 15
        val periodicWorkRequestWorker =
            PeriodicWorkRequestBuilder<SyncWorker>(
                repeatIntervalMinutes, // Intervalo
                TimeUnit.MINUTES       // Unidade de tempo

            )
                .setConstraints(
                    Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
                )
                .build()
        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            UNIQUE_PERIODIC_WORK_NAME,
            ExistingPeriodicWorkPolicy.REPLACE,
            periodicWorkRequestWorker
        )

        WorkManager.getInstance(this).getWorkInfoByIdLiveData(periodicWorkRequestWorker.id)
            .observeForever {

                when (it.state) {
                    WorkInfo.State.ENQUEUED -> {
                        Log.e("MeuWorkerDebug", "Trabalho Enqueued}")
                    }

                    WorkInfo.State.RUNNING -> {
                        Log.e("MeuWorkerDebug", "Trabalho Running}")
                    }

                    WorkInfo.State.SUCCEEDED -> {
                        Log.e("MeuWorkerDebug", "Trabalho Success}")
                    }

                    WorkInfo.State.FAILED -> {
                        Log.e("MeuWorkerDebug", "Trabalho falhou. OutputData: ${it.outputData}")
                    }

                    WorkInfo.State.BLOCKED -> {
                        Log.e("MeuWorkerDebug", "Trabalho Blocked}")


                    }

                    WorkInfo.State.CANCELLED -> {
                        Log.e("MeuWorkerDebug", "Trabalho Canceled}")

                    }
                }

                Log.d(
                    "MainActivity",
                    "Trabalho periódico '${UNIQUE_PERIODIC_WORK_NAME}' agendado para cada $repeatIntervalMinutes minutos."
                )
            }
    }
}
    @Composable
    fun Popup(
        onCreate: (String, String, String, String) -> Unit,
        onClose: () -> Unit
    ) {
        var numbering by remember { mutableStateOf("") }
        var place by remember { mutableStateOf("") }
        var shelf by remember { mutableStateOf("") }
        var group by remember { mutableStateOf("") }

        Dialog(onDismissRequest = onClose) {
            Card {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Criar uma nova ficha",
                        style = MaterialTheme.typography.headlineSmall
                    )
                    OutlinedTextField(
                        value = numbering,
                        onValueChange = { numbering = it },
                        label = { Text("Numeração") }
                    )
                    OutlinedTextField(
                        value = place,
                        onValueChange = { place = it },
                        label = { Text("Sítio") }
                    )
                    OutlinedTextField(
                        value = shelf,
                        onValueChange = { shelf = it },
                        label = { Text("Prateleira") }
                    )
                    OutlinedTextField(
                        value = group,
                        onValueChange = { group = it },
                        label = { Text("Grupo") }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Button(onClick = onClose) {
                            Text("Cancelar")
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Button(onClick = { onCreate(numbering, place, shelf, group) }) {
                            Text("Criar")
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun App(modifier: Modifier = Modifier, navHostController: NavHostController) {
        Column(modifier = modifier.background(MaterialTheme.colorScheme.background)) {

            NavGraphHost(navHostController)
        }
    }


    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview() {
        RelicRegistryTheme {
            //App(modifier = Modifier.fillMaxSize())
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun PopupPreview() {
        RelicRegistryTheme {
            Popup({ _, _, _, _ -> }, {})
        }
    }