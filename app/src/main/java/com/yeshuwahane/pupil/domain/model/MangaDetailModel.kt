package com.yeshuwahane.pupil.domain.model



data class MangaDetailModel(
    val id: String,
    val title: String,
    val summary: String,
    val genres: String,
    val authors: String,
    val thumb: String,
    val subTitle: String,
    val status: String,
    val type: String,
    val createAt: Long,
    val nsfw: Boolean,
    val totalChapter: Int,
    val updatedAt: Long
)