package com.example.instgramclone.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.instgramclone.model.Post
import com.example.instgramclone.model.User
import com.example.instgramclone.viewmodel.ExplerePageViewModel
import com.google.gson.Gson
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatPage(navController: NavController,viewModel: ExplerePageViewModel) {
    var text by remember { mutableStateOf("") }
    val searchResults = viewModel.searchList.observeAsState(arrayListOf())

    Scaffold (
        topBar = {
            TopAppBar(
                colors = androidx.compose.material3.TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = Color.White,
                    titleContentColor = Color.Black,
                ),
                modifier = Modifier.height(50.dp),
                title = {
                    Text(text = "Chats",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp)
                })
        }
    ){innerPadding ->
        Column (
            modifier = Modifier
                .padding(innerPadding)
        ){
            TextField(
                value = text,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 15.dp, start = 15.dp),
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                label = { Text(text = "Search")},
                maxLines = 1,
                shape = RoundedCornerShape(50),
                leadingIcon = { Icon(imageVector = Icons.Default.Search,
                    contentDescription = null) },
                onValueChange ={ newText->
                    text = newText
                    if (newText.isNotBlank()){
                        viewModel.getExplorePageSearchList(newText)
                    }
                    searchResults.value.removeIf { newText.isBlank() || newText.isNullOrEmpty() }
                })

            if (searchResults.value.isNotEmpty()){
                LazyColumn {
                    items(searchResults.value) { user ->

                        val userJson = Gson().toJson(user)

                        Column (modifier = Modifier.clickable {
                            navController.navigate("chatpagedetail/${
                                URLEncoder.encode(
                                    userJson,
                                    StandardCharsets.UTF_8.toString()
                                )
                            }")
                        }){
                            UserCard(user = user)
                        }
                    }
                }
            }else{
                /*LazyColumn {
                    items() { user ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    val userJson = Gson().toJson(user)
                                    navController.navigate(
                                        "chatpagedetail/${
                                            URLEncoder.encode(
                                                userJson,
                                                StandardCharsets.UTF_8.toString()
                                            )
                                        }"
                                    )
                                }
                        ) {
                            UserCard(user = user)
                        }
                    }
                }*/


            }

        }

    }
}