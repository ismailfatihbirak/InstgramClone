package com.example.instgramclone.datasource

import android.app.Activity
import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.content.IntentSender
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
import com.example.instgramclone.R
import com.example.instgramclone.model.User
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.Firebase
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.storage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.util.UUID
import kotlin.coroutines.cancellation.CancellationException
import kotlin.coroutines.resume
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
            collectionUser.document().set(newUser)
        }

    suspend fun uploadProfilePhoto(uri: Uri): String = withContext(Dispatchers.IO) {
        var storage: FirebaseStorage = Firebase.storage
        val storageRef = storage.reference
        val uuid = UUID.randomUUID()
        val imageName = "$uuid.jpg"
        val imagesRef = storageRef.child("profileimages").child(imageName)

        if (uri != null) {
            imagesRef.putFile(uri).await()
        }

        val downloadUriTask = storage.reference.child("profileimages").child(imageName).downloadUrl
        val downloadUri = try {
            downloadUriTask.await()
        } catch (e: Exception) {
            null
        }

        downloadUri?.toString() ?: ""
    }





}


