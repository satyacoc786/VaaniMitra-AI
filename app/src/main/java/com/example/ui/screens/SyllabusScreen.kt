package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.ScrollableTabRow
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Chapter
import com.example.data.model.ClassSyllabus
import com.example.data.model.SubjectSyllabus
import com.example.data.repository.SyllabusRepository
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
fun SyllabusScreen(
    initialGrade: Int = 1,
    onPlayAudio: (String, String) -> Unit,
    onStartQuizForChapter: (String) -> Unit,
    onGenerateWorksheetForChapter: (String, String, String) -> Unit,
    onExplainChapter: (String, String, String) -> Unit,
    onClose: () -> Unit,
    modifier: Modifier = Modifier
) {
    val allClasses = SyllabusRepository.allClassesSyllabus

    // Grade selection (1 to 10)
    var selectedGradeLevel by remember { mutableIntStateOf(initialGrade) }
    val currentClass = allClasses.firstOrNull { it.gradeLevel == selectedGradeLevel } ?: allClasses[0]

    // Subject selection within the class
    var selectedSubjectIndex by remember(selectedGradeLevel) { mutableIntStateOf(0) }
    val subjects = currentClass.subjects
    val currentSubject = subjects.getOrNull(selectedSubjectIndex) ?: subjects[0]

    // Expanded chapter tracking
    var expandedChapterId by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(AppWhite)
    ) {
        // Top App Bar
        Surface(
            color = AppWhite,
            border = androidx.compose.foundation.BorderStroke(1.dp, AppBorderLight),
            shadowElevation = 2.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(onClick = onClose) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = BrandBluePrimary
                    )
                }

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "1–10 Graded Syllabus",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = TextDarkNavy
                        )
                    )
                    Text(
                        text = "Categorized NCERT & State Curriculum",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = TextMutedSlate,
                            fontSize = 11.sp
                        )
                    )
                }

                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = BrandBlueLight
                ) {
                    Text(
                        text = "Class 1–10",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = BrandBluePrimary
                        ),
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }
        }

        // Horizontal Grade Selector (1 to 10)
        ScrollableTabRow(
            selectedTabIndex = selectedGradeLevel - 1,
            containerColor = AppWhite,
            contentColor = BrandBluePrimary,
            edgePadding = 12.dp,
            divider = {}
        ) {
            (1..10).forEach { gradeNum ->
                val isSelected = selectedGradeLevel == gradeNum
                Tab(
                    selected = isSelected,
                    onClick = {
                        selectedGradeLevel = gradeNum
                        selectedSubjectIndex = 0
                        expandedChapterId = null
                    },
                    text = {
                        Text(
                            text = "Class $gradeNum",
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                            color = if (isSelected) BrandBluePrimary else TextMutedSlate
                        )
                    },
                    modifier = Modifier.testTag("syllabus_grade_tab_$gradeNum")
                )
            }
        }

        // Class Overview Banner
        Card(
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(containerColor = BrandBlueLight),
            border = androidx.compose.foundation.BorderStroke(1.dp, BrandBluePrimary.copy(alpha = 0.2f)),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Row(
                modifier = Modifier.padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(38.dp)
                        .clip(CircleShape)
                        .background(BrandBluePrimary),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "${currentClass.gradeLevel}",
                        style = MaterialTheme.typography.titleMedium.copy(
                            color = AppWhite,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "${currentClass.gradeName} (${currentClass.ageGroup})",
                        style = MaterialTheme.typography.titleSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = BrandBlueDark
                        )
                    )
                    Text(
                        text = currentClass.tagline,
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = TextDarkNavy,
                            fontSize = 11.sp
                        )
                    )
                }
            }
        }

        // Subject Selector Chips
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(subjects.size) { index ->
                val subj = subjects[index]
                val isSelected = selectedSubjectIndex == index
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = if (isSelected) BrandBluePrimary else AppSurfaceLight,
                    border = androidx.compose.foundation.BorderStroke(
                        1.dp,
                        if (isSelected) BrandBluePrimary else AppBorderLight
                    ),
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .clickable {
                            selectedSubjectIndex = index
                            expandedChapterId = null
                        }
                        .testTag("subject_chip_${subj.subjectName.lowercase().take(6)}")
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = when (subj.iconName) {
                                "math" -> Icons.Default.AutoAwesome
                                "social" -> Icons.Default.MenuBook
                                "english" -> Icons.Default.Description
                                else -> Icons.Default.Lightbulb
                            },
                            contentDescription = null,
                            tint = if (isSelected) AppWhite else BrandBluePrimary,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = subj.subjectName,
                            style = MaterialTheme.typography.bodySmall.copy(
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                color = if (isSelected) AppWhite else TextDarkNavy
                            )
                        )
                    }
                }
            }
        }

        // Chapters List
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 6.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "${currentSubject.subjectName} Syllabus (${currentSubject.chapters.size} Chapters)",
                        style = MaterialTheme.typography.titleSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = TextDarkNavy
                        )
                    )
                    Text(
                        text = "${currentClass.gradeName}",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = BrandBluePrimary
                        )
                    )
                }
            }

            items(currentSubject.chapters) { chapter ->
                val isExpanded = expandedChapterId == chapter.id

                ChapterCard(
                    chapter = chapter,
                    gradeName = currentClass.gradeName,
                    subjectName = currentSubject.subjectName,
                    isExpanded = isExpanded,
                    onToggleExpand = {
                        expandedChapterId = if (isExpanded) null else chapter.id
                    },
                    onPlayAudio = onPlayAudio,
                    onStartQuiz = { onStartQuizForChapter(chapter.title) },
                    onGenerateWorksheet = {
                        onGenerateWorksheetForChapter(currentSubject.subjectName, currentClass.gradeName, chapter.title)
                    },
                    onExplain = {
                        onExplainChapter(currentSubject.subjectName, currentClass.gradeName, chapter.title)
                    }
                )
            }

            item {
                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}

