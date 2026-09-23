package com.example.cst438_team1_project1.data.api

import android.util.Log
import com.example.cst438_team1_project1.viewModels.CoinRow
import com.example.cst438_team1_project1.data.Dao.CryptoCoinDao
import com.example.cst438_team1_project1.data.entity.CryptoCoin

class CryptoCoinRepository (
    private val coinGeckoAPI: CoinGeckoAPI,
    private val cryptoCoinDao: CryptoCoinDao
) {
    suspend fun searchCoins(query: String, apiKey: String): List<CoinRow> {
        val response = coinGeckoAPI.searchCoins(query, apiKey).coins

        return response.map { apiCoin ->
            CoinRow(
                coinId = -1,
                coinSlug = apiCoin.id,
                coinName = apiCoin.name,
                coinTicker = apiCoin.symbol,
                coinImage = apiCoin.large
            )
        }
    }

    suspend fun loadAllCoins(): List<CoinRow> {
        return cryptoCoinDao.getAllCoins().map { dbCoin ->
            CoinRow(
                coinId = dbCoin.coinId,
                coinSlug = dbCoin.coinSlug,
                coinName = dbCoin.coinName,
                coinTicker = dbCoin.coinTicker,
                coinImage = dbCoin.coinImage
            )
        }
    }

    suspend fun addCoin(coinRow: CoinRow) {
        val dbCoin = CryptoCoin(
            coinSlug = coinRow.coinSlug,
            coinName = coinRow.coinName,
            coinTicker = coinRow.coinTicker,
            coinImage = coinRow.coinImage
        )
        val insertedId = cryptoCoinDao.insertCoin(dbCoin)

        Log.d("DATABASE_TEST", "Inserted coin with ID: $insertedId")
    }

    suspend fun removeCoin(coinRow: CoinRow) {
        val deleted_id = cryptoCoinDao.deleteCoin(convertCoinRow(coinRow))

        Log.d("DATABASE_TEST", "Removed coin with ID: $deleted_id")
    }

    fun convertCoinRow(coinRow: CoinRow): CryptoCoin {
        return CryptoCoin(
            coinId = coinRow.coinId,
            coinSlug = coinRow.coinSlug,
            coinName = coinRow.coinName,
            coinTicker = coinRow.coinTicker,
            coinImage = coinRow.coinImage,
        )
    }

    // TODO: Implement use or remove
    fun convertDbCoin(dbCoin: CryptoCoin): CoinRow {
        return CoinRow(
            coinId = dbCoin.coinId,
            coinSlug = dbCoin.coinSlug,
            coinName = dbCoin.coinName,
            coinTicker = dbCoin.coinTicker,
            coinImage = dbCoin.coinImage,
        )
    }
}
