plugins {
    `kverify-module`
    `kverify-module-publish`
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
