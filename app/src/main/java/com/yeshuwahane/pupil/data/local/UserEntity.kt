package com.yeshuwahane.pupil.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "user_table")
data class UserEntity(
    @PrimaryKey val email: String,
    val password: String
)