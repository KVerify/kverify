package io.github.kverify.core.rule

import io.github.kverify.core.result.ValidationResult
import io.github.kverify.core.scope.CollectingValidationScope
import io.github.kverify.core.scope.ValidationScope
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertTrue

class PredicateRuleTest {
    @Test
    fun executeDoesNothingWhenCheckPasses() {
        val rule =
            PredicateRule(
                validationCheck = { _, value: String -> value.isNotEmpty() },
                violationFactory = { _, _ -> ViolationReason("empty") },
            )
        val scope = CollectingValidationScope()

        rule.execute(scope, "hello")

        assertTrue(scope.build().isValid)
    }

    @Test
    fun executeReportsViolationWhenCheckFails() {
        val rule =
            PredicateRule(
                validationCheck = { _, value: String -> value.isNotEmpty() },
                violationFactory = { _, _ -> ViolationReason("empty") },
            )
        val scope = CollectingValidationScope()

        rule.execute(scope, "")

        val result = scope.build()
        assertIs<ValidationResult.Invalid>(result)
        assertEquals("empty", result.violations.first().reason)
    }

    @Test
    fun violationFactoryReceivesCorrectArguments() {
        var capturedScope: ValidationScope? = null
        var capturedValue: String? = null

        val rule =
            PredicateRule(
                validationCheck = { _, _: String -> false },
                violationFactory = { scope, value: String ->
                    capturedScope = scope
                    capturedValue = value
                    ViolationReason("error")
                },
            )
        val scope = CollectingValidationScope()

        rule.execute(scope, "test")

        assertEquals(scope, capturedScope)
        assertEquals("test", capturedValue)
    }
}
