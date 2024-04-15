package com.example.instgramclone.repo

import android.content.Context
import android.graphics.Bitmap
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
    suspend fun uploadMedia(uri: Uri,pathString:String):String = insdts.uploadMedia(uri,pathString)
    suspend fun addPost(newPost: Post,authId: String) = insdts.addPost(newPost,authId)
    suspend fun addStory(newStory: User,authId: String) = insdts.addStory(newStory, authId)
    suspend fun getHomePagePostReelsList() : List<User> = insdts.getHomePagePostReelsList()
    suspend fun addReels(newReels: Reel, authId: String) =insdts.addReels(newReels, authId)
    suspend fun getReelsList(): List<Reel> = insdts.getReelsList()
    suspend fun saveMyProfileInformation(authId: String):User = insdts.saveMyProfileInformation(authId)
    suspend fun getExplorePagePostList(): List<Post> =insdts.getExplorePagePostList()
    suspend fun getExplorePageSearchList(searchText:String): ArrayList<User> =insdts.getExplorePageSearchList(searchText)
    suspend fun postAddLike(newUser: User,postList: List<Post>,postIndex: Int,uAuthId:String) =insdts.postAddLike(newUser,postList,postIndex,uAuthId)
    suspend fun postAddComment(newUser: User, postList: List<Post>, postIndex: Int, uAuthId:String) = insdts.postAddComment(newUser, postList, postIndex, uAuthId)
    fun sendMessage(senderId: String, receiverId: String, messageText: String) = insdts.sendMessage(senderId, receiverId, messageText)
    suspend fun getMessages(senderId: String, receiverId: String) = insdts.getMessages(senderId, receiverId)


}