package com.example.instgramclone.viewmodel

import android.content.Context
import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.instgramclone.repo.InstgramCloneRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class EditProfilePageViewModel @Inject constructor(var insrepo : InstgramCloneRepository) : ViewModel() {
    var downloadUri by mutableStateOf("")
    fun saveProfileInformation(authId:String, profilePhoto:String, userName:String, name:String, bio:String) {
        CoroutineScope(Dispatchers.Main).launch {
            insrepo.saveProfileInformation(authId, profilePhoto, userName, name, bio)
        }
    }

    fun uploadProfilePhoto(uri:Uri) {
        CoroutineScope(Dispatchers.Main).launch {
            downloadUri = insrepo.uploadProfilePhoto(uri)
        }
    }

}