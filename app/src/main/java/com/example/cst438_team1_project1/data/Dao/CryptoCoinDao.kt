package com.example.cst438_team1_project1.data.Dao

import androidx.room3.Dao
import androidx.room3.Delete
import androidx.room3.Insert
import androidx.room3.Query
import androidx.room3.Update
import com.example.cst438_team1_project1.data.entity.CryptoCoin

@Dao
interface CryptoCoinDao {
    @Insert
    suspend fun insertCoin(coin: CryptoCoin)
    // Equivalent to "INSERT INTO table (..., ...) VALUES(..., ...)"

    @Delete
    suspend fun deleteCoin(coin: CryptoCoin)
    // Easier alternative to delete query

    @Update
    suspend fun updateCoin(coin: CryptoCoin)
    // Not needed as coins are static... but did it anyway

    @Query("SELECT * FROM crypto_coins WHERE coin_name = :coinName LIMIT 1")
    suspend fun findByName(coinName: String) : CryptoCoin?
    // Finds a coin based on full length name

    @Query("SELECT * FROM crypto_coins WHERE coin_ticker = :coinTicker LIMIT 1")
    suspend fun findByTicker(coinTicker: String) : CryptoCoin?
    // Finds a coin based on the ticker (abbreviated name)

    @Query("SELECT * FROM crypto_coins WHERE coin_id = :coinId LIMIT 1")
    suspend fun findById(coinId: Int) : CryptoCoin?
    // Finds a coin by specific ID

    @Query("SELECT * FROM crypto_coins WHERE coin_name LIKE '%' || :coinName || '%'")
    suspend fun searchByName(coinName: String) : List<CryptoCoin>
    // Finds a coins by string, can be used for search bars as autofill
    // Like is used for case-insensitive string pattern matching
    // % = ignore all characters before/after   || = string concatenation
}