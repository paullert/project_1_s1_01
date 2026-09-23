package com.example.cst438_team1_project1.data.Dao

import androidx.room3.Dao
import androidx.room3.Delete
import androidx.room3.Insert
import androidx.room3.Query
import androidx.room3.Update
import com.example.cst438_team1_project1.data.entity.CryptoCoin
import com.example.cst438_team1_project1.data.entity.SavePoint

@Dao
interface SavePointDao {
    @Insert
    suspend fun insertSavePoint(savePoint: SavePoint)


    @Delete
    suspend fun deleteSavePoint(savePoint: SavePoint)

    //Update not needed: SavePoint is static

    @Query("SELECT * FROM crypto_save_points WHERE userId = :userId ORDER BY created_at DESC")
    suspend fun findSavePointsByUserId(userId: Int): List<SavePoint>

    //When a user is looking at a coin, you might want to know if they’ve already saved it
    @Query("SELECT * FROM crypto_save_points WHERE userId = :userId AND coinId = :coinId")
    suspend fun getSavePoint(userId: Int, coinId: Int): SavePoint?

    //Deletes crypto coin based on user and coin Id!
    @Query("DELETE FROM crypto_save_points WHERE userId = :userId AND coinId = :coinId")
    suspend fun deleteByUserAndCoin(userId: Int, coinId: Int)

    //STRICTLY FOR TESTING -->
    @Query("SELECT * FROM crypto_save_points")
    suspend fun getAllSavePoints(): List<SavePoint>

    @Query("DELETE FROM crypto_save_points WHERE userId = :userId")
    suspend fun deleteByUserId(userId: Int)
}