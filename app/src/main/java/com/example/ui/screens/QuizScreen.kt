package com.example.ui.screens

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
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.QuizData
import com.example.data.model.QuizQuestion
import com.example.ui.theme.AppBorderLight
import com.example.ui.theme.AppCardBg
import com.example.ui.theme.AppSurfaceLight
import com.example.ui.theme.AppWhite
import com.example.ui.theme.BrandBlueDark
import com.example.ui.theme.BrandBlueLight
import com.example.ui.theme.BrandBluePrimary
import com.example.ui.theme.BrandCyan
import com.example.ui.theme.ErrorRed
import com.example.ui.theme.ErrorRedLight
import com.example.ui.theme.SuccessGreen
import com.example.ui.theme.SuccessGreenLight
import com.example.ui.theme.TextDarkNavy
import com.example.ui.theme.TextMutedSlate
import com.example.ui.theme.TextSubtle
import com.example.ui.theme.WarningOrange
import com.example.ui.theme.WarningOrangeLight

@Composable
fun QuizScreen(
    quizData: QuizData?,
    activeQuestionIndex: Int,
    selectedOptionIndex: Int?,
    isAnswerSubmitted: Boolean,
    score: Int,
    isQuizFinished: Boolean,
    wrongQuestions: List<Pair<Int, Int>>,
    onSelectOption: (Int) -> Unit,
    onSubmitAnswer: () -> Unit,
    onNextQuestion: () -> Unit,
    onRestartQuiz: () -> Unit,
    onClose: () -> Unit,
    onPlayAudio: (String, String) -> Unit = { _, _ -> },
    modifier: Modifier = Modifier
) {
    if (quizData == null) {
        Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("No active quiz. Generate a quiz from a lesson or textbook page.")
        }
        return
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(AppWhite)
            .padding(16.dp)
    ) {
        // Top Bar
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(onClick = onClose) {
                Icon(Icons.AutoMirrored.Default.ArrowBack, contentDescription = "Close Quiz")
            }
            Text(
                text = quizData.title,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = TextDarkNavy
                )
            )
            Spacer(modifier = Modifier.width(48.dp))
        }

        Spacer(modifier = Modifier.height(10.dp))

        if (isQuizFinished) {
            // Result Screen
            QuizResultContent(
                quizData = quizData,
                score = score,
                wrongQuestions = wrongQuestions,
                onRestart = onRestartQuiz,
                onClose = onClose
            )
        } else {
            // Active Question
            val currentQ = quizData.questions.getOrNull(activeQuestionIndex)
            if (currentQ != null) {
                ActiveQuestionContent(
                    question = currentQ,
                    currentIndex = activeQuestionIndex,
                    totalQuestions = quizData.questions.size,
                    selectedOption = selectedOptionIndex,
                    isSubmitted = isAnswerSubmitted,
                    onSelectOption = onSelectOption,
                    onSubmitAnswer = onSubmitAnswer,
                    onNextQuestion = onNextQuestion,
                    onPlayAudio = onPlayAudio
                )
            }
        }
    }
}

