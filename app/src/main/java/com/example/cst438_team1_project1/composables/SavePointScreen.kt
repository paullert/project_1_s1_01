package com.example.cst438_team1_project1.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.cst438_team1_project1.viewModels.SavePointViewModel

@Composable
fun SavePointScreen(navController: NavController, viewModel: SavePointViewModel) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 80.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row() {
            Text("Track your coins here!")
        }
        //todo: make this navigate to add savePoint screen!
        Button(onClick = {
            navController.navigate("addSavePoint")
        }
        ) {
            Text("Add SavePoint")
        }
    }


    //NAVIGATION BAR
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 40.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.Bottom
    ) {
        Column() {
            Button(onClick = { navController.navigate("Home") }) {
                Text("Home")
            }
        }


    }

}