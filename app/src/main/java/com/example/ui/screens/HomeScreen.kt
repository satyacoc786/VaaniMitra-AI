package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material.icons.filled.RecordVoiceOver
import androidx.compose.material.icons.filled.RocketLaunch
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Translate
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.LessonEntity
import com.example.data.model.UserMode
import com.example.ui.NavTab
import com.example.ui.theme.AppBorderLight
import com.example.ui.theme.AppCardBg
import com.example.ui.theme.AppSurfaceLight
import com.example.ui.theme.AppWhite
import com.example.ui.theme.BrandBlueDark
import com.example.ui.theme.BrandBlueLight
import com.example.ui.theme.BrandBluePrimary
import com.example.ui.theme.BrandCyan
import com.example.ui.theme.BrandCyanLight
import com.example.ui.theme.SuccessGreen
import com.example.ui.theme.SuccessGreenLight
import com.example.ui.theme.TextDarkNavy
import com.example.ui.theme.TextMutedSlate
import com.example.ui.theme.TextSubtle
import com.example.ui.theme.WarningOrange
import com.example.ui.theme.WarningOrangeLight

@Composable
fun HomeScreen(
    userMode: UserMode,
    savedLessons: List<LessonEntity>,
    onNavigateTab: (NavTab) -> Unit,
    onOpenWorksheet: () -> Unit,
    onOpenQuiz: () -> Unit,
    onOpenLessons: () -> Unit,
    onOpenSyllabus: () -> Unit,
    onOpenDemo: () -> Unit,
    onPlayLessonAudio: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(AppWhite),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // 1. Warm Greeting & Role context
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = BrandBlueLight),
                border = androidx.compose.foundation.BorderStroke(1.dp, BrandBluePrimary.copy(alpha = 0.2f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = if (userMode == UserMode.TEACHER) "Namaste, Teacher! 👋" else "Namaste, Learner! 👋",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = BrandBlueDark
                            )
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = if (userMode == UserMode.TEACHER)
                                "Ready to translate speech, generate quizzes, and craft worksheets today?"
                            else
                                "What subject would you like to explore or practice today?",
                            style = MaterialTheme.typography.bodySmall.copy(color = TextDarkNavy)
                        )
                    }

                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .background(BrandBluePrimary),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.AutoAwesome,
                            contentDescription = "AI Assistant",
                            tint = AppWhite,
                            modifier = Modifier.size(26.dp)
                        )
                    }
                }
            }
        }

        // 2. 🚀 Hackathon 3-Minute Demo Banner
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = BrandCyanLight),
                border = androidx.compose.foundation.BorderStroke(1.dp, BrandCyan.copy(alpha = 0.3f)),
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onOpenDemo() }
                    .testTag("launch_demo_button")
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(BrandCyan),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.RocketLaunch,
                            contentDescription = "Demo Mode",
                            tint = AppWhite,
                            modifier = Modifier.size(22.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "🚀 Hackathon Guided Demo (3 Min)",
                            style = MaterialTheme.typography.titleSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = TextDarkNavy
                            )
                        )
                        Text(
                            text = "Walk through the full voice, OCR, quiz & offline flow seamlessly.",
                            style = MaterialTheme.typography.bodySmall.copy(color = TextMutedSlate, fontSize = 11.sp)
                        )
                    }
                    Icon(
                        imageVector = Icons.AutoMirrored.Default.ArrowForward,
                        contentDescription = "Start",
                        tint = BrandCyan,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }

        // 3. Continue Learning / Current Subject Card
        item {
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
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "CONTINUE LEARNING",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = BrandBluePrimary,
                                letterSpacing = 1.sp
                            )
                        )
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = SuccessGreenLight
                        ) {
                            Text(
                                text = "80% Complete",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = SuccessGreen
                                ),
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Photosynthesis & Cellular Respiration",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = TextDarkNavy
                        )
                    )
                    Text(
                        text = "Grade 7 • General Science • Bilingual Hindi-English",
                        style = MaterialTheme.typography.bodySmall.copy(color = TextMutedSlate)
                    )

                    Spacer(modifier = Modifier.height(12.dp))
                    LinearProgressIndicator(
                        progress = { 0.8f },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(6.dp)
                            .clip(RoundedCornerShape(3.dp)),
                        color = BrandBluePrimary,
                        trackColor = AppSurfaceLight
                    )

                    Spacer(modifier = Modifier.height(12.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OutlinedButton(
                            onClick = { onOpenSyllabus() },
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            Icon(
                                imageVector = Icons.Default.School,
                                contentDescription = null,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Syllabus 1–10", fontSize = 12.sp)
                        }

                        Button(
                            onClick = { onOpenQuiz() },
                            colors = ButtonDefaults.buttonColors(containerColor = BrandBluePrimary),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier
                                .weight(1f)
                                .testTag("resume_learning_button")
                        ) {
                            Icon(
                                imageVector = Icons.Default.PlayArrow,
                                contentDescription = null,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Take Quiz", fontSize = 12.sp)
                        }
                    }
                }
            }
        }

        // 4. Quick Action Grid (Prompt Section #5)
        item {
            Text(
                text = "Quick Classroom Tools",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = TextDarkNavy
                )
            )
        }

        item {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    QuickActionCard(
                        title = "Translate",
                        subtitle = "Voice & Classroom",
                        icon = Icons.Default.Translate,
                        badgeColor = BrandBlueLight,
                        iconColor = BrandBluePrimary,
                        modifier = Modifier.weight(1f),
                        onClick = { onNavigateTab(NavTab.TRANSLATE) }
                    )

                    QuickActionCard(
                        title = "Scan Book",
                        subtitle = "OCR & Explanations",
                        icon = Icons.Default.CameraAlt,
                        badgeColor = BrandCyanLight,
                        iconColor = BrandCyan,
                        modifier = Modifier.weight(1f),
                        onClick = { onNavigateTab(NavTab.SCAN) }
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    QuickActionCard(
                        title = "Explain Topic",
                        subtitle = "Ask VaaniMitra AI",
                        icon = Icons.Default.Lightbulb,
                        badgeColor = WarningOrangeLight,
                        iconColor = WarningOrange,
                        modifier = Modifier.weight(1f),
                        onClick = { onNavigateTab(NavTab.LEARN) }
                    )

                    QuickActionCard(
                        title = "Smart Quiz",
                        subtitle = "Instant Feedback",
                        icon = Icons.Default.Quiz,
                        badgeColor = SuccessGreenLight,
                        iconColor = SuccessGreen,
                        modifier = Modifier.weight(1f),
                        onClick = { onOpenQuiz() }
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    QuickActionCard(
                        title = "Worksheet Maker",
                        subtitle = "Printable & Answers",
                        icon = Icons.Default.Description,
                        badgeColor = BrandBlueLight,
                        iconColor = BrandBlueDark,
                        modifier = Modifier.weight(1f),
                        onClick = { onOpenWorksheet() }
                    )

                    QuickActionCard(
                        title = "My Lessons",
                        subtitle = "Saved Curriculum",
                        icon = Icons.AutoMirrored.Default.MenuBook,
                        badgeColor = BrandCyanLight,
                        iconColor = BrandCyan,
                        modifier = Modifier.weight(1f),
                        onClick = { onOpenLessons() }
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    QuickActionCard(
                        title = "Class 1–10 Syllabus",
                        subtitle = "NCERT & Graded Topics",
                        icon = Icons.Default.School,
                        badgeColor = BrandBlueLight,
                        iconColor = BrandBluePrimary,
                        modifier = Modifier.fillMaxWidth(),
                        onClick = { onOpenSyllabus() }
                    )
                }
            }
        }

        // 5. Saved Lessons Quick Access (Prompt Section #14)
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Saved Lessons",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = TextDarkNavy
                    )
                )
                Text(
                    text = "View All (${savedLessons.size})",
                    style = MaterialTheme.typography.labelMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = BrandBluePrimary
                    ),
                    modifier = Modifier.clickable { onOpenLessons() }
                )
            }
        }

        items(savedLessons.take(3)) { lesson ->
            LessonItemCard(
                lesson = lesson,
                onPlayAudio = { onPlayLessonAudio(lesson.translatedContent) },
                onClick = { onOpenLessons() }
            )
        }
    }
}

