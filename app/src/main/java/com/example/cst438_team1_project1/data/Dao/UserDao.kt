package com.example.cst438_team1_project1.data.Dao

import androidx.room3.Dao
import androidx.room3.Delete
import androidx.room3.Insert
import androidx.room3.Query
import com.example.cst438_team1_project1.data.entity.User

/*
# This saves user, finds user or delets user.
# DAO's only job is to talk to the database.
# It knows how to save, find, update, and delete users.
 */

@Dao
interface UserDao {
    @Insert
    suspend fun insertUser(user: User): Long
    //^^ means the same as INSERT INTO USER (username, password) VALUES('user1','abc123')

    @Query("SELECT * FROM user WHERE username = :username LIMIT 1")
    suspend fun findByUsername(username: String) : User?
    //^^ find the user whose user name matched and it will return 1 user or if user dne it'll return null

    @Query("UPDATE user SET username = :newUsername WHERE id = :id")
    suspend fun updateUsername(id: Int, newUsername: String)

    @Query("UPDATE user SET password = :newPass WHERE id = :id")
    suspend fun updatePassword(id: Int, newPass: String)

    @Delete
    suspend fun deleteUser(user: User)
    //^^could also write a QUERY in sql but dont need it cuz room has delete
}