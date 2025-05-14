package com.vegh.mvisample

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.vegh.mvisample.peresentation.UserListViewModel
import com.vegh.mvisample.peresentation.photodetail.PhotoDetailScreen
import com.vegh.mvisample.peresentation.userList.GalleryScreen
import com.vegh.mvisample.peresentation.userList.componets.CustomTopBar
import com.vegh.mvisample.ui.theme.MvisampleTheme
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Retrofit

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            MvisampleTheme {

                Surface(modifier = Modifier.fillMaxSize()) {
                    Scaffold(
                        topBar = {
                            CustomTopBar(title = "Gallery")
                        }
                    ) { innerPadding ->

                AppNavHost()
                }}


            }
        }
    }
}




@Composable
fun AppNavHost(navController: NavHostController = rememberNavController()) {
    NavHost(navController, startDestination = "gallery") {
        composable("gallery") {
            GalleryScreen(navController = navController)
        }
        composable(
            route = "detail/{uri}/{isVideo}",
            arguments = listOf(
                navArgument("uri") { type = NavType.StringType },
                navArgument("isVideo") { type = NavType.BoolType }
            )
        ) { backStackEntry ->
            val uriString = backStackEntry.arguments?.getString("uri") ?: ""
            val isVideo = backStackEntry.arguments?.getBoolean("isVideo") ?: false
            uriString?.let {
                PhotoDetailScreen(uri = Uri.parse(uriString), isVideo = isVideo)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MvisampleTheme {


    }
}