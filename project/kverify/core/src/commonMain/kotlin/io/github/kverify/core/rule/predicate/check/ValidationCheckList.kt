package io.github.kverify.core.rule.predicate.check

/**
 * A composite [ValidationCheck] that combines multiple checks using boolean logic.
 *
 * Implementations define how the [checks] are evaluated (e.g., AND, OR logic).
 *
 * @see AllValidationCheckList
 * @see AnyValidationCheckList
 */
public interface ValidationCheckList<in T> : ValidationCheck<T> {
    /**
     * The checks combined in this list.
     */
    public val checks: List<ValidationCheck<T>>
}
