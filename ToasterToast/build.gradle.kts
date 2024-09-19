
plugins {
        id("com.android.library")
        id("org.jetbrains.kotlin.android")

}

android {
        namespace = "com.example.toastertoast"
        compileSdk = 34

        defaultConfig {
                minSdk = 24

                testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                consumerProguardFiles("consumer-rules.pro")
        }

        buildTypes {
                release {
                        isMinifyEnabled = false
                        proguardFiles(
                                getDefaultProguardFile("proguard-android-optimize.txt"),
                                "proguard-rules.pro"
                        )
                }
        }
        compileOptions {
                sourceCompatibility = JavaVersion.VERSION_1_8
                targetCompatibility = JavaVersion.VERSION_1_8
        }
        kotlinOptions {
                jvmTarget = "1.8"
        }
        group = "com.github.AjayChauhanMobillor2023"  // Use your GitHub username
        version = "1.0.0"                  // Version of your library

}

dependencies {

        implementation("androidx.core:core-ktx:1.13.1")
        implementation("androidx.appcompat:appcompat:1.7.0")
        testImplementation("junit:junit:4.13.2")
        androidTestImplementation("androidx.test.ext:junit:1.2.1")
        androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
        implementation ("com.google.android.material:material:1.12.0")
}


//afterEvaluate {
//        publishing {
//                publications {
//                        // Creates a Maven publication called "release".
//                        release(MavenPublication) {
//                                from components.release
//                                        groupId ="com.github.AjayChauhanMobillor2023" // GitHub username
//                                artifactId = "Toaster-Toast "      // GitHub repository name
//                                version = "1.0.0"
//                        }
//                }
//        }
//}

