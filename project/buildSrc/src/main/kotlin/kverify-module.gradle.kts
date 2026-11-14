import com.vanniktech.maven.publish.JavadocJar
import com.vanniktech.maven.publish.KotlinMultiplatform
import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent
import org.jetbrains.dokka.gradle.engine.parameters.VisibilityModifier
import org.jetbrains.kotlin.gradle.dsl.abi.ExperimentalAbiValidation

plugins {
    org.jetbrains.kotlin.multiplatform
    org.jetbrains.dokka
    com.vanniktech.maven.publish
    de.infix.testBalloon
}

kotlin {
    explicitApi()
    applyKverifyMultiplatformDefaults()

    @OptIn(ExperimentalAbiValidation::class)
    abiValidation {
        enabled = true
    }

    sourceSets {
        commonTest.dependencies {
            implementation(kotlin("test"))
            implementation(project(":test-kit"))
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
                TestLogEvent.FAILED,
                TestLogEvent.PASSED,
            )
        exceptionFormat = TestExceptionFormat.FULL
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

        val repositoryRoot = rootProject.projectDir.parentFile
        val modulePath = projectDir.relativeTo(repositoryRoot).invariantSeparatorsPath
        val branchUrl = "${LibrarySettings.GITHUB_URL}/tree/${LibrarySettings.GITHUB_BRANCH}"

        sourceLink {
            localDirectory.set(projectDir.resolve("src"))
            remoteUrl("$branchUrl/$modulePath/src")
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

    publishToMavenCentral()
    signAllPublications()
}
