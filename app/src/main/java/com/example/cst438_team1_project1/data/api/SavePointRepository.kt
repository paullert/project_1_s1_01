package com.example.cst438_team1_project1.data.api

import android.util.Log
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
}
