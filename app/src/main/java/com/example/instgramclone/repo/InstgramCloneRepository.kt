package com.example.instgramclone.repo

import android.content.Context
import android.net.Uri
import com.example.instgramclone.datasource.InstgramCloneDataSource
import com.google.firebase.auth.AuthResult

class InstgramCloneRepository(var insdts:InstgramCloneDataSource) {
    suspend fun emailAuthenticationSignUp(email:String,password:String,context: Context) : Boolean = insdts.emailAuthenticationSignUp(email, password, context)
    suspend fun emailAuthenticationSignIn(email:String,password:String,context: Context) : Boolean = insdts.emailAuthenticationSignIn(email, password, context)
    suspend fun saveProfileInformation(authId:String, profilePhoto:String, userName:String, name:String, bio:String) = insdts.saveProfileInformation(authId, profilePhoto, userName, name, bio)
    suspend fun uploadProfilePhoto(uri: Uri):String=insdts.uploadProfilePhoto(uri)
}