package com.example.instgramclone.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.instgramclone.model.Post
import com.example.instgramclone.model.User
import com.example.instgramclone.repo.InstgramCloneRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class ExplerePageViewModel @Inject constructor(var insrepo : InstgramCloneRepository) : ViewModel() {
    var postList = MutableLiveData<List<Post>>()
    var searchList = MutableLiveData<ArrayList<User>>()
    var pastSearchList = MutableLiveData<List<User>>()
    fun getExplorePagePostList(){
        viewModelScope.launch {
            postList.value = insrepo.getExplorePagePostList()
        }
    }
    fun getExplorePageSearchList(searchText:String){
        viewModelScope.launch {
            searchList.value = insrepo.getExplorePageSearchList(searchText)
        }
    }



}