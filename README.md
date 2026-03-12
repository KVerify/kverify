<p align="center">
  <img src="docs/img/kverify-logo.svg" alt="KVerify Logo">
</p>

<p align="center">
  <strong>Type-safe, composable validation for Kotlin</strong>
</p>

<p align="center">
  <a href="https://kotlinlang.org"><img src="https://img.shields.io/badge/kotlin--multiplatform-2.0.21-blue.svg?logo=kotlin" alt="Kotlin 2.0.21"></a>
  <a href="LICENSE"><img src="https://img.shields.io/badge/License-Apache%202.0-blue.svg" alt="License"></a>
  <img src="https://img.shields.io/badge/Version-2.0.0-blue" alt="Version 2.0.0">
</p>

---

KVerify is a lightweight Kotlin Multiplatform validation library. No annotations, no reflection, no framework lock-in — just a clean DSL that gets out of your way.

Use it in your backend, mobile app, game, or test suite. It doesn't care where it runs.

## Why KVerify?

### Your errors are real objects

Most validation libraries give you strings. KVerify gives you typed violations you can actually work with:

```kotlin
data class PasswordTooShortViolation(
    val actualLength: Int,
    val minimumLength: Int,
) : Violation {
    override val reason =
        "Password must be at least $minimumLength characters, but got $actualLength"
}

// Handle errors by type, not by parsing strings
when (violation) {
    is PasswordTooShortViolation -> "Try a longer password (min: ${violation.minimumLength})"
    is EmailFormatViolation -> "Please check your email format"
    else -> violation.reason
}
```

### You choose what happens on failure

Collect all violations, or stop at the first — depending on what the situation calls for:

```kotlin
// Collect everything (great for forms, APIs, DTOs)
val result = validateCollecting {
    verify(user::name).notBlank().minLength(3)
    verify(user::email).notBlank()
    verify(user::age).atLeast(13)
}

result.onInvalid { violations ->
    violations.forEach { println(it.reason) }
}

// Throw immediately (great for business logic, use cases)
validateThrowing {
    verify(order::total).atLeast(minimumAmount)
    verify(order::items).minSize(1)
}
```

### Write rules once, use them everywhere

Rules are just extension functions on `Verification<T>`. Define them once, reuse across your whole codebase:

```kotlin
fun Verification<String>.validEmail(): Verification<String> =
    apply {
        scope.failIf({ !value.contains("@") }) {
            EmailFormatViolation(value)
        }
    }

// Works anywhere, on any value of the right type
validateCollecting {
    verify(user::email).validEmail()
    verify(invite::recipientEmail).validEmail()
}
```

For domain-specific validation (e.g. validating a whole request object), write a `ValidationScope` extension instead:

```kotlin
fun ValidationScope.validateSignUp(request: SignUpRequest) {
    verify(request::username).notBlank().minLength(3).maxLength(20)
    verify(request::email).notBlank().validEmail()
    verify(request::age).atLeast(13)
}

val result = validateCollecting {
    validateSignUp(request)
}
```

## Getting Started

Add the dependencies:

```kotlin
dependencies {
    implementation("io.github.kverify:kverify-core:2.0.0")
    implementation("io.github.kverify:kverify-rule-set:2.0.0")
}
```

Validate something:

```kotlin
val result = validateCollecting {
    verify(user::name).notBlank().minLength(3).maxLength(50)
    verify(user::age).atLeast(0).atMost(150)
    verify(user::tags).distinct().maxSize(10)
}

result.fold(
    onValid = { println("All good!") },
    onInvalid = { violations -> violations.forEach { println(it.reason) } }
)
```

## Built-in Rules

**Strings** — `notBlank`, `minLength`, `maxLength`, `exactLength`, `lengthRange`

**Comparable** — `atLeast`, `atMost`, `between`, `greaterThan`, `lessThan`

**Collections** — `minSize`, `maxSize`, `exactSize`, `sizeRange`, `distinct`

**Equality** — `notNull`, `equalTo`, `notEqualTo`, `oneOf`, `noneOf`

## Requirements

- Kotlin 1.9+
- JVM, JavaScript, and Native platforms supported

## License

KVerify is licensed under the [Apache 2.0 License](LICENSE).

---

<p align="center">
  <strong>Built for Kotlin developers who care about type safety and clean code.</strong>
</p>