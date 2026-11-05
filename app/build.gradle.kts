import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.example.helloworldandmyfirstapp"
    compileSdk = 37

    defaultConfig {
        applicationId = "com.example.helloworldandmyfirstapp"
        minSdk = 24
        targetSdk = 37
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            optimization {
                enable = false
            }
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        compose = true
    }
}

abstract class CodeStatsTask : DefaultTask() {
    @get:InputDirectory
    abstract val sourceDir: DirectoryProperty

    @get:OutputFile
    abstract val outputFile: RegularFileProperty

    @TaskAction
    fun generate() {
        val src = sourceDir.get().asFile
        var totalLines = 0
        if (src.exists()) {
            src.walkTopDown()
                .filter { it.extension == "kt" && !it.name.equals("CodeStats.kt") }
                .forEach { file ->
                    totalLines += file.readLines().size
                }
        }
        val formattedCount = String.format("%,d", totalLines)
        val outFile = outputFile.get().asFile
        outFile.parentFile.mkdirs()
        outFile.writeText("""
            package com.example.helloworldandmyfirstapp
            
            object CodeStats {
                const val LINE_COUNT = "${formattedCount} LINES OF CODE"
            }
        """.trimIndent())
    }
}

val generateCodeStats = tasks.register<CodeStatsTask>("generateCodeStats") {
    sourceDir.set(layout.projectDirectory.dir("src/main/java"))
    outputFile.set(layout.projectDirectory.file("src/main/java/com/example/helloworldandmyfirstapp/CodeStats.kt"))
}

tasks.withType<KotlinCompile>().configureEach {
    dependsOn(generateCodeStats)
}

dependencies {
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.material.icons.extended)
    implementation(libs.androidx.media3.exoplayer)
    implementation(libs.androidx.media3.session)
    implementation(libs.androidx.media3.ui)
    implementation(libs.coil.compose)
    implementation(libs.androidx.compose.ui.text.google.fonts)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    testImplementation(libs.junit)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
    implementation(libs.androidx.compose.ui.tooling)
}
