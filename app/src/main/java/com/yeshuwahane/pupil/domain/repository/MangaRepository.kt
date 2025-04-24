package com.yeshuwahane.pupil.domain.repository

import com.yeshuwahane.pupil.data.local.MangaDetailEntity
import com.yeshuwahane.pupil.data.utils.DataResource
import com.yeshuwahane.pupil.domain.model.MangaModel
import com.yeshuwahane.pupil.domain.model.MangaDetailModel


interface MangaRepository {
    suspend fun fetchManga(page: Int): DataResource<List<MangaModel>>
    suspend fun getCachedManga(): List<MangaModel>
    suspend fun cacheManga(mangaModelList: List<MangaModel>)
    suspend fun getMangaById(id: String): DataResource<MangaDetailModel>
    suspend fun cacheMangaDetail(manga: MangaDetailEntity)
    suspend fun getCachedMangaDetail(id: String): MangaDetailEntity?

}