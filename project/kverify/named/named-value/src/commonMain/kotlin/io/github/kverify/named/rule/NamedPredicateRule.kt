package io.github.kverify.named.rule

import io.github.kverify.core.check.ValidationCheck
import io.github.kverify.core.check.ViolationFactory
import io.github.kverify.core.rule.PredicateRule
import io.github.kverify.named.check.NamedValidationCheck
import io.github.kverify.named.check.NamedViolationFactory
import io.github.kverify.named.check.asNamedValidationCheck
import io.github.kverify.named.check.asNamedViolationFactory
import io.github.kverify.named.model.NamedValue
import kotlin.jvm.JvmName

public typealias NamedPredicateRule<T> = PredicateRule<NamedValue<T>>

@Suppress("NOTHING_TO_INLINE")
@JvmName("namedPredicateRuleFromCheckAndNamedFactory")
public inline fun <T> NamedPredicateRule(
    validationCheck: ValidationCheck<T>,
    violationFactory: NamedViolationFactory<T>,
): NamedPredicateRule<T> =
    NamedPredicateRule(
        validationCheck = validationCheck.asNamedValidationCheck(),
        violationFactory = violationFactory,
    )

@Suppress("NOTHING_TO_INLINE")
@JvmName("namedPredicateRuleFromCheckAndFactory")
public inline fun <T> NamedPredicateRule(
    validationCheck: ValidationCheck<T>,
    violationFactory: ViolationFactory<T>,
): NamedPredicateRule<T> =
    NamedPredicateRule(
        validationCheck = validationCheck.asNamedValidationCheck(),
        violationFactory = violationFactory.asNamedViolationFactory(),
    )

@Suppress("NOTHING_TO_INLINE")
@JvmName("namedPredicateRuleFromNamedCheckAndFactory")
public inline fun <T> NamedPredicateRule(
    validationCheck: NamedValidationCheck<T>,
    violationFactory: ViolationFactory<T>,
): NamedPredicateRule<T> =
    NamedPredicateRule(
        validationCheck = validationCheck,
        violationFactory = violationFactory.asNamedViolationFactory(),
    )
