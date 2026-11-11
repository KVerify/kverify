package io.github.kverify.core.strategy

import de.infix.testBalloon.framework.core.testSuite
import io.github.kverify.core.context.EmptyValidationContext
import io.github.kverify.core.context.ValidationContext
import io.github.kverify.core.context.pathName
import io.github.kverify.core.scope.failWith
import io.github.kverify.core.violation.Violation
import io.github.kverify.core.violation.ViolationReason
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertSame
import kotlin.test.assertTrue

val CollectingValidationStrategyTest by testSuite {
    testSuite("failWith") {
        test("adds the violation to the given collection") {
            val storage = mutableListOf<Violation>()
            val strategy = CollectingValidationStrategy(storage)
            val violation = ViolationReason("a")

            strategy.failWith(violation)

            assertEquals(listOf<Violation>(violation), storage)
        }

        test("adds the violations in the order they were reported") {
            val storage = mutableListOf<Violation>()
            val strategy = CollectingValidationStrategy(storage)
            val first = ViolationReason("a")
            val second = ViolationReason("b")

            strategy.failWith(first)
            strategy.failWith(second)

            assertEquals(listOf<Violation>(first, second), storage)
        }
    }

    testSuite("validateCollecting") {
        test("returns the violations in the order they were reported") {
            val first = ViolationReason("a")
            val second = ViolationReason("b")

            val result =
                validateCollecting {
                    failWith(first)
                    failWith(second)
                }

            assertEquals(listOf<Violation>(first, second), result)
        }

        test("returns an empty list when nothing is reported") {
            val result = validateCollecting { }

            assertTrue(result.isEmpty())
        }

        test("keeps running the block after a violation") {
            var reachedAfter = false

            validateCollecting {
                failWith(ViolationReason("a"))
                reachedAfter = true
            }

            assertTrue(reachedAfter)
        }

        test("calls the block once") {
            var calls = 0

            validateCollecting { calls++ }

            assertEquals(1, calls)
        }

        test("runs the block in a scope with the given context") {
            val context = pathName("a")
            var captured: ValidationContext? = null

            validateCollecting(context) { captured = validationContext }

            assertSame(context, captured)
        }

        test("runs the block in a scope with an empty context by default") {
            var captured: ValidationContext? = null

            validateCollecting { captured = validationContext }

            assertSame(EmptyValidationContext, captured)
        }

        test("runs the block in a scope with a collecting strategy") {
            var captured: ValidationStrategy? = null

            validateCollecting { captured = validationStrategy }

            assertIs<CollectingValidationStrategy>(captured)
        }
    }
}
