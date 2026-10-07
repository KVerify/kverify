package io.github.kverify.core.scope

import io.github.kverify.core.context.ValidationContext
import io.github.kverify.core.context.pathIndex
import io.github.kverify.core.context.pathName

public interface ValidationScope {
    public val validationContext: ValidationContext
}

public fun ValidationScope.pathName(name: String): ValidationContext = validationContext.pathName(name)

public fun ValidationScope.pathIndex(index: Int): ValidationContext = validationContext.pathIndex(index)
