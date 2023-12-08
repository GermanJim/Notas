package com.example.notes

import android.Manifest.permission.POST_NOTIFICATIONS
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.notes.navigation.SetupNavigation
import com.example.notes.ui.theme.NotesTheme
import com.example.notes.ui.viewmodels.SharedViewModel
import com.example.notes.utils.GlobalVariable
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var navController: NavHostController
    private lateinit var sharedViewModel: SharedViewModel

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedViewModel = ViewModelProvider(this)[SharedViewModel::class.java]

        installSplashScreen().apply {
            setKeepOnScreenCondition {
                sharedViewModel.shouldShowSplashScreen.value
            }
        }

        if (sharedViewModel.shouldShowSplashScreen.value) {
            requestNotificationPermission()
        }

        setContent {

            NotesTheme {
                navController = rememberNavController()
                SetupNavigation(navController = navController, sharedViewModel = sharedViewModel)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    fun requestNotificationPermission() {
        val requestPermissionLauncher =
            registerForActivityResult(
                ActivityResultContracts.RequestPermission()
            ) { isGranted: Boolean ->
                Log.d("NOTIFICATION_PERMISSION", isGranted.toString())
                GlobalVariable.hasNotificationPermission = isGranted
            }
        requestPermissionLauncher.launch(
            POST_NOTIFICATIONS
        )
    }
}


