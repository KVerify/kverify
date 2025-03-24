plugins {
    `kverify-module`
    `kverify-module-publish`
    alias(libs.plugins.kotest.multiplatform)
}

kotlin {
    sourceSets {
        jvmMain.dependencies {
            implementation(kotlin("reflect"))
        }
    }
}
