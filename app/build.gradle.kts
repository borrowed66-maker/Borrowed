plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("com.google.devtools.ksp")
    id("org.jetbrains.kotlin.plugin.serialization")
}

android {
    namespace = "com.example.borrowed"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.borrowed"
        minSdk = 26
        targetSdk = 36
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
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)

    // --- added for ViewModels in Compose ---
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.7")

    // --- added: extra Material icons (used by status icons, filter chips, etc) ---
    implementation("androidx.compose.material:material-icons-extended")

    // --- added: Navigation between screens ---
    implementation("androidx.navigation:navigation-compose:2.8.5")

    // --- Material Components (needed for XML Theme.MaterialComponents base theme) ---
    implementation("com.google.android.material:material:1.12.0")

    // --- added: Supabase (Auth + Postgrest + Realtime) via BOM ---
    implementation(platform("io.github.jan-tennert.supabase:bom:3.0.1"))
    implementation("io.github.jan-tennert.supabase:postgrest-kt")
    implementation("io.github.jan-tennert.supabase:auth-kt")
    implementation("io.github.jan-tennert.supabase:realtime-kt")
    // Ktor engine (must match Ktor version used by supabase-kt 3.0.1 = Ktor 3.0.x)
    implementation("io.ktor:ktor-client-android:3.0.1")

    // --- added: Room (local database) ---
    implementation("androidx.room:room-runtime:2.6.1")
    implementation("androidx.room:room-ktx:2.6.1")
    ksp("androidx.room:room-compiler:2.6.1")

    // --- added: Serialization (needed by Supabase DTOs) ---
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.7.3")

    // --- added: Coroutines ---
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.9.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.9.0")

    // --- added: DataStore (for Settings screen prefs) ---
    implementation("androidx.datastore:datastore-preferences:1.1.1")

    // --- added: Coil (image loading) ---
    implementation("io.coil-kt.coil3:coil-compose:3.0.4")

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}