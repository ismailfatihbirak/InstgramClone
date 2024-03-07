package com.example.instgramclone.view

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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpPage2(navController: NavController) {
    var tf by remember { mutableStateOf("") }
    var password by remember { mutableStateOf(value = "") }
    var showPassword by remember { mutableStateOf(value = false) }
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
            tf = tf,
            onTfChange = { newTf ->
                tf = newTf
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
            onClick = { navController.navigate("signinpage") },
            text = "Sign Up")
        Spacer(modifier = Modifier.height(40.dp))

        Or()

        Spacer(modifier = Modifier.height(40.dp))

        FacebookButton(
            onClick = { /*TODO*/ },
            text = "Sign Up with Facebook")



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


