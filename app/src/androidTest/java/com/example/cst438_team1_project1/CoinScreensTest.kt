package com.example.cst438_team1_project1

import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.room3.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.cst438_team1_project1.composables.AddCoins
import com.example.cst438_team1_project1.composables.ViewCoins
import com.example.cst438_team1_project1.data.AppDatabase
import com.example.cst438_team1_project1.data.api.CoinGeckoAPI
import com.example.cst438_team1_project1.data.api.CryptoCoinRepository
import com.example.cst438_team1_project1.data.api.api_responses.SearchCoinsResponse
import com.example.cst438_team1_project1.data.entity.CryptoCoin
import com.example.cst438_team1_project1.viewModels.CoinViewModelFactory
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CoinScreensTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var database: AppDatabase
    private lateinit var repository: CryptoCoinRepository

    private val fakeApi = object : CoinGeckoAPI {
        override suspend fun searchCoins(query: String, apiKey: String) =
            SearchCoinsResponse(emptyList())

        override suspend fun getCoinPricesByName(name: String) =
            SearchCoinsResponse(emptyList())

        override suspend fun getCoinPricesByID(ids: String) =
            SearchCoinsResponse(emptyList())

        override suspend fun getCoinPricesBySymbol(symbols: String) =
            SearchCoinsResponse(emptyList())
    }

    @Before
    fun setUp() {
        database = Room.inMemoryDatabaseBuilder<AppDatabase>()
            .setDriver(BundledSQLiteDriver())
            .build()
        repository = CryptoCoinRepository(fakeApi, database.cryptoCoinDao())
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun addCoinsScreen_displaysSearchControls() {
        composeTestRule.setContent {
            AddCoins(
                navController = rememberNavController(),
                viewModel = viewModel(factory = CoinViewModelFactory(repository))
            )
        }

        composeTestRule.onNodeWithText("Search for a coin").assertIsDisplayed()
        composeTestRule.onNodeWithText("Search").assertIsDisplayed().assertIsNotEnabled()
        composeTestRule.onNodeWithText("Home Screen").assertIsDisplayed()
    }

    @Test
    fun viewCoinsScreen_displaysSavedCoin() = runTest {
        database.cryptoCoinDao().insertCoin(
            CryptoCoin(
                coinId = 1,
                coinName = "Bitcoin",
                coinTicker = "BTC",
                coinImage = "bitcoin_url"
            )
        )

        composeTestRule.setContent {
            ViewCoins(
                navController = rememberNavController(),
                viewModel = viewModel(factory = CoinViewModelFactory(repository))
            )
        }

        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("View Saved Coins").assertIsDisplayed()
        composeTestRule.onNodeWithText("Bitcoin (BTC)").assertIsDisplayed()
        composeTestRule.onNodeWithText("Remove").assertIsDisplayed()
    }
}
