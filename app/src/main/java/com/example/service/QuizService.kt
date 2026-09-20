package com.example.service

import com.example.data.model.QuizData
import com.example.data.model.QuizQuestion
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject

class QuizService(
    private val geminiApiClient: GeminiApiClient,
    private val networkService: NetworkService
) {

    // Offline question bank for common curriculum topics
    private val photosynthesisQuestions = listOf(
        QuizQuestion(
            id = 1,
            question = "Which pigment gives leaves their green color and traps solar energy?",
            options = listOf("Chlorophyll", "Hemoglobin", "Carotene", "Melanin"),
            correctOptionIndex = 0,
            explanation = "Chlorophyll is the green pigment in plant chloroplasts responsible for absorbing sunlight."
        ),
        QuizQuestion(
            id = 2,
            question = "What gas do plants absorb from the atmosphere for photosynthesis?",
            options = listOf("Oxygen", "Carbon Dioxide", "Nitrogen", "Hydrogen"),
            correctOptionIndex = 1,
            explanation = "Plants take in Carbon Dioxide (CO2) through microscopic pores called stomata."
        ),
        QuizQuestion(
            id = 3,
            question = "What are the two primary end-products of the photosynthetic reaction?",
            options = listOf("Water and Nitrogen", "Glucose and Oxygen", "Carbon Monoxide and Salt", "Alcohol and Methane"),
            correctOptionIndex = 1,
            explanation = "The chemical equation yields simple sugar (Glucose, C6H12O6) and Oxygen gas (O2)."
        ),
        QuizQuestion(
            id = 4,
            question = "Which part of the plant cell is known as the 'kitchen of the cell'?",
            options = listOf("Mitochondria", "Nucleus", "Chloroplast", "Ribosome"),
            correctOptionIndex = 2,
            explanation = "Chloroplasts contain chlorophyll where photosynthetic synthesis actually takes place."
        ),
        QuizQuestion(
            id = 5,
            question = "Through which vascular tissue do plants transport water from roots to leaves?",
            options = listOf("Phloem", "Xylem", "Cortex", "Epidermis"),
            correctOptionIndex = 1,
            explanation = "Xylem vessels conduct water and dissolved mineral ions upward from the soil."
        )
    )

    private val algebraQuestions = listOf(
        QuizQuestion(
            id = 1,
            question = "What is the maximum degree of the variable in a linear equation?",
            options = listOf("0", "1", "2", "3"),
            correctOptionIndex = 1,
            explanation = "A linear equation has an algebraic degree of strictly 1."
        ),
        QuizQuestion(
            id = 2,
            question = "Solve for x: 2x + 6 = 18",
            options = listOf("x = 4", "x = 6", "x = 8", "x = 12"),
            correctOptionIndex = 1,
            explanation = "Subtract 6 from both sides: 2x = 12, then divide by 2: x = 6."
        ),
        QuizQuestion(
            id = 3,
            question = "In the equation 5y - 3 = 12, what is the term '5' called?",
            options = listOf("Constant", "Coefficient", "Exponent", "Variable"),
            correctOptionIndex = 1,
            explanation = "The number multiplied by a variable is called its coefficient."
        ),
        QuizQuestion(
            id = 4,
            question = "If x/3 = 7, what is the value of x?",
            options = listOf("10", "14", "21", "24"),
            correctOptionIndex = 2,
            explanation = "Multiply both sides by 3: x = 7 * 3 = 21."
        ),
        QuizQuestion(
            id = 5,
            question = "Which of the following is NOT a linear equation?",
            options = listOf("3x + 2 = 9", "x^2 + 4 = 20", "7y - 5 = 10", "4a + 1 = 13"),
            correctOptionIndex = 1,
            explanation = "x^2 + 4 = 20 is a quadratic equation because the degree is 2."
        )
    )

    suspend fun generateQuiz(
        topic: String,
        sourceText: String? = null,
        questionCount: Int = 5,
        allowOnline: Boolean = true
    ): QuizData = withContext(Dispatchers.Default) {
        val normalized = topic.trim().lowercase()
        val isOnlineAvailable = networkService.isOnline.value && geminiApiClient.isConfigured()

        // 1. Try online Gemini generation if enabled
        if (allowOnline && isOnlineAvailable) {
            val contextSnippet = if (!sourceText.isNullOrBlank()) "Content to base quiz on: $sourceText" else "Topic: $topic"
            val prompt = """
                Generate a $questionCount-question multiple-choice school quiz based on: $contextSnippet.
                Respond with a valid JSON array of question objects matching:
                [
                  {
                    "question": "Question text here",
                    "options": ["Option A", "Option B", "Option C", "Option D"],
                    "correctOptionIndex": 0,
                    "explanation": "Brief explanation why this option is correct"
                  }
                ]
            """.trimIndent()

            val result = geminiApiClient.generateContent(prompt)
            if (result.isSuccess) {
                val jsonStr = result.getOrNull()?.trim() ?: ""
                val parsedQuestions = parseQuizJson(jsonStr)
                if (parsedQuestions.isNotEmpty()) {
                    return@withContext QuizData(
                        title = "$topic Quiz",
                        topic = topic,
                        questions = parsedQuestions.take(questionCount)
                    )
                }
            }
        }

        // 2. Offline Fallback
        delay(500)
        val selectedQuestions = when {
            normalized.contains("algebra") || normalized.contains("math") || normalized.contains("equation") -> {
                algebraQuestions
            }
            else -> {
                photosynthesisQuestions
            }
        }

        QuizData(
            title = "$topic Smart Quiz",
            topic = topic,
            questions = selectedQuestions.take(questionCount)
        )
    }

    private fun parseQuizJson(raw: String): List<QuizQuestion> {
        return try {
            val cleanJson = raw.removePrefix("```json").removePrefix("```").removeSuffix("```").trim()
            val array = JSONArray(cleanJson)
            val list = mutableListOf<QuizQuestion>()
            for (i in 0 until array.length()) {
                val obj = array.getJSONObject(i)
                val q = obj.getString("question")
                val optArray = obj.getJSONArray("options")
                val options = mutableListOf<String>()
                for (j in 0 until optArray.length()) {
                    options.add(optArray.getString(j))
                }
                val correctIndex = obj.getInt("correctOptionIndex")
                val exp = obj.optString("explanation", "Correct answer verified.")
                list.add(QuizQuestion(id = i + 1, question = q, options = options, correctOptionIndex = correctIndex, explanation = exp))
            }
            list
        } catch (e: Exception) {
            emptyList()
        }
    }
}
