package io.github.kverify.core.scope

import io.github.kverify.core.result.ValidationResult
import io.github.kverify.core.rule.Rule
import io.github.kverify.core.rule.ViolationReason
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertNull
import kotlin.test.assertTrue

class VerificationTest {
    private fun notEmptyRule(): Rule<String> =
        object : Rule<String> {
            override fun execute(
                scope: ValidationScope,
                value: String,
            ) {
                scope.failIf(value.isEmpty()) { ViolationReason("empty") }
            }
        }

    private fun failingRule(reason: String): Rule<String> =
        object : Rule<String> {
            override fun execute(
                scope: ValidationScope,
                value: String,
            ) {
                scope.onFailure(ViolationReason(reason))
            }
        }

    @Test
    fun enforceExecutesRule() {
        val scope = CollectingValidationScope()
        val verification = ScopedVerification("hello", scope)

        verification.enforce(notEmptyRule())

        assertTrue(scope.build().isValid)
    }

    @Test
    fun enforceExecutesRuleWithFailure() {
        val scope = CollectingValidationScope()
        val verification = ScopedVerification("", scope)

        verification.enforce(notEmptyRule())

        assertTrue(scope.build().isInvalid)
    }

    @Test
    fun usingDelegatesToEnforce() {
        val scope = CollectingValidationScope()
        val verification = ScopedVerification("", scope)

        verification using notEmptyRule()

        assertTrue(scope.build().isInvalid)
    }

    @Test
    fun usingWithIterableExecutesAllRules() {
        val scope = CollectingValidationScope()
        val verification = ScopedVerification("", scope)
        val rules = listOf(failingRule("rule1"), failingRule("rule2"))

        verification using rules

        val result = scope.build()
        assertIs<ValidationResult.Invalid>(result)
        assertEquals(2, result.violations.size)
    }

    @Test
    fun usingWithVarargExecutesAllRules() {
        val scope = CollectingValidationScope()
        val verification = ScopedVerification("", scope)

        verification.using(failingRule("rule1"), failingRule("rule2"))

        val result = scope.build()
        assertIs<ValidationResult.Invalid>(result)
        assertEquals(2, result.violations.size)
    }

    @Test
    fun ifNotNullReturnsVerificationWhenValueIsNotNull() {
        val scope = CollectingValidationScope()
        val verification = ScopedVerification<String?>("hello", scope)

        val result = verification.ifNotNull()

        assertIs<ScopedVerification<String>>(result)
        assertEquals("hello", result.value)
    }

    @Test
    fun ifNotNullReturnsNullWhenValueIsNull() {
        val scope = CollectingValidationScope()
        val verification = ScopedVerification<String?>(null, scope)

        val result = verification.ifNotNull()

        assertNull(result)
    }
}
