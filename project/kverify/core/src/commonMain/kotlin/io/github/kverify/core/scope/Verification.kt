package io.github.kverify.core.scope

public interface Verification<out T> {
    public val value: T
    public val scope: ValidationScope
}

public fun <T> Verification(
    value: T,
    scope: ValidationScope,
): Verification<T> =
    VerificationImpl(
        value = value,
        scope = scope,
    )

public fun <T : Any> Verification<T?>.takeIfNotNull(): Verification<T>? =
    if (value != null) {
        @Suppress("UNCHECKED_CAST")
        this as Verification<T>
    } else {
        null
    }

private class VerificationImpl<out T>(
    override val value: T,
    override val scope: ValidationScope,
) : Verification<T>
