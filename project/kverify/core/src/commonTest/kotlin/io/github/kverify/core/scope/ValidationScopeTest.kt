package io.github.kverify.core.scope

import io.github.kverify.core.context.EmptyValidationContext
import io.github.kverify.core.context.IndexPathElement
import io.github.kverify.core.context.NamePathElement
import io.github.kverify.core.context.ValidationContext
import io.github.kverify.core.context.ValidationPathElement
import io.github.kverify.core.context.validationPath
import io.github.kverify.core.violation.Violation
import io.github.kverify.core.violation.violation
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertSame
import kotlin.test.assertTrue
import kotlin.test.fail

class ValidationScopeTest {
    private fun collectingScope(context: ValidationContext = EmptyValidationContext): CollectingValidationScope =
        CollectingValidationScope(mutableListOf(), context)

    private fun collectingScopeWithStorage(
        context: ValidationContext = EmptyValidationContext,
    ): Pair<MutableList<Violation>, CollectingValidationScope> {
        val storage = mutableListOf<Violation>()
        return storage to CollectingValidationScope(storage, context)
    }

    @Test
    fun plusReturnsNewScopeWithCombinedContext() {
        val originalElement = NamePathElement("original")
        val additionalElement = NamePathElement("additional")
        val scope = collectingScope(originalElement)

        val extended = scope + additionalElement

        assertEquals(listOf(originalElement, additionalElement), extended.validationContext.toList())
    }

    @Test
    fun plusEmptyContextReturnsSameScope() {
        val scope = collectingScope()

        val result = scope + EmptyValidationContext

        assertSame(scope, result)
    }

    @Test
    fun plusDoesNotMutateOriginalScopeContext() {
        val originalElement = NamePathElement("original")
        val scope = collectingScope(originalElement)

        scope + NamePathElement("extra")

        assertEquals(listOf(originalElement), scope.validationContext.toList())
    }

    @Test
    fun plusReturnsContextExtendedValidationScope() {
        val scope = collectingScope()

        val extended = scope + NamePathElement("extra")

        assertTrue(extended is ContextExtendedValidationScope<*>)
    }

    @Test
    fun verifyValueBindsValueToVerification() {
        val scope = collectingScope()
        val value = "hello"

        val verification = scope.verify(value)

        assertSame(value, verification.value)
    }

    @Test
    fun verifyValueBindsCurrentScopeToVerification() {
        val scope = collectingScope()

        val verification = scope.verify("anything")

        assertSame(scope, verification.scope)
    }

    @Test
    fun verifyPropertyBindsPropertyValueToVerification() {
        data class Subject(
            val age: Int,
        )
        val subject = Subject(age = 25)
        val scope = collectingScope()

        val verification = scope.verify(subject::age)

        assertEquals(subject.age, verification.value)
    }

    @Test
    fun verifyPropertyAppendsPropertyNameToContext() {
        data class Subject(
            val username: String,
        )
        val subject = Subject(username = "alice")
        val scope = collectingScope()

        val verification = scope.verify(subject::username)

        assertEquals(listOf(NamePathElement("username")), verification.scope.validationContext.validationPath())
    }

    @Test
    fun verifyPropertyDoesNotMutateOriginalScopeContext() {
        data class Subject(
            val email: String,
        )
        val subject = Subject(email = "a@b.com")
        val scope = collectingScope()

        scope.verify(subject::email)

        assertEquals(EmptyValidationContext, scope.validationContext)
    }

    @Test
    fun verifyPropertyWithExistingContextAppendsPropNameAfterExisting() {
        data class Subject(
            val street: String,
        )
        val subject = Subject(street = "Main St")
        val parentElement = NamePathElement("address")
        val scope = collectingScope(parentElement)

        val verification = scope.verify(subject::street)

        assertEquals(
            listOf(parentElement, NamePathElement("street")),
            verification.scope.validationContext.validationPath(),
        )
    }

    @Test
    fun failIfDoesNotEnforceViolationWhenPredicateReturnsFalse() {
        val (storage, scope) = collectingScopeWithStorage()

        scope.failIf({ false }) { violation("should not be called") }

        assertTrue(storage.isEmpty())
    }

    @Test
    fun failIfEnforcesViolationWhenPredicateReturnsTrue() {
        val (storage, scope) = collectingScopeWithStorage()
        val v = violation("condition met")

        scope.failIf({ true }) { v }

        assertEquals(listOf(v), storage)
    }

    @Test
    fun failIfDoesNotCallViolationLambdaWhenPredicateReturnsFalse() {
        val scope = collectingScope()
        var violationLambdaCalled = false

        scope.failIf({ false }) {
            violationLambdaCalled = true
            violation("should not be called")
        }

        assertFalse(violationLambdaCalled)
    }

    @Test
    fun pathNameReturnsNewScopeWithNameAppendedToPath() {
        val name = "address"
        val scope = collectingScope()

        val extended = scope.pathName(name)

        assertEquals(listOf(NamePathElement(name)), extended.validationContext.validationPath())
    }

    @Test
    fun pathNameDoesNotMutateOriginalScope() {
        val scope = collectingScope()

        scope.pathName("field")

        assertEquals(EmptyValidationContext, scope.validationContext)
    }

    @Test
    fun pathNameWithBlockExecutesBlockInNewScope() {
        val (storage, scope) = collectingScopeWithStorage()
        val v = violation("nested")

        scope.pathName("parent") { enforce { v } }

        assertEquals(listOf(v), storage)
    }

    @Test
    fun pathNameWithBlockReceivesCorrectPath() {
        val name = "user"
        val scope = collectingScope()
        var capturedPath: List<ValidationPathElement>? = null

        scope.pathName(name) { capturedPath = validationContext.validationPath() }

        assertEquals(listOf(NamePathElement(name)), capturedPath)
    }

    @Test
    fun pathNameWithBlockReturnsExtendedScope() {
        val name = "items"
        val scope = collectingScope()

        val returned = scope.pathName(name) {}

        assertEquals(listOf(NamePathElement(name)), returned.validationContext.validationPath())
    }

    @Test
    fun pathIndexReturnsNewScopeWithIndexAppendedToPath() {
        val index = 3
        val scope = collectingScope()

        val extended = scope.pathIndex(index)

        assertEquals(listOf(IndexPathElement(index)), extended.validationContext.validationPath())
    }

    @Test
    fun pathIndexDoesNotMutateOriginalScope() {
        val scope = collectingScope()

        scope.pathIndex(0)

        assertEquals(EmptyValidationContext, scope.validationContext)
    }

    @Test
    fun pathIndexWithBlockExecutesBlockInNewScope() {
        val (storage, scope) = collectingScopeWithStorage()
        val v = violation("indexed")

        scope.pathIndex(1) { enforce { v } }

        assertEquals(listOf(v), storage)
    }

    @Test
    fun pathIndexWithBlockReceivesCorrectPath() {
        val index = 5
        val scope = collectingScope()
        var capturedPath: List<ValidationPathElement>? = null

        scope.pathIndex(index) { capturedPath = validationContext.validationPath() }

        assertEquals(listOf(IndexPathElement(index)), capturedPath)
    }

    @Test
    fun pathIndexWithBlockReturnsExtendedScope() {
        val index = 7
        val scope = collectingScope()

        val returned = scope.pathIndex(index) {}

        assertEquals(listOf(IndexPathElement(index)), returned.validationContext.validationPath())
    }
}
