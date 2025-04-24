package com.yeshuwahane.pupil.domain.usecase

import com.yeshuwahane.pupil.data.local.MangaDao
import com.yeshuwahane.pupil.data.mapper.toDomain
import com.yeshuwahane.pupil.data.mapper.toEntity
import com.yeshuwahane.pupil.data.mapper.toUi
import com.yeshuwahane.pupil.data.utils.DataResource
import com.yeshuwahane.pupil.domain.model.MangaModel
import com.yeshuwahane.pupil.domain.model.MangaDetailModel
import com.yeshuwahane.pupil.domain.repository.MangaRepository
import javax.inject.Inject




class MangaUseCase @Inject constructor(
    private val repository: MangaRepository,
    private val dao: MangaDao
) {

    suspend fun fetchManga(page: Int): DataResource<List<MangaModel>> {
        val result = repository.fetchManga(page)

        if (result.isSuccess()) {
            result.data?.let { mangaList ->
                if (page == 1) dao.clearManga()
                dao.insertAll(mangaList.map { it.toEntity() })
            }
        }

        return result
    }

    suspend fun fetchCachedManga(): List<MangaModel> {
        return dao.getAllManga().map { it.toDomain() }
    }


    suspend fun fetchMangaDetail(id: String): DataResource<MangaDetailModel> {
        val result = repository.getMangaById(id)

        return if (result.isSuccess()) {
            result.data?.let {
                dao.insertDetail(it.toEntity())
                DataResource.success(it)
            } ?: DataResource.error(Throwable("No data"), null)
        } else {
            val cached = dao.getMangaDetailById(id)
            if (cached != null) {
                DataResource.success(cached.toUi())
            } else {
                DataResource.error(result.error, null)
            }
        }
    }

}
