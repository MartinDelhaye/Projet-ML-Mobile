package com.example.mathia

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.mathia.navigation.NavGraph
import com.example.mathia.network.RetrofitInstance
import com.example.mathia.ui.theme.MathIATheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MathIATheme {
                val navController = rememberNavController()
                LaunchedEffect(Unit) {
                    try {
                        RetrofitInstance.api.health()
                    } catch (e: Exception) {}
                }
                NavGraph(navController = navController)
            }
        }
    }
}