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

@Suppress("UnusedReceiverParameter", "NOTHING_TO_INLINE")
public inline fun <T> T.validateAll(): ValidationResult.Valid = ValidationResult.Valid

public infix fun <T> T.validateAll(rule: Rule<T>): ValidationResult =
    this.validateAllTo(
        destination = ArrayList(),
        rule = rule,
    )

public fun <T> T.validateAll(vararg rules: Rule<T>): ValidationResult =
    this.validateAllTo(
        destination = ArrayList(),
        rules = rules,
    )

public infix fun <T> T.validateAll(rules: Iterable<Rule<T>>): ValidationResult =
    this.validateAllTo(
        destination = ArrayList(),
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

    return validateAllUsing(
        context = AggregatingValidationContext(destination),
        block,
    )
}

@OptIn(ExperimentalContracts::class)
public inline fun <T> runValidatingAllTo(
    destination: MutableCollection<Violation>,
    block: AggregatingValidationContext.() -> T,
): Result<T> {
    contract {
        callsInPlace(block, InvocationKind.EXACTLY_ONCE)
    }

    return runValidatingAllUsing(context = AggregatingValidationContext(destination), block)
}

@Suppress("UnusedParameter", "UnusedReceiverParameter", "NOTHING_TO_INLINE")
public inline fun <T> T.validateAllTo(destination: MutableCollection<Violation>): ValidationResult.Valid = ValidationResult.Valid

public fun <T> T.validateAllTo(
    destination: MutableCollection<Violation>,
    rule: Rule<T>,
): ValidationResult =
    this.validateAllUsing(
        context = AggregatingValidationContext(destination),
        rule = rule,
    )

public fun <T> T.validateAllTo(
    destination: MutableCollection<Violation>,
    vararg rules: Rule<T>,
): ValidationResult =
    this.validateAllUsing(
        context = AggregatingValidationContext(destination),
        rules = rules,
    )

public fun <T> T.validateAllTo(
    destination: MutableCollection<Violation>,
    rules: Iterable<Rule<T>>,
): ValidationResult =
    this.validateAllUsing(
        context = AggregatingValidationContext(destination),
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

@Suppress("UnusedReceiverParameter")
public fun <T, C : AggregatingValidationContext> T.validateAllUsing(context: C): ValidationResult = context.build()

public fun <T, C : AggregatingValidationContext> T.validateAllUsing(
    context: C,
    rule: Rule<T>,
): ValidationResult {
    val value = this

    return validateAllUsing(context) { value verifyWith rule }
}

public fun <T, C : AggregatingValidationContext> T.validateAllUsing(
    context: C,
    vararg rules: Rule<T>,
): ValidationResult {
    val value = this

    return validateAllUsing(context) {
        value.verifyWith(rules = rules)
    }
}

public fun <T, C : AggregatingValidationContext> T.validateAllUsing(
    context: C,
    rules: Iterable<Rule<T>>,
): ValidationResult {
    val value = this

    return validateAllUsing(context) { value verifyWith rules }
}
