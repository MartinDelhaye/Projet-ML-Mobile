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
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.ColorFilter
import com.example.mathia.ui.theme.Principal
import com.example.mathia.ui.theme.Secondary
import com.example.mathia.ui.theme.White

@Composable
fun AppScreen(
    navController: NavHostController,
    showHomeButton: Boolean = true,
    onInfoClick: (() -> Unit)? = null,
    content: @Composable () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Secondary)
    ) {
        // Barre de navigation
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Principal)
                .statusBarsPadding()
                .padding(8.dp)
        ) {
            // Bouton Home à gauche
            if (showHomeButton) {
                IconButton(
                    onClick = { navController.navigate(Screen.HomeScreen.route) },
                    modifier = Modifier.align(Alignment.CenterStart)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.icon_home),
                        contentDescription = "Accueil",
                        modifier = Modifier.size(24.dp),
                        colorFilter = ColorFilter.tint(White)
                    )
                }
            }

            // Bouton Info toujours à droite
            onInfoClick?.let {
                IconButton(
                    onClick = it,
                    modifier = Modifier.align(Alignment.CenterEnd)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.icon_info),
                        contentDescription = "Info",
                        modifier = Modifier.size(24.dp),
                        colorFilter = ColorFilter.tint(White)
                    )
                }
            }
        }

        // Contenu de la page
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            content()
        }
    }
}