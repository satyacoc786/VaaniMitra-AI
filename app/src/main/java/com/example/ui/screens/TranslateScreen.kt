package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
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
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Headphones
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.RecordVoiceOver
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Language
import com.example.data.model.SpeechState
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
import com.example.ui.theme.ErrorRedLight
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
fun TranslateScreen(
    speechState: SpeechState,
    speechTranscript: String,
    translatedText: String,
    sourceLanguage: Language,
    targetLanguage: Language,
    isTranslationOffline: Boolean,
    lastTranslationEngine: String,
    classroomTeacherText: String,
    classroomStudentText: String,
    samplePhrases: List<String>,
    onStartListening: () -> Unit,
    onStopListening: () -> Unit,
    onSetTranscript: (String) -> Unit,
    onTranslate: (String) -> Unit,
    onPlayAudio: (String, String) -> Unit,
    onSimulatePreset: (String) -> Unit,
    onSaveLesson: (String, String, String) -> Unit,
    onSwapLanguages: () -> Unit,
    onSendClassroomMessage: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedTab by remember { mutableIntStateOf(0) } // 0 = Voice Translator, 1 = Two-Way Classroom Mode

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(AppWhite)
    ) {
        // Tab header: Voice Translation vs Classroom Two-Way Mode
        PrimaryTabRow(
            selectedTabIndex = selectedTab,
            containerColor = AppWhite,
            contentColor = BrandBluePrimary
        ) {
            Tab(
                selected = selectedTab == 0,
                onClick = { selectedTab = 0 },
                text = {
                    Text(
                        "Voice Translator",
                        fontWeight = if (selectedTab == 0) FontWeight.Bold else FontWeight.Normal
                    )
                },
                modifier = Modifier.testTag("tab_voice_translator")
            )
            Tab(
                selected = selectedTab == 1,
                onClick = { selectedTab = 1 },
                text = {
                    Text(
                        "Classroom Mode (2-Way)",
                        fontWeight = if (selectedTab == 1) FontWeight.Bold else FontWeight.Normal
                    )
                },
                modifier = Modifier.testTag("tab_classroom_mode")
            )
        }

        if (selectedTab == 0) {
            VoiceTranslatorContent(
                speechState = speechState,
                speechTranscript = speechTranscript,
                translatedText = translatedText,
                sourceLanguage = sourceLanguage,
                targetLanguage = targetLanguage,
                isTranslationOffline = isTranslationOffline,
                lastTranslationEngine = lastTranslationEngine,
                samplePhrases = samplePhrases,
                onStartListening = onStartListening,
                onStopListening = onStopListening,
                onSetTranscript = onSetTranscript,
                onTranslate = onTranslate,
                onPlayAudio = onPlayAudio,
                onSimulatePreset = onSimulatePreset,
                onSaveLesson = onSaveLesson
            )
        } else {
            ClassroomTwoWayContent(
                sourceLanguage = sourceLanguage,
                targetLanguage = targetLanguage,
                classroomTeacherText = classroomTeacherText,
                classroomStudentText = classroomStudentText,
                onSwapLanguages = onSwapLanguages,
                onSendMessage = onSendClassroomMessage,
                onPlayAudio = onPlayAudio
            )
        }
    }
}

