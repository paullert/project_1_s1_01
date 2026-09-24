package com.example.cst438_team1_project1

import androidx.room3.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.example.cst438_team1_project1.data.AppDatabase
import com.example.cst438_team1_project1.data.Dao.UserDao
import com.example.cst438_team1_project1.data.entity.User
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNull
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
        userDao.insertUser(user)

        val savedUser = userDao.findByUsername("bloop1020")
        assertEquals("bloop1020", savedUser?.username)
        assertEquals("password1", savedUser?.password)
    }

    @Test
    fun findByUsernameTest() = runTest {
        val user = User(username = "bloop1020", password = "password1")
        userDao.insertUser(user)
        val foundUser = userDao.findByUsername("bloop1020")
        assertEquals("bloop1020", foundUser?.username)
        assertEquals("password1", foundUser?.password)
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

    @Test
    fun updatePasswordTest() = runTest {
        val user = User(username = "bloop1020", password = "password1")
        val insertedId = userDao.insertUser(user).toInt()

        userDao.updatePassword(insertedId, "newPASSWORD")
        val updatedUser = userDao.findByUsername("bloop1020")
        assert(updatedUser != null)
        assertEquals("newPASSWORD", updatedUser?.password)
    }

    @Test
    fun deleteUserTest() = runTest {
        val user = User(username = "bloop1020", password = "password1")
       val insertedId = userDao.insertUser(user).toInt()

        val savedUser = user.copy(userId = insertedId)
        userDao.deleteUser(savedUser)

        val deletedUser = userDao.findByUsername("bloop1020")
        assertNull(deletedUser)
    }
}
