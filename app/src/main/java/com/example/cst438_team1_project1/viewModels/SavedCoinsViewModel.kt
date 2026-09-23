package com.example.cst438_team1_project1.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cst438_team1_project1.data.api.CryptoCoinRepository
import com.example.cst438_team1_project1.data.api.SavePointRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

//data class Coin (
////    val coinId: String,
//    val coinName: String,
//    val coinTicker: String,
//    val coinImage: String
//)

data class SavedCoinsUiState(
    val isLoading: Boolean = false,
    val result: List<CoinRow> = emptyList(),
    val error: String? = null
)

class SavedCoinsViewModel(private val repository: CryptoCoinRepository,private val savePointRepository: SavePointRepository) : ViewModel() {
    private var _uiState = MutableStateFlow(SavedCoinsUiState())
    val uiState: StateFlow<SavedCoinsUiState> = _uiState.asStateFlow()

    init {
        loadCoins()
    }

    fun loadCoins() {
        viewModelScope.launch {
            _uiState.update { currentState ->
                currentState.copy(
                    isLoading = true,
                    error = null
                )
            }

            try {
                val coins = repository.loadAllCoins()

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

    fun removeCoin(coin: CoinRow) {
        viewModelScope.launch {
            try {
                Log.d("DATABASE_TEST", "Remove clicked: ${coin.coinName}")

                repository.removeCoin(coin)

                Log.d("DATABASE_TEST", "Repository delete finished")
                loadCoins()
            } catch (exception: Exception) {
                Log.e(
                    "DATABASE_TEST",
                    "Delete failed",
                    exception
                )
            }
        }
    }

    fun addSavePoint(coin: CoinRow, userId: Int){
        viewModelScope.launch {
            try {
                if (savePointRepository.addSavePoint(coin.coinId, userId, coin.coinSlug)) {
                    Log.d("DATABASE_TEST", "Added savePoint for coin: ${coin.coinId}")
                }
            }catch(exception: Exception){
                Log.e(
                    "DATABASE_TEST",
                    "Add savePoint failed",
                    exception
                )
            }
        }
    }


}
