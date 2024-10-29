package edu.farmingdale.individualprojectaiello
data class Question(
    val questionText: String,
    val answers: List<String>,
    val correctAnswers: Set<String>
)//Stores Questions values
