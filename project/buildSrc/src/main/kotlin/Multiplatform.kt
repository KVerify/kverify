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
    @Suppress("DEPRECATION")
    macosX64()
    macosArm64()
    @Suppress("DEPRECATION")
    tvosX64()
    tvosArm64()
    tvosSimulatorArm64()
    @Suppress("DEPRECATION")
    watchosX64()
    watchosArm64()
    watchosDeviceArm64()
    watchosSimulatorArm64()

    linuxX64()
    linuxArm64()

    mingwX64()

    androidNativeArm32()
    androidNativeArm64()
    androidNativeX86()
    androidNativeX64()

    wasmJs {
        browser()
        nodejs()
    }
    wasmWasi {
        nodejs()
    }

    applyDefaultHierarchyTemplate()
}
