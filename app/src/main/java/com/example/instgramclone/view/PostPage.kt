package com.example.instgramclone.view

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.AlertDialogDefaults.containerColor
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItemDefaults.contentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.instgramclone.R
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.storage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.reflect.Modifier

@Composable
fun PostPage(navController: NavController) {
    var photoDescription by remember { mutableStateOf("") }
    var uri by remember { mutableStateOf<Uri?>(null) }
    var mediaSelected by remember { mutableStateOf(false) }
    var auth:FirebaseAuth
    var firestore : FirebaseFirestore
    var storage : FirebaseStorage
    auth = Firebase.auth
    firestore = Firebase.firestore
    storage = Firebase.storage
    Scaffold(){ innerPadding ->
        Column (
            modifier = androidx.compose.ui.Modifier
                .padding(innerPadding),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            val selectedTabIndex = remember { mutableStateOf(3)}
            TabRow(
                selectedTabIndex = selectedTabIndex.value,
                containerColor = Color.White,
                contentColor = colorResource(id = R.color.fb_blue),
            ) {
                Tab(selected = selectedTabIndex.value == 0,
                    onClick = {
                        selectedTabIndex.value = 0
                        mediaSelected = false},
                    text = { Text(text = "Post")})
                Tab(selected = selectedTabIndex.value == 1,
                    onClick = {
                        selectedTabIndex.value = 1
                        mediaSelected = false},
                    text = { Text(text = "Story")})
                Tab(selected = selectedTabIndex.value == 2,
                    onClick = {
                        selectedTabIndex.value = 2
                        mediaSelected = false},
                    text = { Text(text = "Reels")})
            }
            Box(modifier = androidx.compose.ui.Modifier.weight(1f)) {
                if (mediaSelected) {
                    if (selectedTabIndex.value == 2) {
                        uri?.let { videoUri ->
                            ExoPlayerView(uri = videoUri.toString())
                        }
                    } else {
                        uri?.let { imageUri ->
                            AsyncImage(
                                model = imageUri,
                                contentDescription = null,
                                modifier = androidx.compose.ui.Modifier.fillMaxSize()
                            )
                        }
                    }
                }
            }
            OutlinedTextField(
                value = photoDescription,
                onValueChange = {photoDescription = it},
                label = { Text(text = "Photo Description")},
                modifier = androidx.compose.ui.Modifier
                    .fillMaxWidth()
                    .padding(all = 15.dp))
            Button(onClick = {}, colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.fb_blue),
                    contentColor = Color.White
            )) {
                Text(text = "SHARE", fontSize = 16.sp)
            }
            Spacer(modifier = androidx.compose.ui.Modifier.height(40.dp))
            val singlePhotoPicker = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.PickVisualMedia(),
                onResult = { selectedUri ->
                    uri = selectedUri
                    mediaSelected = true
                })

            if (!mediaSelected) {
                when (selectedTabIndex.value) {
                    0, 1 -> {
                        singlePhotoPicker.launch(
                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                        )
                    }
                    2 -> {
                        singlePhotoPicker.launch(
                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.VideoOnly)
                        )
                    }
                }

            }
        }
    }
}





