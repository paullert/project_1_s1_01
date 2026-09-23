package com.example.cst438_team1_project1.data.api

import android.util.Log
import com.example.cst438_team1_project1.BuildConfig
import com.example.cst438_team1_project1.data.Dao.CryptoCoinDao
import com.example.cst438_team1_project1.data.Dao.SavePointDao
import com.example.cst438_team1_project1.data.entity.SavePoint

data class SavePointWithCoin(
    val savePoint: SavePoint,
    val coinName: String,
    val coinTicker: String,
    val coinSlug: String,
    val currentPriceSnapshot: String
)

class SavePointRepository(
    private val coinGeckoAPI: CoinGeckoAPI,
    private val cryptoCoinDao: CryptoCoinDao,// <-- perhaps optional
    private val savePointDao: SavePointDao,
) {
    suspend fun loadAllSavePoints(userId: Int): List<SavePointWithCoin> {
        val savePoints = savePointDao.findSavePointsByUserId(userId)
        val coinsBySavePointId = savePoints.associate { savePoint ->
            savePoint.savePointId to cryptoCoinDao.findById(savePoint.coinId)
        }
        val currentPrices = loadCurrentUsdPrices(
            coinsBySavePointId.values.mapNotNull { coin -> coin?.coinSlug }
        )

        return savePoints.map { savePoint ->
            val coin = coinsBySavePointId[savePoint.savePointId]
            SavePointWithCoin(
                savePoint = savePoint,
                coinName = coin?.coinName ?: "Unknown Coin",
                coinTicker = coin?.coinTicker ?: "",
                coinSlug = coin?.coinSlug ?: "",
                currentPriceSnapshot = currentPrices[coin?.coinSlug].orEmpty()
            )
        }
    }

    suspend fun addSavePoint(coinId: Int, userId: Int, coinSlug: String): Boolean {
        return addSavePoint(
            SavePoint(
                coinId = coinId,
                userId = userId,
                valueSnapshot = loadCurrentUsdPrice(coinSlug).orEmpty()
            )
        )
    }

    suspend fun addSavePoint(savePoint: SavePoint): Boolean {
        return try {
            savePointDao.insertSavePoint(savePoint)
            true
        } catch (exception: Exception) {
            false
        }
    }

    suspend fun deleteSavePoint(savePoint: SavePoint){
        val deletedId = savePointDao.deleteSavePoint(savePoint)
        Log.d("DATABASE_TEST", "Removed savePoint with ID: $deletedId")
    }

    suspend fun deleteSavePointById(userId: Int,savePointId:Int){
        val deletedId = savePointDao.deleteByUserAndCoin(userId,savePointId)
        Log.d("DATABASE_TEST", "Removed savePoint with ID: $deletedId")
    }

    private suspend fun loadCurrentUsdPrice(coinSlug: String): String? {
        return loadCurrentUsdPrices(listOf(coinSlug))[coinSlug]
    }

    private suspend fun loadCurrentUsdPrices(coinSlugs: List<String>): Map<String, String> {
        val ids = coinSlugs
            .filter { coinSlug -> coinSlug.isNotBlank() }
            .distinct()
            .joinToString(",")

        if (ids.isBlank()) {
            return emptyMap()
        }

        return try {
            coinGeckoAPI.getCoinPricesByID(
                ids = ids,
                vsCurrencies = "usd",
                apiKey = BuildConfig.COINGECKO_API_KEY
            ).mapNotNull { (coinSlug, prices) ->
                prices["usd"]?.let { usdPrice ->
                    coinSlug to usdPrice.toString()
                }
            }.toMap()
        } catch (exception: Exception) {
            emptyMap()
        }
    }
}
