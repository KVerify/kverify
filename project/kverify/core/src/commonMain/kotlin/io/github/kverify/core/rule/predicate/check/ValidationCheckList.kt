package io.github.kverify.core.rule.predicate.check

public interface ValidationCheckList<in T> : ValidationCheck<T> {
    public val checks: List<ValidationCheck<T>>
}
