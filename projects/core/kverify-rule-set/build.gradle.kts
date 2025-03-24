import com.vanniktech.maven.publish.SonatypeHost

plugins {
    `kverify-module`
    alias(libs.plugins.kotest.multiplatform)
    alias(libs.plugins.maven.publish)
    id("maven-publish")
    id("signing")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(project(":core:kverify-core"))
        }
    }
}

// TODO: Move publishing into a separate script
mavenPublishing {
    val kverifyVersion: String by project
    val groupId: String by project

    coordinates(
        groupId = groupId,
        version = kverifyVersion,
        artifactId = "kverify-rule-set",
    )

    pom {
        configureMavenCentralMetadata(project)
    }

    publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL)
    signAllPublications()
}
