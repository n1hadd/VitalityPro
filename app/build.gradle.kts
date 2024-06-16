plugins {
    alias(libs.plugins.androidApplication)
}

android {
    namespace = "com.example.vitalitypro"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.vitalitypro"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        buildConfigField("String", "FOOD_DATA_API_KEY", "\"sRPk5sopXGK9QYdQ3rGlBX2uZmAm9jIWMFnYbboq\"")



    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildFeatures{
        viewBinding = true
        android.buildFeatures.buildConfig = true
    }
}

dependencies {
    implementation ("com.github.bumptech.glide:glide:4.16.0")
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation ("com.opencsv:opencsv:5.5.2")
    implementation("androidx.fragment:fragment:1.6.2")
    implementation ("nl.bryanderidder:themed-toggle-button-group:1.4.1")
    implementation ("com.github.PhilJay:MPAndroidChart:v3.1.0")
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation ("com.github.bumptech.glide:glide:4.12.0")
    annotationProcessor ("com.github.bumptech.glide:compiler:4.12.0")
    implementation ("com.jakewharton.threetenabp:threetenabp:1.3.1")


}