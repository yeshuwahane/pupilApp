package com.yeshuwahane.pupil.presentation.features.face

import android.graphics.RectF
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner





@Composable
fun FaceDetectionEntry() {
    val cameraPermissionGranted = rememberCameraPermission()

    if (cameraPermissionGranted.value) {
        FaceDetectionScreen()
    } else {
        // Optionally show loading/permission prompt state here
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Waiting for camera permission...")
        }
    }
}



@Composable
fun FaceDetectionScreen(modifier: Modifier = Modifier) {
    val viewModel = hiltViewModel<FaceDetectionViewModel>()
    val faceBox by viewModel.boundingBox.collectAsState()
    val lifecycleOwner = LocalLifecycleOwner.current

    Box(modifier = Modifier.fillMaxSize()) {
        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = { context ->
                val previewView = PreviewView(context).apply {
                    scaleType = PreviewView.ScaleType.FILL_CENTER
                }
                viewModel.bindCamera(previewView, lifecycleOwner)
                previewView
            }
        )

        Canvas(modifier = Modifier.fillMaxSize()) {
            val refBoxWidth = size.width * 0.6f
            val refBoxHeight = size.height * 0.4f
            val refLeft = (size.width - refBoxWidth) / 2f
            val refTop = (size.height - refBoxHeight) / 2f
            val refRect = RectF(refLeft, refTop, refLeft + refBoxWidth, refTop + refBoxHeight)

            val isInside = faceBox?.let {
                it.left >= refRect.left && it.top >= refRect.top &&
                        it.right <= refRect.right && it.bottom <= refRect.bottom
            } == true

            // Static reference box (gray -> green when matched)
            drawRect(
                color = if (isInside) Color.Green else Color.Gray.copy(alpha = 0.5f),
                topLeft = Offset(refRect.left, refRect.top),
                size = Size(refRect.width(), refRect.height()),
                style = Stroke(width = 4.dp.toPx())
            )

//            // Only one red box when face is detected
//            faceBox?.let {
//                drawRect(
//                    color = Color.Red,
//                    topLeft = Offset(it.left, it.top),
//                    size = Size(it.width(), it.height()),
//                    style = Stroke(width = 4.dp.toPx())
//                )
//            }
        }
    }
}
