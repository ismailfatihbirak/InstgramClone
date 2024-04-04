package com.example.instgramclone.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.instgramclone.model.Reel
import com.example.instgramclone.model.User
import com.example.instgramclone.repo.InstgramCloneRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class ReelsPageViewModel @Inject constructor(var insrepo : InstgramCloneRepository) : ViewModel() {
    var usersList = MutableLiveData<List<User>>()
    var reelsList = MutableLiveData<List<Reel>>()
    fun userListfun(){
        viewModelScope.launch {
            usersList.value = insrepo.homePagePostReelsList()
        }
    }

    fun reelsListfun(){
        viewModelScope.launch {
            reelsList.value = insrepo.ReelsList()
        }
    }
}