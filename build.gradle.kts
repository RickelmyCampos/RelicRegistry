// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    alias(libs.plugins.compose.compiler) apply false
    id("com.google.dagger.hilt.android") version "2.50" apply false
   // alias(libs.plugins.androidx.room) apply false
    id("androidx.room") version "2.6.1" apply false
    id("com.google.gms.google-services") version "4.4.2" apply false

}