package io.github.kverify.core.rule

import io.github.kverify.core.context.ValidationContext
import kotlin.jvm.JvmInline

/**
 * A convenience [Rule] with [ValidationContext] as receiver in its execution scope.
 *
 * Implementations define validation logic in [executeScoped] where [ValidationContext] is available as `this`,
 * avoiding the need for explicit `with(context) { }` wrapping.
 *
 * @see Rule
 */
public interface ScopedRule<in T> : Rule<T> {
    /**
     * Executes `this` rule for the given [value] with [ValidationContext] as receiver.
     */
    public fun ValidationContext.executeScoped(value: T)

    /**
     * Delegates to [executeScoped] with [context] as receiver.
     */
    override fun execute(
        context: ValidationContext,
        value: T,
    ): Unit = with(context) { executeScoped(value) }
}

/**
 * Creates a [ScopedRule] from a lambda with [ValidationContext] as receiver.
 */
@Suppress("FunctionName")
public inline fun <T> Rule(crossinline execute: ValidationContext.(T) -> Unit): ScopedRule<T> =
    object : ScopedRule<T> {
        override fun ValidationContext.executeScoped(value: T): Unit = execute(value)
    }

/**
 * Converts `this` [Rule] to a [ScopedRule].
 */
@Suppress("NOTHING_TO_INLINE")
public inline fun <T> Rule<T>.asScoped(): ScopedRule<T> = ScopedRuleAdapter(this)

/**
 * Wraps [originalRule] to provide scoped execution.
 *
 * @see ScopedRule
 */
@JvmInline
@PublishedApi
internal value class ScopedRuleAdapter<in T>(
    private val originalRule: Rule<T>,
) : ScopedRule<T> {
    /**
     * Delegates to [originalRule] with the receiver as context.
     */
    override fun ValidationContext.executeScoped(value: T): Unit = originalRule.execute(this, value)
}