@Composable
fun QuickActionCard(
    title: String,
    subtitle: String,
    icon: ImageVector,
    badgeColor: Color,
    iconColor: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = AppWhite),
        border = androidx.compose.foundation.BorderStroke(1.dp, AppBorderLight),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        modifier = modifier
            .clip(RoundedCornerShape(14.dp))
            .clickable { onClick() }
            .testTag("quick_action_${title.lowercase().replace(' ', '_')}")
    ) {
        Column(
            modifier = Modifier.padding(14.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(38.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(badgeColor),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = title,
                    tint = iconColor,
                    modifier = Modifier.size(20.dp)
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = TextDarkNavy
                )
            )
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodySmall.copy(
                    color = TextMutedSlate,
                    fontSize = 11.sp
                )
            )
        }
    }
}

@Composable
fun LessonItemCard(
    lesson: LessonEntity,
    onPlayAudio: () -> Unit,
    onClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = AppWhite),
        border = androidx.compose.foundation.BorderStroke(1.dp, AppBorderLight),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(42.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(BrandBlueLight),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Default.MenuBook,
                    contentDescription = null,
                    tint = BrandBluePrimary,
                    modifier = Modifier.size(22.dp)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = lesson.title,
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = TextDarkNavy
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "${lesson.subject} • ${lesson.grade} • ${lesson.sourceLanguage} ⇄ ${lesson.targetLanguage}",
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = TextMutedSlate,
                        fontSize = 11.sp
                    )
                )
            }

            IconButton(
                onClick = onPlayAudio,
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(AppSurfaceLight)
            ) {
                Icon(
                    imageVector = Icons.Default.VolumeUp,
                    contentDescription = "Play Lesson Audio",
                    tint = BrandBluePrimary,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}
