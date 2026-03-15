package io.github.kverify.core.scope

import io.github.kverify.core.context.EmptyValidationContext
import io.github.kverify.core.context.IndexPathElement
import io.github.kverify.core.context.NamePathElement
import io.github.kverify.core.context.ValidationPathElement
import io.github.kverify.core.context.validationPath
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertSame

class VerificationTest {
    private fun emptyScope() = CollectingValidationScope(mutableListOf(), EmptyValidationContext)

    @Test
    fun storesProvidedValue() {
        val value = "test"
        val scope = emptyScope()

        val verification = Verification(value, scope)

        assertSame(value, verification.value)
    }

    @Test
    fun storesProvidedScope() {
        val scope = emptyScope()

        val verification = Verification("value", scope)

        assertSame(scope, verification.scope)
    }

    @Test
    fun takeIfNotNullReturnsSameValueWhenNotNull() {
        val value = "present"
        val verification = Verification<String?>(value, emptyScope())

        val result = verification.takeIfNotNull()

        assertEquals(value, result?.value)
    }

    @Test
    fun takeIfNotNullReturnsSameScopeWhenNotNull() {
        val scope = emptyScope()
        val verification = Verification<String?>("present", scope)

        val result = verification.takeIfNotNull()

        assertSame(scope, result?.scope)
    }

    @Test
    fun takeIfNotNullReturnsNullWhenValueIsNull() {
        val verification = Verification<String?>(null, emptyScope())

        val result = verification.takeIfNotNull()

        assertNull(result)
    }

    @Test
    fun eachWithElementBlockVisitsEachElement() {
        val elements = listOf("a", "b", "c")
        val verification = Verification(elements, emptyScope())
        val visited = mutableListOf<String>()

        verification.each { element -> visited.add(element) }

        assertEquals(elements, visited)
    }

    @Test
    fun eachWithElementBlockReceivesCorrectIndexPathElements() {
        val elements = listOf("x", "y", "z")
        val verification = Verification(elements, emptyScope())
        val capturedPaths = mutableListOf<List<ValidationPathElement>>()

        verification.each { capturedPaths.add(validationContext.validationPath().elements) }

        capturedPaths.forEachIndexed { index, path ->
            assertEquals(listOf(IndexPathElement(index)), path)
        }
    }

    @Test
    fun eachWithElementBlockReturnsSameVerification() {
        val verification = Verification(listOf(1, 2, 3), emptyScope())

        val returned = verification.each { }

        assertSame(verification, returned)
    }

    @Test
    fun eachWithElementBlockOnEmptyCollectionDoesNotVisitAnything() {
        val verification = Verification(emptyList<String>(), emptyScope())
        var visited = false

        verification.each { visited = true }

        assertEquals(false, visited)
    }

    @Test
    fun eachWithElementBlockDoesNotLeakIndexedScopeToOriginalScope() {
        val scope = emptyScope()
        val verification = Verification(listOf("only"), scope)

        verification.each { }

        assertEquals(EmptyValidationContext, scope.validationContext)
    }

    @Test
    fun eachWithIndexedBlockReceivesCorrectIndexAndElement() {
        val elements = listOf("a", "b", "c")
        val verification = Verification(elements, emptyScope())
        val captured = mutableListOf<Pair<Int, String>>()

        verification.each { index, element -> captured.add(index to element) }

        captured.forEachIndexed { expected, (actualIndex, actualElement) ->
            assertEquals(expected, actualIndex)
            assertEquals(elements[expected], actualElement)
        }
    }

    @Test
    fun eachWithIndexedBlockReceivesCorrectIndexPathElements() {
        val elements = listOf("p", "q")
        val verification = Verification(elements, emptyScope())
        val capturedPaths = mutableListOf<List<ValidationPathElement>>()

        verification.each { _, _ -> capturedPaths.add(validationContext.validationPath().elements) }

        capturedPaths.forEachIndexed { index, path ->
            assertEquals(listOf(IndexPathElement(index)), path)
        }
    }

    @Test
    fun eachWithIndexedBlockReturnsSameVerification() {
        val verification = Verification(listOf(10, 20), emptyScope())

        val returned = verification.each { _, _ -> }

        assertSame(verification, returned)
    }

    @Test
    fun eachWithIndexedBlockOnEmptyCollectionDoesNotVisitAnything() {
        val verification = Verification(emptyList<Int>(), emptyScope())
        var visited = false

        verification.each { _, _ -> visited = true }

        assertEquals(false, visited)
    }

    @Test
    fun eachWithIndexedBlockExistingContextIsPrependedToIndexPath() {
        val parentElement = NamePathElement("items")
        val parentScope = CollectingValidationScope(mutableListOf(), parentElement)
        val elements = listOf("one", "two")
        val verification = Verification(elements, parentScope)
        val capturedPaths = mutableListOf<List<ValidationPathElement>>()

        verification.each { _, _ -> capturedPaths.add(validationContext.validationPath().elements) }

        capturedPaths.forEachIndexed { index, path ->
            assertEquals(listOf(parentElement, IndexPathElement(index)), path)
        }
    }
}
