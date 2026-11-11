package io.github.kverify.rule.set

import de.infix.testBalloon.framework.core.testSuite
import io.github.kverify.core.context.pathName
import io.github.kverify.rule.set.util.assertNoViolations
import io.github.kverify.rule.set.util.assertViolationPath
import io.github.kverify.rule.set.util.namePath
import io.github.kverify.rule.set.util.violationFor
import io.github.kverify.rule.set.util.violationsFor
import io.github.kverify.rule.set.violation.NotNullViolation
import kotlin.test.assertEquals
import kotlin.test.assertIs

val AnyRulesTest by testSuite {
    testSuite("notNull") {
        test("reports a null value") {
            val violation = violationFor<String?>(null) { notNull() }

            assertIs<NotNullViolation>(violation)
        }

        test("does not report a value that is not null") {
            val violations = violationsFor<String?>("a") { notNull() }

            assertNoViolations(violations)
        }

        test("does not report an empty value") {
            val violations = violationsFor<String?>("") { notNull() }

            assertNoViolations(violations)
        }

        test("uses the default reason") {
            val violation = violationFor<String?>(null) { notNull() }

            assertEquals("Must not be null", violation.reason)
        }

        test("uses the given reason") {
            val violation = violationFor<String?>(null) { notNull("custom") }

            assertEquals("custom", violation.reason)
        }

        test("reports the path of the context") {
            val context = pathName("field")

            val violation = violationFor<String?>(null, context) { notNull() }

            val expectedPath = namePath("field")
            assertViolationPath(expectedPath, violation)
        }
    }
}
