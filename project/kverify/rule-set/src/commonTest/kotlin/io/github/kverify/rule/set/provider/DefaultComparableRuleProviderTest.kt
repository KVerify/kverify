package io.github.kverify.rule.set.provider

import io.github.kverify.util.MockComparableCheckProvider
import io.github.kverify.util.MockComparableViolationProvider
import io.github.kverify.util.assertName
import kotlin.test.Test
import kotlin.test.assertEquals

class DefaultComparableRuleProviderTest {
    private val mockCheckProvider = MockComparableCheckProvider()
    private val mockViolationProvider = MockComparableViolationProvider()
    private val provider =
        DefaultComparableRuleProvider(
            comparableCheckProvider = mockCheckProvider,
            comparableViolationProvider = mockViolationProvider,
        )
    private val value = 5

    @Test
    fun betweenUsesCorrectCheck() {
        val name = "between"
        val rule = provider.between(1..10)

        val reason =
            rule
                .violationFactory
                .createViolation(value)
                .reason

        assertName(rule.validationCheck, name)
        assertEquals(reason, name)
    }

    @Test
    fun equalToUsesCorrectCheck() {
        val name = "equalTo"
        val rule = provider.equalTo(10)

        val reason =
            rule
                .violationFactory
                .createViolation(value)
                .reason

        assertName(rule.validationCheck, name)
        assertEquals(reason, name)
    }

    @Test
    fun greaterThanOrEqualToUsesCorrectCheck() {
        val name = "greaterThanOrEqualTo"
        val rule = provider.greaterThanOrEqualTo(10)

        val reason =
            rule
                .violationFactory
                .createViolation(value)
                .reason

        assertName(rule.validationCheck, name)
        assertEquals(reason, name)
    }

    @Test
    fun greaterThanUsesCorrectCheck() {
        val name = "greaterThan"
        val rule = provider.greaterThan(10)

        val reason =
            rule
                .violationFactory
                .createViolation(value)
                .reason

        assertName(rule.validationCheck, name)
        assertEquals(reason, name)
    }

    @Test
    fun lessThanOrEqualToUsesCorrectCheck() {
        val name = "lessThanOrEqualTo"
        val rule = provider.lessThanOrEqualTo(10)

        val reason =
            rule
                .violationFactory
                .createViolation(value)
                .reason

        assertName(rule.validationCheck, name)
        assertEquals(reason, name)
    }

    @Test
    fun lessThanUsesCorrectCheck() {
        val name = "lessThan"
        val rule = provider.lessThan(10)

        val reason =
            rule
                .violationFactory
                .createViolation(value)
                .reason

        assertName(rule.validationCheck, name)
        assertEquals(reason, name)
    }

    @Test
    fun notBetweenUsesCorrectCheck() {
        val name = "notBetween"
        val rule = provider.notBetween(1..10)

        val reason =
            rule
                .violationFactory
                .createViolation(value)
                .reason

        assertName(rule.validationCheck, name)
        assertEquals(reason, name)
    }

    @Test
    fun notEqualToUsesCorrectCheck() {
        val name = "notEqualTo"
        val rule = provider.notEqualTo(10)

        val reason =
            rule
                .violationFactory
                .createViolation(value)
                .reason

        assertName(rule.validationCheck, name)
        assertEquals(reason, name)
    }
}
