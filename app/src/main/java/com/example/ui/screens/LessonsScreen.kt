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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import com.example.data.local.LessonEntity
import com.example.ui.theme.AppBorderLight
import com.example.ui.theme.AppCardBg
import com.example.ui.theme.AppSurfaceLight
import com.example.ui.theme.AppWhite
import com.example.ui.theme.BrandBlueDark
import com.example.ui.theme.BrandBlueLight
import com.example.ui.theme.BrandBluePrimary
import com.example.ui.theme.BrandCyan
import com.example.ui.theme.BrandCyanLight
import com.example.ui.theme.ErrorRed
import com.example.ui.theme.SuccessGreen
import com.example.ui.theme.SuccessGreenLight
import com.example.ui.theme.TextDarkNavy
import com.example.ui.theme.TextMutedSlate
import com.example.ui.theme.TextSubtle

@Composable
fun LessonsScreen(
    lessons: List<LessonEntity>,
    onPlayAudio: (String) -> Unit,
    onStartQuizForLesson: (String) -> Unit,
    onDeleteLesson: (Long) -> Unit,
    onClose: () -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(AppWhite)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Header
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(onClick = onClose) {
                    Icon(Icons.AutoMirrored.Default.ArrowBack, contentDescription = "Back")
                }
                Text(
                    text = "My Saved Lessons (${lessons.size})",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = TextDarkNavy
                    )
                )
                Spacer(modifier = Modifier.width(48.dp))
            }
        }

        items(lessons) { lesson ->
            ExpandableLessonCard(
                lesson = lesson,
                onPlayAudio = { onPlayAudio(lesson.translatedContent) },
                onPlayOriginal = { onPlayAudio(lesson.originalContent) },
                onStartQuiz = { onStartQuizForLesson(lesson.title) },
                onDelete = { onDeleteLesson(lesson.id) }
            )
        }
    }
}

@Composable
fun ExpandableLessonCard(
    lesson: LessonEntity,
    onPlayAudio: () -> Unit,
    onPlayOriginal: () -> Unit,
    onStartQuiz: () -> Unit,
    onDelete: () -> Unit
) {
    var isExpanded by remember { mutableStateOf(false) }

    val iconColor = when {
        lesson.subject.contains("Science", ignoreCase = true) -> BrandBluePrimary
        lesson.subject.contains("Math", ignoreCase = true) -> SuccessGreen
        lesson.subject.contains("English", ignoreCase = true) -> Color(0xFFD97706)
        else -> BrandCyan
    }

    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = AppWhite),
        border = androidx.compose.foundation.BorderStroke(1.dp, AppBorderLight),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(iconColor.copy(alpha = 0.15f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Default.MenuBook,
                        contentDescription = null,
                        tint = iconColor,
                        modifier = Modifier.size(24.dp)
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = lesson.title,
                        style = MaterialTheme.typography.titleSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = TextDarkNavy
                        )
                    )
                    Text(
                        text = "${lesson.subject} • ${lesson.grade} • ${lesson.sourceLanguage} ⇄ ${lesson.targetLanguage}",
                        style = MaterialTheme.typography.bodySmall.copy(color = TextMutedSlate)
                    )
                }

                IconButton(onClick = { isExpanded = !isExpanded }) {
                    Icon(
                        imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                        contentDescription = "Expand"
                    )
                }
            }

            AnimatedVisibility(visible = isExpanded) {
                Column(modifier = Modifier.padding(top = 14.dp)) {
                    // Original Content
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Original (${lesson.sourceLanguage}):",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = TextMutedSlate
                            )
                        )
                        IconButton(
                            onClick = { onPlayOriginal() },
                            modifier = Modifier.size(24.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.VolumeUp,
                                contentDescription = "Listen to Original",
                                tint = BrandBluePrimary,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }
                    Text(
                        text = lesson.originalContent,
                        style = MaterialTheme.typography.bodyMedium.copy(color = TextDarkNavy)
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    // Translated Content
                    Text(
                        text = "Translated (${lesson.targetLanguage}):",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = BrandBluePrimary
                        )
                    )
                    Text(
                        text = lesson.translatedContent,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Medium,
                            color = TextDarkNavy
                        )
                    )

                    if (lesson.explanation.isNotBlank()) {
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = "AI Explanation:",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = TextMutedSlate
                            )
                        )
                        Text(
                            text = lesson.explanation,
                            style = MaterialTheme.typography.bodySmall.copy(color = TextDarkNavy)
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Action buttons
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Button(
                            onClick = onPlayAudio,
                            colors = ButtonDefaults.buttonColors(containerColor = BrandBluePrimary),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            Icon(Icons.Default.VolumeUp, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Play Audio", fontSize = 12.sp)
                        }

                        OutlinedButton(
                            onClick = onStartQuiz,
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            Icon(Icons.Default.Quiz, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Quiz", fontSize = 12.sp)
                        }

                        IconButton(
                            onClick = onDelete,
                            modifier = Modifier.size(36.dp)
                        ) {
                            Icon(Icons.Default.Delete, contentDescription = "Delete", tint = ErrorRed)
                        }
                    }
                }
            }
        }
    }
}
