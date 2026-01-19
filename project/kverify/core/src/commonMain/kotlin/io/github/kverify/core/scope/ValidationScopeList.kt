package io.github.kverify.core.scope

import io.github.kverify.core.context.EmptyValidationContext
import io.github.kverify.core.context.ValidationContext
import io.github.kverify.core.violation.Violation
import kotlin.js.JsName
import kotlin.jvm.JvmInline

@JvmInline
public value class ValidationScopeList(
    public val validationScopes: List<ValidationScope>,
) : ValidationScope {
    override val validationContext: ValidationContext
        get() =
            validationScopes
                .map { it.validationContext }
                .fold<ValidationContext, ValidationContext>(EmptyValidationContext) { acc, validationContext ->
                    acc + validationContext
                }

    override fun onFailure(violation: Violation) {
        for (validationScope in validationScopes) {
            validationScope.onFailure(violation)
        }
    }
}

@Suppress("NOTHING_TO_INLINE")
@JsName("EmptyValidationScopeList")
public inline fun ValidationScopeList(): ValidationScopeList =
    ValidationScopeList(
        validationScopes = emptyList(),
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun ValidationScopeList(validationScope: ValidationScope): ValidationScopeList =
    ValidationScopeList(
        validationScopes = listOf(validationScope),
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun ValidationScopeList(vararg validationScopes: ValidationScope): ValidationScopeList =
    ValidationScopeList(
        validationScopes = validationScopes.asList(),
    )

public operator fun ValidationScopeList.plus(other: ValidationScope): ValidationScopeList =
    when (other) {
        is ValidationScopeList -> ValidationScopeList(validationScopes + other.validationScopes)
        else -> ValidationScopeList(validationScopes + other)
    }

public operator fun ValidationScopeList.plus(other: ValidationScopeList): ValidationScopeList =
    ValidationScopeList(
        validationScopes + other.validationScopes,
    )
