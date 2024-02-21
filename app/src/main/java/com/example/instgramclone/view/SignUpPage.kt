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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.instgramclone.R

@Composable
fun SignUpPage() {
    Column(
        modifier = Modifier.padding(all=30.dp),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(modifier = Modifier.height(80.dp))
        Image(painter = painterResource(id = R.drawable.logos_instagram), contentDescription = "" )
        Spacer(modifier = Modifier.height(30.dp))
        Button(onClick = { /* Do something! */ },colors = ButtonDefaults.buttonColors(
            containerColor = colorResource(id = R.color.fb_blue),
            contentColor = Color.White
        ), modifier = Modifier.fillMaxWidth(),) {
            Icon(
                painterResource(id = R.drawable.vector ), contentDescription = "",
                modifier = Modifier.size(ButtonDefaults.IconSize))
            Spacer(Modifier.size(ButtonDefaults.IconSpacing))
            Text(text = "Continue with Facebook", fontSize = 12.sp)
        }
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
        TextButton(onClick = { /*TODO*/ }) {
            Text(text = "Sign Up With Email", fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = Color.Black)
        }

    }
    Column (
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally){
        TextButton(onClick = { /*TODO*/ }){
            Text(text = "Already have an account", fontSize = 12.sp, color = colorResource(id = R.color.text_gry))
            Text(text = "Sign in.", fontSize = 12.sp, fontWeight = FontWeight.Medium, color = Color.Black)
        }
    }


}