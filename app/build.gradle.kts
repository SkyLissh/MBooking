import java.util.Properties

plugins {
  alias(libs.plugins.android.application)
  alias(libs.plugins.kotlin.android)
  alias(libs.plugins.kotlin.serialization)
  alias(libs.plugins.compose.compiler)
}

val secretProperties = Properties().apply {
  val secretFile = rootProject.file("secret.properties")
  secretFile.inputStream().use { input -> load(input) }
}

val keystoreProperties = Properties().apply {
  val keystoreFile = rootProject.file("keystore.properties")
  keystoreFile.inputStream().use { input -> load(input) }
}

android {
  namespace = "com.skylissh.mbooking"
  compileSdk = 34

  signingConfigs {
    create("release") {
      storeFile = file(keystoreProperties["storeFile"] as String)
      storePassword = keystoreProperties["storePassword"] as String
      keyAlias = keystoreProperties["keyAlias"] as String
      keyPassword = keystoreProperties["keyPassword"] as String
    }
  }

  defaultConfig {
    applicationId = "com.skylissh.mbooking"
    minSdk = 26
    targetSdk = 34
    versionCode = 1
    versionName = "1.0"

    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    vectorDrawables {
      useSupportLibrary = true
    }

    buildConfigField("String", "TMDB_API_TOKEN", secretProperties["TMDB_API_TOKEN"] as String)
    buildConfigField("String", "TMDB_API_URL", secretProperties["TMDB_API_URL"] as String)
    buildConfigField(
      "String",
      "TMDB_API_IMAGE_URL",
      secretProperties["TMDB_API_IMAGE_URL"] as String
    )
  }

  buildTypes {
    release {
      isMinifyEnabled = true
      isShrinkResources = true
      proguardFiles(
        getDefaultProguardFile("proguard-android-optimize.txt"),
        "proguard-rules.pro"
      )
      signingConfig = signingConfigs.getByName("release")
    }
  }
  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
  }
  kotlinOptions {
    jvmTarget = "1.8"
  }
  buildFeatures {
    compose = true
    buildConfig = true
  }
  composeOptions {
    kotlinCompilerExtensionVersion = "1.5.1"
  }
  packaging {
    resources {
      excludes += "/META-INF/{AL2.0,LGPL2.1}"
    }
  }
}

dependencies {

  // Jetpack Compose
  implementation(libs.androidx.core.ktx)
  implementation(libs.androidx.lifecycle.runtime.ktx)
  implementation(libs.androidx.activity.compose)
  implementation(platform(libs.androidx.compose.bom))
  implementation(libs.androidx.ui)
  implementation(libs.androidx.ui.graphics)
  implementation(libs.androidx.ui.tooling.preview)
  implementation(libs.androidx.material3)
  implementation(libs.androidx.google.fonts)
  implementation(libs.androidx.navigation.compose)
  implementation(libs.lifecycle.viewmodel.compose)
  implementation(libs.lottie.compose)

  // Ktor
  implementation(libs.ktor.client.core)
  implementation(libs.ktor.client.cio)
  implementation(libs.ktor.client.content.negotiation)
  implementation(libs.ktor.client.resources)
  implementation(libs.ktor.serialization.kotlinx.json)

  // Coil
  implementation(libs.coil.compose)
  implementation(libs.coil.network)

  // Composables Core
  implementation(libs.lucide.icons)
  implementation(libs.composables.core)

  // Koin
  implementation(platform(libs.koin.bom))
  implementation(libs.koin.core)
  implementation(libs.koin.androidx.compose)
  implementation(libs.koin.androidx.compose.navigation)

  // Kotlinx
  implementation(libs.kotlinx.serialization.json)
  implementation(libs.kotlinx.datetime)

  testImplementation(libs.junit)
  androidTestImplementation(libs.androidx.junit)
  androidTestImplementation(libs.androidx.espresso.core)
  androidTestImplementation(platform(libs.androidx.compose.bom))
  androidTestImplementation(libs.androidx.ui.test.junit4)
  debugImplementation(libs.androidx.ui.tooling)
  debugImplementation(libs.androidx.ui.test.manifest)
}
