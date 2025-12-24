package io.github.kverify.check.set.provider

import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class DefaultComparableCheckProviderTest {
    private val provider = DefaultComparableCheckProvider()

    @Test
    fun equalToReturnsTrueWhenValuesEqual() {
        val check = provider.equalTo(5)
        assertTrue(check.isValid(5))
    }

    @Test
    fun equalToReturnsFalseWhenValuesDiffer() {
        val check = provider.equalTo(5)
        assertFalse(check.isValid(3))
    }

    @Test
    fun equalToWorksWithStrings() {
        val check = provider.equalTo("test")
        assertTrue(check.isValid("test"))
        assertFalse(check.isValid("other"))
    }

    @Test
    fun notEqualToReturnsTrueWhenValuesDiffer() {
        val check = provider.notEqualTo(5)
        assertTrue(check.isValid(3))
    }

    @Test
    fun notEqualToReturnsFalseWhenValuesEqual() {
        val check = provider.notEqualTo(5)
        assertFalse(check.isValid(5))
    }

    @Test
    fun notEqualToWorksWithStrings() {
        val check = provider.notEqualTo("test")
        assertTrue(check.isValid("other"))
        assertFalse(check.isValid("test"))
    }

    @Test
    fun greaterThanReturnsTrueWhenValueGreater() {
        val check = provider.greaterThan(5)
        assertTrue(check.isValid(10))
    }

    @Test
    fun greaterThanReturnsFalseWhenValueEqual() {
        val check = provider.greaterThan(5)
        assertFalse(check.isValid(5))
    }

    @Test
    fun greaterThanReturnsFalseWhenValueLess() {
        val check = provider.greaterThan(5)
        assertFalse(check.isValid(3))
    }

    @Test
    fun greaterThanWorksWithStrings() {
        val check = provider.greaterThan("apple")
        assertTrue(check.isValid("banana"))
        assertFalse(check.isValid("aardvark"))
    }

    @Test
    fun greaterThanOrEqualToReturnsTrueWhenValueGreater() {
        val check = provider.greaterThanOrEqualTo(5)
        assertTrue(check.isValid(10))
    }

    @Test
    fun greaterThanOrEqualToReturnsTrueWhenValueEqual() {
        val check = provider.greaterThanOrEqualTo(5)
        assertTrue(check.isValid(5))
    }

    @Test
    fun greaterThanOrEqualToReturnsFalseWhenValueLess() {
        val check = provider.greaterThanOrEqualTo(5)
        assertFalse(check.isValid(3))
    }

    @Test
    fun greaterThanOrEqualToWorksWithStrings() {
        val check = provider.greaterThanOrEqualTo("apple")
        assertTrue(check.isValid("apple"))
        assertTrue(check.isValid("banana"))
        assertFalse(check.isValid("aardvark"))
    }

    @Test
    fun lessThanReturnsTrueWhenValueLess() {
        val check = provider.lessThan(5)
        assertTrue(check.isValid(3))
    }

    @Test
    fun lessThanReturnsFalseWhenValueEqual() {
        val check = provider.lessThan(5)
        assertFalse(check.isValid(5))
    }

    @Test
    fun lessThanReturnsFalseWhenValueGreater() {
        val check = provider.lessThan(5)
        assertFalse(check.isValid(10))
    }

    @Test
    fun lessThanWorksWithStrings() {
        val check = provider.lessThan("banana")
        assertTrue(check.isValid("apple"))
        assertFalse(check.isValid("cherry"))
    }

    @Test
    fun lessThanOrEqualToReturnsTrueWhenValueLess() {
        val check = provider.lessThanOrEqualTo(5)
        assertTrue(check.isValid(3))
    }

    @Test
    fun lessThanOrEqualToReturnsTrueWhenValueEqual() {
        val check = provider.lessThanOrEqualTo(5)
        assertTrue(check.isValid(5))
    }

    @Test
    fun lessThanOrEqualToReturnsFalseWhenValueGreater() {
        val check = provider.lessThanOrEqualTo(5)
        assertFalse(check.isValid(10))
    }

    @Test
    fun lessThanOrEqualToWorksWithStrings() {
        val check = provider.lessThanOrEqualTo("banana")
        assertTrue(check.isValid("banana"))
        assertTrue(check.isValid("apple"))
        assertFalse(check.isValid("cherry"))
    }

    @Test
    fun betweenReturnsTrueWhenValueInRange() {
        val check = provider.between(5..10)
        assertTrue(check.isValid(7))
    }

    @Test
    fun betweenReturnsTrueWhenValueAtLowerBound() {
        val check = provider.between(5..10)
        assertTrue(check.isValid(5))
    }

    @Test
    fun betweenReturnsTrueWhenValueAtUpperBound() {
        val check = provider.between(5..10)
        assertTrue(check.isValid(10))
    }

    @Test
    fun betweenReturnsFalseWhenValueBelowRange() {
        val check = provider.between(5..10)
        assertFalse(check.isValid(3))
    }

    @Test
    fun betweenReturnsFalseWhenValueAboveRange() {
        val check = provider.between(5..10)
        assertFalse(check.isValid(15))
    }

    @Test
    fun betweenWorksWithStrings() {
        val check = provider.between("apple".."cherry")
        assertTrue(check.isValid("banana"))
        assertTrue(check.isValid("apple"))
        assertTrue(check.isValid("cherry"))
        assertFalse(check.isValid("aardvark"))
        assertFalse(check.isValid("durian"))
    }

    @Test
    fun notBetweenReturnsTrueWhenValueBelowRange() {
        val check = provider.notBetween(5..10)
        assertTrue(check.isValid(3))
    }

    @Test
    fun notBetweenReturnsTrueWhenValueAboveRange() {
        val check = provider.notBetween(5..10)
        assertTrue(check.isValid(15))
    }

    @Test
    fun notBetweenReturnsFalseWhenValueInRange() {
        val check = provider.notBetween(5..10)
        assertFalse(check.isValid(7))
    }

    @Test
    fun notBetweenReturnsFalseWhenValueAtBounds() {
        val check = provider.notBetween(5..10)
        assertFalse(check.isValid(5))
        assertFalse(check.isValid(10))
    }

    @Test
    fun notBetweenWorksWithStrings() {
        val check = provider.notBetween("apple".."cherry")
        assertTrue(check.isValid("aardvark"))
        assertTrue(check.isValid("durian"))
        assertFalse(check.isValid("banana"))
        assertFalse(check.isValid("apple"))
        assertFalse(check.isValid("cherry"))
    }
}
