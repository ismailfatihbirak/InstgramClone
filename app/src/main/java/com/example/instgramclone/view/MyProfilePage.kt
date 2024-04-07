package com.example.instgramclone.view

import android.graphics.Bitmap
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.SimpleExoPlayer
import androidx.media3.ui.PlayerView
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.instgramclone.R
import com.example.instgramclone.model.Post
import com.example.instgramclone.model.Reel
import com.example.instgramclone.model.User
import com.example.instgramclone.viewmodel.HomePageViewModel
import com.example.instgramclone.viewmodel.MyProfileViewModel
import com.google.android.mediahome.video.PreviewProgram
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyProfilePage(navController: NavController,viewModel: MyProfileViewModel) {
    lateinit var auth: FirebaseAuth
    auth = Firebase.auth
    var user1 by remember { mutableStateOf<User?>(null) }

    user1 = viewModel.user1.value
    LaunchedEffect(auth.currentUser) {
        auth.currentUser?.uid?.let { userId ->
            viewModel.myProfileInformation(userId)
        }
    }



    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = Color.White,
                    titleContentColor = Color.White,
                ),
                modifier = Modifier.height(50.dp),
                title = {
                    Row (verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Absolute.Left,
                        modifier = Modifier.fillMaxSize()){
                        Text(
                            text = user1?.userName ?: "Default Name",
                            fontSize = 20.sp,
                            color = Color.Black,
                            fontWeight = FontWeight.Medium)
                    }


                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Localized description",
                            Modifier.size(24.dp)
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { /* do something */ }) {
                        Icon(
                            imageVector = Icons.Filled.MoreVert,
                            contentDescription = "Localized description",
                            Modifier.size(28.dp)
                        )
                    }

                })
        },
        bottomBar = {
            fun navigateToScreen(destination: String) {
                navController.navigate(destination){
                    popUpTo("myprofilepage"){ inclusive = true}
                }
            }
            BottomNavigation(onItemClick = :: navigateToScreen)

        }
    ){ innerPadding ->
        Column (
            modifier = Modifier
                .padding(innerPadding)
        ){
            Row(verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = 10.dp)) {
                AsyncImage(
                    model = user1?.profilePhoto ?: "Default",
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(90.dp))
                Column (horizontalAlignment = Alignment.CenterHorizontally){
                    Text(text = "25",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Medium)
                    Text(text = "Posts",
                        fontSize = 12.sp)
                }
                Column (horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "500",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Medium)
                    Text(text = "Followers",
                        fontSize = 12.sp)
                }
                Column (horizontalAlignment = Alignment.CenterHorizontally){
                    Text(text = "300",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Medium)
                    Text(text = "Following",
                        fontSize = 12.sp)
                }

            }
            Text(text = user1?.name ?: "Default ",
                fontSize = 14.sp,
                modifier = Modifier.padding(start = 10.dp))
            Text(text = user1?.bio ?: "Default",
                fontSize = 14.sp,
                modifier = Modifier.padding(start = 10.dp))
            Spacer(modifier = Modifier.height(20.dp))
            Row (horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = 15.dp)){
                Button(onClick = { /*TODO*/ }, colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.tf_color),
                    contentColor = Color.Black
                ), modifier = Modifier.size(195.dp,35.dp)) {
                    Text(text = "Edit Profile", fontSize = 12.sp)
                }
                Button(onClick = { /*TODO*/ }, colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.tf_color),
                    contentColor = Color.Black
                ), modifier = Modifier.size(195.dp,35.dp)) {
                    Text(text = "Share Profile", fontSize = 12.sp)
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            MyProfileTabExample(
                photoList = user1?.posts ?: listOf(),
                reelList = user1?.reels ?: listOf(),
                viewModel
            )
        }

    }
}

@Composable
fun MyProfileTabExample(photoList : List<Post>,reelList: List<Reel>,viewModel: MyProfileViewModel) {
    val selectedTabIndex = remember { mutableStateOf(0) }

    Column {

        TabRow(
            selectedTabIndex = selectedTabIndex.value,
            containerColor = Color.White,
            contentColor = Color.Black
        ) {
            Tab(
                selected = selectedTabIndex.value == 0,
                onClick = { selectedTabIndex.value = 0 },
                icon = { Icon(painter = painterResource(id = R.drawable.tab_icon), contentDescription = "") }
            )
            Tab(
                selected = selectedTabIndex.value == 1,
                onClick = { selectedTabIndex.value = 1 },
                icon = { Icon(painter = painterResource(id = R.drawable.tab_icon_video), contentDescription = "") }
            )
        }

        when (selectedTabIndex.value) {
            0 -> {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(3)
                ) {
                    items(photoList.size){
                        val photo = photoList[it]
                        AsyncImage(
                            model = photo.photo ,
                            contentDescription = "",
                            modifier = Modifier
                                .size(138.dp)
                                .padding(bottom = 2.dp))
                    }
                }
            }
            1 -> {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    ) {
                    var previewBitmap = mutableStateOf<Bitmap?>(null)
                    items(reelList.size){
                        val reel = reelList[it]
                        previewBitmap.value =getVideoPreviewImage(reel.video.toString())
                        Log.e("prev","${previewBitmap}")
                        if (previewBitmap != null) {
                            previewBitmap.value?.let { it1 ->
                                Image(
                                    bitmap = it1.asImageBitmap(),
                                    contentDescription = null,
                                    contentScale = ContentScale.FillWidth
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
@Composable
fun getVideoPreviewImage(videoUrl: String): Bitmap? {
    val retriever = MediaMetadataRetriever()
    return try {
        retriever.setDataSource(videoUrl, HashMap<String, String>())
        retriever.frameAtTime
    } catch (e: Exception) {
        e.printStackTrace()
        null
    } finally {
        retriever.release()
    }
}


