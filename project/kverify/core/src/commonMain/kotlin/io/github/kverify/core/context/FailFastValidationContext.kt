package io.github.kverify.core.context

import io.github.kverify.core.model.ValidationResult
import io.github.kverify.core.rule.Rule
import io.github.kverify.core.violation.Violation

internal object AbortValidationException : RuntimeException()

internal class FailFastValidationContext : ThrowingValidationContext {
    lateinit var firstViolation: Violation
        private set

    override fun onFailure(violation: Violation): Nothing {
        firstViolation = violation

        throw AbortValidationException
    }
}

public fun validateFailFast(block: ThrowingValidationContext.() -> Unit): ValidationResult {
    val context = FailFastValidationContext()

    return try {
        context.block()

        ValidationResult.Valid
    } catch (_: AbortValidationException) {
        ValidationResult.Invalid(
            violations = listOf(context.firstViolation),
        )
    }
}

public infix fun <T> T.validateFailFastWithRule(rule: Rule<T>): ValidationResult {
    val value = this

    return validateFailFast { value applyRule rule }
}

public infix fun <T> T.validateFailFastWithRules(rules: Iterable<Rule<T>>): ValidationResult {
    val value = this

    return validateFailFast { value applyRules rules }
}

public fun <T> T.validateFailFastWithRules(vararg rules: Rule<T>): ValidationResult {
    val value = this

    return validateFailFast { value.applyRules(rules = rules) }
}

public infix fun <T> T.validateFailFastWithRules(rulesIterator: Iterator<Rule<T>>): ValidationResult {
    val value = this

    return validateFailFast {
        runRules(
            value = value,
            rulesIterator = rulesIterator,
        )
    }
}

public infix fun <T> T.satisfies(rule: Rule<T>): Boolean {
    val value = this
    val context = FailFastValidationContext()

    return try {
        rule.run(
            context = context,
            value = value,
        )

        true
    } catch (_: AbortValidationException) {
        false
    }
}

public fun <T> T.satisfies(rulesIterator: Iterator<Rule<T>>): Boolean {
    val value = this
    val context = FailFastValidationContext()

    return try {
        context.runRules(
            value = value,
            rulesIterator = rulesIterator,
        )

        true
    } catch (_: AbortValidationException) {
        false
    }
}

public fun <T> T.satisfies(rules: Iterable<Rule<T>>): Boolean = satisfies(rulesIterator = rules.iterator())

public fun <T> T.satisfies(vararg rules: Rule<T>): Boolean = satisfies(rulesIterator = rules.iterator())

public infix fun <T> T.notSatisfies(rule: Rule<T>): Boolean = !satisfies(rule = rule)

public fun <T> T.notSatisfies(rulesIterator: Iterator<Rule<T>>): Boolean = !satisfies(rulesIterator = rulesIterator)

public fun <T> T.notSatisfies(rules: Iterable<Rule<T>>): Boolean = !satisfies(rules = rules)

public fun <T> T.notSatisfies(vararg rules: Rule<T>): Boolean = !satisfies(rules = rules)
