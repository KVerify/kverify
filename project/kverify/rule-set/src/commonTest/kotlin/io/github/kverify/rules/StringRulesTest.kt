package io.github.kverify.rules

import io.github.kverify.core.context.NamePathElement
import io.github.kverify.violations.ExactLengthViolation
import io.github.kverify.violations.LengthRangeViolation
import io.github.kverify.violations.MaxLengthViolation
import io.github.kverify.violations.MinLengthViolation
import io.github.kverify.violations.NotBlankViolation
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertSame

class StringRulesTest {
    @Test
    fun notBlankPassesWhenValueIsNotBlank() {
        throwing("hello").notBlank()
    }

    @Test
    fun notBlankFailsWhenValueIsBlank() {
        val (storage, verification) = collecting("   ")

        verification.notBlank()

        assertEquals(1, storage.size)
        assertIs<NotBlankViolation>(storage[0])
    }

    @Test
    fun notBlankFailsWhenValueIsEmpty() {
        val (storage, verification) = collecting("")

        verification.notBlank()

        assertEquals(1, storage.size)
        assertIs<NotBlankViolation>(storage[0])
    }

    @Test
    fun notBlankViolationHasDefaultReason() {
        val (storage, verification) = collecting("")

        verification.notBlank()

        assertEquals("Value must not be blank", storage[0].reason)
    }

    @Test
    fun notBlankViolationHasCustomReason() {
        val customReason = "name cannot be blank"
        val (storage, verification) = collecting("")

        verification.notBlank(reason = customReason)

        assertEquals(customReason, storage[0].reason)
    }

    @Test
    fun notBlankViolationCarriesCorrectPath() {
        val pathElement = NamePathElement("name")
        val (storage, verification) = collecting("", pathElement)

        verification.notBlank()

        assertEquals(listOf(pathElement), (storage[0] as NotBlankViolation).validationPath.elements)
    }

    @Test
    fun notBlankReturnsSameVerification() {
        val (_, verification) = collecting("text")

        val returned = verification.notBlank()

        assertSame(verification, returned)
    }

    @Test
    fun minLengthPassesWhenLengthEqualsMin() {
        val min = 3
        throwing("abc").minLength(min)
    }

    @Test
    fun minLengthPassesWhenLengthExceedsMin() {
        throwing("abcde").minLength(3)
    }

    @Test
    fun minLengthFailsWhenLengthIsBelowMin() {
        val (storage, verification) = collecting("ab")

        verification.minLength(3)

        assertEquals(1, storage.size)
        assertIs<MinLengthViolation>(storage[0])
    }

    @Test
    fun minLengthViolationStoresMinAndActualLength() {
        val min = 5
        val value = "hi"
        val (storage, verification) = collecting(value)

        verification.minLength(min)

        val violation = storage[0] as MinLengthViolation
        assertEquals(min, violation.minLengthAllowed)
        assertEquals(value.length, violation.actualLength)
    }

    @Test
    fun minLengthViolationHasDefaultReason() {
        val min = 5
        val value = "hi"
        val (storage, verification) = collecting(value)

        verification.minLength(min)

        assertEquals("Value must be at least $min characters long. Actual length: ${value.length}", storage[0].reason)
    }

    @Test
    fun minLengthViolationHasCustomReason() {
        val customReason = "too short"
        val (storage, verification) = collecting("x")

        verification.minLength(10, reason = customReason)

        assertEquals(customReason, storage[0].reason)
    }

    @Test
    fun minLengthViolationCarriesCorrectPath() {
        val pathElement = NamePathElement("password")
        val (storage, verification) = collecting("abc", pathElement)

        verification.minLength(8)

        assertEquals(listOf(pathElement), (storage[0] as MinLengthViolation).validationPath.elements)
    }

    @Test
    fun minLengthReturnsSameVerification() {
        val (_, verification) = collecting("hello")

        val returned = verification.minLength(1)

        assertSame(verification, returned)
    }

    @Test
    fun maxLengthPassesWhenLengthEqualsMax() {
        val max = 5
        throwing("abcde").maxLength(max)
    }

    @Test
    fun maxLengthPassesWhenLengthIsBelowMax() {
        throwing("abc").maxLength(5)
    }

    @Test
    fun maxLengthFailsWhenLengthExceedsMax() {
        val (storage, verification) = collecting("abcdef")

        verification.maxLength(5)

        assertEquals(1, storage.size)
        assertIs<MaxLengthViolation>(storage[0])
    }

    @Test
    fun maxLengthViolationStoresMaxAndActualLength() {
        val max = 3
        val value = "toolong"
        val (storage, verification) = collecting(value)

        verification.maxLength(max)

        val violation = storage[0] as MaxLengthViolation
        assertEquals(max, violation.maxLengthAllowed)
        assertEquals(value.length, violation.actualLength)
    }

    @Test
    fun maxLengthViolationHasDefaultReason() {
        val max = 3
        val value = "toolong"
        val (storage, verification) = collecting(value)

        verification.maxLength(max)

        assertEquals("Value must be at most $max characters long. Actual length: ${value.length}", storage[0].reason)
    }

    @Test
    fun maxLengthViolationHasCustomReason() {
        val customReason = "too long"
        val (storage, verification) = collecting("verylongstring")

        verification.maxLength(5, reason = customReason)

        assertEquals(customReason, storage[0].reason)
    }

