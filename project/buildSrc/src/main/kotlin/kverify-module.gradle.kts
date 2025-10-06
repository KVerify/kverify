import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl

plugins {
    org.jetbrains.kotlin.multiplatform
}

@OptIn(ExperimentalKotlinGradlePluginApi::class, ExperimentalWasmDsl::class)
kotlin {
    explicitApi()

    compilerOptions {
        applyKverifyCommonCompilerOptions()
        optIn.addAll(kverifyOptIns)
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

    applyDefaultHierarchyTemplate()

    sourceSets {
        commonTest {
            dependencies {
                implementation(kotlin("test"))
                implementation(project(":test-kit"))
            }
        }
    }
}

tasks.named<Test>("jvmTest") {
    useJUnitPlatform()
    filter {
        isFailOnNoMatchingTests = false
    }
    testLogging {
        showExceptions = true
        showStandardStreams = true
        events =
            setOf(
                org.gradle.api.tasks.testing.logging.TestLogEvent.FAILED,
                org.gradle.api.tasks.testing.logging.TestLogEvent.PASSED,
            )
        exceptionFormat = org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
    }
}
