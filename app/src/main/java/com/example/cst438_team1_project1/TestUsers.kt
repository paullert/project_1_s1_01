package com.example.cst438_team1_project1.data

import com.example.cst438_team1_project1.data.entity.User

suspend fun createTestUsers(database: AppDatabase) {
    val userDao = database.userDao()

    if (userDao.findByUsername("testuser") == null) {
        userDao.insertUser(
            User(
                username = "testuser",
                password = "password123"
            )
        )
    }

    if (userDao.findByUsername("daniel") == null) {
        userDao.insertUser(
            User(
                username = "daniel",
                password = "daniel"
            )
        )
    }

    if (userDao.findByUsername("admin") == null) {
        userDao.insertUser(
            User(
                username = "admin",
                password = "admin123"
            )
        )
    }
}