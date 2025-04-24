package com.yeshuwahane.pupil.presentation.features.face

import android.Manifest
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat


@Composable
fun rememberCameraPermission(): State<Boolean> {
    val context = LocalContext.current
    val permissionState = remember { mutableStateOf(false) }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { granted ->
            permissionState.value = granted
            if (!granted) {
                Toast.makeText(context, "Camera permission is required.", Toast.LENGTH_LONG).show()
            }
        }
    )

    LaunchedEffect(Unit) {
        val granted = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED

        if (!granted) {
            permissionLauncher.launch(Manifest.permission.CAMERA)
        } else {
            permissionState.value = true
        }
    }

    return permissionState
}
