import com.vanniktech.maven.publish.JavadocJar
import com.vanniktech.maven.publish.KotlinMultiplatform
import com.vanniktech.maven.publish.SonatypeHost
import org.jetbrains.dokka.gradle.engine.parameters.VisibilityModifier
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl

plugins {
    org.jetbrains.kotlin.multiplatform
    org.jetbrains.dokka
    com.vanniktech.maven.publish
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

    wasmJs {
        browser()
        nodejs()
    }
    wasmWasi {
        nodejs()
    }

    applyDefaultHierarchyTemplate()

    sourceSets {
        commonTest.dependencies {
            implementation(kotlin("test"))
            implementation(kotlin("test-common"))
            implementation(kotlin("test-annotations-common"))
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

dokka {
    moduleName.set("kverify-${project.name}")

    dokkaPublications.html {
        suppressInheritedMembers.set(true)
        outputDirectory.set(layout.buildDirectory.dir("dokka"))
    }

    dokkaSourceSets.configureEach {
        documentedVisibilities(
            VisibilityModifier.Public,
            VisibilityModifier.Protected,
        )

        val readmeFile = projectDir.resolve("README.md")
        if (readmeFile.exists()) {
            includes.from(readmeFile)
        }

        sourceLink {
            localDirectory.set(projectDir.resolve("src"))
            remoteUrl("${LibrarySettings.GITHUB_URL}/tree/${LibrarySettings.GITHUB_BRANCH}/${project.path.replace(":", "/")}/src")
            remoteLineSuffix.set("#L")
        }
    }
}

mavenPublishing {
    configure(
        KotlinMultiplatform(
            javadocJar = JavadocJar.Dokka("dokkaGeneratePublicationHtml"),
        ),
    )

    coordinates(
        groupId = LibrarySettings.GROUP,
        version = LibrarySettings.VERSION,
        artifactId = "kverify-${project.name}",
    )

    pom {
        configureMavenCentralMetadata(project)
    }

    publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL)
    signAllPublications()
}
