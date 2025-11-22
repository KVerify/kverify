package io.github.kverify.core.util

import io.github.kverify.core.context.ValidationContext
import io.github.kverify.core.rule.Rule

object PassingRule : Rule<Any?> {
    override fun execute(
        context: ValidationContext,
        value: Any?,
    ): Unit = Unit
}
