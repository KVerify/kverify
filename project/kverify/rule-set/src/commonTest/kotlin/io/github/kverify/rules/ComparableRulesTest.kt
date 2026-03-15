package io.github.kverify.rules

import io.github.kverify.core.context.NamePathElement
import io.github.kverify.violations.AtLeastViolation
import io.github.kverify.violations.AtMostViolation
import io.github.kverify.violations.BetweenViolation
import io.github.kverify.violations.GreaterThanViolation
import io.github.kverify.violations.LessThanViolation
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertSame

class ComparableRulesTest {
    @Test
    fun atLeastPassesWhenValueEqualsMin() {
        val min = 5
        throwing(min).atLeast(min)
    }

    @Test
    fun atLeastPassesWhenValueExceedsMin() {
        throwing(10).atLeast(5)
    }

    @Test
    fun atLeastFailsWhenValueIsBelowMin() {
        val (storage, verification) = collecting(4)

        verification.atLeast(5)

        assertEquals(1, storage.size)
        assertIs<AtLeastViolation<*>>(storage[0])
    }

    @Test
    fun atLeastViolationStoresMinAndActual() {
        val min = 10
        val actual = 3
        val (storage, verification) = collecting(actual)

        verification.atLeast(min)

        val violation = storage[0] as AtLeastViolation<*>
        assertEquals(min, violation.minAllowed)
        assertEquals(actual, violation.actual)
    }

    @Test
    fun atLeastViolationHasDefaultReason() {
        val min = 10
        val actual = 3
        val (storage, verification) = collecting(actual)

        verification.atLeast(min)

        assertEquals("Value must be at least $min. Actual: $actual", storage[0].reason)
    }

    @Test
    fun atLeastViolationHasCustomReason() {
        val customReason = "age too low"
        val (storage, verification) = collecting(17)

        verification.atLeast(18, reason = customReason)

        assertEquals(customReason, storage[0].reason)
    }

    @Test
    fun atLeastViolationCarriesCorrectPath() {
        val pathElement = NamePathElement("age")
        val (storage, verification) = collecting(0, pathElement)

        verification.atLeast(1)

        assertEquals(listOf(pathElement), (storage[0] as AtLeastViolation<*>).validationPath.elements)
    }

    @Test
    fun atLeastReturnsSameVerification() {
        val (_, verification) = collecting(10)

        val returned = verification.atLeast(1)

        assertSame(verification, returned)
    }

    @Test
    fun atMostPassesWhenValueEqualsMax() {
        val max = 100
        throwing(max).atMost(max)
    }

    @Test
    fun atMostPassesWhenValueIsBelowMax() {
        throwing(50).atMost(100)
    }

    @Test
    fun atMostFailsWhenValueExceedsMax() {
        val (storage, verification) = collecting(101)

        verification.atMost(100)

        assertEquals(1, storage.size)
        assertIs<AtMostViolation<*>>(storage[0])
    }

    @Test
    fun atMostViolationStoresMaxAndActual() {
        val max = 50
        val actual = 75
        val (storage, verification) = collecting(actual)

        verification.atMost(max)

        val violation = storage[0] as AtMostViolation<*>
        assertEquals(max, violation.maxAllowed)
        assertEquals(actual, violation.actual)
    }

    @Test
    fun atMostViolationHasDefaultReason() {
        val max = 50
        val actual = 75
        val (storage, verification) = collecting(actual)

        verification.atMost(max)

        assertEquals("Value must be at most $max. Actual: $actual", storage[0].reason)
    }

    @Test
    fun atMostViolationHasCustomReason() {
        val customReason = "quantity exceeded"
        val (storage, verification) = collecting(200)

        verification.atMost(99, reason = customReason)

        assertEquals(customReason, storage[0].reason)
    }

    @Test
    fun atMostViolationCarriesCorrectPath() {
        val pathElement = NamePathElement("quantity")
        val (storage, verification) = collecting(200, pathElement)

        verification.atMost(100)

        assertEquals(listOf(pathElement), (storage[0] as AtMostViolation<*>).validationPath.elements)
    }

    @Test
    fun atMostReturnsSameVerification() {
        val (_, verification) = collecting(10)

        val returned = verification.atMost(100)

        assertSame(verification, returned)
    }

    @Test
    fun betweenPassesWhenValueEqualsMin() {
        throwing(1).between(1, 10)
    }

    @Test
    fun betweenPassesWhenValueEqualsMax() {
        throwing(10).between(1, 10)
    }

    @Test
    fun betweenPassesWhenValueIsWithinRange() {
        throwing(5).between(1, 10)
    }

    @Test
    fun betweenFailsWhenValueIsBelowMin() {
        val (storage, verification) = collecting(4)

        verification.between(5, 10)

        assertEquals(1, storage.size)
        assertIs<BetweenViolation<*>>(storage[0])
    }

    @Test
    fun betweenFailsWhenValueIsAboveMax() {
        val (storage, verification) = collecting(11)

        verification.between(5, 10)

        assertEquals(1, storage.size)
        assertIs<BetweenViolation<*>>(storage[0])
    }

