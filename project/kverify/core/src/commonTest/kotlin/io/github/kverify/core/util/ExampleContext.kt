package io.github.kverify.core.util

import io.github.kverify.core.context.EmptyValidationContext
import io.github.kverify.core.context.PersistentValidationContext
import io.github.kverify.core.context.ValidationContext

class ExampleContext : ValidationContext {
    override fun plus(other: ValidationContext): ValidationContext {
        TODO("Not yet implemented")
    }

    override fun <E : ValidationContext.Element> get(key: ValidationContext.Key<E>): E? {
        TODO("Not yet implemented")
    }

    override fun minusKey(key: ValidationContext.Key<*>): ValidationContext {
        TODO("Not yet implemented")
    }

    override fun iterator(): Iterator<ValidationContext.Element> {
        TODO("Not yet implemented")
    }

    companion object : ValidationContext by ExampleContext()
}

val testValidationContexts: Array<ValidationContext> =
    arrayOf(
        ExampleContext,
        PersistentValidationContext(ExampleElement),
        EmptyValidationContext,
    )
