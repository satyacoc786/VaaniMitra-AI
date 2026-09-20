package com.example.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.local.AppDatabase
import com.example.data.local.LessonEntity
import com.example.data.local.LessonRepository
import com.example.data.model.ExplanationResult
import com.example.data.model.FlashcardItem
import com.example.data.model.Language
import com.example.data.model.LanguagePack
import com.example.data.model.PronunciationExercise
import com.example.data.model.QuizData
import com.example.data.model.SpeechState
import com.example.data.model.UserMode
import com.example.data.model.WorksheetData
import com.example.service.AIExplanationService
import com.example.service.GeminiApiClient
import com.example.service.NetworkService
import com.example.service.OCRService
import com.example.service.OfflineModelService
import com.example.service.QuizService
import com.example.service.SampleTextbookPage
import com.example.service.SpeechService
import com.example.service.TTSService
import com.example.service.TranslationService
import com.example.service.WorksheetService
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

enum class NavTab(val label: String) {
    HOME("Home"),
    TRANSLATE("Translate"),
    LEARN("Learn"),
    SCAN("Scan"),
    PROFILE("Profile")
}

enum class ActiveSubScreen {
    NONE,
    WORKSHEET,
    QUIZ,
    LESSONS,
    DEMO_WORKFLOW,
    SYLLABUS
}

class MainViewModel(application: Application) : AndroidViewModel(application) {

    // Services
    val networkService = NetworkService(application)
    val offlineModelService = OfflineModelService()
    val speechService = SpeechService(application)
    val ttsService = TTSService(application)
    val geminiApiClient = GeminiApiClient()

    val translationService = TranslationService(geminiApiClient, networkService, offlineModelService)
    val ocrService = OCRService(application)
    val aiExplanationService = AIExplanationService(geminiApiClient, networkService)
    val quizService = QuizService(geminiApiClient, networkService)
    val worksheetService = WorksheetService(geminiApiClient, networkService)

    // Database & Repository
    private val database = AppDatabase.getDatabase(application, viewModelScope)
    val lessonRepository = LessonRepository(database.lessonDao(), database.quizRecordDao())

    val savedLessons = lessonRepository.allLessons.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    // App Navigation State
    private val _currentNavTab = MutableStateFlow(NavTab.HOME)
    val currentNavTab: StateFlow<NavTab> = _currentNavTab.asStateFlow()

    private val _activeSubScreen = MutableStateFlow(ActiveSubScreen.NONE)
    val activeSubScreen: StateFlow<ActiveSubScreen> = _activeSubScreen.asStateFlow()

    // User Mode (Teacher / Student)
    private val _userMode = MutableStateFlow(UserMode.TEACHER)
    val userMode: StateFlow<UserMode> = _userMode.asStateFlow()

    // Language Selection
    private val _sourceLanguage = MutableStateFlow(Language.DEFAULT_SOURCE) // Hindi
    val sourceLanguage: StateFlow<Language> = _sourceLanguage.asStateFlow()

    private val _targetLanguage = MutableStateFlow(Language.DEFAULT_TARGET) // English
    val targetLanguage: StateFlow<Language> = _targetLanguage.asStateFlow()

    // Voice & Translation State
    val speechState: StateFlow<SpeechState> = speechService.speechState
    val speechTranscript: StateFlow<String> = speechService.transcript
    val speechSoundLevel: StateFlow<Float> = speechService.soundLevel

    private val _translatedText = MutableStateFlow("")
    val translatedText: StateFlow<String> = _translatedText.asStateFlow()

    private val _lastTranslationEngine = MutableStateFlow("VaaniMitra IndicTrans (On-Device)")
    val lastTranslationEngine: StateFlow<String> = _lastTranslationEngine.asStateFlow()

    private val _isTranslationOffline = MutableStateFlow(true)
    val isTranslationOffline: StateFlow<Boolean> = _isTranslationOffline.asStateFlow()

    // Classroom Two-Way Mode State
    private val _classroomTeacherText = MutableStateFlow("आज हम प्रकाश संश्लेषण के बारे में सीखेंगे।")
    val classroomTeacherText: StateFlow<String> = _classroomTeacherText.asStateFlow()

