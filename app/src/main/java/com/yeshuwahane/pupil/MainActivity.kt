package com.yeshuwahane.pupil

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.yeshuwahane.pupil.presentation.navigation.AppNavHost
import com.yeshuwahane.pupil.presentation.navigation.NavigationRoutes
import com.yeshuwahane.pupil.ui.theme.PupilTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val splashViewModel = hiltViewModel<SplashViewModel>()
            val isSignedIn by splashViewModel.isSignedIn.collectAsStateWithLifecycle()

            if (isSignedIn == null) {
                // Show splash or loading UI
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else {
                val navController = rememberNavController()
                AppNavHost(
                    navController = navController,
                    startDestination = if (isSignedIn == true)
                        NavigationRoutes.Home
                    else
                        NavigationRoutes.SignIn
                )
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PupilTheme {
        Greeting("Android")
    }
}