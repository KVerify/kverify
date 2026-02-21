package io.github.kverify.rule.set

import io.github.kverify.context.ValidationPathElement
import io.github.kverify.rule.Violation

internal interface PathAwareViolation : Violation {
    val validationPath: List<ValidationPathElement>
}
