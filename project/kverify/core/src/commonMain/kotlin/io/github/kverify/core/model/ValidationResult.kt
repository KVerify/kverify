@file:Suppress("TooManyFunctions")

package io.github.kverify.core.model

import io.github.kverify.core.exception.ValidationException
import io.github.kverify.core.violation.Violation
import kotlin.js.JsName
import kotlin.jvm.JvmInline
import kotlin.jvm.JvmName

public sealed interface ValidationResult {
    public val isValid: Boolean

    public val isInvalid: Boolean

    public data object Valid : ValidationResult {
        override val isValid: Boolean = true
        override val isInvalid: Boolean = false

        override fun toString(): String = "ValidationResult.Valid"
    }

    @JvmInline
    public value class Invalid(
        public val violations: List<Violation>,
    ) : ValidationResult {
        public constructor(violation: Violation) : this(listOf(violation))
        public constructor(vararg violations: Violation) : this(violations.asList())

        override val isValid: Boolean get() = false
        override val isInvalid: Boolean get() = true

        override fun toString(): String = "ValidationResult.Invalid(violations=$violations)"
    }
}

@Suppress("NOTHING_TO_INLINE")
public inline fun ValidationResult.violationsOrEmpty(): List<Violation> =
    if (this is ValidationResult.Invalid) {
        this.violations
    } else {
        emptyList()
    }

@Suppress("NOTHING_TO_INLINE")
public inline fun ValidationResult.violationsOrNull(): List<Violation>? =
    if (this is ValidationResult.Invalid) {
        this.violations
    } else {
        null
    }

@JsName("ValidationResultValid")
@Suppress("NOTHING_TO_INLINE", "FunctionName")
public inline fun ValidationResult(): ValidationResult.Valid = ValidationResult.Valid

@JsName("ValidationResultInvalidSingle")
@Suppress("NOTHING_TO_INLINE", "FunctionName")
public inline fun ValidationResult(violation: Violation): ValidationResult.Invalid = ValidationResult.Invalid(violation)

@JsName("ValidationResultInvalidVararg")
@Suppress("NOTHING_TO_INLINE")
public inline fun ValidationResult(vararg violations: Violation): ValidationResult =
    ValidationResult(
        violations = violations.asList(),
    )

@JsName("ValidationResultInvalidList")
@Suppress("NOTHING_TO_INLINE")
public inline fun ValidationResult(violations: List<Violation>): ValidationResult =
    if (violations.isEmpty()) {
        ValidationResult.Valid
    } else {
        ValidationResult.Invalid(
            violations = violations,
        )
    }

public operator fun ValidationResult.plus(other: ValidationResult): ValidationResult =
    when (this) {
        is ValidationResult.Invalid ->
            when (other) {
                is ValidationResult.Invalid -> ValidationResult.Invalid(this.violations + other.violations)
                is ValidationResult.Valid -> this
            }

        is ValidationResult.Valid -> other
    }

public operator fun ValidationResult.plus(violation: Violation): ValidationResult.Invalid =
    when (this) {
        is ValidationResult.Invalid ->
            ValidationResult.Invalid(
                violations = this.violations + violation,
            )

        is ValidationResult.Valid ->
            ValidationResult.Invalid(
                violations = listOf(violation),
            )
    }

@JvmName("plusViolationList")
public operator fun ValidationResult.plus(violations: List<Violation>): ValidationResult {
    if (violations.isEmpty()) return this

    return when (this) {
        is ValidationResult.Invalid ->
            ValidationResult.Invalid(
                violations = this.violations + violations,
            )

        is ValidationResult.Valid ->
            ValidationResult.Invalid(
                violations = violations,
            )
    }
}

@JvmName("plusValidationResultList")
public operator fun ValidationResult.plus(validationResults: List<ValidationResult>): ValidationResult {
    val violations =
        validationResults
            .getViolations()
            .ifEmpty { return this }

    return this + violations
}

public fun List<ValidationResult>.mergeValidationResults(): ValidationResult {
    val violations =
        getViolations()
            .ifEmpty { return ValidationResult.Valid }

    return ValidationResult(violations)
}

@Suppress("NOTHING_TO_INLINE")
private inline fun List<ValidationResult>.getViolations(): List<Violation> =
    if (this.isEmpty()) {
        emptyList()
    } else {
        buildList {
            for (result in this@getViolations) {
                if (result is ValidationResult.Invalid) addAll(result.violations)
            }
        }
    }

public inline fun <T> ValidationResult.ifValid(block: () -> T): T? =
    if (this.isValid) {
        block()
    } else {
        null
    }

public inline fun <T> ValidationResult.ifInvalid(block: (List<Violation>) -> T): T? =
    if (this is ValidationResult.Invalid) {
        block(this.violations)
    } else {
        null
    }

public inline fun <T> ValidationResult.fold(
    ifValid: () -> T,
    ifInvalid: (List<Violation>) -> T,
): T =
    when (this) {
        is ValidationResult.Valid -> ifValid()
        is ValidationResult.Invalid -> ifInvalid(this.violations)
    }

public inline fun ValidationResult.onValid(block: () -> Unit): ValidationResult {
    if (this.isValid) block()

    return this
}

public inline fun ValidationResult.onInvalid(block: (List<Violation>) -> Unit): ValidationResult {
    if (this is ValidationResult.Invalid) block(this.violations)

    return this
}

@Suppress("LongParameterList")
public fun ValidationResult.throwOnFailure(
    separator: CharSequence = "\n",
    prefix: CharSequence = "Validation failed:\n",
    postfix: CharSequence = "",
    limit: Int = -1,
    truncated: CharSequence = "...",
    onEmptyPrefix: CharSequence = "Validation failed",
    cause: Throwable? = null,
    transform: (Violation) -> String = { "\t- ${it.reason}" },
) {
    if (this !is ValidationResult.Invalid) return

    val prefix =
        if (violations.isEmpty()) {
            onEmptyPrefix
        } else {
            prefix
        }

    val message =
        violations.joinToString(
            separator = separator,
            prefix = prefix,
            postfix = postfix,
            limit = limit,
            truncated = truncated,
            transform = transform,
        )

    throw ValidationException(
        message = message,
        violations = violations,
        cause = cause,
    )
}
