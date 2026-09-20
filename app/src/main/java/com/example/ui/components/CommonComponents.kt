package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material.icons.filled.CloudOff
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.material.icons.filled.Translate
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Language
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
import com.example.ui.theme.ErrorRed
import com.example.ui.theme.OnlineBlue
import com.example.ui.theme.OnlineBlueLight
import com.example.ui.theme.SuccessGreen
import com.example.ui.theme.SuccessGreenLight
import com.example.ui.theme.TextDarkNavy
import com.example.ui.theme.TextMutedSlate
import com.example.ui.theme.TextSubtle

@Composable
fun VaaniMitraHeader(
    userMode: UserMode,
    sourceLanguage: Language,
    targetLanguage: Language,
    isOnline: Boolean,
    isOfflineReady: Boolean,
    onToggleUserMode: () -> Unit,
    onSwapLanguages: () -> Unit,
    onSelectSourceLanguage: (Language) -> Unit,
    onSelectTargetLanguage: (Language) -> Unit,
    onLaunchDemo: () -> Unit,
    modifier: Modifier = Modifier
) {
    var showSourceLangMenu by remember { mutableStateOf(false) }
    var showTargetLangMenu by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(AppWhite)
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        // Top row: Brand title & User Mode switcher badge
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(BrandBluePrimary),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.AutoAwesome,
                            contentDescription = "VaaniMitra AI Logo",
                            tint = AppWhite,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "VaaniMitra AI",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold,
                            color = TextDarkNavy,
                            letterSpacing = (-0.5).sp
                        )
                    )
                }
                Text(
                    text = "Every Classroom. Every Language. Every Learner.",
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = TextMutedSlate,
                        fontSize = 11.sp
                    )
                )
            }

            // Mode switcher badge (Teacher / Student)
            Surface(
                shape = RoundedCornerShape(20.dp),
                color = if (userMode == UserMode.TEACHER) BrandBlueLight else BrandCyanLight,
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .clickable { onToggleUserMode() }
                    .border(
                        1.dp,
                        if (userMode == UserMode.TEACHER) BrandBluePrimary.copy(alpha = 0.4f) else BrandCyan.copy(alpha = 0.4f),
                        RoundedCornerShape(20.dp)
                    )
                    .testTag("user_mode_badge")
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = if (userMode == UserMode.TEACHER) Icons.Default.School else Icons.Default.Person,
                        contentDescription = "User Mode",
                        tint = if (userMode == UserMode.TEACHER) BrandBlueDark else BrandCyan,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = if (userMode == UserMode.TEACHER) "Teacher" else "Student",
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontWeight = FontWeight.SemiBold,
                            color = if (userMode == UserMode.TEACHER) BrandBlueDark else BrandCyan
                        )
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Second row: Language selector pair + Accurate Status Pill (Offline Ready vs Online Mode)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Language Pair Switcher
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = AppSurfaceLight,
                border = androidx.compose.foundation.BorderStroke(1.dp, AppBorderLight),
                modifier = Modifier.weight(1f)
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    // Source
                    Box {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.clickable { showSourceLangMenu = true }
                        ) {
                            Text(text = sourceLanguage.flag, fontSize = 14.sp)
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = sourceLanguage.name,
                                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Medium)
                            )
                        }
                        DropdownMenu(
                            expanded = showSourceLangMenu,
                            onDismissRequest = { showSourceLangMenu = false }
                        ) {
                            Language.ALL.forEach { lang ->
                                DropdownMenuItem(
                                    text = { Text("${lang.flag} ${lang.name} (${lang.nativeName})") },
                                    onClick = {
                                        onSelectSourceLanguage(lang)
                                        showSourceLangMenu = false
                                    }
                                )
                            }
                        }
                    }

                    IconButton(
                        onClick = onSwapLanguages,
                        modifier = Modifier.size(28.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.SwapHoriz,
                            contentDescription = "Swap Languages",
                            tint = BrandBluePrimary,
                            modifier = Modifier.size(18.dp)
                        )
                    }

                    // Target
                    Box {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.clickable { showTargetLangMenu = true }
                        ) {
                            Text(text = targetLanguage.flag, fontSize = 14.sp)
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = targetLanguage.name,
                                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Medium)
                            )
                        }
                        DropdownMenu(
                            expanded = showTargetLangMenu,
                            onDismissRequest = { showTargetLangMenu = false }
                        ) {
                            Language.ALL.forEach { lang ->
                                DropdownMenuItem(
                                    text = { Text("${lang.flag} ${lang.name} (${lang.nativeName})") },
                                    onClick = {
                                        onSelectTargetLanguage(lang)
                                        showTargetLangMenu = false
                                    }
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.width(8.dp))

            // Accurate Status Indicator Pill (🟢 Offline Ready / 🔵 Online Mode)
            StatusPill(isOnline = isOnline, isOfflineReady = isOfflineReady)
        }
    }
}

