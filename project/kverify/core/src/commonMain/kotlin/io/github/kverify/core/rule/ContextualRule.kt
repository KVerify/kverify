package io.github.kverify.core.rule

import io.github.kverify.core.context.ValidationContext

public interface ContextualRule<in T> : Rule<T> {
    public fun ValidationContext.verify(value: T)

    override fun run(
        context: ValidationContext,
        value: T,
    ): Unit = context.verify(value)
}

public inline fun <T> rule(crossinline verify: ValidationContext.(T) -> Unit): ContextualRule<T> =
    object : ContextualRule<T> {
        override fun ValidationContext.verify(value: T): Unit = verify(value)
    }
