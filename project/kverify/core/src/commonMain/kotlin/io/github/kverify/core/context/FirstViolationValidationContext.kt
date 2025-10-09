package io.github.kverify.core.context

import io.github.kverify.core.model.ValidationResult
import io.github.kverify.core.rule.Rule
import io.github.kverify.core.rule.runValidation
import io.github.kverify.core.violation.Violation

internal object AbortValidationException : RuntimeException()

/**
 * Internal context that records the first [Violation] and aborts validation by throwing.
 */
internal class FirstViolationValidationContext : ThrowingValidationContext() {
    /**
     * The first reported violation.
     */
    lateinit var firstViolation: Violation
        private set

    override fun onFailure(violation: Violation): Nothing {
        firstViolation = violation

        throw AbortValidationException
    }
}

/**
 * Runs [block] and returns a [ValidationResult]
 * containing only the first violation (if any).
 */
public fun validateFirst(block: ThrowingValidationContext.() -> Unit): ValidationResult {
    val context = FirstViolationValidationContext()

    return try {
        context.apply(block)

        ValidationResult.Valid
    } catch (_: AbortValidationException) {
        ValidationResult.Invalid(
            violations = listOf(context.firstViolation),
        )
    }
}

/**
 * Validates `this` value with a single [rule] and returns a [ValidationResult]
 * containing only the first violation (if any).
 */
public infix fun <T> T.validateFirstWithRule(rule: Rule<T>): ValidationResult {
    val value = this

    val result = validateFirst { value applyRule rule }

    return result
}

/**
 * Validates `this` value with multiple [rules] and returns a [ValidationResult]
 * containing only the first violation (if any).
 */
public fun <T> T.validateFirstWithRules(vararg rules: Rule<T>): ValidationResult {
    val value = this

    val result = validateFirst { value.applyRules(rules = rules) }

    return result
}

/**
 * Returns `true` if `this` value satisfies the given [rule], stopping at the first failure.
 */
public infix fun <T> T.satisfies(rule: Rule<T>): Boolean {
    val value = this
    val context = FirstViolationValidationContext()

    return try {
        rule.runValidation(
            context = context,
            value = value,
        )

        true
    } catch (_: AbortValidationException) {
        false
    }
}

/**
 * Returns `true` if `this` value satisfies all provided [rules], stopping at the first failure.
 */
public fun <T> T.satisfies(vararg rules: Rule<T>): Boolean {
    val value = this
    val context = FirstViolationValidationContext()

    return try {
        rules.forEach { rule ->
            rule.runValidation(
                context = context,
                value = value,
            )
        }

        true
    } catch (_: AbortValidationException) {
        false
    }
}

/**
 * Returns `true` if `this` value does not satisfy the given [rule], stopping at the first failure.
 */
public fun <T> T.notSatisfies(rule: Rule<T>): Boolean = !satisfies(rule)

/**
 * Returns `true` if `this` value does not satisfy all provided [rules], stopping at the first failure.
 */
public fun <T> T.notSatisfies(vararg rules: Rule<T>): Boolean = !satisfies(rules = rules)
