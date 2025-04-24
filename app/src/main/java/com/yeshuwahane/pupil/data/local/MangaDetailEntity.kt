package com.yeshuwahane.pupil.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "manga_detail_table")
data class MangaDetailEntity(
    @PrimaryKey val id: String,
    val title: String,
    val subtitle: String,
    val summary: String,
    val genres: String,
    val authors: String,
    val thumb: String,
    val status: String,
    val type: String,
    val createAt: Long,
    val nsfw: Boolean,
    val totalChapter: Int,
    val updatedAt: Long
)