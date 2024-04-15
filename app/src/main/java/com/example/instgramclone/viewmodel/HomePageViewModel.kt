package com.example.instgramclone.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.instgramclone.model.Post
import com.example.instgramclone.model.User
import com.example.instgramclone.repo.InstgramCloneRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class HomePageViewModel @Inject constructor(var insrepo : InstgramCloneRepository) : ViewModel() {
    var homePageList = MutableLiveData<List<User>>()
    var user1 = mutableStateOf<User?>(null)
    fun getHomePagePostReelsList(){
        viewModelScope.launch {
            homePageList.value = insrepo.getHomePagePostReelsList()
        }
    }
    fun addLike(newUser: User, postList: List<Post>, postIndex: Int,uAuthId:String){
        viewModelScope.launch {
            insrepo.postAddLike(newUser,postList,postIndex,uAuthId)
        }
    }
    fun addComment(newUser: User, postList: List<Post>, postIndex: Int,uAuthId:String){
        viewModelScope.launch {
            insrepo.postAddComment(newUser,postList,postIndex,uAuthId)
        }
    }
    fun myProfileInformation(authId:String){
        viewModelScope.launch {
            user1.value = insrepo.saveMyProfileInformation(authId)
        }
    }
}