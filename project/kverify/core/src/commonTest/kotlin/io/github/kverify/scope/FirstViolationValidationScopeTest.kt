package io.github.kverify.scope

import io.github.kverify.context.EmptyValidationContext
import io.github.kverify.context.ListValidationContext
import io.github.kverify.context.ValidationPathElement
import io.github.kverify.rule.Rule
import io.github.kverify.rule.ViolationReason
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertSame
import kotlin.test.assertTrue

class FirstViolationValidationScopeTest {
    private fun notEmptyRule(): Rule<String> =
        object : Rule<String> {
            override fun execute(
                scope: ValidationScope,
                value: String,
            ) {
                scope.failIf(value.isEmpty()) { ViolationReason("empty") }
            }
        }

    private fun minLengthRule(min: Int): Rule<String> =
        object : Rule<String> {
            override fun execute(
                scope: ValidationScope,
                value: String,
            ) {
                scope.failIf(value.length < min) { ViolationReason("too short") }
            }
        }

    @Test
    fun defaultContextIsEmpty() {
        val scope = FirstViolationValidationScope()

        assertSame(EmptyValidationContext, scope.validationContext)
    }

    @Test
    fun firstViolationIsNullByDefault() {
        val scope = FirstViolationValidationScope()

        assertNull(scope.firstViolation)
    }

    @Test
    fun onFailureStoresFirstViolation() {
        val scope = FirstViolationValidationScope()
        val violation1 = ViolationReason("first")
        val violation2 = ViolationReason("second")

        scope.onFailure(violation1)
        scope.onFailure(violation2)

        assertEquals(violation1, scope.firstViolation)
    }

    @Test
    fun verifyWithFirstViolationReturnsNullOnSuccess() {
        val result =
            verifyWithFirstViolation {
                failIf(false) { ViolationReason("should not appear") }
            }

        assertNull(result)
    }

    @Test
    fun verifyWithFirstViolationReturnsFirstViolation() {
        val result =
            verifyWithFirstViolation {
                failIf(true) { ViolationReason("first") }
                failIf(true) { ViolationReason("second") }
            }

        assertEquals("first", result?.reason)
    }

    @Test
    fun verifyWithFirstViolationAcceptsCustomContext() {
        val context = ListValidationContext(ValidationPathElement.Property("user"))

        val result =
            verifyWithFirstViolation(context) {
                assertEquals(
                    listOf(ValidationPathElement.Property("user")),
                    validationContext.elements,
                )
            }

        assertNull(result)
    }

    @Test
    fun passesReturnsTrueWhenRulePasses() {
        assertTrue("hello" passes notEmptyRule())
    }

    @Test
    fun passesReturnsFalseWhenRuleFails() {
        assertFalse("" passes notEmptyRule())
    }

    @Test
    fun passesWithIterableReturnsTrueWhenAllPass() {
        val rules = listOf(notEmptyRule(), minLengthRule(3))

        assertTrue("hello" passes rules)
    }

    @Test
    fun passesWithIterableReturnsFalseWhenAnyFails() {
        val rules = listOf(notEmptyRule(), minLengthRule(3))

        assertFalse("hi" passes rules)
    }

    @Test
    fun passesWithVarargWorks() {
        assertTrue("hello".passes(notEmptyRule(), minLengthRule(3)))
        assertFalse("hi".passes(notEmptyRule(), minLengthRule(3)))
    }

    @Test
    fun notPassesReturnsTrueWhenRuleFails() {
        assertTrue("" notPasses notEmptyRule())
    }

    @Test
    fun notPassesReturnsFalseWhenRulePasses() {
        assertFalse("hello" notPasses notEmptyRule())
    }

    @Test
    fun notPassesWithIterableWorks() {
        val rules = listOf(notEmptyRule())

        assertTrue("" notPasses rules)
        assertFalse("hello" notPasses rules)
    }

    @Test
    fun notPassesWithVarargWorks() {
        assertTrue("".notPasses(notEmptyRule()))
        assertFalse("hello".notPasses(notEmptyRule()))
    }
}
