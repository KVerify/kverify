package io.github.kverify.rule.set.provider

import io.github.kverify.rule.set.NamedValueViolationGenerator
import io.github.kverify.rule.set.ValueViolationGenerator
import io.github.kverify.rule.set.comparable.ComparableBetweenRule
import io.github.kverify.rule.set.comparable.ComparableEqualToRule
import io.github.kverify.rule.set.comparable.ComparableGreaterThanOrEqualToRule
import io.github.kverify.rule.set.comparable.ComparableGreaterThanRule
import io.github.kverify.rule.set.comparable.ComparableLessThanOrEqualToRule
import io.github.kverify.rule.set.comparable.ComparableLessThanRule
import io.github.kverify.rule.set.comparable.ComparableNotBetweenRule
import io.github.kverify.rule.set.comparable.ComparableNotEqualToRule
import io.github.kverify.rule.set.comparable.NamedComparableBetweenRule
import io.github.kverify.rule.set.comparable.NamedComparableEqualToRule
import io.github.kverify.rule.set.comparable.NamedComparableGreaterThanOrEqualToRule
import io.github.kverify.rule.set.comparable.NamedComparableGreaterThanRule
import io.github.kverify.rule.set.comparable.NamedComparableLessThanOrEqualToRule
import io.github.kverify.rule.set.comparable.NamedComparableLessThanRule
import io.github.kverify.rule.set.comparable.NamedComparableNotBetweenRule
import io.github.kverify.rule.set.comparable.NamedComparableNotEqualToRule
import io.github.kverify.violation.set.provider.ComparableViolationProvider

public interface ComparableRuleProvider {
    public val comparableViolationProvider: ComparableViolationProvider
        get() = ComparableViolationProvider.Default

    // region between
    public fun <T : Comparable<T>> between(
        range: ClosedRange<T>,
        violationGenerator: ValueViolationGenerator<T> = { value ->
            comparableViolationProvider.between(
                range = range,
                value = value,
            )
        },
    ): ComparableBetweenRule<T> =
        ComparableBetweenRule(
            range = range,
            violationGenerator = violationGenerator,
        )

    public fun <T : Comparable<T>> between(
        range: ClosedRange<T>,
        name: String,
    ): ComparableBetweenRule<T> =
        ComparableBetweenRule(
            range = range,
            name = name,
        )

    public fun <T : Comparable<T>> between(
        min: T,
        max: T,
        violationGenerator: ValueViolationGenerator<T> = { value ->
            comparableViolationProvider.between(
                range = min..max,
                value = value,
            )
        },
    ): ComparableBetweenRule<T> =
        between(
            range = min..max,
            violationGenerator = violationGenerator,
        )

    public fun <T : Comparable<T>> between(
        min: T,
        max: T,
        name: String,
    ): ComparableBetweenRule<T> =
        between(
            range = min..max,
            name = name,
        )
    // endregion

    // region namedBetween
    public fun <T : Comparable<T>> namedBetween(
        range: ClosedRange<T>,
        violationGenerator: NamedValueViolationGenerator<T> = { (name, value) ->
            comparableViolationProvider.between(
                range = range,
                value = value,
                name = name,
            )
        },
    ): NamedComparableBetweenRule<T> =
        NamedComparableBetweenRule(
            range = range,
            violationGenerator = violationGenerator,
        )

    public fun <T : Comparable<T>> namedBetween(
        min: T,
        max: T,
        violationGenerator: NamedValueViolationGenerator<T> = { (name, value) ->
            comparableViolationProvider.between(
                range = min..max,
                value = value,
                name = name,
            )
        },
    ): NamedComparableBetweenRule<T> =
        namedBetween(
            range = min..max,
            violationGenerator = violationGenerator,
        )
    // endregion

    // region equalTo
    public fun <T : Comparable<T>> equalTo(
        other: T,
        violationGenerator: ValueViolationGenerator<T> = { value ->
            comparableViolationProvider.equalTo(
                other = other,
                value = value,
            )
        },
    ): ComparableEqualToRule<T> =
        ComparableEqualToRule(
            other = other,
            violationGenerator = violationGenerator,
        )

    public fun <T : Comparable<T>> equalTo(
        other: T,
        name: String,
    ): ComparableEqualToRule<T> =
        ComparableEqualToRule(
            other = other,
            name = name,
        )
    // endregion

