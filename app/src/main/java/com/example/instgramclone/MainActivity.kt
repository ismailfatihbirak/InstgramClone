package com.example.instgramclone

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
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
import com.example.instgramclone.view.StoryDetailPage
import com.example.instgramclone.viewmodel.EditProfilePageViewModel
import com.example.instgramclone.viewmodel.HomePageViewModel
import com.example.instgramclone.viewmodel.MyProfileViewModel
import com.example.instgramclone.viewmodel.PostPageViewModel
import com.example.instgramclone.viewmodel.ReelsPageViewModel
import com.example.instgramclone.viewmodel.SignInPageViewModel
import com.example.instgramclone.viewmodel.SignUpPage2ViewModel
import com.google.android.gms.auth.api.identity.Identity
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
    NavHost(navController, startDestination = "explorepage") {
        composable("signuppage") { backStackEntry ->
            val viewModel = hiltViewModel<SignUpPage2ViewModel>()
            SignUpPage(navController,viewModel)
        }
        composable("signuppage2") { backStackEntry ->
            val viewModel = hiltViewModel<SignUpPage2ViewModel>()
            SignUpPage2(navController,viewModel)
        }
        composable("signinpage") { backStackEntry ->
            val viewModel = hiltViewModel<SignInPageViewModel>()
            SignInPage(navController,viewModel)
        }
        composable("editprofilepage") { backStackEntry ->
            val viewModel = hiltViewModel<EditProfilePageViewModel>()
            EditProfilePage(navController,viewModel)
        }
        composable("homepage") { backStackEntry ->
            val viewModel = hiltViewModel<HomePageViewModel>()
            HomePage(navController,viewModel)
        }
        composable("explorepage") { backStackEntry ->
            ExplorePage(navController)
        }
        composable("myprofilepage") { backStackEntry ->
            val viewModel = hiltViewModel<MyProfileViewModel>()
            MyProfilePage(navController,viewModel)
        }
        composable("postpage") { backStackEntry ->
            val viewModel = hiltViewModel<PostPageViewModel>()
            PostPage(navController,viewModel)
        }
        composable("profilepage") { backStackEntry ->
            ProfilePage(navController)
        }
        composable("reelspage") { backStackEntry ->
            val viewModel = hiltViewModel<ReelsPageViewModel>()
            ReelsPage(navController,viewModel)
        }
        composable("storydetailpage/{profilePhoto}/{photo}/{userName}",
            arguments = listOf(
                navArgument("profilePhoto") { type = NavType.StringType},
                navArgument("photo") { type = NavType.StringType},
                navArgument("userName") { type = NavType.StringType}
            )
            ) { backStackEntry ->
            val profilePhoto = backStackEntry.arguments?.getString("profilePhoto")!!
            val photo = backStackEntry.arguments?.getString("photo")!!
            val userName = backStackEntry.arguments?.getString("userName")!!
            StoryDetailPage(navController,profilePhoto,photo,userName)
        }


    }
}


