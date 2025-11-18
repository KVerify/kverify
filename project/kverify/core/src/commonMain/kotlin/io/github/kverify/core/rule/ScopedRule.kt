package io.github.kverify.core.rule

import io.github.kverify.core.context.ValidationContext
import kotlin.jvm.JvmInline

public interface ScopedRule<in T> : Rule<T> {
    public fun ValidationContext.executeScoped(value: T)

    override fun execute(
        context: ValidationContext,
        value: T,
    ): Unit = with(context) { executeScoped(value) }
}

public inline fun <T> rule(crossinline execute: ValidationContext.(T) -> Unit): ScopedRule<T> =
    object : ScopedRule<T> {
        override fun ValidationContext.executeScoped(value: T): Unit = execute(value)
    }

@Suppress("NOTHING_TO_INLINE")
public inline fun <T> Rule<T>.asScoped(): ScopedRule<T> = ScopedRuleAdapter(this)

@JvmInline
@PublishedApi
internal value class ScopedRuleAdapter<in T>(
    private val originalRule: Rule<T>,
) : ScopedRule<T> {
    override fun ValidationContext.executeScoped(value: T): Unit = originalRule.execute(this, value)
}
