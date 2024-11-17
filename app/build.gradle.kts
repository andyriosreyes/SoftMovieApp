plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.jetbrains.kotlin.kapt)
    alias(libs.plugins.dagger.hilt.android)
    id ("kotlin-parcelize")
    alias(libs.plugins.safeargs.kotlin)
}

android {
    namespace = "com.dino.ander.movieapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.dino.ander.movieapp"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        multiDexEnabled = true
        vectorDrawables.useSupportLibrary = true
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        testInstrumentationRunner = "com.dino.ander.movieapp.CustomTestRunner"
    }

    buildTypes {
        debug {
            isMinifyEnabled = false
            isDebuggable = true
        }
        release {
            isMinifyEnabled = true
            isDebuggable = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    flavorDimensions.add("env")


    productFlavors {
        create("dev") {
            dimension = "env"
            applicationIdSuffix = ".dev"
            versionNameSuffix = "-dev"
            manifestPlaceholders["marca_prefix"] = "dev-MovieApp"
            buildConfigField("String", "BASE_URL", "\"https://api.themoviedb.org/3/movie/\"")
        }
        create("qa") {
            dimension = "env"
            applicationIdSuffix = ".qa"
            versionNameSuffix = "-qa"
            manifestPlaceholders["marca_prefix"] = "qa-MovieApp"
            buildConfigField("String", "BASE_URL", "\"http://example.com/\"")
        }
        create("prod") {
            dimension = "env"
            manifestPlaceholders["marca_prefix"] = "MovieApp"
            buildConfigField("String", "BASE_URL", "\"http://example.com/\"")
        }
    }

    buildFeatures {
        dataBinding = true
        viewBinding = true
        buildConfig = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }
    kotlinOptions {
        jvmTarget = "17"
    }


    kapt {
        correctErrorTypes = true
    }
    hilt {
        enableAggregatingTask = true
    }

}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.fragment)

    //inyeccion de dependencia
    implementation(libs.hilt)
    implementation(libs.androidx.runner)
    kapt(libs.hilt.compiler)
    //refresh
    implementation(libs.androidx.swiperefreshlayout)
    //room - database
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.room.runtime)
    kapt(libs.androidx.room.compiler)
    //paginacion
    implementation(libs.androidx.paging.common.android)
    implementation(libs.androidx.paging.runtime.ktx)
    //corrutinas
    implementation(libs.coroutines.core)
    implementation(libs.coroutines.android)

    //conexion
    implementation(libs.retrofit)
    implementation(libs.retrofit.gson)
    implementation(libs.gson)
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")

    implementation(libs.lifecycle.viewmodel.ktx)
    //imagenes
    implementation(libs.glide)
    kapt(libs.glide.compiler)
    //navegacion
    implementation(libs.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    testImplementation("org.mockito.kotlin:mockito-kotlin:5.0.0")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.1")
    // For local unit tests
    testImplementation("org.robolectric:robolectric:4.13")
    testImplementation("com.google.dagger:hilt-android-testing:2.52")
    kaptTest("com.google.dagger:hilt-compiler:2.52")
}