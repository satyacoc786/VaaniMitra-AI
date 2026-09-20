package com.example.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [LessonEntity::class, QuizRecordEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun lessonDao(): LessonDao
    abstract fun quizRecordDao(): QuizRecordDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "vaanimitra_database"
                )
                    .addCallback(DatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                instance
            }
        }

        private class DatabaseCallback(
            private val scope: CoroutineScope
        ) : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                INSTANCE?.let { database ->
                    scope.launch(Dispatchers.IO) {
                        populateInitialLessons(database.lessonDao())
                    }
                }
            }

            suspend fun populateInitialLessons(lessonDao: LessonDao) {
                val initialLessons = listOf(
                    LessonEntity(
                        title = "Photosynthesis & Plant Biology",
                        subject = "Science",
                        grade = "Grade 7",
                        sourceLanguage = "Hindi",
                        targetLanguage = "English",
                        originalContent = "प्रकाश संश्लेषण वह प्रक्रिया है जिसके द्वारा हरे पौधे सूर्य के प्रकाश, जल और कार्बन डाइऑक्साइड का उपयोग करके अपना भोजन तैयार करते हैं।",
                        translatedContent = "Photosynthesis is the process by which green plants synthesize nutrients from carbon dioxide and water using sunlight and chlorophyll.",
                        explanation = "Plants act as the natural food factories of our planet. Using chlorophyll (the green pigment in leaves), they trap solar energy to turn water from soil and carbon dioxide from air into glucose (food) while releasing oxygen into the atmosphere.",
                        keyPoints = "1. Requires Sunlight, Water (H2O), Carbon Dioxide (CO2), and Chlorophyll\n2. Produces Glucose (C6H12O6) and Oxygen (O2)\n3. Chlorophyll in chloroplasts absorbs solar radiation\n4. Stomata in leaves facilitate gas exchange",
                        isOfflineAvailable = true
                    ),
                    LessonEntity(
                        title = "Algebra & Linear Equations",
                        subject = "Mathematics",
                        grade = "Grade 8",
                        sourceLanguage = "Hindi",
                        targetLanguage = "English",
                        originalContent = "रैखिक समीकरण गणितीय कथन होते हैं जिनमें अज्ञात चर की अधिकतम घात एक होती है। उदाहरण के लिए: 2x + 5 = 15.",
                        translatedContent = "Linear equations are mathematical statements where the highest power of the variable is one. For example: 2x + 5 = 15.",
                        explanation = "Think of a linear equation like a balanced weighing scale. Whatever operation you perform on one side (adding, subtracting, multiplying, dividing), you must perform on the other side to keep balance and isolate the variable 'x'.",
                        keyPoints = "1. Degree of variable is strictly 1\n2. Standard form is ax + b = c\n3. Balance principle: operations apply to both LHS and RHS\n4. Solution represents the point where equality holds true",
                        isOfflineAvailable = true
                    ),
                    LessonEntity(
                        title = "English Grammar: Tenses & Verbs",
                        subject = "English",
                        grade = "Grade 6",
                        sourceLanguage = "Hindi",
                        targetLanguage = "English",
                        originalContent = "क्रिया काल हमें यह बताता है कि कोई कार्य भूतकाल, वर्तमान काल या भविष्य काल में कब घटित हुआ।",
                        translatedContent = "Verb tenses indicate the precise time an action happened: in the past, present, or future.",
                        explanation = "In English, verbs change their form based on time. Simple Present (I eat), Present Continuous (I am eating), Simple Past (I ate), and Simple Future (I will eat). Master these regular and irregular patterns for fluent expression.",
                        keyPoints = "1. Three primary times: Past, Present, Future\n2. Four aspects: Simple, Continuous, Perfect, Perfect Continuous\n3. Subject-Verb Agreement: Singular subjects take singular verbs\n4. Helping verbs (is, am, are, was, were, will) anchor the timeline",
                        isOfflineAvailable = true
                    ),
                    LessonEntity(
                        title = "Computer Networks & Internet",
                        subject = "Computer Science",
                        grade = "Grade 9",
                        sourceLanguage = "Hindi",
                        targetLanguage = "English",
                        originalContent = "कंप्यूटर नेटवर्क दो या दो से अधिक परस्पर जुड़े उपकरणों का एक समूह है जो डेटा और संसाधन साझा करते हैं।",
                        translatedContent = "A computer network is an interconnected group of autonomous computing devices that exchange data and share resources.",
                        explanation = "Just like roads connect cities to transport goods, networks connect laptops, servers, and phones via optical cables or Wi-Fi radio waves to transmit data packets using the standard TCP/IP protocol.",
                        keyPoints = "1. LAN (Local Area Network) vs WAN (Wide Area Network)\n2. IP Addresses uniquely identify each device on the network\n3. Routers direct packet traffic between different subnets\n4. The Internet is the global network of interconnected networks",
                        isOfflineAvailable = true
                    )
                )

                for (lesson in initialLessons) {
                    lessonDao.insertLesson(lesson)
                }
            }
        }
    }
}
