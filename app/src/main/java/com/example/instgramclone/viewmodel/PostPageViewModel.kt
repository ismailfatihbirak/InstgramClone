package com.example.instgramclone.viewmodel

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.instgramclone.model.Post
import com.example.instgramclone.model.User
import com.example.instgramclone.repo.InstgramCloneRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class PostPageViewModel @Inject constructor(var insrepo : InstgramCloneRepository) : ViewModel() {
    var downloadUri = MutableLiveData("")
    fun uploadPostPhoto(uri: Uri,pathString:String) {
        CoroutineScope(Dispatchers.Main).launch {
            downloadUri.value = insrepo.uploadPhoto(uri,pathString)
        }
    }

    fun addPost(newPost: Post,authId:String){
        CoroutineScope(Dispatchers.Main).launch {
            insrepo.addPost(newPost,authId)
        }
    }
    fun addStory(newStory:User,authId:String){
        CoroutineScope(Dispatchers.Main).launch {
            insrepo.addStory(newStory,authId)
        }
    }
}