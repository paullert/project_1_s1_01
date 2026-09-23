package com.example.cst438_team1_project1.composables

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.cst438_team1_project1.viewModels.SavePointViewModel

@Composable
fun SavePointScreen(navController: NavController, viewModel: SavePointViewModel) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(top = 80.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row() {
                Text(
                    text = "Tracked Coins",
                    fontSize = 35.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(15.dp))

            if (state.isLoading) {
                Text("Loading...")
            }

            state.error?.let { errorMessage ->
                Text(errorMessage)
            }

            LazyColumn(
                modifier = Modifier.fillMaxWidth()
            ) {
                items(
                    items = state.result,
                    key = { savePoint -> savePoint.savePointId }
                ) { savePoint ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "Coin ID: ${savePoint.coinId}",
                                fontWeight = FontWeight.Bold
                            )
                            Text(text = "Value: $${savePoint.valueSnapshot}")
                        }

                        Button(
                            onClick = {
                                viewModel.deleteSavePoint(savePoint)
                            }
                        ) {
                            Text("Delete")
                        }
                    }
                }
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
                Button(
                    onClick = {
                        navController.navigate("SavePointScreen")
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFC8BBE7),
                        contentColor = Color.Black
                    )
                ) {
                    Text("Save Points")
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
}
