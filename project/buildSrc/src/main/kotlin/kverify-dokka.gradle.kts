import org.jetbrains.dokka.gradle.engine.parameters.VisibilityModifier

plugins {
    org.jetbrains.dokka
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
