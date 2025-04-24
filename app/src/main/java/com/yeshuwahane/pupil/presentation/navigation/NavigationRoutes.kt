package com.yeshuwahane.pupil.presentation.navigation








object NavigationRoutes {
    const val SignIn = "sign_in"
    const val Home = "home"

    const val Manga = "manga"
    const val Face = "face"

    const val MangaDetail = "manga_detail/{id}"

    fun mangaDetailRoute(id: String) = "manga_detail/$id"
}
