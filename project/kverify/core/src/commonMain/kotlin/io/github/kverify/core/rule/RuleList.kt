package io.github.kverify.core.rule

import io.github.kverify.core.scope.ValidationScope

public class RuleList<in T>(
    public val rules: List<Rule<T>>,
) : Rule<T> {
    override fun execute(
        scope: ValidationScope,
        value: T,
    ) {
        for (rule in rules) {
            rule.execute(
                scope = scope,
                value = value,
            )
        }
    }
}

@Suppress("NOTHING_TO_INLINE")
public inline fun <T> RuleList(vararg rules: Rule<T>): RuleList<T> = RuleList(rules.asList())
