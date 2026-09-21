package com.example.cst438_team1_project1.viewModels

import com.example.cst438_team1_project1.data.api.CryptoCoinRepository
import com.example.cst438_team1_project1.data.api.SavePointRepository
import com.example.cst438_team1_project1.data.entity.SavePoint
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class SavePointUiState(
    val isLoading: Boolean = false,
    val result: List<SavePoint> = emptyList(),
    val error: String? = null
)

class SavePointViewModel(private val repository: SavePointRepository){
    private var _uiState = MutableStateFlow(SavePointUiState())
    val uiState: StateFlow<SavePointUiState> = _uiState.asStateFlow()

    init {
        loadSavePoints()
    }

    fun loadSavePoints(){

    }
}