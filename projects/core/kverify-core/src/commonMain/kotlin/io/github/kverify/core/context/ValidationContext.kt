package io.github.kverify.core.context

import io.github.kverify.core.model.Rule
import io.github.kverify.core.model.runValidation
import io.github.kverify.core.violation.Violation
import io.github.kverify.core.violation.asViolation

/**
 * Represents a validation context that handles validation failures.
 */
fun interface ValidationContext {
    /**
     * Handles a validation failure for a given [violation].
     */
    fun onFailure(violation: Violation)

    /**
     * @return this value after applying all [rules].
     */
    fun <T> T.applyRules(vararg rules: Rule<T>): T {
        rules.forEach {
            it.runValidation(
                context = this@ValidationContext,
                value = this@applyRules,
            )
        }
        return this
    }
}

/**
 * Converts [message] into [io.github.kverify.core.violation.StringViolation]
 * and handles a validation failure.
 */
fun ValidationContext.onFailure(message: String): Unit =
    onFailure(
        message.asViolation(),
    )

/**
 * @return this value after applying all [rules] within a given [context].
 */
fun <T> T.applyRules(
    context: ValidationContext,
    vararg rules: Rule<T>,
): T =
    context.run validationContext@{
        this@applyRules.applyRules(rules = rules)
    }

/**
 * Applies given [rules] to [Unit] within the current validation context.
 */
fun ValidationContext.applyUnitRules(vararg rules: Rule<Unit>): Unit =
    rules.forEach {
        it.runValidation(
            context = this@applyUnitRules,
            value = Unit,
        )
    }

/**
 * Calls [ValidationContext.onFailure] with the result of calling [violationGenerator]
 * if the [condition] is `false`.
 */
inline fun ValidationContext.validate(
    condition: Boolean,
    violationGenerator: () -> Violation,
) {
    if (!condition) {
        val violation = violationGenerator()
        onFailure(violation)
    }
}
