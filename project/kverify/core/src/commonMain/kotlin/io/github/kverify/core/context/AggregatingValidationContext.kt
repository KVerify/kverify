@file:Suppress("TooManyFunctions")

package io.github.kverify.core.context

import io.github.kverify.core.exception.ValidationException
import io.github.kverify.core.model.ValidationResult
import io.github.kverify.core.model.fold
import io.github.kverify.core.rule.Rule
import io.github.kverify.core.violation.Violation
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

public open class AggregatingValidationContext(
    protected val violationsStorage: MutableCollection<Violation>,
) : ValidationContext {
    override fun onFailure(violation: Violation) {
        violationsStorage.add(violation)
    }

    public open fun build(): ValidationResult =
        ValidationResult(
            violationsStorage.toList(),
        )
}

// ============================================================
// Validate using AggregatingValidationContext with ArrayList as violationStorage
// ============================================================
@OptIn(ExperimentalContracts::class)
public inline fun validateAll(block: AggregatingValidationContext.() -> Unit): ValidationResult {
    contract {
        callsInPlace(block, InvocationKind.EXACTLY_ONCE)
    }

    return validateAllTo(ArrayList(), block)
}

@OptIn(ExperimentalContracts::class)
public inline fun <T> runValidatingAll(block: AggregatingValidationContext.() -> T): Result<T> {
    contract {
        callsInPlace(block, InvocationKind.EXACTLY_ONCE)
    }

    return runValidatingAllTo(ArrayList(), block)
}

public infix fun <T> T.validateAllWithRules(rulesIterator: Iterator<Rule<T>>): ValidationResult =
    this.validateAllWithRulesTo(
        destination = ArrayList(),
        rulesIterator = rulesIterator,
    )

public infix fun <T> T.validateAllWithRules(rules: Iterable<Rule<T>>): ValidationResult =
    this.validateAllWithRulesTo(
        destination = ArrayList(),
        rules = rules,
    )

public fun <T> T.validateAllWithRules(vararg rules: Rule<T>): ValidationResult =
    this.validateAllWithRulesTo(
        ArrayList(),
        rules = rules,
    )

// ============================================================
// Validate using AggregatingValidationContext with provided destination as violationStorage
// ============================================================
@OptIn(ExperimentalContracts::class)
public inline fun validateAllTo(
    destination: MutableCollection<Violation>,
    block: AggregatingValidationContext.() -> Unit,
): ValidationResult {
    contract {
        callsInPlace(block, InvocationKind.EXACTLY_ONCE)
    }

    return validateAllUsing(AggregatingValidationContext(destination), block)
}

@OptIn(ExperimentalContracts::class)
public inline fun <T> runValidatingAllTo(
    destination: MutableCollection<Violation>,
    block: AggregatingValidationContext.() -> T,
): Result<T> {
    contract {
        callsInPlace(block, InvocationKind.EXACTLY_ONCE)
    }

    return runValidatingAllUsing(AggregatingValidationContext(destination), block)
}

public fun <T> T.validateAllWithRulesTo(
    destination: MutableCollection<Violation>,
    rulesIterator: Iterator<Rule<T>>,
): ValidationResult =
    this.validateAllWithRulesUsing(
        AggregatingValidationContext(destination),
        rulesIterator = rulesIterator,
    )

public fun <T> T.validateAllWithRulesTo(
    destination: MutableCollection<Violation>,
    rules: Iterable<Rule<T>>,
): ValidationResult =
    this.validateAllWithRulesUsing(
        AggregatingValidationContext(destination),
        rules = rules,
    )

public fun <T> T.validateAllWithRulesTo(
    destination: MutableCollection<Violation>,
    vararg rules: Rule<T>,
): ValidationResult =
    this.validateAllWithRulesUsing(
        AggregatingValidationContext(destination),
        rules = rules,
    )

// ============================================================
// Validate using provided context
// ============================================================
@OptIn(ExperimentalContracts::class)
public inline fun <C : AggregatingValidationContext> validateAllUsing(
    context: C,
    block: C.() -> Unit,
): ValidationResult {
    contract {
        callsInPlace(block, InvocationKind.EXACTLY_ONCE)
    }

    return context.apply(block).build()
}

@OptIn(ExperimentalContracts::class)
public inline fun <T, C : AggregatingValidationContext> runValidatingAllUsing(
    context: C,
    block: C.() -> T,
): Result<T> {
    contract {
        callsInPlace(block, InvocationKind.EXACTLY_ONCE)
    }

    val result = context.run(block)

    return context.build().fold(
        ifValid = { Result.success(result) },
        ifInvalid = {
            val exception = ValidationException(it)
            Result.failure(exception)
        },
    )
}

public fun <T, C : AggregatingValidationContext> T.validateAllWithRulesUsing(
    context: C,
    rulesIterator: Iterator<Rule<T>>,
): ValidationResult {
    val value = this

    return validateAllUsing(context) {
        runRules(
            value = value,
            rulesIterator = rulesIterator,
        )
    }
}

public fun <T, C : AggregatingValidationContext> T.validateAllWithRulesUsing(
    context: C,
    rules: Iterable<Rule<T>>,
): ValidationResult {
    val value = this

    return validateAllUsing(context) { value applyRules rules }
}

public fun <T, C : AggregatingValidationContext> T.validateAllWithRulesUsing(
    context: C,
    vararg rules: Rule<T>,
): ValidationResult {
    val value = this

    return validateAllUsing(context) {
        value.applyRules(rules = rules)
    }
}
