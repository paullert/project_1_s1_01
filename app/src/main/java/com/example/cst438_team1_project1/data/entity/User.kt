package com.example.cst438_team1_project1.data.entity

import androidx.room3.Entity
import androidx.room3.PrimaryKey

/*
#This file represents one user (id, username, password)
    We don't need to create the table ourselves by doing
        CREATE TABLE User (
            id INTEGER PRIMARY KEY,
            username TEXT,
            password TEXT
        );
    Room sees @Entity and creates a table called user and generates
    SQL for us based on what we gave it
 */

@Entity
data class User (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val username: String,
    val password: String
)