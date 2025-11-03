plugins {
    `kverify-module`
    `kverify-module-publish`
    alias(libs.plugins.atomicfu)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(project(":kverify:core"))
            implementation(libs.coroutine.core)
        }
    }
}
