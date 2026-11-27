<p align="center">
    <img src="docs/img/kverify-logo.svg" alt="KVerify logo" width="280">
</p>

<p align="center">
    <a href="https://kotlinlang.org"><img src="https://img.shields.io/badge/kotlin-2.3.21-blue.svg?logo=kotlin" alt="Kotlin"></a>
    <a href="https://central.sonatype.com/namespace/io.github.kverify"><img src="https://img.shields.io/maven-central/v/io.github.kverify/kverify-core" alt="Maven Central"></a>
    <a href="LICENSE"><img src="https://img.shields.io/badge/license-Apache%20License%202.0-blue.svg?style=flat" alt="GitHub license"></a>
</p>

---

KVerify is a Kotlin Multiplatform library with a DSL for validating data.

```kotlin
import io.github.kverify.core.forProperty
import io.github.kverify.core.validateCollecting
import io.github.kverify.ruleset.atLeast
import io.github.kverify.ruleset.notBlank

data class User(
    val name: String,
    val age: Int,
)

fun main() {
    val user = User(name = " ", age = 15)

    val violations =
        validateCollecting {
            forProperty(user::name).notBlank()
            forProperty(user::age).atLeast(18)
        }

    violations.forEach(::println)
}
```

The code prints:

```text
NotBlankViolation(validationPath=[NamePathSegment(name)], reason=Must not be blank)
AtLeastViolation(min=18, validationPath=[NamePathSegment(age)], reason=Must be at least 18)
```

## Highlights

- **Multiplatform.** Runs on JVM, JS, Wasm and Native.
- **No annotations, no compiler plugin.** Your classes stay unchanged, and the library depends only on the Kotlin
  standard library.
- **Rules are extension functions.** Your rules need no base class and no registration.
- **Violations are typed.** Each kind of failure has its own type, and it can hold the data of the failure, not just a
  message.
- **Pluggable strategies.** A `ValidationStrategy` decides what happens to a violation. Two ship with the library, and
  you can implement your own.
- **Validation paths.** `forProperty` and `each` track the path automatically, and a violation can carry it:
  `"items", 1, "name"`.

## Built-in rules

A rule is an extension function on `ValueValidationScope<V>`.

| Receiver                                  | Rules                                                                                  |
|-------------------------------------------|----------------------------------------------------------------------------------------|
| `ValueValidationScope<Any?>`              | `notNull`                                                                              |
| `ValueValidationScope<CharSequence>`      | `notBlank`, `minLength`, `maxLength`, `lengthBetween`, `exactLength`, `matchesPattern` |
| `ValueValidationScope<Collection<*>>`     | `minSize`, `maxSize`, `sizeBetween`, `exactSize`, `distinct`                           |
| `ValueValidationScope<T : Comparable<T>>` | `atLeast`, `atMost`, `greaterThan`, `lessThan`, `between`                              |

Every rule takes an optional `reason` that replaces the default one.

## Installation

### Requirements

- Kotlin 2.3.21 or later.
- On the JVM, Java 8 or later.

### Gradle

Make sure that you have `mavenCentral()` in the list of repositories:

```kotlin
repositories {
    mavenCentral()
}
```

Add the library to dependencies:

```kotlin
dependencies {
    // The built-in rules and their violations. Includes kverify-core.
    implementation("io.github.kverify:kverify-rule-set:<VERSION>")

    // Only the engine, without the built-in rules. Not needed if you use kverify-rule-set.
    // implementation("io.github.kverify:kverify-core:<VERSION>")
}
```

