package com.example.cst438_team1_project1.data.api

import com.example.cst438_team1_project1.data.Dao.CryptoCoinDao
import com.example.cst438_team1_project1.data.Dao.SavePointDao
import com.example.cst438_team1_project1.data.entity.SavePoint

class SavePointRepository(
    private val coinGeckoAPI: CoinGeckoAPI,
    private val cryptoCoinDao: CryptoCoinDao,// <-- perhaps optional
    private val savePointDao: SavePointDao,
) {
    suspend fun loadAllSavePoints(userId: Int): List<SavePoint> {
        val response = savePointDao.findSavePointsByUserId(userId)
        return response;
    }
}