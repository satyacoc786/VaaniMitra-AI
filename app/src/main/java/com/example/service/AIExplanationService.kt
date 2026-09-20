package com.example.service

import com.example.data.model.ExplanationResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import org.json.JSONObject

class AIExplanationService(
    private val geminiApiClient: GeminiApiClient,
    private val networkService: NetworkService
) {

    // Offline curriculum knowledge base for high-frequency topics
    private val offlineKnowledgeBase = mapOf(
        "photosynthesis" to ExplanationResult(
            topic = "Photosynthesis",
            simpleExplanation = "Photosynthesis is how green plants cook their own food using sunlight. Leaves act like tiny kitchens where water from the soil and carbon dioxide from the air mix together under sunlight to make sugary food (glucose) and give off clean oxygen for us to breathe.",
            realWorldExample = "Think of a solar-powered sandwich maker on a rooftop. The solar panels (chlorophyll) catch sunlight, take flour and veggies (water & CO2), and bake a warm sandwich (glucose) while giving off fresh air (oxygen).",
            keyPoints = listOf(
                "Chlorophyll is the green pigment in leaves that absorbs solar radiation.",
                "Stomata are tiny pores on leaves that take in Carbon Dioxide and release Oxygen.",
                "Water and minerals travel up from roots through xylem tubes.",
                "The process releases the oxygen gas that all animals and humans need to breathe."
            ),
            quickCheckQuestions = listOf(
                "Which green pigment captures sunlight in plant leaves?",
                "What gas is released into the air as a byproduct of photosynthesis?"
            ),
            isFromOfflineModel = true
        ),
        "gravity" to ExplanationResult(
            topic = "Gravity",
            simpleExplanation = "Gravity is an invisible pulling force that every object with mass exerts on everything around it. Earth's gravity pulls everything down toward its center, keeping our feet on the ground and holding the atmosphere in place.",
            realWorldExample = "When you drop a cricket ball from your hand, it doesn't float into the sky—it falls directly to the ground because the entire Earth is pulling it down.",
            keyPoints = listOf(
                "Discovered and mathematically formalized by Sir Isaac Newton.",
                "The heavier an object is, the stronger its gravitational pull.",
                "Gravity keeps Earth orbiting around the Sun and the Moon orbiting Earth.",
                "On the Moon, gravity is only 1/6th as strong as on Earth, which is why astronauts can jump high."
            ),
            quickCheckQuestions = listOf(
                "What invisible force keeps planets orbiting around the Sun?",
                "How does an astronaut's weight change on the Moon compared to Earth?"
            ),
            isFromOfflineModel = true
        ),
        "linear equations" to ExplanationResult(
            topic = "Linear Equations",
            simpleExplanation = "A linear equation is a mathematical riddle where an unknown letter (like x) is hiding behind numbers. It is called 'linear' because if you graph it on grid paper, it creates a perfectly straight line.",
            realWorldExample = "If you have 5 rupees and buy 2 mystery chocolates and your total wallet becomes empty from 25 rupees, finding how much 1 chocolate costs is solving: 2x + 5 = 25.",
            keyPoints = listOf(
                "The highest exponent (power) of the unknown variable is always 1.",
                "Standard algebraic form is ax + b = c.",
                "The Golden Rule: whatever you do to the left side, you must do to the right side.",
                "Solving means isolating 'x' on one side of the equal sign."
            ),
            quickCheckQuestions = listOf(
                "What is the maximum power of the variable in a linear equation?",
                "If 3x = 15, what is the value of x?"
            ),
            isFromOfflineModel = true
        ),
        "water cycle" to ExplanationResult(
            topic = "Water Cycle",
            simpleExplanation = "The water cycle is nature's giant recycling system. The Sun warms water in lakes and oceans, turning it into invisible vapor (evaporation). The vapor cools into clouds (condensation), and falls back to Earth as rain or snow (precipitation).",
            realWorldExample = "When water boils in a covered tea kettle, water drops form on the inside of the lid and drip back down—that is the water cycle in miniature right on your stove!",
            keyPoints = listOf(
                "Evaporation: Liquid water turns into gas vapor via solar heat.",
                "Transpiration: Plants release extra moisture through leaves.",
                "Condensation: Water vapor cools and groups into cloud droplets.",
                "Precipitation: Droplets grow heavy and fall as rain, hail, or snow."
            ),
            quickCheckQuestions = listOf(
                "What is the process called when water changes from liquid to vapor?",
                "Where does water go after it rains on soil and mountains?"
            ),
            isFromOfflineModel = true
        )
    )

    suspend fun explainTopic(
        topic: String,
        grade: String = "Grade 7",
        subject: String = "Science",
        language: String = "English",
        difficulty: String = "Medium",
        allowOnline: Boolean = true
    ): ExplanationResult = withContext(Dispatchers.Default) {
        val normalized = topic.trim().lowercase()
        val isOnlineAvailable = networkService.isOnline.value && geminiApiClient.isConfigured()

        // 1. If online and allowed, generate tailored AI explanation
        if (allowOnline && isOnlineAvailable) {
            val prompt = """
                You are VaaniMitra AI, an expert school teacher. Explain the topic "$topic" for a student in $grade studying $subject.
                Language of explanation: $language.
                Difficulty: $difficulty.
                
                Respond in valid JSON format with exactly these four keys:
                {
                  "simpleExplanation": "Clear, engaging explanation in simple words suited for $grade",
                  "realWorldExample": "A relatable daily life analogy or example",
                  "keyPoints": ["Point 1", "Point 2", "Point 3", "Point 4"],
                  "quickCheckQuestions": ["Question 1", "Question 2"]
                }
            """.trimIndent()

            val result = geminiApiClient.generateContent(prompt)
            if (result.isSuccess) {
                val jsonStr = result.getOrNull()?.trim() ?: ""
                val parsed = parseExplanationJson(topic, jsonStr, grade, language)
                if (parsed != null) {
                    return@withContext parsed.copy(isFromOfflineModel = false)
                }
            }
        }

        // 2. Offline Fallback: Check local knowledge base
        delay(600) // Brief processing indicator
        for ((key, value) in offlineKnowledgeBase) {
            if (normalized.contains(key) || key.contains(normalized)) {
                return@withContext value.copy(
                    grade = grade,
                    targetLanguage = language,
                    isFromOfflineModel = true
                )
            }
        }

        // 3. Fallback to dynamic on-device template generator
        return@withContext generateOfflineTemplateExplanation(topic, grade, subject, language, difficulty)
    }

    private fun parseExplanationJson(topic: String, raw: String, grade: String, language: String): ExplanationResult? {
        return try {
            val cleanJson = raw.removePrefix("```json").removePrefix("```").removeSuffix("```").trim()
            val obj = JSONObject(cleanJson)
            val simple = obj.optString("simpleExplanation", "")
            val example = obj.optString("realWorldExample", "")
            val pointsArray = obj.optJSONArray("keyPoints")
            val points = mutableListOf<String>()
            if (pointsArray != null) {
                for (i in 0 until pointsArray.length()) {
                    points.add(pointsArray.getString(i))
                }
            }
            val questionsArray = obj.optJSONArray("quickCheckQuestions")
            val questions = mutableListOf<String>()
            if (questionsArray != null) {
                for (i in 0 until questionsArray.length()) {
                    questions.add(questionsArray.getString(i))
                }
            }

            if (simple.isNotBlank()) {
                ExplanationResult(
                    topic = topic,
                    simpleExplanation = simple,
                    realWorldExample = example,
                    keyPoints = if (points.isNotEmpty()) points else listOf("Core concept in $grade syllabus"),
                    quickCheckQuestions = if (questions.isNotEmpty()) questions else listOf("Explain $topic in your own words."),
                    isFromOfflineModel = false,
                    grade = grade,
                    targetLanguage = language
                )
            } else null
        } catch (e: Exception) {
            null
        }
    }

    private fun generateOfflineTemplateExplanation(
        topic: String,
        grade: String,
        subject: String,
        language: String,
        difficulty: String
    ): ExplanationResult {
        return ExplanationResult(
            topic = topic,
            simpleExplanation = "$topic is a foundational concept in $subject for $grade students. It explains how natural principles and structured rules operate in the physical or conceptual world, allowing us to predict outcomes and solve practical problems systematically.",
            realWorldExample = "Think of $topic like following a well-tested kitchen recipe or assembling a bicycle: when every component is connected in sequence, the entire system works harmoniously.",
            keyPoints = listOf(
                "Core syllabus topic tailored for $grade $subject.",
                "Explains the underlying mechanisms and essential relationships of $topic.",
                "Provides mathematical and observational patterns verified by experiments.",
                "Enables students to apply classroom knowledge to real-world problem solving."
            ),
            quickCheckQuestions = listOf(
                "Why is understanding $topic important in $subject?",
                "Name one practical application of $topic in everyday life."
            ),
            isFromOfflineModel = true,
            grade = grade,
            targetLanguage = language
        )
    }
}
