plugins {
    `kverify-module`
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(project(":kverify:core"))
        }
    }
}
