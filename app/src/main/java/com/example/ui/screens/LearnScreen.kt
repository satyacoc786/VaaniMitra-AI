package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.FlipCameraAndroid
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Translate
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.ExplanationResult
import com.example.data.model.FlashcardItem
import com.example.data.model.PronunciationExercise
import com.example.ui.theme.AppBorderLight
import com.example.ui.theme.AppCardBg
import com.example.ui.theme.AppSurfaceLight
import com.example.ui.theme.AppWhite
import com.example.ui.theme.BrandBlueDark
import com.example.ui.theme.BrandBlueLight
import com.example.ui.theme.BrandBluePrimary
import com.example.ui.theme.BrandCyan
import com.example.ui.theme.BrandCyanLight
import com.example.ui.theme.OnlineBlue
import com.example.ui.theme.OnlineBlueLight
import com.example.ui.theme.SuccessGreen
import com.example.ui.theme.SuccessGreenLight
import com.example.ui.theme.TextDarkNavy
import com.example.ui.theme.TextMutedSlate
import com.example.ui.theme.TextSubtle
import com.example.ui.theme.WarningOrange
import com.example.ui.theme.WarningOrangeLight

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LearnScreen(
    explanationResult: ExplanationResult?,
    isExplaining: Boolean,
    flashcards: List<FlashcardItem>,
    currentFlashcardIndex: Int,
    isFlashcardFlipped: Boolean,
    pronunciationExercises: List<PronunciationExercise>,
    currentPronunciationIndex: Int,
    pronunciationScore: Int?,
    pronunciationFeedback: String?,
    onAskExplanation: (String, String, String, String) -> Unit,
    onPlayAudio: (String, String) -> Unit,
    onStartQuizFromExplanation: (String) -> Unit,
    onSaveLesson: (String, String, String, String, String) -> Unit,
    onNextFlashcard: () -> Unit,
    onPrevFlashcard: () -> Unit,
    onFlipFlashcard: () -> Unit,
    onToggleFlashcardRevision: () -> Unit,
    onNextPronunciationExercise: () -> Unit,
    onSimulatePronunciationCheck: () -> Unit,
    onOpenSyllabus: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    var selectedLearnTab by remember { mutableIntStateOf(0) } // 0 = Ask VaaniMitra, 1 = Syllabus 1-10, 2 = Flashcards, 3 = Pronunciation

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(AppWhite)
    ) {
        PrimaryTabRow(
            selectedTabIndex = selectedLearnTab,
            containerColor = AppWhite,
            contentColor = BrandBluePrimary
        ) {
            Tab(
                selected = selectedLearnTab == 0,
                onClick = { selectedLearnTab = 0 },
                text = { Text("Ask AI", fontWeight = if (selectedLearnTab == 0) FontWeight.Bold else FontWeight.Normal) },
                modifier = Modifier.testTag("tab_ask_vaanimitra")
            )
            Tab(
                selected = selectedLearnTab == 1,
                onClick = { onOpenSyllabus() },
                text = { Text("1–10 Syllabus", fontWeight = if (selectedLearnTab == 1) FontWeight.Bold else FontWeight.Normal) },
                modifier = Modifier.testTag("tab_syllabus_ncert")
            )
            Tab(
                selected = selectedLearnTab == 2,
                onClick = { selectedLearnTab = 2 },
                text = { Text("Flashcards", fontWeight = if (selectedLearnTab == 2) FontWeight.Bold else FontWeight.Normal) },
                modifier = Modifier.testTag("tab_flashcards")
            )
            Tab(
                selected = selectedLearnTab == 3,
                onClick = { selectedLearnTab = 3 },
                text = { Text("Pronounce", fontWeight = if (selectedLearnTab == 3) FontWeight.Bold else FontWeight.Normal) },
                modifier = Modifier.testTag("tab_pronunciation")
            )
        }

        when (selectedLearnTab) {
            0 -> AskVaaniMitraContent(
                explanationResult = explanationResult,
                isExplaining = isExplaining,
                onAskExplanation = onAskExplanation,
                onPlayAudio = onPlayAudio,
                onStartQuiz = onStartQuizFromExplanation,
                onSaveLesson = onSaveLesson
            )
            2 -> FlashcardsContent(
                flashcards = flashcards,
                currentIndex = currentFlashcardIndex,
                isFlipped = isFlashcardFlipped,
                onNext = onNextFlashcard,
                onPrev = onPrevFlashcard,
                onFlip = onFlipFlashcard,
                onToggleRevision = onToggleFlashcardRevision,
                onPlayAudio = onPlayAudio
            )
            3 -> PronunciationContent(
                exercises = pronunciationExercises,
                currentIndex = currentPronunciationIndex,
                score = pronunciationScore,
                feedback = pronunciationFeedback,
                onNextExercise = onNextPronunciationExercise,
                onCheckPronunciation = onSimulatePronunciationCheck,
                onPlayAudio = onPlayAudio
            )
            else -> AskVaaniMitraContent(
                explanationResult = explanationResult,
                isExplaining = isExplaining,
                onAskExplanation = onAskExplanation,
                onPlayAudio = onPlayAudio,
                onStartQuiz = onStartQuizFromExplanation,
                onSaveLesson = onSaveLesson
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AskVaaniMitraContent(
    explanationResult: ExplanationResult?,
    isExplaining: Boolean,
    onAskExplanation: (String, String, String, String) -> Unit,
    onPlayAudio: (String, String) -> Unit,
    onStartQuiz: (String) -> Unit,
    onSaveLesson: (String, String, String, String, String) -> Unit
) {
    var topicQuery by remember { mutableStateOf("Photosynthesis") }
    var selectedGrade by remember { mutableStateOf("Grade 7") }
    var selectedSubject by remember { mutableStateOf("Science") }
    var selectedDifficulty by remember { mutableStateOf("Medium") }
    var isSaved by remember { mutableStateOf(false) }

    val grades = listOf(
        "Class 1", "Class 2", "Class 3", "Class 4", "Class 5",
        "Class 6", "Class 7", "Class 8", "Class 9", "Class 10"
    )
    val subjects = listOf("Science", "Mathematics", "Social Studies", "English", "Computer Science")
    val difficulties = listOf("Easy", "Medium", "Advanced")

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Search & Filter Box
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = AppWhite),
                border = androidx.compose.foundation.BorderStroke(1.dp, AppBorderLight),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(
                        text = "Ask Any Educational Question",
                        style = MaterialTheme.typography.titleSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = TextDarkNavy
                        )
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = topicQuery,
                        onValueChange = { topicQuery = it },
                        placeholder = { Text("e.g. Photosynthesis, Gravity, Linear Equations") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("explanation_query_input"),
                        shape = RoundedCornerShape(12.dp),
                        leadingIcon = {
                            Icon(Icons.Default.Search, contentDescription = null, tint = BrandBluePrimary)
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = BrandBluePrimary,
                            unfocusedBorderColor = AppBorderLight
                        )
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    // Selectors Row (Grade & Subject)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text("Grade", style = MaterialTheme.typography.labelSmall.copy(color = TextMutedSlate))
                            Spacer(modifier = Modifier.height(2.dp))
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = AppSurfaceLight,
                                border = androidx.compose.foundation.BorderStroke(1.dp, AppBorderLight)
                            ) {
                                Text(
                                    text = selectedGrade,
                                    modifier = Modifier
                                        .padding(horizontal = 10.dp, vertical = 8.dp)
                                        .fillMaxWidth(),
                                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold)
                                )
                            }
                        }

                        Column(modifier = Modifier.weight(1f)) {
                            Text("Subject", style = MaterialTheme.typography.labelSmall.copy(color = TextMutedSlate))
                            Spacer(modifier = Modifier.height(2.dp))
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = AppSurfaceLight,
                                border = androidx.compose.foundation.BorderStroke(1.dp, AppBorderLight)
                            ) {
                                Text(
                                    text = selectedSubject,
                                    modifier = Modifier
                                        .padding(horizontal = 10.dp, vertical = 8.dp)
                                        .fillMaxWidth(),
                                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold)
                                )
                            }
                        }

                        Column(modifier = Modifier.weight(1f)) {
                            Text("Level", style = MaterialTheme.typography.labelSmall.copy(color = TextMutedSlate))
                            Spacer(modifier = Modifier.height(2.dp))
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = AppSurfaceLight,
                                border = androidx.compose.foundation.BorderStroke(1.dp, AppBorderLight)
                            ) {
                                Text(
                                    text = selectedDifficulty,
                                    modifier = Modifier
                                        .padding(horizontal = 10.dp, vertical = 8.dp)
                                        .fillMaxWidth(),
                                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Button(
                        onClick = { onAskExplanation(topicQuery, selectedGrade, selectedSubject, selectedDifficulty) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("generate_explanation_button"),
                        colors = ButtonDefaults.buttonColors(containerColor = BrandBluePrimary),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        if (isExplaining) {
                            CircularProgressIndicator(color = AppWhite, modifier = Modifier.size(18.dp), strokeWidth = 2.dp)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Explaining Topic...")
                        } else {
                            Icon(Icons.Default.Lightbulb, contentDescription = null, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Explain in Simple Words")
                        }
                    }
                }
            }
        }

        // Structured Explanation Output (Prompt Section #8)
        if (explanationResult != null) {
            item {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = AppWhite),
                    border = androidx.compose.foundation.BorderStroke(1.dp, AppBorderLight),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = explanationResult.topic,
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = TextDarkNavy
                                )
                            )

                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = if (explanationResult.isFromOfflineModel) SuccessGreenLight else OnlineBlueLight
                            ) {
                                Text(
                                    text = if (explanationResult.isFromOfflineModel) "🟢 Local Knowledge" else "🔵 Online AI",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = if (explanationResult.isFromOfflineModel) SuccessGreen else OnlineBlue
                                    ),
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        // 1. Simple Explanation
                        SectionHeader("📖 Simple Explanation")
                        Text(
                            text = explanationResult.simpleExplanation,
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = TextDarkNavy,
                                lineHeight = 22.sp
                            )
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        // 2. Real-World Example
                        SectionHeader("🌍 Real-World Example")
                        Text(
                            text = explanationResult.realWorldExample,
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = TextDarkNavy,
                                lineHeight = 22.sp
                            )
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        // 3. Key Points (3–5 points)
                        SectionHeader("🎯 Key Points")
                        explanationResult.keyPoints.forEachIndexed { idx, point ->
                            Row(modifier = Modifier.padding(vertical = 2.dp)) {
                                Text("• ", color = BrandBluePrimary, fontWeight = FontWeight.Bold)
                                Text(point, style = MaterialTheme.typography.bodyMedium.copy(color = TextDarkNavy))
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        // 4. Quick Check (Questions)
                        SectionHeader("❓ Quick Comprehension Check")
                        explanationResult.quickCheckQuestions.forEachIndexed { idx, q ->
                            Text(
                                text = "${idx + 1}. $q",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    fontWeight = FontWeight.Medium,
                                    color = TextMutedSlate
                                )
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Action Buttons: Listen, Save, Create Quiz
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Button(
                                onClick = { onPlayAudio(explanationResult.simpleExplanation, "en") },
                                colors = ButtonDefaults.buttonColors(containerColor = BrandBluePrimary),
                                shape = RoundedCornerShape(10.dp),
                                modifier = Modifier.weight(1f)
                            ) {
                                Icon(Icons.Default.VolumeUp, contentDescription = null, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Listen", fontSize = 12.sp)
                            }

                            OutlinedButton(
                                onClick = { onStartQuiz(explanationResult.topic) },
                                shape = RoundedCornerShape(10.dp),
                                modifier = Modifier.weight(1f)
                            ) {
                                Icon(Icons.Default.Quiz, contentDescription = null, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Quiz", fontSize = 12.sp)
                            }

                            OutlinedButton(
                                onClick = {
                                    onSaveLesson(
                                        explanationResult.topic,
                                        explanationResult.topic,
                                        explanationResult.simpleExplanation,
                                        explanationResult.simpleExplanation,
                                        explanationResult.keyPoints.joinToString("\n")
                                    )
                                    isSaved = true
                                },
                                shape = RoundedCornerShape(10.dp),
                                modifier = Modifier.weight(1f)
                            ) {
                                Icon(
                                    imageVector = if (isSaved) Icons.Default.Check else Icons.Default.BookmarkBorder,
                                    contentDescription = null,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(if (isSaved) "Saved" else "Save", fontSize = 12.sp)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SectionHeader(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.labelMedium.copy(
            fontWeight = FontWeight.Bold,
            color = BrandBlueDark
        ),
        modifier = Modifier.padding(bottom = 4.dp)
    )
}

@Composable
fun FlashcardsContent(
    flashcards: List<FlashcardItem>,
    currentIndex: Int,
    isFlipped: Boolean,
    onNext: () -> Unit,
    onPrev: () -> Unit,
    onFlip: () -> Unit,
    onToggleRevision: () -> Unit,
    onPlayAudio: (String, String) -> Unit
) {
    val currentCard = flashcards.getOrNull(currentIndex) ?: return

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Card ${currentIndex + 1} of ${flashcards.size}",
                style = MaterialTheme.typography.labelMedium.copy(color = TextMutedSlate)
            )

            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = {
                    val textToRead = if (!isFlipped) currentCard.term else "${currentCard.term}. ${currentCard.simpleDefinition}"
                    onPlayAudio(textToRead, "en")
                }) {
                    Icon(
                        imageVector = Icons.Default.VolumeUp,
                        contentDescription = "Listen to Flashcard",
                        tint = BrandBluePrimary
                    )
                }

                IconButton(onClick = onToggleRevision) {
                    Icon(
                        imageVector = if (currentCard.isMarkedForRevision) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                        contentDescription = "Revision",
                        tint = if (currentCard.isMarkedForRevision) WarningOrange else TextSubtle
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Big Flashcard
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = if (isFlipped) BrandBlueLight else AppWhite),
            border = androidx.compose.foundation.BorderStroke(1.5.dp, BrandBluePrimary.copy(alpha = 0.3f)),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(240.dp)
                .clickable { onFlip() }
                .testTag("flashcard_item_surface")
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                contentAlignment = Alignment.Center
            ) {
                if (!isFlipped) {
                    // Front: Term
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = currentCard.term,
                            style = MaterialTheme.typography.headlineMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = BrandBlueDark
                            )
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Tap to flip for definition & example",
                            style = MaterialTheme.typography.bodySmall.copy(color = TextMutedSlate)
                        )
                    }
                } else {
                    // Back: Definition, Example, Translation
                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Text(
                            text = currentCard.simpleDefinition,
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontWeight = FontWeight.SemiBold,
                                color = TextDarkNavy
                            )
                        )
                        Text(
                            text = "💡 Example: ${currentCard.example}",
                            style = MaterialTheme.typography.bodySmall.copy(color = TextMutedSlate)
                        )
                        Text(
                            text = "🇮🇳 ${currentCard.translation}",
                            style = MaterialTheme.typography.bodySmall.copy(color = BrandBluePrimary)
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Navigation Controls
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = onPrev,
                enabled = currentIndex > 0,
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(AppSurfaceLight)
            ) {
                Icon(Icons.AutoMirrored.Default.ArrowBack, contentDescription = "Previous")
            }

            Button(
                onClick = onFlip,
                colors = ButtonDefaults.buttonColors(containerColor = BrandBluePrimary),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(Icons.Default.FlipCameraAndroid, contentDescription = null, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text(if (isFlipped) "Show Term" else "Flip Card")
            }

            IconButton(
                onClick = onNext,
                enabled = currentIndex < flashcards.size - 1,
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(AppSurfaceLight)
            ) {
                Icon(Icons.AutoMirrored.Default.ArrowForward, contentDescription = "Next")
            }
        }
    }
}

@Composable
fun PronunciationContent(
    exercises: List<PronunciationExercise>,
    currentIndex: Int,
    score: Int?,
    feedback: String?,
    onNextExercise: () -> Unit,
    onCheckPronunciation: () -> Unit,
    onPlayAudio: (String, String) -> Unit
) {
    val currentExercise = exercises.getOrNull(currentIndex) ?: return

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = AppWhite),
                border = androidx.compose.foundation.BorderStroke(1.dp, AppBorderLight),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Practice ${currentIndex + 1} of ${exercises.size}",
                            style = MaterialTheme.typography.labelSmall.copy(color = TextMutedSlate)
                        )
                        IconButton(onClick = { onPlayAudio(currentExercise.targetSentence, "en") }) {
                            Icon(Icons.Default.VolumeUp, contentDescription = "Listen", tint = BrandBluePrimary)
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "\"${currentExercise.targetSentence}\"",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = TextDarkNavy,
                            lineHeight = 26.sp
                        )
                    )

                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = currentExercise.translation,
                        style = MaterialTheme.typography.bodySmall.copy(color = TextMutedSlate)
                    )

                    Spacer(modifier = Modifier.height(10.dp))
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = BrandBlueLight
                    ) {
                        Text(
                            text = "🗣️ ${currentExercise.phoneticTip}",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.Medium,
                                color = BrandBlueDark
                            ),
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                        )
                    }
                }
            }
        }

        // Tap to Speak Button
        item {
            Button(
                onClick = onCheckPronunciation,
                colors = ButtonDefaults.buttonColors(containerColor = BrandBluePrimary),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("pronunciation_check_button")
            ) {
                Icon(Icons.Default.Mic, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Tap to Speak & Check Accuracy")
            }
        }

        // Pronunciation Score & Feedback
        if (score != null) {
            item {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = SuccessGreenLight),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Accuracy: $score%",
                            style = MaterialTheme.typography.headlineMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = SuccessGreen
                            )
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = feedback ?: "",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = TextDarkNavy,
                                fontWeight = FontWeight.Medium
                            )
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Button(
                            onClick = onNextExercise,
                            colors = ButtonDefaults.buttonColors(containerColor = SuccessGreen),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text("Next Exercise")
                        }
                    }
                }
            }
        }
    }
}