    private val _classroomStudentText = MutableStateFlow("Today we will learn about photosynthesis.")
    val classroomStudentText: StateFlow<String> = _classroomStudentText.asStateFlow()

    // Privacy Consent Dialog
    private val _showPrivacyDialog = MutableStateFlow(false)
    val showPrivacyDialog: StateFlow<Boolean> = _showPrivacyDialog.asStateFlow()
    private var pendingOnlineAction: (() -> Unit)? = null

    // AI Explanation State (Ask VaaniMitra)
    private val _explanationResult = MutableStateFlow<ExplanationResult?>(null)
    val explanationResult: StateFlow<ExplanationResult?> = _explanationResult.asStateFlow()

    private val _isExplaining = MutableStateFlow(false)
    val isExplaining: StateFlow<Boolean> = _isExplaining.asStateFlow()

    // OCR / Textbook Scanner State
    private val _ocrExtractedText = MutableStateFlow("")
    val ocrExtractedText: StateFlow<String> = _ocrExtractedText.asStateFlow()

    private val _ocrConfidence = MutableStateFlow(0f)
    val ocrConfidence: StateFlow<Float> = _ocrConfidence.asStateFlow()

    private val _ocrErrorMessage = MutableStateFlow<String?>(null)
    val ocrErrorMessage: StateFlow<String?> = _ocrErrorMessage.asStateFlow()

    private val _isScanningOcr = MutableStateFlow(false)
    val isScanningOcr: StateFlow<Boolean> = _isScanningOcr.asStateFlow()

    // Smart Quiz State
    private val _currentQuizData = MutableStateFlow<QuizData?>(null)
    val currentQuizData: StateFlow<QuizData?> = _currentQuizData.asStateFlow()

    private val _activeQuestionIndex = MutableStateFlow(0)
    val activeQuestionIndex: StateFlow<Int> = _activeQuestionIndex.asStateFlow()

    private val _selectedOptionIndex = MutableStateFlow<Int?>(null)
    val selectedOptionIndex: StateFlow<Int?> = _selectedOptionIndex.asStateFlow()

    private val _isAnswerSubmitted = MutableStateFlow(false)
    val isAnswerSubmitted: StateFlow<Boolean> = _isAnswerSubmitted.asStateFlow()

    private val _quizScore = MutableStateFlow(0)
    val quizScore: StateFlow<Int> = _quizScore.asStateFlow()

    private val _isQuizFinished = MutableStateFlow(false)
    val isQuizFinished: StateFlow<Boolean> = _isQuizFinished.asStateFlow()

    private val _wrongQuestions = MutableStateFlow<List<Pair<Int, Int>>>(emptyList())
    val wrongQuestions: StateFlow<List<Pair<Int, Int>>> = _wrongQuestions.asStateFlow()

    // Worksheet State
    private val _currentWorksheet = MutableStateFlow<WorksheetData?>(null)
    val currentWorksheet: StateFlow<WorksheetData?> = _currentWorksheet.asStateFlow()

    private val _isGeneratingWorksheet = MutableStateFlow(false)
    val isGeneratingWorksheet: StateFlow<Boolean> = _isGeneratingWorksheet.asStateFlow()

    // Flashcards State
    private val _flashcards = MutableStateFlow<List<FlashcardItem>>(
        listOf(
            FlashcardItem(
                id = 1,
                term = "Photosynthesis",
                simpleDefinition = "Process where green plants use sunlight, water, and CO2 to create food.",
                example = "Leaves catching sunlight like solar panels to bake sugar.",
                translation = "प्रकाश संश्लेषण: सूर्य के प्रकाश से भोजन बनाने की प्रक्रिया।"
            ),
            FlashcardItem(
                id = 2,
                term = "Chlorophyll",
                simpleDefinition = "Green biological pigment in chloroplasts that absorbs sunlight.",
                example = "Gives spinach and grass their green color.",
                translation = "क्लोरोफिल: पौधों में हरा वर्णक जो प्रकाश अवशोषित करता है।"
            ),
            FlashcardItem(
                id = 3,
                term = "Stomata",
                simpleDefinition = "Microscopic mouth-like pores on leaves for gas exchange.",
                example = "Tiny leaf nostrils that breathe in CO2 and breathe out O2.",
                translation = "रंध्र: पत्तियों पर सूक्ष्म छिद्र जो गैस विनिमय करते हैं।"
            ),
            FlashcardItem(
                id = 4,
                term = "Xylem",
                simpleDefinition = "Plant vascular tissue that carries water upward from roots.",
                example = "Drinking straws connecting roots to the highest treetop.",
                translation = "जाइलम: जड़ों से पत्तियों तक जल ले जाने वाली संवहनी नलिका।"
            )
        )
    )
    val flashcards: StateFlow<List<FlashcardItem>> = _flashcards.asStateFlow()

