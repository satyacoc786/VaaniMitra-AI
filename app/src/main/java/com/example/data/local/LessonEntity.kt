package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "lessons")
data class LessonEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val subject: String,
    val grade: String,
    val sourceLanguage: String,
    val targetLanguage: String,
    val originalContent: String,
    val translatedContent: String,
    val explanation: String,
    val keyPoints: String, // newline separated
    val quizJson: String = "",
    val flashcardsJson: String = "",
    val isOfflineAvailable: Boolean = true,
    val timestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "quiz_records")
data class QuizRecordEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val topic: String,
    val score: Int,
    val totalQuestions: Int,
    val userMode: String,
    val dateFormatted: String,
    val timestamp: Long = System.currentTimeMillis()
)
