package com.example.instgramclone.viewmodel

import android.content.Context
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
class SignInPageViewModel @Inject constructor(var insrepo : InstgramCloneRepository) : ViewModel() {
    var emailAuthControl by mutableStateOf(false)
    fun emailAuthentication(email:String,password:String,context: Context) {
        CoroutineScope(Dispatchers.Main).launch {
            emailAuthControl = insrepo.emailAuthenticationSignIn(email, password, context)
        }
    }
}