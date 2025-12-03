@file:Suppress("TooManyFunctions")

package io.github.kverify.core.context

import io.github.kverify.core.model.ValidationResult
import io.github.kverify.core.rule.Rule
import io.github.kverify.core.violation.Violation
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

public open class CollectingValidationContext(
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
public inline fun verifyCollecting(block: CollectingValidationContext.() -> Unit): ValidationResult {
    contract {
        callsInPlace(block, InvocationKind.EXACTLY_ONCE)
    }

    return verifyCollectingTo(ArrayList(), block)
}

@Suppress("UnusedReceiverParameter", "NOTHING_TO_INLINE")
public inline fun <T> T.verifyWithCollecting(): ValidationResult.Valid = ValidationResult.Valid

@Suppress("NOTHING_TO_INLINE")
public inline infix fun <T> T.verifyWithCollecting(rule: Rule<T>): ValidationResult =
    this.verifyWithCollectingTo(
        destination = ArrayList(),
        rule = rule,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun <T> T.verifyWithCollecting(vararg rules: Rule<T>): ValidationResult =
    this.verifyWithCollectingTo(
        destination = ArrayList(),
        rules = rules,
    )

@Suppress("NOTHING_TO_INLINE")
public inline infix fun <T> T.verifyWithCollecting(rules: Iterable<Rule<T>>): ValidationResult =
    this.verifyWithCollectingTo(
        destination = ArrayList(),
        rules = rules,
    )

// ============================================================
// Validate using AggregatingValidationContext with provided destination as violationStorage
// ============================================================
@OptIn(ExperimentalContracts::class)
public inline fun verifyCollectingTo(
    destination: MutableCollection<Violation>,
    block: CollectingValidationContext.() -> Unit,
): ValidationResult {
    contract {
        callsInPlace(block, InvocationKind.EXACTLY_ONCE)
    }

    return verifyCollectingUsing(
        context = CollectingValidationContext(destination),
        block,
    )
}

@Suppress("UnusedParameter", "UnusedReceiverParameter", "NOTHING_TO_INLINE")
public inline fun <T> T.verifyWithCollectingTo(destination: MutableCollection<Violation>): ValidationResult.Valid = ValidationResult.Valid

@Suppress("NOTHING_TO_INLINE")
public inline fun <T> T.verifyWithCollectingTo(
    destination: MutableCollection<Violation>,
    rule: Rule<T>,
): ValidationResult =
    this.verifyWithCollectingUsing(
        context = CollectingValidationContext(destination),
        rule = rule,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun <T> T.verifyWithCollectingTo(
    destination: MutableCollection<Violation>,
    vararg rules: Rule<T>,
): ValidationResult =
    this.verifyWithCollectingUsing(
        context = CollectingValidationContext(destination),
        rules = rules,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun <T> T.verifyWithCollectingTo(
    destination: MutableCollection<Violation>,
    rules: Iterable<Rule<T>>,
): ValidationResult =
    this.verifyWithCollectingUsing(
        context = CollectingValidationContext(destination),
        rules = rules,
    )

// ============================================================
// Validate using provided context
// ============================================================
@OptIn(ExperimentalContracts::class)
public inline fun <C : CollectingValidationContext> verifyCollectingUsing(
    context: C,
    block: C.() -> Unit,
): ValidationResult {
    contract {
        callsInPlace(block, InvocationKind.EXACTLY_ONCE)
    }

    return context.apply(block).build()
}

@Suppress("UnusedReceiverParameter", "NOTHING_TO_INLINE")
public inline fun <T, C : CollectingValidationContext> T.verifyWithCollectingUsing(context: C): ValidationResult = context.build()

@Suppress("NOTHING_TO_INLINE")
public inline fun <T, C : CollectingValidationContext> T.verifyWithCollectingUsing(
    context: C,
    rule: Rule<T>,
): ValidationResult {
    val value = this

    return verifyCollectingUsing(context) { value verifyWith rule }
}

@Suppress("NOTHING_TO_INLINE")
public inline fun <T, C : CollectingValidationContext> T.verifyWithCollectingUsing(
    context: C,
    vararg rules: Rule<T>,
): ValidationResult {
    val value = this

    return verifyCollectingUsing(context) {
        value.verifyWith(rules = rules)
    }
}

@Suppress("NOTHING_TO_INLINE")
public inline fun <T, C : CollectingValidationContext> T.verifyWithCollectingUsing(
    context: C,
    rules: Iterable<Rule<T>>,
): ValidationResult {
    val value = this

    return verifyCollectingUsing(context) { value verifyWith rules }
}
