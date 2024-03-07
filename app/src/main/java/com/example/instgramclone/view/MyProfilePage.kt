package com.example.instgramclone.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import androidx.navigation.NavController
import com.example.instgramclone.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyProfilePage(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = Color.White,
                    titleContentColor = Color.White,
                ),
                modifier = Modifier.height(50.dp),
                title = {
                    Row (verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Absolute.Left,
                        modifier = Modifier.fillMaxSize()){
                        Text(
                            text = "daniel_aburizzi",
                            fontSize = 20.sp,
                            color = Color.Black,
                            fontWeight = FontWeight.Medium)
                    }


                },
                navigationIcon = {
                    IconButton(onClick = { /* do something */ }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Localized description",
                            Modifier.size(24.dp)
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { /* do something */ }) {
                        Icon(
                            imageVector = Icons.Filled.MoreVert,
                            contentDescription = "Localized description",
                            Modifier.size(28.dp)
                        )
                    }

                })
        },
        bottomBar = {
            fun navigateToScreen(destination: String) {
                navController.navigate(destination){
                    popUpTo("myprofilepage"){ inclusive = true}
                }
            }
            BottomNavigation(onItemClick = :: navigateToScreen)

        }
    ){ innerPadding ->
        Column (
            modifier = Modifier
                .padding(innerPadding)
        ){
            Row(verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = 10.dp)) {
                Image(
                    painter = painterResource(id = R.drawable.ic_launcher_background),
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(90.dp)
                )
                Column (horizontalAlignment = Alignment.CenterHorizontally){
                    Text(text = "25",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Medium)
                    Text(text = "Posts",
                        fontSize = 12.sp)
                }
                Column (horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "500",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Medium)
                    Text(text = "Followers",
                        fontSize = 12.sp)
                }
                Column (horizontalAlignment = Alignment.CenterHorizontally){
                    Text(text = "300",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Medium)
                    Text(text = "Following",
                        fontSize = 12.sp)
                }

            }
            Text(text = "Daniel Aburizzi",
                fontSize = 14.sp,
                modifier = Modifier.padding(start = 10.dp))
            Text(text = "Bio Description",
                fontSize = 14.sp,
                modifier = Modifier.padding(start = 10.dp))
            Spacer(modifier = Modifier.height(20.dp))
            Row (horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = 15.dp)){
                Button(onClick = { /*TODO*/ }, colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.tf_color),
                    contentColor = Color.Black
                ), modifier = Modifier.size(195.dp,35.dp)) {
                    Text(text = "Edit Profile", fontSize = 12.sp)
                }
                Button(onClick = { /*TODO*/ }, colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.tf_color),
                    contentColor = Color.Black
                ), modifier = Modifier.size(195.dp,35.dp)) {
                    Text(text = "Share Profile", fontSize = 12.sp)
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            MyProfileTabExample()
        }

    }
}

@Composable
fun MyProfileTabExample() {
    val selectedTabIndex = remember { mutableStateOf(0) }

    Column {

        TabRow(
            selectedTabIndex = selectedTabIndex.value,
            containerColor = Color.White,
            contentColor = Color.Black
        ) {
            Tab(
                selected = selectedTabIndex.value == 0,
                onClick = { selectedTabIndex.value = 0 },
                icon = { Icon(painter = painterResource(id = R.drawable.tab_icon), contentDescription = "") }
            )
            Tab(
                selected = selectedTabIndex.value == 1,
                onClick = { selectedTabIndex.value = 1 },
                icon = { Icon(painter = painterResource(id = R.drawable.tab_icon_video), contentDescription = "") }
            )
        }

        when (selectedTabIndex.value) {
            0 -> {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(3)
                ) {
                    item{
                        Image(painter = painterResource(id = R.drawable.ic_launcher_background), contentDescription = "",
                            modifier = Modifier.size(145.dp).padding(bottom = 2.dp))
                    }
                    item{
                        Image(painter = painterResource(id = R.drawable.ic_launcher_background), contentDescription = "",
                            modifier = Modifier.size(145.dp).padding(bottom = 2.dp))
                    }
                    item{
                        Image(painter = painterResource(id = R.drawable.ic_launcher_background), contentDescription = "",
                            modifier = Modifier.size(145.dp).padding(bottom = 2.dp))
                    }
                    item{
                        Image(painter = painterResource(id = R.drawable.ic_launcher_background), contentDescription = "",
                            modifier = Modifier.size(145.dp).padding(bottom = 2.dp))
                    }
                    item{
                        Image(painter = painterResource(id = R.drawable.ic_launcher_background), contentDescription = "",
                            modifier = Modifier.size(145.dp).padding(bottom = 2.dp))
                    }
                    item{
                        Image(painter = painterResource(id = R.drawable.ic_launcher_background), contentDescription = "",
                            modifier = Modifier.size(145.dp).padding(bottom = 2.dp))
                    }

                }
            }
            1 -> {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),

                    ) {
                    item{
                        Image(painter = painterResource(id = R.drawable.grid_foto), contentDescription = "",
                            modifier = Modifier
                                .height(250.dp)
                                .padding(bottom = 2.dp))
                    }
                    item{
                        Image(painter = painterResource(id = R.drawable.grid_foto), contentDescription = "",
                            modifier = Modifier
                                .height(250.dp)
                                .padding(bottom = 2.dp))
                    }
                    item{
                        Image(painter = painterResource(id = R.drawable.grid_foto), contentDescription = "",
                            modifier = Modifier
                                .height(250.dp)
                                .padding(bottom = 2.dp))
                    }
                    item{
                        Image(painter = painterResource(id = R.drawable.grid_foto), contentDescription = "",
                            modifier = Modifier
                                .height(250.dp)
                                .padding(bottom = 2.dp))
                    }
                    item{
                        Image(painter = painterResource(id = R.drawable.grid_foto), contentDescription = "",
                            modifier = Modifier
                                .height(250.dp)
                                .padding(bottom = 2.dp))
                    }
                    item{
                        Image(painter = painterResource(id = R.drawable.grid_foto), contentDescription = "",
                            modifier = Modifier
                                .height(250.dp)
                                .padding(bottom = 2.dp))
                    }

                }

            }
        }
    }
}