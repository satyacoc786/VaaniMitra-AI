package com.example.data.repository

import com.example.data.model.Chapter
import com.example.data.model.ClassSyllabus
import com.example.data.model.SubjectSyllabus

object SyllabusRepository {

    val allClassesSyllabus: List<ClassSyllabus> = listOf(
        // CLASS 1
        ClassSyllabus(
            gradeLevel = 1,
            gradeName = "Class 1",
            ageGroup = "Age 6-7",
            tagline = "Foundational Literacy & Environmental Wonder",
            subjects = listOf(
                SubjectSyllabus(
                    subjectName = "Environmental Studies (EVS)",
                    iconName = "science",
                    description = "Exploring living things, family, senses and animals",
                    chapters = listOf(
                        Chapter(
                            id = "c1_evs_1",
                            chapterNumber = 1,
                            title = "My Body & The 5 Senses",
                            hindiTitle = "मेरा शरीर और पाँच इंद्रियाँ",
                            description = "Learning our body parts: eyes to see, ears to hear, nose to smell, tongue to taste, and skin to touch.",
                            hindiDescription = "शरीर के अंग और उनकी ज्ञानेंद्रियाँ: आँख, कान, नाक, जीभ और त्वचा।",
                            keyTopics = listOf("Eyes, Ears, Nose, Mouth, Hands", "Five Senses", "Personal Hygiene & Cleanliness"),
                            sampleTextbookContent = "We have five senses. Our eyes help us see colorful flowers. Our ears hear sweet music. Our nose smells food. Our tongue tastes sweet fruits. Clean hands keep us healthy."
                        ),
                        Chapter(
                            id = "c1_evs_2",
                            chapterNumber = 2,
                            title = "My Family & Home",
                            hindiTitle = "मेरा परिवार और घर",
                            description = "Understanding family members, helping parents, and parts of a warm loving home.",
                            hindiDescription = "परिवार के सदस्य, माता-पिता की सहायता और प्यारा घर।",
                            keyTopics = listOf("Parents, Siblings & Grandparents", "Rooms in a House", "Sharing & Caring"),
                            sampleTextbookContent = "A family lives together in a home. Mother and father care for us. Grandparents tell sweet bedtime stories. We should help keep our home neat and tidy."
                        ),
                        Chapter(
                            id = "c1_evs_3",
                            chapterNumber = 3,
                            title = "Plants Around Us",
                            hindiTitle = "हमारे आसपास के पेड़-पौधे",
                            description = "Identifying leaves, colorful flowers, big trees, and little garden herbs.",
                            hindiDescription = "पेड़, पौधे, पत्तियाँ और रंग-बिरंगे फूल।",
                            keyTopics = listOf("Big Trees & Small Herbs", "Leaves, Flowers & Fruits", "Watering Plants"),
                            sampleTextbookContent = "Plants are our green friends. Big plants are called trees. Mango and Banyan are big trees. Plants need sunlight and clean water to grow. They give us shade and sweet fruits."
                        ),
                        Chapter(
                            id = "c1_evs_4",
                            chapterNumber = 4,
                            title = "Animals & Birds Friends",
                            hindiTitle = "पशु और पक्षी मित्र",
                            description = "Domestic and wild animals, birds that fly, and keeping animals safe.",
                            hindiDescription = "घरेलू और जंगली जानवर तथा आसमान में उड़ते पक्षी।",
                            keyTopics = listOf("Domestic Pets (Dog, Cow, Cat)", "Birds with Feathers", "Caring for Animals"),
                            sampleTextbookContent = "Cows give us healthy milk. Dogs guard our houses. Birds have colorful wings and fly in the sky. Sparrows and peacocks are beautiful birds. We must be gentle and give water to birds."
                        )
                    )
                ),
                SubjectSyllabus(
                    subjectName = "Mathematics",
                    iconName = "math",
                    description = "Counting 1 to 50, shapes, and early addition",
                    chapters = listOf(
                        Chapter(
                            id = "c1_math_1",
                            chapterNumber = 1,
                            title = "Numbers 1 to 20 & Counting",
                            hindiTitle = "गिनती 1 से 20",
                            description = "Counting objects, forward and backward counting, and writing numbers.",
                            hindiDescription = "वस्तुओं को गिनना, आगे और पीछे की गिनती।",
                            keyTopics = listOf("Counting with Fingers & Beads", "Writing Digits 1 to 20", "Number Names One to Ten"),
                            sampleTextbookContent = "One little puppy, two gentle kittens, three flying parrots. Counting from 1 to 10 helps us count our pencils, books, and toys."
                        ),
                        Chapter(
                            id = "c1_math_2",
                            chapterNumber = 2,
                            title = "Shapes & Space",
                            hindiTitle = "आकृतियाँ और स्थान",
                            description = "Discovering circles, squares, triangles, big vs small, and inside vs outside.",
                            hindiDescription = "वृत्त, वर्ग, त्रिभुज और बड़ा-छोटा की पहचान।",
                            keyTopics = listOf("Circle, Square, Triangle, Rectangle", "Inside & Outside", "Bigger & Smaller Objects"),
                            sampleTextbookContent = "A round ball is like a circle. A carrom board is a square. An ice-cream cone has a triangular top. Look around your classroom and find circles and rectangles."
                        ),
                        Chapter(
                            id = "c1_math_3",
                            chapterNumber = 3,
                            title = "Simple Addition (Putting Together)",
                            hindiTitle = "जोड़ना (एक साथ मिलाना)",
                            description = "Learning addition by grouping apples, marbles, and everyday items.",
                            hindiDescription = "वस्तुओं को एक साथ मिलाकर जोड़ना।",
                            keyTopics = listOf("Putting Objects Together", "Plus (+) Symbol", "Addition within 10"),
                            sampleTextbookContent = "Rohan has 2 shiny red apples. Meena gives him 3 more juicy apples. Now Rohan has 2 + 3 = 5 apples altogether."
                        )
                    )
                ),
                SubjectSyllabus(
                    subjectName = "English",
                    iconName = "english",
                    description = "Phonics, alphabet fun, and simple rhymes",
                    chapters = listOf(
                        Chapter(
                            id = "c1_eng_1",
                            chapterNumber = 1,
                            title = "Alphabet Sounds & Sight Words",
                            hindiTitle = "वर्णमाला और शुरुआती शब्द",
                            description = "A to Z letter sounds, vowel basics, and recognizing everyday words.",
                            hindiDescription = "A से Z ध्वनियाँ और आसान शब्द।",
                            keyTopics = listOf("Letter Sounds A to Z", "Cat, Bat, Sun, Dog", "Reading Short Sentences"),
                            sampleTextbookContent = "A is for Apple, crisp and red. B is for Ball, bouncing high. The cat sat on the mat. The sun is shining bright in the blue sky."
                        ),
                        Chapter(
                            id = "c1_eng_2",
                            chapterNumber = 2,
                            title = "Action Words & Simple Greetings",
                            hindiTitle = "क्रिया शब्द और अभिवादन",
                            description = "Jump, run, read, smile and polite words like Good Morning, Thank You.",
                            hindiDescription = "दौड़ना, पढ़ना और धन्यवाद व नमस्ते कहना।",
                            keyTopics = listOf("Polite Words (Please, Thank You)", "Action Verbs", "Greetings in Classroom"),
                            sampleTextbookContent = "Always greet your teacher with 'Good morning, teacher'. When a friend shares a pencil, say 'Thank you'. We love to run, play, and read books together."
                        )
                    )
                )
            )
        ),

        // CLASS 2
        ClassSyllabus(
            gradeLevel = 2,
            gradeName = "Class 2",
            ageGroup = "Age 7-8",
            tagline = "Curiosity, Everyday Science & Simple Stories",
            subjects = listOf(
                SubjectSyllabus(
                    subjectName = "Environmental Studies (EVS)",
                    iconName = "science",
                    description = "Seasons, food we eat, community helpers, and safety rules",
                    chapters = listOf(
                        Chapter(
                            id = "c2_evs_1",
                            chapterNumber = 1,
                            title = "Healthy Food & Good Habits",
                            hindiTitle = "पौष्टिक भोजन और अच्छी आदतें",
                            description = "Types of food: energy giving, body building, and protective food like green vegetables.",
                            hindiDescription = "ऊर्जा देने वाला भोजन, फल, सब्जियाँ और स्वस्थ आदतें।",
                            keyTopics = listOf("Energy-giving Food (Rice, Wheat)", "Body-building Food (Milk, Dal)", "Drinking Clean Water"),
                            sampleTextbookContent = "Food gives us energy to play and learn. We must eat fresh fruits, green vegetables, and drink boiled or filtered water. Wash hands before and after every meal."
                        ),
                        Chapter(
                            id = "c2_evs_2",
                            chapterNumber = 2,
                            title = "Community Helpers Around Us",
                            hindiTitle = "हमारे मददगार",
                            description = "Appreciating teachers, doctors, farmers, postmen, and sanitation workers.",
                            hindiDescription = "शिक्षक, डॉक्टर, किसान, और सफाई कर्मी के कार्य।",
                            keyTopics = listOf("Farmers Who Grow Food", "Doctors & Nurses", "Firefighters & Police"),
                            sampleTextbookContent = "Many people help make our village and town safe. The farmer works in fields to grow wheat and vegetables. The doctor helps cure our fever. Respect every helper."
                        ),
                        Chapter(
                            id = "c2_evs_3",
                            chapterNumber = 3,
                            title = "Seasons & Weather Changes",
                            hindiTitle = "ऋतुएँ और मौसम",
                            description = "Summer, Monsoon, Winter, Spring, and clothes we wear in each season.",
                            hindiDescription = "गर्मी, वर्षा, सर्दी और वसंत ऋतु।",
                            keyTopics = listOf("Summer (Cotton Clothes)", "Monsoon (Raincoats & Umbrellas)", "Winter (Woolen Sweaters)"),
                            sampleTextbookContent = "In summer the sun is hot, so we wear cool cotton clothes. In monsoon clouds pour rain, and children jump with umbrellas. In winter we wear warm woolen caps."
                        )
                    )
                ),
                SubjectSyllabus(
                    subjectName = "Mathematics",
                    iconName = "math",
                    description = "Numbers up to 100, subtraction, and measurement",
                    chapters = listOf(
                        Chapter(
                            id = "c2_math_1",
                            chapterNumber = 1,
                            title = "Two-Digit Numbers & Place Value",
                            hindiTitle = "दो अंकों की संख्या और स्थानीय मान",
                            description = "Understanding Tens and Ones, comparing numbers, and counting by 2s, 5s and 10s.",
                            hindiDescription = "दहाई और इकाई की समझ।",
                            keyTopics = listOf("Tens & Ones (e.g. 45 = 4 tens 5 ones)", "Skip Counting", "Greater Than & Less Than"),
                            sampleTextbookContent = "Number 42 has 4 bundles of ten sticks and 2 single sticks. 4 Tens and 2 Ones make forty-two. 58 is greater than 35."
                        ),
                        Chapter(
                            id = "c2_math_2",
                            chapterNumber = 2,
                            title = "Subtraction (Taking Away)",
                            hindiTitle = "घटाना (अलग करना)",
                            description = "Subtracting single and two-digit numbers with practical story sums.",
                            hindiDescription = "घटाने के सरल अभ्यास और सवाल।",
                            keyTopics = listOf("Taking Away from a Group", "Minus (-) Symbol", "Word Problems"),
                            sampleTextbookContent = "A basket has 9 sweet oranges. The children ate 4 oranges during lunch break. How many oranges remain? 9 - 4 = 5 oranges left."
                        ),
                        Chapter(
                            id = "c2_math_3",
                            chapterNumber = 3,
                            title = "Measuring Length, Weight & Time",
                            hindiTitle = "मापन: लंबाई, भार और समय",
                            description = "Measuring using handspan, paces, weighing light vs heavy, and days of the week.",
                            hindiDescription = "हाथ से नापना, हल्का-भारी और सप्ताह के दिन।",
                            keyTopics = listOf("Handspan & Steps", "Heavy vs Light", "7 Days of the Week"),
                            sampleTextbookContent = "Sunday, Monday, Tuesday, Wednesday, Thursday, Friday, Saturday! There are seven days in a week. An elephant is heavy while a feather is very light."
                        )
                    )
                )
            )
        ),

        // CLASS 3
        ClassSyllabus(
            gradeLevel = 3,
            gradeName = "Class 3",
            ageGroup = "Age 8-9",
            tagline = "Observation, Multiplication & World Around Us",
            subjects = listOf(
                SubjectSyllabus(
                    subjectName = "Science / EVS",
                    iconName = "science",
                    description = "Living vs non-living, parts of a plant, water cycle, and birds",
                    chapters = listOf(
                        Chapter(
                            id = "c3_sci_1",
                            chapterNumber = 1,
                            title = "Living & Non-Living Things",
                            hindiTitle = "सजीव और निर्जीव वस्तुएं",
                            description = "Living things breathe, grow, need food, reproduce, and feel changes.",
                            hindiDescription = "सजीव सांस लेते हैं, बढ़ते हैं और भोजन करते हैं।",
                            keyTopics = listOf("Breathing & Nutrition", "Growth in Plants and Animals", "Natural vs Man-made"),
                            sampleTextbookContent = "A puppy grows into a dog. A tiny seedling grows into a towering tree. But a wooden chair or stone cannot grow or breathe. Living things need air, water, and food to survive."
                        ),
                        Chapter(
                            id = "c3_sci_2",
                            chapterNumber = 2,
                            title = "Parts of a Plant & Their Work",
                            hindiTitle = "पौधों के विभिन्न भाग और कार्य",
                            description = "Roots absorb water from soil; green leaves prepare food using sunlight; flowers produce seeds.",
                            hindiDescription = "जड़ें, तना, पत्तियाँ, फूल और फल।",
                            keyTopics = listOf("Tap Roots & Fibrous Roots", "Stem Supports the Plant", "Leaves are Food Factories"),
                            sampleTextbookContent = "The root anchors the plant firmly in the soil and sips water. The stem carries water to all branches. The green leaf is called the kitchen of the plant because it prepares food in sunlight."
                        ),
                        Chapter(
                            id = "c3_sci_3",
                            chapterNumber = 3,
                            title = "Water: The Elixir of Life",
                            hindiTitle = "जल: जीवन का आधार",
                            description = "Sources of freshwater, three forms of water (ice, liquid, vapour), and saving water.",
                            hindiDescription = "जल के स्रोत, तीन अवस्थाएं और जल संरक्षण।",
                            keyTopics = listOf("Rivers, Lakes, Rain & Wells", "Evaporation & Rain", "Conserving Drinking Water"),
                            sampleTextbookContent = "Water has no color, shape, or smell. Heating liquid water turns it into steam or vapour. Cooling water creates solid ice. Always close taps tightly to avoid wasting precious drops."
                        )
                    )
                ),
                SubjectSyllabus(
                    subjectName = "Mathematics",
                    iconName = "math",
                    description = "3-digit numbers, multiplication tables, and fractions introduction",
                    chapters = listOf(
                        Chapter(
                            id = "c3_math_1",
                            chapterNumber = 1,
                            title = "Numbers up to 1000",
                            hindiTitle = "1000 तक की संख्याएँ",
                            description = "Hundreds, Tens, Ones, expanded form, and number line.",
                            hindiDescription = "सैकड़ा, दहाई, इकाई और विस्तारित रूप।",
                            keyTopics = listOf("3-Digit Place Value", "Expanded Form (345 = 300+40+5)", "Ascending & Descending Order"),
                            sampleTextbookContent = "10 tens make one Hundred (100). 10 hundreds make one Thousand (1000). In 782, the place value of 7 is 700, 8 is 80, and 2 is 2."
                        ),
                        Chapter(
                            id = "c3_math_2",
                            chapterNumber = 2,
                            title = "Multiplication as Repeated Addition",
                            hindiTitle = "गुणा (बार-बार जोड़ना)",
                            description = "Tables 2 to 10, multiplying 2-digit numbers, and multiplication grid.",
                            hindiDescription = "पहाड़े (2 से 10) और सरल गुणा।",
                            keyTopics = listOf("Repeated Addition (4 + 4 + 4 = 3 × 4 = 12)", "Tables 2 to 9", "Multiplication Word Problems"),
                            sampleTextbookContent = "If one box holds 6 crayons, how many crayons are in 5 boxes? 5 times 6 is 30 crayons. Multiplication is a fast way of adding the same number again and again."
                        )
                    )
                )
            )
        ),

        // CLASS 4
        ClassSyllabus(
            gradeLevel = 4,
            gradeName = "Class 4",
            ageGroup = "Age 9-10",
            tagline = "Adaptation, Division & Indian Geography Intro",
            subjects = listOf(
                SubjectSyllabus(
                    subjectName = "Science",
                    iconName = "science",
                    description = "Animal adaptations, human digestion, matter states, and simple machines",
                    chapters = listOf(
                        Chapter(
                            id = "c4_sci_1",
                            chapterNumber = 1,
                            title = "Animal Habitats & Adaptations",
                            hindiTitle = "जंतुओं के आवास और अनुकूलन",
                            description = "How animals survive in deserts (camel), cold poles (polar bear), and water (fish).",
                            hindiDescription = "मरुस्थल, ध्रुवीय क्षेत्र और जलीय जीवों का अनुकूलन।",
                            keyTopics = listOf("Desert Adaptations (Hump & Padded Feet)", "Camouflage", "Aquatic Gills and Fins"),
                            sampleTextbookContent = "A camel is called the ship of the desert because it can survive days without water and has padded feet to walk on soft sand. Polar bears have thick fur and blubber fat to stay warm in freezing snow."
                        ),
                        Chapter(
                            id = "c4_sci_2",
                            chapterNumber = 2,
                            title = "The Human Digestive System",
                            hindiTitle = "मानव पाचन तंत्र",
                            description = "The journey of food from mouth, food pipe, stomach, intestines, to energy.",
                            hindiDescription = "मुख, आमाशय, आंत और भोजन का पाचन।",
                            keyTopics = listOf("Chewing & Saliva", "Stomach Acids & Churning", "Small Intestine Absorption"),
                            sampleTextbookContent = "Digestion begins in our mouth. Teeth chew the food, while saliva breaks down starch. The food travels down the esophagus into the stomach, where juices break it down further."
                        ),
                        Chapter(
                            id = "c4_sci_3",
                            chapterNumber = 3,
                            title = "States of Matter & Changes",
                            hindiTitle = "पदार्थ की अवस्थाएं और परिवर्तन",
                            description = "Solids have fixed shape, liquids flow, gases expand. Melting, freezing, boiling.",
                            hindiDescription = "ठोस, द्रव और गैस के गुण।",
                            keyTopics = listOf("Solids, Liquids, Gases", "Melting & Freezing", "Solution: Solute and Solvent"),
                            sampleTextbookContent = "Ice is solid water. When left at room temperature, it melts into liquid water. When heated on a stove, liquid water boils into gaseous steam."
                        )
                    )
                ),
                SubjectSyllabus(
                    subjectName = "Mathematics",
                    iconName = "math",
                    description = "Large numbers up to 10,000, division, perimeter, and fractions",
                    chapters = listOf(
                        Chapter(
                            id = "c4_math_1",
                            chapterNumber = 1,
                            title = "Division & Equal Sharing",
                            hindiTitle = "भाग (समान रूप से बांटना)",
                            description = "Quotient, remainder, divisor, dividing 3-digit numbers by single digit.",
                            hindiDescription = "भागफल, शेषफल और भाजक।",
                            keyTopics = listOf("Equal Sharing Concept", "Long Division Method", "Checking: Divisor × Quotient + Remainder"),
                            sampleTextbookContent = "If 48 notebooks are distributed equally among 6 students, each student gets 48 ÷ 6 = 8 notebooks. Division is inverse of multiplication."
                        ),
                        Chapter(
                            id = "c4_math_2",
                            chapterNumber = 2,
                            title = "Fractions: Halves, Thirds & Fourths",
                            hindiTitle = "भिन्न: आधा, तिहाई और चौथाई",
                            description = "Numerator and denominator, equivalent fractions, shaded area fractions.",
                            hindiDescription = "अंश और हर, समान भिन्न।",
                            keyTopics = listOf("Numerator (top) & Denominator (bottom)", "Like Fractions Addition", "Real-life Pizza & Roti Sharing"),
                            sampleTextbookContent = "When a whole chapati is cut into 4 equal slices, 1 slice is called 1/4 (one-fourth). If you eat 2 slices, you have eaten 2/4 which equals 1/2 of the chapati."
                        )
                    )
                )
            )
        ),

        // CLASS 5
        ClassSyllabus(
            gradeLevel = 5,
            gradeName = "Class 5",
            ageGroup = "Age 10-11",
            tagline = "Ecosystems, Fractions, Decimals & Heritage",
            subjects = listOf(
                SubjectSyllabus(
                    subjectName = "Science",
                    iconName = "science",
                    description = "Food chains, respiratory system, simple machines, and natural resources",
                    chapters = listOf(
                        Chapter(
                            id = "c5_sci_1",
                            chapterNumber = 1,
                            title = "Food Chains & Forests",
                            hindiTitle = "खाद्य श्रृंखला और वन",
                            description = "Producers (plants), consumers (herbivores, carnivores), and decomposers in nature.",
                            hindiDescription = "उत्पादक, उपभोक्ता और अपघटक की कड़ी।",
                            keyTopics = listOf("Sun as Primary Energy Source", "Grass → Deer → Tiger", "Importance of Protecting Forests"),
                            sampleTextbookContent = "Green plants are producers because they produce food through photosynthesis. A deer eats grass, and a lion hunts the deer. This transfer of energy forms a food chain."
                        ),
                        Chapter(
                            id = "c5_sci_2",
                            chapterNumber = 2,
                            title = "Nervous System & Sense Organs",
                            hindiTitle = "तंत्रिका तंत्र और ज्ञानेंद्रियां",
                            description = "Brain, spinal cord, nerves, reflex actions, and brain protection.",
                            hindiDescription = "मस्तिष्क, मेरुरज्जु और तंत्रिकाएं।",
                            keyTopics = listOf("Cerebrum, Cerebellum, Medulla", "Spinal Cord Messages", "Reflex Action (Touching a Hot Pan)"),
                            sampleTextbookContent = "The brain is the master control computer of our body. It receives messages from our eyes, ears, and skin through nerves, decides actions in a split second, and sends orders to muscles."
                        ),
                        Chapter(
                            id = "c5_sci_3",
                            chapterNumber = 3,
                            title = "Simple Machines & Everyday Work",
                            hindiTitle = "सरल मशीनें और दैनिक जीवन",
                            description = "Lever, pulley, wheel and axle, inclined plane, wedge, and screw.",
                            hindiDescription = "उत्तोलक, घिरनी, पहिया और नत समतल।",
                            keyTopics = listOf("Class 1, 2, 3 Levers (Scissors, Bottle Opener)", "Pulley on a Well", "Inclined Plane Ramps"),
                            sampleTextbookContent = "A machine makes our work easier and faster with less force. A ramp at a hospital helps roll a wheelchair smoothly. A pulley on a village well helps draw heavy buckets of water."
                        )
                    )
                ),
                SubjectSyllabus(
                    subjectName = "Mathematics",
                    iconName = "math",
                    description = "Decimals, angles, perimeter & area, factors and multiples (LCM/HCF)",
                    chapters = listOf(
                        Chapter(
                            id = "c5_math_1",
                            chapterNumber = 1,
                            title = "Factors, Multiples & Prime Numbers",
                            hindiTitle = "गुणनखंड, गुणज और अभाज्य संख्याएँ",
                            description = "Finding HCF and LCM, prime vs composite numbers, and divisibility rules.",
                            hindiDescription = "म.स.प (HCF) और ल.स.प (LCM) ज्ञात करना।",
                            keyTopics = listOf("Divisibility by 2, 3, 5, 10", "Prime Numbers (2, 3, 5, 7...)", "Highest Common Factor"),
                            sampleTextbookContent = "A prime number has exactly two factors: 1 and itself (e.g., 2, 3, 5, 7, 11). The Lowest Common Multiple (LCM) of 4 and 6 is 12."
                        ),
                        Chapter(
                            id = "c5_math_2",
                            chapterNumber = 2,
                            title = "Decimals & Money Calculations",
                            hindiTitle = "दशमलव और रुपये-पैसे की गणना",
                            description = "Tenths and hundredths, converting fractions to decimals, adding rupees and paise.",
                            hindiDescription = "दशमलव का जोड़ और घटाव।",
                            keyTopics = listOf("Decimal Point Representation", "Addition and Subtraction of Decimals", "Metric Conversion (meters to cm)"),
                            sampleTextbookContent = "1 rupee = 100 paise. 50 paise is written as ₹0.50. When measuring cloth, 1 meter 75 cm is written as 1.75 meters."
                        ),
                        Chapter(
                            id = "c5_math_3",
                            chapterNumber = 3,
                            title = "Area & Perimeter",
                            hindiTitle = "क्षेत्रफल और परिमाप",
                            description = "Perimeter is boundary length; Area is surface covered in square units.",
                            hindiDescription = "आयत और वर्ग का परिमाप व क्षेत्रफल।",
                            keyTopics = listOf("Perimeter of Rectangle = 2 × (L + B)", "Area of Square = Side × Side", "Real-world Garden Fencing"),
                            sampleTextbookContent = "To put a fence around a rectangular garden measuring 10m by 5m, we calculate the perimeter: 2 × (10 + 5) = 30 meters of wire needed."
                        )
                    )
                )
            )
        ),

        // CLASS 6
        ClassSyllabus(
            gradeLevel = 6,
            gradeName = "Class 6",
            ageGroup = "Age 11-12",
            tagline = "Foundational Physics, Chemistry, Biology & Integers",
            subjects = listOf(
                SubjectSyllabus(
                    subjectName = "Science",
                    iconName = "science",
                    description = "Food components, light shadows, electricity circuits, separation of substances",
                    chapters = listOf(
                        Chapter(
                            id = "c6_sci_1",
                            chapterNumber = 1,
                            title = "Components of Food & Deficiency Diseases",
                            hindiTitle = "भोजन के घटक और अभावजन्य रोग",
                            description = "Carbohydrates, proteins, fats, vitamins, minerals, and diseases like scurvy and rickets.",
                            hindiDescription = "कार्बोहाइड्रेट, प्रोटीन, वसा, विटामिन और संतुलित आहार।",
                            keyTopics = listOf("Starch and Protein Tests", "Vitamin Deficiency (Scurvy, Goitre, Anemia)", "Balanced Diet"),
                            sampleTextbookContent = "Carbohydrates and fats give us energy. Proteins build our muscles. Lack of Vitamin C causes bleeding gums (scurvy). Lack of iodine causes swelling in the neck (goitre)."
                        ),
                        Chapter(
                            id = "c6_sci_2",
                            chapterNumber = 2,
                            title = "Sorting Materials & Separation Techniques",
                            hindiTitle = "पदार्थों का पृथक्करण और समूह बनाना",
                            description = "Filtration, sedimentation, decantation, evaporation, sieving, and handpicking.",
                            hindiDescription = "अवसादन, निस्तारण, निस्यंदन (फिल्ट्रेशन) और वाष्पीकरण।",
                            keyTopics = listOf("Soluble vs Insoluble Substances", "Threshing and Winnowing", "Filtration of Muddy Water"),
                            sampleTextbookContent = "Farmers separate grains from stalks using threshing and remove husk with wind (winnowing). Salt is harvested from sea water using natural sun evaporation in shallow pans."
                        ),
                        Chapter(
                            id = "c6_sci_3",
                            chapterNumber = 3,
                            title = "Light, Shadows & Reflections",
                            hindiTitle = "प्रकाश, छाया एवं परावर्तन",
                            description = "Luminous vs non-luminous objects, transparent, translucent, opaque, and pinhole camera.",
                            hindiDescription = "पारदर्शी, अपारदर्शी, पारभासी और छाया का निर्माण।",
                            keyTopics = listOf("Light Travels in Straight Lines", "Opaque Objects Create Dark Shadows", "Pinhole Camera Inverted Image"),
                            sampleTextbookContent = "Light always travels along a straight path. When an opaque obstacle blocks the path of light, a shadow is formed on the opposite surface. A plane mirror forms a clear reflected image."
                        ),
                        Chapter(
                            id = "c6_sci_4",
                            chapterNumber = 4,
                            title = "Electricity & Circuits",
                            hindiTitle = "विद्युत तथा परिपथ",
                            description = "Electric cells, open vs closed circuits, conductors, insulators, and switches.",
                            hindiDescription = "विद्युत सेल, परिपथ, चालक और विद्युतरोधी।",
                            keyTopics = listOf("Positive & Negative Terminals of Cell", "Complete Circuit for Bulb Glowing", "Conductors (Copper) vs Insulators (Rubber)"),
                            sampleTextbookContent = "Electric current flows when there is an unbroken loop from the positive terminal to the negative terminal of an electric cell. Metals like copper conduct electricity, while plastic coatings keep us safe from electric shocks."
                        )
                    )
                ),
                SubjectSyllabus(
                    subjectName = "Mathematics",
                    iconName = "math",
                    description = "Negative integers, algebra introduction, ratios, and basic geometry",
                    chapters = listOf(
                        Chapter(
                            id = "c6_math_1",
                            chapterNumber = 1,
                            title = "Integers & Number Line",
                            hindiTitle = "पूर्णांक और संख्या रेखा",
                            description = "Positive and negative numbers, zero, addition and subtraction on number line.",
                            hindiDescription = "धनात्मक और ऋणात्मक संख्याएं, संख्या रेखा पर संक्रियाएं।",
                            keyTopics = listOf("Opposites in Real Life (-5°C temperature)", "Number Line Left vs Right", "Rules: (+)(-) and (-)(-)"),
                            sampleTextbookContent = "Numbers to the right of zero on a number line are positive (+1, +2, +3), while numbers to the left are negative (-1, -2, -3). The sum of -4 and +6 is +2."
                        ),
                        Chapter(
                            id = "c6_math_2",
                            chapterNumber = 2,
                            title = "Introduction to Algebra",
                            hindiTitle = "बीजगणित का परिचय",
                            description = "Variables, constants, forming expressions, and solving simple balance equations.",
                            hindiDescription = "चर, अचर और सरल समीकरण।",
                            keyTopics = listOf("Variables (x, y, n) Representing Unknowns", "Algebraic Expressions (2n + 1)", "Solving x + 5 = 12"),
                            sampleTextbookContent = "In algebra, a variable like 'x' stands for an unknown quantity. If a box contains 'x' pens and you add 3 pens to make 10 pens, then x + 3 = 10, meaning x = 7."
                        ),
                        Chapter(
                            id = "c6_math_3",
                            chapterNumber = 3,
                            title = "Ratio & Proportion",
                            hindiTitle = "अनुपात और समानुपात",
                            description = "Comparing quantities by division, simplest form, and the unitary method.",
                            hindiDescription = "अनुपात की तुलना और एकिक नियम।",
                            keyTopics = listOf("Ratio a:b Definition", "Equivalent Ratios", "Unitary Method (Cost of 1 item first)"),
                            sampleTextbookContent = "If 5 mangoes cost ₹50, what is the cost of 8 mangoes? Using the unitary method, 1 mango costs ₹50 ÷ 5 = ₹10. Therefore, 8 mangoes cost 8 × ₹10 = ₹80."
                        )
                    )
                ),
                SubjectSyllabus(
                    subjectName = "Social Science",
                    iconName = "social",
                    description = "Early humans, Indus Valley, Solar System, diversity and democracy",
                    chapters = listOf(
                        Chapter(
                            id = "c6_soc_1",
                            chapterNumber = 1,
                            title = "The Earth in the Solar System",
                            hindiTitle = "सौरमंडल में पृथ्वी",
                            description = "Planets, Sun, Moon, asteroids, and why Earth is a unique blue planet with life.",
                            hindiDescription = "ग्रह, उपग्रह, सूर्य और पृथ्वी की विशेषताएं।",
                            keyTopics = listOf("8 Planets Order", "Earth's Atmosphere & Water", "Phases of the Moon"),
                            sampleTextbookContent = "Earth is the third planet from the Sun and the only known planet that supports life. It has water in liquid form and a protective blanket of air called atmosphere."
                        ),
                        Chapter(
                            id = "c6_soc_2",
                            chapterNumber = 2,
                            title = "From Hunting-Gathering to Growing Food (Indus Valley)",
                            hindiTitle = "आरंभिक मानव और हड़प्पा सभ्यता",
                            description = "Stone age tools, cave paintings at Bhimbetka, Harappa city planning, and Great Bath.",
                            hindiDescription = "भीमबेटका के शैलचित्र, हड़प्पा के सुनियोजित नगर और स्नानागार।",
                            keyTopics = listOf("Paleolithic to Neolithic Tools", "Harappan Brick Architecture & Drainage", "Bronze Age Craftsmanship"),
                            sampleTextbookContent = "Harappa and Mohenjodaro were ancient cities with advanced covered brick drains, grid-pattern streets, and public baths built over 4,500 years ago along the Indus River."
                        )
                    )
                )
            )
        ),

        // CLASS 7
        ClassSyllabus(
            gradeLevel = 7,
            gradeName = "Class 7",
            ageGroup = "Age 12-13",
            tagline = "Nutrition, Heat, Acids/Bases, Equations & Medieval India",
            subjects = listOf(
                SubjectSyllabus(
                    subjectName = "Science",
                    iconName = "science",
                    description = "Plant/animal nutrition, heat transfer, acids bases and salts, respiration",
                    chapters = listOf(
                        Chapter(
                            id = "c7_sci_1",
                            chapterNumber = 1,
                            title = "Nutrition in Plants & Photosynthesis",
                            hindiTitle = "पादपों में पोषण एवं प्रकाश-संश्लेषण",
                            description = "Autotrophic nutrition, chlorophyll, stomata, water and carbon dioxide equation, parasites (Cuscuta).",
                            hindiDescription = "स्वपोषी पोषण, क्लोरोफिल, रंध्र और प्रकाश-संश्लेषण समीकरण।",
                            keyTopics = listOf("Equation: 6CO₂ + 6H₂O + Sunlight → C₆H₁₂O₆ + 6O₂", "Stomata Guard Cells", "Heterotrophic & Insectivorous Plants (Pitcher Plant)"),
                            sampleTextbookContent = "Green leaves contain chlorophyll pigment that traps solar energy. Through microscopic pores called stomata, plants take in carbon dioxide. In the presence of sunlight, they synthesize glucose and release vital oxygen."
                        ),
                        Chapter(
                            id = "c7_sci_2",
                            chapterNumber = 2,
                            title = "Heat, Temperature & Modes of Transfer",
                            hindiTitle = "ऊष्मा, तापमान एवं चालन/संवहन/विकिरण",
                            description = "Conduction in solids, convection in fluids, radiation through vacuum, clinical thermometers.",
                            hindiDescription = "चालन, संवहन, विकिरण और थर्मामीटर का उपयोग।",
                            keyTopics = listOf("Conduction in Metal Spoons", "Sea Breeze & Land Breeze Convection", "Solar Radiation"),
                            sampleTextbookContent = "Heat flows from a hotter body to a colder body. Conduction happens when vibrating particles pass energy without moving. Convection transfers heat through moving air or liquid currents. Radiation requires no medium at all."
                        ),
                        Chapter(
                            id = "c7_sci_3",
                            chapterNumber = 3,
                            title = "Acids, Bases & Salts (Indicators)",
                            hindiTitle = "अम्ल, क्षारक और लवण (सूचक)",
                            description = "Litmus paper, turmeric, phenolphthalein indicators, neutralization reactions, and ant sting cure.",
                            hindiDescription = "लिटमस, हल्दी सूचक, उदासीनीकरण और चींटी का डंक।",
                            keyTopics = listOf("Acids are Sour, Bases are Bitter and Soapy", "Litmus: Acid turns Blue Litmus Red", "Neutralization: Acid + Base → Salt + Water"),
                            sampleTextbookContent = "Lemon juice and vinegar contain acids. Baking soda and soap contain bases. An ant's sting injects formic acid, which can be neutralized by gently rubbing moist baking soda or calamine solution."
                        ),
                        Chapter(
                            id = "c7_sci_4",
                            chapterNumber = 4,
                            title = "Respiration in Organisms",
                            hindiTitle = "जीवों में श्वसन",
                            description = "Aerobic vs anaerobic respiration, human lungs, diaphragm movement, and breathing in fish.",
                            hindiDescription = "वायवीय और अवायवीय श्वसन, फेफड़े और डायफ्राम।",
                            keyTopics = listOf("Aerobic: Glucose + O₂ → CO₂ + H₂O + Energy", "Anaerobic in Yeast (Ethanol)", "Diaphragm Moves Down on Inhalation"),
                            sampleTextbookContent = "Respiration is the biochemical breakdown of food to release energy inside cells. When you inhale, ribs move up and outwards while the diaphragm moves down, expanding chest cavity volume so air rushes into the lungs."
                        )
                    )
                ),
                SubjectSyllabus(
                    subjectName = "Mathematics",
                    iconName = "math",
                    description = "Rational numbers, simple equations, lines and angles, triangles",
                    chapters = listOf(
                        Chapter(
                            id = "c7_math_1",
                            chapterNumber = 1,
                            title = "Fractions & Decimals Operations",
                            hindiTitle = "भिन्न एवं दशमलव की संक्रियाएं",
                            description = "Multiplication and division of fractions, reciprocating fractions, decimal shifts.",
                            hindiDescription = "भिन्नों का गुणा और भाग, दशमलव का गुणन।",
                            keyTopics = listOf("Multiplying Fractions (Numerator × Numerator)", "Division by Reciprocal", "Decimal Multiplication"),
                            sampleTextbookContent = "To divide 3/4 by 2/3, multiply 3/4 by the reciprocal 3/2: (3/4) × (3/2) = 9/8. Multiplying a decimal by 10 shifts the decimal point one place to the right."
                        ),
                        Chapter(
                            id = "c7_math_2",
                            chapterNumber = 2,
                            title = "Simple Linear Equations",
                            hindiTitle = "सरल समीकरण",
                            description = "Transposition method, balancing equations, and solving age/number word puzzles.",
                            hindiDescription = "पक्षान्तरण विधि और व्यावहारिक समीकरण।",
                            keyTopics = listOf("Transposition of Terms (+ becomes -)", "Solving 3x + 7 = 22", "Formulating Word Problems"),
                            sampleTextbookContent = "In 4x - 5 = 15, we transpose -5 to the right hand side as +5: 4x = 20. Then divide both sides by 4 to find x = 5. Always verify your solution by substituting it back into the original equation."
                        ),
                        Chapter(
                            id = "c7_math_3",
                            chapterNumber = 3,
                            title = "Lines, Angles & Triangle Properties",
                            hindiTitle = "रेखाएँ, कोण और त्रिभुज के गुण",
                            description = "Complementary/supplementary angles, alternate interior angles, angle sum property = 180°.",
                            hindiDescription = "पूरक, संपूरक कोण और त्रिभुज के तीनों कोणों का योग = 180°।",
                            keyTopics = listOf("Complementary (sum 90°), Supplementary (sum 180°)", "Parallel Lines Cut by Transversal", "Pythagoras Theorem for Right Triangles"),
                            sampleTextbookContent = "The sum of all three interior angles of any triangle is always exactly 180 degrees. In a right-angled triangle, the square of the hypotenuse equals the sum of the squares of the other two sides: a² + b² = c²."
                        )
                    )
                )
            )
        ),

        // CLASS 8
        ClassSyllabus(
            gradeLevel = 8,
            gradeName = "Class 8",
            ageGroup = "Age 13-14",
            tagline = "Cell Biology, Force & Pressure, Linear Equations & Modern History",
            subjects = listOf(
                SubjectSyllabus(
                    subjectName = "Science",
                    iconName = "science",
                    description = "Cell structure, force and pressure, combustion and flame, microorganisms",
                    chapters = listOf(
                        Chapter(
                            id = "c8_sci_1",
                            chapterNumber = 1,
                            title = "Cell: Structure & Functions",
                            hindiTitle = "कोशिका — संरचना एवं प्रकार्य",
                            description = "Plant vs animal cells, nucleus, cell membrane, cell wall, mitochondria, and microscope viewing.",
                            hindiDescription = "पादप और जंतु कोशिका, केंद्रक, कोशिका भित्ति और माइटोकॉन्ड्रिया।",
                            keyTopics = listOf("Cell Wall in Plant Cells Only", "Nucleus Holds Chromosomes & DNA", "Mitochondria as Powerhouse of the Cell"),
                            sampleTextbookContent = "The cell is the basic structural and functional unit of life. Plant cells have a rigid outer cell wall and chloroplasts for photosynthesis, while animal cells only have a flexible cell membrane. The nucleus directs cellular activities."
                        ),
                        Chapter(
                            id = "c8_sci_2",
                            chapterNumber = 2,
                            title = "Force, Pressure & Friction",
                            hindiTitle = "बल, दाब एवं घर्षण",
                            description = "Contact vs non-contact forces, Pressure = Force / Area, atmospheric pressure, reducing friction.",
                            hindiDescription = "संपर्क व असंपर्क बल, दाब = बल / क्षेत्रफल और घर्षण।",
                            keyTopics = listOf("Gravitational & Electrostatic Forces", "Pressure Increases with Smaller Contact Area", "Friction as a Necessary Evil (Lubricants & Ball Bearings)"),
                            sampleTextbookContent = "Pressure is force acting per unit area: P = F / A. A sharp knife cuts vegetables easily because its tiny edge concentrates force into huge pressure. Friction allows us to walk without slipping, but causes machine wear."
                        ),
                        Chapter(
                            id = "c8_sci_3",
                            chapterNumber = 3,
                            title = "Microorganisms: Friend & Foe",
                            hindiTitle = "सूक्ष्मजीव: मित्र एवं शत्रु",
                            description = "Bacteria, fungi, protozoa, viruses, antibiotics, vaccines, pasteurization, and nitrogen cycle.",
                            hindiDescription = "जीवाणु, कवक, प्रोटोजोआ, वायरस, एंटीबायोटिक्स और नाइट्रोजन चक्र।",
                            keyTopics = listOf("Lactobacillus in Curd Making", "Alexander Fleming & Penicillin", "Rhizobium Nitrogen Fixation in Legumes"),
                            sampleTextbookContent = "Lactobacillus bacteria promote the formation of curd from milk. Yeast causes fermentation to make bread fluffy. However, pathogenic microbes cause diseases like cholera, tuberculosis, and malaria. Vaccines train our immune system."
                        ),
                        Chapter(
                            id = "c8_sci_4",
                            chapterNumber = 4,
                            title = "Sound: Vibration, Frequency & Amplitude",
                            hindiTitle = "ध्वनि: कंपन, आवृत्ति और आयाम",
                            description = "Sound produced by vibrating bodies, human vocal cords, loudness (amplitude), pitch (frequency).",
                            hindiDescription = "ध्वनि का उत्पादन, स्वरयंत्र, आयाम और आवृत्ति।",
                            keyTopics = listOf("Vibrating Membranes & Strings", "Pitch Depends on Frequency (Hertz)", "Audible Range: 20 Hz to 20,000 Hz"),
                            sampleTextbookContent = "Sound requires a material medium (solid, liquid, or gas) to travel and cannot propagate through a vacuum. Frequency determines pitch (high pitch whistle vs deep roar). The human ear detects vibrations between 20 Hz and 20,000 Hz."
                        )
                    )
                ),
                SubjectSyllabus(
                    subjectName = "Mathematics",
                    iconName = "math",
                    description = "Linear equations in one variable, squares & cubes, algebraic identities, mensuration",
                    chapters = listOf(
                        Chapter(
                            id = "c8_math_1",
                            chapterNumber = 1,
                            title = "Squares, Square Roots & Cube Roots",
                            hindiTitle = "वर्ग, वर्गमूल और घनमूल",
                            description = "Properties of square numbers, prime factorization, long division method for square roots.",
                            hindiDescription = "अभाज्य गुणनखंड और भाग विधि से वर्गमूल।",
                            keyTopics = listOf("Square of Numbers (15² = 225)", "Prime Factorization Method", "Pythagorean Triplets (3, 4, 5)"),
                            sampleTextbookContent = "The square root of a number is that value which, when multiplied by itself, yields the original number. For example, √144 = 12. Cube roots involve finding a number that multiplies three times: ∛125 = 5."
                        ),
                        Chapter(
                            id = "c8_math_2",
                            chapterNumber = 2,
                            title = "Algebraic Expressions & Standard Identities",
                            hindiTitle = "बीजीय व्यंजक और सर्वसमिकाएँ",
                            description = "Monomials, binomials, polynomials, (a+b)², (a-b)², and (a+b)(a-b) = a² - b².",
                            hindiDescription = "(a+b)², (a-b)² और a²-b² सर्वसमिकाएँ।",
                            keyTopics = listOf("Identity 1: (a + b)² = a² + 2ab + b²", "Identity 2: (a - b)² = a² - 2ab + b²", "Identity 3: (a + b)(a - b) = a² - b²"),
                            sampleTextbookContent = "Standard identities are equalities that hold true for any values substituted for variables. Expanding (2x + 3y)² using Identity 1 gives (2x)² + 2(2x)(3y) + (3y)² = 4x² + 12xy + 9y²."
                        ),
                        Chapter(
                            id = "c8_math_3",
                            chapterNumber = 3,
                            title = "Mensuration: Surface Area & Volume",
                            hindiTitle = "क्षेत्रमिति: पृष्ठीय क्षेत्रफल और आयतन",
                            description = "Area of trapezium and general quadrilaterals, surface area and volume of cube, cuboid, and cylinder.",
                            hindiDescription = "समलंब, घनाभ, घन और बेलन का क्षेत्रफल व आयतन।",
                            keyTopics = listOf("Area of Trapezium = 1/2 × (a + b) × h", "Volume of Cylinder = πr²h", "Surface Area of Cuboid = 2(lb + bh + hl)"),
                            sampleTextbookContent = "The volume of a cylindrical water tank with radius 7m and height 10m is V = πr²h = (22/7) × 7 × 7 × 10 = 1540 cubic meters. One cubic meter can hold 1,000 liters of water."
                        )
                    )
                )
            )
        ),

        // CLASS 9
        ClassSyllabus(
            gradeLevel = 9,
            gradeName = "Class 9",
            ageGroup = "Age 14-15",
            tagline = "Newton's Laws, Atomic Structure, Tissues, Coordinate Geometry",
            subjects = listOf(
                SubjectSyllabus(
                    subjectName = "Science (Physics, Chemistry, Biology)",
                    iconName = "science",
                    description = "Motion, forces, atoms and molecules, tissues, gravitation, work and energy",
                    chapters = listOf(
                        Chapter(
                            id = "c9_sci_1",
                            chapterNumber = 1,
                            title = "Motion: Velocity, Acceleration & Equations",
                            hindiTitle = "गति: वेग, त्वरण एवं गति के समीकरण",
                            description = "Distance vs displacement, uniform motion, speed, velocity, three equations of motion.",
                            hindiDescription = "दूरी, विस्थापन, वेग, त्वरण और गति के तीन समीकरण।",
                            keyTopics = listOf("v = u + at", "s = ut + 1/2 at²", "v² = u² + 2as", "Distance-Time and Velocity-Time Graphs"),
                            sampleTextbookContent = "Displacement is the shortest straight-line distance from initial to final position with direction. Acceleration is rate of change of velocity: a = (v - u) / t. The area under a velocity-time graph represents the displacement."
                        ),
                        Chapter(
                            id = "c9_sci_2",
                            chapterNumber = 2,
                            title = "Force & Newton's Three Laws of Motion",
                            hindiTitle = "बल तथा न्यूटन के गति के तीन नियम",
                            description = "Inertia, momentum (p = mv), F = ma, action and reaction pairs, conservation of momentum.",
                            hindiDescription = "जड़त्व, संवेग, F = ma, क्रिया-प्रतिक्रिया और संवेग संरक्षण।",
                            keyTopics = listOf("First Law: Inertia (Mass is Measure of Inertia)", "Second Law: Force = Mass × Acceleration", "Third Law: Action & Reaction are Equal and Opposite"),
                            sampleTextbookContent = "Newton's First Law states an object remains at rest unless acted upon by an external net force. The Second Law defines F = ma. The Third Law explains rocket propulsion: hot gases expelled downwards push the rocket upward."
                        ),
                        Chapter(
                            id = "c9_sci_3",
                            chapterNumber = 3,
                            title = "Atoms, Molecules & Chemical Formulae",
                            hindiTitle = "परमाणु, अणु और रासायनिक सूत्र",
                            description = "Law of conservation of mass, Dalton's atomic theory, atomic mass, valency, writing formulae.",
                            hindiDescription = "डाल्टन का परमाणु सिद्धांत, संयोजकता और सूत्र लेखन।",
                            keyTopics = listOf("Law of Constant Proportions", "Criss-cross Valency Method (Al³⁺ and O²⁻ → Al₂O₃)", "Mole Concept Introduction"),
                            sampleTextbookContent = "An atom is the smallest indivisible unit of an element participating in chemical reactions. Using the criss-cross method, combining Calcium (valency 2) and Chlorine (valency 1) yields the neutral formula CaCl₂."
                        ),
                        Chapter(
                            id = "c9_sci_4",
                            chapterNumber = 4,
                            title = "Plant & Animal Tissues",
                            hindiTitle = "पादप एवं जंतु ऊतक",
                            description = "Meristematic vs permanent plant tissues (xylem, phloem), epithelial, connective, muscular, nervous.",
                            hindiDescription = "विभज्योतक, स्थायी ऊतक (जाइलम/फ्लोएम) और पेशीय ऊतक।",
                            keyTopics = listOf("Xylem Conducts Water, Phloem Transports Food", "Blood as Fluid Connective Tissue", "Neuron Structure with Axon and Dendrites"),
                            sampleTextbookContent = "A tissue is a group of similar cells working together to perform a specialized function. In plants, Xylem transports water upwards from roots while Phloem distributes sugars made in leaves to all organs."
                        )
                    )
                ),
                SubjectSyllabus(
                    subjectName = "Mathematics",
                    iconName = "math",
                    description = "Number systems (irrational), polynomials, coordinate geometry, Euclid, Heron's formula",
                    chapters = listOf(
                        Chapter(
                            id = "c9_math_1",
                            chapterNumber = 1,
                            title = "Number Systems & Irrational Numbers",
                            hindiTitle = "संख्या पद्धति और अपरिमेय संख्याएँ",
                            description = "Proving √2 is irrational, real numbers on number line, laws of exponents, rationalizing denominator.",
                            hindiDescription = "अपरिमेय संख्याएं और हर का परिमेयकरण।",
                            keyTopics = listOf("Rational (p/q) vs Irrational (non-terminating non-repeating)", "Rationalizing 1 / (√5 + √2)", "Laws of Indices"),
                            sampleTextbookContent = "Irrational numbers cannot be expressed in the form p/q where p and q are integers and q ≠ 0. Their decimal expansion is non-terminating and non-recurring. To rationalize 1/(√3 - 1), multiply numerator and denominator by (√3 + 1)."
                        ),
                        Chapter(
                            id = "c9_math_2",
                            chapterNumber = 2,
                            title = "Polynomials: Remainder & Factor Theorems",
                            hindiTitle = "बहुपद: शेषफल एवं गुणनखंड प्रमेय",
                            description = "Degree of polynomial, zeroes, splitting middle term, cubic factorization, algebraic identities.",
                            hindiDescription = "शून्यक, मध्य पद विभाजन और गुणनखंड प्रमेय।",
                            keyTopics = listOf("Zeroes of a Polynomial p(x) = 0", "Factor Theorem: if p(a)=0 then (x - a) is a factor", "Splitting Middle Term of Quadratic"),
                            sampleTextbookContent = "To factorize x² - 5x + 6, we find two numbers whose sum is -5 and product is +6 (-2 and -3). Thus x² - 5x + 6 = (x - 2)(x - 3). The zeroes of this polynomial are x = 2 and x = 3."
                        ),
                        Chapter(
                            id = "c9_math_3",
                            chapterNumber = 3,
                            title = "Heron's Formula & Surface Area",
                            hindiTitle = "हीरोन का सूत्र और पृष्ठीय क्षेत्रफल",
                            description = "Area of scalene triangles using s = (a+b+c)/2, Area = √[s(s-a)(s-b)(s-c)], spheres and cones.",
                            hindiDescription = "हीरोन सूत्र से त्रिभुज का क्षेत्रफल।",
                            keyTopics = listOf("Semi-perimeter s = (a + b + c)/2", "Formula: Area = √[s(s - a)(s - b)(s - c)]", "Curved Surface Area of Cone = πrl"),
                            sampleTextbookContent = "When the height of a triangle is unknown but all three sides a, b, c are known, Heron's formula calculates the exact area without perpendiculars: Area = √[s(s-a)(s-b)(s-c)], where s is the semi-perimeter."
                        )
                    )
                )
            )
        ),

        // CLASS 10 (Board Exam Class)
        ClassSyllabus(
            gradeLevel = 10,
            gradeName = "Class 10",
            ageGroup = "Age 15-16",
            tagline = "Board Exam Curriculum: Optics, Electricity, Life Processes, Trigonometry & Quadratic",
            subjects = listOf(
                SubjectSyllabus(
                    subjectName = "Science (Physics, Chemistry, Biology)",
                    iconName = "science",
                    description = "Chemical reactions, life processes, light reflection & refraction, electricity, heredity",
                    chapters = listOf(
                        Chapter(
                            id = "c10_sci_1",
                            chapterNumber = 1,
                            title = "Chemical Reactions & Equations",
                            hindiTitle = "रासायनिक अभिक्रियाएं एवं समीकरण",
                            description = "Balancing chemical equations, combination, decomposition, displacement, double displacement, redox, and corrosion.",
                            hindiDescription = "समीकरण संतुलन, संयोजन, वियोजन, विस्थापन और उपचयन-अपचयन।",
                            keyTopics = listOf("Balancing Law of Conservation of Mass", "Displacement: Fe + CuSO₄ → FeSO₄ + Cu", "Redox: Oxidation and Reduction in terms of Oxygen/Electrons"),
                            sampleTextbookContent = "A chemical equation must be balanced because atoms can neither be created nor destroyed in a reaction. When iron nails are placed in blue copper sulfate solution, iron displaces copper, turning the liquid pale green and depositing brown copper."
                        ),
                        Chapter(
                            id = "c10_sci_2",
                            chapterNumber = 2,
                            title = "Life Processes (Nutrition, Circulation, Excretion)",
                            hindiTitle = "जैव प्रक्रम (पोषण, श्वसन, वहन, उत्सर्जन)",
                            description = "Detailed human digestive tract, double circulation in heart, nephron structure in kidneys.",
                            hindiDescription = "मानव हृदय, दोहरा परिसंचरण और वृक्क (नेफ्रॉन) में उत्सर्जन।",
                            keyTopics = listOf("Four Chambers of Human Heart & Double Circulation", "Structure and Function of Nephron", "Stomata Mechanism and Guard Cell Osmosis"),
                            sampleTextbookContent = "In humans, blood goes through the heart twice during each cycle (Double Circulation). Oxygenated blood from lungs reaches left atrium, is pumped to body via aorta, and deoxygenated blood returns to right side. Nephrons filter urea from blood."
                        ),
                        Chapter(
                            id = "c10_sci_3",
                            chapterNumber = 3,
                            title = "Light: Reflection & Refraction (Lens & Mirror Formulae)",
                            hindiTitle = "प्रकाश: परावर्तन तथा अपवर्तन (दर्पण व लेंस सूत्र)",
                            description = "Concave and convex mirrors/lenses, ray diagrams, Mirror formula: 1/f = 1/v + 1/u, Snell's law, refractive index.",
                            hindiDescription = "अवतल/उत्तल दर्पण एवं लेंस, किरण आरेख और स्नेल का नियम।",
                            keyTopics = listOf("Mirror Formula: 1/f = 1/v + 1/u", "Lens Formula: 1/f = 1/v - 1/u", "Snell's Law: n = sin i / sin r", "Power of Lens P = 1/f (in meters, Diopters)"),
                            sampleTextbookContent = "Concave mirrors converge light and can form real, inverted images as well as magnified virtual images (used by dentists). When light passes from rarer air to denser glass, it bends towards the normal. Power of a lens is measured in Diopters (D)."
                        ),
                        Chapter(
                            id = "c10_sci_4",
                            chapterNumber = 4,
                            title = "Electricity & Ohm's Law",
                            hindiTitle = "विद्युत एवं ओम का नियम",
                            description = "Electric current, potential difference (V), Ohm's law (V = IR), factors affecting resistance, series vs parallel circuits, Joule heating.",
                            hindiDescription = "V = IR, श्रेणीक्रम एवं पार्श्वक्रम परिपथ, जूल का तापन नियम।",
                            keyTopics = listOf("Ohm's Law: V = I × R", "Resistance Factors: R = ρ(L / A)", "Series (R_total = R₁ + R₂) vs Parallel (1/R_total = 1/R₁ + 1/R₂)", "Joule's Heating: H = I²Rt"),
                            sampleTextbookContent = "Ohm's Law states that electric current flowing through a metallic conductor is directly proportional to potential difference across its ends, provided temperature remains constant: V = IR. In homes, appliances are connected in parallel so each receives full 220V voltage."
                        ),
                        Chapter(
                            id = "c10_sci_5",
                            chapterNumber = 5,
                            title = "Carbon & Its Compounds",
                            hindiTitle = "कार्बन एवं उसके यौगिक",
                            description = "Covalent bonding, tetravalency, catenation, homologous series, functional groups, ethanol, and ethanoic acid.",
                            hindiDescription = "सहसंयोजी आबंध, समजातीय श्रेणी और एथेनॉल-एथेनोइक अम्ल।",
                            keyTopics = listOf("Tetravalency & Catenation Ability", "Alkanes (C_nH_{2n+2}), Alkenes, Alkynes", "Functional Groups (-OH, -CHO, -COOH)", "Saponification Reaction for Soap"),
                            sampleTextbookContent = "Carbon forms an enormous number of compounds due to its ability to link with other carbon atoms (catenation) and its tetravalency (valency 4). Soap molecules have a hydrophilic ionic head that loves water and a hydrophobic carbon tail that traps oil droplets into micelles."
                        )
                    )
                ),
                SubjectSyllabus(
                    subjectName = "Mathematics",
                    iconName = "math",
                    description = "Real numbers, quadratic equations, arithmetic progressions, trigonometry, statistics",
                    chapters = listOf(
                        Chapter(
                            id = "c10_math_1",
                            chapterNumber = 1,
                            title = "Quadratic Equations",
                            hindiTitle = "द्विघात समीकरण",
                            description = "Standard form ax² + bx + c = 0, discriminant D = b² - 4ac, nature of roots, quadratic formula.",
                            hindiDescription = "ax² + bx + c = 0, विविक्तकर D = b² - 4ac और मूलों की प्रकृति।",
                            keyTopics = listOf("Discriminant D = b² - 4ac", "Roots: x = [-b ± √(b² - 4ac)] / (2a)", "Real and Distinct Roots (D > 0), Equal Roots (D = 0)"),
                            sampleTextbookContent = "For any quadratic equation ax² + bx + c = 0, the discriminant D = b² - 4ac reveals the nature of roots. If D > 0, there are two distinct real roots. If D = 0, the roots are real and equal. If D < 0, there are no real roots."
                        ),
                        Chapter(
                            id = "c10_math_2",
                            chapterNumber = 2,
                            title = "Arithmetic Progressions (AP)",
                            hindiTitle = "समान्तर श्रेढ़ी (AP)",
                            description = "First term (a), common difference (d), nth term formula a_n = a + (n-1)d, sum of n terms.",
                            hindiDescription = "n वां पद a_n = a + (n-1)d और n पदों का योग S_n।",
                            keyTopics = listOf("Common Difference d = a₂ - a₁", "General Term: a_n = a + (n - 1)d", "Sum Formula: S_n = n/2 × [2a + (n - 1)d]"),
                            sampleTextbookContent = "An Arithmetic Progression is a sequence where each term is obtained by adding a fixed constant 'd' to the preceding term. For example, 2, 7, 12, 17... has a = 2 and d = 5. The 20th term is a₂₀ = 2 + (20 - 1) × 5 = 2 + 95 = 97."
                        ),
                        Chapter(
                            id = "c10_math_3",
                            chapterNumber = 3,
                            title = "Introduction to Trigonometry & Heights and Distances",
                            hindiTitle = "त्रिकोणमिति का परिचय एवं ऊंचाई और दूरी",
                            description = "Trigonometric ratios (sin, cos, tan), specific angles (0°, 30°, 45°, 60°, 90°), sin²θ + cos²θ = 1, angle of elevation.",
                            hindiDescription = "sin, cos, tan अनुपात, सर्वसमिकाएँ और उन्नयन/अवनमन कोण।",
                            keyTopics = listOf("sin = Opp/Hyp, cos = Adj/Hyp, tan = Opp/Adj", "Identity: sin²θ + cos²θ = 1", "Angle of Elevation & Depression", "tan 45° = 1, sin 30° = 1/2"),
                            sampleTextbookContent = "Trigonometry studies relationships between side lengths and angles of triangles. From a distance of 30 meters from the base of a tower, if the angle of elevation to the top is 45°, then tan 45° = Height / 30. Since tan 45° = 1, the tower height is 30 meters."
                        ),
                        Chapter(
                            id = "c10_math_4",
                            chapterNumber = 4,
                            title = "Statistics & Probability",
                            hindiTitle = "सांख्यिकी एवं प्रायिकता",
                            description = "Mean of grouped data (direct and assumed mean method), median, mode, empirical relationship: 3 Median = Mode + 2 Mean.",
                            hindiDescription = "समांतर माध्य, माध्यक, बहुलक और प्रायिकता।",
                            keyTopics = listOf("Mean = Σ(f_i × x_i) / Σf_i", "Mode of Grouped Data", "Median Class Formula", "Probability P(E) = Favourable / Total"),
                            sampleTextbookContent = "The empirical relationship connecting the three central tendencies is: 3 Median = Mode + 2 Mean. The probability of an event always lies between 0 (impossible) and 1 (certain)."
                        )
                    )
                )
            )
        )
    )

    fun getSyllabusForGrade(gradeLevel: Int): ClassSyllabus? {
        return allClassesSyllabus.firstOrNull { it.gradeLevel == gradeLevel }
    }
}
