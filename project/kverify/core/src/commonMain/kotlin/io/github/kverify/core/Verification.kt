package io.github.kverify.core

public interface Verification<T> {
    public fun enforce(rule: Rule<T>)
}

@PublishedApi
internal class VerificationImpl<T>(
    val value: T,
    scope: ValidationScope,
    path: ValidationPath = emptyList(),
) : Verification<T> {
    private val scope =
        if (path.isNotEmpty()) {
            scope + ListValidationContext(path)
        } else {
            scope
        }

    override fun enforce(rule: Rule<T>): Unit = rule.execute(scope, value)
}

@Suppress("NOTHING_TO_INLINE")
public inline infix fun <T> Verification<T>.with(rule: Rule<T>): Unit = enforce(rule)

@Suppress("NOTHING_TO_INLINE")
public inline infix fun <T> Verification<T>.with(rules: Iterable<Rule<T>>) {
    for (rule in rules) enforce(rule)
}

@Suppress("NOTHING_TO_INLINE")
public inline fun <T> Verification<T>.with(vararg rules: Rule<T>): Unit = with(rules.asList())
