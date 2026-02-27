package io.github.kverify.core.context

public interface ValidationContext {
    public val elements: List<Element>

    public operator fun plus(other: ValidationContext): ValidationContext

    public interface Element
}
