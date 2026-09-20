package com.example.data.model

data class Chapter(
    val id: String,
    val chapterNumber: Int,
    val title: String,
    val hindiTitle: String = "",
    val description: String,
    val hindiDescription: String = "",
    val keyTopics: List<String>,
    val learningObjectives: List<String> = emptyList(),
    val sampleTextbookContent: String = ""
)

data class SubjectSyllabus(
    val subjectName: String,
    val iconName: String, // e.g., "science", "math", "social", "english", "hindi"
    val description: String,
    val chapters: List<Chapter>
)

data class ClassSyllabus(
    val gradeLevel: Int, // 1 to 10
    val gradeName: String, // "Class 1", "Class 2", ... "Class 10"
    val ageGroup: String, // e.g. "Age 6-7", "Age 15-16"
    val tagline: String,
    val subjects: List<SubjectSyllabus>
)
