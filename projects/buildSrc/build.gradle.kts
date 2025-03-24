plugins {
    `kotlin-dsl`
}

kotlin {
    compilerOptions {
        allWarningsAsErrors = true
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.bundles.pluginsForBuildSrc)
}
