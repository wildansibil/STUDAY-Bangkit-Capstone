package com.studay.app.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class UserEntity(
    @PrimaryKey val id: String,
    val email: String,
    val name: String,
    val password: String,  // Password bisa dienkripsi
    val token: String
)
