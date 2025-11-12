package io.github.kverify.core.context

import io.github.kverify.core.model.ValidationResult
import io.github.kverify.core.rule.Rule
import io.github.kverify.core.violation.Violation
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

@PublishedApi
internal open class FirstViolationValidationContext : ValidationContext {
    var firstViolation: Violation? = null
        protected set

    override fun onFailure(violation: Violation) {
        if (firstViolation == null) firstViolation = violation
    }

    override fun <T> runRules(
        value: T,
        rulesIterator: Iterator<Rule<T>>,
    ): T {
        for (rule in rulesIterator) {
            rule.run(
                context = this,
                value = value,
            )

            if (firstViolation != null) break
        }

        return value
    }
}

@PublishedApi
internal inline fun FirstViolationValidationContext(init: FirstViolationValidationContext.() -> Unit): FirstViolationValidationContext {
    contract {
        callsInPlace(init, InvocationKind.EXACTLY_ONCE)
    }

    return FirstViolationValidationContext().apply(init)
}

public inline fun validateFirst(block: ValidationContext.() -> Unit): ValidationResult {
    contract {
        callsInPlace(block, InvocationKind.EXACTLY_ONCE)
    }

    val firstViolation = FirstViolationValidationContext(block).firstViolation

    return if (firstViolation == null) {
        ValidationResult.Valid
    } else {
        ValidationResult.Invalid(listOf(firstViolation))
    }
}

public infix fun <T> T.validateFirstWithRule(rule: Rule<T>): ValidationResult {
    val value = this

    return validateFirst { value applyRule rule }
}

public infix fun <T> T.validateFirstWithRules(rules: Iterable<Rule<T>>): ValidationResult {
    val value = this

    return validateFirst { value applyRules rules }
}

public fun <T> T.validateFirstWithRules(vararg rules: Rule<T>): ValidationResult {
    val value = this

    return validateFirst { value.applyRules(rules = rules) }
}

public infix fun <T> T.validateFirstWithRules(rulesIterator: Iterator<Rule<T>>): ValidationResult {
    val value = this

    return validateFirst {
        runRules(
            value = value,
            rulesIterator = rulesIterator,
        )
    }
}

public infix fun <T> T.satisfies(rule: Rule<T>): Boolean {
    val value = this

    return FirstViolationValidationContext { value applyRule rule }.firstViolation == null
}

public infix fun <T> T.satisfies(rules: Iterable<Rule<T>>): Boolean {
    val value = this

    return FirstViolationValidationContext { value applyRules rules }.firstViolation == null
}

public fun <T> T.satisfies(vararg rules: Rule<T>): Boolean {
    val value = this

    return FirstViolationValidationContext { value.applyRules(rules = rules) }.firstViolation == null
}

public fun <T> T.satisfies(rulesIterator: Iterator<Rule<T>>): Boolean {
    val value = this

    return FirstViolationValidationContext {
        runRules(
            value = value,
            rulesIterator = rulesIterator,
        )
    }.firstViolation == null
}
