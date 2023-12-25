package com.example.deskrstatistik.Navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.deskrstatistik.Screens.MainScreen

@Composable
fun Navigation(){
    val navCtrl = rememberNavController()

    NavHost(navController = navCtrl, startDestination = NavRoutes.MainScreen.name){
        composable(NavRoutes.MainScreen.name){
                MainScreen(navCtrl = navCtrl)
        }
    }
}