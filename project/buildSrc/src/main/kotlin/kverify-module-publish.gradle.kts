import com.vanniktech.maven.publish.SonatypeHost

plugins {
    com.vanniktech.maven.publish
}

mavenPublishing {
    coordinates(
        groupId = LibrarySettings.GROUP,
        version = LibrarySettings.VERSION,
        artifactId = project.name,
    )

    pom {
        configureMavenCentralMetadata(project)
    }

    publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL)
    signAllPublications()
}
