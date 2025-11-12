package io.github.kverify.core.context

import io.github.kverify.core.model.ValidationResult
import io.github.kverify.core.rule.Rule
import io.github.kverify.core.violation.Violation

@PublishedApi
internal object AbortValidationException : RuntimeException()

@PublishedApi
internal class FailFastValidationContext : ThrowingValidationContext {
    lateinit var firstViolation: Violation
        private set

    override fun onFailure(violation: Violation): Nothing {
        firstViolation = violation

        throw AbortValidationException
    }
}

@PublishedApi
internal inline fun <T> runFailFast(
    block: ThrowingValidationContext.() -> T,
    onThrow: (Violation) -> T,
): T {
    val context = FailFastValidationContext()

    return try {
        context.block()
    } catch (_: AbortValidationException) {
        onThrow(context.firstViolation)
    }
}

public fun validateFailFast(block: ThrowingValidationContext.() -> Unit): ValidationResult =
    runFailFast(
        block = {
            block()
            ValidationResult.Valid
        },
        onThrow = {
            ValidationResult.Invalid(listOf(it))
        },
    )

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

private inline fun failFastThrows(block: ThrowingValidationContext.() -> Unit): Boolean =
    runFailFast(
        block = {
            block()
            false
        },
        onThrow = { true },
    )

public infix fun <T> T.satisfies(rule: Rule<T>): Boolean {
    val value = this

    return !failFastThrows { value applyRule rule }
}

public fun <T> T.satisfies(rules: Iterable<Rule<T>>): Boolean {
    val value = this

    return !failFastThrows { value applyRules rules }
}

public fun <T> T.satisfies(vararg rules: Rule<T>): Boolean {
    val value = this

    return !failFastThrows { value.applyRules(rules = rules) }
}

public fun <T> T.satisfies(rulesIterator: Iterator<Rule<T>>): Boolean {
    val value = this

    return !failFastThrows {
        runRules(
            value = value,
            rulesIterator = rulesIterator,
        )
    }
}

public infix fun <T> T.notSatisfies(rule: Rule<T>): Boolean = !satisfies(rule = rule)

public fun <T> T.notSatisfies(rulesIterator: Iterator<Rule<T>>): Boolean = !satisfies(rulesIterator = rulesIterator)

public fun <T> T.notSatisfies(rules: Iterable<Rule<T>>): Boolean = !satisfies(rules = rules)

public fun <T> T.notSatisfies(vararg rules: Rule<T>): Boolean = !satisfies(rules = rules)
