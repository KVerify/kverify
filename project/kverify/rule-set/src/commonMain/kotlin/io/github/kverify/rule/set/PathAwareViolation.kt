package io.github.kverify.rule.set

import io.github.kverify.core.ValidationPath
import io.github.kverify.core.Violation

internal interface PathAwareViolation : Violation {
    val validationPath: ValidationPath
}
