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
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material.icons.filled.CloudOff
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.RocketLaunch
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.SdCard
import androidx.compose.material.icons.filled.Security
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.LanguagePack
import com.example.data.model.UserMode
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
import com.example.ui.theme.OnlineBlue
import com.example.ui.theme.OnlineBlueLight
import com.example.ui.theme.SuccessGreen
import com.example.ui.theme.SuccessGreenLight
import com.example.ui.theme.TextDarkNavy
import com.example.ui.theme.TextMutedSlate
import com.example.ui.theme.TextSubtle
import com.example.ui.theme.WarningOrange
import com.example.ui.theme.WarningOrangeLight

@Composable
fun ProfileScreen(
    userMode: UserMode,
    isOnline: Boolean,
    isSimulatingOffline: Boolean,
    languagePacks: List<LanguagePack>,
    totalStorageMb: Int,
    onSetUserMode: (UserMode) -> Unit,
    onToggleSimulateOffline: (Boolean) -> Unit,
    onDownloadPack: (LanguagePack) -> Unit,
    onDeletePack: (LanguagePack) -> Unit,
    onLaunchDemo: () -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(AppWhite)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // 1. User Mode Switcher Card (Prompt Section #3 & #28)
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = AppWhite),
                border = androidx.compose.foundation.BorderStroke(1.dp, AppBorderLight),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Classroom Role & Profile",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = TextDarkNavy
                        )
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        // Teacher Option
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = if (userMode == UserMode.TEACHER) BrandBlueLight else AppSurfaceLight,
                            border = androidx.compose.foundation.BorderStroke(
                                1.5.dp,
                                if (userMode == UserMode.TEACHER) BrandBluePrimary else AppBorderLight
                            ),
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(12.dp))
                                .clickable { onSetUserMode(UserMode.TEACHER) }
                                .testTag("role_selector_teacher")
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Icon(
                                    imageVector = Icons.Default.School,
                                    contentDescription = null,
                                    tint = if (userMode == UserMode.TEACHER) BrandBluePrimary else TextMutedSlate
                                )
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    text = "Teacher Mode",
                                    style = MaterialTheme.typography.labelMedium.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = if (userMode == UserMode.TEACHER) BrandBlueDark else TextDarkNavy
                                    )
                                )
                                Text(
                                    text = "Worksheets, quizzes, translation",
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        color = TextMutedSlate,
                                        fontSize = 10.sp
                                    )
                                )
                            }
                        }

                        // Student Option
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = if (userMode == UserMode.STUDENT) BrandCyanLight else AppSurfaceLight,
                            border = androidx.compose.foundation.BorderStroke(
                                1.5.dp,
                                if (userMode == UserMode.STUDENT) BrandCyan else AppBorderLight
                            ),
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(12.dp))
                                .clickable { onSetUserMode(UserMode.STUDENT) }
                                .testTag("role_selector_student")
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Icon(
                                    imageVector = Icons.Default.Person,
                                    contentDescription = null,
                                    tint = if (userMode == UserMode.STUDENT) BrandCyan else TextMutedSlate
                                )
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    text = "Student Mode",
                                    style = MaterialTheme.typography.labelMedium.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = if (userMode == UserMode.STUDENT) BrandCyan else TextDarkNavy
                                    )
                                )
                                Text(
                                    text = "Explanations, quizzes, pronunciation",
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        color = TextMutedSlate,
                                        fontSize = 10.sp
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }

        // 2. Offline Center: Feature-Level Availability & Simulate Offline Toggle (Prompt Section #15)
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
                        Column {
                            Text(
                                text = "Offline Center",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = TextDarkNavy
                                )
                            )
                            Text(
                                text = "Verify feature availability without internet",
                                style = MaterialTheme.typography.bodySmall.copy(color = TextMutedSlate)
                            )
                        }

                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = if (isOnline) OnlineBlueLight else SuccessGreenLight
                        ) {
                            Text(
                                text = if (isOnline) "🔵 Online Mode" else "🟢 Offline Ready",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = if (isOnline) OnlineBlue else SuccessGreen
                                ),
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Simulate Offline Mode Switch (Critical for hackathon testing!)
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .background(AppSurfaceLight)
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Simulate Offline Mode",
                                style = MaterialTheme.typography.labelMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = TextDarkNavy
                                )
                            )
                            Text(
                                text = "Force app into full offline operation to test reliability.",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = TextMutedSlate,
                                    fontSize = 11.sp
                                )
                            )
                        }

                        Switch(
                            checked = isSimulatingOffline,
                            onCheckedChange = onToggleSimulateOffline,
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = BrandBluePrimary,
                                checkedTrackColor = BrandBlueLight
                            ),
                            modifier = Modifier.testTag("simulate_offline_toggle")
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Text(
                        text = "Feature-Level Availability:",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = TextMutedSlate
                        )
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // 8 Feature Badges requested in prompt section #15
                    val features = listOf(
                        Pair("Speech Recognition", true),
                        Pair("Translation (Local Model)", true),
                        Pair("OCR (Textbook Extractor)", true),
                        Pair("Saved Lessons & Quizzes", true),
                        Pair("Template Quiz", true),
                        Pair("Advanced AI Explanation", !isSimulatingOffline && isOnline),
                        Pair("Cloud Synchronization", !isSimulatingOffline && isOnline),
                        Pair("Advanced AI Generation", !isSimulatingOffline && isOnline)
                    )

                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        features.forEach { (name, isAvailable) ->
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Box(
                                        modifier = Modifier
                                            .size(8.dp)
                                            .clip(CircleShape)
                                            .background(if (isAvailable) SuccessGreen else OnlineBlue)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = name,
                                        style = MaterialTheme.typography.bodySmall.copy(
                                            color = TextDarkNavy,
                                            fontWeight = FontWeight.Medium
                                        )
                                    )
                                }

                                Text(
                                    text = if (isAvailable) "🟢 On-Device" else "🔵 Cloud Only",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = if (isAvailable) SuccessGreen else OnlineBlue
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }

        // 3. Language Pack Manager (Prompt Section #16)
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
                        Column {
                            Text(
                                text = "Language Pack Manager",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = TextDarkNavy
                                )
                            )
                            Text(
                                text = "Installed footprint: $totalStorageMb MB on device",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = BrandBluePrimary,
                                    fontWeight = FontWeight.SemiBold
                                )
                            )
                        }

                        Icon(
                            imageVector = Icons.Default.SdCard,
                            contentDescription = null,
                            tint = BrandBluePrimary
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    languagePacks.forEach { pack ->
                        LanguagePackRow(
                            pack = pack,
                            onDownload = { onDownloadPack(pack) },
                            onDelete = { onDeletePack(pack) }
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
        }

        // 4. Privacy & Data Protection Notice (Prompt Section #18)
        item {
            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = AppSurfaceLight),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Security,
                        contentDescription = "Privacy Shield",
                        tint = SuccessGreen
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = "100% Offline Classroom Privacy",
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = TextDarkNavy
                            )
                        )
                        Text(
                            text = "Student voices and scanned textbook pages never leave your device without explicit teacher confirmation.",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = TextMutedSlate,
                                fontSize = 11.sp
                            )
                        )
                    }
                }
            }
        }

        // 5. Hackathon Guided Demo Trigger Button
        item {
            Button(
                onClick = onLaunchDemo,
                colors = ButtonDefaults.buttonColors(containerColor = BrandCyan),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("launch_guided_demo_profile_button")
            ) {
                Icon(Icons.Default.RocketLaunch, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Start 3-Minute Guided Hackathon Demo")
            }
        }
    }
}

