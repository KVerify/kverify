package io.github.kverify.scope

import io.github.kverify.context.EmptyValidationContext
import io.github.kverify.context.ListValidationContext
import io.github.kverify.context.ValidationPathElement
import io.github.kverify.result.ValidationResult
import io.github.kverify.rule.ViolationReason
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertSame
import kotlin.test.assertTrue

class CollectingValidationScopeTest {
    @Test
    fun defaultContextIsEmpty() {
        val scope = CollectingValidationScope()

        assertSame(EmptyValidationContext, scope.validationContext)
    }

    @Test
    fun customContextIsPreserved() {
        val context = ListValidationContext(ValidationPathElement.Property("user"))
        val scope = CollectingValidationScope(validationContext = context)

        assertSame(context, scope.validationContext)
    }

    @Test
    fun onFailureCollectsViolations() {
        val scope = CollectingValidationScope()

        scope.onFailure(ViolationReason("error1"))
        scope.onFailure(ViolationReason("error2"))

        val result = scope.build()
        assertIs<ValidationResult.Invalid>(result)
        assertEquals(2, result.violations.size)
        assertEquals("error1", result.violations[0].reason)
        assertEquals("error2", result.violations[1].reason)
    }

    @Test
    fun buildReturnsValidWhenNoViolations() {
        val scope = CollectingValidationScope()

        val result = scope.build()

        assertIs<ValidationResult.Valid>(result)
    }

    @Test
    fun buildReturnsInvalidWithViolations() {
        val scope = CollectingValidationScope()
        scope.onFailure(ViolationReason("error"))

        val result = scope.build()

        assertIs<ValidationResult.Invalid>(result)
        assertEquals(1, result.violations.size)
    }

    @Test
    fun verifyWithCollectingReturnsValidOnSuccess() {
        val result =
            verifyWithCollecting {
                failIf(false) { ViolationReason("should not appear") }
            }

        assertTrue(result.isValid)
    }

    @Test
    fun verifyWithCollectingReturnsInvalidOnFailure() {
        val result =
            verifyWithCollecting {
                failIf(true) { ViolationReason("error1") }
                failIf(true) { ViolationReason("error2") }
            }

        assertIs<ValidationResult.Invalid>(result)
        assertEquals(2, result.violations.size)
    }

    @Test
    fun verifyWithCollectingCollectsAllViolations() {
        val result =
            verifyWithCollecting {
                failIf(true) { ViolationReason("first") }
                failIf(false) { ViolationReason("skipped") }
                failIf(true) { ViolationReason("third") }
            }

        assertIs<ValidationResult.Invalid>(result)
        assertEquals(2, result.violations.size)
        assertEquals("first", result.violations[0].reason)
        assertEquals("third", result.violations[1].reason)
    }

    @Test
    fun verifyWithCollectingAcceptsCustomContext() {
        val context = ListValidationContext(ValidationPathElement.Property("user"))

        val result =
            verifyWithCollecting(validationContext = context) {
                assertEquals(
                    listOf(ValidationPathElement.Property("user")),
                    validationContext.elements,
                )
            }

        assertTrue(result.isValid)
    }
}
