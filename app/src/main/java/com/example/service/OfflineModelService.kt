package com.example.service

import com.example.data.model.Language
import com.example.data.model.LanguagePack
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class OfflineModelService {

    private val _languagePacks = MutableStateFlow<List<LanguagePack>>(
        listOf(
            LanguagePack(
                language = Language("en", "English", "English", "🇬🇧", true, 120),
                isInstalled = true,
                sizeMb = 120,
                offlineFeatures = listOf("ASR Engine", "Core Vocab (80k words)", "Android TTS", "Local OCR")
            ),
            LanguagePack(
                language = Language("hi", "Hindi", "हिन्दी", "🇮🇳", true, 160),
                isInstalled = true,
                sizeMb = 160,
                offlineFeatures = listOf("Devanagari ASR", "Classroom Vocab (95k words)", "Hindi TTS Voice", "Devanagari OCR")
            ),
            LanguagePack(
                language = Language("te", "Telugu", "తెలుగు", "🇮🇳", true, 185),
                isInstalled = false,
                sizeMb = 185,
                offlineFeatures = listOf("Telugu ASR", "Indic Phrase Model", "Telugu Voice Data", "Telugu Script OCR")
            ),
            LanguagePack(
                language = Language("ta", "Tamil", "தமிழ்", "🇮🇳", true, 190),
                isInstalled = false,
                sizeMb = 190,
                offlineFeatures = listOf("Tamil Speech Model", "Regional Dictionary", "Tamil Voice", "Tamil OCR")
            ),
            LanguagePack(
                language = Language("kn", "Kannada", "ಕನ್ನಡ", "🇮🇳", true, 175),
                isInstalled = false,
                sizeMb = 175,
                offlineFeatures = listOf("Kannada ASR", "Kannada Dictionary", "Kannada Voice", "Kannada OCR")
            ),
            LanguagePack(
                language = Language("ml", "Malayalam", "മലയാളം", "🇮🇳", true, 180),
                isInstalled = false,
                sizeMb = 180,
                offlineFeatures = listOf("Malayalam ASR", "Malayalam Lexicon", "Malayalam Voice", "Malayalam OCR")
            ),
            LanguagePack(
                language = Language("mr", "Marathi", "मराठी", "🇮🇳", true, 170),
                isInstalled = false,
                sizeMb = 170,
                offlineFeatures = listOf("Marathi Speech Engine", "Devanagari Lexicon", "Marathi Voice", "Marathi OCR")
            ),
            LanguagePack(
                language = Language("bn", "Bengali", "বাংলা", "🇮🇳", true, 185),
                isInstalled = false,
                sizeMb = 185,
                offlineFeatures = listOf("Bengali Speech Model", "Eastern Indic Vocab", "Bengali Voice", "Bangla OCR")
            )
        )
    )
    val languagePacks: StateFlow<List<LanguagePack>> = _languagePacks.asStateFlow()

    fun isLanguageOfflineReady(code: String): Boolean {
        return _languagePacks.value.any { it.language.code == code && it.isInstalled }
    }

    fun startDownload(pack: LanguagePack, scope: CoroutineScope) {
        scope.launch(Dispatchers.Default) {
            val code = pack.language.code
            updatePackState(code) { it.copy(isDownloading = true, downloadProgress = 0.05f) }

            // Simulated realistic download steps
            for (step in 1..10) {
                delay(250)
                updatePackState(code) { it.copy(downloadProgress = step / 10f) }
            }

            delay(200)
            updatePackState(code) {
                it.copy(
                    isInstalled = true,
                    isDownloading = false,
                    downloadProgress = 1f
                )
            }
        }
    }

    fun deletePack(pack: LanguagePack) {
        // Prevent deleting primary English/Hindi to maintain minimum offline guarantee
        if (pack.language.code == "en" || pack.language.code == "hi") return
        updatePackState(pack.language.code) {
            it.copy(isInstalled = false, isDownloading = false, downloadProgress = 0f)
        }
    }

    private fun updatePackState(code: String, transform: (LanguagePack) -> LanguagePack) {
        _languagePacks.value = _languagePacks.value.map {
            if (it.language.code == code) transform(it) else it
        }
    }

    fun getTotalInstalledSizeMb(): Int {
        return _languagePacks.value.filter { it.isInstalled }.sumOf { it.sizeMb }
    }
}
