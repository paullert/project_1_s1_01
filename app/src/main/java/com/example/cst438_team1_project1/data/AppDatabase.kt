package com.example.cst438_team1_project1.data

import android.content.Context
import androidx.room3.Database
import androidx.room3.Room
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

    //gemini helped with companion obj its needed for coroutine
    companion object {
        //volatile helps so multiple databases are created on different threads
        @Volatile
        private var INSTANCE: AppDatabase? = null
        //only AppDatabase can access this priv var. initially when app opened database is null

        fun getDatabase(context: Context): AppDatabase {
            if(INSTANCE != null){
                return INSTANCE!!
                //!! means like i promise this isn't null
            }

            //build database and save it in instane
            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,//room doesn't know which database to build so we tell it which one
                "userDatabase"
            ).build()

            return INSTANCE!!
        }
    }
}