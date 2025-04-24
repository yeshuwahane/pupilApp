package com.yeshuwahane.pupil.presentation.features.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.yeshuwahane.pupil.presentation.features.face.FaceDetectionEntry
import com.yeshuwahane.pupil.presentation.features.face.FaceDetectionScreen
import com.yeshuwahane.pupil.presentation.features.manga.MangaScreen
import com.yeshuwahane.pupil.presentation.navigation.NavigationRoutes


@Composable
fun HomeScreen(
    onMangaSelected: (String) -> Unit
) {
    val navController = rememberNavController()
    val items = listOf(
        NavigationRoutes.Manga to Pair("Manga", Icons.AutoMirrored.Filled.MenuBook),
        NavigationRoutes.Face to Pair("Face", Icons.Default.Face)
    )

    Scaffold(
        backgroundColor = Color.Black,
        bottomBar = {
            NavigationBar(containerColor = Color.Black) {
                val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
                items.forEach { (route, pair) ->
                    val (label, icon) = pair
                    NavigationBarItem(
                        selected = currentRoute == route,
                        onClick = {
                            navController.navigate(route) {
                                popUpTo(NavigationRoutes.Manga)
                                launchSingleTop = true
                            }
                        },
                        label = { Text(label, color = Color.White) },
                        icon = { Icon(imageVector = icon, contentDescription = label, tint = Color.White) }
                    )
                }
            }
        },
        modifier = Modifier
            .systemBarsPadding()
            .background(Color.Black)
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = NavigationRoutes.Manga,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(NavigationRoutes.Manga) {
                MangaScreen(onItemClick = onMangaSelected)
            }
            composable(NavigationRoutes.Face) {
                FaceDetectionEntry()
            }
        }
    }
}
