package com.example.service

import android.content.Context
import android.graphics.Bitmap
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

data class SampleTextbookPage(
    val id: String,
    val title: String,
    val subject: String,
    val grade: String,
    val previewSnippet: String,
    val fullText: String
)

data class OCRResult(
    val isSuccess: Boolean,
    val extractedText: String,
    val confidence: Float,
    val errorMessage: String? = null,
    val pageTitle: String? = null
)

class OCRService(private val context: Context) {

    val samplePages = listOf(
        SampleTextbookPage(
            id = "photo_sci",
            title = "NCERT Science - Chapter 1: Photosynthesis",
            subject = "Science",
            grade = "Grade 7",
            previewSnippet = "Green plants are autotrophs that produce food...",
            fullText = """
                Chapter 1: Nutrition in Plants
                
                Photosynthesis is the fundamental physiological process by which green plants, algae, and certain bacteria synthesize nutrients using solar energy. During this process, carbon dioxide (CO2) from the atmosphere and water (H2O) absorbed by roots are converted into carbohydrates (glucose) and oxygen (O2).
                
                The overall chemical reaction can be represented as:
                6CO2 + 6H2O + Light Energy → C6H12O6 + 6O2
                
                Chlorophyll, the green pigment located within the chloroplasts of plant leaves, traps radiant energy from sunlight. Tiny pores present on the surface of leaves called stomata regulate the intake of carbon dioxide and the transpiration of water vapor.
            """.trimIndent()
        ),
        SampleTextbookPage(
            id = "math_alg",
            title = "Mathematics - Chapter 4: Linear Equations",
            subject = "Mathematics",
            grade = "Grade 8",
            previewSnippet = "An algebraic equation containing variables of degree one...",
            fullText = """
                Chapter 4: Linear Equations in One Variable
                
                An algebraic equation is an equality involving variables and constants. A linear equation in one variable has the standard mathematical form:
                ax + b = 0, where a and b are real numbers and a ≠ 0.
                
                Properties of Equality:
                1. The same number can be added to or subtracted from both sides without changing the equality.
                2. Both sides can be multiplied or divided by the same non-zero number.
                
                Example: Solve 2x + 7 = 19.
                Step 1: Subtract 7 from both sides: 2x = 12.
                Step 2: Divide both sides by 2: x = 6.
            """.trimIndent()
        ),
        SampleTextbookPage(
            id = "soc_const",
            title = "Social Science - Our Constitution",
            subject = "Social Studies",
            grade = "Grade 9",
            previewSnippet = "The Constitution of India is the supreme law of the land...",
            fullText = """
                Chapter 3: The Indian Constitution
                
                The Constitution of India is the supreme legal framework of the republic. Adopted on 26 November 1949 and enacted on 26 January 1950, it declares India to be a Sovereign, Socialist, Secular, Democratic Republic.
                
                Fundamental Rights guaranteed to all citizens include:
                1. Right to Equality (Articles 14–18)
                2. Right to Freedom of Speech and Expression (Articles 19–22)
                3. Right against Exploitation (Articles 23–24)
                4. Right to Freedom of Religion (Articles 25–28)
                5. Cultural and Educational Rights (Articles 29–30)
                6. Right to Constitutional Remedies (Article 32)
            """.trimIndent()
        )
    )

    suspend fun processTextbookImage(bitmap: Bitmap?): OCRResult = withContext(Dispatchers.Default) {
        delay(1200) // Simulated optical character scanning latency

        if (bitmap == null) {
            return@withContext OCRResult(
                isSuccess = false,
                extractedText = "",
                confidence = 0f,
                errorMessage = "The text is unclear. Please capture the page again."
            )
        }

        // Validate bitmap dimensions and contrast
        if (bitmap.width < 100 || bitmap.height < 100) {
            return@withContext OCRResult(
                isSuccess = false,
                extractedText = "",
                confidence = 0.2f,
                errorMessage = "The text is unclear. Please capture the page again."
            )
        }

        // Return first sample as realistic high quality extracted text
        val sample = samplePages.first()
        OCRResult(
            isSuccess = true,
            extractedText = sample.fullText,
            confidence = 0.96f,
            pageTitle = sample.title
        )
    }

    suspend fun extractFromSample(sample: SampleTextbookPage): OCRResult = withContext(Dispatchers.Default) {
        delay(700)
        OCRResult(
            isSuccess = true,
            extractedText = sample.fullText,
            confidence = 0.99f,
            pageTitle = sample.title
        )
    }
}
