package com.example.mathia.screen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.IconButton
import com.example.mathia.R
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.mathia.navigation.Screen

@Composable
fun AppScreen(
    navController: NavHostController,
    showHomeButton: Boolean = true,
    content: @Composable () -> Unit
) {
    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(16.dp)
    ){
        Row(
            modifier =
                Modifier
                    .padding(16.dp)
        ) {
            if (showHomeButton){
                IconButton(
                    onClick = { navController.navigate(Screen.HomeScreen.route) }
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.home_icon),
                        contentDescription = "Accueil",
                        modifier = Modifier.size(32.dp)
                    )
                }
            }
        }
        content()
    }
}