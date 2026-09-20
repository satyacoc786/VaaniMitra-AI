package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface LessonDao {
    @Query("SELECT * FROM lessons ORDER BY timestamp DESC")
    fun getAllLessons(): Flow<List<LessonEntity>>

    @Query("SELECT * FROM lessons WHERE id = :id LIMIT 1")
    suspend fun getLessonById(id: Long): LessonEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLesson(lesson: LessonEntity): Long

    @Update
    suspend fun updateLesson(lesson: LessonEntity)

    @Query("DELETE FROM lessons WHERE id = :id")
    suspend fun deleteLessonById(id: Long)

    @Query("SELECT COUNT(*) FROM lessons")
    fun getLessonsCount(): Flow<Int>
}

@Dao
interface QuizRecordDao {
    @Query("SELECT * FROM quiz_records ORDER BY timestamp DESC")
    fun getAllQuizRecords(): Flow<List<QuizRecordEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuizRecord(record: QuizRecordEntity): Long

    @Query("SELECT AVG(CAST(score AS FLOAT) / totalQuestions * 100) FROM quiz_records")
    fun getAverageScore(): Flow<Float?>

    @Query("SELECT COUNT(*) FROM quiz_records")
    fun getTotalAttempts(): Flow<Int>
}
