package io.github.kverify.core.scope

import io.github.kverify.core.context.ValidationPathElement

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

public inline fun <T, I : Iterable<T>> Verification<I>.each(block: ValidationScope.(Int, T) -> Unit): Verification<I> =
    apply {
        for ((idx, element) in value.withIndex()) {
            val indexedScope = scope + ValidationPathElement.Index(idx)
            indexedScope.block(idx, element)
        }
    }

public inline fun <T, I : Iterable<T>> Verification<I>.each(block: ValidationScope.(T) -> Unit): Verification<I> =
    each { _, element ->
        block(element)
    }
