import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl

plugins {
    org.jetbrains.kotlin.multiplatform
}

repositories {
    mavenCentral()
}

@OptIn(ExperimentalKotlinGradlePluginApi::class, ExperimentalWasmDsl::class)
kotlin {
    compilerOptions {
        applyKverifyCommonCompilerOptions()
    }

    jvm {
        compilerOptions {
            applyKverifyJvmCompilerOptions()
        }
    }

    js {
        nodejs()
        useCommonJs()
    }

    iosX64()
    iosArm64()
    iosSimulatorArm64()
    macosX64()
    macosArm64()
    tvosX64()
    tvosArm64()
    tvosSimulatorArm64()
    watchosX64()
    watchosArm64()
    watchosSimulatorArm64()

    linuxX64()
    linuxArm64()

    mingwX64()

    wasmJs {
        browser()
        nodejs()
    }
    wasmWasi {
        nodejs()
    }

    applyDefaultHierarchyTemplate()
}

tasks {
    withType<Test>().configureEach {
        useJUnitPlatform()
    }
}
