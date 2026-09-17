package com.example.cst438_team1_project1.data.api

import android.util.Log
import com.example.cst438_team1_project1.Coin
import com.example.cst438_team1_project1.data.Dao.CryptoCoinDao
import com.example.cst438_team1_project1.data.entity.CryptoCoin

class CryptoCoinRepository (
    private val coinGeckoAPI: CoinGeckoAPI,
    private val cryptoCoinDao: CryptoCoinDao
) {
    suspend fun searchCoins(query: String, apiKey: String): List<Coin> {
        val response = coinGeckoAPI.searchCoins(query, apiKey).coins

        return response.map { apiCoin ->
            Coin(
                coinName = apiCoin.name,
                coinTicker = apiCoin.symbol,
                coinImage = apiCoin.large
            )
        }
    }

    suspend fun addCoin(coin: Coin) {
        val databaseCoin = CryptoCoin(
            coinName = coin.coinName,
            coinTicker = coin.coinTicker,
            coinImage = coin.coinImage
        )
        val insertedId = cryptoCoinDao.insertCoin(databaseCoin)

        Log.d("DATABASE_TEST", "Inserted coin with ID: $insertedId")
    }
}