package com.example.instgramclone.repo

import android.content.Context
import android.net.Uri
import com.example.instgramclone.datasource.InstgramCloneDataSource
import com.example.instgramclone.model.Post
import com.example.instgramclone.model.Reel
import com.example.instgramclone.model.User
import com.google.firebase.auth.AuthResult

class InstgramCloneRepository(var insdts:InstgramCloneDataSource) {
    suspend fun emailAuthenticationSignUp(email:String,password:String,context: Context) : Boolean = insdts.emailAuthenticationSignUp(email, password, context)
    suspend fun emailAuthenticationSignIn(email:String,password:String,context: Context) : Boolean = insdts.emailAuthenticationSignIn(email, password, context)
    suspend fun saveProfileInformation(authId:String, profilePhoto:String, userName:String, name:String, bio:String) = insdts.saveProfileInformation(authId, profilePhoto, userName, name, bio)
    suspend fun uploadPhoto(uri: Uri,pathString:String):String = insdts.uploadPhoto(uri,pathString)
    suspend fun addPost(newPost: Post,authId: String) = insdts.addPost(newPost,authId)
    suspend fun addStory(newStory: User,authId: String) = insdts.addStory(newStory, authId)
    suspend fun homePagePostReelsList() : List<User> = insdts.homePagePostReelsList()
    suspend fun addReels(newReels: Reel, authId: String) =insdts.addReels(newReels, authId)
    suspend fun ReelsList(): List<Reel> = insdts.ReelsList()
}