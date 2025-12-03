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
public inline fun <T> T.verifyAll(): ValidationResult.Valid = ValidationResult.Valid

@Suppress("NOTHING_TO_INLINE")
public inline infix fun <T> T.verifyAll(rule: Rule<T>): ValidationResult =
    this.verifyAllTo(
        destination = ArrayList(),
        rule = rule,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun <T> T.verifyAll(vararg rules: Rule<T>): ValidationResult =
    this.verifyAllTo(
        destination = ArrayList(),
        rules = rules,
    )

@Suppress("NOTHING_TO_INLINE")
public inline infix fun <T> T.verifyAll(rules: Iterable<Rule<T>>): ValidationResult =
    this.verifyAllTo(
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
public inline fun <T> T.verifyAllTo(destination: MutableCollection<Violation>): ValidationResult.Valid = ValidationResult.Valid

@Suppress("NOTHING_TO_INLINE")
public inline fun <T> T.verifyAllTo(
    destination: MutableCollection<Violation>,
    rule: Rule<T>,
): ValidationResult =
    this.verifyAllUsing(
        context = AggregatingValidationContext(destination),
        rule = rule,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun <T> T.verifyAllTo(
    destination: MutableCollection<Violation>,
    vararg rules: Rule<T>,
): ValidationResult =
    this.verifyAllUsing(
        context = AggregatingValidationContext(destination),
        rules = rules,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun <T> T.verifyAllTo(
    destination: MutableCollection<Violation>,
    rules: Iterable<Rule<T>>,
): ValidationResult =
    this.verifyAllUsing(
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
public inline fun <T, C : AggregatingValidationContext> T.verifyAllUsing(context: C): ValidationResult = context.build()

@Suppress("NOTHING_TO_INLINE")
public inline fun <T, C : AggregatingValidationContext> T.verifyAllUsing(
    context: C,
    rule: Rule<T>,
): ValidationResult {
    val value = this

    return verifyAllUsing(context) { value verifyWith rule }
}

@Suppress("NOTHING_TO_INLINE")
public inline fun <T, C : AggregatingValidationContext> T.verifyAllUsing(
    context: C,
    vararg rules: Rule<T>,
): ValidationResult {
    val value = this

    return verifyAllUsing(context) {
        value.verifyWith(rules = rules)
    }
}

@Suppress("NOTHING_TO_INLINE")
public inline fun <T, C : AggregatingValidationContext> T.verifyAllUsing(
    context: C,
    rules: Iterable<Rule<T>>,
): ValidationResult {
    val value = this

    return verifyAllUsing(context) { value verifyWith rules }
}
