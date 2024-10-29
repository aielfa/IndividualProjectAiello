package edu.farmingdale.individualprojectaiello

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import edu.farmingdale.individualprojectaiello.ui.theme.gradientBackground

@Composable
fun LoginScreen(navController: NavHostController, viewModel: QuizViewModel) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var emailError by remember { mutableStateOf(false) }
    var passwordError by remember { mutableStateOf(false) }
    var loginError by remember { mutableStateOf(false) }

    // Email is email
    fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .gradientBackground()
            .padding(16.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.align(Alignment.Center)
        ) {
            Text("Login", style = MaterialTheme.typography.titleLarge, color = Color.White)

            TextField(
                value = email,
                onValueChange = {
                    email = it
                    emailError = !isValidEmail(email)
                    loginError = false
                },
                label = { Text("Email") },
                isError = emailError,
                modifier = Modifier.padding(16.dp)
            )
            if (emailError) {
                Text("Invalid email format", color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)
            }

            TextField(
                value = password,
                onValueChange = {
                    password = it
                    passwordError = password.isEmpty()
                    loginError = false
                },
                label = { Text("Password") },
                visualTransformation = PasswordVisualTransformation(),
                isError = passwordError,
                modifier = Modifier.padding(16.dp)
            )
            if (passwordError) {
                Text("Password cannot be empty", color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)
            }

            Button(
                onClick = {
                    if (viewModel.loginUser(email, password)) {
                        navController.navigate("rules") { //Say rules
                            popUpTo("login") { inclusive = true }
                        }
                    } else {
                        loginError = true
                    }
                },
                enabled = email.isNotEmpty() && password.isNotEmpty() && !emailError && !passwordError,
                modifier = Modifier.padding(16.dp)
            ) {
                Text("Login")
            }

            if (loginError) {
                Text("Incorrect email or password", color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)
            }

            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = { navController.navigate("register") }) {
                Text("Register")
            }
        }
    }
}
