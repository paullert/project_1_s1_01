package com.example.cst438_team1_project1.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.cst438_team1_project1.data.api.SavePointRepository

class SavePointModelFactory(
    private val repository: SavePointRepository,
    private val userId: Int
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(SavePointViewModel::class.java) -> {
                SavePointViewModel(repository, userId)
            }
            else -> {
                throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        } as T
    }
}
