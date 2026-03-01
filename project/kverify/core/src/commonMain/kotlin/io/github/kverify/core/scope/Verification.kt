package io.github.kverify.core.scope

public class Verification<T>(
    public val value: T,
    public val scope: ValidationScope,
)

public fun <T : Any> Verification<T?>.takeIfNotNull(): Verification<T>? =
    if (value != null) {
        @Suppress("UNCHECKED_CAST")
        this as Verification<T>
    } else {
        null
    }
