import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi

plugins {
    org.jetbrains.kotlin.multiplatform
}

repositories {
    mavenCentral()
}

@OptIn(ExperimentalKotlinGradlePluginApi::class)
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
}

tasks {
    withType<Test>().configureEach {
        useJUnitPlatform()
    }
}
