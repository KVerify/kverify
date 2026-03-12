package io.github.kverify.core.scope

import io.github.kverify.core.context.NamePathElement
import io.github.kverify.core.context.validationPath
import io.github.kverify.core.violation.Violation
import io.github.kverify.core.violation.violation
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class CollectingValidationScopeTest {
    @Test
    fun noViolations_returnsValidResult() {
        val result = validateCollecting {}

        assertTrue(result.isValid)
        assertEquals(emptyList(), result.violations)
    }

    @Test
    fun collectsAllViolations() {
        val first = violation("first error")
        val second = violation("second error")

        val result =
            validateCollecting {
                enforce { first }
                enforce { second }
            }

        assertEquals(listOf(first, second), result.violations)
    }

    @Test
    fun continuesAfterFailure() {
        var secondRuleReached = false

        validateCollecting {
            enforce { violation("first") }
            secondRuleReached = true
            enforce { null }
        }

        assertTrue(secondRuleReached)
    }

    @Test
    fun customValidationContext_applied() {
        val contextName = "root"

        validateCollecting(validationContext = NamePathElement(contextName)) {
            val path = validationContext.validationPath()

            assertEquals(NamePathElement(contextName), path.single())
        }
    }

    @Test
    fun failIf_addsViolationWhenTrue() {
        val expected = violation("condition met")

        val result = validateCollecting { failIf({ true }) { expected } }

        assertEquals(expected, result.violations.single())
    }

    @Test
    fun failIf_skipsViolationWhenFalse() {
        val result = validateCollecting { failIf({ false }) { violation("unreachable") } }

        assertTrue(result.isValid)
    }

    @Test
    fun nullReturningRule_addsNoViolation() {
        val result = validateCollecting { enforce { null } }

        assertTrue(result.isValid)
    }

    @Test
    fun preservesViolationOrder() {
        val violations = (1..5).map { violation("error $it") }

        val result =
            validateCollecting {
                violations.forEach { v -> enforce { v } }
            }

        assertEquals(violations, result.violations)
    }
}
