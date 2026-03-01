package io.github.kverify.core.scope

public class ScopedVerification<T>(
    public val value: T,
    public val scope: ValidationScope,
)

public fun <T : Any> ScopedVerification<T?>.takeIfNotNull(): ScopedVerification<T>? =
    if (value != null) {
        @Suppress("UNCHECKED_CAST")
        this as ScopedVerification<T>
    } else {
        null
    }
