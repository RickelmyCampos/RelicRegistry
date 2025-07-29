package com.gilbersoncampos.relicregistry

import android.annotation.SuppressLint
import android.app.DownloadManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.FileProvider
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
import com.gilbersoncampos.relicregistry.ui.components.AlertDialogCustom
import com.gilbersoncampos.relicregistry.ui.components.CreateEditRecordModal
import com.gilbersoncampos.relicregistry.ui.theme.RelicRegistryTheme
import com.gilbersoncampos.relicregistry.worker.SyncWorker
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.IOException
import java.net.URL
import java.util.concurrent.TimeUnit


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val TAG = "AppUpdater"
    private var downloadId: Long = -1L
    private lateinit var downloadManager: DownloadManager

    private var _showInstallPermissionDialog = mutableStateOf(false)

    companion object {
        const val UNIQUE_PERIODIC_WORK_NAME = "RelicRegistryPeriodicSync"
    }

    private val onDownloadComplete = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            Log.d(TAG, "RECEBIDO")
            val id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
            if (id == downloadId) {
                Log.d(TAG, "Download concluído com ID: $id")
                installApk(context)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //TODO "Finalizar implementacao"
//        createNotificationChannel()
//        schedulePeriodicWork()
        downloadManager = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        registerReceiver(
            onDownloadComplete,
            IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE),
            RECEIVER_EXPORTED
        )
        setContent {
            RelicRegistryTheme {
                val viewModel: MainViewModel = hiltViewModel()
                val textLoadingUiState by viewModel.textLoadingState.collectAsState()
                var showPopUp by remember { mutableStateOf(false) }
                var hasUpdate by remember { mutableStateOf(false) }
               // var showInstallPermission by remember { mutableStateOf(false) }
                var versionInfos by remember { mutableStateOf<VersionInfo?>(null) }
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
                        CreateEditRecordModal(onCreate = { numbering, place, shelf, group ->
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
                    if (hasUpdate) {
                        AlertDialogCustom(
                            title = "Nova Atualização",
                            text = "A seguinte atualização se encontra disponível\n- Versão: ${versionInfos?.latestVersionName}(${versionInfos?.latestVersionCode})\n- Descrição: ${versionInfos?.releaseNotes}\n\n Deseja Atualizar?",
                            onDismiss = { hasUpdate = false })
                        {
                            hasUpdate = false
                            versionInfos?.let {
                                downloadApk(it.apkUrl)
                            }
                           // viewModel.setTextLoading("Baixando Atualização...")
                        }
                    }
                    textLoadingUiState?.let {
                        AlertDialog(
                            title = { Text(text = it) },
                            text = { LinearProgressIndicator() },
                            onDismissRequest = {  },
                            confirmButton = {}
                            )
                    }
                    if (_showInstallPermissionDialog.value) {
                        AlertDialogCustom(
                            title = "Permissão Necessária",
                            text =
                            "Para instalar aplicativos de fontes desconhecidas, por favor, habilite a permissão para este aplicativo nas configurações.",
                            onConfirm = {
                                _showInstallPermissionDialog.value = false
                                val intent = Intent(ACTION_MANAGE_UNKNOWN_APP_SOURCES)
                                intent.data = Uri.parse("package:$packageName")
                                startActivity(intent) },
                            onDismiss = {  _showInstallPermissionDialog.value = false }

                        )
                    }
                    App(
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.background)
                            .padding(innerPadding),
                        navHostController = navController,
                        onVerifyUpdate = {
                            viewModel.checkForUpdate(getCurrentVersionCode()) { show, versionInfo ->
                                versionInfos = versionInfo
                                hasUpdate = show
                            }
                        }
                    )
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(onDownloadComplete)
    }

    private fun installApk(context: Context) {
        val query = DownloadManager.Query().setFilterById(downloadId)
        val cursor = downloadManager.query(query)

        if (cursor != null && cursor.moveToFirst()) {
            val columnIndex = cursor.getColumnIndex(DownloadManager.COLUMN_STATUS)
            if (columnIndex != -1) {
                val status = cursor.getInt(columnIndex)
                if (status == DownloadManager.STATUS_SUCCESSFUL) {
                    val uriIndex = cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI)
                    if (uriIndex != -1) {
                        val downloadedUriString = cursor.getString(uriIndex)
                        val downloadedUri = Uri.parse(downloadedUriString)
                        Log.d(TAG, "URI do APK baixado: $downloadedUri")

                        val file = File(downloadedUri.path ?: "")
                        if (!file.exists()) {
                            Log.e(
                                TAG,
                                "Arquivo APK não encontrado no caminho: ${file.absolutePath}"
                            )
                            // _statusText.value = "Erro: Arquivo APK não encontrado."
                            return
                        }

                        if (!packageManager.canRequestPackageInstalls()) {
                            _showInstallPermissionDialog.value = true
                            return
                        }

                        val installIntent = Intent(Intent.ACTION_VIEW)
                        val fileUri = downloadManager.getUriForDownloadedFile(downloadId)
                        installIntent.setDataAndType(
                            fileUri,
                            "application/vnd.android.package-archive"
                        )
                        installIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                        installIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        try {
                            context.startActivity(installIntent)
                            // _statusText.value = "Instalação iniciada. Por favor, siga as instruções."
                        } catch (e: Exception) {
                            Log.e(TAG, "Erro ao iniciar a instalação: ${e.message}", e)
                            //_statusText.value = "Erro ao iniciar a instalação: ${e.message}"
                        }
                    } else {
                        Log.e(TAG, "Coluna COLUMN_LOCAL_URI não encontrada.")
                        //_statusText.value = "Erro: URI do download não encontrada."
                    }
                } else {
                    // _statusText.value = "Download falhou com status: $status"
                    Log.e(TAG, "Download falhou com status: $status")
                }
            } else {
                Log.e(TAG, "Coluna COLUMN_STATUS não encontrada.")
                //_statusText.value = "Erro: Status do download não encontrado."
            }
            cursor.close()
        } else {
            //_statusText.value = "Download não encontrado ou falhou."
            Log.e(TAG, "Cursor nulo ou não moveu para o primeiro.")
        }
    }

    private fun downloadApk(apkUrl: String) {
        Log.d(TAG, "Baixando atualização...")

        // Cria o diretório de downloads se não existir
        val downloadsDir = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)
        downloadsDir?.mkdirs() // Garante que o diretório exista

        val destinationFile = File(downloadsDir, "app_update.apk")
        if (destinationFile.exists()) {
            destinationFile.delete() // Deleta o arquivo antigo se existir
        }

        val request = DownloadManager.Request(Uri.parse(apkUrl))
            .setTitle("Atualização do Aplicativo")
            .setDescription("Baixando a nova versão do aplicativo...")
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "app_update.apk")
            ///.setDestinationUri(Uri.fromFile(destinationFile)) // Define o destino do download
            .setAllowedOverMetered(true) // Permite download em redes móveis
            .setAllowedOverRoaming(true) // Permite download em roaming

        downloadId = downloadManager.enqueue(request)
        Log.d(TAG, "Iniciando download com ID: $downloadId")
    }

    private fun getCurrentVersionCode(): Int {
        return try {
            val packageInfo = packageManager.getPackageInfo(packageName, 0)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                packageInfo.longVersionCode.toInt()
            } else {
                @Suppress("DEPRECATION")
                packageInfo.versionCode
            }
        } catch (e: PackageManager.NameNotFoundException) {
            Log.e(TAG, "Erro ao obter o código da versão: ${e.message}")
            -1
        }
    }

    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is not in the Support Library.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "meu canal"
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
fun App(modifier: Modifier = Modifier, navHostController: NavHostController,onVerifyUpdate:()->Unit = {}) {
    Column(modifier = modifier.background(MaterialTheme.colorScheme.background)) {

        NavGraphHost(navHostController,onVerifyUpdate)
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
        CreateEditRecordModal({ _, _, _, _ -> }, null, false, {})
    }
}