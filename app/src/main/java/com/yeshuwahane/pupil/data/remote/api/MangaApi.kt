package com.yeshuwahane.pupil.data.remote.api

import com.yeshuwahane.pupil.data.remote.dto.MangaDetailResponse
import com.yeshuwahane.pupil.data.remote.dto.MangaResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query




interface MangaApi {

    @GET("manga/fetch")
    @Headers(
        "x-rapidapi-key: e084a2f168mshf6ee3d3b9e3c118p124cf4jsnb4184248b2e1",
        "x-rapidapi-host: mangaverse-api.p.rapidapi.com"
    )
    suspend fun fetchManga(
        @Query("page") page: Int = 1,
        @Query("genres") genres: String = "Harem,Fantasy",
        @Query("nsfw") nsfw: Boolean = true,
        @Query("type") type: String = "all"
    ): MangaResponse

    @GET("manga")
    @Headers(
        "x-rapidapi-key: e084a2f168mshf6ee3d3b9e3c118p124cf4jsnb4184248b2e1",
        "x-rapidapi-host: mangaverse-api.p.rapidapi.com"
    )
    suspend fun fetchMangaDetail(@Query("id") id: String): MangaDetailResponse
}