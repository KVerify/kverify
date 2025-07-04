package io.github.kverify.core.rule

import io.github.kverify.core.context.ValidationContext
import io.github.kverify.core.model.NamedValue
import kotlin.jvm.JvmInline

/**
 * A validation [Rule] for a [io.github.kverify.core.model.NamedValue].
 */
public typealias NamedRule<T> = Rule<NamedValue<T>>

/**
 * Wraps this [Rule] to operate on a [NamedValue], preserving the original validation logic.
 *
 * This allows applying a standard [Rule] to named input without duplicating rule definitions.
 */
@Suppress("NOTHING_TO_INLINE")
public inline fun <T> Rule<T>.asNamedRule(): NamedRule<T> = NamedRuleAdapter(this)

/**
 * Adapts a [Rule] to a [NamedRule], enabling validation of [NamedValue]s using the original rule.
 */
@JvmInline
@PublishedApi
internal value class NamedRuleAdapter<T>(
    private val baseRule: Rule<T>,
) : NamedRule<T> {
    override fun ValidationContext.runValidation(value: NamedValue<T>) {
        val context = this@runValidation

        baseRule.runValidation(
            context = context,
            value = value.value,
        )
    }
}
