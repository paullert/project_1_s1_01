package com.example.cst438_team1_project1

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun FavoritesScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 80.dp)
    ) {
        Row() {
            Text("This will be for the user's favorite coins to see")
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
        Column() {
            Button(onClick = {
                navController.navigate("Favorites")
            }) {
                Text("Favorites")
            }
        }
        Column() {
            Button(onClick = {
                navController.navigate("Account")
            }) {
                Text("Account")
            }
        }
    }
}