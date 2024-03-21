package com.example.instgramclone.view

import android.content.Intent
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.instgramclone.R
import com.example.instgramclone.viewmodel.SignUpPage2ViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.Firebase
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await



@Composable
fun SignUpPage(navController: NavController,viewModel: SignUpPage2ViewModel) {
    var auth: FirebaseAuth
    val context = LocalContext.current
    auth = Firebase.auth
    var user by remember { mutableStateOf(Firebase.auth.currentUser) }
    val token = stringResource(R.string.default_web_client_id)
    val launcher = rememberFirebaseAuthLauncher(
        onAuthComplete = { result ->
            user = result.user
            navController.navigate("homepage")
        },
        onAuthError = {
            user = null
        }
    )
    Column(
        modifier = Modifier.padding(all=30.dp),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally) {

        Spacer(modifier = Modifier.height(80.dp))
        Image(
            painter = painterResource(id = R.drawable.logos_instagram),
            contentDescription = "" )
        Spacer(modifier = Modifier.height(30.dp))

        GoogleButton(
            onClick = {
                val gso =
                    GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestIdToken(token)
                        .requestEmail()
                        .build()
                val googleSignInClient = GoogleSignIn.getClient(context, gso)
                launcher.launch(googleSignInClient.signInIntent)

                      }, text = "Continue with Google")

        Or()

        TextButton(
            onClick = { navController.navigate("signuppage2") }) {
            Text(
                text = "Sign Up With Email",
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black)
        }

    }
    Column (
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(bottom = 20.dp)){
        TextButton(onClick = { navController.navigate("signinpage") }){
            Text(
                text = "Already have an account",
                fontSize = 12.sp,
                color = colorResource(id = R.color.text_gry))
            Text(
                text = " Sign in.",
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black)
        }
    }



}
@Composable
fun GoogleButton(onClick: () -> Unit,text : String) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = colorResource(id = R.color.fb_blue),
            contentColor = Color.White
        ),
        modifier = Modifier.fillMaxWidth(),
    ) {
        Icon(
            painterResource(id = R.drawable.google_icon),
            contentDescription = "",
            modifier = Modifier.size(ButtonDefaults.IconSize))
        Spacer(Modifier.size(ButtonDefaults.IconSpacing))
        Text(
            text = text,
            fontSize = 12.sp)
    }
}

@Composable
fun Or() {
    Row (
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()){
        Image(
            painter = painterResource(id = R.drawable.line_2),
            contentDescription = "")
        Spacer(modifier = Modifier.width(20.dp))
        Text(
            text = "OR",
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold)
        Spacer(modifier = Modifier.width(20.dp))
        Image(painter = painterResource(id = R.drawable.line_2), contentDescription = "")
    }
}
@Composable
fun rememberFirebaseAuthLauncher(
    onAuthComplete: (AuthResult) -> Unit,
    onAuthError: (ApiException) -> Unit
): ManagedActivityResultLauncher<Intent, ActivityResult> {
    val scope = rememberCoroutineScope()
    return rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        try {
            val account = task.getResult(ApiException::class.java)!!
            val credential = GoogleAuthProvider.getCredential(account.idToken!!, null)
            scope.launch {
                val authResult = Firebase.auth.signInWithCredential(credential).await()
                onAuthComplete(authResult)
            }
        } catch (e: ApiException) {
            onAuthError(e)
        }
    }
}




















