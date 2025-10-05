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

    // Tier 1
    macosArm64()
    iosSimulatorArm64()
    iosArm64()

    // Tier 2
    linuxX64()
    linuxArm64()
    macosX64()
    iosX64()
    watchosSimulatorArm64()
    watchosX64()
    watchosArm32()
    tvosSimulatorArm64()
    tvosX64()
    tvosArm64()

    // Tier 3
    mingwX64()
    watchosDeviceArm64()

    // Wasm
    wasmJs()
    wasmWasi()

    applyDefaultHierarchyTemplate()
}

tasks {
    withType<Test>().configureEach {
        useJUnitPlatform()
    }
}
