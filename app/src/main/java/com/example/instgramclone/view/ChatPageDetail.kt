package com.example.instgramclone.view

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.instgramclone.model.Message
import com.example.instgramclone.model.User
import com.example.instgramclone.viewmodel.ChatPageDetailViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatPageDetail(navController: NavController,user: User,viewModel: ChatPageDetailViewModel) {
    var auth: FirebaseAuth
    auth = Firebase.auth
    val text = remember { mutableStateOf("") }

    user.authId
    auth.currentUser?.uid

    Scaffold (
        bottomBar = {
            OutlinedTextField(
                value = text.value,
                onValueChange = {text.value = it},
                label = { Text(text = "message")},
                modifier = Modifier
                    .padding(start = 10.dp, end = 10.dp, bottom = 10.dp)
                    .fillMaxWidth(),
                trailingIcon = {
                    IconButton(onClick = {
                        viewModel.sendMessage(auth.currentUser?.uid!!, user.authId!!,text.value)
                        viewModel.getMessages(auth.currentUser?.uid!!, user.authId!!)
                    }) {
                        Icon(
                            imageVector = Icons.Default.Send,
                            contentDescription = "")
                    }

                })
        }
    ){innerPadding ->
        Column (
            modifier = Modifier
                .padding(innerPadding)
        ){
            Row (modifier = Modifier.clickable {
                viewModel.getMessages(auth.currentUser?.uid!!, user.authId!!)
            }){
                UserCard(user = user)
            }
            ChatScreen(auth.currentUser?.uid!!, user.authId!!,viewModel)
        }

    }

}

@Composable
fun ChatBubble(
    message: String,
    sender: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = if (sender == "Me") Arrangement.End else Arrangement.Start
    ) {
        Card(
            modifier = Modifier.padding(8.dp),
            colors = CardDefaults.cardColors(
                containerColor = if (sender == "Me") Color(0xFF64B5F6) else Color(0xFFE0E0E0)
            ),
            shape = RoundedCornerShape(16.dp)
        ) {
            Box(
                modifier = Modifier.padding(12.dp)
            ) {
                Text(
                    text = message,
                    color = if (sender == "Me") Color.White else Color.Black,
                    style = TextStyle(fontSize = 16.sp)
                )
            }
        }
    }
}

@Composable
fun ChatScreen(senderId: String, receiverId: String,viewModel: ChatPageDetailViewModel) {
    viewModel.getMessages(senderId, receiverId)
    val messages by viewModel.messages.observeAsState(emptyList())
    LazyColumn {
        items(messages.size) { index ->
            val message = messages[index]
            ChatBubble(
                message = message.messageText!!,
                sender = if (message.senderId == senderId) "Me" else "You"
            )
        }
    }
}





