import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

@OptIn(ExperimentalWasmDsl::class)
internal fun KotlinMultiplatformExtension.applyKverifyMultiplatformDefaults() {
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
    macosArm64()
    tvosArm64()
    tvosSimulatorArm64()
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
