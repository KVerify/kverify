package io.github.kverify.named.rule

import io.github.kverify.core.context.ValidationContext
import io.github.kverify.core.rule.Rule
import io.github.kverify.named.model.NamedValue
import kotlin.jvm.JvmInline

/**
 * A [Rule] operating on a [NamedValue].
 */
public typealias NamedRule<T> = Rule<NamedValue<T>>

/**
 * @return a new anonymous object that implements [NamedRule] interface with given [execute] lambda.
 */
public inline fun <T> NamedRule(crossinline execute: ValidationContext.(NamedValue<T>) -> Unit): NamedRule<T> =
    object : NamedRule<T> {
        override fun execute(
            context: ValidationContext,
            value: NamedValue<T>,
        ): Unit = execute(context, value)
    }

/**
 * Wraps `this` [Rule] to work with [NamedValue].
 */
@Suppress("NOTHING_TO_INLINE")
public inline fun <T> Rule<T>.asNamedRule(): NamedRule<T> = NamedRuleAdapter(this)

/**
 * Wraps [baseRule] to execute validation on [NamedValue] instances.
 *
 * @see Rule
 */
@JvmInline
public value class NamedRuleAdapter<T>(
    public val baseRule: Rule<T>,
) : NamedRule<T> {
    /**
     * Extracts the value from [NamedValue] and delegates to [baseRule].
     */
    override fun execute(
        context: ValidationContext,
        value: NamedValue<T>,
    ) {
        baseRule.execute(context, value.value)
    }
}
