package io.github.kverify.scope

import io.github.kverify.context.EmptyValidationContext
import io.github.kverify.context.ListValidationContext
import io.github.kverify.context.ValidationPathElement
import io.github.kverify.rule.ViolationReason
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertTrue

class ValidationScopeTest {
    @Test
    fun plusAddsContextToScope() {
        val scope = CollectingValidationScope()
        val context = ListValidationContext(ValidationPathElement.Property("name"))

        val extended = scope + context

        assertIs<ValidationScope>(extended)
        assertEquals(context.elements, extended.validationContext.elements)
    }

    @Test
    fun plusChainsMultipleContexts() {
        val scope = CollectingValidationScope()
        val context1 = ListValidationContext(ValidationPathElement.Property("user"))
        val context2 = ListValidationContext(ValidationPathElement.Property("name"))

        val extended = scope + context1 + context2

        assertEquals(
            listOf(
                ValidationPathElement.Property("user"),
                ValidationPathElement.Property("name"),
            ),
            extended.validationContext.elements,
        )
    }

    @Test
    fun failIfCallsOnFailureWhenConditionIsTrue() {
        val scope = CollectingValidationScope()

        scope.failIf(true) { ViolationReason("failed") }

        val result = scope.build()
        assertTrue(result.isInvalid)
    }

    @Test
    fun failIfDoesNotCallOnFailureWhenConditionIsFalse() {
        val scope = CollectingValidationScope()

        scope.failIf(false) { ViolationReason("failed") }

        val result = scope.build()
        assertTrue(result.isValid)
    }

    @Test
    fun verifyValueReturnsScopedVerification() {
        val scope = CollectingValidationScope()

        val verification = scope verify "hello"

        assertIs<ScopedVerification<String>>(verification)
        assertEquals("hello", verification.value)
    }

    @Test
    fun verifyPropertyReturnsScopedVerificationWithPath() {
        val testObj = TestData("hello")
        val scope = CollectingValidationScope()

        val verification = scope verify testObj::name

        assertEquals("hello", verification.value)
        assertEquals(
            listOf(ValidationPathElement.Property("name")),
            verification.scope.validationContext.elements,
        )
    }

    @Test
    fun verifyWithPathElementsAddsContext() {
        val scope = CollectingValidationScope()
        val path =
            listOf(
                ValidationPathElement.Property("users"),
                ValidationPathElement.Index(0),
            )

        val verification = scope.verify(path, "value")

        assertIs<ScopedVerification<String>>(verification)
        assertEquals(
            path,
            verification.scope.validationContext.elements,
        )
    }

    @Test
    fun verifyWithEmptyPathDoesNotExtendContext() {
        val scope = CollectingValidationScope()

        val verification = scope.verify(emptyList(), "value")

        assertIs<ScopedVerification<String>>(verification)
        assertEquals(EmptyValidationContext.elements, verification.scope.validationContext.elements)
    }

    private data class TestData(
        val name: String,
    )
}
