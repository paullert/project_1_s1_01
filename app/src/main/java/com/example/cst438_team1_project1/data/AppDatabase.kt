package com.example.cst438_team1_project1.data

import androidx.room3.Database
import androidx.room3.RoomDatabase
import com.example.cst438_team1_project1.data.entity.User
import com.example.cst438_team1_project1.data.Dao.UserDao

/*
This is the database itseld. Holds all tables.
Database manages all of the entities/tables and gives us access to the DAOs.
We will later also make a crypto table.
How to make a database can all be found in:
https://developer.android.com/training/data-storage/room
 */

@Database(
    entities = [User::class],
    version = 1
)

abstract class AppDatabase : RoomDatabase(){
    abstract fun userDao(): UserDao
}