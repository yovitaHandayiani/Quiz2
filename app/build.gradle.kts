plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("kotlin-parcelize")
}

android {
    namespace = "quiz.navigation"
    compileSdk = 35

    defaultConfig {
        applicationId = "quiz.navigation"
        minSdk = 30
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
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures{
        viewBinding = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation("com.google.android.material:material:1.6.0") // For TabLayout and ViewPager2
    implementation("androidx.viewpager2:viewpager2:1.0.0")  // For ViewPager2

    // For MotionLayout
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    // For Physics-based animations
    implementation("androidx.dynamicanimation:dynamicanimation:1.0.0")

    // For AnimatedVectorDrawable
    implementation("androidx.vectordrawable:vectordrawable-animated:1.1.0")

    //Lottie
    implementation("com.airbnb.android:lottie:3.4.0")

    //Rive
    implementation("app.rive:rive-android:9.6.5")
    // During initialization, you may need to add a dependency
    // for Jetpack Startup
    implementation("androidx.startup:startup-runtime:1.1.1")
}