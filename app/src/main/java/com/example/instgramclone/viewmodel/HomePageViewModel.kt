package com.example.instgramclone.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
    fun homePagePostListfun(){
        viewModelScope.launch {
            homePageList.value = insrepo.homePagePostReelsList()
        }
    }
}