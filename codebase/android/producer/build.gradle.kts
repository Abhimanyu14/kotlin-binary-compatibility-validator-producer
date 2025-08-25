/*
 * Copyright 2025-2025 Abhimanyu
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

@file:Suppress("UnstableApiUsage")

import com.vanniktech.maven.publish.AndroidSingleVariantLibrary
import com.vanniktech.maven.publish.SonatypeHost

plugins {
    alias(libs.plugins.plugin.android.library)
    alias(libs.plugins.plugin.kotlin.android)
    alias(libs.plugins.plugin.kotlin.compose)
    alias(libs.plugins.plugin.kotlinx.binary.compatibility.validator)
    alias(libs.plugins.plugin.maven.publish)
}

android {
    namespace = "com.makeappssimple.abhimanyu.kotlin.binary.compatibility.validator.producer"
    compileSdk = libs.versions.compile.sdk.get().toInt()
    ndkVersion = libs.versions.ndk.get()

    buildFeatures {
        buildConfig = true
        compose = true
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    defaultConfig {
        minSdk = libs.versions.min.sdk.get().toInt()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        consumerProguardFiles("consumer-rules.pro")
    }

    kotlinOptions {
        jvmTarget = libs.versions.jvm.get()
    }
}

dependencies {
    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.compose.runtime)
    implementation(libs.androidx.core.ktx)

    implementation(platform(libs.androidx.compose.bom))
}

mavenPublishing {
    configure(
        AndroidSingleVariantLibrary(
            // whether to publish a javadoc jar
            publishJavadocJar = true,
            // whether to publish a sources jar
            sourcesJar = true,
            // the published variant
            variant = "release",
        )
    )

    // Define coordinates for the published artifact
    coordinates(
        groupId = "io.github.Abhimanyu14",
        artifactId = "kotlin-binary-compatibility-validator-producer",
        version = libs.versions.producer.version.name.get()
    )

    // Configure POM metadata for the published artifact
    pom {
        name.set("Kotlin Binary Compatibility Validator Producer")
        description.set("Kotlin Binary Compatibility Validator Producer")
        inceptionYear.set("2025")
        url.set("https://github.com/Abhimanyu14/kotlin-binary-compatibility-validator-producer")

        // Specify developers information
        developers {
            developer {
                id.set("Abhimanyu14")
                name.set("Abhimanyu")
                email.set("abhimanyu.n14@gmail.com")
            }
        }

        licenses {
            license {
                name.set("Apache License 2.0")
                url.set("https://www.apache.org/licenses/LICENSE-2.0")
                distribution.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
            }
        }

        // Specify SCM information
        scm {
            url.set("https://github.com/Abhimanyu14/kotlin-binary-compatibility-validator-producer")
        }
    }

    // Configure publishing to Maven Central
    publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL)

    // Enable GPG signing for all publications
    signAllPublications()
}

kotlin {
    explicitApi()
}
