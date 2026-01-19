@file:Suppress("TooManyFunctions")

package io.github.kverify.core.scope

import io.github.kverify.core.annotation.KverifyDsl
import io.github.kverify.core.context.EmptyValidationContext
import io.github.kverify.core.context.ValidationContext
import io.github.kverify.core.rule.Rule
import io.github.kverify.core.violation.Violation

@KverifyDsl
public interface ValidationScope {
    public val validationContext: ValidationContext

    public fun onFailure(violation: Violation)

    public infix fun <T> T.verifyWith(rule: Rule<T>): T {
        val scope = this@ValidationScope
        val value = this@verifyWith

        rule.execute(
            scope = scope,
            value = value,
        )

        return value
    }

    public infix fun <T> T.verifyWith(rules: Iterable<Rule<T>>): T {
        val value = this@verifyWith
        val scope = this@ValidationScope

        for (rule in rules) {
            rule.execute(
                scope = scope,
                value = value,
            )
        }

        return value
    }

    public fun <T> T.verifyWith(vararg rules: Rule<T>): T =
        this.verifyWith(
            rules = rules.asIterable(),
        )
}

public operator fun ValidationScope.plus(other: ValidationScope): ValidationScopeList =
    when {
        this is ValidationScopeList && other is ValidationScopeList -> ValidationScopeList(validationScopes + other.validationScopes)
        this is ValidationScopeList -> ValidationScopeList(validationScopes + other)
        other is ValidationScopeList -> ValidationScopeList(listOf(this) + other.validationScopes)
        else -> ValidationScopeList(listOf(this, other))
    }

@Suppress("NOTHING_TO_INLINE")
public inline fun <T> ValidationScope.verify(
    value: T,
    rule: Rule<T>,
): T = value verifyWith rule

@Suppress("NOTHING_TO_INLINE")
public inline fun <T> ValidationScope.verify(
    value: T,
    rules: Iterable<Rule<T>>,
): T = value verifyWith rules

@Suppress("NOTHING_TO_INLINE")
public inline fun <T> ValidationScope.verify(
    value: T,
    vararg rules: Rule<T>,
): T = value.verifyWith(rules = rules)

public inline fun ValidationScope.failIf(
    condition: Boolean,
    lazyViolation: () -> Violation,
) {
    if (condition) {
        val violation = lazyViolation()
        onFailure(violation)
    }
}

public inline fun ValidationScope.failIfNot(
    condition: Boolean,
    lazyViolation: () -> Violation,
): Unit =
    failIf(
        condition = !condition,
        lazyViolation = lazyViolation,
    )
