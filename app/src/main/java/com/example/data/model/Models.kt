package com.example.data.model

enum class UserMode(val label: String, val subtitle: String) {
    TEACHER("Teacher Mode", "Create worksheets, quizzes, translate speech & manage classroom"),
    STUDENT("Student Mode", "Ask questions, practice quizzes, scan books & practice pronunciation")
}

data class Language(
    val code: String,
    val name: String,
    val nativeName: String,
    val flag: String,
    val isOfflineSupported: Boolean = true,
    val packSizeMb: Int = 160
) {
    companion object {
        val ALL = listOf(
            Language("en", "English", "English", "🇬🇧", true, 120),
            Language("hi", "Hindi", "हिन्दी", "🇮🇳", true, 160),
            Language("te", "Telugu", "తెలుగు", "🇮🇳", true, 185),
            Language("ta", "Tamil", "தமிழ்", "🇮🇳", true, 190),
            Language("kn", "Kannada", "ಕನ್ನಡ", "🇮🇳", true, 175),
            Language("ml", "Malayalam", "മലയാളം", "🇮🇳", true, 180),
            Language("mr", "Marathi", "मराठी", "🇮🇳", true, 170),
            Language("bn", "Bengali", "বাংলা", "🇮🇳", true, 185)
        )

        val DEFAULT_SOURCE = ALL[1] // Hindi
        val DEFAULT_TARGET = ALL[0] // English
    }
}

data class LanguagePack(
    val language: Language,
    val isInstalled: Boolean,
    val sizeMb: Int,
    val isDownloading: Boolean = false,
    val downloadProgress: Float = 0f,
    val offlineFeatures: List<String> = listOf("Speech-to-Text", "Offline Dictionary", "Text-to-Speech", "Local OCR")
)

data class ExplanationResult(
    val topic: String,
    val simpleExplanation: String,
    val realWorldExample: String,
    val keyPoints: List<String>,
    val quickCheckQuestions: List<String>,
    val isFromOfflineModel: Boolean = true,
    val targetLanguage: String = "English",
    val grade: String = "Grade 7"
)

data class QuizQuestion(
    val id: Int,
    val question: String,
    val options: List<String>,
    val correctOptionIndex: Int,
    val explanation: String
)

data class QuizData(
    val title: String,
    val topic: String,
    val questions: List<QuizQuestion>
)

data class FlashcardItem(
    val id: Int,
    val term: String,
    val simpleDefinition: String,
    val example: String,
    val translation: String,
    val isMarkedForRevision: Boolean = false
)

data class WorksheetData(
    val subject: String,
    val grade: String,
    val topic: String,
    val language: String,
    val difficulty: String,
    val multipleChoiceQuestions: List<String>,
    val fillInTheBlanks: List<String>,
    val shortAnswerQuestions: List<String>,
    val answerKey: List<String>,
    val isReviewedByTeacher: Boolean = false
)

data class PronunciationExercise(
    val id: Int,
    val targetSentence: String,
    val translation: String,
    val phoneticTip: String
)

enum class SpeechState {
    IDLE,
    LISTENING,
    TRANSCRIBING,
    TRANSLATING,
    SPEAKING,
    ERROR
}
