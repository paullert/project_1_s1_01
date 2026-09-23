package com.example.cst438_team1_project1.composables

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.cst438_team1_project1.viewModels.CoinRow

@Composable
fun CoinRow(
    coinRow: CoinRow,
    buttonFunction: () -> Unit,
    buttonText: String,
    secondButtonFunction: (() -> Unit)? = null,
    secondButtonText: String? = null
) {
    Row() {
        AsyncImage(
            model = coinRow.coinImage,
            contentDescription = "The ${coinRow.coinName} crypto currency logo"
        )
        Text(
            text = "${coinRow.coinName} (${coinRow.coinTicker})",
            modifier = Modifier
                .padding(8.dp)
        )

        Button(
            onClick = {
                buttonFunction()
            }
        ) {
            Text(buttonText)
        }

        if (secondButtonFunction != null && secondButtonText != null) {
            Button(
                onClick = {
                    secondButtonFunction()
                }
            ) {
                Text(secondButtonText)
            }
        }
    }
}
