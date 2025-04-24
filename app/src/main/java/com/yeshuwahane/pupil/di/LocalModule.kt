package com.yeshuwahane.pupil.di

import android.content.Context
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.room.Room
import com.google.mediapipe.tasks.core.BaseOptions
import com.google.mediapipe.tasks.vision.core.RunningMode
import com.google.mediapipe.tasks.vision.facedetector.FaceDetector
import com.yeshuwahane.pupil.data.local.AuthDao
import com.yeshuwahane.pupil.data.local.MangaDao
import com.yeshuwahane.pupil.data.local.MangaDatabase
import com.yeshuwahane.pupil.data.utils.SessionManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object LocalModule {

    @Provides
    @Singleton
    fun provideMangaDatabase(@ApplicationContext context: Context): MangaDatabase =
        Room.databaseBuilder(
                context,
                MangaDatabase::class.java,
                "manga_db"
            ).fallbackToDestructiveMigration(false).build()

    @Provides
    @Singleton
    fun provideMangaDao(db: MangaDatabase): MangaDao = db.mangaDao()

    @Provides
    @Singleton
    fun provideAuthDao(db: MangaDatabase): AuthDao = db.authDao()

    @Provides
    @Singleton
    fun provideSessionManager(@ApplicationContext context: Context): SessionManager =
        SessionManager(context)


    @Provides
    @Singleton
    fun provideFaceDetector(@ApplicationContext context: Context): FaceDetector {
        val baseOptions = BaseOptions.builder()
//            .setModelAssetPath("face_detection_short_range.tflite") // ✅ Your filename here
            .setModelAssetPath("blaze_face_short_range.tflite") // ✅ Your filename here
            .build()

        val options = FaceDetector.FaceDetectorOptions.builder()
            .setBaseOptions(baseOptions)
            .setMinDetectionConfidence(0.5f)
            .setRunningMode(RunningMode.IMAGE)
            .build()

        return FaceDetector.createFromOptions(context, options)
    }


    @Provides
    @Singleton
    fun provideCameraProvider(@ApplicationContext context: Context): ProcessCameraProvider {
        return ProcessCameraProvider.getInstance(context).get()
    }




}