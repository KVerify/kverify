<p align="center">
  <img src="docs/img/kverify-logo.svg" alt="KVerify Logo">
</p>

<p align="center">
  <strong>A powerful, flexible Kotlin Multiplatform validation library</strong>
</p>

<p align="center">
  <a href="https://kotlinlang.org"><img src="https://img.shields.io/badge/kotlin--multiplatform-2.0.0-blue.svg?logo=kotlin" alt="Kotlin 2.0.0"></a>
  <a href="LICENSE"><img src="https://img.shields.io/badge/License-Apache%202.0-blue.svg" alt="License"></a>
  <img src="https://img.shields.io/badge/Version-2.0.0--rc--1-blue" alt="Version 2.0.0-rc-1">
</p>

---

## 🔎 Overview

KVerify is a modern Kotlin Multiplatform validation library that provides a concise and powerful DSL for defining and
executing validation rules. Whether you're validating simple values or complex object hierarchies, KVerify makes it easy
while maintaining type safety and flexibility.


## ✨ Key Features

- ✅ Concise and expressive validation DSL
- 🔄 Supports multiple validation strategies (`validateAll`, `validateFirst`, `validateOrThrow`)
- 📦 Built-in rule sets for common validation cases
- 🛠️ Easy-to-extend API for custom rules
- 🌍 Fully compatible with Kotlin Multiplatform

## 📦 Installation

Add KVerify to your project using Gradle:

```kotlin
dependencies {
    implementation("io.github.kverify:kverify-core:$version")
    implementation("io.github.kverify:rule-set:$version") // optional
}
```

## 📜 DSL Example

```kotlin
val kotlinIsTheBestLanguage = true
val kverifyMakesLifeEasier = true

val emailPattern = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$".toRegex()

val emailRule =
    Rule<String> { email: String ->
        validate(email.matches(emailPattern)) {
            "Hey, this doesn't look like a proper email!".asViolation()
        }
    }

validateAll {
    "john.doe@example.com".applyRules(
        StringRules.notBlank(),
        StringRules.lengthBetween(3..255),
        ComparableRules.notEqualTo("not-an-email"),
        emailRule,
    )

    validate(kotlinIsTheBestLanguage && kverifyMakesLifeEasier) {
        "No violations, just truth here!".asViolation()
    }
}.onValid {
    println("Woo-hoo! Everything checks out!")
}.onInvalid { violations: List<Violation> ->
    println("Oops! Something went wrong. You've got ${violations.size} issues to fix!")
}.throwOnFailure()
```

For more examples and advanced usage, check out the [documentation](https://github.com/KVerify/kverify/wiki)

## 📄 License

KVerify is licensed under the [Apache 2.0 License](LICENSE).
