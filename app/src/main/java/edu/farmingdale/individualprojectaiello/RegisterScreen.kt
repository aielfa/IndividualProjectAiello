package edu.farmingdale.individualprojectaiello

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import edu.farmingdale.individualprojectaiello.ui.theme.gradientBackground

@Composable
fun RegisterScreen(navController: NavHostController, viewModel: QuizViewModel) {
    var firstName by remember { mutableStateOf("") }
    var familyName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var dateOfBirth by remember { mutableStateOf("") }

    // Error messages
    var firstNameError by remember { mutableStateOf<String?>(null) }
    var emailError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }
    var dobError by remember { mutableStateOf<String?>(null) }

    //make sure valid inputs are put in
    fun isValidFirstName(name: String): Boolean {
        return name.length in 3..30
    }

    fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun isValidPassword(password: String): Boolean {
        return password.length >= 6
    }

    fun isValidDateOfBirth(dob: String): Boolean {
        //MM-DD-YYYY format
        val dateRegex = Regex("""^(0[1-9]|1[0-2])-(0[1-9]|[12]\d|3[01])-\d{4}$""")
        return dateRegex.matches(dob)
    }

    // background
    Box(
        modifier = Modifier
            .fillMaxSize()
            .gradientBackground()
            .padding(16.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .align(Alignment.Center)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            //back
            Button(
                onClick = { navController.popBackStack() },
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(bottom = 16.dp)
            ) {
                Text("Back")
            }

            Text("Register", style = MaterialTheme.typography.titleLarge, color = Color.White)

            // First Name valid
            TextField(
                value = firstName,
                onValueChange = {
                    firstName = it
                    firstNameError = if (isValidFirstName(firstName)) null else "First name must be between 3 and 30 characters"
                },
                label = { Text("First Name") },
                isError = firstNameError != null,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )
            if (firstNameError != null) {
                Text(firstNameError!!, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)
            }


            TextField(
                value = familyName,
                onValueChange = { familyName = it },
                label = { Text("Family Name") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )

            // DOB with numerical keyboard
            TextField(
                value = dateOfBirth,
                onValueChange = {
                    dateOfBirth = it
                    dobError = if (isValidDateOfBirth(dateOfBirth)) null else "Enter date as MM-DD-YYYY"
                },
                label = { Text("Date of Birth (MM-DD-YYYY)") },
                isError = dobError != null,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )
            if (dobError != null) {
                Text(dobError!!, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)
            }

            // Email valid
            TextField(
                value = email,
                onValueChange = {
                    email = it
                    emailError = if (isValidEmail(email)) null else "Enter a valid email"
                },
                label = { Text("Email") },
                isError = emailError != null,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )
            if (emailError != null) {
                Text(emailError!!, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)
            }

            // Password valid
            TextField(
                value = password,
                onValueChange = {
                    password = it
                    passwordError = if (isValidPassword(password)) null else "Password must be at least 6 characters"
                },
                label = { Text("Password") },
                visualTransformation = PasswordVisualTransformation(),
                isError = passwordError != null,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )
            if (passwordError != null) {
                Text(passwordError!!, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)
            }

            // Register
            Button(
                onClick = {
                    if (isValidFirstName(firstName) && isValidEmail(email) && isValidPassword(password) && isValidDateOfBirth(dateOfBirth)) {
                        viewModel.registerUser(firstName, familyName, email, password)
                        navController.popBackStack()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            ) {
                Text("Register")
            }
        }
    }
}
