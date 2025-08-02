plugins {
    `kverify-module`
    `kverify-module-publish`
    alias(libs.plugins.kotest.multiplatform)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(project(":kverify:core"))
            api(project(":kverify:named:named-value"))
            api(project(":kverify:named:named-rule-set"))
        }
    }
}