    @Test
    fun maxLengthViolationCarriesCorrectPath() {
        val pathElement = NamePathElement("bio")
        val (storage, verification) = collecting("a".repeat(300), pathElement)

        verification.maxLength(255)

        assertEquals(listOf(pathElement), (storage[0] as MaxLengthViolation).validationPath.elements)
    }

    @Test
    fun maxLengthReturnsSameVerification() {
        val (_, verification) = collecting("hello")

        val returned = verification.maxLength(10)

        assertSame(verification, returned)
    }

    @Test
    fun exactLengthPassesWhenLengthMatchesExactly() {
        throwing("abc").exactLength(3)
    }

    @Test
    fun exactLengthFailsWhenLengthIsBelowExpected() {
        val (storage, verification) = collecting("ab")

        verification.exactLength(3)

        assertEquals(1, storage.size)
        assertIs<ExactLengthViolation>(storage[0])
    }

    @Test
    fun exactLengthFailsWhenLengthIsAboveExpected() {
        val (storage, verification) = collecting("abcd")

        verification.exactLength(3)

        assertEquals(1, storage.size)
        assertIs<ExactLengthViolation>(storage[0])
    }

    @Test
    fun exactLengthViolationStoresExpectedAndActualLength() {
        val expectedLength = 5
        val value = "hi"
        val (storage, verification) = collecting(value)

        verification.exactLength(expectedLength)

        val violation = storage[0] as ExactLengthViolation
        assertEquals(expectedLength, violation.expectedLength)
        assertEquals(value.length, violation.actualLength)
    }

    @Test
    fun exactLengthViolationHasDefaultReason() {
        val expectedLength = 5
        val value = "hi"
        val (storage, verification) = collecting(value)

        verification.exactLength(expectedLength)

        assertEquals(
            "Value must be exactly $expectedLength characters long. Actual length: ${value.length}",
            storage[0].reason,
        )
    }

    @Test
    fun exactLengthViolationHasCustomReason() {
        val customReason = "must be exactly 4 chars"
        val (storage, verification) = collecting("ab")

        verification.exactLength(4, reason = customReason)

        assertEquals(customReason, storage[0].reason)
    }

    @Test
    fun exactLengthViolationCarriesCorrectPath() {
        val pathElement = NamePathElement("pin")
        val (storage, verification) = collecting("12", pathElement)

        verification.exactLength(4)

        assertEquals(listOf(pathElement), (storage[0] as ExactLengthViolation).validationPath.elements)
    }

    @Test
    fun exactLengthReturnsSameVerification() {
        val (_, verification) = collecting("abc")

        val returned = verification.exactLength(3)

        assertSame(verification, returned)
    }

    @Test
    fun lengthRangePassesWhenLengthEqualsMin() {
        throwing("ab").lengthRange(2, 5)
    }

    @Test
    fun lengthRangePassesWhenLengthEqualsMax() {
        throwing("abcde").lengthRange(2, 5)
    }

    @Test
    fun lengthRangePassesWhenLengthIsWithinRange() {
        throwing("abc").lengthRange(2, 5)
    }

    @Test
    fun lengthRangeFailsWhenLengthIsBelowMin() {
        val (storage, verification) = collecting("a")

        verification.lengthRange(2, 5)

        assertEquals(1, storage.size)
        assertIs<LengthRangeViolation>(storage[0])
    }

    @Test
    fun lengthRangeFailsWhenLengthIsAboveMax() {
        val (storage, verification) = collecting("abcdef")

        verification.lengthRange(2, 5)

        assertEquals(1, storage.size)
        assertIs<LengthRangeViolation>(storage[0])
    }

    @Test
    fun lengthRangeViolationStoresMinMaxAndActualLength() {
        val min = 3
        val max = 6
        val value = "ab"
        val (storage, verification) = collecting(value)

        verification.lengthRange(min, max)

        val violation = storage[0] as LengthRangeViolation
        assertEquals(min, violation.minLengthAllowed)
        assertEquals(max, violation.maxLengthAllowed)
        assertEquals(value.length, violation.actualLength)
    }

    @Test
    fun lengthRangeViolationHasDefaultReason() {
        val min = 3
        val max = 6
        val value = "ab"
        val (storage, verification) = collecting(value)

        verification.lengthRange(min, max)

        assertEquals(
            "Value must be between $min and $max characters long. Actual length: ${value.length}",
            storage[0].reason,
        )
    }

    @Test
    fun lengthRangeViolationHasCustomReason() {
        val customReason = "length out of range"
        val (storage, verification) = collecting("x")

        verification.lengthRange(3, 10, reason = customReason)

        assertEquals(customReason, storage[0].reason)
    }

    @Test
    fun lengthRangeViolationCarriesCorrectPath() {
        val pathElement = NamePathElement("username")
        val (storage, verification) = collecting("x", pathElement)

        verification.lengthRange(3, 20)

        assertEquals(listOf(pathElement), (storage[0] as LengthRangeViolation).validationPath.elements)
    }

    @Test
    fun lengthRangeReturnsSameVerification() {
        val (_, verification) = collecting("hello")

        val returned = verification.lengthRange(1, 10)

        assertSame(verification, returned)
    }
}
