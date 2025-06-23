package io.github.kverify.core.context

import io.github.kverify.core.model.Rule
import io.github.kverify.core.model.runValidation
import io.github.kverify.core.violation.Violation

/**
 * The core interface for handling validation failures within the kverify validation library.
 *
 * This functional interface defines the contract for processing validation violations.
 * Different implementations can handle failures in various ways - throwing exceptions immediately,
 * accumulating violations for later processing, or logging errors.
 *
 * The interface serves as the foundation for all validation contexts in the library,
 * enabling flexible validation strategies through different implementations.
 */
public fun interface ValidationContext {
    /**
     * Processes a validation failure for the given violation.
     *
     * This method is called whenever a validation rule fails.
     * The implementation determines how the violation is handled - whether to throw an exception,
     * store it for later processing, log it, or take other appropriate action.
     *
     * Different context implementations provide different failure handling strategies:
     * - Throwing contexts immediately terminate with exceptions
     * - Aggregating contexts collect violations for batch-processing
     * - Custom contexts can implement domain-specific failure handling
     *
     * @param violation The validation violation that occurred.
     */
    public fun onFailure(violation: Violation)

    /**
     * Applies multiple validation rules to the receiver object within the current validation context.
     *
     * Executes each provided rule against the receiver object,
     * using the current validation context to handle any failures.
     * Rules are applied sequentially,
     * and the [onFailure] behavior depends on the specific context implementation.
     *
     * Returns the original receiver object to enable method chaining and fluent validation syntax.
     * This allows for convenient validation patterns where the validated object continues
     * through the processing pipeline.
     *
     * @param rules The validation rules to apply to the receiver object.
     * @return The original receiver object after all rules have been applied.
     */
    public fun <T> T.applyRules(vararg rules: Rule<T>): T {
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
 * Applies multiple validation rules to the receiver object using the specified context.
 *
 * This standalone function provides an alternative to the extension method approach,
 * allowing explicit specification of the validation context. Rules are applied sequentially
 * against the receiver object, with the provided context handling any failures.
 *
 * Returns the original receiver object to enable method chaining and integration
 * with existing processing pipelines. The explicit context parameter makes this
 * function suitable for scenarios where the context needs to be passed explicitly
 * rather than being available in the receiver scope.
 *
 * Best suited for functional programming patterns, library integration scenarios,
 * and cases where explicit context control is preferred over extension methods.
 *
 * @param context The validation context to use for handling failures.
 * @param rules The validation rules to apply to the receiver object.
 * @return The original receiver object after all rules have been applied.
 */
public fun <T> T.applyRulesUsing(
    context: ValidationContext,
    vararg rules: Rule<T>,
): T =
    context.run validationContext@{
        this@applyRulesUsing.applyRules(rules = rules)
    }

/**
 * Applies validation rules that operate on Unit values within the current context.
 *
 * This specialized method handles validation rules that don't require specific input values
 * but instead validate general conditions, global state, or perform contextual checks.
 * Each rule is executed with Unit as the value, allowing for validation logic that
 * focuses on conditions rather than data transformation.
 *
 * Best suited for validation scenarios involving:
 * - Global state validation
 * - Contextual precondition checks
 * - Environment validation
 * - Configuration validation
 * - Any validation logic that doesn't depend on specific input values
 *
 * @param rules The Unit-based validation rules to execute within this context.
 */
public fun ValidationContext.applyUnitRules(vararg rules: Rule<Unit>): Unit =
    rules.forEach {
        it.runValidation(
            context = this@applyUnitRules,
            value = Unit,
        )
    }

/**
 * Conditionally triggers a validation failure based on a boolean condition.
 *
 * Evaluates the provided condition and triggers validation failure if the condition is false.
 * The violation is generated only when needed (lazy evaluation), avoiding unnecessary
 * object creation when validation passes.
 *
 * This method provides a convenient way to perform conditional validation checks
 * without requiring explicit if-else logic in validation code. The inline nature
 * ensures efficient execution while the lambda-based violation generation enables
 * lazy evaluation and custom violation creation.
 *
 * Best suited for:
 * - Precondition validation
 * - Business rule enforcement
 * - Custom validation logic
 * - Conditional checks within larger validation workflows
 *
 * @param condition The boolean condition to evaluate - validation fails if false.
 * @param violationGenerator A lambda that produces the violation when validation fails.
 */
public inline fun ValidationContext.validate(
    condition: Boolean,
    violationGenerator: () -> Violation,
) {
    if (!condition) {
        val violation = violationGenerator()
        onFailure(violation)
    }
}
