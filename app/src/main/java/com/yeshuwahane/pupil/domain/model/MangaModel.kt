package com.yeshuwahane.pupil.domain.model







data class MangaModel(
    val id: String,
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