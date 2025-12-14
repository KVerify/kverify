package io.github.kverify.core.rule.predicate.check

/**
 * A predicate that determines whether a value is valid.
 *
 * Implementations return `true` for valid values and `false` for invalid ones.
 * Used by [PredicateRule][io.github.kverify.core.rule.predicate.PredicateRule] to perform validation.
 *
 * @see ValidationCheckList
 * @see io.github.kverify.core.rule.predicate.PredicateRule
 */
public fun interface ValidationCheck<in T> {
    /**
     * Tests whether the given [value] is valid.
     *
     * @return `true` if valid, `false` otherwise
     */
    public fun isValid(value: T): Boolean
}

/**
 * ### Example:
 * ```kt
 * val isPositive = ValidationCheck<Int> { it > 0 }
 * val isNotPositive = !isPositive
 *
 * isPositive.isValid(5) // true
 * isNotPositive.isValid(5) // false
 * ```
 *
 * @return a new [ValidationCheck] that inverts the result of `this` check.
 */
@Suppress("NOTHING_TO_INLINE")
public inline operator fun <T> ValidationCheck<T>.not(): ValidationCheck<T> = InvertedValidationCheck(this)

/**
 * Combines `this` check with another check using `AND` logic.
 *
 * If either check is a [AllValidationCheckList],
 * its [checks][AllValidationCheckList.checks] are flattened into the result.
 * Otherwise, both checks are combined into a new [AllValidationCheckList].
 *
 * ### Example:
 * ```kt
 * val checkList1 = AllValidationCheckList(check1, check2)
 * val checkList2 = AllValidationCheckList(check3, check4)
 *
 * // AllValidationCheckList(check1, check2, check3)
 * checkList1 and check3
 *
 * // AllValidationCheckList(check1, check2, check3, check4)
 * checkList1 and checkList2
 *
 * val isPositiveCheck = ValidationCheck<Int> { it > 0 }
 * val lessThanTenCheck = ValidationCheck<Int> { it < 10 }
 *
 * val combinedCheck = isPositiveCheck and lessThanTenCheck
 *
 * (1..10).all { combinedCheck.isValid(it) } // true
 * combinedCheck.isValid(0) // false
 * combinedCheck.isValid(11) // false
 * ```
 *
 * @param other The check to combine with
 * @return A new [AllValidationCheckList] that passes only if all checks pass
 */
public infix fun <T> ValidationCheck<T>.and(other: ValidationCheck<T>): AllValidationCheckList<T> =
    when {
        this is AllValidationCheckList && other is AllValidationCheckList -> {
            AllValidationCheckList(this.checks + other.checks)
        }

        this is AllValidationCheckList -> {
            AllValidationCheckList(this.checks + other)
        }

        other is AllValidationCheckList -> {
            AllValidationCheckList(listOf(this) + other.checks)
        }

        else -> {
            AllValidationCheckList(this, other)
        }
    }

/**
 * Combines `this` check with another check using `OR` logic.
 *
 * If either check is a [AnyValidationCheckList],
 * its [checks][AnyValidationCheckList.checks] are flattened into the result.
 * Otherwise, both checks are combined into a new [AnyValidationCheckList].
 *
 * ### Example:
 * ```kt
 * val checkList1 = AnyValidationCheckList(check1, check2)
 * val checkList2 = AnyValidationCheckList(check3, check4)
 *
 * // AnyValidationCheckList(check1, check2, check3)
 * checkList1 or check3
 *
 * // AnyValidationCheckList(check1, check2, check3, check4)
 * checkList1 or checkList2
 *
 * val isNegativeCheck = ValidationCheck<Int> { it < 0 }
 * val greaterThanTenCheck = ValidationCheck<Int> { it > 10 }
 *
 * val combinedCheck = isNegativeCheck or greaterThanTenCheck
 *
 * combinedCheck.isValid(-5) // true
 * combinedCheck.isValid(15) // true
 * combinedCheck.isValid(5) // false
 * ```
 *
 * @param other The check to combine with
 * @return A new [AnyValidationCheckList] that passes if at least one check passes
 */
public infix fun <T> ValidationCheck<T>.or(other: ValidationCheck<T>): AnyValidationCheckList<T> =
    when {
        this is AnyValidationCheckList && other is AnyValidationCheckList -> {
            AnyValidationCheckList(this.checks + other.checks)
        }

        this is AnyValidationCheckList -> {
            AnyValidationCheckList(this.checks + other)
        }

        other is AnyValidationCheckList -> {
            AnyValidationCheckList(listOf(this) + other.checks)
        }

        else -> {
            AnyValidationCheckList(this, other)
        }
    }
