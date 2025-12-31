package io.github.kverify.named.rule.set.provider

import io.github.kverify.named.model.NamedValue
import io.github.kverify.util.MockComparableCheckProvider
import io.github.kverify.util.MockComparableViolationProvider
import io.github.kverify.util.assertName
import kotlin.test.Test
import kotlin.test.assertEquals

class DefaultNamedComparableRuleProviderTest {
    private val mockCheckProvider = MockComparableCheckProvider()
    private val mockViolationProvider = MockComparableViolationProvider()
    private val provider =
        DefaultNamedComparableRuleProvider(
            comparableCheckProvider = mockCheckProvider,
            comparableViolationProvider = mockViolationProvider,
        )
    private val namedValue = NamedValue("myValue", 5)

    @Test
    fun namedBetweenUsesCorrectCheck() {
        val name = "between"
        val rule = provider.namedBetween(1..10)

        val reason =
            rule
                .violationFactory
                .createViolation(namedValue)
                .reason

        assertName(rule.validationCheck, name)
        assertEquals(reason, name)
    }

    @Test
    fun namedEqualToUsesCorrectCheck() {
        val name = "equalTo"
        val rule = provider.namedEqualTo(10)

        val reason =
            rule
                .violationFactory
                .createViolation(namedValue)
                .reason

        assertName(rule.validationCheck, name)
        assertEquals(reason, name)
    }

    @Test
    fun namedGreaterThanOrEqualToUsesCorrectCheck() {
        val name = "greaterThanOrEqualTo"
        val rule = provider.namedGreaterThanOrEqualTo(10)

        val reason =
            rule
                .violationFactory
                .createViolation(namedValue)
                .reason

        assertName(rule.validationCheck, name)
        assertEquals(reason, name)
    }

    @Test
    fun namedGreaterThanUsesCorrectCheck() {
        val name = "greaterThan"
        val rule = provider.namedGreaterThan(10)

        val reason =
            rule
                .violationFactory
                .createViolation(namedValue)
                .reason

        assertName(rule.validationCheck, name)
        assertEquals(reason, name)
    }

    @Test
    fun namedLessThanOrEqualToUsesCorrectCheck() {
        val name = "lessThanOrEqualTo"
        val rule = provider.namedLessThanOrEqualTo(10)

        val reason =
            rule
                .violationFactory
                .createViolation(namedValue)
                .reason

        assertName(rule.validationCheck, name)
        assertEquals(reason, name)
    }

    @Test
    fun namedLessThanUsesCorrectCheck() {
        val name = "lessThan"
        val rule = provider.namedLessThan(10)

        val reason =
            rule
                .violationFactory
                .createViolation(namedValue)
                .reason

        assertName(rule.validationCheck, name)
        assertEquals(reason, name)
    }

    @Test
    fun namedNotBetweenUsesCorrectCheck() {
        val name = "notBetween"
        val rule = provider.namedNotBetween(1..10)

        val reason =
            rule
                .violationFactory
                .createViolation(namedValue)
                .reason

        assertName(rule.validationCheck, name)
        assertEquals(reason, name)
    }

    @Test
    fun namedNotEqualToUsesCorrectCheck() {
        val name = "notEqualTo"
        val rule = provider.namedNotEqualTo(10)

        val reason =
            rule
                .violationFactory
                .createViolation(namedValue)
                .reason

        assertName(rule.validationCheck, name)
        assertEquals(reason, name)
    }
}
