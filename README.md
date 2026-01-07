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

KVerify is a validation library that treats validation as **first-class code**, not configuration. Built for Kotlin
developers who want compile-time safety, flexibility, and clean APIs.

## Why KVerify?

### Type-Safe Composition

Rules are typed values that compose naturally:

```kotlin
// Define rules once
val nicknameRule = RuleList(
    StringRules.notBlank(),
    StringRules.lengthBetween(3..20),
    StringRules.alphanumeric(),
)

// Compose with +
val userRule = nicknameRule + emailRule + ageRule

// Type safety guaranteed at compile time
user.nickname verifyWith nicknameRule  // ✅ Works
user.age verifyWith nicknameRule       // ❌ Compile error
```

### Choose Your Validation Strategy

Different use cases need different error handling:

```kotlin
// Collect all errors (for forms, DTOs, APIs)
val result = verifyCollecting {
    user.nickname verifyWith nicknameRule
    user.email verifyWith emailRule
    user.age verifyWith ageRule
}
// Returns: Valid or Invalid(List<Violation>)

// Throw on first error (for business logic, use cases)
verifyThrowing {
    order.total verifyWith minimumAmountRule
    order.items verifyWith notEmptyRule
}
// Throws: ValidationException on first failure
```

### Rich, Typed Violations

Errors are real objects, not just strings:

```kotlin
data class PasswordTooShortViolation(
    val actualLength: Int,
    val minimumLength: Int,
) : Violation {
    override val reason =
        "Password must be at least $minimumLength characters, but is $actualLength"
}

// Handle errors by type
when (val violation = result.violations.first()) {
    is PasswordTooShortViolation ->
        "Try a longer password (min: ${violation.minimumLength})"
    is EmailInvalidViolation ->
        "Please check your email format"
    else -> violation.reason
}
```

## Quick Start

**Add dependencies:**

```kotlin
dependencies {
    implementation("io.github.kverify:kverify-core:2.0.0")
    implementation("io.github.kverify:kverify-rule-set:2.0.0")
    implementation("io.github.kverify:kverify-named-value:2.0.0")
    implementation("io.github.kverify:kverify-named-rule-set:2.0.0")
}
```

**Validate anything:**

```kotlin
import io.github.kverify.core.context.verifyCollecting
import io.github.kverify.rule.set.provider.*

object StringRules : StringRuleProvider by DefaultStringRuleProvider()

data class SignUpRequest(
    val username: String,
    val email: String,
    val age: Int,
)

fun validateSignUp(request: SignUpRequest): ValidationResult = verifyCollecting {
    request.username withName "username" verifyWith RuleList(
        NamedStringRules.namedNotBlank(),
        NamedStringRules.namedLengthBetween(3..20),
        NamedStringRules.namedAlphanumeric(),
    )

    request.email withName "email" verifyWith emailRule
    request.age withName "age" verifyWith ageRule
}

// Use the result
val result = validateSignUp(request)

result.fold(
    ifValid = { createUser(request) },
    ifInvalid = { violations ->
        violations.forEach { println(it.reason) }
        // 'username' must be alphanumeric
        // 'email' must contain @
        // 'age' must be at least 13
    }
)
```

## What Makes It Different?

| Feature              | KVerify               | Bean Validation       | Konform            |
|----------------------|-----------------------|-----------------------|--------------------|
| **Type Safety**      | ✅ Compile-time        | ❌ Runtime annotations | ✅                  |
| **Composable**       | ✅ Rules are values    | ❌ Annotation-based    | ⚠️ Limited         |
| **Error Strategies** | ✅ Multiple strategies | ❌ Always throws       | ⚠️ Single strategy |
| **Typed Violations** | ✅ Custom error types  | ❌ String messages     | ❌ String messages  |
| **Custom Rules**     | ✅ First-class         | ⚠️ Complex            | ✅                  |
| **Multiplatform**    | ✅ JVM, JS, Native     | ⚠️ JVM only           | ✅                  |

## Features

- **45+ built-in rules** for strings, numbers, collections
- **Multiple validation strategies** - collecting, throwing, fail-fast, first-error
- **Custom typed violations** - structured error information
- **Field-specific errors** - named values for better error messages
- **Localization support** - customize error messages per locale
- **Zero reflection** in core (optional for convenience)
- **Coroutine support** - async validation for database/API checks
- **Modular** - use only what you need

## Requirements

- Kotlin 1.9+
- Compatible with JVM, JavaScript, and Native platforms

## License

KVerify is licensed under the [Apache 2.0 License](LICENSE).

---

<p align="center">
  <strong>Built for Kotlin developers who care about type safety and clean code.</strong>
</p>
