package com.example.instgramclone.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.bumptech.glide.integration.compose.GlideImage
import com.example.instgramclone.R
import com.example.instgramclone.viewmodel.HomePageViewModel
import kotlinx.coroutines.delay
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePage(navController: NavController,viewModel:HomePageViewModel) {
    viewModel.homePagePostListfun()
    val homePageList = viewModel.homePageList.observeAsState(listOf())

    Scaffold(
        topBar = {
            TopAppBar(
                colors = androidx.compose.material3.TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = Color.White,
                    titleContentColor = Color.White,
                ),
                modifier = Modifier.height(50.dp),
                title = {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomStart) {
                        Image(
                            painter = painterResource(id = R.drawable.topbarlogo),
                            contentDescription = "Top Bar Logo",
                            )
                    }
                },
                actions = {
                    IconButton(onClick = { /* do something */ }) {
                        Icon(
                            imageVector = Icons.Filled.FavoriteBorder,
                            contentDescription = "Localized description",
                            Modifier.size(32.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    IconButton(onClick = { /* do something */ }) {
                        Icon(
                            painter = painterResource(id = R.drawable.topbar_messenger_icon),
                            contentDescription = "Localized description",
                            Modifier.size(28.dp)
                        )
                    }
                })
        },
        bottomBar = {
            fun navigateToScreen(destination: String) {
                navController.navigate(destination)
            }
            BottomNavigation(onItemClick = :: navigateToScreen)

        }
    ){ innerPadding ->
        Column (
            modifier = Modifier
                .padding(innerPadding)
        ){
            Row{
                LazyRow {
                    item {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Box(contentAlignment = Alignment.BottomEnd) {
                                CustomDrawer(R.drawable.ic_launcher_background)
                                Icon(
                                    imageVector = Icons.Filled.Add,
                                    contentDescription = "Localized description",
                                    Modifier
                                        .size(32.dp)
                                        .padding(end = 13.dp, bottom = 13.dp),
                                    tint = Color.White
                                )
                            }
                            Text(
                                text = "Your story",
                                fontSize = 12.sp)
                        }
                    }
                    items(
                        count = homePageList.value.count(),
                        itemContent = {
                            val user = homePageList.value[it]
                            if (user.story != null){
                                Card(
                                    modifier = Modifier
                                        .padding(all = 8.dp).
                                        clickable {
                                            navController.navigate("storydetailpage/" +
                                                    "${URLEncoder.encode(user.profilePhoto, 
                                                        StandardCharsets.UTF_8.toString())}/" +
                                                    "${URLEncoder.encode(user.story, StandardCharsets.UTF_8.toString())}/" +
                                                    "${user.userName}") }
                                    , colors = CardDefaults.cardColors(
                                        containerColor = Color.White
                                    )) {
                                    Column (horizontalAlignment = Alignment.CenterHorizontally){
                                        AsyncImage(
                                            model = user.story,
                                            contentDescription = "",
                                            contentScale = ContentScale.Crop,
                                            modifier = Modifier
                                                .clip(CircleShape)
                                                .size(77.dp))
                                        Text(
                                            text = user.userName!!,
                                            fontSize = 12.sp, modifier = Modifier.padding(top = 6.dp))
                                    }
                                }
                            }
                        }
                    )
                }
            }
            LazyColumn {
                itemsIndexed(
                    items = homePageList.value ?: emptyList(),
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
                                        Text(text = "liked by daniel and 905,325 others", fontSize = 14.sp,
                                            modifier = Modifier.padding(start = 12.dp))
                                        Text(text = post.photoDescription!!, fontSize = 14.sp,
                                            modifier = Modifier.padding(start = 12.dp))
                                        TextButton(onClick = { /*TODO*/ }) {
                                            Text(text = "view all comments", fontSize = 14.sp, color = colorResource(
                                                id = R.color.CommentsAllText
                                            ))
                                        }


                                    }
                                }
                            }
                        }
                    }
                )
            }
        }

    }



}





