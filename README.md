<div align="center">
  <img src="docs/img/kverify-logo.svg" alt="KVerify" width="280"/>
  <br/><br/>
  <strong>Type-safe, composable validation for Kotlin Multiplatform.</strong>
  <br/><br/>

[![Maven Central](https://img.shields.io/badge/Maven%20Central-kverify-blue)](https://central.sonatype.com/namespace/io.github.kverify)
[![Kotlin](https://img.shields.io/badge/Kotlin-1.9%2B-blue?logo=kotlin)](https://kotlinlang.org)
[![License](https://img.shields.io/badge/License-Apache%202.0-green)](LICENSE)
[![KMP](https://img.shields.io/badge/Multiplatform-supported-brightgreen)](https://kotlinlang.org/docs/multiplatform.html)
</div>

---

## Installation

```kotlin
dependencies {
    // Validation engine + built-in rules
    implementation("io.github.kverify:kverify-rule-set:<version>")

    // Validation engine only
    implementation("io.github.kverify:kverify-core:<version>")
}
```

`kverify-rule-set` includes `kverify-core` — you only need one. Supports all Kotlin Multiplatform targets. Requires
Kotlin 1.9+.

---

## The problem with validation

Validation always starts simple and ends messy. `if` checks scattered across the codebase. Error messages hardcoded as
strings. No structure, no reuse, no way to know which field failed without parsing the message. And if you reach for a
framework, you get annotations crawling over your model classes and a build plugin you didn't ask for.

KVerify is what validation should have been. A clean DSL, typed failures, automatic path tracking — and nothing your
project didn't already have.

---

## Violations are values, not messages

Most libraries give you a string when something fails. KVerify gives you a typed data class — with the actual values
that caused the failure — that you can inspect, pattern-match on, serialize, or forward to a frontend as structured
errors.

```kotlin
when (violation) {
    is MinLengthViolation ->
        respondWithError(violation.validationPath, "At least ${violation.minLengthAllowed} characters required")
    is NotBlankViolation ->
        respondWithError(violation.validationPath, "This field is required")
    else ->
        respondWithError(message = violation.reason)
}
```

No string parsing. No guessing. The type tells you everything.

---

## Path tracking that builds itself

Pass a property reference to `verify` and KVerify tracks exactly where in the object structure each violation came
from — through nested objects, through collections, through any depth.

```kotlin
data class OrderItem(val name: String, val price: Double)
data class Order(val customerName: String, val items: List<OrderItem>)

val result = validateCollecting {
    verify(order::customerName).notBlank()

    verify(order::items).each { item ->
        verify(item::name).notBlank()
        verify(item::price).greaterThan(0.0)
    }
}

result.violations
    .filterIsInstance<PathAwareViolation>()
    .forEach { println("${it.validationPath}: ${it.reason}") }
```

```
ValidationPath("customerName"): Value must not be blank
ValidationPath("items", 1, "name"): Value must not be blank
ValidationPath("items", 1, "price"): Value must be greater than 0.0. Actual: -1.0
```

No path configuration. No field name strings. The property reference does the work.

---

## Collect everything, or stop at the first failure

Two scopes. Same rules. You decide at the call site.

```kotlin
// Run every rule — ideal for forms, APIs, DTOs
val result = validateCollecting {
    verify(request::username).notBlank().minLength(3)
    verify(request::email).notBlank()
    verify(request::age).atLeast(18)
}

result.fold(
    onValid = { proceed() },
    onInvalid = { violations -> respondWithErrors(violations) }
)
```

```kotlin
// Stop at the first failure — ideal for business rules and invariants
validateThrowing {
    verify(order::total).greaterThan(BigDecimal.ZERO)
    verify(order::items).minSize(1)
}
```

The validation logic is the same either way. Only the scope changes.

Need something different? The scope is an interface — implement your own. A logging scope, an auditing scope, anything
you need. See the [Wiki](https://github.com/KVerify/kverify/wiki/ValidationScope) for details.

---

## Validation is just Kotlin

Rules are extension functions. They live where they make sense, they compose naturally, and they read exactly like what
they validate. No annotations, no code generation, no framework to learn.

```kotlin
fun ValidationScope.validateAddress(address: Address) {
    verify(address::street).notBlank()
    verify(address::city).notBlank()
    verify(address::postalCode).exactLength(5)
}

fun ValidationScope.validateUser(user: User) {
    verify(user::name).notBlank().minLength(3)
    verify(user::age).atLeast(18)
    pathName("address").validateAddress(user.address)
}

val result = validateCollecting {
    validateUser(user)
}
```

---

## Null-safe chaining

Optional fields fit naturally into the chain. Rules only run when the value is present.

```kotlin
validateCollecting {
    verify(user::middleName)
        .takeIfNotNull()
        ?.minLength(2)
        ?.maxLength(50)
}
```

---

## Built-in rules

| Category   | Rules                                                              |
|------------|--------------------------------------------------------------------|
| String     | `notBlank`, `minLength`, `maxLength`, `exactLength`, `lengthRange` |
| Comparable | `atLeast`, `atMost`, `between`, `greaterThan`, `lessThan`          |
| Collection | `minSize`, `maxSize`, `exactSize`, `sizeRange`, `distinct`         |
| Equality   | `notNull`, `equalTo`, `notEqualTo`, `oneOf`, `noneOf`              |

Every rule accepts an optional `reason` parameter. Every rule produces a typed violation with the full validation path
and the constraint values that caused the failure.

---

## Documentation

Full documentation is on the [Wiki](https://github.com/KVerify/kverify/wiki).

---

## License

KVerify is licensed under the [Apache License 2.0](LICENSE).