package com.example

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import com.example.ui.ActiveSubScreen
import com.example.ui.MainViewModel
import com.example.ui.NavTab
import com.example.ui.components.PrivacyConsentDialog
import com.example.ui.components.VaaniMitraBottomNav
import com.example.ui.components.VaaniMitraHeader
import com.example.ui.screens.DemoWorkflowScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.LearnScreen
import com.example.ui.screens.LessonsScreen
import com.example.ui.screens.ProfileScreen
import com.example.ui.screens.QuizScreen
import com.example.ui.screens.ScanScreen
import com.example.ui.screens.SyllabusScreen
import com.example.ui.screens.TranslateScreen
import com.example.ui.screens.WorksheetScreen
import com.example.ui.theme.AppWhite
import com.example.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                VaaniMitraApp(viewModel = viewModel)
            }
        }
    }
}

@Composable
fun VaaniMitraApp(viewModel: MainViewModel) {
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { /* Permission results handled gracefully */ }

    LaunchedEffect(Unit) {
        permissionLauncher.launch(
            arrayOf(
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.CAMERA
            )
        )
    }

    val currentTab by viewModel.currentNavTab.collectAsState()
    val activeSubScreen by viewModel.activeSubScreen.collectAsState()
    val userMode by viewModel.userMode.collectAsState()
    val sourceLang by viewModel.sourceLanguage.collectAsState()
    val targetLang by viewModel.targetLanguage.collectAsState()
    val isOnline by viewModel.networkService.isOnline.collectAsState()
    val isSimulatingOffline by viewModel.networkService.isSimulatingOffline.collectAsState()
    val languagePacks by viewModel.offlineModelService.languagePacks.collectAsState()

    // Speech & Translation states
    val speechState by viewModel.speechState.collectAsState()
    val speechTranscript by viewModel.speechTranscript.collectAsState()
    val translatedText by viewModel.translatedText.collectAsState()
    val isTranslationOffline by viewModel.isTranslationOffline.collectAsState()
    val lastTranslationEngine by viewModel.lastTranslationEngine.collectAsState()
    val classroomTeacherText by viewModel.classroomTeacherText.collectAsState()
    val classroomStudentText by viewModel.classroomStudentText.collectAsState()

    // Learning states
    val explanationResult by viewModel.explanationResult.collectAsState()
    val isExplaining by viewModel.isExplaining.collectAsState()
    val flashcards by viewModel.flashcards.collectAsState()
    val currentFlashcardIndex by viewModel.currentFlashcardIndex.collectAsState()
    val isFlashcardFlipped by viewModel.isFlashcardFlipped.collectAsState()
    val pronunciationIndex by viewModel.currentPronunciationIndex.collectAsState()
    val pronunciationScore by viewModel.pronunciationScore.collectAsState()
    val pronunciationFeedback by viewModel.pronunciationFeedback.collectAsState()

    // Scanner states
    val ocrExtractedText by viewModel.ocrExtractedText.collectAsState()
    val ocrConfidence by viewModel.ocrConfidence.collectAsState()
    val ocrError by viewModel.ocrErrorMessage.collectAsState()
    val isScanningOcr by viewModel.isScanningOcr.collectAsState()

    // Quiz & Worksheet states
    val currentQuizData by viewModel.currentQuizData.collectAsState()
    val activeQuestionIndex by viewModel.activeQuestionIndex.collectAsState()
    val selectedOptionIndex by viewModel.selectedOptionIndex.collectAsState()
    val isAnswerSubmitted by viewModel.isAnswerSubmitted.collectAsState()
    val quizScore by viewModel.quizScore.collectAsState()
    val isQuizFinished by viewModel.isQuizFinished.collectAsState()
    val wrongQuestions by viewModel.wrongQuestions.collectAsState()

    val currentWorksheet by viewModel.currentWorksheet.collectAsState()
    val isGeneratingWorksheet by viewModel.isGeneratingWorksheet.collectAsState()

    val savedLessons by viewModel.savedLessons.collectAsState()
    val selectedSyllabusGrade by viewModel.selectedSyllabusGrade.collectAsState()

    // Privacy Dialog & Demo
    val showPrivacyDialog by viewModel.showPrivacyDialog.collectAsState()
    val demoStep by viewModel.demoStep.collectAsState()

    PrivacyConsentDialog(
        visible = showPrivacyDialog,
        onConfirm = { viewModel.confirmPrivacyConsent() },
        onCancel = { viewModel.cancelPrivacyConsent() }
    )

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(AppWhite),
        topBar = {
            if (activeSubScreen == ActiveSubScreen.NONE) {
                VaaniMitraHeader(
                    userMode = userMode,
                    sourceLanguage = sourceLang,
                    targetLanguage = targetLang,
                    isOnline = isOnline,
                    isOfflineReady = true,
                    onToggleUserMode = { viewModel.toggleUserMode() },
                    onSwapLanguages = { viewModel.swapLanguages() },
                    onSelectSourceLanguage = { viewModel.setLanguages(it, targetLang) },
                    onSelectTargetLanguage = { viewModel.setLanguages(sourceLang, it) },
                    onLaunchDemo = { viewModel.startHackathonDemo() },
                    modifier = Modifier.windowInsetsPadding(WindowInsets.statusBars)
                )
            }
        },
        bottomBar = {
            if (activeSubScreen == ActiveSubScreen.NONE) {
                VaaniMitraBottomNav(
                    currentTab = currentTab,
                    onTabSelected = { viewModel.navigateTo(it) }
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(AppWhite)
        ) {
            when (activeSubScreen) {
                ActiveSubScreen.QUIZ -> {
                    QuizScreen(
                        quizData = currentQuizData,
                        activeQuestionIndex = activeQuestionIndex,
                        selectedOptionIndex = selectedOptionIndex,
                        isAnswerSubmitted = isAnswerSubmitted,
                        score = quizScore,
                        isQuizFinished = isQuizFinished,
                        wrongQuestions = wrongQuestions,
                        onSelectOption = { viewModel.selectQuizOption(it) },
                        onSubmitAnswer = { viewModel.submitQuizAnswer() },
                        onNextQuestion = { viewModel.nextQuizQuestion() },
                        onRestartQuiz = { viewModel.restartQuiz() },
                        onPlayAudio = { text, lang -> viewModel.playAudio(text, lang) },
                        onClose = { viewModel.closeSubScreen() }
                    )
                }
                ActiveSubScreen.WORKSHEET -> {
                    WorksheetScreen(
                        currentWorksheet = currentWorksheet,
                        isGenerating = isGeneratingWorksheet,
                        onGenerate = { sub, gr, top, lang, diff, cnt ->
                            viewModel.generateWorksheet(sub, gr, top, lang, diff, cnt)
                        },
                        onMarkReviewed = { viewModel.markWorksheetReviewed() },
                        onSaveToLessons = { title, orig, trans ->
                            viewModel.saveCurrentLesson(title, orig, trans, "", "")
                        },
                        onPlayAudio = { text, lang -> viewModel.playAudio(text, lang) },
                        onClose = { viewModel.closeSubScreen() }
                    )
                }
                ActiveSubScreen.LESSONS -> {
                    LessonsScreen(
                        lessons = savedLessons,
                        onPlayAudio = { viewModel.playAudio(it) },
                        onStartQuizForLesson = { viewModel.startQuizForTopic(it) },
                        onDeleteLesson = { viewModel.deleteLesson(it) },
                        onClose = { viewModel.closeSubScreen() }
                    )
                }
                ActiveSubScreen.DEMO_WORKFLOW -> {
                    DemoWorkflowScreen(
                        currentStep = demoStep,
                        isOfflineMode = isSimulatingOffline || !isOnline,
                        onNextStep = { viewModel.advanceDemoStep() },
                        onPrevStep = { viewModel.prevDemoStep() },
                        onExitDemo = { viewModel.exitDemo() }
                    )
                }
                ActiveSubScreen.SYLLABUS -> {
                    SyllabusScreen(
                        initialGrade = selectedSyllabusGrade,
                        onPlayAudio = { text, lang -> viewModel.playAudio(text, lang) },
                        onStartQuizForChapter = { topic -> viewModel.startQuizForTopic(topic) },
                        onGenerateWorksheetForChapter = { subject, grade, chapterTitle ->
                            viewModel.generateWorksheet(
                                subject = subject,
                                grade = grade,
                                topic = chapterTitle,
                                language = "English",
                                difficulty = "Medium",
                                questionCount = 10
                            )
                        },
                        onExplainChapter = { subject, grade, chapterTitle ->
                            viewModel.askExplanation(
                                topic = chapterTitle,
                                grade = grade,
                                subject = subject,
                                difficulty = "Medium"
                            )
                            viewModel.navigateTo(NavTab.LEARN)
                        },
                        onClose = { viewModel.closeSubScreen() }
                    )
                }
                ActiveSubScreen.NONE -> {
                    when (currentTab) {
                        NavTab.HOME -> {
                            HomeScreen(
                                userMode = userMode,
                                savedLessons = savedLessons,
                                onNavigateTab = { viewModel.navigateTo(it) },
                                onOpenWorksheet = {
                                    viewModel.generateWorksheet(
                                        subject = "Science",
                                        grade = "Grade 7",
                                        topic = "Photosynthesis",
                                        language = "English",
                                        difficulty = "Medium",
                                        questionCount = 10
                                    )
                                },
                                onOpenQuiz = { viewModel.startQuizForTopic("Photosynthesis") },
                                onOpenLessons = { viewModel.openSubScreen(ActiveSubScreen.LESSONS) },
                                onOpenSyllabus = { viewModel.openSyllabus(1) },
                                onOpenDemo = { viewModel.startHackathonDemo() },
                                onPlayLessonAudio = { viewModel.playAudio(it) }
                            )
                        }
                        NavTab.TRANSLATE -> {
                            TranslateScreen(
                                speechState = speechState,
                                speechTranscript = speechTranscript,
                                translatedText = translatedText,
                                sourceLanguage = sourceLang,
                                targetLanguage = targetLang,
                                isTranslationOffline = isTranslationOffline,
                                lastTranslationEngine = lastTranslationEngine,
                                classroomTeacherText = classroomTeacherText,
                                classroomStudentText = classroomStudentText,
                                samplePhrases = viewModel.speechService.classroomSamplePhrases,
                                onStartListening = { viewModel.startListening() },
                                onStopListening = { viewModel.stopListening() },
                                onSetTranscript = { viewModel.setTranscript(it) },
                                onTranslate = { viewModel.executeTranslation(it) },
                                onPlayAudio = { text, lang -> viewModel.playAudio(text, lang) },
                                onSimulatePreset = { viewModel.simulatePresetSpeech(it) },
                                onSaveLesson = { topic, orig, trans ->
                                    viewModel.saveCurrentLesson(topic, orig, trans, "", "")
                                },
                                onSwapLanguages = { viewModel.swapLanguages() },
                                onSendClassroomMessage = { viewModel.sendClassroomTeacherMessage(it) }
                            )
                        }
                        NavTab.LEARN -> {
                            LearnScreen(
                                explanationResult = explanationResult,
                                isExplaining = isExplaining,
                                flashcards = flashcards,
                                currentFlashcardIndex = currentFlashcardIndex,
                                isFlashcardFlipped = isFlashcardFlipped,
                                pronunciationExercises = viewModel.pronunciationExercises,
                                currentPronunciationIndex = pronunciationIndex,
                                pronunciationScore = pronunciationScore,
                                pronunciationFeedback = pronunciationFeedback,
                                onAskExplanation = { topic, grade, subject, diff ->
                                    viewModel.askExplanation(topic, grade, subject, diff)
                                },
                                onPlayAudio = { text, lang -> viewModel.playAudio(text, lang) },
                                onStartQuizFromExplanation = { viewModel.startQuizForTopic(it) },
                                onSaveLesson = { title, orig, trans, exp, keyP ->
                                    viewModel.saveCurrentLesson(title, orig, trans, exp, keyP)
                                },
                                onNextFlashcard = { viewModel.nextFlashcard() },
                                onPrevFlashcard = { viewModel.prevFlashcard() },
                                onFlipFlashcard = { viewModel.flipFlashcard() },
                                onToggleFlashcardRevision = { viewModel.toggleFlashcardRevision() },
                                onNextPronunciationExercise = { viewModel.nextPronunciationExercise() },
                                onSimulatePronunciationCheck = {
                                    val target = viewModel.pronunciationExercises[pronunciationIndex].targetSentence
                                    viewModel.evaluatePronunciation(target)
                                },
                                onOpenSyllabus = { viewModel.openSyllabus(1) }
                            )
                        }
                        NavTab.SCAN -> {
                            ScanScreen(
                                samplePages = viewModel.ocrService.samplePages,
                                extractedText = ocrExtractedText,
                                confidence = ocrConfidence,
                                isScanning = isScanningOcr,
                                errorMessage = ocrError,
                                onScanSample = { viewModel.scanSamplePage(it) },
                                onUpdateExtractedText = { viewModel.updateOcrText(it) },
                                onExplainExtractedText = { viewModel.askExplanation(it) },
                                onTranslateExtractedText = { viewModel.executeTranslation(it) },
                                onCreateQuizFromExtractedText = { viewModel.startQuizForTopic("Textbook Lesson", it) },
                                onPlayAudio = { text, lang -> viewModel.playAudio(text, lang) }
                            )
                        }
                        NavTab.PROFILE -> {
                            ProfileScreen(
                                userMode = userMode,
                                isOnline = isOnline,
                                isSimulatingOffline = isSimulatingOffline,
                                languagePacks = languagePacks,
                                totalStorageMb = viewModel.offlineModelService.getTotalInstalledSizeMb(),
                                onSetUserMode = { viewModel.setUserMode(it) },
                                onToggleSimulateOffline = { viewModel.networkService.setSimulateOffline(it) },
                                onDownloadPack = { viewModel.downloadLanguagePack(it) },
                                onDeletePack = { viewModel.deleteLanguagePack(it) },
                                onLaunchDemo = { viewModel.startHackathonDemo() }
                            )
                        }
                    }
                }
            }
        }
    }
}