@Composable
fun CustomDrawer(id:Int) {
    Image(painter = painterResource(id = id),
        contentDescription = "",
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .size(91.dp)
            .aspectRatio(1f)
            .background(
                Color.White
            )
            .padding(8.dp)
            .graphicsLayer {
                compositingStrategy = CompositingStrategy.Offscreen
            }
            .drawWithCache {
                val path = Path()
                path.addOval(
                    Rect(
                        topLeft = Offset.Zero,
                        bottomRight = Offset(size.width, size.height)
                    )
                )
                onDrawWithContent {
                    clipPath(path) {
                        this@onDrawWithContent.drawContent()
                    }
                    val dotSize = size.width / 5f
                    drawCircle(
                        Color.Black,
                        radius = dotSize,
                        center = Offset(
                            x = size.width - dotSize,
                            y = size.height - dotSize
                        ),
                        blendMode = BlendMode.Clear
                    )
                    drawCircle(
                        Color(0xFF009BFB), radius = dotSize * 0.8f,
                        center = Offset(
                            x = size.width - dotSize,
                            y = size.height - dotSize
                        )
                    )
                }

            }
    )
}

@Composable
fun Item() {
    Card(
        modifier = Modifier
            .padding(all = 8.dp)
        , colors = CardDefaults.cardColors(
            containerColor = Color.White
        )) {
        Column (horizontalAlignment = Alignment.CenterHorizontally){
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_background),
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .clip(CircleShape)
                    .size(77.dp)
            )
            Text(
                text = "daniel",
                fontSize = 12.sp, modifier = Modifier.padding(top = 6.dp))
        }
    }

}

@Composable
fun Item2() {
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
                        Image(
                            painter = painterResource(id = R.drawable.ic_launcher_background),
                            contentDescription = "",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .clip(CircleShape)
                                .size(50.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "daniel",
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
                Image(
                    painter = painterResource(id = R.drawable.ic_launcher_background),
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
                Text(text = "liked by daniel and 905,325 others", fontSize = 14.sp,
                    modifier = Modifier.padding(start = 12.dp))
                Text(text = "daniel photo description", fontSize = 14.sp,
                    modifier = Modifier.padding(start = 12.dp))
                TextButton(onClick = { /*TODO*/ }) {
                    Text(text = "view all comments", fontSize = 14.sp, color = colorResource(
                        id = R.color.CommentsAllText
                    ))
                }


            }
        }

}
sealed class BottomNavItem(
    var title: String,
    var icon: Int,
    var destination: String
) {
    object Home :
        BottomNavItem(
            "Home",
            R.drawable.bottom_bar_home,
            "homepage"
        )

    object Search :
        BottomNavItem(
            "Search",
            R.drawable.bottom_bar_search,
            "explorepage"
        )

    object Add :
        BottomNavItem(
            "Add",
            R.drawable.bottom_bar_add,
            "postpage"
        )

    object Reels :
        BottomNavItem(
            "Reels",
            R.drawable.bottom_bar_reels,
            "reelspage"
        )

    object Profile :
        BottomNavItem(
            "Profile",
            R.drawable.pp,
            "myprofilepage"
        )
}



@Composable
fun BottomNavigation(
    onItemClick: (String) -> Unit
) {
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Search,
        BottomNavItem.Add,
        BottomNavItem.Reels,
        BottomNavItem.Profile
    )

    NavigationBar(containerColor = Color.White) {
        items.forEach { item ->
            AddItem(
                screen = item,
                onClick = { onItemClick(item.destination) }
            )
        }
    }
}


@Composable
fun RowScope.AddItem(
    screen: BottomNavItem,
    onClick: () -> Unit
) {
    NavigationBarItem(

        label = {
            Text(text = screen.title)
        },

        // The icon resource
        icon = {
            Icon(
                painterResource(id = screen.icon),
                contentDescription = screen.title
            )
        },

        selected = true,

        alwaysShowLabel = true,

        onClick = onClick,

        colors = NavigationBarItemDefaults.colors(
            indicatorColor = Color.White
        )
    )
}







