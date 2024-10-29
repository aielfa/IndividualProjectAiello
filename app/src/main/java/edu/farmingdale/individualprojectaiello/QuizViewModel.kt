package edu.farmingdale.individualprojectaiello

import android.app.Application
import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel

class QuizViewModel(application: Application) : AndroidViewModel(application) {

    private val sharedPreferences = application.getSharedPreferences("QuizGamePrefs", Context.MODE_PRIVATE)

    // Registration/Login Values
    var user by mutableStateOf<User?>(null)
    var email by mutableStateOf("")
    var password by mutableStateOf("")
    var isUserLoggedIn by mutableStateOf(false)

    // Quiz Questions
    val questions = listOf(
        Question("What is 5 + 7?", listOf("10", "12", "15"), setOf("12")),
        Question("Select all prime numbers", listOf("2", "4", "5", "6"), setOf("2", "5")),
        Question("What is the square root of 64?", listOf("6", "8", "10"), setOf("8")),
        Question("Solve: 9 x 9", listOf("72", "81", "90"), setOf("81")),
        Question("What is 15 - 4?", listOf("10", "11", "12"), setOf("11")),
        Question("Select all multiples of 3", listOf("3", "6", "7", "9"), setOf("3", "6", "9")),
        Question("What is the value of π (pi) approximately?", listOf("2.14", "3.14", "4.14"), setOf("3.14")),
        Question("What is 20 ÷ 4?", listOf("4", "5", "6"), setOf("5")),
        Question("Select all even numbers", listOf("1", "2", "3", "4"), setOf("2", "4")),
        Question("Solve: 6 x 7", listOf("36", "42", "48"), setOf("42"))
    )

    var currentQuestionIndex by mutableStateOf(0)
    var score by mutableStateOf(0)
    var selectedAnswers by mutableStateOf(setOf<String>())

    init {
        resetLoginStatusOnStart() // Startup login screen
    }

    fun registerUser(firstName: String, familyName: String, email: String, password: String) {
        user = User(firstName, familyName, email, password)
        this.email = email
        this.password = password

        // Save login so if logging out you can log back in
        sharedPreferences.edit()
            .putString("email", email)
            .putString("password", password)
            .putBoolean("isLoggedIn", true)
            .apply()

        isUserLoggedIn = true
    }

    // Login
    fun loginUser(inputEmail: String, inputPassword: String): Boolean {
        val savedEmail = sharedPreferences.getString("email", null)
        val savedPassword = sharedPreferences.getString("password", null)
        //Check Credentials
        val success = inputEmail == savedEmail && inputPassword == savedPassword
        if (success) {
            saveLoginStatus(true)
            isUserLoggedIn = true
        } else {
            isUserLoggedIn = false
        }
        return success
    }
    private fun resetLoginStatusOnStart() {
        sharedPreferences.edit().putBoolean("isLoggedIn", false).apply()
        isUserLoggedIn = false
        email = ""
        password = ""
        user = null
    }
    private fun saveLoginStatus(isLoggedIn: Boolean) {
        sharedPreferences.edit().putBoolean("isLoggedIn", isLoggedIn).apply()
        isUserLoggedIn = isLoggedIn
    }
    fun logoutUser() {
        saveLoginStatus(false)
        sharedPreferences.edit()
            .remove("email")
            .remove("password")
            .apply()

        isUserLoggedIn = false
        email = ""
        password = ""
        user = null
    }

    fun getCurrentQuestion(): Question? {
        return if (currentQuestionIndex in questions.indices) {
            questions[currentQuestionIndex]
        } else {
            null // Return null if index is out of bounds
        }
    }

    // Go to next question
    fun nextQuestion() {
        if (currentQuestionIndex < questions.size - 1) {
            currentQuestionIndex++
            selectedAnswers = emptySet() // Reset selections for the next question
        }
    }

    fun resetQuiz() {
        score = 0
        currentQuestionIndex = 0
        selectedAnswers = emptySet()
    }
}
