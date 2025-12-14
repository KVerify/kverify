import org.jetbrains.dokka.DokkaConfiguration.Visibility
import org.jetbrains.dokka.gradle.DokkaTask

plugins {
    org.jetbrains.dokka
}

tasks.withType<DokkaTask>().configureEach {
    dokkaSourceSets.configureEach {
        documentedVisibilities =
            setOf(
                Visibility.PUBLIC,
                Visibility.PROTECTED,
            )

        val readmeFile = projectDir.resolve("README.md")
        if (readmeFile.exists()) {
            includes.from(readmeFile)
        }

        sourceLink {
            localDirectory.set(projectDir.resolve("src"))
            remoteUrl.set(
                uri("https://github.com/kverify/kverify/tree/main/${project.path.replace(":", "/")}/src").toURL(),
            )
            remoteLineSuffix.set("#L")
        }

        moduleName.set("kverify-${project.name}")

        suppressInheritedMembers.set(true)
    }

    outputDirectory.set(layout.buildDirectory.dir("dokka"))
}
