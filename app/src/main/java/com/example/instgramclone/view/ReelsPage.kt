package com.example.instgramclone.view

import android.net.Uri
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.FrameLayout
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.PagerSnapDistance
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.DisposableEffectResult
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.common.util.Log
import androidx.media3.common.util.UnstableApi
import androidx.media3.common.util.Util
import androidx.media3.datasource.DataSource
import androidx.media3.datasource.DefaultDataSourceFactory
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.SimpleExoPlayer
import androidx.media3.exoplayer.source.ProgressiveMediaSource
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.instgramclone.R
import com.example.instgramclone.viewmodel.ReelsPageViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive

@androidx.annotation.OptIn(UnstableApi::class)
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ReelsPage(navController: NavController,viewModel:ReelsPageViewModel) {
    viewModel.userListfun()
    viewModel.getReelsList()
    val userList = viewModel.usersList.observeAsState(listOf())
    val reelsList = viewModel.reelsList.observeAsState(listOf())
    val pagerState = rememberPagerState(pageCount = {reelsList.value.count()})

    VerticalPager(state = pagerState,
        modifier = Modifier.fillMaxSize()) { page ->

        var userIndex = 1

        DisposableEffect(reelsList.value.lastIndex){
            onDispose {
                userIndex++
            }
        }
        val user = userList.value[userIndex]
        val reels = reelsList.value[pagerState.currentPage]

        Box(modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 30.dp),
                contentAlignment = Alignment.BottomStart){

            ExoPlayerView(reels.video!!)

            Column (horizontalAlignment = Alignment.End,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 150.dp, end = 15.dp)){

                Column (horizontalAlignment = Alignment.CenterHorizontally){
                        IconButton(onClick = { /*TODO*/ }) {

                            Icon(imageVector = Icons.Filled.FavoriteBorder,
                                contentDescription =""
                                ,modifier = Modifier.size(36.dp)
                                ,tint = Color.White)
                        }
                        Text(text = "10 bin ",
                            color = Color.White)
                    }

                Spacer(modifier = Modifier.height(10.dp))

                Column (horizontalAlignment = Alignment.CenterHorizontally){

                        IconButton(onClick = {

                        }) {
                            Icon(painter = painterResource(id = R.drawable.comment_icon)
                                ,contentDescription =""
                                ,modifier = Modifier.size(30.dp)
                                ,tint = Color.White)
                        }

                        Text(text = "500",
                            color = Color.White)
                    }

                Spacer(modifier = Modifier.height(10.dp))

                Column (horizontalAlignment = Alignment.CenterHorizontally){

                        IconButton(onClick = { /*TODO*/ }) {
                            Icon(imageVector = Icons.AutoMirrored.Filled.Send
                                ,contentDescription =""
                                ,modifier = Modifier.size(32.dp)
                                ,tint = Color.White)
                        }

                        Text(text = "5.000",
                            color = Color.White)
                    }
                }
                Column {
                    Row (modifier = Modifier.padding(start = 15.dp)){

                        AsyncImage(
                            model = user.profilePhoto,
                            contentDescription = "",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .clip(CircleShape)
                                .size(45.dp))

                        TextButton(onClick = { /*TODO*/ },
                            colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent,
                            contentColor = Color.White
                        )) {
                            Text(text = user.userName!!,
                                fontSize = 16.sp)
                        }

                        OutlinedButton(onClick = { /*TODO*/ },
                            colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent,
                            contentColor = Color.White
                        )) {
                            Text(text = "Takip Et")
                        }

                    }
                    Spacer(modifier = Modifier.height(20.dp))

                    Text(text = reelsList.value[page].videoDescription!!,
                        modifier = Modifier.padding(start = 15.dp),
                        color = Color.White)
                }
            }

    }


}

@androidx.annotation.OptIn(UnstableApi::class)
@Composable
fun ExoPlayerView(uri: String) {
    val context = LocalContext.current
    val exoPlayer = remember { ExoPlayer.Builder(context).build() }
    val mediaSource = remember(uri) {
        MediaItem.fromUri(uri)
    }

    LaunchedEffect(mediaSource) {
        exoPlayer.setMediaItem(mediaSource)
        exoPlayer.prepare()
        exoPlayer.playWhenReady
    }

    DisposableEffect(Unit) {
        onDispose {
            exoPlayer.release()
        }
    }

    val playerView = remember {
        PlayerView(context).apply {
            player = exoPlayer
            useController = false
        }
    }

    AndroidView(
        factory = { playerView },
        modifier = Modifier
            .fillMaxSize()
            .clickable {
                if (exoPlayer.isPlaying) {
                    exoPlayer.pause()
                } else {
                    exoPlayer.play()
                }
            }
    )
}










