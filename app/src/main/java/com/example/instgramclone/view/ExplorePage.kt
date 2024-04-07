package com.example.instgramclone.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarColors
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TextFieldDefaults.shape
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.instgramclone.R
import com.example.instgramclone.model.User
import com.example.instgramclone.viewmodel.ExplerePageViewModel
import com.google.gson.Gson
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExplorePage(navController: NavController,viewModel: ExplerePageViewModel) {
    var text by remember { mutableStateOf("") }

    viewModel.ExplorePagePostList()
    val postList = viewModel.postList.observeAsState(listOf())

    val searchResults = viewModel.searchList.observeAsState(arrayListOf())

    Scaffold(
        bottomBar = {
            fun navigateToScreen(destination: String) {
                navController.navigate(destination){
                    popUpTo("explorepage"){ inclusive = true}
                }
            }
            BottomNavigation(onItemClick = :: navigateToScreen)

        }
    ){ innerPadding ->
        Column (
            modifier = Modifier
                .padding(innerPadding)
        ){
            TextField(
                value = text,
                modifier = Modifier
                    .fillMaxWidth(),
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
                        viewModel.ExplorePageSearchList(newText)
                    }
                    searchResults.value.removeIf { newText.isBlank() || newText.isNullOrEmpty() }
                })

            if (searchResults.value.isNotEmpty()){
                LazyColumn {
                    items(searchResults.value) { user ->
                        val userJson = Gson().toJson(user)
                        Column (modifier = Modifier.clickable {
                            navController.navigate("profilepage/${
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
                LazyVerticalGrid(
                    columns = GridCells.Fixed(3)
                ) {
                    items(
                        count = postList.value.size,
                        itemContent = {
                            val post = postList.value[it]
                            AsyncImage(
                                model = post.photo,
                                contentDescription = "",
                                modifier = Modifier
                                    .size(138.dp)
                                    .padding(bottom = 2.dp))
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun UserCard(user: User) {
    Card(
        modifier = Modifier.padding(8.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Row (modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically){
            AsyncImage(
                model = user.profilePhoto,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(80.dp)
                    .fillMaxWidth()
                    .clip(CircleShape))
            Spacer(modifier = Modifier.width(20.dp))
            Column {
                Text(text = user.userName.toString(), fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(2.dp))
                Text(text = user.name.toString())
            }

        }
    }
}

