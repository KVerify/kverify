package io.github.kverify.rules

import io.github.kverify.core.context.EmptyValidationContext
import io.github.kverify.core.context.NamePathElement
import io.github.kverify.core.scope.CollectingValidationScope
import io.github.kverify.core.scope.ThrowingValidationScope
import io.github.kverify.core.scope.Verification
import io.github.kverify.core.violation.Violation
import io.github.kverify.violations.DistinctViolation
import io.github.kverify.violations.ExactSizeViolation
import io.github.kverify.violations.MaxSizeViolation
import io.github.kverify.violations.MinSizeViolation
import io.github.kverify.violations.SizeRangeViolation
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertSame

class CollectionRulesTest {
    @Test
    fun minSizePassesWhenSizeEqualsMin() {
        val min = 3
        throwing(listOf(1, 2, 3)).minSize(min)
    }

    @Test
    fun minSizePassesWhenSizeExceedsMin() {
        throwing(listOf(1, 2, 3, 4)).minSize(3)
    }

    @Test
    fun minSizeFailsWhenSizeIsBelowMin() {
        val (storage, verification) = collecting(listOf(1, 2))

        verification.minSize(3)

        assertEquals(1, storage.size)
        assertIs<MinSizeViolation>(storage[0])
    }

    @Test
    fun minSizeViolationStoresMinAndActualSize() {
        val min = 5
        val value = listOf(1, 2)
        val (storage, verification) = collecting(value)

        verification.minSize(min)

        val violation = storage[0] as MinSizeViolation
        assertEquals(min, violation.minSizeAllowed)
        assertEquals(value.size, violation.actualSize)
    }

    @Test
    fun minSizeViolationHasDefaultReason() {
        val min = 5
        val value = listOf(1, 2)
        val (storage, verification) = collecting(value)

        verification.minSize(min)

        assertEquals("Collection must have at least $min elements. Actual size: ${value.size}", storage[0].reason)
    }

    @Test
    fun minSizeViolationHasCustomReason() {
        val customReason = "not enough items"
        val (storage, verification) = collecting(listOf(1))

        verification.minSize(3, reason = customReason)

        assertEquals(customReason, storage[0].reason)
    }

    @Test
    fun minSizeViolationCarriesCorrectPath() {
        val pathElement = NamePathElement("tags")
        val (storage, verification) = collecting(listOf(1), pathElement)

        verification.minSize(3)

        assertEquals(listOf(pathElement), (storage[0] as MinSizeViolation).validationPath.elements)
    }

    @Test
    fun minSizeReturnsSameVerification() {
        val (_, verification) = collecting(listOf(1, 2, 3))

        val returned = verification.minSize(1)

        assertSame(verification, returned)
    }

    @Test
    fun maxSizePassesWhenSizeEqualsMax() {
        val max = 3
        throwing(listOf(1, 2, 3)).maxSize(max)
    }

    @Test
    fun maxSizePassesWhenSizeIsBelowMax() {
        throwing(listOf(1, 2)).maxSize(3)
    }

    @Test
    fun maxSizeFailsWhenSizeExceedsMax() {
        val (storage, verification) = collecting(listOf(1, 2, 3, 4))

        verification.maxSize(3)

        assertEquals(1, storage.size)
        assertIs<MaxSizeViolation>(storage[0])
    }

    @Test
    fun maxSizeViolationStoresMaxAndActualSize() {
        val max = 2
        val value = listOf(1, 2, 3, 4)
        val (storage, verification) = collecting(value)

        verification.maxSize(max)

        val violation = storage[0] as MaxSizeViolation
        assertEquals(max, violation.maxSizeAllowed)
        assertEquals(value.size, violation.actualSize)
    }

    @Test
    fun maxSizeViolationHasDefaultReason() {
        val max = 2
        val value = listOf(1, 2, 3)
        val (storage, verification) = collecting(value)

        verification.maxSize(max)

        assertEquals("Collection must have at most $max elements. Actual size: ${value.size}", storage[0].reason)
    }

