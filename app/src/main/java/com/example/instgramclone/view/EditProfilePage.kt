package com.example.instgramclone.view

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.instgramclone.R
import com.example.instgramclone.viewmodel.EditProfilePageViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.storage
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfilePage(navController: NavController,viewModel:EditProfilePageViewModel) {
    var name by remember { mutableStateOf("") }
    var userName by remember { mutableStateOf("") }
    var bio by remember { mutableStateOf("") }
    var uri by remember { mutableStateOf<Uri?>(null) }
    var mediaSelected by remember { mutableStateOf(false) }
    val downloadUri = viewModel.downloadUri.observeAsState().value
    var auth: FirebaseAuth
    auth = Firebase.auth

    LaunchedEffect(mediaSelected){
        uri?.let { viewModel.uploadProfilePhoto(it,"profileimages") }
    }



    val PhotoPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { selectedUri ->
            uri = selectedUri
            mediaSelected = true
        })



    Column(
        modifier = Modifier.padding(all=15.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically){
            IconButton(onClick = {
                navController.navigate("homepage")
            }) {
                Icon(Icons.Default.Clear, contentDescription = "", modifier = Modifier.size(40.dp))
            }
            Text(text = "Edit Profile", fontSize = 16.sp)
            IconButton(onClick = {
                viewModel.saveProfileInformation(auth.currentUser?.uid!!,downloadUri!!,userName,name,bio)
                navController.navigate("homepage")
            }) {
                Icon(Icons.Default.Check, contentDescription = "", modifier = Modifier.size(40.dp))
            }
        }
        Column (
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally){
            AsyncImage(
                model = uri,
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape))
            Spacer(modifier = Modifier.height(10.dp))
            TextButton(onClick = {
                PhotoPicker.launch(
                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                )
            }) {
                Text(text = "Change Profile photo", fontSize = 17.sp, color = colorResource(id = R.color.change_profil_photo_text))
            }
        }
        Column (
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally){

            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                modifier = Modifier.fillMaxWidth(),
                label = {
                    Text("Name",
                        fontSize = 15.sp,) }
            )
            OutlinedTextField(
                value = userName,
                onValueChange = { userName = it },
                modifier = Modifier.fillMaxWidth(),
                label = {
                    Text("Username",
                        fontSize = 15.sp,) }
            )
            OutlinedTextField(
                value = bio,
                onValueChange = { bio = it },
                modifier = Modifier.fillMaxWidth(),
                label = {
                    Text("Bio",
                        fontSize = 15.sp,) }
            )
        }
        Spacer(modifier = Modifier.height(200.dp))

    }

}