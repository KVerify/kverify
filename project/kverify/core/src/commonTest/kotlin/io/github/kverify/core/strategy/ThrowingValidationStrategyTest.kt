package io.github.kverify.core.strategy

import de.infix.testBalloon.framework.core.testSuite
import io.github.kverify.core.context.EmptyValidationContext
import io.github.kverify.core.context.ValidationContext
import io.github.kverify.core.context.pathName
import io.github.kverify.core.exception.ViolationException
import io.github.kverify.core.scope.failWith
import io.github.kverify.core.violation.ViolationReason
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertIs
import kotlin.test.assertSame

val ThrowingValidationStrategyTest by testSuite {
    testSuite("failWith") {
        test("throws a ViolationException with the violation") {
            val strategy = ThrowingValidationStrategy()
            val violation = ViolationReason("a")

            val exception =
                assertFailsWith<ViolationException> {
                    strategy.failWith(violation)
                }

            assertSame(violation, exception.violation)
        }
    }

    testSuite("validateThrowing") {
        test("returns the result of the block") {
            val result = validateThrowing { 42 }

            assertEquals(42, result)
        }

        test("throws a ViolationException with the reported violation") {
            val violation = ViolationReason("a")

            val exception =
                assertFailsWith<ViolationException> {
                    validateThrowing { failWith(violation) }
                }

            assertSame(violation, exception.violation)
        }

        test("stops the block at the first violation") {
            var reachedAfter = false

            assertFailsWith<ViolationException> {
                validateThrowing {
                    failWith(ViolationReason("a"))
                    reachedAfter = true
                }
            }

            assertFalse(reachedAfter)
        }

        test("calls the block once") {
            var calls = 0

            validateThrowing { calls++ }

            assertEquals(1, calls)
        }

        test("runs the block in a scope with the given context") {
            val context = pathName("a")
            var captured: ValidationContext? = null

            validateThrowing(context) { captured = validationContext }

            assertSame(context, captured)
        }

        test("runs the block in a scope with an empty context by default") {
            var captured: ValidationContext? = null

            validateThrowing { captured = validationContext }

            assertSame(EmptyValidationContext, captured)
        }

        test("runs the block in a scope with a throwing strategy") {
            var captured: ValidationStrategy? = null

            validateThrowing { captured = validationStrategy }

            assertIs<ThrowingValidationStrategy>(captured)
        }
    }
}
