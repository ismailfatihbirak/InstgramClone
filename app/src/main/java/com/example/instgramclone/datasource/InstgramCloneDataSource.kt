package com.example.instgramclone.datasource

import android.app.Activity
import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.graphics.Bitmap
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.provider.Settings.Global.getString
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.instgramclone.R
import com.example.instgramclone.model.Post
import com.example.instgramclone.model.Reel
import com.example.instgramclone.model.User
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.Firebase
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.storage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.util.UUID
import kotlin.coroutines.cancellation.CancellationException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class InstgramCloneDataSource(var collectionUser: CollectionReference) {
    suspend fun emailAuthenticationSignUp(email:String,password:String,context:Context) : Boolean =
        withContext(Dispatchers.IO){
            suspendCoroutine<Boolean> { continuation ->
                val auth = Firebase.auth
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Log.d(ContentValues.TAG, "createUserWithEmail:success")
                            continuation.resume(true)
                        } else {
                            Log.w(ContentValues.TAG, "createUserWithEmail:failure", task.exception)
                            Toast.makeText(
                                context,
                                "Authentication failed.",
                                Toast.LENGTH_SHORT,
                            ).show()
                            continuation.resume(false)
                        }
                    }
            }
        }

    suspend fun emailAuthenticationSignIn(email:String,password:String,context:Context) : Boolean =
        withContext(Dispatchers.IO){
            suspendCoroutine<Boolean> { continuation ->
                val auth = Firebase.auth
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener{ task ->
                        if (task.isSuccessful) {
                            Log.d(ContentValues.TAG, "signInWithEmail:success")
                            continuation.resume(true)
                        } else {
                            Log.w(ContentValues.TAG, "signInWithEmail:failure", task.exception)
                            Toast.makeText(
                                context,
                                "Authentication failed.",
                                Toast.LENGTH_SHORT,
                            ).show()
                            continuation.resume(false)
                        }
                    }

            }
        }

    suspend fun saveProfileInformation(authId:String, profilePhoto:String, userName:String, name:String, bio:String) =
        withContext(Dispatchers.IO){
            val newUser = User(authId,profilePhoto,userName,name,bio,null,null,null,null)
            collectionUser.document(authId).set(newUser)
        }

    suspend fun uploadPhoto(uri: Uri,pathString:String): String = withContext(Dispatchers.IO) {
        var storage: FirebaseStorage = Firebase.storage
        val storageRef = storage.reference
        val uuid = UUID.randomUUID()
        val imageName = "$uuid.jpg"
        val imagesRef = storageRef.child(pathString).child(imageName)
        uri.let { imagesRef.putFile(uri).await() }
        val downloadUri = imagesRef.downloadUrl.await()
        return@withContext downloadUri?.toString() ?: ""
    }

    suspend fun addPost(newPost: Post,authId: String) = withContext(Dispatchers.IO) {
        collectionUser.document(authId).update("posts", FieldValue.arrayUnion(newPost))
    }
    suspend fun addStory(newStory: User,authId: String) = withContext(Dispatchers.IO) {
        collectionUser.document(authId).update("story", newStory.story)
    }
    suspend fun addReels(newReels: Reel,authId: String) = withContext(Dispatchers.IO) {
        collectionUser.document(authId).update("reels", FieldValue.arrayUnion(newReels))
    }

    suspend fun homePagePostReelsList(): List<User> = suspendCoroutine { continuation ->
        val liste = ArrayList<User>()
        collectionUser.get().addOnSuccessListener { snapshot ->
            for (document in snapshot.documents) {
                val user = document.toObject(User::class.java)
                user?.let { liste.add(it) }
            }
            continuation.resume(liste)
        }.addOnFailureListener { exception ->
            continuation.resumeWithException(exception)
        }
    }
    suspend fun myProfileInformation(authId: String): User = suspendCoroutine { continuation ->
        collectionUser.document(authId).get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    val userData = document.toObject(User::class.java)
                    continuation.resume(userData!!)
                } else {
                    Log.d(TAG, "No such document")
                    continuation.resumeWithException(Exception("No such document"))
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "get failed with ", exception)
                continuation.resumeWithException(exception)
            }
    }

    suspend fun ReelsList(): List<Reel> = suspendCoroutine { continuation ->
        val liste = ArrayList<Reel>()
        collectionUser.get().addOnSuccessListener { snapshot ->
            for (document in snapshot.documents) {
                val user = document.toObject(User::class.java)
                val aa = user!!.reels
                if (aa != null) {
                    for (i in aa) {
                        liste.add(i)
                    }
                }
            }
            continuation.resume(liste)
        }.addOnFailureListener { exception ->
            continuation.resumeWithException(exception)
        }
    }







}