    private val _currentFlashcardIndex = MutableStateFlow(0)
    val currentFlashcardIndex: StateFlow<Int> = _currentFlashcardIndex.asStateFlow()

    private val _isFlashcardFlipped = MutableStateFlow(false)
    val isFlashcardFlipped: StateFlow<Boolean> = _isFlashcardFlipped.asStateFlow()

    // Pronunciation Practice
    val pronunciationExercises = listOf(
        PronunciationExercise(
            id = 1,
            targetSentence = "Photosynthesis is the process by which plants make food.",
            translation = "प्रकाश संश्लेषण वह प्रक्रिया है जिससे पौधे भोजन बनाते हैं।",
            phoneticTip = "Focus on: Pho-to-syn-the-sis (foh-toh-sin-thuh-sis)"
        ),
        PronunciationExercise(
            id = 2,
            targetSentence = "Chloroplasts contain chlorophyll to absorb radiant solar energy.",
            translation = "क्लोरोप्लास्ट में सौर ऊर्जा को अवशोषित करने के लिए क्लोरोफिल होता है।",
            phoneticTip = "Focus on: Chlo-ro-plasts (klor-uh-plasts)"
        ),
        PronunciationExercise(
            id = 3,
            targetSentence = "Linear equations have variables with a degree of one.",
            translation = "रैखिक समीकरणों में एक घात वाले चर होते हैं।",
            phoneticTip = "Focus on: Lin-e-ar (lin-ee-er)"
        )
    )

    private val _currentPronunciationIndex = MutableStateFlow(0)
    val currentPronunciationIndex: StateFlow<Int> = _currentPronunciationIndex.asStateFlow()

    private val _pronunciationScore = MutableStateFlow<Int?>(null)
    val pronunciationScore: StateFlow<Int?> = _pronunciationScore.asStateFlow()

    private val _pronunciationFeedback = MutableStateFlow<String?>(null)
    val pronunciationFeedback: StateFlow<String?> = _pronunciationFeedback.asStateFlow()

    // 🚀 Hackathon 16-Step Demo Orchestration
    private val _demoStep = MutableStateFlow(1)
    val demoStep: StateFlow<Int> = _demoStep.asStateFlow()

    private val _isDemoRunning = MutableStateFlow(false)
    val isDemoRunning: StateFlow<Boolean> = _isDemoRunning.asStateFlow()

    fun navigateTo(tab: NavTab) {
        _activeSubScreen.value = ActiveSubScreen.NONE
        _currentNavTab.value = tab
    }

    private val _selectedSyllabusGrade = MutableStateFlow(1)
    val selectedSyllabusGrade: StateFlow<Int> = _selectedSyllabusGrade.asStateFlow()

    fun openSyllabus(grade: Int = 1) {
        _selectedSyllabusGrade.value = grade
        _activeSubScreen.value = ActiveSubScreen.SYLLABUS
    }

    fun openSubScreen(screen: ActiveSubScreen) {
        _activeSubScreen.value = screen
    }

    fun closeSubScreen() {
        _activeSubScreen.value = ActiveSubScreen.NONE
    }

    fun setUserMode(mode: UserMode) {
        _userMode.value = mode
    }

    fun toggleUserMode() {
        _userMode.value = if (_userMode.value == UserMode.TEACHER) UserMode.STUDENT else UserMode.TEACHER
    }

    fun setLanguages(source: Language, target: Language) {
        _sourceLanguage.value = source
        _targetLanguage.value = target
    }

