package com.example.cst438_team1_project1.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cst438_team1_project1.BuildConfig
import com.example.cst438_team1_project1.data.api.CryptoCoinRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class CoinRow (
    val coinId: Int,
    val coinName: String,
    val coinSlug: String,
    val coinTicker: String,
    val coinImage: String
)

class SearchCoinsViewModel(private val repository: CryptoCoinRepository) : ViewModel() {
    private var _uiState = MutableStateFlow(SavedCoinsUiState())
    val uiState: StateFlow<SavedCoinsUiState> = _uiState.asStateFlow()


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

    fun addCoin(coinRow: CoinRow) {
        viewModelScope.launch {
            try {
                Log.d("DATABASE_TEST", "Add clicked: ${coinRow.coinName}")

                repository.addCoin(coinRow)

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
