package com.example.cst438_team1_project1.data.entity

import androidx.room3.ColumnInfo
import androidx.room3.Entity
import androidx.room3.ForeignKey
import androidx.room3.PrimaryKey

/* Example Foreign Key
    ForeignKey(
        entity = Customer::class,
        parentColumns = arrayOf("customer_id"),
        childColumns = arrayOf("customer_id"),
        onUpdate = ForeignKey.CASCADE,
        onDelete = ForeignKey.CASCADE
    )
 */

@Entity(
    tableName = "save_points",
    foreignKeys = [
        ForeignKey(
            entity = CryptoCoin::class,
            parentColumns = ["coin_id"],
            childColumns = ["coin_id"],
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.CASCADE
        ),
        // TODO: change User entity property from "id" to "user_id"
        ForeignKey(
            entity = User::class,
            parentColumns = ["id"],
            childColumns = ["user_id"],
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.CASCADE
        ),
    ]
)
data class SavePoint (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "saved_point_id")
    val savedPointId: Int,
    @ColumnInfo(name = "coin_id")
    val coinId: Int,
    @ColumnInfo(name = "user_id")
    var userId: Int,
    @ColumnInfo(name = "price_usd")
    var priceUSD: Int
)