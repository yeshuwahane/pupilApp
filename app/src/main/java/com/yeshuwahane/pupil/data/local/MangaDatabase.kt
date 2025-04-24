package com.yeshuwahane.pupil.data.local

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(
    entities = [MangaEntity::class, UserEntity::class,MangaDetailEntity::class],
    version = 4,
    exportSchema = false
)
abstract class MangaDatabase : RoomDatabase() {
    abstract fun mangaDao(): MangaDao
    abstract fun authDao(): AuthDao
}