    // region namedEqualTo
    public fun <T : Comparable<T>> namedEqualTo(
        other: T,
        violationGenerator: NamedValueViolationGenerator<T> = { (name, value) ->
            comparableViolationProvider.equalTo(
                other = other,
                value = value,
                name = name,
            )
        },
    ): NamedComparableEqualToRule<T> =
        NamedComparableEqualToRule(
            other = other,
            violationGenerator = violationGenerator,
        )
    // endregion

    // region greaterThanOrEqualTo
    public fun <T : Comparable<T>> greaterThanOrEqualTo(
        other: T,
        violationGenerator: ValueViolationGenerator<T> = { value ->
            comparableViolationProvider.greaterThanOrEqualTo(
                other = other,
                value = value,
            )
        },
    ): ComparableGreaterThanOrEqualToRule<T> =
        ComparableGreaterThanOrEqualToRule(
            other = other,
            violationGenerator = violationGenerator,
        )

    public fun <T : Comparable<T>> greaterThanOrEqualTo(
        other: T,
        name: String,
    ): ComparableGreaterThanOrEqualToRule<T> =
        ComparableGreaterThanOrEqualToRule(
            other = other,
            name = name,
        )
    // endregion

    // region namedGreaterThanOrEqualTo
    public fun <T : Comparable<T>> namedGreaterThanOrEqualTo(
        other: T,
        violationGenerator: NamedValueViolationGenerator<T> = { (name, value) ->
            comparableViolationProvider.greaterThanOrEqualTo(
                other = other,
                value = value,
                name = name,
            )
        },
    ): NamedComparableGreaterThanOrEqualToRule<T> =
        NamedComparableGreaterThanOrEqualToRule(
            other = other,
            violationGenerator = violationGenerator,
        )
    // endregion

    // region greaterThan
    public fun <T : Comparable<T>> greaterThan(
        other: T,
        violationGenerator: ValueViolationGenerator<T> = { value ->
            comparableViolationProvider.greaterThan(
                other = other,
                value = value,
            )
        },
    ): ComparableGreaterThanRule<T> =
        ComparableGreaterThanRule(
            other = other,
            violationGenerator = violationGenerator,
        )

    public fun <T : Comparable<T>> greaterThan(
        other: T,
        name: String,
    ): ComparableGreaterThanRule<T> =
        ComparableGreaterThanRule(
            other = other,
            name = name,
        )
    // endregion

    // region namedGreaterThan
    public fun <T : Comparable<T>> namedGreaterThan(
        other: T,
        violationGenerator: NamedValueViolationGenerator<T> = { (name, value) ->
            comparableViolationProvider.greaterThan(
                other = other,
                value = value,
                name = name,
            )
        },
    ): NamedComparableGreaterThanRule<T> =
        NamedComparableGreaterThanRule(
            other = other,
            violationGenerator = violationGenerator,
        )
    // endregion

    // region lessThanOrEqualTo
    public fun <T : Comparable<T>> lessThanOrEqualTo(
        other: T,
        violationGenerator: ValueViolationGenerator<T> = { value ->
            comparableViolationProvider.lessThanOrEqualTo(
                other = other,
                value = value,
            )
        },
    ): ComparableLessThanOrEqualToRule<T> =
        ComparableLessThanOrEqualToRule(
            other = other,
            violationGenerator = violationGenerator,
        )

    public fun <T : Comparable<T>> lessThanOrEqualTo(
        other: T,
        name: String,
    ): ComparableLessThanOrEqualToRule<T> =
        ComparableLessThanOrEqualToRule(
            other = other,
            name = name,
        )
    // endregion

    // region namedLessThanOrEqualTo
    public fun <T : Comparable<T>> namedLessThanOrEqualTo(
        other: T,
        violationGenerator: NamedValueViolationGenerator<T> = { (name, value) ->
            comparableViolationProvider.lessThanOrEqualTo(
                other = other,
                value = value,
                name = name,
            )
        },
    ): NamedComparableLessThanOrEqualToRule<T> =
        NamedComparableLessThanOrEqualToRule(
            other = other,
            violationGenerator = violationGenerator,
        )
    // endregion

    // region lessThan
    public fun <T : Comparable<T>> lessThan(
        other: T,
        violationGenerator: ValueViolationGenerator<T> = { value ->
            comparableViolationProvider.lessThan(
                other = other,
                value = value,
            )
        },
    ): ComparableLessThanRule<T> =
        ComparableLessThanRule(
            other = other,
            violationGenerator = violationGenerator,
        )

