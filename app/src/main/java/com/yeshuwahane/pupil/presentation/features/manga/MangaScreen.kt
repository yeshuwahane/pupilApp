package com.yeshuwahane.pupil.presentation.features.manga

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import coil3.compose.AsyncImagePainter
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.yeshuwahane.pupil.domain.model.MangaModel
import com.yeshuwahane.pupil.presentation.utils.shimmerLoadingAnimation




@Composable
fun MangaScreen(
    onItemClick: (String) -> Unit
) {
    val viewModel = hiltViewModel<MangaViewModel>()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val listState = rememberLazyGridState()

    LaunchedEffect(Unit) {
        if (uiState.mangaModelResourceState.data.isNullOrEmpty()) {
            viewModel.loadManga()
        }
    }


    LaunchedEffect(listState) {
        snapshotFlow { listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
            .collect { lastVisibleIndex ->
                val totalItems = listState.layoutInfo.totalItemsCount
                if (lastVisibleIndex != null && lastVisibleIndex >= totalItems - 3) {
                    viewModel.loadManga()
                }
            }
    }


    Box(modifier = Modifier.fillMaxSize().background(Color.Black)) {
        val data = uiState.mangaModelResourceState.data.orEmpty()

        when {
            uiState.mangaModelResourceState.isLoading() && data.isEmpty() -> MangaShimmerGrid()

            uiState.mangaModelResourceState.isError() && data.isEmpty() -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = uiState.mangaModelResourceState.error?.message ?: "Offline",
                        color = Color.Red
                    )
                    Spacer(Modifier.height(16.dp))
                    Button(onClick = { viewModel.retryLoad() }) {
                        Text("Retry")
                    }
                }
            }

            else -> MangaGrid(mangases = data, onItemClick = onItemClick, listState = listState)
        }


    }


}

@Composable
fun MangaGrid(
    mangases: List<MangaModel>,
    onItemClick: (String) -> Unit,
    listState: LazyGridState
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        state = listState,
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .background(Color.Black)
            .fillMaxSize()
    ) {
        items(mangases) { manga ->
            val context = LocalContext.current
            val painter = rememberAsyncImagePainter(
                model = ImageRequest.Builder(context)
                    .data(manga.thumb)
                    .crossfade(true)
                    .build()
            )

            // DO NOT wrap this in a flow or collect it
            val painterState by painter.state.collectAsState()


            val isLoading = painterState is AsyncImagePainter.State.Loading

            Box(
                modifier = Modifier
                    .aspectRatio(0.7f)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.DarkGray)
                    .then(
                        if (isLoading) Modifier.shimmerLoadingAnimation() else Modifier
                    )
                    .clickable { onItemClick(manga.id) }
            ) {
                Image(
                    painter = painter,
                    contentDescription = manga.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .matchParentSize()
                        .clip(RoundedCornerShape(8.dp))
                )
            }
        }
    }
}







@Composable
fun MangaShimmerGrid() {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        items(12) {
            Box(
                modifier = Modifier
                    .aspectRatio(0.7f)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.DarkGray.copy(alpha = 0.3f))
                    .shimmerLoadingAnimation()
            )
        }
    }
}
