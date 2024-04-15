package com.example.instgramclone.view

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.example.instgramclone.model.Post
import com.example.instgramclone.model.User
import com.example.instgramclone.viewmodel.PostPageViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import io.grpc.android.BuildConfig
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Objects


@Composable
fun CameraPage(viewModel: PostPageViewModel) {
    var auth: FirebaseAuth
    auth = Firebase.auth
    var photoDescription by remember { mutableStateOf("") }
    val downloadUri = viewModel.downloadUri.observeAsState().value
    val authId = auth.currentUser?.uid
    var mediaSelected by remember { mutableStateOf(false) }

    val newPost = Post(downloadUri, null, null, photoDescription)
    val newStory = User(authId,null,null,null,null,null,null,downloadUri,null,null)

    val context = LocalContext.current
    val file = context.createImageFile()
    val uri = FileProvider.getUriForFile(
        Objects.requireNonNull(context),
        context.packageName + ".provider", file
    )

    var capturedImageUri by remember {
        mutableStateOf<Uri>(Uri.EMPTY)
    }

    LaunchedEffect(mediaSelected){
        if (capturedImageUri.path?.isNotEmpty() == true) {
            viewModel.uploadMedia(capturedImageUri,"postmedia")
        }
    }

    val cameraLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) {
            capturedImageUri = uri
            mediaSelected=true
        }

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) {
        if (it) {
            Toast.makeText(context, "Permission Granted", Toast.LENGTH_SHORT).show()
            cameraLauncher.launch(uri)
        } else {
            Toast.makeText(context, "Permission Denied", Toast.LENGTH_SHORT).show()
        }
    }

    Column(
        Modifier
            .fillMaxSize()
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedButton(onClick = {
            val permissionCheckResult =
                ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)
            if (permissionCheckResult == PackageManager.PERMISSION_GRANTED) {
                cameraLauncher.launch(uri)
            } else {
                // Request a permission
                permissionLauncher.launch(Manifest.permission.CAMERA)
            }
        }) {
            Text(text = "Open The Camera")
        }
    }
    if (capturedImageUri.path?.isNotEmpty() == true) {
        Image(
            modifier = Modifier
                .padding(16.dp, 8.dp),
            painter = rememberAsyncImagePainter(capturedImageUri),
            contentDescription = null
        )
    }
    Row (verticalAlignment = Alignment.Bottom,
        modifier = Modifier.padding(bottom = 70.dp)){
        OutlinedTextField(
            value = photoDescription,
            onValueChange = {photoDescription = it},
            label = { Text(text = "Photo Description")},
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 15.dp))
    }
    Row (horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.Bottom,
        modifier = Modifier.padding(bottom = 15.dp)){
        OutlinedButton(onClick = {
            if(!downloadUri.isNullOrBlank()){
                viewModel.addPost(newPost,authId!!)
                Toast.makeText(
                    context,
                    "Post Successful",
                    Toast.LENGTH_SHORT
                ).show()
            }
            mediaSelected=false
        }) {
            Text(text = "Post")
        }
        OutlinedButton(onClick = {
            if(!downloadUri.isNullOrBlank()){
                viewModel.addStory(newStory,authId!!)
                Toast.makeText(
                    context,
                    "Story Successful",
                    Toast.LENGTH_SHORT
                ).show()
            }
            mediaSelected=false

        }) {
            Text(text = "Story")
        }
    }
}


fun Context.createImageFile(): File {
    val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
    val imageFileName = "JPEG_" + timeStamp + "_"
    val image = File.createTempFile(
        imageFileName,
        ".jpg",
        externalCacheDir
    )
    return image
}