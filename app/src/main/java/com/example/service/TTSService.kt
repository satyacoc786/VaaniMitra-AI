package com.example.service

import android.content.Context
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.util.Log
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.Locale

class TTSService(context: Context) : TextToSpeech.OnInitListener {

    private val TAG = "VaaniMitraTTS"
    private var tts: TextToSpeech? = null
    private var isInitialized = false

    // Queue of pending speech requests if speak is called before onInit finishes
    private val pendingSpeechQueue = mutableListOf<Triple<String, String, (() -> Unit)?>>()

    private val _isSpeaking = MutableStateFlow(false)
    val isSpeaking: StateFlow<Boolean> = _isSpeaking.asStateFlow()

    private val _speechRate = MutableStateFlow(1.0f)
    val speechRate: StateFlow<Float> = _speechRate.asStateFlow()

    init {
        try {
            Log.d(TAG, "Initializing Android TextToSpeech engine...")
            tts = TextToSpeech(context.applicationContext, this)
        } catch (e: Exception) {
            Log.e(TAG, "Exception during TextToSpeech initialization", e)
        }
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            isInitialized = true
            Log.d(TAG, "TextToSpeech successfully initialized.")

            tts?.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
                override fun onStart(utteranceId: String?) {
                    Log.d(TAG, "TTS utterance started: $utteranceId")
                    _isSpeaking.value = true
                }

                override fun onDone(utteranceId: String?) {
                    Log.d(TAG, "TTS utterance completed: $utteranceId")
                    _isSpeaking.value = false
                }

                @Deprecated("Deprecated in Java")
                override fun onError(utteranceId: String?) {
                    Log.e(TAG, "TTS utterance error on: $utteranceId")
                    _isSpeaking.value = false
                }

                override fun onError(utteranceId: String?, errorCode: Int) {
                    Log.e(TAG, "TTS utterance error on: $utteranceId with errorCode: $errorCode")
                    _isSpeaking.value = false
                }
            })
            tts?.setSpeechRate(_speechRate.value)

            // Drain any pending speech requests that were triggered before engine was ready
            synchronized(pendingSpeechQueue) {
                if (pendingSpeechQueue.isNotEmpty()) {
                    Log.d(TAG, "Draining ${pendingSpeechQueue.size} pending speech items.")
                    val items = ArrayList(pendingSpeechQueue)
                    pendingSpeechQueue.clear()
                    for ((queuedText, queuedLang, queuedDone) in items) {
                        speak(queuedText, queuedLang, queuedDone)
                    }
                }
            }
        } else {
            Log.e(TAG, "TextToSpeech onInit failed with status: $status")
            isInitialized = false
        }
    }

    fun setSpeed(speed: Float) {
        _speechRate.value = speed
        tts?.setSpeechRate(speed)
    }

    fun speak(text: String, languageCode: String = "en", onDone: (() -> Unit)? = null) {
        val trimmed = text.trim()
        if (trimmed.isEmpty()) {
            Log.w(TAG, "speak called with empty text, ignoring.")
            return
        }

        // If not initialized yet, queue it so voice will play as soon as engine is ready!
        if (!isInitialized || tts == null) {
            Log.d(TAG, "TTS engine not yet initialized, queuing utterance: ${trimmed.take(30)}...")
            synchronized(pendingSpeechQueue) {
                pendingSpeechQueue.clear() // Keep latest speech to prevent stacking old audio
                pendingSpeechQueue.add(Triple(trimmed, languageCode, onDone))
            }
            return
        }

        val primaryLocale = when (languageCode.lowercase()) {
            "hi" -> Locale("hi", "IN")
            "te" -> Locale("te", "IN")
            "ta" -> Locale("ta", "IN")
            "kn" -> Locale("kn", "IN")
            "ml" -> Locale("ml", "IN")
            "mr" -> Locale("mr", "IN")
            "bn" -> Locale("bn", "IN")
            else -> Locale.US
        }

        try {
            var langResult = tts?.setLanguage(primaryLocale)
            if (langResult == TextToSpeech.LANG_MISSING_DATA || langResult == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.w(TAG, "Locale $primaryLocale not supported or missing data. Falling back to English (India/US).")
                langResult = tts?.setLanguage(Locale("en", "IN"))
                if (langResult == TextToSpeech.LANG_MISSING_DATA || langResult == TextToSpeech.LANG_NOT_SUPPORTED) {
                    tts?.setLanguage(Locale.US)
                }
            }

            val utteranceId = "vaani_${System.currentTimeMillis()}"
            _isSpeaking.value = true
            Log.d(TAG, "Invoking tts.speak for: ${trimmed.take(40)}... with utteranceId: $utteranceId")
            val speakResult = tts?.speak(trimmed, TextToSpeech.QUEUE_FLUSH, null, utteranceId)
            if (speakResult != TextToSpeech.SUCCESS) {
                Log.e(TAG, "tts.speak returned non-success code: $speakResult")
                _isSpeaking.value = false
            }
        } catch (e: Exception) {
            _isSpeaking.value = false
            Log.e(TAG, "Exception during tts.speak", e)
        }
    }

    fun stop() {
        try {
            tts?.stop()
        } catch (e: Exception) {
            Log.e(TAG, "Exception during tts.stop", e)
        }
        _isSpeaking.value = false
    }

    fun shutdown() {
        try {
            tts?.stop()
            tts?.shutdown()
        } catch (e: Exception) {
            Log.e(TAG, "Exception during tts.shutdown", e)
        }
    }
}
