package edu.farmingdale.individualprojectaiello

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun AppNavigation(navController: NavHostController, viewModel: QuizViewModel = viewModel()) {
    NavHost(navController = navController, startDestination = "splash") {
        composable("splash") { SplashScreen(navController, viewModel) }
        composable("login") { LoginScreen(navController, viewModel) }
        composable("register") { RegisterScreen(navController, viewModel) }
        composable("rules") { RulesScreen(navController) } //States rules
        composable("quiz") { QuizScreen(navController, viewModel) }
        // Pass score and total Questions to state how many questions you got right out of total
        composable("result/{score}/{totalQuestions}") { backStackEntry ->
            val score = backStackEntry.arguments?.getString("score")?.toIntOrNull() ?: 0
            val totalQuestions = backStackEntry.arguments?.getString("totalQuestions")?.toIntOrNull() ?: viewModel.questions.size
            ResultScreen(navController, score, totalQuestions, viewModel)
        }
    }
}