    @Test
    fun maxSizeViolationHasCustomReason() {
        val customReason = "too many items"
        val (storage, verification) = collecting(listOf(1, 2, 3, 4, 5))

        verification.maxSize(3, reason = customReason)

        assertEquals(customReason, storage[0].reason)
    }

    @Test
    fun maxSizeViolationCarriesCorrectPath() {
        val pathElement = NamePathElement("items")
        val (storage, verification) = collecting(listOf(1, 2, 3, 4), pathElement)

        verification.maxSize(2)

        assertEquals(listOf(pathElement), (storage[0] as MaxSizeViolation).validationPath.elements)
    }

    @Test
    fun maxSizeReturnsSameVerification() {
        val (_, verification) = collecting(listOf(1, 2))

        val returned = verification.maxSize(5)

        assertSame(verification, returned)
    }

    @Test
    fun exactSizePassesWhenSizeMatchesExactly() {
        throwing(listOf(1, 2, 3)).exactSize(3)
    }

    @Test
    fun exactSizeFailsWhenSizeIsBelowExpected() {
        val (storage, verification) = collecting(listOf(1, 2))

        verification.exactSize(3)

        assertEquals(1, storage.size)
        assertIs<ExactSizeViolation>(storage[0])
    }

    @Test
    fun exactSizeFailsWhenSizeIsAboveExpected() {
        val (storage, verification) = collecting(listOf(1, 2, 3, 4))

        verification.exactSize(3)

        assertEquals(1, storage.size)
        assertIs<ExactSizeViolation>(storage[0])
    }

    @Test
    fun exactSizeViolationStoresExpectedAndActualSize() {
        val expectedSize = 5
        val value = listOf(1, 2)
        val (storage, verification) = collecting(value)

        verification.exactSize(expectedSize)

        val violation = storage[0] as ExactSizeViolation
        assertEquals(expectedSize, violation.expectedSize)
        assertEquals(value.size, violation.actualSize)
    }

    @Test
    fun exactSizeViolationHasDefaultReason() {
        val expectedSize = 5
        val value = listOf(1, 2)
        val (storage, verification) = collecting(value)

        verification.exactSize(expectedSize)

        assertEquals(
            "Collection must have exactly $expectedSize elements. Actual size: ${value.size}",
            storage[0].reason,
        )
    }

    @Test
    fun exactSizeViolationHasCustomReason() {
        val customReason = "must have exactly 2 items"
        val (storage, verification) = collecting(listOf(1))

        verification.exactSize(2, reason = customReason)

        assertEquals(customReason, storage[0].reason)
    }

    @Test
    fun exactSizeViolationCarriesCorrectPath() {
        val pathElement = NamePathElement("coordinates")
        val (storage, verification) = collecting(listOf(1), pathElement)

        verification.exactSize(2)

        assertEquals(listOf(pathElement), (storage[0] as ExactSizeViolation).validationPath.elements)
    }

    @Test
    fun exactSizeReturnsSameVerification() {
        val (_, verification) = collecting(listOf(1, 2, 3))

        val returned = verification.exactSize(3)

        assertSame(verification, returned)
    }

    @Test
    fun sizeRangePassesWhenSizeEqualsMin() {
        throwing(listOf(1, 2)).sizeRange(2, 5)
    }

    @Test
    fun sizeRangePassesWhenSizeEqualsMax() {
        throwing(listOf(1, 2, 3, 4, 5)).sizeRange(2, 5)
    }

    @Test
    fun sizeRangePassesWhenSizeIsWithinRange() {
        throwing(listOf(1, 2, 3)).sizeRange(2, 5)
    }

    @Test
    fun sizeRangeFailsWhenSizeIsBelowMin() {
        val (storage, verification) = collecting(listOf(1))

        verification.sizeRange(2, 5)

        assertEquals(1, storage.size)
        assertIs<SizeRangeViolation>(storage[0])
    }

