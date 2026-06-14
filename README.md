<div align="center">
  <img src="docs/img/kverify-logo.svg" alt="KVerify" width="280"/>
  <br/><br/>
  <strong>Kotlin-first validation DSL with typed failures.</strong>
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

`kverify-rule-set` includes `kverify-core` - you only need one. Supports all Kotlin Multiplatform targets. Requires
Kotlin 1.9+. The latest version is available
on [Maven Central](https://central.sonatype.com/namespace/io.github.kverify).

## Validation is just Kotlin

Rules are extension functions. Built-in rules and your own rules are used the same way.

```kotlin
private val usernameRegex = Regex("^[A-Za-z0-9_]+$")

fun Verification<String>.matchesUsername(): Verification<String> =
    apply {
        scope.failIf({ !value.matches(usernameRegex) }) {
            violation("Username has invalid format")
        }
    }
```

```kotlin
validateCollecting {
    verify(request::username)
        .notBlank()
        .lengthRange(3, 20)
        .matchesUsername()

    verify(request::age).atLeast(18)
    verify(request::referralCode).takeIfNotNull()?.notBlank()
}.throwOnInvalid()
```

No annotations. No code generation. No model pollution.

Just Kotlin functions and IDE-discoverable validation rules.

## Typed failures, not string messages

`violation("...")` is useful for simple rules. When the failure matters to your API, tests, or domain logic, make it a
type.

```kotlin
data class InvalidUsernameViolation(
    val actualValue: String,
    override val validationPath: ValidationPath,
    override val reason: String,
) : PathAwareViolation
```

The rule stays the same shape:

```kotlin
fun Verification<String>.matchesUsername(reason: String? = null): Verification<String> =
    apply {
        scope.failIf({ !value.matches(usernameRegex) }) {
            InvalidUsernameViolation(
                actualValue = value,
                validationPath = scope.validationContext.validationPath(),
                reason = reason ?: "Username has invalid format",
            )
        }
    }
```

## Path tracking that builds itself

Typed failures are much more useful when they know where they came from.

Pass a property reference to `verify`, and KVerify adds the property name to the validation path automatically.

```kotlin
data class OrderItem(
    val name: String,
    val price: Double,
)

data class Order(
    val customerName: String,
    val items: List<OrderItem>,
)

val result = validateCollecting {
    verify(order::customerName).notBlank()

    verify(order::items).each { item ->
        verify(item::name).notBlank()
        verify(item::price).greaterThan(0.0)
    }
}
```

Validation paths:

```text
ValidationPath("customerName")
ValidationPath("items", 0, "name")
ValidationPath("items", 0, "price")
```

No field-name strings. No manual path configuration. Collection indexes are added automatically by `each`.

Because paths are built from property references, IDE rename refactoring works with them too.

## Collect everything, or stop at the first failure

Validation rules do not decide what happens when they fail. The scope does.

Extract your validation once:

```kotlin
fun ValidationScope.validateRequest(request: UserRequest) {
    verify(request::username)
        .notBlank()
        .lengthRange(3, 20)
        .matchesUsername()

    verify(request::age).atLeast(18)
    verify(request::referralCode).takeIfNotNull()?.notBlank()
}
```

Collect every violation:

```kotlin
val result = validateCollecting {
    validateRequest(request)
}
```

Stop at the first violation:

```kotlin
validateThrowing {
    validateRequest(request)
}
```

Or define your own behavior:

```kotlin
class PrintingValidationScope(
    override val validationContext: ValidationContext = EmptyValidationContext,
) : ValidationScope {
    override fun enforce(rule: Rule) {
        val violation = rule.check() ?: return

        println("Violation encountered: $violation")
    }
}

inline fun validatePrinting(
    validationContext: ValidationContext = EmptyValidationContext,
    block: PrintingValidationScope.() -> Unit,
): Unit = PrintingValidationScope(validationContext).run(block)
```

And run the same validation inside it:

```kotlin
validatePrinting {
    validateRequest(request)
}
```

Same validation. Different failure handling.

## Built-in rules

`kverify-rule-set` provides common rules for strings, comparable values, collections and equality checks.

| Category   | Rules                                                              |
|------------|--------------------------------------------------------------------|
| String     | `notBlank`, `minLength`, `maxLength`, `exactLength`, `lengthRange` |
| Comparable | `atLeast`, `atMost`, `between`, `greaterThan`, `lessThan`          |
| Collection | `minSize`, `maxSize`, `exactSize`, `sizeRange`, `distinct`         |
| Equality   | `notNull`, `equalTo`, `notEqualTo`, `oneOf`, `noneOf`              |

Every rule accepts an optional `reason` parameter. Every built-in rule produces a typed violation.

## Documentation

Full documentation is on the [Wiki](https://github.com/KVerify/kverify/wiki).

---

## License

KVerify is licensed under the [Apache License 2.0](LICENSE).