    fun swapLanguages() {
        val temp = _sourceLanguage.value
        _sourceLanguage.value = _targetLanguage.value
        _targetLanguage.value = temp

        val tempText = _classroomTeacherText.value
        _classroomTeacherText.value = _classroomStudentText.value
        _classroomStudentText.value = tempText
    }

    // Speech & Translation Controls
    fun startListening() {
        speechService.startListening(_sourceLanguage.value.code)
    }

    fun stopListening() {
        speechService.stopListening()
    }

    fun setTranscript(text: String) {
        speechService.setTranscript(text)
    }

    fun executeTranslation(text: String, allowOnline: Boolean = true) {
        if (text.isBlank()) return

        val isOnline = networkService.isOnline.value && geminiApiClient.isConfigured()

        if (allowOnline && isOnline) {
            requestOnlineConsent {
                performTranslationInternal(text, allowOnline = true)
            }
        } else {
            performTranslationInternal(text, allowOnline = false)
        }
    }

    private fun performTranslationInternal(text: String, allowOnline: Boolean) {
        viewModelScope.launch {
            speechService.setSpeechState(SpeechState.TRANSLATING)
            val output = translationService.translate(
                text = text,
                sourceLangCode = _sourceLanguage.value.code,
                targetLangCode = _targetLanguage.value.code,
                allowOnline = allowOnline
            )
            _translatedText.value = output.translatedText
            _lastTranslationEngine.value = output.engineName
            _isTranslationOffline.value = output.isOffline
            speechService.setSpeechState(SpeechState.IDLE)
        }
    }

    fun playAudio(text: String, languageCode: String = _targetLanguage.value.code) {
        ttsService.speak(text, languageCode)
    }

    fun stopAudio() {
        ttsService.stop()
    }

    fun simulatePresetSpeech(preset: String) {
        speechService.simulateVoiceInput(preset, viewModelScope) { transcribed ->
            executeTranslation(transcribed, allowOnline = false)
        }
    }

    // Classroom Mode
    fun sendClassroomTeacherMessage(message: String) {
        _classroomTeacherText.value = message
        viewModelScope.launch {
            val output = translationService.translate(
                text = message,
                sourceLangCode = _sourceLanguage.value.code,
                targetLangCode = _targetLanguage.value.code,
                allowOnline = networkService.isOnline.value
            )
            _classroomStudentText.value = output.translatedText
        }
    }

    // AI Explanation (Ask VaaniMitra)
    fun askExplanation(topic: String, grade: String = "Grade 7", subject: String = "Science", difficulty: String = "Medium") {
        if (topic.isBlank()) return
        _isExplaining.value = true

        val isOnline = networkService.isOnline.value && geminiApiClient.isConfigured()
        if (isOnline) {
            requestOnlineConsent {
                viewModelScope.launch {
                    val result = aiExplanationService.explainTopic(
                        topic = topic,
                        grade = grade,
                        subject = subject,
                        language = _targetLanguage.value.name,
                        difficulty = difficulty,
                        allowOnline = true
                    )
                    _explanationResult.value = result
                    _isExplaining.value = false
                }
            }
        } else {
            viewModelScope.launch {
                val result = aiExplanationService.explainTopic(
                    topic = topic,
                    grade = grade,
                    subject = subject,
                    language = _targetLanguage.value.name,
                    difficulty = difficulty,
                    allowOnline = false
                )
                _explanationResult.value = result
                _isExplaining.value = false
            }
        }
    }

    // OCR Textbook Scan
    fun scanSamplePage(sample: SampleTextbookPage) {
        viewModelScope.launch {
            _isScanningOcr.value = true
            _ocrErrorMessage.value = null
            val result = ocrService.extractFromSample(sample)
            _isScanningOcr.value = false
            if (result.isSuccess) {
                _ocrExtractedText.value = result.extractedText
                _ocrConfidence.value = result.confidence
            } else {
                _ocrErrorMessage.value = result.errorMessage
            }
        }
    }

    fun updateOcrText(text: String) {
        _ocrExtractedText.value = text
    }

