package com.yeshuwahane.pupil.presentation.features.manga.manga_details

import com.yeshuwahane.pupil.data.utils.DataResource
import com.yeshuwahane.pupil.domain.model.MangaDetailModel


data class MangaDetailUiState(
    val mangaDetailResourceState: DataResource<MangaDetailModel> = DataResource.initial()
)


