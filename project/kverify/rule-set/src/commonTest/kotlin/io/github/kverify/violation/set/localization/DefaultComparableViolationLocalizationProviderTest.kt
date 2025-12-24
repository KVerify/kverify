package io.github.kverify.violation.set.localization

import kotlin.test.Test
import kotlin.test.assertEquals

class DefaultComparableViolationLocalizationProviderTest {
    private val provider = DefaultComparableViolationLocalizationProvider

    @Test
    fun betweenWithCustomNameAndIntegers() {
        val result = provider.between(10, 1..5, "age")
        assertEquals("'age' must be between '1' and '5', but it is '10'.", result)
    }

    @Test
    fun betweenWithNullNameAndIntegers() {
        val result = provider.between(0, 1..10, null)
        assertEquals("'comparable' must be between '1' and '10', but it is '0'.", result)
    }

    @Test
    fun betweenWithStrings() {
        val result = provider.between("zebra", "apple".."mango", "fruit")
        assertEquals("'fruit' must be between 'apple' and 'mango', but it is 'zebra'.", result)
    }

    @Test
    fun betweenBelowRange() {
        val result = provider.between(5, 10..20, "value")
        assertEquals("'value' must be between '10' and '20', but it is '5'.", result)
    }

    @Test
    fun betweenAboveRange() {
        val result = provider.between(25, 10..20, "value")
        assertEquals("'value' must be between '10' and '20', but it is '25'.", result)
    }

    @Test
    fun equalToWithCustomNameAndIntegers() {
        val result = provider.equalTo(5, 10, "count")
        assertEquals("'count' must be equal to '10', but it is '5'.", result)
    }

    @Test
    fun equalToWithNullNameAndIntegers() {
        val result = provider.equalTo(3, 5, null)
        assertEquals("'comparable' must be equal to '5', but it is '3'.", result)
    }

    @Test
    fun equalToWithStrings() {
        val result = provider.equalTo("hello", "world", "greeting")
        assertEquals("'greeting' must be equal to 'world', but it is 'hello'.", result)
    }

    @Test
    fun equalToWithNegativeNumbers() {
        val result = provider.equalTo(-5, 0, "temperature")
        assertEquals("'temperature' must be equal to '0', but it is '-5'.", result)
    }

    @Test
    fun greaterThanOrEqualToWithCustomNameAndIntegers() {
        val result = provider.greaterThanOrEqualTo(5, 10, "score")
        assertEquals("'score' must be greater than or equal to '10', but it is '5'.", result)
    }

    @Test
    fun greaterThanOrEqualToWithNullNameAndIntegers() {
        val result = provider.greaterThanOrEqualTo(3, 5, null)
        assertEquals("'comparable' must be greater than or equal to '5', but it is '3'.", result)
    }

    @Test
    fun greaterThanOrEqualToWithStrings() {
        val result = provider.greaterThanOrEqualTo("apple", "banana", "fruit")
        assertEquals("'fruit' must be greater than or equal to 'banana', but it is 'apple'.", result)
    }

    @Test
    fun greaterThanWithCustomNameAndIntegers() {
        val result = provider.greaterThan(5, 10, "value")
        assertEquals("'value' must be greater than '10', but it is '5'.", result)
    }

    @Test
    fun greaterThanWithNullNameAndIntegers() {
        val result = provider.greaterThan(8, 15, null)
        assertEquals("'comparable' must be greater than '15', but it is '8'.", result)
    }

    @Test
    fun greaterThanWithStrings() {
        val result = provider.greaterThan("alpha", "zeta", "name")
        assertEquals("'name' must be greater than 'zeta', but it is 'alpha'.", result)
    }

    @Test
    fun greaterThanWithNegativeNumbers() {
        val result = provider.greaterThan(-10, 0, "balance")
        assertEquals("'balance' must be greater than '0', but it is '-10'.", result)
    }

    @Test
    fun lessThanOrEqualToWithCustomNameAndIntegers() {
        val result = provider.lessThanOrEqualTo(15, 10, "limit")
        assertEquals("'limit' must be less than or equal to '10', but it is '15'.", result)
    }

    @Test
    fun lessThanOrEqualToWithNullNameAndIntegers() {
        val result = provider.lessThanOrEqualTo(20, 10, null)
        assertEquals("'comparable' must be less than or equal to '10', but it is '20'.", result)
    }

    @Test
    fun lessThanOrEqualToWithStrings() {
        val result = provider.lessThanOrEqualTo("zebra", "apple", "animal")
        assertEquals("'animal' must be less than or equal to 'apple', but it is 'zebra'.", result)
    }

    @Test
    fun lessThanWithCustomNameAndIntegers() {
        val result = provider.lessThan(15, 10, "value")
        assertEquals("'value' must be less than '10', but it is '15'.", result)
    }

    @Test
    fun lessThanWithNullNameAndIntegers() {
        val result = provider.lessThan(25, 20, null)
        assertEquals("'comparable' must be less than '20', but it is '25'.", result)
    }

    @Test
    fun lessThanWithStrings() {
        val result = provider.lessThan("zoo", "apple", "word")
        assertEquals("'word' must be less than 'apple', but it is 'zoo'.", result)
    }

    @Test
    fun lessThanWithNegativeNumbers() {
        val result = provider.lessThan(5, 0, "offset")
        assertEquals("'offset' must be less than '0', but it is '5'.", result)
    }

    @Test
    fun notBetweenWithCustomNameAndIntegers() {
        val result = provider.notBetween(5, 1..10, "age")
        assertEquals("'age' must not be between '1' and '10', but it is '5'.", result)
    }

    @Test
    fun notBetweenWithNullNameAndIntegers() {
        val result = provider.notBetween(7, 5..10, null)
        assertEquals("'comparable' must not be between '5' and '10', but it is '7'.", result)
    }

    @Test
    fun notBetweenWithStrings() {
        val result = provider.notBetween("banana", "apple".."cherry", "fruit")
        assertEquals("'fruit' must not be between 'apple' and 'cherry', but it is 'banana'.", result)
    }

    @Test
    fun notBetweenAtLowerBound() {
        val result = provider.notBetween(5, 5..10, "value")
        assertEquals("'value' must not be between '5' and '10', but it is '5'.", result)
    }

    @Test
    fun notBetweenAtUpperBound() {
        val result = provider.notBetween(10, 5..10, "value")
        assertEquals("'value' must not be between '5' and '10', but it is '10'.", result)
    }

    @Test
    fun notEqualToWithCustomNameAndIntegers() {
        val result = provider.notEqualTo(5, 5, "count")
        assertEquals("'count' must not be equal to '5', but it is.", result)
    }

    @Test
    fun notEqualToWithNullNameAndIntegers() {
        val result = provider.notEqualTo(10, 10, null)
        assertEquals("'comparable' must not be equal to '10', but it is.", result)
    }

    @Test
    fun notEqualToWithStrings() {
        val result = provider.notEqualTo("test", "test", "value")
        assertEquals("'value' must not be equal to 'test', but it is.", result)
    }

    @Test
    fun notEqualToWithZero() {
        val result = provider.notEqualTo(0, 0, "zero")
        assertEquals("'zero' must not be equal to '0', but it is.", result)
    }
}
