package edu.farmingdale.individualprojectaiello

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import edu.farmingdale.individualprojectaiello.ui.theme.gradientBackground

@Composable
fun QuizScreen(navController: NavHostController, viewModel: QuizViewModel) {
    val question = viewModel.getCurrentQuestion()
    val currentQuestionIndex = viewModel.currentQuestionIndex + 1 //track questions
    val totalQuestions = viewModel.questions.size

    var isAnswerCorrect by remember { mutableStateOf<Boolean?>(null) }
    var hasSubmitted by remember { mutableStateOf(false) }
    var feedbackVisible by remember { mutableStateOf(false) }

    if (question == null) {
        // Go to result screen
        navController.navigate("result/${viewModel.score}/$totalQuestions")
    } else {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .gradientBackground() // background
                .padding(25.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.align(Alignment.Center)
            ) {
                Text(question.questionText, style = MaterialTheme.typography.titleLarge, color = Color.White)

                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.padding(top = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(question.answers.size) { index ->
                        val answer = question.answers[index]
                        val isSelected = viewModel.selectedAnswers.contains(answer)

                        // Background correct or incorrect
                        val backgroundColor = when {
                            feedbackVisible && isSelected && isAnswerCorrect == true -> Color.Green
                            feedbackVisible && isSelected && isAnswerCorrect == false -> Color.Red
                            else -> MaterialTheme.colorScheme.primary
                        }

                        Text(
                            text = answer,
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier
                                .padding(8.dp)
                                .border(
                                    BorderStroke(2.dp, if (isSelected) MaterialTheme.colorScheme.primary else Color.Transparent),
                                    shape = MaterialTheme.shapes.medium
                                )
                                .background(
                                    brush = Brush.linearGradient(listOf(backgroundColor, backgroundColor)),
                                    shape = MaterialTheme.shapes.medium
                                )
                                .clickable(enabled = !hasSubmitted) { // Disable selection after submission
                                    viewModel.selectedAnswers = if (isSelected) {
                                        viewModel.selectedAnswers - answer
                                    } else {
                                        viewModel.selectedAnswers + answer
                                    }
                                }
                                .padding(16.dp),
                            color = if (isSelected) Color.White else MaterialTheme.colorScheme.onBackground
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                if (feedbackVisible) {
                    Text(
                        text = if (isAnswerCorrect == true) "Correct!" else "Incorrect!",
                        color = if (isAnswerCorrect == true) Color.Green else Color.Red,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(top = 16.dp)
                    )
                }

                // Submit button
                Button(
                    onClick = {
                        isAnswerCorrect = viewModel.selectedAnswers == question.correctAnswers
                        hasSubmitted = true
                        feedbackVisible = true

                        // Increment score only if the answer is correct
                        if (isAnswerCorrect == true) {
                            viewModel.score++
                        }
                    },
                    enabled = !hasSubmitted, // Enable only if not yet submitted
                    modifier = Modifier.padding(top = 16.dp)
                ) {
                    Text("Submit")
                }

                // Next or Finish button
                Button(
                    onClick = {
                        if (viewModel.currentQuestionIndex >= totalQuestions - 1) {
                            navController.navigate("result/${viewModel.score}/$totalQuestions")
                        } else {
                            viewModel.nextQuestion()
                            hasSubmitted = false
                            feedbackVisible = false
                            isAnswerCorrect = null
                        }
                    },
                    enabled = hasSubmitted,
                    modifier = Modifier.padding(top = 16.dp)
                ) {
                    Text(if (viewModel.currentQuestionIndex >= totalQuestions - 1) "Finish" else "Next")
                }
            }

            // Progress bar at the bottom right
            Text(
                text = "Question $currentQuestionIndex / $totalQuestions",
                style = MaterialTheme.typography.titleMedium.copy(fontSize = 18.sp),
                color = Color.White,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(8.dp)
            )
        }
    }
}
