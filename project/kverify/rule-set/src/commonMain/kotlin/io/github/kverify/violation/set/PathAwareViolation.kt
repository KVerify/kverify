package io.github.kverify.violation.set

import io.github.kverify.core.context.element.ValidationPathElement
import io.github.kverify.core.violation.Violation

internal interface PathAwareViolation : Violation {
    val validationPath: List<ValidationPathElement>
}
