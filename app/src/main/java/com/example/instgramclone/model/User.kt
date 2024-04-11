package com.example.instgramclone.model

data class User(
    var authId: String? = null,
    var profilePhoto: String? = null,
    var userName: String? = null,
    var name: String? = null,
    var bio: String? = null,
    var posts: List<Post>? = listOf<Post>(),
    var reels: List<Reel>? = listOf<Reel>(),
    var story: String? = null,
    var follower: String? = null,
    var following: String? = null,
    var comment: String? = null,
)
data class Post(
    var photo:String? = null,
    var like:ArrayList<User>? = arrayListOf<User>(),
    var comment:ArrayList<User>? = arrayListOf<User>(),
    var photoDescription:String? = null
)

data class Reel(
    var video:String? = null,
    var like:List<User>? = listOf<User>(),
    var comment:List<User>? = listOf<User>(),
    var videoDescription:String? = null
)

