package com.example.cst438_team1_project1.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.cst438_team1_project1.data.AppDatabase
import com.example.cst438_team1_project1.data.SessionManager
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

@Composable
fun ChangePasswordScreen(navController: NavController) {
    Text("CHANGE PASSWORD PAGE")

    var newPass by remember { mutableStateOf("") }
    var confirmPass by remember { mutableStateOf("") }
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    var errorMessage by remember { mutableStateOf("") }

    val sessionManager = SessionManager(context)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 80.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("CHANGE PASSWORD PAGE", fontSize = 30.sp)

        if (errorMessage.isNotBlank()) {
            Text(text = errorMessage, fontSize = 14.sp, color = Color.Red)
        }

        Row() {
            Text(text = "Password: ", fontSize = 14.sp)

            TextField(
                value = newPass,
                onValueChange = {
                    newPass = it
                },
                placeholder = {
                    Text("Enter new username")
                }
            )
        }

        Row() {
            Text(text = "Confirm password: ", fontSize = 14.sp)
            TextField(
                value = confirmPass,
                onValueChange = {
                    confirmPass = it
                },
                placeholder = {
                    Text("Confirm new password")
                })
        }

        Button(onClick = {

            errorMessage = ""

            val passText = newPass
            val confirmText = confirmPass

            if (passText.isBlank() || confirmText.isBlank()) {
                errorMessage = "Password must be filled in."
                return@Button
            }

            if (passText != confirmText) {
                errorMessage = "Passwords must match."
                return@Button
            }

            coroutineScope.launch {
                val savedUserId = sessionManager.readUserId.first()

                if (savedUserId == null) {
                    errorMessage = "No Logged in user found"
                    return@launch
                }

                val db = AppDatabase.getDatabase(context)
                val dao = db.userDao()
                dao.updatePassword(savedUserId, passText)
                navController.popBackStack()
            }


        }) {
            Text(text = "Change Password")
        }

        Button(onClick = {
            navController.popBackStack()
        }) {
            Text("Cancel")
        }
    }
}
