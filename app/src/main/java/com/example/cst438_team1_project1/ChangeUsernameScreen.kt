package com.example.cst438_team1_project1

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
fun ChangeUsernameScreen(navController: NavController) {

    var newUsername by remember { mutableStateOf("") }
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val sessionManager = SessionManager(context)

    var errorMessage by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 80.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "CHANGE USERNAME PAGE",
            fontSize = 30.sp
        )

        if (errorMessage.isNotBlank()) {
            Text(text = errorMessage, fontSize = 14.sp, color = Color.Red)
        }

        Row() {
            Text(text = "Username: ", fontSize = 14.sp)

            TextField(
                value = newUsername,
                onValueChange = {
                    newUsername = it
                },
                placeholder = {
                    Text("Enter new username")
                }
            )

        }

        Row() {
            Button(onClick = {
                errorMessage = ""

                if (newUsername.isBlank()) {
                    errorMessage = "Username cannot be blank."
                    return@Button
                }

                //room logic here
                coroutineScope.launch {
                    val savedUserId = sessionManager.readUserId.first()

                    if (savedUserId == null) {
                        errorMessage = "No logged in user found"
                        return@launch
                    }

                    val db = AppDatabase.getDatabase(context)
                    val userDao = db.userDao()
                    val existingUser = userDao.findByUsername(newUsername)

                    if (existingUser != null && existingUser.userId != savedUserId) {
                        errorMessage = "Username already exists."
                        return@launch
                    }

                    userDao.updateUsername(savedUserId, newUsername)
                    navController.popBackStack() //using popBackStack because if we do .navigate("home")
                    //its like adding another screen to the stack and we don't want that
                }

            }) {
                Text("Change Username")
            }

            Button(onClick = {
                navController.popBackStack()
            })
            {
                Text("Cancel")
            }
        }
    }
}