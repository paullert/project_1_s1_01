package com.example.cst438_team1_project1

import android.content.Context
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawContext
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.launch
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable
import androidx.room3.Room
import androidx.sqlite.driver.AndroidSQLiteDriver
import com.example.cst438_team1_project1.data.AppDatabase
import com.example.cst438_team1_project1.data.api.RetrofitClient
import com.example.cst438_team1_project1.data.entity.User
import kotlinx.coroutines.coroutineScope
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.Surface
import androidx.compose.ui.Alignment
import com.example.cst438_team1_project1.data.SessionManager
import kotlinx.coroutines.flow.first
import org.junit.Test

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            Surface(modifier = Modifier.fillMaxSize(),
                color = Color.White) { //screen was black so added default background color
                val remNavController = rememberNavController()

                NavHost(
                    navController = remNavController,
                    startDestination = "SignUp"
                ) //TODO: CHANGE startDestination to LOGIN Page when done
                {
                    composable("SignUp") {
                        SignUpScreen(remNavController)
                    }
                    composable("Home") {
                        HomeScreen(remNavController)
                    }
                    composable("Favorites") {
                        FavoritesScreen(remNavController)
                    }
                    composable("Account") {
                        AccountScreen(remNavController)
                    }
                    composable("changeUsername") {
                        ChangeUsernameScreen(remNavController)
                    }
                    composable("changePassword") {
                        ChangePasswordScreen(remNavController)
                    }
                }

            }
        }
    }

    @Composable
    fun SignUpScreen(navController: NavController) {

        var username by remember { mutableStateOf("") }
        var pass1 by remember { mutableStateOf("") }
        var pass2 by remember { mutableStateOf("") }
        var errorMessage by remember { mutableStateOf("") }

        var context = LocalContext.current
        var coroutineScope = rememberCoroutineScope()
        //got help from gemini with coroutine scope

        Column(modifier = Modifier.fillMaxSize().padding(top = 80.dp)) {

            Row() {
                Text("Crypto-Tracker", fontSize = 40.sp, fontWeight = FontWeight.Bold)
            }

            Row() {
                Text(text = errorMessage, fontSize = 14.sp, color = Color.Red)
            }

            Row() {
                Text("Choose a Username:")
                TextField(
                    value = username,
                    onValueChange = { username = it },
                    placeholder = { Text("username here.") }
                )
            }
            /*
        can find how to enter values into a text field from this link:
        https://developer.android.com/develop/ui/compose/text/migrate-state-based
        it talks about variable and mutableStateOf
        */

            Row() {
                Text("Choose a password:")
                TextField(
                    value = pass1,
                    onValueChange = { pass1 = it },
                    placeholder = { Text("password here") }
                )
            }

            Row() {
                Text("Confirm password:")
                TextField(
                    value = pass2,
                    onValueChange = { pass2 = it },
                    placeholder = { Text("enter same password") }
                )
            }

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
                    val insertedUserId = userDao.insertUser(newUser).toInt()
                    val sessionManager = SessionManager(context)
                    sessionManager.saveUserId(insertedUserId)
                    //when trying to insert user it kept crashing had to add KSP to project & add Room 3 compiler

                    //TODO make it so that it'll go to homepage screen after creating acc
                    navController.navigate("Home")

                    // Example of how to call the Retrofit API:
                    try {
                        val response = RetrofitClient.coinbaseApi.getExchangeRates("USD")
                        Log.d("CoinbaseAPI", "Rates for USD: ${response.data.rates}")
                    } catch (e: Exception) {
                        Log.e("CoinbaseAPI", "Error fetching rates", e)
                    }
                }

            }) {
                Text("SIGN UP!")
            }

            Row() {
                Text(text = "Already have an account?")
            }

            Button(onClick = {}) {
                Text(text = "LOGIN")
                //TODO make it so that go to Login page
                //will look something like: navController.navigate("Login")
            }
        }
    }

    @Composable
    fun HomeScreen(navController: NavController) { //will eventually also take a parameter for navController when complete
        Column(modifier = Modifier.fillMaxSize().padding(top = 80.dp)) {
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
            modifier = Modifier.fillMaxSize().padding(bottom = 40.dp),
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

    @Composable
    fun FavoritesScreen(navController: NavController) { //will eventually also take a parameter for navController when complete
        Column(modifier = Modifier.fillMaxSize().padding(top = 80.dp)) {
            Row() {
                Text("This will be for the user's favorite coins to see")
            }
        }

        //NAVIGATION BAR
        Row(
            modifier = Modifier.fillMaxSize().padding(bottom = 40.dp),
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

    @Composable
    fun AccountScreen(navController: NavController) {

        Column(
            modifier = Modifier.fillMaxSize().padding(top = 80.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row() {
                Text("This is the Accounts Screen!")
            }

            Button(onClick = {
                navController.navigate("changeUsername")
            }
            ) {
                Text("Change Username")
            }

            Button(onClick = {
                navController.navigate("changePassword")
            }) {
                Text("Change Password")
            }

            Button(onClick = {
                //delete Account logic here
            }) {
                Text("Delete Account")
            }
        }


        //NAVIGATION BAR
        Row(
            modifier = Modifier.fillMaxSize().padding(bottom = 40.dp),
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

@Composable
fun ChangeUsernameScreen(navController: NavController) {

        var newUsername by remember { mutableStateOf("") }
        val context = LocalContext.current
        val coroutineScope = rememberCoroutineScope()
        val sessionManager = SessionManager(context)

        var errorMessage by remember { mutableStateOf("") }

        Column(
            modifier = Modifier.fillMaxSize().padding(top = 80.dp),
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

            Row(){
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

                        if (existingUser != null && existingUser.id != savedUserId) {
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
}

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
        modifier = Modifier.fillMaxSize().padding(top = 80.dp),
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
