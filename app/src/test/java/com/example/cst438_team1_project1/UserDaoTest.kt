package com.example.cst438_team1_project1

import android.content.Context
import androidx.room3.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import androidx.test.core.app.ApplicationProvider
import com.example.cst438_team1_project1.data.AppDatabase
import com.example.cst438_team1_project1.data.Dao.UserDao
import com.example.cst438_team1_project1.data.entity.User
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test


class UserDaoTest {
    private lateinit var userDao: UserDao
    private lateinit var db: AppDatabase

    @Before
    fun setup(){
        db = Room.inMemoryDatabaseBuilder<AppDatabase>().setDriver(BundledSQLiteDriver()).build()
        userDao = db.userDao()
    }

    @After
    fun teardown(){
        db.close()
    }

    @Test
    fun insertUserTest() = runTest {
        val user = User(username = "bloop1020", password = "password1")
        val insertedId = userDao.insertUser(user).toInt()
        val retrievedUser = userDao.findByUsername("bloop1020")
        assert(retrievedUser != null)
        assertEquals(insertedId, retrievedUser?.userId)
    }

    @Test
    fun updateUsernameTest() = runTest {
        val user = User(username = "bloop1020", password = "password1")
        val insertedId = userDao.insertUser(user).toInt()

        userDao.updateUsername(insertedId, "newUSERNAME")
        val updatedUser = userDao.findByUsername("newUSERNAME")
        assert(updatedUser != null)
        assertEquals("newUSERNAME", updatedUser?.username)
    }
}