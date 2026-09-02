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
import androidx.room3.Room
import androidx.sqlite.driver.AndroidSQLiteDriver
import com.example.cst438_team1_project1.data.AppDatabase
import com.example.cst438_team1_project1.data.entity.User
import kotlinx.coroutines.coroutineScope

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

    var username by remember { mutableStateOf("") }
    var pass1 by remember { mutableStateOf("") }
    var pass2 by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    var context = LocalContext.current
    var coroutineScope = rememberCoroutineScope()
    //got help from gemini with coroutine scope

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
        /*
        can find how to enter values into a text field from this link:
        https://developer.android.com/develop/ui/compose/text/migrate-state-based
        it talks about variable and mutableStateOf
        */

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

            /*
            we use coroutine  so that users aren't waiting after button is clicked for
            something to happen
            UI runs on main thread
            Database works on background thread
            Coroutine is a safe way to switch between them
            Button clicked -> lauch coroutine -> call suspend functions-> update UI
             */
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
                //when trying to insert user it kept crashing had to add KSP to project & add Room 3 compiler

                //TODO make it so that it'll go to homepage screen after creating acc
            }

        }) {
            Text("SIGN UP!")
        }

        Row() {
            Text(text = "Already have an account?")
        }

        Button(onClick = {}){
            Text(text = "LOGIN")
            //TODO make it so that go to Login page
        }
    }
}

@Composable
fun HomePage() {
    Text("Home Page TBD")
}