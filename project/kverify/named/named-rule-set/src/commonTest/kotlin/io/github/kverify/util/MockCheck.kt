package io.github.kverify.util

import io.github.kverify.core.rule.predicate.check.ValidationCheck
import io.github.kverify.named.check.NamedValidationCheckAdapter
import kotlin.test.assertIs
import kotlin.test.assertSame

class MockCheck(
    val name: String,
) : ValidationCheck<Any?> {
    override fun isValid(value: Any?) = false
}

fun assertName(
    check: ValidationCheck<*>,
    expectedName: String,
) {
    val check =
        if (check is NamedValidationCheckAdapter<*>) {
            check.baseCheck
        } else {
            check
        }

    assertIs<MockCheck>(check)
    assertSame(expectedName, check.name)
}
