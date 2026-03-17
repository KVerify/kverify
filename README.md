# Getting Started

This page walks you through your first validation with KVerify. If you haven't added the dependency yet,
see [Installation](Installation).

## Your first validation

KVerify validations are built around three ideas: you create a scope, verify values inside it, and get a result back.

```kotlin
data class User(
    val name: String,
    val email: String,
    val age: Int,
)

val user = User(name = "", email = "bob@example.com", age = 17)

val result = validateCollecting {
    verify(user::name).notBlank().minLength(3)
    verify(user::email).notBlank()
    verify(user::age).atLeast(18, reason = "User must be at least 18 years old")
}
```

`validateCollecting` runs every rule regardless of failures — it never stops early. This makes it ideal for forms, APIs,
and DTOs where you want to report all problems at once.

Every built-in rule accepts an optional `reason` parameter. When omitted, a sensible default is used — but supplying
your own message is encouraged wherever the context makes a more precise explanation valuable.

`result` is a `ValidationResult`. You can inspect it like this:

```kotlin
result.fold(
    onValid = { println("All good!") },
    onInvalid = { violations ->
        violations.forEach { println(it.reason) }
    }
)
```

For `user` above, this would print:

```
Value must not be blank
User must be at least 18 years old
```

## Using property references

Notice that `verify` is called with `user::name` rather than `user.name`. This is the recommended approach — passing a
property reference lets KVerify automatically include the property name in any violations it produces, which makes
failure messages much easier to trace back to the source.

If you don't have a property reference available, `verify(value)` works too, but violations will not carry path
information automatically.

## Throwing on the first failure

If you'd rather stop at the first violation instead of collecting all of them, use `validateThrowing`:

```kotlin
validateThrowing {
    verify(order::total).atLeast(minimumAmount)
    verify(order::items).minSize(1)
}
```

The first rule that fails throws a `ViolationException` immediately. This is a good fit for business logic and use cases
where a single violation means the operation cannot proceed.

Choosing between the two is covered in depth on the [ValidationScope](ValidationScope) page.

## Next steps

- [ValidationScope](ValidationScope) — understand the entry point for all validations
- [Built-in Rules](Built-in-Rules) — full reference for the rules used above