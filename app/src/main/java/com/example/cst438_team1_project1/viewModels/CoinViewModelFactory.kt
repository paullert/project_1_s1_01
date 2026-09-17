package com.example.cst438_team1_project1.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.cst438_team1_project1.data.api.CryptoCoinRepository
import kotlin.jvm.java

class CoinViewModelFactory(
    private val repository: CryptoCoinRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(
        modelClass: Class<T>
    ): T {
        return when {
            modelClass.isAssignableFrom(SearchCoinsViewModel::class.java) -> {
                SearchCoinsViewModel(repository)
            }

            modelClass.isAssignableFrom(SavedCoinsViewModel::class.java) -> {
                SavedCoinsViewModel(repository)
            }

            else -> {
                throw IllegalArgumentException(
                    "Unknown ViewModel class: ${modelClass.name}"
                )
            }
        } as T
    }
}