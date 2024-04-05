package com.example.instgramclone.viewmodel

import android.graphics.Bitmap
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.instgramclone.model.User
import com.example.instgramclone.repo.InstgramCloneRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class MyProfileViewModel @Inject constructor(var insrepo : InstgramCloneRepository) : ViewModel() {
    var user1 = mutableStateOf<User?>(null)
    fun myProfileInformation(authId:String){
        viewModelScope.launch {
            user1.value = insrepo.myProfileInformation(authId)
        }
    }


}