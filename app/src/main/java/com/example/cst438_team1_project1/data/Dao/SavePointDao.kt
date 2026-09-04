package com.example.cst438_team1_project1.data.Dao

import androidx.room3.Dao
import androidx.room3.Delete
import androidx.room3.Insert
import androidx.room3.Query
import androidx.room3.Update
import com.example.cst438_team1_project1.data.entity.SavePoint

@Dao
interface SavePointDao {
    @Insert
    suspend fun insertSavePoint(save: SavePoint)
    // Equivalent to "INSERT INTO table (..., ...) VALUES(..., ...)"

    @Delete
    suspend fun deleteSavePoint(save: SavePoint)
    // Easier alternative to delete query

    @Update
    suspend fun updateSavePoint(save: SavePoint)
    // Could be used later on, we'll see

    @Query("SELECT * FROM save_points WHERE user_id = :userId")
    suspend fun searchByUser(userId: Int) : SavePoint?
    // Finds save points by user id

    @Query("SELECT * FROM save_points WHERE user_id = :userId ORDER BY price_USD DESC")
    suspend fun searchByUserAsc(userId: Int) : SavePoint?
    // Finds save points by user id, but orders by most expensive coins first

    @Query("SELECT * FROM save_points WHERE coin_id = :coinId")
    suspend fun searchByCoinId(coinId: Int) : SavePoint?
    // Doubt this is useful for the user, maybe admins
}