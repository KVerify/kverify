import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi

plugins {
    org.jetbrains.kotlin.multiplatform
}

@OptIn(ExperimentalKotlinGradlePluginApi::class)
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
        useCommonJs()
    }

    applyDefaultHierarchyTemplate()

    sourceSets {
        commonTest {
            dependencies {
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
