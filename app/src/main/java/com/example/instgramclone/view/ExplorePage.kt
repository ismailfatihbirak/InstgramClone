package com.example.instgramclone.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarColors
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.instgramclone.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExplorePage(navController: NavController) {
    var text by remember { mutableStateOf("") } // Query for SearchBar
    var active by remember { mutableStateOf(false) } // Active state for SearchBar

    Scaffold(
        topBar = {
            SearchBar(modifier = Modifier.fillMaxWidth().padding(10.dp),
                query = text,
                onQueryChange = {
                    text = it
                },
                onSearch = {
                    active = false
                },
                active = active,
                onActiveChange = {
                    active = it
                },
                placeholder = {
                    Text(text = "Search")
                },
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Search, contentDescription = null)
                }) {}
        },
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



    }
}

