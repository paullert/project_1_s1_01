package com.example.cst438_team1_project1

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.launch
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable
import com.example.cst438_team1_project1.data.AppDatabase
import com.example.cst438_team1_project1.data.api.RetrofitClient
import androidx.compose.material3.Surface
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cst438_team1_project1.composables.AddCoins
import com.example.cst438_team1_project1.composables.ViewCoins
import com.example.cst438_team1_project1.data.SessionManager
import com.example.cst438_team1_project1.data.api.CryptoCoinRepository
import com.example.cst438_team1_project1.viewModels.CoinViewModelFactory
import com.example.cst438_team1_project1.data.createTestUsers
import androidx.compose.runtime.collectAsState

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val database = AppDatabase.getDatabase(this)

        // Populate in-app list and add values to coin table
        val repository = CryptoCoinRepository(
            coinGeckoAPI = RetrofitClient.coinGeckoAPI,
            cryptoCoinDao = database.cryptoCoinDao()
        )

        lifecycleScope.launch {
            createTestUsers(database)
        }
        setContent {
            // Used to search API, generate an in-app list, and add values to coin table

            val sessionManager = remember { SessionManager(this@MainActivity) }
            val loggedInUserId by sessionManager.readUserId.collectAsState(initial = null)
            val startDestination = if (loggedInUserId != null) { "ViewCoins" } else { "Login" }


            Surface(
                modifier = Modifier.fillMaxSize(),
                color = Color.White
            ) { //screen was black so added default background color
                val remNavController = rememberNavController()
                NavHost(
                    navController = remNavController,
                    startDestination = startDestination
                )
                {
                    composable("Login") {
                        LoginScreen(remNavController)
                    }
                    composable("SignUp") {
                        SignUpScreen(remNavController)
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
                    // Added through separate file with compose function
                    composable("AddCoins") { //ADD COINS is same as Explore
                        AddCoins(remNavController, viewModel = viewModel(
                            factory = CoinViewModelFactory(repository)
                        ))
                    }

                    composable("ViewCoins") { //THIS IS NEW HOME
                        ViewCoins(remNavController, viewModel = viewModel(
                            factory = CoinViewModelFactory(repository)
                        ))
                    }
                }

            }
        }
    }


}
