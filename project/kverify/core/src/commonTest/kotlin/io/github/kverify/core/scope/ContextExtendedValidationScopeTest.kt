package io.github.kverify.core.scope

import io.github.kverify.core.context.EmptyValidationContext
import io.github.kverify.core.context.IndexPathElement
import io.github.kverify.core.context.NamePathElement
import io.github.kverify.core.violation.Violation
import io.github.kverify.core.violation.violation
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertSame
import kotlin.test.assertTrue

class ContextExtendedValidationScopeTest {
    private fun collectingScope(
        context: io.github.kverify.core.context.ValidationContext = EmptyValidationContext,
    ): Pair<MutableList<Violation>, CollectingValidationScope> {
        val storage = mutableListOf<Violation>()
        return storage to CollectingValidationScope(storage, context)
    }

    @Test
    fun validationContextCombinesOriginalAndAdditional() {
        val originalElement = NamePathElement("original")
        val additionalElement = NamePathElement("additional")
        val (_, original) = collectingScope(originalElement)

        val extended = ContextExtendedValidationScope(original, additionalElement)

        assertEquals(listOf(originalElement, additionalElement), extended.validationContext.toList())
    }

    @Test
    fun validationContextWhenOriginalIsEmptyContainsOnlyAdditional() {
        val additionalElement = NamePathElement("extra")
        val (_, original) = collectingScope()

        val extended = ContextExtendedValidationScope(original, additionalElement)

        assertEquals(listOf(additionalElement), extended.validationContext.toList())
    }

    @Test
    fun validationContextWhenAdditionalIsEmptyContainsOnlyOriginal() {
        val originalElement = IndexPathElement(0)
        val (_, original) = collectingScope(originalElement)

        val extended = ContextExtendedValidationScope(original, EmptyValidationContext)

        assertEquals(listOf(originalElement), extended.validationContext.toList())
    }

    @Test
    fun validationContextWithBothEmptyIsEmpty() {
        val (_, original) = collectingScope()

        val extended = ContextExtendedValidationScope(original, EmptyValidationContext)

        assertEquals(emptyList(), extended.validationContext.toList())
    }

    @Test
    fun validationContextPreservesOrderOriginalBeforeAdditional() {
        val first = NamePathElement("first")
        val second = IndexPathElement(1)
        val third = NamePathElement("third")
        val (_, original) = collectingScope(first + second)

        val extended = ContextExtendedValidationScope(original, third)

        assertEquals(listOf(first, second, third), extended.validationContext.toList())
    }

    @Test
    fun originalValidationScopeIsStoredByReference() {
        val (_, original) = collectingScope()

        val extended = ContextExtendedValidationScope(original, EmptyValidationContext)

        assertSame(original, extended.originalValidationScope)
    }

    @Test
    fun enforceIsFullyDelegatedToOriginalScope() {
        val (storage, original) = collectingScope()
        val extended = ContextExtendedValidationScope(original, NamePathElement("extra"))
        val v = violation("delegated")

        extended.enforce { v }

        assertEquals(listOf(v), storage)
    }

    @Test
    fun passingRuleDoesNotAddToStorageViaDelegate() {
        val (storage, original) = collectingScope()
        val extended = ContextExtendedValidationScope(original, NamePathElement("extra"))

        extended.enforce { null }

        assertTrue(storage.isEmpty())
    }

    @Test
    fun multipleFailingRulesAreAllDelegatedToOriginalStorage() {
        val (storage, original) = collectingScope()
        val extended = ContextExtendedValidationScope(original, NamePathElement("extra"))
        val v1 = violation("first")
        val v2 = violation("second")

        extended.enforce { v1 }
        extended.enforce { v2 }

        assertEquals(listOf(v1, v2), storage)
    }

    @Test
    fun originalScopeContextIsUnchangedAfterExtension() {
        val originalElement = NamePathElement("original")
        val (_, original) = collectingScope(originalElement)

        ContextExtendedValidationScope(original, NamePathElement("extra"))

        assertEquals(listOf(originalElement), original.validationContext.toList())
    }
}
