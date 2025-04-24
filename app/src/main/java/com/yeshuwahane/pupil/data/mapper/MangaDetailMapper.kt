package com.yeshuwahane.pupil.data.mapper

import com.yeshuwahane.pupil.data.local.MangaDetailEntity
import com.yeshuwahane.pupil.data.remote.dto.MangaDetailResponse
import com.yeshuwahane.pupil.domain.model.MangaDetailModel


// Manga Detail Response -> UI state
fun MangaDetailResponse.Data.toUi(): MangaDetailModel {
    return MangaDetailModel(
        id = id,
        title = title,
        subTitle = subTitle,
        authors = authors.joinToString(", "),
        summary = summary,
        genres = genres.joinToString(", "),
        thumb = thumb,
        status = status,
        type = type,
        createAt = createAt,
        updatedAt = updateAt,
        nsfw = nsfw,
        totalChapter = totalChapter
    )
}


fun MangaDetailModel.toEntity() = MangaDetailEntity(
    id = id,
    title = title,
    subtitle = subTitle,
    summary = summary,
    genres = genres,
    authors = authors,
    thumb = thumb,
    status = status,
    type = type,
    createAt = createAt,
    updatedAt = updatedAt,
    nsfw = nsfw,
    totalChapter = totalChapter
)

fun MangaDetailEntity.toUi() = MangaDetailModel(
    id = id,
    title = title,
    subTitle = subtitle,
    summary = summary,
    genres = genres,
    authors = authors,
    thumb = thumb,
    status = status,
    type = type,
    createAt = createAt,
    updatedAt = updatedAt,
    nsfw = nsfw,
    totalChapter = totalChapter
)


