import com.varabyte.kobweb.gradle.worker.util.configAsKobwebWorker

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kobweb.worker)
}

group = "com.example.BlogMultiplatform.worker"
version = "1.0-SNAPSHOT"

kotlin {
    configAsKobwebWorker()

    sourceSets {
        commonMain.dependencies {
            implementation(libs.kotlinx.serialization)
        }
        jsMain.dependencies {
            implementation(libs.kobweb.worker)
            //implementation(libs.kotlinx.serialization)
        }
        jvmMain.dependencies {
            implementation(libs.kotlinx.serialization)
        }
    }
}
