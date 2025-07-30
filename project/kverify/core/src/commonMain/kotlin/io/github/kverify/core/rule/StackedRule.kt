package io.github.kverify.core.rule

import io.github.kverify.core.context.ValidationContext
import kotlin.jvm.JvmInline

@JvmInline
public value class StackedRule<T>(
    public val rules: List<Rule<T>>,
) : Rule<T> {
    override fun ValidationContext.runValidation(value: T) {
        for (rule in rules) {
            rule.runValidation(
                context = this@runValidation,
                value = value,
            )
        }
    }
}
