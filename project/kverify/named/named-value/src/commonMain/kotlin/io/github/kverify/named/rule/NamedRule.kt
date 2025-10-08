package io.github.kverify.named.rule

import io.github.kverify.core.context.ValidationContext
import io.github.kverify.core.rule.Rule
import io.github.kverify.core.rule.runValidation
import io.github.kverify.named.model.NamedValue
import kotlin.jvm.JvmInline

/**
 * A [Rule] operating on a [NamedValue].
 */
public typealias NamedRule<T> = Rule<NamedValue<T>>

/**
 * Wraps `this` [Rule] to work with [NamedValue].
 */
@Suppress("NOTHING_TO_INLINE")
public inline fun <T> Rule<T>.asNamedRule(): NamedRule<T> = NamedRuleAdapter(this)

/**
 * Internal adapter delegating validation of [NamedValue] to an underlying [Rule].
 */
@JvmInline
@PublishedApi
internal value class NamedRuleAdapter<T>(
    private val baseRule: Rule<T>,
) : NamedRule<T> {
    override fun ValidationContext.runValidation(value: NamedValue<T>) {
        val context = this@runValidation
        baseRule.runValidation(context, value.value)
    }
}
