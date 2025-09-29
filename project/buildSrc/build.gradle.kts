plugins {
    `kotlin-dsl`
}

kotlin {
    compilerOptions {
        allWarningsAsErrors = true
    }
}

repositories {
    google()
    mavenCentral()
}

dependencies {
    implementation(libs.bundles.pluginsForBuildSrc)
}
