package edu.farmingdale.individualprojectaiello

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import edu.farmingdale.individualprojectaiello.ui.theme.gradientBackground

@Composable
fun ResultScreen(
    navController: NavHostController,
    score: Int,
    totalQuestions: Int,
    viewModel: QuizViewModel
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .gradientBackground()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.align(Alignment.Center),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Display result out of total questions
            Text(
                text = "Your Score: $score / $totalQuestions",
                style = MaterialTheme.typography.displayMedium,
                color = Color.White
            )

            // Redo quiz button
            Button(
                onClick = {
                    viewModel.resetQuiz()
                    navController.navigate("quiz") {
                        popUpTo("quiz") { inclusive = true }
                    }
                },
                modifier = Modifier.padding(16.dp)
            ) {
                Text("Try Again")
            }

            // Log out button
            Button(
                onClick = {
                    viewModel.logoutUser()
                    navController.navigate("login") {
                        popUpTo("login") { inclusive = true }
                    }
                },
                modifier = Modifier.padding(16.dp)
            ) {
                Text("Logout")
            }
        }
    }
}
