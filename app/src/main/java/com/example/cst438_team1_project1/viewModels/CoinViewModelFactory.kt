package com.example.cst438_team1_project1.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.cst438_team1_project1.data.api.CryptoCoinRepository
import com.example.cst438_team1_project1.data.api.SavePointRepository
import kotlin.jvm.java

class CoinViewModelFactory(
    private val repository: CryptoCoinRepository,
    private val savePointRepository: SavePointRepository? = null
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(
        modelClass: Class<T>
    ): T {
        return when {
            modelClass.isAssignableFrom(SearchCoinsViewModel::class.java) -> {
                SearchCoinsViewModel(repository)
            }

            modelClass.isAssignableFrom(SavedCoinsViewModel::class.java) -> {
                SavedCoinsViewModel(
                    repository,
                    savePointRepository ?: throw IllegalArgumentException(
                        "SavePointRepository is required for SavedCoinsViewModel"
                    )
                )
            }

            else -> {
                throw IllegalArgumentException(
                    "Unknown ViewModel class: ${modelClass.name}"
                )
            }
        } as T
    }
}
