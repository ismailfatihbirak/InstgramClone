package com.example.instgramclone.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.instgramclone.model.Message
import com.example.instgramclone.model.User
import com.example.instgramclone.repo.InstgramCloneRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatPageDetailViewModel @Inject constructor(var insrepo : InstgramCloneRepository) : ViewModel() {
    var messages = MutableLiveData<List<Message>>()
    fun sendMessage(senderId: String, receiverId: String, messageText: String){
        insrepo.sendMessage(senderId, receiverId, messageText)
    }
    fun getMessages(senderId: String, receiverId: String){
        viewModelScope.launch {
            messages.value = insrepo.getMessages(senderId, receiverId)
        }
    }
}