@Composable
fun VoiceTranslatorContent(
    speechState: SpeechState,
    speechTranscript: String,
    translatedText: String,
    sourceLanguage: Language,
    targetLanguage: Language,
    isTranslationOffline: Boolean,
    lastTranslationEngine: String,
    samplePhrases: List<String>,
    onStartListening: () -> Unit,
    onStopListening: () -> Unit,
    onSetTranscript: (String) -> Unit,
    onTranslate: (String) -> Unit,
    onPlayAudio: (String, String) -> Unit,
    onSimulatePreset: (String) -> Unit,
    onSaveLesson: (String, String, String) -> Unit
) {
    var isEditingTranscript by remember { mutableStateOf(false) }
    var editableTranscriptText by remember(speechTranscript) { mutableStateOf(speechTranscript) }
    var lessonSavedNotification by remember { mutableStateOf(false) }

    // Pulsing animation for microphone button when listening
    val infiniteTransition = rememberInfiniteTransition(label = "mic_pulse")
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 1.0f,
        targetValue = if (speechState == SpeechState.LISTENING) 1.25f else 1.0f,
        animationSpec = infiniteRepeatable(
            animation = tween(600, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse_scale"
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // 1. Processing Stage Breadcrumbs
        item {
            ProcessingStagesIndicator(speechState = speechState)
        }

        // 2. Large Central Microphone Button with Pulse
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
                contentAlignment = Alignment.Center
            ) {
                // Pulse halo
                if (speechState == SpeechState.LISTENING) {
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .scale(pulseScale)
                            .clip(CircleShape)
                            .background(ErrorRed.copy(alpha = 0.25f))
                    )
                }

                Surface(
                    shape = CircleShape,
                    color = when (speechState) {
                        SpeechState.LISTENING -> ErrorRed
                        SpeechState.TRANSCRIBING, SpeechState.TRANSLATING -> WarningOrange
                        else -> BrandBluePrimary
                    },
                    shadowElevation = 6.dp,
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                        .clickable {
                            if (speechState == SpeechState.LISTENING) {
                                onStopListening()
                            } else {
                                onStartListening()
                            }
                        }
                        .testTag("microphone_button")
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        if (speechState == SpeechState.TRANSCRIBING || speechState == SpeechState.TRANSLATING) {
                            CircularProgressIndicator(
                                color = AppWhite,
                                modifier = Modifier.size(36.dp),
                                strokeWidth = 3.dp
                            )
                        } else {
                            Icon(
                                imageVector = if (speechState == SpeechState.LISTENING) Icons.Default.Stop else Icons.Default.Mic,
                                contentDescription = "Microphone",
                                tint = AppWhite,
                                modifier = Modifier.size(40.dp)
                            )
                        }
                    }
                }
            }

            Text(
                text = when (speechState) {
                    SpeechState.LISTENING -> "Listening in ${sourceLanguage.name}... Speak now."
                    SpeechState.TRANSCRIBING -> "Transcribing speech..."
                    SpeechState.TRANSLATING -> "Translating into ${targetLanguage.name}..."
                    SpeechState.SPEAKING -> "Speaking translated text..."
                    SpeechState.ERROR -> "Voice error. Tap mic or select a classroom sample below."
                    else -> "Tap microphone to speak in ${sourceLanguage.name}"
                },
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.SemiBold,
                    color = if (speechState == SpeechState.LISTENING) ErrorRed else TextDarkNavy
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp),
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
        }

        // 3. Quick Classroom Speech Presets (For instant testing without microphone)
        item {
            Column {
                Text(
                    text = "Classroom Speech Presets (Quick Test)",
                    style = MaterialTheme.typography.labelMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = TextMutedSlate
                    )
                )
                Spacer(modifier = Modifier.height(6.dp))
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(samplePhrases) { phrase ->
                        Surface(
                            shape = RoundedCornerShape(20.dp),
                            color = AppSurfaceLight,
                            border = androidx.compose.foundation.BorderStroke(1.dp, AppBorderLight),
                            modifier = Modifier
                                .clip(RoundedCornerShape(20.dp))
                                .clickable { onSimulatePreset(phrase) }
                                .testTag("sample_preset_chip")
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.PlayArrow,
                                    contentDescription = null,
                                    tint = BrandBluePrimary,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = phrase.take(30) + if (phrase.length > 30) "..." else "",
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        color = TextDarkNavy,
                                        fontWeight = FontWeight.Medium
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }

        // 4. Source Transcript Box (Editable)
        item {
            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = AppWhite),
                border = androidx.compose.foundation.BorderStroke(1.dp, AppBorderLight),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(text = sourceLanguage.flag, fontSize = 16.sp)
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Transcript (${sourceLanguage.name})",
                                style = MaterialTheme.typography.labelMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = TextDarkNavy
                                )
                            )
                        }

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            if (speechTranscript.isNotBlank()) {
                                IconButton(
                                    onClick = { onPlayAudio(speechTranscript, sourceLanguage.code) },
                                    modifier = Modifier.size(30.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.VolumeUp,
                                        contentDescription = "Read Transcript Aloud",
                                        tint = BrandBluePrimary,
                                        modifier = Modifier.size(18.dp)
                                    )
                                }
                            }
                            IconButton(
                                onClick = { isEditingTranscript = !isEditingTranscript },
                                modifier = Modifier.size(30.dp)
                            ) {
                                Icon(
                                    imageVector = if (isEditingTranscript) Icons.Default.Check else Icons.Default.Edit,
                                    contentDescription = "Edit Transcript",
                                    tint = BrandBluePrimary,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    if (isEditingTranscript) {
                        OutlinedTextField(
                            value = editableTranscriptText,
                            onValueChange = {
                                editableTranscriptText = it
                                onSetTranscript(it)
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("transcript_edit_field"),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = BrandBluePrimary,
                                unfocusedBorderColor = AppBorderLight
                            ),
                            shape = RoundedCornerShape(10.dp)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(
                            onClick = {
                                isEditingTranscript = false
                                onTranslate(editableTranscriptText)
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = BrandBluePrimary),
                            modifier = Modifier.align(Alignment.End)
                        ) {
                            Text("Save & Translate")
                        }
                    } else {
                        Text(
                            text = if (speechTranscript.isNotBlank()) speechTranscript else "Speech transcript will appear here. You can also tap the edit icon to type directly.",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = if (speechTranscript.isNotBlank()) TextDarkNavy else TextSubtle,
                                lineHeight = 22.sp
                            ),
                            modifier = Modifier.testTag("transcript_display_text")
                        )
                    }
                }
            }
        }

        // 5. Target Translation Box
        item {
            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = BrandBlueLight.copy(alpha = 0.5f)),
                border = androidx.compose.foundation.BorderStroke(1.dp, BrandBluePrimary.copy(alpha = 0.3f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(text = targetLanguage.flag, fontSize = 16.sp)
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Translation (${targetLanguage.name})",
                                style = MaterialTheme.typography.labelMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = BrandBlueDark
                                )
                            )
                        }

                        // Engine Badge (🟢 On-Device vs 🔵 Online)
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = if (isTranslationOffline) SuccessGreenLight else OnlineBlueLight
                        ) {
                            Text(
                                text = if (isTranslationOffline) "🟢 On Device" else "🔵 Online AI",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = if (isTranslationOffline) SuccessGreen else OnlineBlue
                                ),
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = if (translatedText.isNotBlank()) translatedText else "Translated output will be generated here.",
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontWeight = FontWeight.Medium,
                            color = TextDarkNavy,
                            lineHeight = 24.sp
                        ),
                        modifier = Modifier.testTag("translated_display_text")
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Action: 🔊 Play Audio
                        Button(
                            onClick = { onPlayAudio(translatedText, targetLanguage.code) },
                            enabled = translatedText.isNotBlank(),
                            colors = ButtonDefaults.buttonColors(containerColor = BrandBluePrimary),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier.testTag("play_translation_audio_button")
                        ) {
                            Icon(
                                imageVector = Icons.Default.VolumeUp,
                                contentDescription = "Play Audio",
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Play Audio")
                        }

                        // Action: Save to Lessons
                        OutlinedButton(
                            onClick = {
                                val topic = if (speechTranscript.length > 20) speechTranscript.take(20) + "..." else "Classroom Lesson"
                                onSaveLesson(topic, speechTranscript, translatedText)
                                lessonSavedNotification = true
                            },
                            enabled = translatedText.isNotBlank(),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier.testTag("save_translation_lesson_button")
                        ) {
                            Icon(
                                imageVector = Icons.Default.BookmarkBorder,
                                contentDescription = "Save Lesson",
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(if (lessonSavedNotification) "Saved!" else "Save Lesson")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ProcessingStagesIndicator(speechState: SpeechState) {
    val stages = listOf(
        Pair("Listening", speechState == SpeechState.LISTENING),
        Pair("Transcribing", speechState == SpeechState.TRANSCRIBING),
        Pair("Translating", speechState == SpeechState.TRANSLATING),
        Pair("Speaking", speechState == SpeechState.SPEAKING)
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(AppSurfaceLight)
            .padding(vertical = 8.dp, horizontal = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        stages.forEachIndexed { index, (name, isActive) ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .clip(CircleShape)
                        .background(if (isActive) BrandBluePrimary else TextSubtle)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = name,
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontWeight = if (isActive) FontWeight.Bold else FontWeight.Normal,
                        color = if (isActive) BrandBluePrimary else TextMutedSlate
                    )
                )
            }
            if (index < stages.size - 1) {
                Text(text = "→", color = TextSubtle, fontSize = 12.sp)
            }
        }
    }
}

@Composable
fun ClassroomTwoWayContent(
    sourceLanguage: Language,
    targetLanguage: Language,
    classroomTeacherText: String,
    classroomStudentText: String,
    onSwapLanguages: () -> Unit,
    onSendMessage: (String) -> Unit,
    onPlayAudio: (String, String) -> Unit
) {
    var teacherInput by remember { mutableStateOf("") }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = BrandBlueLight),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(
                            text = "Classroom Conversation Mode",
                            style = MaterialTheme.typography.titleSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = BrandBlueDark
                            )
                        )
                        Text(
                            text = "Teacher speaks ${sourceLanguage.name} • Student hears ${targetLanguage.name}",
                            style = MaterialTheme.typography.bodySmall.copy(color = TextMutedSlate)
                        )
                    }

                    IconButton(onClick = onSwapLanguages) {
                        Icon(
                            imageVector = Icons.Default.SwapHoriz,
                            contentDescription = "Swap roles",
                            tint = BrandBluePrimary
                        )
                    }
                }
            }
        }

        // Teacher Dialogue Card
        item {
            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = AppWhite),
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
                            text = "Teacher (${sourceLanguage.name})",
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = BrandBluePrimary
                            )
                        )
                        IconButton(
                            onClick = { onPlayAudio(classroomTeacherText, sourceLanguage.code) },
                            modifier = Modifier.size(28.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.VolumeUp,
                                contentDescription = "Play",
                                tint = BrandBluePrimary,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = classroomTeacherText,
                        style = MaterialTheme.typography.bodyLarge.copy(color = TextDarkNavy)
                    )
                }
            }
        }

        // Student Translated Dialogue Card
        item {
            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = BrandCyanLight.copy(alpha = 0.5f)),
                border = androidx.compose.foundation.BorderStroke(1.dp, BrandCyan.copy(alpha = 0.3f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Student (${targetLanguage.name})",
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = BrandCyan
                            )
                        )
                        IconButton(
                            onClick = { onPlayAudio(classroomStudentText, targetLanguage.code) },
                            modifier = Modifier.size(28.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.VolumeUp,
                                contentDescription = "Play",
                                tint = BrandCyan,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = classroomStudentText,
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontWeight = FontWeight.Medium,
                            color = TextDarkNavy
                        )
                    )
                }
            }
        }

        // Message input row
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = teacherInput,
                    onValueChange = { teacherInput = it },
                    placeholder = { Text("Speak or type classroom message...") },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(24.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = BrandBluePrimary,
                        unfocusedBorderColor = AppBorderLight
                    )
                )
                Spacer(modifier = Modifier.width(8.dp))
                IconButton(
                    onClick = {
                        if (teacherInput.isNotBlank()) {
                            onSendMessage(teacherInput)
                            teacherInput = ""
                        }
                    },
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(BrandBluePrimary)
                ) {
                    Icon(
                        imageVector = Icons.Default.Send,
                        contentDescription = "Send",
                        tint = AppWhite,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    }
}
