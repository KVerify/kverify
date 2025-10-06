plugins {
    `kverify-internal-module`
}

repositories {
    mavenCentral()
}

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                api(libs.kotest.property)
                api(kotlin("test"))
                api(kotlin("test-common"))
                api(kotlin("test-annotations-common"))
            }
        }
    }
}
