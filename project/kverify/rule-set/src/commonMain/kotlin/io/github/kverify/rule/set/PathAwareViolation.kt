package io.github.kverify.rule.set

import io.github.kverify.core.ValidationPathElement
import io.github.kverify.core.Violation

internal interface PathAwareViolation : Violation {
    val validationPath: List<ValidationPathElement>
}
