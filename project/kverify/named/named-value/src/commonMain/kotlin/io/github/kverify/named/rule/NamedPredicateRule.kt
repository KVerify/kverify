package io.github.kverify.named.rule

import io.github.kverify.core.rule.predicate.PredicateRule
import io.github.kverify.core.rule.predicate.ViolationFactory
import io.github.kverify.core.rule.predicate.check.ValidationCheck
import io.github.kverify.named.check.NamedValidationCheck
import io.github.kverify.named.check.NamedViolationFactory
import io.github.kverify.named.check.asNamedValidationCheck
import io.github.kverify.named.check.asNamedViolationFactory
import io.github.kverify.named.model.NamedValue
import kotlin.jvm.JvmName

/**
 * A [PredicateRule] operating on a [NamedValue].
 */
public typealias NamedPredicateRule<T> = PredicateRule<NamedValue<T>>

/**
 * Creates a [NamedPredicateRule] from a [ValidationCheck] and a [NamedViolationFactory].
 */
@Suppress("NOTHING_TO_INLINE")
@JvmName("NamedPredicateRuleFromCheckAndNamedFactory")
public inline fun <T> NamedPredicateRule(
    validationCheck: ValidationCheck<T>,
    violationFactory: NamedViolationFactory<T>,
): NamedPredicateRule<T> =
    NamedPredicateRule(
        validationCheck = validationCheck.asNamedValidationCheck(),
        violationFactory = violationFactory,
    )

/**
 * Creates a [NamedPredicateRule] from a [ValidationCheck] and a [ViolationFactory].
 */
@Suppress("NOTHING_TO_INLINE")
@JvmName("NamedPredicateRuleFromCheckAndFactory")
public inline fun <T> NamedPredicateRule(
    validationCheck: ValidationCheck<T>,
    violationFactory: ViolationFactory<T>,
): NamedPredicateRule<T> =
    NamedPredicateRule(
        validationCheck = validationCheck.asNamedValidationCheck(),
        violationFactory = violationFactory.asNamedViolationFactory(),
    )

/**
 * Creates a [NamedPredicateRule] from a [NamedValidationCheck] and a [ViolationFactory].
 */
@Suppress("NOTHING_TO_INLINE")
@JvmName("NamedPredicateRuleFromNamedCheckAndFactory")
public inline fun <T> NamedPredicateRule(
    validationCheck: NamedValidationCheck<T>,
    violationFactory: ViolationFactory<T>,
): NamedPredicateRule<T> =
    NamedPredicateRule(
        validationCheck = validationCheck,
        violationFactory = violationFactory.asNamedViolationFactory(),
    )
