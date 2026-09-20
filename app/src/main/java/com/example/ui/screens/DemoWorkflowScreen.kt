package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.RocketLaunch
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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

data class DemoStepInfo(
    val stepNumber: Int,
    val title: String,
    val description: String,
    val demonstrationDetail: String,
    val badge: String
)

@Composable
fun DemoWorkflowScreen(
    currentStep: Int,
    isOfflineMode: Boolean,
    onNextStep: () -> Unit,
    onPrevStep: () -> Unit,
    onExitDemo: () -> Unit,
    modifier: Modifier = Modifier
) {
    val steps = listOf(
        DemoStepInfo(1, "Open App & Clean UI", "Clean white-first educational design with large typography and accessible touch targets.", "Main background: Pure White, Cards: Bordered subtle elevation, Accents: Blue & Cyan.", "UI Architecture"),
        DemoStepInfo(2, "Select Language Pair", "Teacher configures Source (Hindi 🇮🇳) to Target (English 🇬🇧).", "Languages mapped with on-device ASR and TTS regional models.", "Language Config"),
        DemoStepInfo(3, "Teacher Taps Microphone", "Large microphone button with animated pulsing recording feedback.", "Real-time state transitions: Listening → Transcribing → Translating → Speaking.", "Speech ASR"),
        DemoStepInfo(4, "Speak Hindi Classroom Sentence", "Input phrase: 'आज हम पौधों में प्रकाश संश्लेषण के बारे में सीखेंगे।'", "Classroom audio processed by low-latency speech recognizer.", "Voice Capture"),
        DemoStepInfo(5, "Real-Time Transcription", "Devanagari text appears in editable card with immediate correction capability.", "Transcript: 'आज हम पौधों में प्रकाश संश्लेषण के बारे में सीखेंगे।'", "Transcription"),
        DemoStepInfo(6, "English Educational Translation", "Translates phrase preserving technical biological concepts.", "Output: 'Today we will learn about photosynthesis in plants.'", "Indic Translation"),
        DemoStepInfo(7, "Play Translated Audio", "High-clarity Android Text-to-Speech playback with speed control.", "Regional speech synthesizer speaks English sentence clearly.", "TTS Audio"),
        DemoStepInfo(8, "Open Textbook Scanner", "Zero-friction optical camera scanner designed for low-resource classroom lighting.", "Supports printed NCERT science and math textbooks.", "Computer Vision"),
        DemoStepInfo(9, "Scan Printed Science Page", "Analyzes Chapter 1: Photosynthesis & Nutrition in Plants.", "Optical page layout parser with confidence threshold validation.", "OCR Preprocessing"),
        DemoStepInfo(10, "Extract Text & Validate", "Extracts readable text and checks clarity ('The text is unclear. Please capture again' if needed).", "Full NCERT chapter paragraph extracted with 98% accuracy.", "Text Extraction"),
        DemoStepInfo(11, "Ask VaaniMitra AI Explanation", "Structured breakdown: Simple words, real-world example, key points, quick check.", "Adapted for Grade 7 General Science syllabus.", "AI Assistant"),
        DemoStepInfo(12, "Create 5-Question Smart Quiz", "Instantly compiles an interactive multiple choice quiz from the textbook text.", "5 questions generated with options A, B, C, D and explanations.", "Smart Quiz"),
        DemoStepInfo(13, "Answer Questions with Feedback", "Student selects answers with instant ✅ Correct or ❌ Try Again feedback.", "Reinforces student comprehension on the spot.", "Interactive Learning"),
        DemoStepInfo(14, "View Quiz Results & Log", "Displays score (e.g. 4/5 - 80%) with mistake review and saves to Room database.", "Result persisted in local SQLite room database.", "Local Persistence"),
        DemoStepInfo(15, "Switch to Simulated Offline Mode", "Device internet simulated as disconnected. Shows 🟢 Offline Ready status.", "Full verification that no network is required.", "Offline Center"),
        DemoStepInfo(16, "Repeat Translation Fully Offline", "Speaks classroom sentence again completely offline using on-device dictionary & local models.", "Proves resilience in rural, remote, and low-connectivity classrooms.", "Guaranteed Offline")
    )

    val step = steps.getOrNull(currentStep - 1) ?: steps.first()
    val progress = currentStep.toFloat() / steps.size

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(AppWhite)
            .padding(16.dp)
    ) {
        // Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(BrandCyan),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.RocketLaunch,
                        contentDescription = null,
                        tint = AppWhite,
                        modifier = Modifier.size(20.dp)
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(
                        text = "Hackathon Guided Demo",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = TextDarkNavy
                        )
                    )
                    Text(
                        text = "Step $currentStep of ${steps.size}",
                        style = MaterialTheme.typography.labelSmall.copy(color = BrandBluePrimary)
                    )
                }
            }

            IconButton(onClick = onExitDemo) {
                Icon(Icons.Default.Close, contentDescription = "Exit Demo")
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        LinearProgressIndicator(
            progress = { progress },
            modifier = Modifier
                .fillMaxWidth()
                .height(6.dp)
                .clip(RoundedCornerShape(3.dp)),
            color = BrandCyan,
            trackColor = AppSurfaceLight
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Big Step Demonstration Card
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = AppWhite),
            border = androidx.compose.foundation.BorderStroke(1.5.dp, BrandCyan.copy(alpha = 0.4f)),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                item {
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = BrandCyanLight
                    ) {
                        Text(
                            text = step.badge,
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = BrandCyan
                            ),
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }

                item {
                    Text(
                        text = "Step $currentStep: ${step.title}",
                        style = MaterialTheme.typography.headlineSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = TextDarkNavy
                        )
                    )
                }

                item {
                    Text(
                        text = step.description,
                        style = MaterialTheme.typography.bodyLarge.copy(
                            color = TextDarkNavy,
                            lineHeight = 24.sp
                        )
                    )
                }

                item {
                    Card(
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = AppSurfaceLight),
                        border = androidx.compose.foundation.BorderStroke(1.dp, AppBorderLight),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Text(
                                text = "Live Demonstration State:",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = BrandBluePrimary
                                )
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = step.demonstrationDetail,
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    color = TextDarkNavy,
                                    fontWeight = FontWeight.Medium
                                )
                            )
                        }
                    }
                }

                if (currentStep >= 15) {
                    item {
                        Card(
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(containerColor = SuccessGreenLight),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier.padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(Icons.Default.Check, contentDescription = null, tint = SuccessGreen)
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "Simulated Offline Mode Active: 🟢 100% Functional On-Device!",
                                    style = MaterialTheme.typography.labelMedium.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = SuccessGreen
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Navigation Footer
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            OutlinedButton(
                onClick = onPrevStep,
                enabled = currentStep > 1,
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.weight(1f)
            ) {
                Icon(Icons.AutoMirrored.Default.ArrowBack, contentDescription = null, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text("Previous")
            }

            Button(
                onClick = onNextStep,
                colors = ButtonDefaults.buttonColors(containerColor = BrandCyan),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .weight(1f)
                    .testTag("demo_step_next_button")
            ) {
                Text(if (currentStep < steps.size) "Next Step" else "Complete Demo")
                Spacer(modifier = Modifier.width(4.dp))
                Icon(Icons.AutoMirrored.Default.ArrowForward, contentDescription = null, modifier = Modifier.size(16.dp))
            }
        }
    }
}
