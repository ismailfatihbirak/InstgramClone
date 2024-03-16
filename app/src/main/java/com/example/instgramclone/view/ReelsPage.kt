package com.example.instgramclone.view

import android.net.Uri
import androidx.compose.foundation.Image
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
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import androidx.navigation.NavController
import com.example.instgramclone.R

@Composable
fun ReelsPage(navController: NavController) {
    Scaffold(

    ){ innerPadding ->
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 30.dp).padding(innerPadding),
            contentAlignment = Alignment.BottomStart){
            ExoPlayerView(EXAMPLE_VIDEO_URI)
            Column (horizontalAlignment = Alignment.End,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 150.dp, end = 15.dp)){
                Column (horizontalAlignment = Alignment.CenterHorizontally){
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(imageVector = Icons.Filled.FavoriteBorder, contentDescription =""
                            ,modifier = Modifier.size(36.dp))
                    }
                    Text(text = "10 bin ")
                }
                Spacer(modifier = Modifier.height(10.dp))
                Column (horizontalAlignment = Alignment.CenterHorizontally){
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(painter = painterResource(id = R.drawable.comment_icon), contentDescription =""
                            ,modifier = Modifier.size(30.dp))
                    }
                    Text(text = "500")
                }
                Spacer(modifier = Modifier.height(10.dp))
                Column (horizontalAlignment = Alignment.CenterHorizontally){
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.Send, contentDescription =""
                            ,modifier = Modifier.size(32.dp))
                    }
                    Text(text = "5.000")
                }
            }
            Column {
                Row (modifier = Modifier.padding(start = 15.dp)){
                    Image(
                        painter = painterResource(id = R.drawable.ic_launcher_background),
                        contentDescription = "",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .clip(CircleShape)
                            .size(45.dp)
                    )
                    TextButton(onClick = { /*TODO*/ },colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent,
                        contentColor = Color.Black
                    )) {
                        Text(text = "pageName", fontSize = 16.sp)
                    }
                    OutlinedButton(onClick = { /*TODO*/ }, colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent,
                        contentColor = Color.Black
                    )) {
                        Text(text = "Takip Et")
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))
                Text(text = "Batı avustrulyada bulunan Hillier Gölü pembe...", modifier = Modifier.padding(start = 15.dp))
            }
        }
    }
        }




const val EXAMPLE_VIDEO_URI = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"

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
        exoPlayer.play()
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
