plugins {
    `kverify-module`
    `kverify-module-publish`
}

kotlin {
    sourceSets {
        jvmMain.dependencies {
            implementation(kotlin("reflect"))
        }
    }
}
