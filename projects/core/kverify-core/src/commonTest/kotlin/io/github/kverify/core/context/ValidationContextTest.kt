package io.github.kverify.core.context

import io.github.kverify.core.model.Rule
import io.github.kverify.core.violation.StringViolation
import io.github.kverify.core.violation.Violation
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import kotlin.test.assertContains
import kotlin.test.fail

class ValidationContextTest :
    FunSpec({

        test("onFailure captures the violation") {
            val violations = mutableListOf<Violation>()
            val context = ValidationContext { violations.add(it) }

            val testViolation = StringViolation("Test Error")
            context.onFailure(testViolation)

            violations.size shouldBe 1
            assertContains(violations, testViolation)
        }

        test("applyRules executes rules and records failures") {
            val violations = mutableListOf<Violation>()
            val context = ValidationContext { violations.add(it) }

            val rule1 = DummyRule<Int>(true, StringViolation("Rule 1 failed"))
            val rule2 = DummyRule<Int>(false, StringViolation("Rule 2 failed"))

            42.applyRules(context, rule1, rule2)

            violations.size shouldBe 1
            assertContains(violations, rule1.violation)
        }

        test("validate triggers onFailure when condition is false") {
            val violations = mutableListOf<Violation>()
            val context = ValidationContext { violations.add(it) }
            val testViolation = StringViolation("Condition failed")

            context.validate(false) { testViolation }

            assertContains(violations, testViolation)
        }

        test("validate does not trigger onFailure when condition is true") {
            val context = ValidationContext { fail("onFailure was triggered") }

            context.validate(true) { StringViolation("Condition failed") }
        }

        test("validate does not trigger violationGenerator lambda when condition is true") {
            val context = ValidationContext { }

            context.validate(true) { fail("violationGenerator was triggered") }
        }
    })

private class DummyRule<T>(
    val shouldFail: Boolean,
    val violation: Violation,
) : Rule<T> {
    override fun ValidationContext.runValidation(value: T) {
        if (shouldFail) onFailure(violation)
    }
}
