package io.github.kverify.violation.set.provider

import io.github.kverify.violation.set.localization.ComparableViolationLocalizationProvider
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class DefaultComparableViolationProviderTest {
    private val localizationProvider = object : ComparableViolationLocalizationProvider {
        override fun <T : Comparable<T>> between(value: T, range: ClosedRange<T>, name: String?): String =
            "between: $name"

        override fun <T : Comparable<T>> equalTo(value: T, other: T, name: String?): String =
            "equalTo: $name"

        override fun <T : Comparable<T>> greaterThanOrEqualTo(value: T, other: T, name: String?): String =
            "greaterThanOrEqualTo: $name"

        override fun <T : Comparable<T>> greaterThan(value: T, other: T, name: String?): String =
            "greaterThan: $name"

        override fun <T : Comparable<T>> lessThanOrEqualTo(value: T, other: T, name: String?): String =
            "lessThanOrEqualTo: $name"

        override fun <T : Comparable<T>> lessThan(value: T, other: T, name: String?): String =
            "lessThan: $name"

        override fun <T : Comparable<T>> notBetween(value: T, range: ClosedRange<T>, name: String?): String =
            "notBetween: $name"

        override fun <T : Comparable<T>> notEqualTo(value: T, other: T, name: String?): String =
            "notEqualTo: $name"
    }

    private val provider = DefaultComparableViolationProvider(localizationProvider)

    @Test
    fun betweenDelegatesToLocalizationProvider() {
        val violation = provider.between(10, 1..5, "age")
        assertEquals("between: age", violation.reason)
    }

    @Test
    fun betweenWithNullName() {
        val violation = provider.between(0, 1..10, null)
        assertEquals("between: null", violation.reason)
    }

    @Test
    fun betweenWithStrings() {
        val violation = provider.between("zebra", "apple".."mango", "fruit")
        assertEquals("between: fruit", violation.reason)
    }

    @Test
    fun equalToDelegatesToLocalizationProvider() {
        val violation = provider.equalTo(5, 10, "count")
        assertEquals("equalTo: count", violation.reason)
    }

    @Test
    fun equalToWithNullName() {
        val violation = provider.equalTo(3, 5, null)
        assertEquals("equalTo: null", violation.reason)
    }

    @Test
    fun equalToWithStrings() {
        val violation = provider.equalTo("hello", "world", "greeting")
        assertEquals("equalTo: greeting", violation.reason)
    }

    @Test
    fun greaterThanOrEqualToDelegatesToLocalizationProvider() {
        val violation = provider.greaterThanOrEqualTo(5, 10, "score")
        assertEquals("greaterThanOrEqualTo: score", violation.reason)
    }

    @Test
    fun greaterThanOrEqualToWithNullName() {
        val violation = provider.greaterThanOrEqualTo(3, 5, null)
        assertEquals("greaterThanOrEqualTo: null", violation.reason)
    }

    @Test
    fun greaterThanOrEqualToWithStrings() {
        val violation = provider.greaterThanOrEqualTo("apple", "banana", "fruit")
        assertEquals("greaterThanOrEqualTo: fruit", violation.reason)
    }

    @Test
    fun greaterThanDelegatesToLocalizationProvider() {
        val violation = provider.greaterThan(5, 10, "value")
        assertEquals("greaterThan: value", violation.reason)
    }

    @Test
    fun greaterThanWithNullName() {
        val violation = provider.greaterThan(8, 15, null)
        assertEquals("greaterThan: null", violation.reason)
    }

    @Test
    fun greaterThanWithStrings() {
        val violation = provider.greaterThan("alpha", "zeta", "name")
        assertEquals("greaterThan: name", violation.reason)
    }

    @Test
    fun lessThanOrEqualToDelegatesToLocalizationProvider() {
        val violation = provider.lessThanOrEqualTo(15, 10, "limit")
        assertEquals("lessThanOrEqualTo: limit", violation.reason)
    }

    @Test
    fun lessThanOrEqualToWithNullName() {
        val violation = provider.lessThanOrEqualTo(20, 10, null)
        assertEquals("lessThanOrEqualTo: null", violation.reason)
    }

    @Test
    fun lessThanOrEqualToWithStrings() {
        val violation = provider.lessThanOrEqualTo("zebra", "apple", "animal")
        assertEquals("lessThanOrEqualTo: animal", violation.reason)
    }

    @Test
    fun lessThanDelegatesToLocalizationProvider() {
        val violation = provider.lessThan(15, 10, "value")
        assertEquals("lessThan: value", violation.reason)
    }

    @Test
    fun lessThanWithNullName() {
        val violation = provider.lessThan(25, 20, null)
        assertEquals("lessThan: null", violation.reason)
    }

    @Test
    fun lessThanWithStrings() {
        val violation = provider.lessThan("zoo", "apple", "word")
        assertEquals("lessThan: word", violation.reason)
    }

    @Test
    fun notBetweenDelegatesToLocalizationProvider() {
        val violation = provider.notBetween(5, 1..10, "age")
        assertEquals("notBetween: age", violation.reason)
    }

    @Test
    fun notBetweenWithNullName() {
        val violation = provider.notBetween(7, 5..10, null)
        assertEquals("notBetween: null", violation.reason)
    }

    @Test
    fun notBetweenWithStrings() {
        val violation = provider.notBetween("banana", "apple".."cherry", "fruit")
        assertEquals("notBetween: fruit", violation.reason)
    }

    @Test
    fun notEqualToDelegatesToLocalizationProvider() {
        val violation = provider.notEqualTo(5, 5, "count")
        assertEquals("notEqualTo: count", violation.reason)
    }

    @Test
    fun notEqualToWithNullName() {
        val violation = provider.notEqualTo(10, 10, null)
        assertEquals("notEqualTo: null", violation.reason)
    }

    @Test
    fun notEqualToWithStrings() {
        val violation = provider.notEqualTo("test", "test", "value")
        assertEquals("notEqualTo: value", violation.reason)
    }

    @Test
    fun usesDefaultLocalizationProviderWhenNotSpecified() {
        val defaultProvider = DefaultComparableViolationProvider()
        val violation = defaultProvider.equalTo(5, 10, "count")
        assertTrue(violation.reason.contains("count"))
    }
}
