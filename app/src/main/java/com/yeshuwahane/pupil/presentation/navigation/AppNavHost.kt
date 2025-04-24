package com.yeshuwahane.pupil.presentation.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.yeshuwahane.pupil.presentation.features.auth.SignInScreen
import com.yeshuwahane.pupil.presentation.features.home.HomeScreen
import com.yeshuwahane.pupil.presentation.features.manga.manga_details.MangaDetailScreen


@Composable
fun AppNavHost(
    navController: NavHostController,
    startDestination: String
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
//            .padding(WindowInsets.systemBars.asPaddingValues())
            .systemBarsPadding() // ✅ Prevent UI from going under status bar
    ) {
        composable(NavigationRoutes.SignIn) {
            SignInScreen {
                navController.navigate(NavigationRoutes.Home) {
                    popUpTo(NavigationRoutes.SignIn) { inclusive = true }
                }
            }
        }

        composable(NavigationRoutes.Home) {
            HomeScreen(
                onMangaSelected = { mangaId ->
                    navController.navigate(NavigationRoutes.mangaDetailRoute(mangaId))
                }
            )
        }

        composable(NavigationRoutes.MangaDetail) { backStackEntry ->
            val mangaId = backStackEntry.arguments?.getString("id") ?: ""
            MangaDetailScreen(mangaId)
        }
    }
}