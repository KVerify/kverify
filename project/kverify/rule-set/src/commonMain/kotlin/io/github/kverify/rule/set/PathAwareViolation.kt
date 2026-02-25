package io.github.kverify.rule.set

import io.github.kverify.core.context.ValidationPathElement
import io.github.kverify.core.rule.Violation

internal interface PathAwareViolation : Violation {
    val validationPath: List<ValidationPathElement>
}
