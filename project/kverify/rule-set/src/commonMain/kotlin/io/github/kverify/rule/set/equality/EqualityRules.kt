package io.github.kverify.rule.set.equality

import io.github.kverify.core.context.validationPath
import io.github.kverify.core.scope.ValueVerificationScope
import io.github.kverify.core.strategy.failIf

public fun <T> ValueVerificationScope<T, *>.notNull(reason: String? = null) {
    validationStrategy.failIf(value == null) {
        NotNullViolation(
            validationPath = validationContext.validationPath(),
            reason = reason ?: "Value must not be null",
        )
    }
}

public fun <T> ValueVerificationScope<T, *>.equalTo(
    expected: T,
    reason: String? = null,
) {
    validationStrategy.failIf(value != expected) {
        EqualToViolation(
            expected = expected,
            actual = value,
            validationPath = validationContext.validationPath(),
            reason = reason ?: "Value must be equal to $expected. Actual: $value",
        )
    }
}

public fun <T> ValueVerificationScope<T, *>.notEqualTo(
    forbidden: T,
    reason: String? = null,
) {
    validationStrategy.failIf(value == forbidden) {
        NotEqualToViolation(
            forbidden = forbidden,
            validationPath = validationContext.validationPath(),
            reason = reason ?: "Value must not be equal to $forbidden",
        )
    }
}

public fun <T> ValueVerificationScope<T, *>.oneOf(
    allowed: Set<T>,
    reason: String? = null,
) {
    validationStrategy.failIf(value !in allowed) {
        OneOfViolation(
            allowed = allowed,
            actual = value,
            validationPath = validationContext.validationPath(),
            reason = reason ?: "Value must be one of $allowed. Actual: $value",
        )
    }
}

public fun <T> ValueVerificationScope<T, *>.noneOf(
    forbidden: Set<T>,
    reason: String? = null,
) {
    validationStrategy.failIf(value in forbidden) {
        NoneOfViolation(
            forbidden = forbidden,
            actual = value,
            validationPath = validationContext.validationPath(),
            reason = reason ?: "Value must not be one of $forbidden. Actual: $value",
        )
    }
}
