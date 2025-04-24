package com.yeshuwahane.pupil.presentation.features.manga.manga_details

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import coil3.compose.AsyncImagePainter
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.yeshuwahane.pupil.presentation.features.manga.MangaViewModel
import com.yeshuwahane.pupil.presentation.utils.shimmerLoadingAnimation





@Composable
fun MangaDetailScreen(mangaId: String) {
    val viewModel = hiltViewModel<MangaViewModel>()
    val uiState by viewModel.detailUiState.collectAsStateWithLifecycle()
    val resource = uiState.mangaDetailResourceState
    val manga = uiState.mangaDetailResourceState.data

    LaunchedEffect(mangaId) {
        viewModel.loadMangaDetail(mangaId)
    }


    when {
        resource.isLoading() -> {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black)
                    .padding(16.dp)
            ) {
                // Star icon shimmer
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .clip(CircleShape)
                            .background(Color.DarkGray)
                            .shimmerLoadingAnimation()
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Image + text shimmer
                Row(
                    verticalAlignment = Alignment.Top,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Box(
                        modifier = Modifier
                            .size(width = 140.dp, height = 200.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color.DarkGray)
                            .shimmerLoadingAnimation()
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    Column {
                        Box(
                            modifier = Modifier
                                .height(20.dp)
                                .width(120.dp)
                                .clip(RoundedCornerShape(4.dp))
                                .background(Color.DarkGray)
                                .shimmerLoadingAnimation()
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Box(
                            modifier = Modifier
                                .height(16.dp)
                                .width(80.dp)
                                .clip(RoundedCornerShape(4.dp))
                                .background(Color.DarkGray)
                                .shimmerLoadingAnimation()
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Summary shimmer lines
                repeat(4) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(12.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .background(Color.DarkGray)
                            .shimmerLoadingAnimation()
                            .padding(vertical = 4.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }

        resource.isSuccess() -> {
            val context = LocalContext.current
            val painter = rememberAsyncImagePainter(
                model = ImageRequest.Builder(context)
                    .data(manga?.thumb)
                    .crossfade(true)
                    .build()
            )

            val painterState by painter.state.collectAsState()


            val isLoading = painterState is AsyncImagePainter.State.Loading

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black)
                    .padding(16.dp)
            ) {

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    IconButton(onClick = {}) {
                        Icon(
                            Icons.Default.Star,
                            contentDescription = "",
                            modifier = Modifier.padding(5.dp),
                            tint = Color.White
                        )
                    }
                }

                Row(
                    verticalAlignment = Alignment.Top,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Box(
                        modifier = Modifier
                            .width(140.dp)
                            .height(200.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color.DarkGray)
                            .then(if (isLoading) Modifier.shimmerLoadingAnimation() else Modifier)
                            .padding(5.dp)
                    ) {
                        Image(
                            painter = painter,
                            contentDescription = manga?.title,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .matchParentSize()
                                .clip(RoundedCornerShape(12.dp))
                        )
                    }

                    Column(modifier = Modifier.padding(5.dp)) {
                        Text(
                            text = manga?.title.orEmpty(),
                            color = Color.White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                        )
                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = manga?.subTitle.orEmpty(),
                            color = Color.White,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Light,
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = manga?.summary.orEmpty(),
                    color = Color.White,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                )
            }
        }


        resource.isError() -> {
            Text("Error: ${resource.error?.message}")
        }
    }


}
