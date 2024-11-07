plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.google.gms.google.services)
}

android {
    namespace = "com.example.quizapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.quizapp"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
}

dependencies {
    // Core Libraries
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)

    // Firebase BOM for automatic version management
    implementation(platform(libs.firebase.bom)) // Use the latest BOM version

    // Firebase Dependencies
    implementation(libs.firebase.auth)
    implementation(libs.firebase.firestore)
    implementation(libs.google.firebase.database)
    implementation(libs.firebase.storage)
    implementation(libs.google.firebase.firestore)

    // Testing Dependencies
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    // Image Loading Library
    implementation(libs.glide)

    // Glide Annotation Processor
    annotationProcessor(libs.compiler) // For Glide
}
