plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.a91map"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.a91map"
        minSdk = 24
        targetSdk = 35
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {

        implementation(libs.appcompat)
        implementation(libs.material)
        implementation(libs.activity)
        implementation(libs.constraintlayout)

        implementation("com.google.android.gms:play-services-maps:18.2.0")       // Google Maps
        implementation("com.google.android.gms:play-services-location:21.0.1")   // Location
        implementation("com.google.android.libraries.places:places:3.3.0")       // Places API

        testImplementation(libs.junit)
        androidTestImplementation(libs.ext.junit)
        androidTestImplementation(libs.espresso.core)




}