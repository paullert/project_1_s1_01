package com.example.cst438_team1_project1.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.cst438_team1_project1.data.AppDatabase
import com.example.cst438_team1_project1.data.SessionManager
import kotlinx.coroutines.launch
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

@Composable
fun LoginScreen(navcontroller: NavController) {

    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var loginError by remember { mutableStateOf("") }

    val context = LocalContext.current
    val sessionManager = remember { SessionManager(context) }
    val database = AppDatabase.getDatabase(context)
    val scope = rememberCoroutineScope()


    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {


        Row() {
            Text("Login Screen!", fontSize = 40.sp, fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(100.dp))

        Row() {
            TextField(
                value = username,
                onValueChange = { username = it },
                placeholder = {
                    Text(
                        "username",
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Row() {
            TextField(
                value = password,
                onValueChange = { password = it },
                placeholder = {
                    Text(
                        "password",
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        Row() {
            if (loginError.isNotEmpty()) {
                Text(loginError)
            }
        }

        Spacer(modifier = Modifier.height(10.dp))


        Row() {
            Button(
                onClick = {
                    scope.launch {
                        val user = database.userDao().findByUsername(username)

                        if (user != null && user.password == password) {
                            sessionManager.saveUserId(user.userId)
                            navcontroller.navigate("ViewCoins") //view Coins is now home
                        } else {
                            loginError = "Invalid username or password"
                        }
                    }
                }
            ) {
                Text(
                    "login",
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(modifier = Modifier.height(200.dp))

        Row() {
            Button(
                onClick = {
                    navcontroller.navigate("SignUp")
                }
            ) {
                Text(
                    "Create Account",
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }


    }
}
