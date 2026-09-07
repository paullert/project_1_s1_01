package com.example.cst438_team1_project1.data.entity

import androidx.room3.ColumnInfo
import androidx.room3.Entity
import androidx.room3.PrimaryKey

@Entity(tableName = "crypto_coins")
data class CryptoCoin (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "coin_id")
    val coinId: Int,
    @ColumnInfo(name = "coin_name")
    val coinName: String,
    @ColumnInfo(name = "coin_ticker")
    val coinTicker: String,
    @ColumnInfo(name = "coin_image")
    val coinImage: String
)