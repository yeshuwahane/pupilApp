package com.yeshuwahane.pupil.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "manga_table")
data class MangaEntity(
    @PrimaryKey val id: String,
    val title: String,
    val subTitle: String,
    val authors: String,
    val genres: String,
    val summary: String,
    val thumb: String,
    val status: String,
    val totalChapter: Int,
    val type: String,
    val nsfw: Boolean
)
