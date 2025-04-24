package com.yeshuwahane.pupil.presentation.features.manga

import com.yeshuwahane.pupil.data.utils.DataResource
import com.yeshuwahane.pupil.domain.model.MangaModel


data class MangaUiState(
    val mangaModelResourceState: DataResource<List<MangaModel>> = DataResource.initial()
)