Replace `<VERSION>` with one of the versions listed on the [releases page](https://github.com/KVerify/kverify/releases).

### Supported platforms

JVM, JS, Wasm (JS and WASI), and Native: iOS, macOS, tvOS, watchOS, Android Native (arm32, arm64, x86, x64), Linux (x64,
arm64) and Windows (mingwX64).

## Examples

### Validation paths

`forProperty` adds the name of the property to the path of the scope, and `each` adds the index of the element. A
violation created by a rule reports the path where it was found.

```kotlin
import io.github.kverify.core.ViolationWithPath
import io.github.kverify.core.each
import io.github.kverify.core.forProperty
import io.github.kverify.core.validateCollecting
import io.github.kverify.ruleset.greaterThan
import io.github.kverify.ruleset.notBlank

data class OrderItem(
    val name: String,
    val price: Double,
)

data class Order(
    val customer: String,
    val items: List<OrderItem>,
)

fun main() {
    val order =
        Order(
            customer = "",
            items = listOf(OrderItem("Tea", 3.5), OrderItem(" ", -1.0)),
        )

    val violations =
        validateCollecting {
            forProperty(order::customer).notBlank()
            forProperty(order::items).each {
                forProperty(value::name).notBlank()
                forProperty(value::price).greaterThan(0.0)
            }
        }

    violations
        .filterIsInstance<ViolationWithPath>()
        .forEach { println(it.validationPath) }
}
```

The code prints:

```text
[NamePathSegment(customer)]
[NamePathSegment(items), IndexPathSegment(1), NamePathSegment(name)]
[NamePathSegment(items), IndexPathSegment(1), NamePathSegment(price)]
```

### Collecting and throwing

A validation is written once, as an extension function on `ValidationScope`. `validateCollecting` runs it with a
`CollectingValidationStrategy` and returns the reported violations. `validateThrowing` runs it with a
`ThrowingValidationStrategy`, which throws a `ViolationException` with the first violation.

```kotlin
import io.github.kverify.core.ValidationScope
import io.github.kverify.core.ViolationException
import io.github.kverify.core.forProperty
import io.github.kverify.core.validateCollecting
import io.github.kverify.core.validateThrowing
import io.github.kverify.ruleset.atLeast
import io.github.kverify.ruleset.lengthBetween
import io.github.kverify.ruleset.notBlank

data class User(
    val name: String,
    val age: Int,
)

fun ValidationScope.validate(user: User) {
    forProperty(user::name).run {
        notBlank()
        lengthBetween(3, 20)
    }
    forProperty(user::age).atLeast(18)
}

fun main() {
    val user = User(name = "", age = 15)

    val violations = validateCollecting { validate(user) }
    println(violations.size)

    try {
        validateThrowing { validate(user) }
    } catch (exception: ViolationException) {
        println(exception.violation.reason)
    }
}
```

The code prints:

```text
3
Must not be blank
```

### Custom rules

A custom rule is an extension function that calls `failIf`/`failWith`. It is used like a built-in rule.

```kotlin
import io.github.kverify.core.ValueValidationScope
import io.github.kverify.core.ViolationReason
import io.github.kverify.core.failIf
import io.github.kverify.core.forProperty
import io.github.kverify.core.validateCollecting

fun ValueValidationScope<String>.noSpaces() {
    failIf(value.contains(' ')) {
        ViolationReason("Must not contain spaces")
    }
}

data class Account(
    val username: String,
)

fun main() {
    val account = Account(username = "john doe")

    val violations =
        validateCollecting {
            forProperty(account::username).noSpaces()
        }

    violations.forEach { println(it.reason) }
}
```

The code prints:

```text
Must not contain spaces
```

`ViolationReason` holds only a message, with no path. To report the path, write a violation class of your own:

```kotlin
import io.github.kverify.core.ValidationPathSegment
import io.github.kverify.core.ValueValidationScope
import io.github.kverify.core.ViolationWithPath
import io.github.kverify.core.failIf
import io.github.kverify.core.validationPath

data class NoSpacesViolation(
    override val reason: String,
    override val validationPath: List<ValidationPathSegment>,
) : ViolationWithPath

fun ValueValidationScope<String>.noSpaces() {
    failIf(value.contains(' ')) { 
        NoSpacesViolation(
            reason = "Must not contain spaces", 
            validationPath = validationContext.validationPath(),
        )
    }
}
```

## Documentation

The [wiki](https://github.com/KVerify/kverify/wiki) has installation details, guides, an explanation of how the library
works, and a reference of the built-in rules and violations.

## License

KVerify is licensed under the [Apache License 2.0](LICENSE).
