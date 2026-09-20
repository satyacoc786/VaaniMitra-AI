package com.example.ui.screens

import android.content.Intent
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
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Print
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.WorksheetData
import com.example.ui.theme.AppBorderLight
import com.example.ui.theme.AppCardBg
import com.example.ui.theme.AppSurfaceLight
import com.example.ui.theme.AppWhite
import com.example.ui.theme.BrandBlueDark
import com.example.ui.theme.BrandBlueLight
import com.example.ui.theme.BrandBluePrimary
import com.example.ui.theme.BrandCyan
import com.example.ui.theme.SuccessGreen
import com.example.ui.theme.SuccessGreenLight
import com.example.ui.theme.TextDarkNavy
import com.example.ui.theme.TextMutedSlate
import com.example.ui.theme.TextSubtle

@Composable
fun WorksheetScreen(
    currentWorksheet: WorksheetData?,
    isGenerating: Boolean,
    onGenerate: (String, String, String, String, String, Int) -> Unit,
    onMarkReviewed: () -> Unit,
    onSaveToLessons: (String, String, String) -> Unit,
    onPlayAudio: (String, String) -> Unit = { _, _ -> },
    onClose: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    var subject by remember { mutableStateOf("Science") }
    var grade by remember { mutableStateOf("Grade 7") }
    var topic by remember { mutableStateOf("Photosynthesis") }
    var language by remember { mutableStateOf("English") }
    var difficulty by remember { mutableStateOf("Medium") }
    var questionCount by remember { mutableStateOf(10) }
    var isSaved by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(AppWhite)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Top Header
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
                    text = "AI Worksheet Maker",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = TextDarkNavy
                    )
                )
                Spacer(modifier = Modifier.width(48.dp))
            }
        }

        // Generator Input Form
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = AppWhite),
                border = androidx.compose.foundation.BorderStroke(1.dp, AppBorderLight),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Customize Curriculum Worksheet",
                        style = MaterialTheme.typography.titleSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = TextDarkNavy
                        )
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    OutlinedTextField(
                        value = topic,
                        onValueChange = { topic = it },
                        label = { Text("Topic / Chapter") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("worksheet_topic_input"),
                        shape = RoundedCornerShape(10.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = BrandBluePrimary,
                            unfocusedBorderColor = AppBorderLight
                        )
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    // Subject, Grade, Difficulty row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedTextField(
                            value = subject,
                            onValueChange = { subject = it },
                            label = { Text("Subject") },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(10.dp)
                        )
                        OutlinedTextField(
                            value = grade,
                            onValueChange = { grade = it },
                            label = { Text("Class") },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(10.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedTextField(
                            value = difficulty,
                            onValueChange = { difficulty = it },
                            label = { Text("Difficulty") },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(10.dp)
                        )
                        OutlinedTextField(
                            value = questionCount.toString(),
                            onValueChange = { questionCount = it.toIntOrNull() ?: 10 },
                            label = { Text("Questions") },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(10.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = {
                            onGenerate(subject, grade, topic, language, difficulty, questionCount)
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = BrandBluePrimary),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("generate_worksheet_button")
                    ) {
                        if (isGenerating) {
                            CircularProgressIndicator(
                                color = AppWhite,
                                modifier = Modifier.size(20.dp),
                                strokeWidth = 2.dp
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Generating Classroom Material...")
                        } else {
                            Icon(Icons.Default.AutoAwesome, contentDescription = null)
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("✨ Generate Worksheet")
                        }
                    }
                }
            }
        }

        // Generated Output Section
        if (currentWorksheet != null) {
            // Teacher Review Bar (Prompt Section #10: Teacher must be able to review)
            item {
                Card(
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (currentWorksheet.isReviewedByTeacher) SuccessGreenLight else BrandBlueLight
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = currentWorksheet.isReviewedByTeacher,
                            onCheckedChange = { onMarkReviewed() },
                            colors = CheckboxDefaults.colors(checkedColor = SuccessGreen)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Column {
                            Text(
                                text = if (currentWorksheet.isReviewedByTeacher) "Verified by Teacher ✅" else "Teacher Review Required",
                                style = MaterialTheme.typography.labelMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = if (currentWorksheet.isReviewedByTeacher) SuccessGreen else BrandBlueDark
                                )
                            )
                            Text(
                                text = "Confirm accuracy before printing or distributing to students.",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = TextMutedSlate,
                                    fontSize = 11.sp
                                )
                            )
                        }
                    }
                }
            }

            // Multiple Choice Questions Section
            item {
                WorksheetSectionCard(
                    title = "Part A: Multiple Choice Questions (MCQs)",
                    items = currentWorksheet.multipleChoiceQuestions,
                    onPlayAudio = onPlayAudio
                )
            }

            // Fill in the Blanks Section
            item {
                WorksheetSectionCard(
                    title = "Part B: Fill in the Blanks",
                    items = currentWorksheet.fillInTheBlanks,
                    onPlayAudio = onPlayAudio
                )
            }

            // Short Answer Section
            item {
                WorksheetSectionCard(
                    title = "Part C: Short Answer Questions",
                    items = currentWorksheet.shortAnswerQuestions,
                    onPlayAudio = onPlayAudio
                )
            }

            // Answer Key Section
            item {
                WorksheetSectionCard(
                    title = "Teacher's Guide & Answer Key",
                    items = currentWorksheet.answerKey,
                    isAnswerKey = true,
                    onPlayAudio = onPlayAudio
                )
            }

            // Action Buttons: Share, Print, Save
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = {
                            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                                type = "text/plain"
                                putExtra(
                                    Intent.EXTRA_TEXT,
                                    "${currentWorksheet.topic} Worksheet (${currentWorksheet.grade})\n\n" +
                                            currentWorksheet.multipleChoiceQuestions.joinToString("\n\n")
                                )
                            }
                            context.startActivity(Intent.createChooser(shareIntent, "Share Worksheet"))
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = BrandBluePrimary),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(Icons.Default.Share, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Share")
                    }

                    OutlinedButton(
                        onClick = {
                            onSaveToLessons(
                                "${currentWorksheet.topic} Worksheet",
                                currentWorksheet.topic,
                                currentWorksheet.multipleChoiceQuestions.joinToString("\n")
                            )
                            isSaved = true
                        },
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(Icons.Default.BookmarkBorder, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(if (isSaved) "Saved" else "Save")
                    }
                }
            }
        }
    }
}

@Composable
fun WorksheetSectionCard(
    title: String,
    items: List<String>,
    isAnswerKey: Boolean = false,
    onPlayAudio: (String, String) -> Unit = { _, _ -> }
) {
    Card(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = if (isAnswerKey) AppSurfaceLight else AppWhite),
        border = androidx.compose.foundation.BorderStroke(1.dp, AppBorderLight),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = if (isAnswerKey) BrandBlueDark else TextDarkNavy
                    ),
                    modifier = Modifier.weight(1f)
                )

                IconButton(
                    onClick = {
                        val fullText = items.joinToString(". ")
                        onPlayAudio(fullText, "en")
                    },
                    modifier = Modifier.size(28.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.VolumeUp,
                        contentDescription = "Read Section Aloud",
                        tint = BrandBluePrimary,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(10.dp))

            items.forEach { item ->
                Text(
                    text = item,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = TextDarkNavy,
                        lineHeight = 22.sp
                    ),
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }
        }
    }
}
