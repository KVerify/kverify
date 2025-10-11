import org.gradle.kotlin.dsl.assign
import org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_1_8
import org.jetbrains.kotlin.gradle.dsl.KotlinCommonCompilerOptions
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmCompilerOptions
import org.jetbrains.kotlin.gradle.dsl.KotlinVersion

val kverifyOptIns =
    listOf(
        "kotlin.contracts.ExperimentalContracts",
    )

internal fun KotlinCommonCompilerOptions.applyKverifyCommonCompilerOptions() {
    allWarningsAsErrors = true
    apiVersion = KotlinVersion.KOTLIN_2_0
    languageVersion = KotlinVersion.KOTLIN_2_0
}

internal const val KVERIFY_JVM_TARGET = 8

internal fun KotlinJvmCompilerOptions.applyKverifyJvmCompilerOptions() {
    applyKverifyCommonCompilerOptions()
    jvmTarget = JVM_1_8
    freeCompilerArgs.add("-Xjdk-release=1.8")
}
