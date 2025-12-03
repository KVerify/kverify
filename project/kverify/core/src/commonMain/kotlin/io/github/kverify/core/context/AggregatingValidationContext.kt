@file:Suppress("TooManyFunctions")

package io.github.kverify.core.context

import io.github.kverify.core.model.ValidationResult
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
public inline fun verifyAll(block: AggregatingValidationContext.() -> Unit): ValidationResult {
    contract {
        callsInPlace(block, InvocationKind.EXACTLY_ONCE)
    }

    return verifyAllTo(ArrayList(), block)
}

@Suppress("UnusedReceiverParameter", "NOTHING_TO_INLINE")
public inline fun <T> T.verifyWithAll(): ValidationResult.Valid = ValidationResult.Valid

@Suppress("NOTHING_TO_INLINE")
public inline infix fun <T> T.verifyWithAll(rule: Rule<T>): ValidationResult =
    this.verifyWithAllTo(
        destination = ArrayList(),
        rule = rule,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun <T> T.verifyWithAll(vararg rules: Rule<T>): ValidationResult =
    this.verifyWithAllTo(
        destination = ArrayList(),
        rules = rules,
    )

@Suppress("NOTHING_TO_INLINE")
public inline infix fun <T> T.verifyWithAll(rules: Iterable<Rule<T>>): ValidationResult =
    this.verifyWithAllTo(
        destination = ArrayList(),
        rules = rules,
    )

// ============================================================
// Validate using AggregatingValidationContext with provided destination as violationStorage
// ============================================================
@OptIn(ExperimentalContracts::class)
public inline fun verifyAllTo(
    destination: MutableCollection<Violation>,
    block: AggregatingValidationContext.() -> Unit,
): ValidationResult {
    contract {
        callsInPlace(block, InvocationKind.EXACTLY_ONCE)
    }

    return verifyAllUsing(
        context = AggregatingValidationContext(destination),
        block,
    )
}

@Suppress("UnusedParameter", "UnusedReceiverParameter", "NOTHING_TO_INLINE")
public inline fun <T> T.verifyWithAllTo(destination: MutableCollection<Violation>): ValidationResult.Valid = ValidationResult.Valid

@Suppress("NOTHING_TO_INLINE")
public inline fun <T> T.verifyWithAllTo(
    destination: MutableCollection<Violation>,
    rule: Rule<T>,
): ValidationResult =
    this.verifyWithAllUsing(
        context = AggregatingValidationContext(destination),
        rule = rule,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun <T> T.verifyWithAllTo(
    destination: MutableCollection<Violation>,
    vararg rules: Rule<T>,
): ValidationResult =
    this.verifyWithAllUsing(
        context = AggregatingValidationContext(destination),
        rules = rules,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun <T> T.verifyWithAllTo(
    destination: MutableCollection<Violation>,
    rules: Iterable<Rule<T>>,
): ValidationResult =
    this.verifyWithAllUsing(
        context = AggregatingValidationContext(destination),
        rules = rules,
    )

// ============================================================
// Validate using provided context
// ============================================================
@OptIn(ExperimentalContracts::class)
public inline fun <C : AggregatingValidationContext> verifyAllUsing(
    context: C,
    block: C.() -> Unit,
): ValidationResult {
    contract {
        callsInPlace(block, InvocationKind.EXACTLY_ONCE)
    }

    return context.apply(block).build()
}

@Suppress("UnusedReceiverParameter", "NOTHING_TO_INLINE")
public inline fun <T, C : AggregatingValidationContext> T.verifyWithAllUsing(context: C): ValidationResult = context.build()

@Suppress("NOTHING_TO_INLINE")
public inline fun <T, C : AggregatingValidationContext> T.verifyWithAllUsing(
    context: C,
    rule: Rule<T>,
): ValidationResult {
    val value = this

    return verifyAllUsing(context) { value verifyWith rule }
}

@Suppress("NOTHING_TO_INLINE")
public inline fun <T, C : AggregatingValidationContext> T.verifyWithAllUsing(
    context: C,
    vararg rules: Rule<T>,
): ValidationResult {
    val value = this

    return verifyAllUsing(context) {
        value.verifyWith(rules = rules)
    }
}

@Suppress("NOTHING_TO_INLINE")
public inline fun <T, C : AggregatingValidationContext> T.verifyWithAllUsing(
    context: C,
    rules: Iterable<Rule<T>>,
): ValidationResult {
    val value = this

    return verifyAllUsing(context) { value verifyWith rules }
}
