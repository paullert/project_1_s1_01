package com.example.cst438_team1_project1

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
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.launch
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable
import com.example.cst438_team1_project1.data.AppDatabase
import com.example.cst438_team1_project1.data.entity.User
import android.util.Log
import com.example.cst438_team1_project1.data.api.RetrofitClient

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent{
            val remNavController = rememberNavController()
            NavHost(
                navController = remNavController,
                startDestination = "SignUp") //TODO: CHANGE startDestination to LOGIN Page when done
            {
                composable("SignUp"){
                    SignUpScreen(remNavController)
                }
                composable("Home"){
                    HomeScreen(remNavController)
                }
                composable("Favorites"){
                    FavoritesScreen()
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

    Column(modifier = Modifier.fillMaxSize().padding(top = 80.dp)){

        Row(){
            Text("Crypto-Tracker", fontSize = 40.sp, fontWeight = FontWeight.Bold)
        }

        Row(){
            Text(text= errorMessage, fontSize = 14.sp, color = Color.Red)
        }

        Row(){
            Text("Choose a Username:")
            TextField( value = username,
                onValueChange = { username = it},
                placeholder = { Text("username here.")}
            )
        }


        Row(){
            Text("Choose a password:")
            TextField( value = pass1,
                onValueChange = { pass1 = it},
                placeholder = { Text("password here")}
            )
        }

        Row(){
            Text("Confirm password:")
            TextField( value = pass2,
                onValueChange = { pass2 = it},
                placeholder = {Text("enter same password")}
            )
        }

        Button(onClick = {
            errorMessage = ""

            if(username.isBlank()){
                errorMessage = "Username cannot be blank."
                return@Button
            }

            if(pass1.isBlank() || pass2.isBlank()){
                errorMessage = "Password must be filled in."
                return@Button
            }

            if(pass1 != pass2){
                errorMessage = "Passwords must match."
                return@Button
            }

            coroutineScope.launch{
                val db = AppDatabase.getDatabase(context)
                val userDao = db.userDao()
                val existingUser = userDao.findByUsername(username)

                if(existingUser != null){
                    errorMessage = "Username already exisits."
                    return@launch
                }

                val newUser = User(username = username, password = pass1)
                userDao.insertUser(newUser)

                navController.navigate("Home")

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

        Button(onClick = {}){
            Text(text = "LOGIN")
        }
    }
}



@Composable
fun HomeScreen(navController: NavController) {
    var context = LocalContext.current;
    Column(modifier = Modifier.fillMaxSize().padding(top = 80.dp)){
        Row(){
            Text(text="WIP HOME PAGE", fontSize = 30.sp, fontWeight = FontWeight.Bold)
        }

        Row(){
            Text(text="This will be the explore page where different coins are shown",
                fontSize = 20.sp)
        }

        Button(onClick = {
            //TODO: FINISH LOG OUT PAGE ONCE ACCOUNT PAGE IS MADE





            navController.navigate("SignUp")

        }) {
            Text("LOG OUT!")
        }
    }
}

@Composable
fun FavoritesScreen(){
    Column(modifier = Modifier.fillMaxSize().padding(top = 80.dp)){
        Row(){
            Text("This will be for the user's favorite coins to see")
        }
    }
}

@Composable
fun AccountScreen(){
}