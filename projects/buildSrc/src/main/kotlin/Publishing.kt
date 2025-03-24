import org.gradle.api.Project
import org.gradle.api.provider.Property
import org.gradle.api.publish.maven.MavenPom

infix fun <T> Property<T>.set(value: T): Unit = this.set(value)

fun MavenPom.configureMavenCentralMetadata(project: Project) {
    name set project.name
    description set "KVerify - Kotlin Validation Library"
    inceptionYear set "2024"
    url set "https://github.com/kverify/kverify"

    organization {
        name set "KVerify"
        url set "https://github.com/kverify"
    }

    developers {
        developer {
            name set "MrKekovich"
            email set "mrkekovich.official@gmail.com"
        }
    }

    issueManagement {
        system set "GitHub"
        url set "https://github.com/kverify/kverify/issues"
    }

    licenses {
        license {
            name set "The Apache Software License, Version 2.0"
            url set "https://www.apache.org/licenses/LICENSE-2.0.txt"
        }
    }

    scm {
        connection set "scm:git:ssh://github.com/kverify/kverify.git"
        url set "https://github.com/kverify/kverify"
    }
}
