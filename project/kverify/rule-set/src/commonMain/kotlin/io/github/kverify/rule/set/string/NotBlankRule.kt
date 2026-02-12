package io.github.kverify.rule.set.string

import io.github.kverify.core.PredicateRule
import io.github.kverify.core.Rule
import io.github.kverify.core.ValidationCheck
import io.github.kverify.core.ValidationPath
import io.github.kverify.core.ValidationScope
import io.github.kverify.core.ViolationFactory
import io.github.kverify.core.pathElements
import io.github.kverify.rule.set.PathAwareViolation

public class NotBlankRule(
    violationFactory: ViolationFactory<String> = NotBlankViolationFactory(),
) : PredicateRule<String>(
        validationCheck = NotBlankCheck,
        violationFactory = violationFactory,
    ) {
    public companion object : Rule<String> by NotBlankRule()
}

@Suppress("NOTHING_TO_INLINE")
public inline fun NotBlankRule(message: String): NotBlankRule =
    NotBlankRule(
        violationFactory =
            NotBlankViolationFactory(
                reason = message,
            ),
    )

public object NotBlankCheck : ValidationCheck<String> {
    override fun isValid(
        scope: ValidationScope,
        value: String,
    ): Boolean = value.isNotBlank()
}

public data class NotBlankViolation(
    override val validationPath: ValidationPath,
    override val reason: String,
) : PathAwareViolation

public class NotBlankViolationFactory(
    public val reason: String? = null,
) : ViolationFactory<String> {
    override fun createViolation(
        scope: ValidationScope,
        value: String,
    ): NotBlankViolation =
        NotBlankViolation(
            validationPath = scope.validationContext.pathElements(),
            reason = reason ?: "Value must not be blank",
        )
}