@Composable
fun LanguagePackRow(
    pack: LanguagePack,
    onDownload: () -> Unit,
    onDelete: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(AppSurfaceLight)
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = pack.language.flag, fontSize = 16.sp)
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "${pack.language.name} (${pack.language.nativeName})",
                    style = MaterialTheme.typography.labelMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = TextDarkNavy
                    )
                )
            }
            Text(
                text = "${pack.sizeMb} MB • ${pack.offlineFeatures.joinToString(", ")}",
                style = MaterialTheme.typography.bodySmall.copy(
                    color = TextMutedSlate,
                    fontSize = 10.sp
                ),
                maxLines = 1
            )

            if (pack.isDownloading) {
                Spacer(modifier = Modifier.height(4.dp))
                LinearProgressIndicator(
                    progress = { pack.downloadProgress },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(4.dp)
                        .clip(RoundedCornerShape(2.dp)),
                    color = BrandBluePrimary
                )
            }
        }

        Spacer(modifier = Modifier.width(8.dp))

        if (pack.isInstalled) {
            Surface(
                shape = RoundedCornerShape(6.dp),
                color = SuccessGreenLight
            ) {
                Text(
                    text = "Installed",
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = SuccessGreen
                    ),
                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                )
            }
            if (pack.language.code != "en" && pack.language.code != "hi") {
                IconButton(onClick = onDelete, modifier = Modifier.size(30.dp)) {
                    Icon(Icons.Default.Delete, contentDescription = "Delete Pack", tint = TextSubtle)
                }
            }
        } else if (pack.isDownloading) {
            CircularProgressIndicator(modifier = Modifier.size(20.dp), strokeWidth = 2.dp)
        } else {
            OutlinedButton(
                onClick = onDownload,
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.height(32.dp)
            ) {
                Icon(Icons.Default.Download, contentDescription = null, modifier = Modifier.size(14.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text("Get", fontSize = 11.sp)
            }
        }
    }
}
