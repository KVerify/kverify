package io.github.kverify.core.strategy

import de.infix.testBalloon.framework.core.testSuite
import io.github.kverify.core.util.RecordingStrategy
import io.github.kverify.core.violation.Violation
import io.github.kverify.core.violation.ViolationReason
import kotlin.test.assertEquals
import kotlin.test.assertTrue

val ValidationStrategyTest by testSuite {
    testSuite("failIf") {
        test("passes the violation to failWith when the condition is true") {
            val strategy = RecordingStrategy()
            val violation = ViolationReason("a")

            strategy.failIf(true) { violation }

            assertEquals(listOf<Violation>(violation), strategy.violations)
        }

        test("creates the violation once when the condition is true") {
            val strategy = RecordingStrategy()
            var created = 0

            strategy.failIf(true) {
                created++
                ViolationReason("a")
            }

            assertEquals(1, created)
        }

        test("does not create the violation when the condition is false") {
            val strategy = RecordingStrategy()
            var created = 0

            strategy.failIf(false) {
                created++
                ViolationReason("a")
            }

            assertEquals(0, created)
        }

        test("does not call failWith when the condition is false") {
            val strategy = RecordingStrategy()

            strategy.failIf(false) { ViolationReason("a") }

            assertTrue(strategy.violations.isEmpty())
        }
    }
}
