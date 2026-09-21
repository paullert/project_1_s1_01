package com.example.cst438_team1_project1

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun HomeScreen(navController: NavController) { //will eventually also take a parameter for navController when complete
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 80.dp)
    ) {
        Row() {
            Text(text = "WIP HOME PAGE", fontSize = 30.sp, fontWeight = FontWeight.Bold)
        }

        Row() {
            Text(
                text = "This will be the explore page where different coins are shown",
                fontSize = 20.sp
            )
        }
    }

    //NAVIGATION BAR
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState())
            .padding(bottom = 40.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.Bottom
    ) {
        Column() {
            Button(onClick = {
                navController.navigate("ViewCoins")
            }) {
                Text("My Coins")
            }
        }
        Column() {
            Button(onClick = {
                navController.navigate("AddCoins")
            }) {
                Text("Add Coins")
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