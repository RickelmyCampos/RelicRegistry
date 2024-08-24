package com.gilbersoncampos.relicregistry

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
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
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.gilbersoncampos.relicregistry.data.model.RecordModel
import com.gilbersoncampos.relicregistry.navigation.NavGraphHost
import com.gilbersoncampos.relicregistry.screen.editRecord.navigateToEditRecord
import com.gilbersoncampos.relicregistry.screen.home.navigateToHome
import com.gilbersoncampos.relicregistry.ui.theme.RelicRegistryTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RelicRegistryTheme {
                val viewModel: MainViewModel = hiltViewModel()
                var showPopUp by remember { mutableStateOf(false) }
                val navController = rememberNavController()
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    floatingActionButton = {
                        FloatingActionButton(onClick = { showPopUp = true }) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "Add new register"
                            )
                        }
                    }
                ) { innerPadding ->
                    if (showPopUp) {
                        Popup(onCreate = { numbering, place, shelf, group ->
                            val initialRecord = RecordModel(
                                0,
                                numbering = numbering,
                                place = place,
                                shelf = shelf,
                                group = group,
                                box = ""
                            )
                            viewModel.createRecord(initialRecord) {
                                id->
                                navController.navigateToEditRecord(id)
                                showPopUp = false
                            }
                        }) {
                            showPopUp = false
                        }
                    }
                    App(
                        modifier = Modifier.padding(innerPadding),
                        navHostController = navController
                    )
                }
            }
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

    NavGraphHost(navHostController)
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