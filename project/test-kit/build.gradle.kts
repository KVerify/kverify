plugins {
    `kverify-internal-module`
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(libs.test.balloon.framework.core)
        }
    }
}
