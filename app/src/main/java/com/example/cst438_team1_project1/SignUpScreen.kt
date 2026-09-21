package com.example.cst438_team1_project1

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.cst438_team1_project1.data.AppDatabase
import com.example.cst438_team1_project1.data.api.RetrofitClient
import com.example.cst438_team1_project1.data.entity.User
import kotlinx.coroutines.launch
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.TextStyle
import com.example.cst438_team1_project1.data.SessionManager

@Composable
fun SignUpScreen(navController: NavController) {

    var username by remember { mutableStateOf("") }
    var pass1 by remember { mutableStateOf("") }
    var pass2 by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    var context = LocalContext.current
    val sessionManager = remember { SessionManager(context) }
    var coroutineScope = rememberCoroutineScope()
    //got help from gemini with coroutine scope

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Row() {
            Text("Crypto-Tracker", fontSize = 40.sp, fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(80.dp))

        Row() {
            Text(text = errorMessage, fontSize = 14.sp, color = Color.Red)
        }

        Row() {
            Text("Choose a Username:",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold)
            TextField(
                value = username,
                onValueChange = { username = it },
                placeholder = { Text("username here",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold) },
                modifier = Modifier.padding(start = 10.dp).width(200.dp)
            )
        }
        /*
    can find how to enter values into a text field from this link:
    https://developer.android.com/develop/ui/compose/text/migrate-state-based
    it talks about variable and mutableStateOf
    */
        Spacer(modifier = Modifier.height(12.dp))

        Row() {
            Text("Choose a password:",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold)
            TextField(
                value = pass1,
                onValueChange = { pass1 = it },
                placeholder = { Text("password here",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold) },
                modifier = Modifier.padding(start = 10.dp).width(200.dp)
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Row() {
            Text("Confirm password:",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold)
            TextField(
                value = pass2,
                onValueChange = { pass2 = it },
                placeholder = { Text("enter same password",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold) },
                modifier = Modifier.padding(start = 10.dp).width(200.dp)
            )
        }

        Spacer(modifier = Modifier.height(25.dp))

        Button(onClick = {
            errorMessage = ""

            if (username.isBlank()) {
                errorMessage = "Username cannot be blank."
                return@Button
            }

            if (pass1.isBlank() || pass2.isBlank()) {
                errorMessage = "Password must be filled in."
                return@Button
            }

            if (pass1 != pass2) {
                errorMessage = "Passwords must match."
                return@Button
            }

            /*
        we use coroutine  so that users aren't waiting after button is clicked for
        something to happen
        UI runs on main thread
        Database works on background thread
        Coroutine is a safe way to switch between them
        Button clicked -> lauch coroutine -> call suspend functions-> update UI
         */
            coroutineScope.launch {
                val db = AppDatabase.getDatabase(context)
                val userDao = db.userDao()
                val existingUser = userDao.findByUsername(username)

                if (existingUser != null) {
                    errorMessage = "Username already exisits."
                    return@launch
                }

                val newUser = User(username = username, password = pass1)
                userDao.insertUser(newUser)
                //when trying to insert user it kept crashing had to add KSP to project & add Room 3 compiler

                navController.navigate("ViewCoins") //view coins is now home

                // Example of how to call the Retrofit API:
                try {
                    val response = RetrofitClient.coinbaseApi.getExchangeRates("USD")
                    Log.d("CoinbaseAPI", "Rates for USD: ${response.data.rates}")
                } catch (e: Exception) {
                    Log.e("CoinbaseAPI", "Error fetching rates", e)
                }
            }

        }) {
            Text("SIGN UP!",
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(200.dp))

        Row() {
            Text(text = "Already have an account?",
                fontSize = 15.sp)
        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(onClick = {
            navController.navigate("Login")
        }) {
            Text(text = "LOGIN",
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold)
        }
    }
}