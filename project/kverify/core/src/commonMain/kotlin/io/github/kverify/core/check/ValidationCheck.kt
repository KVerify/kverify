package io.github.kverify.core.check

public fun interface ValidationCheck<in T> {
    public fun isValid(value: T): Boolean
}
