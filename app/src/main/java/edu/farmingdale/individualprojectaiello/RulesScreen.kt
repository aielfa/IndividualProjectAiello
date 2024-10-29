package edu.farmingdale.individualprojectaiello

import androidx.compose.foundation.layout.*
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
fun RulesScreen(navController: NavHostController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .gradientBackground() //background
            .padding(16.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.align(Alignment.Center)
        ) {
            Text("Quiz Rules", style = MaterialTheme.typography.titleLarge, color = Color.White)
            Spacer(modifier = Modifier.height(16.dp))
            Text(//Rules
                text = "1. Answer each question.\n" +
                        "2. Some questions may have multiple correct answers.\n" +
                        "3. Each correct answer awards you a point.\n" +
                        "4. Try to answer all questions correctly.",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(8.dp),
                color = Color.White
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    navController.navigate("quiz")
                },
                modifier = Modifier.padding(16.dp)
            ) {
                Text("Start Quiz")
            }
        }
    }
}
