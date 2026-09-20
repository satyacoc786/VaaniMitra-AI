package com.example.data.local

import kotlinx.coroutines.flow.Flow

class LessonRepository(
    private val lessonDao: LessonDao,
    private val quizRecordDao: QuizRecordDao
) {
    val allLessons: Flow<List<LessonEntity>> = lessonDao.getAllLessons()
    val lessonsCount: Flow<Int> = lessonDao.getLessonsCount()
    val allQuizRecords: Flow<List<QuizRecordEntity>> = quizRecordDao.getAllQuizRecords()
    val averageScore: Flow<Float?> = quizRecordDao.getAverageScore()
    val totalAttempts: Flow<Int> = quizRecordDao.getTotalAttempts()

    suspend fun getLessonById(id: Long): LessonEntity? = lessonDao.getLessonById(id)

    suspend fun saveLesson(lesson: LessonEntity): Long = lessonDao.insertLesson(lesson)

    suspend fun updateLesson(lesson: LessonEntity) = lessonDao.updateLesson(lesson)

    suspend fun deleteLesson(id: Long) = lessonDao.deleteLessonById(id)

    suspend fun recordQuizResult(topic: String, score: Int, totalQuestions: Int, userMode: String) {
        val record = QuizRecordEntity(
            topic = topic,
            score = score,
            totalQuestions = totalQuestions,
            userMode = userMode,
            dateFormatted = java.text.SimpleDateFormat("MMM dd, yyyy", java.util.Locale.getDefault()).format(java.util.Date())
        )
        quizRecordDao.insertQuizRecord(record)
    }
}
