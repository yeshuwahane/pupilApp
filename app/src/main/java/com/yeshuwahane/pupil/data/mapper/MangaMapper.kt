package com.yeshuwahane.pupil.data.mapper

import com.yeshuwahane.pupil.data.local.MangaEntity
import com.yeshuwahane.pupil.data.remote.dto.MangaResponse
import com.yeshuwahane.pupil.domain.model.MangaModel


// Network DTO -> Domain
fun MangaResponse.Data.toDomain(): MangaModel = MangaModel(
    id = id,
    title = title,
    subTitle = subTitle,
    authors = authors.joinToString(),
    genres = genres.joinToString(),
    summary = summary,
    thumb = thumb,
    status = status,
    totalChapter = totalChapter,
    type = type,
    nsfw = nsfw
)

// Domain -> Entity
fun MangaModel.toEntity(): MangaEntity = MangaEntity(
    id = id,
    title = title,
    subTitle = subTitle,
    authors = authors,
    genres = genres,
    summary = summary,
    thumb = thumb,
    status = status,
    totalChapter = totalChapter,
    type = type,
    nsfw = nsfw
)

// Entity -> Domain
fun MangaEntity.toDomain(): MangaModel = MangaModel(
    id = id,
    title = title,
    subTitle = subTitle,
    authors = authors,
    genres = genres,
    summary = summary,
    thumb = thumb,
    status = status,
    totalChapter = totalChapter,
    type = type,
    nsfw = nsfw
)

