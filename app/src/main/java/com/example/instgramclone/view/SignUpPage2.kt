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
import com.example.instgramclone.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpPage2() {
    var tf by remember { mutableStateOf("") }
    var password by remember { mutableStateOf(value = "") }
    var showPassword by remember { mutableStateOf(value = false) }
    Column (
        modifier = Modifier.padding(all=30.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Image(painter = painterResource(id = com.example.instgramclone.R.drawable.logos_instagram_2), contentDescription = "")
        Spacer(modifier = Modifier.height(20.dp))
        OutlinedTextField(
            value = tf,
            onValueChange = { tf = it },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text("Email",
                    fontSize = 10.sp,) }
        )
        Spacer(modifier = Modifier.height(12.dp))
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = password,
            onValueChange = { newText ->
                password = newText
            },
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
                if (showPassword) {
                    IconButton(onClick = { showPassword = false }) {
                        Icon(
                            painterResource(id = R.drawable.baseline_visibility_24),
                            contentDescription = "hide_password"
                        )
                    }
                } else {
                    IconButton(
                        onClick = { showPassword = true }) {
                        Icon(
                            painterResource(id = R.drawable.baseline_visibility_off_24) ,
                            contentDescription = "hide_password"
                        )
                    }
                }
            }
        )
        Spacer(modifier = Modifier.height(12.dp))
        Button(onClick = { /* Do something! */ },colors = ButtonDefaults.buttonColors(
            containerColor = colorResource(id = R.color.fb_blue),
            contentColor = Color.White
        ), modifier = Modifier.fillMaxWidth(),) {

            Text(text = "Sign Up", fontSize = 12.sp)
        }
        Spacer(modifier = Modifier.height(40.dp))
        Row (
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()){
            Image(painter = painterResource(id = R.drawable.line_2), contentDescription = "")
            Spacer(modifier = Modifier.width(20.dp))
            Text(text = "OR", fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
            Spacer(modifier = Modifier.width(20.dp))
            Image(painter = painterResource(id = R.drawable.line_2), contentDescription = "")
        }
        Spacer(modifier = Modifier.height(40.dp))
        Button(onClick = { /* Do something! */ },colors = ButtonDefaults.buttonColors(
            containerColor = colorResource(id = R.color.fb_blue),
            contentColor = Color.White
        ), modifier = Modifier.fillMaxWidth(),) {
            Icon(
                painterResource(id = R.drawable.vector ), contentDescription = "",
                modifier = Modifier.size(ButtonDefaults.IconSize))
            Spacer(Modifier.size(ButtonDefaults.IconSpacing))
            Text(text = "Sign Up with Facebook", fontSize = 12.sp)
        }



    }




}

