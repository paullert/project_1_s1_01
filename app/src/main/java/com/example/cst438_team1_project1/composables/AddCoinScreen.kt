package com.example.cst438_team1_project1.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.cst438_team1_project1.viewModels.SearchCoinsViewModel

@Composable
fun AddCoins(navController: NavController, viewModel: SearchCoinsViewModel = viewModel()) {
    var query by remember {
        mutableStateOf("")
    }

    val state by viewModel.uiState.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        TextField(
            value = query,
            onValueChange = { newText ->
                query = newText
            },
            label = {
                Text("Search for a coin")
            }
        )

        Button(
            onClick = {
                viewModel.searchCoins(query)
            },
            enabled = query.isNotBlank() && !state.isLoading
        ) {
            Text(
                if (state.isLoading) {
                    "Searching..."
                } else {
                    "Search"
                }
            )
        }

        state.error?.let { errorMessage ->
            Text(errorMessage)
        }

        LazyColumn {
            items(
                items = state.result,
                key = { coin -> coin.coinName }
            ) { coin ->
                CoinRow(
                    coinRow = coin,
                    buttonFunction = {
                        viewModel.addCoin(coin)
                    },
                    buttonText = "Add"
                )
            }
        }

        Button(onClick = {
            navController.popBackStack()
        }) {
            Text("Home Screen")
        }
    }
}