    // Smart Quiz
    fun startQuizForTopic(topic: String, sourceText: String? = null) {
        viewModelScope.launch {
            val quiz = quizService.generateQuiz(
                topic = topic,
                sourceText = sourceText,
                questionCount = 5,
                allowOnline = networkService.isOnline.value
            )
            _currentQuizData.value = quiz
            _activeQuestionIndex.value = 0
            _selectedOptionIndex.value = null
            _isAnswerSubmitted.value = false
            _quizScore.value = 0
            _isQuizFinished.value = false
            _wrongQuestions.value = emptyList()
            openSubScreen(ActiveSubScreen.QUIZ)
        }
    }

    fun selectQuizOption(index: Int) {
        if (_isAnswerSubmitted.value) return
        _selectedOptionIndex.value = index
    }

    fun submitQuizAnswer() {
        val quiz = _currentQuizData.value ?: return
        val currentQ = quiz.questions.getOrNull(_activeQuestionIndex.value) ?: return
        val selected = _selectedOptionIndex.value ?: return

        _isAnswerSubmitted.value = true
        if (selected == currentQ.correctOptionIndex) {
            _quizScore.value += 1
        } else {
            _wrongQuestions.value = _wrongQuestions.value + Pair(_activeQuestionIndex.value, selected)
        }
    }

    fun nextQuizQuestion() {
        val quiz = _currentQuizData.value ?: return
        if (_activeQuestionIndex.value < quiz.questions.size - 1) {
            _activeQuestionIndex.value += 1
            _selectedOptionIndex.value = null
            _isAnswerSubmitted.value = false
        } else {
            _isQuizFinished.value = true
            // Save result to Room Database
            viewModelScope.launch {
                lessonRepository.recordQuizResult(
                    topic = quiz.topic,
                    score = _quizScore.value,
                    totalQuestions = quiz.questions.size,
                    userMode = _userMode.value.label
                )
            }
        }
    }

    fun restartQuiz() {
        _activeQuestionIndex.value = 0
        _selectedOptionIndex.value = null
        _isAnswerSubmitted.value = false
        _quizScore.value = 0
        _isQuizFinished.value = false
        _wrongQuestions.value = emptyList()
    }

    // Worksheet Generator
    fun generateWorksheet(
        subject: String,
        grade: String,
        topic: String,
        language: String,
        difficulty: String,
        questionCount: Int = 10
    ) {
        viewModelScope.launch {
            _isGeneratingWorksheet.value = true
            val sheet = worksheetService.generateWorksheet(
                subject = subject,
                grade = grade,
                topic = topic,
                language = language,
                difficulty = difficulty,
                questionCount = questionCount,
                allowOnline = networkService.isOnline.value
            )
            _currentWorksheet.value = sheet
            _isGeneratingWorksheet.value = false
            openSubScreen(ActiveSubScreen.WORKSHEET)
        }
    }

    fun markWorksheetReviewed() {
        _currentWorksheet.value = _currentWorksheet.value?.copy(isReviewedByTeacher = true)
    }

    // Flashcards
    fun nextFlashcard() {
        if (_currentFlashcardIndex.value < _flashcards.value.size - 1) {
            _currentFlashcardIndex.value += 1
            _isFlashcardFlipped.value = false
        }
    }

    fun prevFlashcard() {
        if (_currentFlashcardIndex.value > 0) {
            _currentFlashcardIndex.value -= 1
            _isFlashcardFlipped.value = false
        }
    }

    fun flipFlashcard() {
        _isFlashcardFlipped.value = !_isFlashcardFlipped.value
    }

    fun toggleFlashcardRevision() {
        val idx = _currentFlashcardIndex.value
        val list = _flashcards.value.toMutableList()
        val item = list[idx]
        list[idx] = item.copy(isMarkedForRevision = !item.isMarkedForRevision)
        _flashcards.value = list
    }

    // Pronunciation Practice
    fun evaluatePronunciation(spokenText: String) {
        val target = pronunciationExercises[_currentPronunciationIndex.value].targetSentence
        val targetWords = target.lowercase().replace(Regex("[^a-z0-9 ]"), "").split("\\s+".toRegex())
        val spokenWords = spokenText.lowercase().replace(Regex("[^a-z0-9 ]"), "").split("\\s+".toRegex())

        var matches = 0
        for (w in spokenWords) {
            if (targetWords.contains(w)) matches++
        }
        val matchRatio = (matches.toFloat() / targetWords.size.coerceAtLeast(1)).coerceIn(0f, 1f)
        val score = (matchRatio * 100).toInt().coerceIn(65, 98)

        _pronunciationScore.value = score
        _pronunciationFeedback.value = when {
            score >= 90 -> "🌟 Excellent fluency! Native-like classroom pronunciation."
            score >= 75 -> "👍 Very good! Clear articulation of key educational terms."
            else -> "🎯 Good attempt. Try stressing the syllables more deliberately."
        }
    }

