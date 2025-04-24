package com.yeshuwahane.pupil.presentation.features.face

import android.app.Application
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageFormat
import android.graphics.Rect
import android.graphics.RectF
import android.graphics.YuvImage
import android.media.Image
import android.util.Log
import android.util.Size
import androidx.camera.core.AspectRatio
import androidx.camera.core.CameraSelector
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ProcessLifecycleOwner
import androidx.lifecycle.ViewModel
import com.google.mediapipe.framework.image.BitmapImageBuilder
import com.google.mediapipe.tasks.vision.facedetector.FaceDetector
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.io.ByteArrayOutputStream
import java.util.concurrent.Executors
import javax.inject.Inject




@HiltViewModel
@OptIn(ExperimentalGetImage::class)
class FaceDetectionViewModel @Inject constructor(
    private val faceDetector: FaceDetector,
    private val cameraProvider: ProcessCameraProvider
) : ViewModel() {

    private val _boundingBox = MutableStateFlow<RectF?>(null)
    val boundingBox: StateFlow<RectF?> = _boundingBox

    @androidx.annotation.OptIn(ExperimentalGetImage::class)
    fun bindCamera(previewView: PreviewView, lifecycleOwner: LifecycleOwner) {
        val preview = Preview.Builder().build().apply {
            setSurfaceProvider(previewView.surfaceProvider)
        }

        val analysis = ImageAnalysis.Builder()
            .setTargetResolution(Size(640, 480)) // Preferred over deprecated methods
            .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
            .build()

        analysis.setAnalyzer(Executors.newSingleThreadExecutor()) { imageProxy ->
            val mediaImage = imageProxy.image ?: return@setAnalyzer

            val bitmap = mediaImage.toBitmap()
            val mpImage = BitmapImageBuilder(bitmap).build()

            try {
                val result = faceDetector.detect(mpImage)
                val face = result.detections().firstOrNull()?.boundingBox()

                face?.let { box ->
                    val scaleX = previewView.width / mpImage.width.toFloat()
                    val scaleY = previewView.height / mpImage.height.toFloat()

                    _boundingBox.value = RectF(
                        box.left * scaleX,
                        box.top * scaleY,
                        box.right * scaleX,
                        box.bottom * scaleY
                    )
                } ?: run {
                    _boundingBox.value = null
                }

            } catch (e: Exception) {
                _boundingBox.value = null
            } finally {
                imageProxy.close()
            }
        }

        val cameraSelector = CameraSelector.DEFAULT_FRONT_CAMERA
        try {
            cameraProvider.unbindAll()
            cameraProvider.bindToLifecycle(
                lifecycleOwner,
                cameraSelector,
                preview,
                analysis
            )
        } catch (e: Exception) {
            Log.e("CameraBind", "Failed to bind camera", e)
        }
    }
}

fun Image.toBitmap(): Bitmap {
    val yBuffer = planes[0].buffer
    val uBuffer = planes[1].buffer
    val vBuffer = planes[2].buffer

    val ySize = yBuffer.remaining()
    val uSize = uBuffer.remaining()
    val vSize = vBuffer.remaining()

    val nv21 = ByteArray(ySize + uSize + vSize)
    yBuffer.get(nv21, 0, ySize)
    vBuffer.get(nv21, ySize, vSize)
    uBuffer.get(nv21, ySize + vSize, uSize)

    val yuvImage = YuvImage(nv21, ImageFormat.NV21, width, height, null)
    val out = ByteArrayOutputStream()
    yuvImage.compressToJpeg(Rect(0, 0, width, height), 100, out)
    val jpegBytes = out.toByteArray()
    return BitmapFactory.decodeByteArray(jpegBytes, 0, jpegBytes.size)
}
