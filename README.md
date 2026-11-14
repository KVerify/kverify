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


A rule is an extension function that reports a violation, and a violation is a class that carries the details of the
failure. What happens to a reported violation is decided by the `ValidationStrategy` that the validation runs with: the
library can collect violations or throw the first one, and you can implement any other behavior.

```kotlin
import io.github.kverify.core.scope.forProperty
import io.github.kverify.core.strategy.validateCollecting
import io.github.kverify.rule.set.atLeast
import io.github.kverify.rule.set.notBlank

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
NotBlankViolation(validationPath=ValidationPath("name"), reason=Must not be blank)
AtLeastViolation(min=18, validationPath=ValidationPath("age"), reason=Must be at least 18)
```

## Modules

| Module             | Description                                                                            |
|--------------------|----------------------------------------------------------------------------------------|
| `kverify-core`     | The validation engine: scopes, strategies, validation paths and the `Violation` types. |
| `kverify-rule-set` | The built-in rules and their violations. Depends on `kverify-core`.                    |

Your classes need no annotations, and the library does not use a compiler plugin. It depends only on the Kotlin
standard library.

## Built-in rules

A rule is an extension function on `ValueValidationScope<V>`. The built-in rules are declared the same way as the ones
you write yourself.

| Receiver                                  | Rules                                                                         |
|-------------------------------------------|-------------------------------------------------------------------------------|
| `ValueValidationScope<Any?>`              | `notNull`                                                                     |
| `ValueValidationScope<CharSequence>`      | `notBlank`, `minLength`, `maxLength`, `lengthRange`, `exactLength`, `matches` |
| `ValueValidationScope<Collection<*>>`     | `minSize`, `maxSize`, `sizeBetween`, `exactSize`, `distinct`                  |
| `ValueValidationScope<T : Comparable<T>>` | `atLeast`, `atMost`, `greaterThan`, `lessThan`, `between`                     |

Every rule takes an optional `reason` that replaces the default one.

## Using in your projects

### Requirements

* Kotlin 2.3.21 or later.
* On the JVM, Java 8 or later.

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
    implementation("io.github.kverify:kverify-rule-set:VERSION")
}
```

In multiplatform projects, add the dependency to the `commonMain` source set:

```kotlin
kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation("io.github.kverify:kverify-rule-set:VERSION")
        }
    }
}
```

To use only the engine, depend on `kverify-core` instead.

Replace `VERSION` with one of the versions listed on the [releases page](https://github.com/KVerify/kverify/releases).

### Maven

Maven projects depend on the JVM artifact:

```xml
<dependency>
    <groupId>io.github.kverify</groupId>
    <artifactId>kverify-rule-set-jvm</artifactId>
    <version>VERSION</version>
</dependency>
```

### Supported platforms

JVM, JS, Wasm (JS and WASI), and Native: iOS, macOS, tvOS, watchOS, Android Native (arm32, arm64, x86, x64), Linux (x64,
arm64) and Windows (mingwX64).

## Examples

### Validation paths

`forProperty` adds the name of the property to the path of the scope, and `each` adds the index of the element. A
violation created by a rule reports the path where it was found.

```kotlin
import io.github.kverify.core.scope.each
import io.github.kverify.core.scope.forProperty
import io.github.kverify.core.strategy.validateCollecting
import io.github.kverify.core.violation.PathAwareViolation
import io.github.kverify.rule.set.greaterThan
import io.github.kverify.rule.set.notBlank

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
        .filterIsInstance<PathAwareViolation>()
        .forEach { println(it.validationPath) }
}
```

The code prints:

```text
ValidationPath("customer")
ValidationPath("items", 1, "name")
ValidationPath("items", 1, "price")
```

### Collecting and throwing

A validation is written once, as an extension function on `ValidationScope`. `validateCollecting` runs it with a
`CollectingValidationStrategy` and returns the reported violations. `validateThrowing` runs it with a
`ThrowingValidationStrategy`, which throws a `ViolationException` with the first violation.

```kotlin
import io.github.kverify.core.exception.ViolationException
import io.github.kverify.core.scope.ValidationScope
import io.github.kverify.core.scope.forProperty
import io.github.kverify.core.strategy.validateCollecting
import io.github.kverify.core.strategy.validateThrowing
import io.github.kverify.rule.set.atLeast
import io.github.kverify.rule.set.lengthRange
import io.github.kverify.rule.set.notBlank

data class User(
    val name: String,
    val age: Int,
)

fun ValidationScope.validate(user: User) {
    forProperty(user::name).run {
        notBlank()
        lengthRange(3, 20)
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

### Custom strategies

A strategy is a class that implements `ValidationStrategy`. Its `failWith` function is called for each violation that a
rule reports. If the function returns, the validation continues with the next rule.

To run a validation with a strategy, create a `ValidationScope` with it and a validation context.
`EmptyValidationContext` is a context that holds no elements.

```kotlin
import io.github.kverify.core.context.EmptyValidationContext
import io.github.kverify.core.scope.ValidationScope
import io.github.kverify.core.strategy.ValidationStrategy
import io.github.kverify.core.violation.Violation
import io.github.kverify.rule.set.atLeast
import io.github.kverify.rule.set.notBlank

class PrintingValidationStrategy : ValidationStrategy {
    override fun failWith(violation: Violation) {
        println("Invalid: ${violation.reason}")
    }
}

fun main() {
    val scope = ValidationScope(PrintingValidationStrategy(), EmptyValidationContext)

    scope.run {
        forValue("").notBlank()
        forValue(15).atLeast(18)
    }
}
```

The code prints:

```text
Invalid: Must not be blank
Invalid: Must be at least 18
```

### Custom rules and violations

A rule calls `failIf` with a condition and a function that creates the violation. The function is called only when the
condition is `true`. A violation that has nothing to report but a message does not need its own class: `ViolationReason`
is a `Violation` that holds only a `reason`.

`takeIfValueNotNull` skips a nullable value that is `null` and narrows the type otherwise.

```kotlin
import io.github.kverify.core.context.validationPath
import io.github.kverify.core.model.ValidationPath
import io.github.kverify.core.scope.ValueValidationScope
import io.github.kverify.core.scope.failIf
import io.github.kverify.core.scope.forProperty
import io.github.kverify.core.scope.takeIfValueNotNull
import io.github.kverify.core.strategy.validateCollecting
import io.github.kverify.core.violation.PathAwareViolation
import io.github.kverify.rule.set.minSize

class InvalidCouponViolation(
    val coupon: String,
    override val validationPath: ValidationPath,
    override val reason: String,
) : PathAwareViolation

private val couponRegex = Regex("[A-Z]{4}-\\d{4}")

fun ValueValidationScope<String>.validCoupon() {
    failIf(!couponRegex.matches(value)) {
        InvalidCouponViolation(
            coupon = value,
            validationPath = validationContext.validationPath(),
            reason = "Must look like ABCD-1234",
        )
    }
}

data class Cart(
    val coupon: String?,
    val items: List<String>,
)

fun main() {
    val cart = Cart(coupon = "abc", items = emptyList())

    val violations =
        validateCollecting {
            forProperty(cart::items).minSize(1)
            forProperty(cart::coupon).takeIfValueNotNull()?.validCoupon()
        }

    violations
        .filterIsInstance<InvalidCouponViolation>()
        .forEach { println("${it.validationPath}: ${it.coupon} - ${it.reason}") }
}
```

The code prints:

```text
ValidationPath("coupon"): abc - Must look like ABCD-1234
```

## License

KVerify is licensed under the [Apache License 2.0](LICENSE).
