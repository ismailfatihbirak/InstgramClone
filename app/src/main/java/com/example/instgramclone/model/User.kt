package com.example.instgramclone.model

data class User(
    val authId: String? = null,
    val profilePhoto: String? = null,
    val userName: String? = null,
    val name: String? = null,
    val bio: String? = null,
    val posts: ArrayList<Post>? = arrayListOf(),
    val reels: ArrayList<Reel>? = arrayListOf(),
    val story: String? = null,
    val follower: String? = null,
    val following: String? = null
)
data class Post(
    val photo:String? = null,
    val like:ArrayList<User>? = arrayListOf(),
    val comment:ArrayList<User>? = arrayListOf(),
    val photoDescription:String? = null
)

data class Reel(
    val video:String? = null,
    val like:ArrayList<User>? = arrayListOf(),
    val comment:ArrayList<User>? = arrayListOf(),
    val videoDescription:String? = null
)