    @Test
    fun sizeRangeFailsWhenSizeIsAboveMax() {
        val (storage, verification) = collecting(listOf(1, 2, 3, 4, 5, 6))

        verification.sizeRange(2, 5)

        assertEquals(1, storage.size)
        assertIs<SizeRangeViolation>(storage[0])
    }

    @Test
    fun sizeRangeViolationStoresMinMaxAndActualSize() {
        val min = 3
        val max = 6
        val value = listOf(1)
        val (storage, verification) = collecting(value)

        verification.sizeRange(min, max)

        val violation = storage[0] as SizeRangeViolation
        assertEquals(min, violation.minSizeAllowed)
        assertEquals(max, violation.maxSizeAllowed)
        assertEquals(value.size, violation.actualSize)
    }

    @Test
    fun sizeRangeViolationHasDefaultReason() {
        val min = 3
        val max = 6
        val value = listOf(1)
        val (storage, verification) = collecting(value)

        verification.sizeRange(min, max)

        assertEquals(
            "Collection must have between $min and $max elements. Actual size: ${value.size}",
            storage[0].reason,
        )
    }

    @Test
    fun sizeRangeViolationHasCustomReason() {
        val customReason = "wrong number of elements"
        val (storage, verification) = collecting(listOf(1))

        verification.sizeRange(3, 10, reason = customReason)

        assertEquals(customReason, storage[0].reason)
    }

    @Test
    fun sizeRangeViolationCarriesCorrectPath() {
        val pathElement = NamePathElement("roles")
        val (storage, verification) = collecting(listOf(1), pathElement)

        verification.sizeRange(2, 5)

        assertEquals(listOf(pathElement), (storage[0] as SizeRangeViolation).validationPath.elements)
    }

    @Test
    fun sizeRangeReturnsSameVerification() {
        val (_, verification) = collecting(listOf(1, 2, 3))

        val returned = verification.sizeRange(1, 5)

        assertSame(verification, returned)
    }

    @Test
    fun distinctPassesWhenAllElementsAreUnique() {
        throwing(listOf(1, 2, 3, 4)).distinct()
    }

    @Test
    fun distinctPassesOnEmptyCollection() {
        throwing(emptyList<Int>()).distinct()
    }

    @Test
    fun distinctPassesOnSingleElementCollection() {
        throwing(listOf(42)).distinct()
    }

    @Test
    fun distinctFailsWhenCollectionContainsDuplicates() {
        val (storage, verification) = collecting(listOf(1, 2, 2, 3))

        verification.distinct()

        assertEquals(1, storage.size)
        assertIs<DistinctViolation>(storage[0])
    }

    @Test
    fun distinctViolationStoresActualAndDistinctSize() {
        val value = listOf(1, 2, 2, 3, 3, 3)
        val (storage, verification) = collecting(value)

        verification.distinct()

        val violation = storage[0] as DistinctViolation
        assertEquals(value.size, violation.actualSize)
        assertEquals(value.toSet().size, violation.distinctSize)
    }

    @Test
    fun distinctViolationHasDefaultReason() {
        val value = listOf(1, 1, 2)
        val duplicatesCount = value.size - value.toSet().size
        val (storage, verification) = collecting(value)

        verification.distinct()

        assertEquals(
            "Collection must contain distinct elements. Found $duplicatesCount duplicates",
            storage[0].reason,
        )
    }

    @Test
    fun distinctViolationHasCustomReason() {
        val customReason = "duplicates not allowed"
        val (storage, verification) = collecting(listOf(1, 1))

        verification.distinct(reason = customReason)

        assertEquals(customReason, storage[0].reason)
    }

    @Test
    fun distinctViolationCarriesCorrectPath() {
        val pathElement = NamePathElement("ids")
        val (storage, verification) = collecting(listOf(1, 1, 2), pathElement)

        verification.distinct()

        assertEquals(listOf(pathElement), (storage[0] as DistinctViolation).validationPath.elements)
    }

    @Test
    fun distinctReturnsSameVerification() {
        val (_, verification) = collecting(listOf(1, 2, 3))

        val returned = verification.distinct()

        assertSame(verification, returned)
    }
}
