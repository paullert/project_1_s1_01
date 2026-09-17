package com.example.cst438_team1_project1

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.cst438_team1_project1.data.AppDatabase
import com.example.cst438_team1_project1.data.api.CryptoCoinRepository
import com.example.cst438_team1_project1.data.api.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class Coin (
//    val coinId: String,
    val coinName: String,
    val coinTicker: String,
    val coinImage: String
)

data class SearchUiState(
    val isLoading: Boolean = false,
    val result: List<Coin> = emptyList(),
    val error: String? = null
)

class SearchViewModel(private val repository: CryptoCoinRepository) : ViewModel() {
    private var _uiState = MutableStateFlow(SearchUiState())
    val uiState: StateFlow<SearchUiState> = _uiState.asStateFlow()


    fun searchCoins(query: String) {
        viewModelScope.launch {
            _uiState.update { currentState ->
                currentState.copy(
                    isLoading = true,
                    error = null
                )
            }

            try {
                val coins = repository.searchCoins(
                    query = query,
                    apiKey = BuildConfig.COINGECKO_API_KEY
                )

                _uiState.update { currentState ->
                    currentState.copy(
                        isLoading = false,
                        result = coins
                    )
                }
            } catch (exception: Exception) {
                _uiState.update { currentState ->
                    currentState.copy(
                        isLoading = false,
                        error = exception.message
                            ?: "Could not load coins"
                    )
                }
            }
        }
    }

    fun addCoin(coin: Coin) {
        viewModelScope.launch {
            try {
                Log.d("DATABASE_TEST", "Add clicked: ${coin.coinName}")

                repository.addCoin(coin)

                Log.d("DATABASE_TEST", "Repository insert finished")
            } catch (exception: Exception) {
                Log.e(
                    "DATABASE_TEST",
                    "Insert failed",
                    exception
                )
            }
        }
    }
}