@Composable
fun StatusPill(isOnline: Boolean, isOfflineReady: Boolean) {
    val pillBg = if (isOnline) OnlineBlueLight else SuccessGreenLight
    val pillColor = if (isOnline) OnlineBlue else SuccessGreen
    val icon = if (isOnline) Icons.Default.Cloud else Icons.Default.CloudOff
    val label = if (isOnline) "🔵 Online Mode" else "🟢 Offline Ready"

    Surface(
        shape = RoundedCornerShape(12.dp),
        color = pillBg,
        border = androidx.compose.foundation.BorderStroke(1.dp, pillColor.copy(alpha = 0.3f)),
        modifier = Modifier.testTag("status_indicator_pill")
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .clip(CircleShape)
                    .background(pillColor)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = if (isOnline) "Online Mode" else "Offline Ready",
                style = MaterialTheme.typography.labelSmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = pillColor
                )
            )
        }
    }
}

@Composable
fun VaaniMitraBottomNav(
    currentTab: NavTab,
    onTabSelected: (NavTab) -> Unit,
    modifier: Modifier = Modifier
) {
    NavigationBar(
        modifier = modifier
            .windowInsetsPadding(WindowInsets.navigationBars)
            .border(1.dp, AppBorderLight)
            .shadow(4.dp),
        containerColor = AppWhite,
        tonalElevation = 2.dp
    ) {
        val items = listOf(
            Triple(NavTab.HOME, Icons.Default.Home, "Home"),
            Triple(NavTab.TRANSLATE, Icons.Default.Translate, "Translate"),
            Triple(NavTab.LEARN, Icons.AutoMirrored.Default.MenuBook, "Learn"),
            Triple(NavTab.SCAN, Icons.Default.CameraAlt, "Scan"),
            Triple(NavTab.PROFILE, Icons.Default.Person, "Profile")
        )

        items.forEach { (tab, icon, label) ->
            val selected = currentTab == tab
            NavigationBarItem(
                selected = selected,
                onClick = { onTabSelected(tab) },
                icon = {
                    Icon(
                        imageVector = icon,
                        contentDescription = label,
                        modifier = Modifier.size(24.dp)
                    )
                },
                label = {
                    Text(
                        text = label,
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal
                        )
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = BrandBluePrimary,
                    selectedTextColor = BrandBluePrimary,
                    indicatorColor = BrandBlueLight,
                    unselectedIconColor = TextSubtle,
                    unselectedTextColor = TextMutedSlate
                ),
                modifier = Modifier.testTag("nav_tab_${label.lowercase()}")
            )
        }
    }
}

@Composable
fun PrivacyConsentDialog(
    visible: Boolean,
    onConfirm: () -> Unit,
    onCancel: () -> Unit
) {
    if (visible) {
        AlertDialog(
            onDismissRequest = onCancel,
            title = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = "Privacy Shield",
                        tint = BrandBluePrimary,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Classroom Data Privacy",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
                }
            },
            text = {
                Text(
                    text = "This request requires an online AI service. Your classroom text will be sent to the selected service for deep linguistic processing. Do you wish to continue?\n\n(Local on-device features remain 100% private and offline)",
                    style = MaterialTheme.typography.bodyMedium.copy(color = TextDarkNavy)
                )
            },
            confirmButton = {
                Button(
                    onClick = onConfirm,
                    colors = ButtonDefaults.buttonColors(containerColor = BrandBluePrimary),
                    modifier = Modifier.testTag("privacy_dialog_confirm")
                ) {
                    Text("Continue")
                }
            },
            dismissButton = {
                OutlinedButton(
                    onClick = onCancel,
                    modifier = Modifier.testTag("privacy_dialog_cancel")
                ) {
                    Text("Cancel")
                }
            },
            containerColor = AppWhite,
            shape = RoundedCornerShape(16.dp)
        )
    }
}
