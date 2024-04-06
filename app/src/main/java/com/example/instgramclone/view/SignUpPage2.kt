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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.instgramclone.R
import com.example.instgramclone.viewmodel.SignUpPage2ViewModel
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

@RequiresApi(Build.VERSION_CODES.P)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpPage2(navController: NavController,viewModel:SignUpPage2ViewModel) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf(value = "") }
    var showPassword by remember { mutableStateOf(value = false) }
    lateinit var auth: FirebaseAuth
    val context = LocalContext.current
    auth = Firebase.auth
    var user by remember { mutableStateOf(Firebase.auth.currentUser) }
    val token = stringResource(R.string.default_web_client_id)
    val launcher = rememberFirebaseAuthLauncher(
        onAuthComplete = { result ->
            user = result.user
            navController.navigate("editprofilepage")
        },
        onAuthError = {
            user = null
        }
    )

    var emailAuthControl by remember { mutableStateOf(false) }
    emailAuthControl = viewModel.emailAuthControl
    if (emailAuthControl){
        navController.navigate("signinpage")
        Toast.makeText(
            LocalContext.current,
            "sign up successful",
            Toast.LENGTH_SHORT
        ).show()
    }




    Column (
        modifier = Modifier.padding(all=30.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Image(
            painter = painterResource(id = com.example.instgramclone.R.drawable.logos_instagram_2),
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
            text = "Sign Up")
        Spacer(modifier = Modifier.height(40.dp))

        Or()

        Spacer(modifier = Modifier.height(40.dp))

        GoogleButton(
            onClick = {
                val gso =
                    GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestIdToken(token)
                        .requestEmail()
                        .build()
                val googleSignInClient = GoogleSignIn.getClient(context, gso)
                launcher.launch(googleSignInClient.signInIntent)

            },
            text = "Sign Up with Google")
    }




}

@Composable
fun BlueButton(onClick: () -> Unit,text : String) {
    Button(
        onClick = onClick,colors = ButtonDefaults.buttonColors(
        containerColor = colorResource(id = R.color.fb_blue),
        contentColor = Color.White
    ), modifier = Modifier.fillMaxWidth(),) {

        Text(text = text, fontSize = 12.sp)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordTextField(
    password: String,
    onPasswordChange: (String) -> Unit,
    showPassword: Boolean,
    onToggleShowPassword: () -> Unit
) {
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = password,
        onValueChange = onPasswordChange,
        label = {
            Text(text = "Password",
                fontSize = 10.sp)
        },
        visualTransformation = if (showPassword) {

            VisualTransformation.None

        } else {

            PasswordVisualTransformation()

        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        trailingIcon = {
            IconButton(
                onClick = onToggleShowPassword
            ) {
                Icon(
                    painter = painterResource(id = if (showPassword) R.drawable.baseline_visibility_24 else R.drawable.baseline_visibility_off_24),
                    contentDescription = "hide_password"
                )
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmailTextField(
    tf: String,
    onTfChange: (String) -> Unit
) {
    OutlinedTextField(
        value = tf,
        onValueChange = onTfChange,
        modifier = Modifier.fillMaxWidth(),
        label = {
            Text(
                text = "Email",
                fontSize = 10.sp
            )
        }
    )
}


