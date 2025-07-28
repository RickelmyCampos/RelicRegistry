package com.gilbersoncampos.relicregistry.screen.historic

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.gilbersoncampos.relicregistry.R
import com.gilbersoncampos.relicregistry.data.model.HistoricSyncModel
import com.gilbersoncampos.relicregistry.data.model.StatusSync
import com.gilbersoncampos.relicregistry.extensions.toBrDateTime
import java.time.LocalDateTime

@Composable
fun HistoricScreen(viewModel: HistoricViewModel = hiltViewModel()) {
    val uiState = viewModel.uiState.collectAsState().value
    var selected by remember { mutableStateOf<HistoricSyncModel?>(null) }
    selected?.let {
            Dialog(onDismissRequest = { selected = null }){
                Card {
                    Column(modifier = Modifier.padding(8.dp)) {
                        Text(it.data)
                    }
                }
            }
        }
    when (uiState) {
        HistoricUiState.Error -> {
            Text(text = "Erro ao buscar a lista")
        }
        HistoricUiState.Loading -> {
            Column(
                Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator()
            }
        }
       is HistoricUiState.Success ->{
            HistoricUI(uiState.historic){
                selected=it
            }
        }

    }
    //TODO ALGO A FAZER
//    val context = LocalContext.current
//    var builder = NotificationCompat.Builder(context, "MeuCanal")
//        .setSmallIcon(R.drawable.ic_check_outline)
//        .setContentTitle("Notifica")
//        .setContentText("esta sendo notificado")
//        .setPriority(NotificationCompat.PRIORITY_LOW)
//    Button(onClick = {
//        openNotify(context,builder)
//    }) { Text("Abrir notificação") }
}
private fun openNotify(context: Context, builder: NotificationCompat.Builder){
    with(NotificationManagerCompat.from(context)) {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            // ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            // public fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>,
            //                                        grantResults: IntArray)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

            return@with
        }
        // notificationId is a unique int for each notification that you must define.
        val NOTIFICATION_ID=1
        notify(NOTIFICATION_ID, builder.build())
    }
}
@Composable
fun HistoricUI(list: List<HistoricSyncModel>,onSelect:(HistoricSyncModel)->Unit){

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(list) { historic ->
            HistoricItem(historic,onSelect)
        }
    }

}
@Composable
fun HistoricItem(item: HistoricSyncModel,onSelect:(HistoricSyncModel)->Unit){
    Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp, vertical = 2.dp).clickable {
onSelect(item)
    }) {
        Row (modifier = Modifier.fillMaxWidth(),Arrangement.SpaceBetween,Alignment.CenterVertically){
            Column {
                Text("Início: ${item.startIn.toBrDateTime()}")
                item.endIn?.let {
                    Text("Finalizou: ${it.toBrDateTime()}")
                }
            }
            when(item.status){
                StatusSync.SUCCESS -> {
                    Icon(painter = painterResource(R.drawable.ic_check_outline), contentDescription = "success", tint = Color.Green)
                }
                StatusSync.ERROR -> {
                    Icon(painter = painterResource(R.drawable.ic_error), contentDescription = "error",tint = Color.Red)
                }
                else -> {}
            }
        }
        if(item.status == StatusSync.LOADING){
            LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
        }
        HorizontalDivider(thickness = 2.dp)
        //Text("Status: ${item.status.nameString}")
    }
}
@Composable
@Preview
fun HistoricItemPreview(){
    val item = HistoricSyncModel(id = 0, endIn = LocalDateTime.now(), status = StatusSync.SUCCESS, startIn = LocalDateTime.now(), data = "")
    Column {

    HistoricItem(item){}
    HistoricItem(item.copy(status = StatusSync.LOADING, endIn = null)){}
    HistoricItem(item.copy(status = StatusSync.ERROR)){}
    HistoricItem(item){}
    }
}