    fun nextPronunciationExercise() {
        if (_currentPronunciationIndex.value < pronunciationExercises.size - 1) {
            _currentPronunciationIndex.value += 1
        } else {
            _currentPronunciationIndex.value = 0
        }
        _pronunciationScore.value = null
        _pronunciationFeedback.value = null
    }

    // Lesson Management (Save / Delete)
    fun saveCurrentLesson(title: String, original: String, translated: String, explanation: String, keyPoints: String) {
        viewModelScope.launch {
            val lesson = LessonEntity(
                title = title,
                subject = "General Science",
                grade = "Grade 7",
                sourceLanguage = _sourceLanguage.value.name,
                targetLanguage = _targetLanguage.value.name,
                originalContent = original,
                translatedContent = translated,
                explanation = explanation,
                keyPoints = keyPoints,
                isOfflineAvailable = true
            )
            lessonRepository.saveLesson(lesson)
        }
    }

    fun deleteLesson(id: Long) {
        viewModelScope.launch {
            lessonRepository.deleteLesson(id)
        }
    }

    // Privacy Consent Dialog
    fun requestOnlineConsent(onConsentGranted: () -> Unit) {
        pendingOnlineAction = onConsentGranted
        _showPrivacyDialog.value = true
    }

    fun confirmPrivacyConsent() {
        _showPrivacyDialog.value = false
        pendingOnlineAction?.invoke()
        pendingOnlineAction = null
    }

    fun cancelPrivacyConsent() {
        _showPrivacyDialog.value = false
        pendingOnlineAction = null
    }

    // 🚀 Guided Hackathon Demo (16 Steps in 3-5 min)
    fun startHackathonDemo() {
        _isDemoRunning.value = true
        _demoStep.value = 1
        openSubScreen(ActiveSubScreen.DEMO_WORKFLOW)
    }

    fun advanceDemoStep() {
        if (_demoStep.value < 16) {
            _demoStep.value += 1
            applyDemoStepEffects(_demoStep.value)
        } else {
            _isDemoRunning.value = false
            closeSubScreen()
        }
    }

    fun prevDemoStep() {
        if (_demoStep.value > 1) {
            _demoStep.value -= 1
            applyDemoStepEffects(_demoStep.value)
        }
    }

    fun exitDemo() {
        _isDemoRunning.value = false
        closeSubScreen()
    }

    private fun applyDemoStepEffects(step: Int) {
        when (step) {
            1 -> setUserMode(UserMode.TEACHER)
            2 -> setLanguages(Language.ALL[1], Language.ALL[0]) // Hindi -> English
            3, 4 -> simulatePresetSpeech("आज हम पौधों में प्रकाश संश्लेषण के बारे में सीखेंगे।")
            5 -> { /* transcript shown */ }
            6 -> { /* translation shown */ }
            7 -> playAudio("Today we will learn about photosynthesis in plants.", "en")
            8, 9, 10 -> scanSamplePage(ocrService.samplePages.first())
            11 -> askExplanation("Photosynthesis", "Grade 7", "Science")
            12, 13 -> startQuizForTopic("Photosynthesis", _ocrExtractedText.value)
            14 -> { /* quiz result */ }
            15 -> networkService.setSimulateOffline(true)
            16 -> openSubScreen(ActiveSubScreen.LESSONS)
        }
    }

    fun downloadLanguagePack(pack: LanguagePack) {
        offlineModelService.startDownload(pack, viewModelScope)
    }

    fun deleteLanguagePack(pack: LanguagePack) {
        offlineModelService.deletePack(pack)
    }

    override fun onCleared() {
        super.onCleared()
        ttsService.shutdown()
        speechService.destroy()
    }
}
