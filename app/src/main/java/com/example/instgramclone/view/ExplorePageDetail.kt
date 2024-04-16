package com.example.instgramclone.view

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.instgramclone.R
import com.example.instgramclone.model.Post
import com.example.instgramclone.model.User
import com.example.instgramclone.viewmodel.HomePageViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExplorePageDetail(index: Int,viewModel: HomePageViewModel) {
    viewModel.getHomePagePostReelsList()
    val homePageList = viewModel.homePageList.observeAsState(listOf())

    Scaffold (
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = Color.White,
                    titleContentColor = Color.White,
                ),
                modifier = Modifier.height(50.dp),
                title = {
                    Text(
                        text = "Explore",
                        fontWeight = FontWeight.Bold)
                },
                navigationIcon = {
                    IconButton(onClick = {  }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Localized description",
                            Modifier.size(32.dp)
                        )
                    }
                }
            )
        }
    ){innerPadding ->
        Column (
            modifier = Modifier
                .padding(innerPadding)
        ){
            LazyColumnExploreDetail(homePageList.value,index)
        }

    }
}

@Composable
fun LazyColumnExploreDetail(homePageList:List<User>,index: Int) {
    LazyColumn {
        itemsIndexed(
            items = homePageList ?: emptyList(),
            itemContent = { userIndex, user ->
                user.posts?.let { postList ->
                    postList.forEachIndexed { postIndex, post ->
                        Card(colors = CardDefaults.cardColors(
                            containerColor = Color.White
                        )) {
                            Column {
                                Row(
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(start = 10.dp, end = 10.dp, top = 5.dp)) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        AsyncImage(
                                            model = user!!.profilePhoto,
                                            contentDescription = "",
                                            contentScale = ContentScale.Crop,
                                            modifier = Modifier
                                                .clip(CircleShape)
                                                .size(50.dp))
                                        Spacer(modifier = Modifier.width(10.dp))
                                        Text(
                                            text = user.userName!!,
                                            fontSize = 14.sp)
                                    }
                                    IconButton(onClick = { /*TODO*/ }) {
                                        Icon(
                                            imageVector = Icons.Filled.MoreVert,
                                            contentDescription = "",
                                            Modifier.size(24.dp)
                                        )
                                    }
                                }
                                AsyncImage(
                                    model = post!!.photo,
                                    contentDescription = "",
                                    modifier = Modifier.size(450.dp,450.dp))
                                Row {
                                    IconButton(onClick = { /*TODO*/ }) {
                                        Icon(
                                            imageVector = Icons.Filled.FavoriteBorder,
                                            contentDescription = "",
                                            Modifier.size(29.dp)
                                        )
                                    }
                                    IconButton(onClick = { /*TODO*/ }) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.comment_icon),
                                            contentDescription = "",
                                            Modifier.size(24.dp))
                                    }
                                    IconButton(onClick = { /*TODO*/ }) {
                                        Icon(
                                            imageVector = Icons.Filled.Send,
                                            contentDescription = "",
                                            Modifier.size(24.dp)
                                        )
                                    }

                                }
                                Text(text = "liked by daniel and 905,325 others",
                                    fontSize = 14.sp,
                                    modifier = Modifier.padding(start = 12.dp))
                                Text(text = post.photoDescription!!,
                                    fontSize = 14.sp,
                                    modifier = Modifier.padding(start = 12.dp))
                                TextButton(onClick = { /*TODO*/ }) {
                                    Text(text = "view all comments",
                                        fontSize = 14.sp,
                                        color = colorResource(id = R.color.CommentsAllText))
                                }


                            }
                        }
                    }
                }
            }
        )
    }
}





