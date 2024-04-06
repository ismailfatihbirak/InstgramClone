package com.example.instgramclone.view

import android.content.ContentValues.TAG
import android.os.Build
import android.provider.Settings.Global.getString
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.instgramclone.R
import com.example.instgramclone.viewmodel.SignInPageViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth


@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun SignInPage(navController: NavController,viewModel: SignInPageViewModel) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf(value = "") }
    var showPassword by remember { mutableStateOf(value = false) }
    val context = LocalContext.current
    var emailAuthControl by remember { mutableStateOf(false) }
    emailAuthControl = viewModel.emailAuthControl

    if (emailAuthControl){
        navController.navigate("editprofilepage")
        Toast.makeText(
            context,
            "login successful",
            Toast.LENGTH_SHORT
        ).show()
    }

    Column (
        modifier = Modifier.padding(all=30.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Image(
            painter = painterResource(id = R.drawable.logos_instagram_2),
            contentDescription = "")
        Spacer(modifier = Modifier.height(20.dp))
        EmailTextField(
            tf = email,
            onTfChange = { newTf ->
                email = newTf
            }
        )
        Spacer(modifier = Modifier.height(12.dp))
        PasswordTextField(
            password = password,
            onPasswordChange = { newPassword ->
                password = newPassword
            },
            showPassword = showPassword,
            onToggleShowPassword = {
                showPassword = !showPassword
            }
        )
        Spacer(modifier = Modifier.height(12.dp))
        BlueButton(
            onClick = {
                viewModel.emailAuthentication(email, password, context)
                      },
            text = "Log in")
    }
}