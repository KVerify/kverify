package io.github.kverify.core.context

import io.github.kverify.core.model.ValidationResult
import io.github.kverify.core.rule.Rule
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
