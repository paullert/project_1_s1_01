package com.example.cst438_team1_project1

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent{
            SignUpScreen()
        }
    }
}

@Composable
fun SignUpScreen() {
    Column(modifier = Modifier.fillMaxSize().padding(top = 80.dp)){

        Row(){
            Text("Crypto-Tracker", fontSize = 40.sp, fontWeight = FontWeight.Bold)
        }

        Row(){
            Text("Choose a Username:")
            TextField( value ="enter new username",
                onValueChange = {},
                )
        }

        Row(){
            Text("Choose a password:")
            TextField( value ="enter password",
                onValueChange = {},
            )
        }

        Row(){
            Text("Confirm password:")
            TextField( value ="re-enter password",
                onValueChange = {},
            )
        }
    }
}