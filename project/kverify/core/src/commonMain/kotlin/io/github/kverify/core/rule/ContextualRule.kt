package io.github.kverify.core.rule

import io.github.kverify.core.context.ValidationContext
import kotlin.jvm.JvmInline

public interface ContextualRule<in T> : Rule<T> {
    public fun ValidationContext.verify(value: T)

    override fun execute(
        context: ValidationContext,
        value: T,
    ): Unit = context.verify(value)
}

public inline fun <T> rule(crossinline verify: ValidationContext.(T) -> Unit): ContextualRule<T> =
    object : ContextualRule<T> {
        override fun ValidationContext.verify(value: T): Unit = verify(value)
    }

@Suppress("NOTHING_TO_INLINE")
public inline fun <T> Rule<T>.asContextual(): ContextualRule<T> = ContextualRuleAdapter(this)

@JvmInline
@PublishedApi
internal value class ContextualRuleAdapter<in T>(
    private val originalRule: Rule<T>,
) : ContextualRule<T> {
    override fun ValidationContext.verify(value: T): Unit = originalRule.execute(this, value)
}