    @Test
    fun betweenViolationStoresMinMaxAndActual() {
        val min = 5
        val max = 10
        val actual = 15
        val (storage, verification) = collecting(actual)

        verification.between(min, max)

        val violation = storage[0] as BetweenViolation<*>
        assertEquals(min, violation.min)
        assertEquals(max, violation.max)
        assertEquals(actual, violation.actual)
    }

    @Test
    fun betweenViolationHasDefaultReason() {
        val min = 1
        val max = 5
        val actual = 9
        val (storage, verification) = collecting(actual)

        verification.between(min, max)

        assertEquals("Value must be between $min and $max. Actual: $actual", storage[0].reason)
    }

    @Test
    fun betweenViolationHasCustomReason() {
        val customReason = "out of allowed range"
        val (storage, verification) = collecting(99)

        verification.between(0, 10, reason = customReason)

        assertEquals(customReason, storage[0].reason)
    }

    @Test
    fun betweenViolationCarriesCorrectPath() {
        val pathElement = NamePathElement("score")
        val (storage, verification) = collecting(200, pathElement)

        verification.between(0, 100)

        assertEquals(listOf(pathElement), (storage[0] as BetweenViolation<*>).validationPath.elements)
    }

    @Test
    fun betweenReturnsSameVerification() {
        val (_, verification) = collecting(5)

        val returned = verification.between(1, 10)

        assertSame(verification, returned)
    }

    @Test
    fun greaterThanPassesWhenValueExceedsMin() {
        throwing(1).greaterThan(0)
    }

    @Test
    fun greaterThanFailsWhenValueEqualsMin() {
        val min = 5
        val (storage, verification) = collecting(min)

        verification.greaterThan(min)

        assertEquals(1, storage.size)
        assertIs<GreaterThanViolation<*>>(storage[0])
    }

    @Test
    fun greaterThanFailsWhenValueIsBelowMin() {
        val (storage, verification) = collecting(4)

        verification.greaterThan(5)

        assertEquals(1, storage.size)
        assertIs<GreaterThanViolation<*>>(storage[0])
    }

    @Test
    fun greaterThanViolationStoresMinExclusiveAndActual() {
        val min = 10
        val (storage, verification) = collecting(min)

        verification.greaterThan(min)

        val violation = storage[0] as GreaterThanViolation<*>
        assertEquals(min, violation.minExclusive)
        assertEquals(min, violation.actual)
    }

    @Test
    fun greaterThanViolationHasDefaultReason() {
        val min = 0
        val actual = 0
        val (storage, verification) = collecting(actual)

        verification.greaterThan(min)

        assertEquals("Value must be greater than $min. Actual: $actual", storage[0].reason)
    }

    @Test
    fun greaterThanViolationHasCustomReason() {
        val customReason = "must be positive"
        val (storage, verification) = collecting(0)

        verification.greaterThan(0, reason = customReason)

        assertEquals(customReason, storage[0].reason)
    }

    @Test
    fun greaterThanViolationCarriesCorrectPath() {
        val pathElement = NamePathElement("price")
        val (storage, verification) = collecting(0, pathElement)

        verification.greaterThan(0)

        assertEquals(listOf(pathElement), (storage[0] as GreaterThanViolation<*>).validationPath.elements)
    }

    @Test
    fun greaterThanReturnsSameVerification() {
        val (_, verification) = collecting(5)

        val returned = verification.greaterThan(0)

        assertSame(verification, returned)
    }

    @Test
    fun lessThanPassesWhenValueIsBelowMax() {
        throwing(9).lessThan(10)
    }

    @Test
    fun lessThanFailsWhenValueEqualsMax() {
        val max = 10
        val (storage, verification) = collecting(max)

        verification.lessThan(max)

        assertEquals(1, storage.size)
        assertIs<LessThanViolation<*>>(storage[0])
    }

    @Test
    fun lessThanFailsWhenValueExceedsMax() {
        val (storage, verification) = collecting(11)

        verification.lessThan(10)

        assertEquals(1, storage.size)
        assertIs<LessThanViolation<*>>(storage[0])
    }

    @Test
    fun lessThanViolationStoresMaxExclusiveAndActual() {
        val max = 5
        val (storage, verification) = collecting(max)

        verification.lessThan(max)

        val violation = storage[0] as LessThanViolation<*>
        assertEquals(max, violation.maxExclusive)
        assertEquals(max, violation.actual)
    }

    @Test
    fun lessThanViolationHasDefaultReason() {
        val max = 100
        val actual = 100
        val (storage, verification) = collecting(actual)

        verification.lessThan(max)

        assertEquals("Value must be less than $max. Actual: $actual", storage[0].reason)
    }

    @Test
    fun lessThanViolationHasCustomReason() {
        val customReason = "must be under limit"
        val (storage, verification) = collecting(100)

        verification.lessThan(100, reason = customReason)

        assertEquals(customReason, storage[0].reason)
    }

    @Test
    fun lessThanViolationCarriesCorrectPath() {
        val pathElement = NamePathElement("weight")
        val (storage, verification) = collecting(1000, pathElement)

        verification.lessThan(500)

        assertEquals(listOf(pathElement), (storage[0] as LessThanViolation<*>).validationPath.elements)
    }

    @Test
    fun lessThanReturnsSameVerification() {
        val (_, verification) = collecting(5)

        val returned = verification.lessThan(10)

        assertSame(verification, returned)
    }
}