@Composable
fun ActiveQuestionContent(
    question: QuizQuestion,
    currentIndex: Int,
    totalQuestions: Int,
    selectedOption: Int?,
    isSubmitted: Boolean,
    onSelectOption: (Int) -> Unit,
    onSubmitAnswer: () -> Unit,
    onNextQuestion: () -> Unit,
    onPlayAudio: (String, String) -> Unit = { _, _ -> }
) {
    val progress = (currentIndex + 1).toFloat() / totalQuestions

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Progress Indicator
        item {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Question ${currentIndex + 1} of $totalQuestions",
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = BrandBluePrimary
                        )
                    )
                    Text(
                        text = "${(progress * 100).toInt()}%",
                        style = MaterialTheme.typography.labelMedium.copy(color = TextMutedSlate)
                    )
                }
                Spacer(modifier = Modifier.height(6.dp))
                LinearProgressIndicator(
                    progress = { progress },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp)
                        .clip(RoundedCornerShape(4.dp)),
                    color = BrandBluePrimary,
                    trackColor = AppSurfaceLight
                )
            }
        }

        // Question Statement
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = AppWhite),
                border = androidx.compose.foundation.BorderStroke(1.dp, AppBorderLight),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ) {
                    Text(
                        text = question.question,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = TextDarkNavy,
                            lineHeight = 24.sp
                        ),
                        modifier = Modifier.weight(1f)
                    )
                    IconButton(
                        onClick = {
                            val optionsPrompt = question.options.mapIndexed { idx, opt ->
                                "Option ${('A'.code + idx).toChar()}: $opt"
                            }.joinToString(". ")
                            onPlayAudio("${question.question}. $optionsPrompt", "en")
                        },
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.VolumeUp,
                            contentDescription = "Read Question and Options Aloud",
                            tint = BrandBluePrimary,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }
        }

        // Options A, B, C, D
        items(question.options.size) { index ->
            val optionText = question.options[index]
            val isSelected = selectedOption == index
            val isCorrect = index == question.correctOptionIndex

            val optionLetter = ('A'.code + index).toChar()

            val bgColor = when {
                !isSubmitted && isSelected -> BrandBlueLight
                isSubmitted && isCorrect -> SuccessGreenLight
                isSubmitted && isSelected && !isCorrect -> ErrorRedLight
                else -> AppWhite
            }

            val borderColor = when {
                !isSubmitted && isSelected -> BrandBluePrimary
                isSubmitted && isCorrect -> SuccessGreen
                isSubmitted && isSelected && !isCorrect -> ErrorRed
                else -> AppBorderLight
            }

            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = bgColor),
                border = androidx.compose.foundation.BorderStroke(1.5.dp, borderColor),
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .clickable(enabled = !isSubmitted) { onSelectOption(index) }
                    .testTag("quiz_option_${optionLetter.lowercaseChar()}")
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                            .background(if (isSelected || (isSubmitted && isCorrect)) BrandBluePrimary else AppSurfaceLight),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = optionLetter.toString(),
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = if (isSelected || (isSubmitted && isCorrect)) AppWhite else TextDarkNavy
                            )
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Text(
                        text = optionText,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = TextDarkNavy,
                            fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal
                        ),
                        modifier = Modifier.weight(1f)
                    )

                    if (isSubmitted) {
                        if (isCorrect) {
                            Icon(Icons.Default.Check, contentDescription = "Correct", tint = SuccessGreen)
                        } else if (isSelected) {
                            Icon(Icons.Default.Close, contentDescription = "Wrong", tint = ErrorRed)
                        }
                    }
                }
            }
        }

        // Instant Feedback Banner
        if (isSubmitted) {
            item {
                val isCorrect = selectedOption == question.correctOptionIndex
                Card(
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = if (isCorrect) SuccessGreenLight else ErrorRedLight),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = if (isCorrect) "✅ Correct!" else "❌ Try Again",
                                style = MaterialTheme.typography.titleSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = if (isCorrect) SuccessGreen else ErrorRed
                                )
                            )
                            IconButton(
                                onClick = { onPlayAudio(question.explanation, "en") },
                                modifier = Modifier.size(28.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.VolumeUp,
                                    contentDescription = "Listen to Explanation",
                                    tint = if (isCorrect) SuccessGreen else ErrorRed,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = question.explanation,
                            style = MaterialTheme.typography.bodySmall.copy(color = TextDarkNavy)
                        )
                    }
                }
            }
        }

        // Action Button: Submit or Next Question
        item {
            Spacer(modifier = Modifier.height(8.dp))
            if (!isSubmitted) {
                Button(
                    onClick = onSubmitAnswer,
                    enabled = selectedOption != null,
                    colors = ButtonDefaults.buttonColors(containerColor = BrandBluePrimary),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("submit_quiz_answer_button")
                ) {
                    Text("Submit Answer")
                }
            } else {
                Button(
                    onClick = onNextQuestion,
                    colors = ButtonDefaults.buttonColors(containerColor = BrandBluePrimary),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("next_quiz_question_button")
                ) {
                    Text(if (currentIndex < totalQuestions - 1) "Next Question" else "View Results")
                }
            }
        }
    }
}

@Composable
fun QuizResultContent(
    quizData: QuizData,
    score: Int,
    wrongQuestions: List<Pair<Int, Int>>,
    onRestart: () -> Unit,
    onClose: () -> Unit
) {
    val total = quizData.questions.size
    val percentage = ((score.toFloat() / total) * 100).toInt()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Box(
                modifier = Modifier
                    .size(90.dp)
                    .clip(CircleShape)
                    .background(BrandBlueLight),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = null,
                    tint = BrandBluePrimary,
                    modifier = Modifier.size(48.dp)
                )
            }
        }

        item {
            Text(
                text = "Quiz Completed!",
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = TextDarkNavy
                )
            )
            Text(
                text = "Topic: ${quizData.topic}",
                style = MaterialTheme.typography.bodyMedium.copy(color = TextMutedSlate)
            )
        }

        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = AppWhite),
                border = androidx.compose.foundation.BorderStroke(1.dp, AppBorderLight),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Your Result: $score / $total",
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = BrandBluePrimary
                        )
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "$percentage% Score Achieved",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.SemiBold,
                            color = if (percentage >= 70) SuccessGreen else WarningOrange
                        )
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Result saved to your local offline study log.",
                        style = MaterialTheme.typography.bodySmall.copy(color = TextMutedSlate)
                    )
                }
            }
        }

        // Review Mistakes if any
        if (wrongQuestions.isNotEmpty()) {
            item {
                Text(
                    text = "Review Mistakes (${wrongQuestions.size}):",
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = ErrorRed
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
            }

            items(wrongQuestions.size) { idx ->
                val (qIdx, chosenOpt) = wrongQuestions[idx]
                val q = quizData.questions[qIdx]
                Card(
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = ErrorRedLight),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(
                            text = "${idx + 1}. ${q.question}",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = TextDarkNavy
                            )
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Your answer: ${q.options[chosenOpt]}",
                            style = MaterialTheme.typography.bodySmall.copy(color = ErrorRed)
                        )
                        Text(
                            text = "Correct answer: ${q.options[q.correctOptionIndex]}",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = SuccessGreen,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                }
            }
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                OutlinedButton(
                    onClick = onRestart,
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(Icons.Default.Refresh, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Try Again")
                }

                Button(
                    onClick = onClose,
                    colors = ButtonDefaults.buttonColors(containerColor = BrandBluePrimary),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Done")
                }
            }
        }
    }
}
