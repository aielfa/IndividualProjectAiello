package edu.farmingdale.individualprojectaiello

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import edu.farmingdale.individualprojectaiello.ui.theme.IndividualProjectAielloTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            QuizGameApp()
        }
    }
}

@Composable
fun QuizGameApp() {
    IndividualProjectAielloTheme {  // Pulls from Theme.kt
        val navController = rememberNavController()
        val viewModel: QuizViewModel = viewModel()

        Surface(color = MaterialTheme.colorScheme.background) {
            AppNavigation(navController, viewModel)
        }
    }
}