    public fun <T : Comparable<T>> lessThan(
        other: T,
        name: String,
    ): ComparableLessThanRule<T> =
        ComparableLessThanRule(
            other = other,
            name = name,
        )
    // endregion

    // region namedLessThan
    public fun <T : Comparable<T>> namedLessThan(
        other: T,
        violationGenerator: NamedValueViolationGenerator<T> = { (name, value) ->
            comparableViolationProvider.lessThan(
                other = other,
                value = value,
                name = name,
            )
        },
    ): NamedComparableLessThanRule<T> =
        NamedComparableLessThanRule(
            other = other,
            violationGenerator = violationGenerator,
        )
    // endregion

    // region notBetween
    public fun <T : Comparable<T>> notBetween(
        range: ClosedRange<T>,
        violationGenerator: ValueViolationGenerator<T> = { value ->
            comparableViolationProvider.notBetween(
                range = range,
                value = value,
            )
        },
    ): ComparableNotBetweenRule<T> =
        ComparableNotBetweenRule(
            range = range,
            violationGenerator = violationGenerator,
        )

    public fun <T : Comparable<T>> notBetween(
        range: ClosedRange<T>,
        name: String,
    ): ComparableNotBetweenRule<T> =
        ComparableNotBetweenRule(
            range = range,
            name = name,
        )

    public fun <T : Comparable<T>> notBetween(
        min: T,
        max: T,
        violationGenerator: ValueViolationGenerator<T> = { value ->
            comparableViolationProvider.notBetween(
                range = min..max,
                value = value,
            )
        },
    ): ComparableNotBetweenRule<T> =
        ComparableNotBetweenRule(
            min = min,
            max = max,
            violationGenerator = violationGenerator,
        )

    public fun <T : Comparable<T>> notBetween(
        min: T,
        max: T,
        name: String,
    ): ComparableNotBetweenRule<T> =
        ComparableNotBetweenRule(
            min = min,
            max = max,
            name = name,
        )
    // endregion

    // region namedNotBetween
    public fun <T : Comparable<T>> namedNotBetween(
        range: ClosedRange<T>,
        violationGenerator: NamedValueViolationGenerator<T> = { (name, value) ->
            comparableViolationProvider.notBetween(
                range = range,
                value = value,
                name = name,
            )
        },
    ): NamedComparableNotBetweenRule<T> =
        NamedComparableNotBetweenRule(
            range = range,
            violationGenerator = violationGenerator,
        )

    public fun <T : Comparable<T>> namedNotBetween(
        min: T,
        max: T,
        violationGenerator: NamedValueViolationGenerator<T> = { (name, value) ->
            comparableViolationProvider.notBetween(
                range = min..max,
                value = value,
                name = name,
            )
        },
    ): NamedComparableNotBetweenRule<T> =
        NamedComparableNotBetweenRule(
            min = min,
            max = max,
            violationGenerator = violationGenerator,
        )
    // endregion

    // region notEqualTo
    public fun <T : Comparable<T>> notEqualTo(
        other: T,
        violationGenerator: ValueViolationGenerator<T> = { value ->
            comparableViolationProvider.notEqualTo(
                other = other,
                value = value,
            )
        },
    ): ComparableNotEqualToRule<T> =
        ComparableNotEqualToRule(
            other = other,
            violationGenerator = violationGenerator,
        )

    public fun <T : Comparable<T>> notEqualTo(
        other: T,
        name: String,
    ): ComparableNotEqualToRule<T> =
        ComparableNotEqualToRule(
            other = other,
            name = name,
        )
    // endregion

    // region namedNotEqualTo
    public fun <T : Comparable<T>> namedNotEqualTo(
        other: T,
        violationGenerator: NamedValueViolationGenerator<T> = { (name, value) ->
            comparableViolationProvider.notEqualTo(
                other = other,
                value = value,
                name = name,
            )
        },
    ): NamedComparableNotEqualToRule<T> =
        NamedComparableNotEqualToRule(
            other = other,
            violationGenerator = violationGenerator,
        )
    // endregion
}

public open class ComparableRules(
    override val comparableViolationProvider: ComparableViolationProvider,
) : ComparableRuleProvider {
    public companion object : ComparableRules(
        comparableViolationProvider = ComparableViolationProvider.Default,
    )
}
