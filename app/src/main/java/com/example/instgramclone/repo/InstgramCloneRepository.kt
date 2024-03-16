package com.example.instgramclone.repo

import android.content.Context
import com.example.instgramclone.datasource.InstgramCloneDataSource

class InstgramCloneRepository(var insdts:InstgramCloneDataSource) {
    suspend fun emailAuthenticationSignUp(email:String,password:String,context: Context) : Boolean = insdts.emailAuthenticationSignUp(email, password, context)

    suspend fun emailAuthenticationSignIn(email:String,password:String,context: Context) : Boolean = insdts.emailAuthenticationSignIn(email, password, context)

}