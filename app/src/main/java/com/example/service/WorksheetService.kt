package com.example.service

import com.example.data.model.WorksheetData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import org.json.JSONObject

class WorksheetService(
    private val geminiApiClient: GeminiApiClient,
    private val networkService: NetworkService
) {

    suspend fun generateWorksheet(
        subject: String,
        grade: String,
        topic: String,
        language: String,
        difficulty: String,
        questionCount: Int = 10,
        allowOnline: Boolean = true
    ): WorksheetData = withContext(Dispatchers.Default) {
        val isOnlineAvailable = networkService.isOnline.value && geminiApiClient.isConfigured()

        if (allowOnline && isOnlineAvailable) {
            val prompt = """
                Generate a comprehensive school worksheet for:
                Subject: $subject, Grade: $grade, Topic: $topic, Language: $language, Difficulty: $difficulty.
                Total questions: $questionCount.
                
                Respond in valid JSON with these keys:
                {
                  "multipleChoiceQuestions": ["1. Question with (A) (B) (C) (D)", "2. Question..."],
                  "fillInTheBlanks": ["1. Sentence with ______ blank", "2. Sentence..."],
                  "shortAnswerQuestions": ["1. Explain ...", "2. State ..."],
                  "answerKey": ["MCQ 1: A", "MCQ 2: B", "Blank 1: answer", "Short 1: key points"]
                }
            """.trimIndent()

            val result = geminiApiClient.generateContent(prompt)
            if (result.isSuccess) {
                val parsed = parseWorksheetJson(subject, grade, topic, language, difficulty, result.getOrNull() ?: "")
                if (parsed != null) return@withContext parsed
            }
        }

        // Offline Template Generation
        delay(600)
        generateOfflineWorksheet(subject, grade, topic, language, difficulty, questionCount)
    }

    private fun parseWorksheetJson(
        subject: String,
        grade: String,
        topic: String,
        language: String,
        difficulty: String,
        raw: String
    ): WorksheetData? {
        return try {
            val cleanJson = raw.removePrefix("```json").removePrefix("```").removeSuffix("```").trim()
            val obj = JSONObject(cleanJson)
            val mcqArray = obj.optJSONArray("multipleChoiceQuestions")
            val fillArray = obj.optJSONArray("fillInTheBlanks")
            val shortArray = obj.optJSONArray("shortAnswerQuestions")
            val keyArray = obj.optJSONArray("answerKey")

            fun toList(arr: org.json.JSONArray?): List<String> {
                val list = mutableListOf<String>()
                if (arr != null) {
                    for (i in 0 until arr.length()) {
                        list.add(arr.getString(i))
                    }
                }
                return list
            }

            WorksheetData(
                subject = subject,
                grade = grade,
                topic = topic,
                language = language,
                difficulty = difficulty,
                multipleChoiceQuestions = toList(mcqArray),
                fillInTheBlanks = toList(fillArray),
                shortAnswerQuestions = toList(shortArray),
                answerKey = toList(keyArray),
                isReviewedByTeacher = false
            )
        } catch (e: Exception) {
            null
        }
    }

    private fun generateOfflineWorksheet(
        subject: String,
        grade: String,
        topic: String,
        language: String,
        difficulty: String,
        questionCount: Int
    ): WorksheetData {
        return WorksheetData(
            subject = subject,
            grade = grade,
            topic = topic,
            language = language,
            difficulty = difficulty,
            multipleChoiceQuestions = listOf(
                "1. What is the fundamental principle governing $topic?\n   (A) Conservation of Energy   (B) Chemical Bonding   (C) Gravitational Attraction   (D) Osmosis",
                "2. Which of the following best defines the primary role of $topic in $subject?\n   (A) Secondary synthesis   (B) Primary regulation   (C) Catalytic breakdown   (D) Structural support",
                "3. In $grade curriculum, what variable directly influences $topic?\n   (A) Ambient temperature   (B) Atmospheric humidity   (C) Solar irradiance   (D) All of the above",
                "4. Which scientific law or formula is applied when calculating values in $topic?\n   (A) Standard Equilibrium   (B) Linear Ratio   (C) Newton's Law   (D) Boyle's Law"
            ),
            fillInTheBlanks = listOf(
                "1. In the study of $topic, the primary input element is called ____________.",
                "2. The rate of reaction in $topic increases as the ____________ rises up to an optimal limit.",
                "3. The standard scientific unit used when measuring $topic is ____________."
            ),
            shortAnswerQuestions = listOf(
                "1. Define $topic in your own words and state its importance to everyday life.",
                "2. Draw or describe the sequential stages involved in the functioning of $topic.",
                "3. Differentiate between the primary and secondary effects of $topic."
            ),
            answerKey = listOf(
                "MCQ 1: (A) Conservation of Energy",
                "MCQ 2: (B) Primary regulation",
                "MCQ 3: (D) All of the above",
                "MCQ 4: (B) Linear Ratio",
                "Blank 1: Substrate / Reactant",
                "Blank 2: Temperature / Energy level",
                "Blank 3: Standard SI Unit (Joules / Pascals / Grams)",
                "Short 1: Look for accurate scientific definition and real-life application.",
                "Short 2: Verify sequential order of process stages.",
                "Short 3: Expect at least 2 clear points of distinction."
            ),
            isReviewedByTeacher = false
        )
    }
}
