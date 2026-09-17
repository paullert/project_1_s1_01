package com.example.cst438_team1_project1.data.entity
import androidx.compose.runtime.snapshots.Snapshot
import androidx.room3.ColumnInfo
import androidx.room3.Entity
import androidx.room3.ForeignKey
import androidx.room3.PrimaryKey


//TODO:create an index that covers this column (userId)
//w: [ksp] userId column references a foreign key but it is not part of an index.
// This may trigger full table scans whenever parent table is modified
// so you are highly advised to create an index that covers this column.


//Foreign Key creates Relationship between SavePoint and User+CryptoCoin
@Entity(tableName = "crypto_save_points",
    foreignKeys = [
        ForeignKey(entity = User::class,
            parentColumns = ["userId"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE),
        ForeignKey(
            entity = CryptoCoin::class,
            parentColumns = ["coin_id"],
            childColumns = ["coinId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
    )
data class SavePoint (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "save_point_id")
    val savePointId: Int,
    val coinId: Int,
    val userId: Int,
    val valueSnapshot: String,
)