@Composable
fun ChapterCard(
    chapter: Chapter,
    gradeName: String,
    subjectName: String,
    isExpanded: Boolean,
    onToggleExpand: () -> Unit,
    onPlayAudio: (String, String) -> Unit,
    onStartQuiz: () -> Unit,
    onGenerateWorksheet: () -> Unit,
    onExplain: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = AppWhite),
        border = androidx.compose.foundation.BorderStroke(1.dp, AppBorderLight),
        modifier = Modifier
            .fillMaxWidth()
            .testTag("chapter_card_${chapter.id}")
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            // Header: Chapter number badge, English/Hindi Title, Expand button
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onToggleExpand() },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = BrandBlueLight,
                    modifier = Modifier.size(34.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(
                            text = "Ch ${chapter.chapterNumber}",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = BrandBluePrimary,
                                fontSize = 10.sp
                            )
                        )
                    }
                }

                Spacer(modifier = Modifier.width(10.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = chapter.title,
                        style = MaterialTheme.typography.titleSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = TextDarkNavy
                        )
                    )
                    if (chapter.hindiTitle.isNotBlank()) {
                        Text(
                            text = chapter.hindiTitle,
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = BrandBluePrimary,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Medium
                            )
                        )
                    }
                }

                IconButton(
                    onClick = onToggleExpand,
                    modifier = Modifier.size(30.dp)
                ) {
                    Icon(
                        imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                        contentDescription = "Expand",
                        tint = BrandBluePrimary
                    )
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            // Short Description
            Text(
                text = chapter.description,
                style = MaterialTheme.typography.bodySmall.copy(
                    color = TextDarkNavy,
                    lineHeight = 18.sp
                )
            )

            // Voice Read Aloud Button for Chapter summary
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = AppSurfaceLight,
                    border = androidx.compose.foundation.BorderStroke(1.dp, AppBorderLight),
                    modifier = Modifier.clickable {
                        onPlayAudio(chapter.description, "en")
                    }
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.VolumeUp,
                            contentDescription = "Read Aloud",
                            tint = BrandBluePrimary,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "Listen (English)",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.SemiBold,
                                color = BrandBluePrimary,
                                fontSize = 11.sp
                            )
                        )
                    }
                }

                if (chapter.hindiDescription.isNotBlank()) {
                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        color = AppSurfaceLight,
                        border = androidx.compose.foundation.BorderStroke(1.dp, AppBorderLight),
                        modifier = Modifier.clickable {
                            onPlayAudio(chapter.hindiDescription, "hi")
                        }
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.VolumeUp,
                                contentDescription = "Read Aloud Hindi",
                                tint = SuccessGreen,
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "सुनें (हिन्दी)",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.SemiBold,
                                    color = SuccessGreen,
                                    fontSize = 11.sp
                                )
                            )
                        }
                    }
                }
            }

            // Expanded content: Key Topics, Textbook Excerpt & Action buttons
            AnimatedVisibility(visible = isExpanded) {
                Column(modifier = Modifier.padding(top = 12.dp)) {
                    // Key Topics
                    Text(
                        text = "Key Curriculum Topics:",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = TextMutedSlate
                        )
                    )
                    Spacer(modifier = Modifier.height(4.dp))

                    chapter.keyTopics.forEach { topic ->
                        Row(modifier = Modifier.padding(vertical = 2.dp)) {
                            Text("• ", color = BrandBluePrimary, fontWeight = FontWeight.Bold)
                            Text(
                                text = topic,
                                style = MaterialTheme.typography.bodySmall.copy(color = TextDarkNavy)
                            )
                        }
                    }

                    // Sample textbook excerpt
                    if (chapter.sampleTextbookContent.isNotBlank()) {
                        Spacer(modifier = Modifier.height(10.dp))
                        Card(
                            shape = RoundedCornerShape(10.dp),
                            colors = CardDefaults.cardColors(containerColor = AppSurfaceLight),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(10.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "📖 Textbook Lesson Excerpt",
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            fontWeight = FontWeight.Bold,
                                            color = BrandBlueDark
                                        )
                                    )
                                    IconButton(
                                        onClick = { onPlayAudio(chapter.sampleTextbookContent, "en") },
                                        modifier = Modifier.size(24.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.VolumeUp,
                                            contentDescription = "Read Excerpt",
                                            tint = BrandBluePrimary,
                                            modifier = Modifier.size(16.dp)
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = chapter.sampleTextbookContent,
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        color = TextDarkNavy,
                                        lineHeight = 18.sp
                                    )
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Action Buttons Row: Explain, Quiz, Worksheet
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Button(
                            onClick = onExplain,
                            colors = ButtonDefaults.buttonColors(containerColor = BrandBluePrimary),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.weight(1f),
                            contentPadding = PaddingValues(horizontal = 6.dp, vertical = 8.dp)
                        ) {
                            Icon(Icons.Default.Lightbulb, contentDescription = null, modifier = Modifier.size(14.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Explain", fontSize = 11.sp)
                        }

                        Button(
                            onClick = onStartQuiz,
                            colors = ButtonDefaults.buttonColors(containerColor = SuccessGreen),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.weight(1f),
                            contentPadding = PaddingValues(horizontal = 6.dp, vertical = 8.dp)
                        ) {
                            Icon(Icons.Default.Quiz, contentDescription = null, modifier = Modifier.size(14.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Quiz", fontSize = 11.sp)
                        }

                        Button(
                            onClick = onGenerateWorksheet,
                            colors = ButtonDefaults.buttonColors(containerColor = BrandCyan),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.weight(1f),
                            contentPadding = PaddingValues(horizontal = 6.dp, vertical = 8.dp)
                        ) {
                            Icon(Icons.Default.Description, contentDescription = null, modifier = Modifier.size(14.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Worksheet", fontSize = 11.sp)
                        }
                    }
                }
            }
        }
    }
}
