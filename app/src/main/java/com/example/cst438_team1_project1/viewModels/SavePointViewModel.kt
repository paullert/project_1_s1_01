package com.example.cst438_team1_project1.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cst438_team1_project1.data.api.CryptoCoinRepository
import com.example.cst438_team1_project1.data.api.SavePointRepository
import com.example.cst438_team1_project1.data.entity.SavePoint
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
data class SavePointUiState(
    val isLoading: Boolean = false,
    val result: List<SavePoint> = emptyList(),
    val error: String? = null
)

class SavePointViewModel(private val repository: SavePointRepository,private val currentUserId: Int): ViewModel(){
    private var _uiState = MutableStateFlow(SavePointUiState())
    val uiState: StateFlow<SavePointUiState> = _uiState.asStateFlow()

    init {
        loadSavePoints()
    }

    fun loadSavePoints(){
        viewModelScope.launch {
            _uiState.update { currentState ->
                currentState.copy(
                    isLoading = true,
                    error = null
                )
            }

            try {
                val savePoints = repository.loadAllSavePoints(currentUserId)
                _uiState.update { currentState ->
                    currentState.copy(
                        isLoading = false,
                        result = savePoints
                    )
                }
            } catch (exception: Exception){
                _uiState.update { currentState ->
                    currentState.copy(
                        isLoading = false,
                        error = exception.message ?: "Could not load coins"
                    )
                }
            }
        }
    }

    fun deleteSavePoint(savePoint: SavePoint){
        viewModelScope.launch {
            try {
                repository.deleteSavePoint(savePoint)
                Log.d("DATABASE_TEST", "Remove SavePoint: ${savePoint.savePointId}")
                loadSavePoints()
            } catch (exception: Exception){
                Log.e(
                    "DATABASE_TEST",
                    "Delete failed",
                    exception
                )
            }
        }
    }
}