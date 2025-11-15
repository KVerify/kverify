package io.github.kverify.core.context

import io.github.kverify.core.model.ValidationResult
import io.github.kverify.core.rule.Rule
import io.github.kverify.core.violation.Violation
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

public interface FirstViolationValidationContext : ValidationContext {
    public val firstViolation: Violation?

    override fun <T> T.applyRule(rule: Rule<T>): T {
        val value = this
        val context = this@FirstViolationValidationContext

        if (firstViolation != null) return value

        rule.execute(
            context = context,
            value = value,
        )

        return this
    }

    override fun <T> runRules(
        value: T,
        rulesIterator: Iterator<Rule<T>>,
    ): T {
        if (firstViolation != null) return value

        for (rule in rulesIterator) {
            rule.execute(
                context = this,
                value = value,
            )

            if (firstViolation != null) break
        }

        return value
    }
}

@PublishedApi
internal class FirstViolationValidationContextImpl : FirstViolationValidationContext {
    override var firstViolation: Violation? = null
        private set

    override fun onFailure(violation: Violation) {
        if (firstViolation == null) firstViolation = violation
    }
}

@PublishedApi
internal inline fun initFirstViolationValidationContext(
    init: FirstViolationValidationContextImpl.() -> Unit,
): FirstViolationValidationContextImpl {
    contract {
        callsInPlace(init, InvocationKind.EXACTLY_ONCE)
    }

    return FirstViolationValidationContextImpl().apply(init)
}

public inline fun validateFirst(block: FirstViolationValidationContext.() -> Unit): ValidationResult {
    contract {
        callsInPlace(block, InvocationKind.EXACTLY_ONCE)
    }

    val firstViolation = initFirstViolationValidationContext(block).firstViolation

    return if (firstViolation == null) {
        ValidationResult.Valid
    } else {
        ValidationResult.Invalid(firstViolation)
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

    return initFirstViolationValidationContext { value applyRule rule }.firstViolation == null
}

public infix fun <T> T.satisfies(rules: Iterable<Rule<T>>): Boolean {
    val value = this

    return initFirstViolationValidationContext { value applyRules rules }.firstViolation == null
}

public fun <T> T.satisfies(vararg rules: Rule<T>): Boolean {
    val value = this

    return initFirstViolationValidationContext { value.applyRules(rules = rules) }.firstViolation == null
}

public fun <T> T.satisfies(rulesIterator: Iterator<Rule<T>>): Boolean {
    val value = this

    return initFirstViolationValidationContext {
        runRules(
            value = value,
            rulesIterator = rulesIterator,
        )
    }.firstViolation == null
}
