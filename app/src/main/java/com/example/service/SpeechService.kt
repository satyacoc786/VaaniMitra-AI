package com.example.service

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import com.example.data.model.SpeechState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Locale

class SpeechService(private val context: Context) {

    private var speechRecognizer: SpeechRecognizer? = null

    private val _speechState = MutableStateFlow(SpeechState.IDLE)
    val speechState: StateFlow<SpeechState> = _speechState.asStateFlow()

    private val _transcript = MutableStateFlow("")
    val transcript: StateFlow<String> = _transcript.asStateFlow()

    private val _soundLevel = MutableStateFlow(0f)
    val soundLevel: StateFlow<Float> = _soundLevel.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    val classroomSamplePhrases = listOf(
        "आज हम पौधों में प्रकाश संश्लेषण के बारे में सीखेंगे।",
        "कृपया सभी छात्र अपनी विज्ञान की पुस्तक का पृष्ठ 42 खोलें।",
        "What is the function of the cell membrane in living organisms?",
        "गणित में आज हम दो चरों वाले रैखिक समीकरण हल करेंगे।",
        "Can anyone explain why the sky appears blue during the day?",
        "कल की परीक्षा के लिए सभी सूत्र याद करके आएं।"
    )

    init {
        initRecognizer()
    }

    private fun initRecognizer() {
        if (SpeechRecognizer.isRecognitionAvailable(context)) {
            try {
                speechRecognizer = SpeechRecognizer.createSpeechRecognizer(context).apply {
                    setRecognitionListener(createListener())
                }
            } catch (e: Exception) {
                speechRecognizer = null
            }
        }
    }

    private fun createListener(): RecognitionListener = object : RecognitionListener {
        override fun onReadyForSpeech(params: Bundle?) {
            _speechState.value = SpeechState.LISTENING
        }

        override fun onBeginningOfSpeech() {
            _speechState.value = SpeechState.LISTENING
        }

        override fun onRmsChanged(rmsdB: Float) {
            _soundLevel.value = (rmsdB / 10f).coerceIn(0f, 1f)
        }

        override fun onBufferReceived(buffer: ByteArray?) {}

        override fun onEndOfSpeech() {
            _speechState.value = SpeechState.TRANSCRIBING
        }

        override fun onError(error: Int) {
            _soundLevel.value = 0f
            val message = when (error) {
                SpeechRecognizer.ERROR_AUDIO -> "Audio recording error. Please check microphone."
                SpeechRecognizer.ERROR_CLIENT -> "Speech recognition client error."
                SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS -> "Microphone permission required."
                SpeechRecognizer.ERROR_NETWORK -> "Network error during speech recognition."
                SpeechRecognizer.ERROR_NETWORK_TIMEOUT -> "Recognition timed out."
                SpeechRecognizer.ERROR_NO_MATCH -> "I couldn't understand the speech clearly. Please try again."
                SpeechRecognizer.ERROR_RECOGNIZER_BUSY -> "Speech service is busy."
                SpeechRecognizer.ERROR_SERVER -> "Server error. Switched to offline fallback."
                SpeechRecognizer.ERROR_SPEECH_TIMEOUT -> "No speech detected. Please speak into the mic."
                else -> "Speech recognition error. Please try again."
            }
            _errorMessage.value = message
            _speechState.value = SpeechState.ERROR
        }

        override fun onResults(results: Bundle?) {
            _soundLevel.value = 0f
            val matches = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
            val recognizedText = matches?.firstOrNull() ?: ""
            _transcript.value = recognizedText
            _speechState.value = SpeechState.IDLE
        }

        override fun onPartialResults(partialResults: Bundle?) {
            val matches = partialResults?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
            val partialText = matches?.firstOrNull()
            if (!partialText.isNullOrBlank()) {
                _transcript.value = partialText
            }
        }

        override fun onEvent(eventType: Int, params: Bundle?) {}
    }

    fun startListening(languageCode: String = "hi") {
        _errorMessage.value = null
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            val locale = when (languageCode.lowercase()) {
                "hi" -> "hi-IN"
                "te" -> "te-IN"
                "ta" -> "ta-IN"
                "kn" -> "kn-IN"
                "ml" -> "ml-IN"
                "mr" -> "mr-IN"
                "bn" -> "bn-IN"
                else -> "en-US"
            }
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, locale)
            putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE, locale)
            putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true)
            putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 1)
        }

        try {
            if (speechRecognizer == null) {
                initRecognizer()
            }
            speechRecognizer?.startListening(intent)
            _speechState.value = SpeechState.LISTENING
        } catch (e: Exception) {
            _errorMessage.value = "Microphone unavailable. Use demo presets below."
            _speechState.value = SpeechState.ERROR
        }
    }

    fun stopListening() {
        try {
            speechRecognizer?.stopListening()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun setSpeechState(state: SpeechState) {
        _speechState.value = state
    }

    fun setTranscript(text: String) {
        _transcript.value = text
    }

    /**
     * Simulates speech input for hackathon demonstration or testing when mic is unavailable.
     */
    fun simulateVoiceInput(presetPhrase: String, scope: CoroutineScope, onTranscribed: (String) -> Unit) {
        scope.launch(Dispatchers.Default) {
            _errorMessage.value = null
            _speechState.value = SpeechState.LISTENING
            _soundLevel.value = 0.8f
            delay(1200)

            _speechState.value = SpeechState.TRANSCRIBING
            _soundLevel.value = 0.2f
            delay(800)

            _transcript.value = presetPhrase
            _speechState.value = SpeechState.IDLE
            _soundLevel.value = 0f
            onTranscribed(presetPhrase)
        }
    }

    fun destroy() {
        try {
            speechRecognizer?.destroy()
            speechRecognizer = null
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
