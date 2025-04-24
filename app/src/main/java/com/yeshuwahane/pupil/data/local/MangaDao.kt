package com.yeshuwahane.pupil.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface MangaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(mangaList: List<MangaEntity>)

    @Query("SELECT * FROM manga_table")
    suspend fun getAllManga(): List<MangaEntity>

    @Query("DELETE FROM manga_table")
    suspend fun clearManga()

    // NEW for detail
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDetail(manga: MangaDetailEntity)

    @Query("SELECT * FROM manga_detail_table WHERE id = :id LIMIT 1")
    suspend fun getMangaDetailById(id: String): MangaDetailEntity?

}