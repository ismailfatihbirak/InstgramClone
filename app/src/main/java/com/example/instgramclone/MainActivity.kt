package com.example.instgramclone

import android.graphics.drawable.Icon
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.instgramclone.ui.theme.InstgramCloneTheme
import com.example.instgramclone.view.EditProfilePage
import com.example.instgramclone.view.ExplorePage
import com.example.instgramclone.view.HomePage
import com.example.instgramclone.view.MyProfilePage
import com.example.instgramclone.view.PostPage
import com.example.instgramclone.view.ProfilePage
import com.example.instgramclone.view.ReelsPage
import com.example.instgramclone.view.SignInPage
import com.example.instgramclone.view.SignUpPage
import com.example.instgramclone.view.SignUpPage2
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            InstgramCloneTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    PageTransations()
                }
            }
        }
    }
}
@Composable
fun PageTransations() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = "signuppage") {
        composable("signuppage") { backStackEntry ->
            SignUpPage(navController)
        }
        composable("signuppage2") { backStackEntry ->
            SignUpPage2(navController)
        }
        composable("signinpage") { backStackEntry ->
            SignInPage(navController)
        }
        composable("editprofilepage") { backStackEntry ->
            EditProfilePage(navController)
        }
        composable("homepage") { backStackEntry ->
            HomePage(navController)
        }
        composable("explorepage") { backStackEntry ->
            ExplorePage(navController)
        }
        composable("myprofilepage") { backStackEntry ->
            MyProfilePage(navController)
        }
        composable("postpage") { backStackEntry ->
            PostPage(navController)
        }
        composable("profilepage") { backStackEntry ->
            ProfilePage(navController)
        }
        composable("reelspage") { backStackEntry ->
            ReelsPage(navController)
        }


    }
}


