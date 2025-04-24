package com.yeshuwahane.pupil.data.repositoryimpl

import com.yeshuwahane.pupil.data.local.MangaDao
import com.yeshuwahane.pupil.data.local.MangaDetailEntity
import com.yeshuwahane.pupil.data.mapper.toDomain
import com.yeshuwahane.pupil.data.mapper.toEntity
import com.yeshuwahane.pupil.data.mapper.toUi
import com.yeshuwahane.pupil.data.remote.api.MangaApi
import com.yeshuwahane.pupil.data.utils.DataResource
import com.yeshuwahane.pupil.data.utils.safeApiCall
import com.yeshuwahane.pupil.domain.model.MangaModel
import com.yeshuwahane.pupil.domain.model.MangaDetailModel
import com.yeshuwahane.pupil.domain.repository.MangaRepository
import javax.inject.Inject



class MangaRepositoryImpl @Inject constructor(
    private val api : MangaApi,
    private val dao : MangaDao
): MangaRepository {
    override suspend fun fetchManga(page: Int): DataResource<List<MangaModel>> {
        return safeApiCall(
            apiCall = { api.fetchManga(page) },
            mapper = { response ->
                response.data.map { it.toDomain() }
            }
        )
    }




    override suspend fun getCachedManga(): List<MangaModel> {
        return dao.getAllManga().map { it.toDomain() }
    }

    override suspend fun cacheManga(mangaModelList: List<MangaModel>) {
        dao.insertAll(mangaModelList.map { it.toEntity() })
    }

    override suspend fun getMangaById(id: String):  DataResource<MangaDetailModel> {
        return safeApiCall(
            apiCall = { api.fetchMangaDetail(id) },
            mapper = { response -> response.data.toUi() } // ✅ use `data` inside response
        )
    }

    override suspend fun cacheMangaDetail(manga: MangaDetailEntity) {
        dao.insertDetail(manga)
    }

    override suspend fun getCachedMangaDetail(id: String): MangaDetailEntity? {
        return dao.getMangaDetailById(id)
    }
}