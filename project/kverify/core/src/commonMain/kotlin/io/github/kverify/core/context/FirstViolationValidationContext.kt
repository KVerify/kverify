package io.github.kverify.core.context

import io.github.kverify.core.model.ValidationResult
import io.github.kverify.core.rule.Rule
import io.github.kverify.core.rule.runValidation
import io.github.kverify.core.violation.Violation

internal object AbortValidationException : RuntimeException()

internal class FirstViolationValidationContext : ThrowingValidationContext() {
    lateinit var firstViolation: Violation
        private set

    override fun onFailure(violation: Violation): Nothing {
        firstViolation = violation

        throw AbortValidationException
    }
}

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

public infix fun <T> T.validateFirstWithRule(rule: Rule<T>): ValidationResult {
    val value = this

    val result = validateFirst { value applyRule rule }

    return result
}

public fun <T> T.validateFirstWithRules(vararg rules: Rule<T>): ValidationResult {
    val value = this

    val result = validateFirst { value.applyRules(rules = rules) }

    return result
}

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
