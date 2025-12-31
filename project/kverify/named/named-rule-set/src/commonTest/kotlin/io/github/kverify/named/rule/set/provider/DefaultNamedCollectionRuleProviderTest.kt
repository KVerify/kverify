package io.github.kverify.named.rule.set.provider

import io.github.kverify.named.model.NamedValue
import io.github.kverify.util.MockCollectionCheckProvider
import io.github.kverify.util.MockCollectionViolationProvider
import io.github.kverify.util.assertName
import kotlin.test.Test
import kotlin.test.assertEquals

class DefaultNamedCollectionRuleProviderTest {
    private val mockCheckProvider = MockCollectionCheckProvider()
    private val mockViolationProvider = MockCollectionViolationProvider()
    private val provider =
        DefaultNamedCollectionRuleProvider(
            collectionCheckProvider = mockCheckProvider,
            collectionViolationProvider = mockViolationProvider,
        )
    private val namedCollection = NamedValue("myCollection", listOf(1, 2, 3))

    @Test
    fun namedContainsAllUsesCorrectCheck() {
        val name = "containsAll"
        val rule = provider.namedContainsAll(listOf(1, 2))

        val reason =
            rule
                .violationFactory
                .createViolation(namedCollection)
                .reason

        assertName(rule.validationCheck, name)
        assertEquals(reason, name)
    }

    @Test
    fun namedContainsNoneUsesCorrectCheck() {
        val name = "containsNone"
        val rule = provider.namedContainsNone(listOf(4, 5))

        val reason =
            rule
                .violationFactory
                .createViolation(namedCollection)
                .reason

        assertName(rule.validationCheck, name)
        assertEquals(reason, name)
    }

    @Test
    fun namedContainsOnlyUsesCorrectCheck() {
        val name = "containsOnly"
        val rule = provider.namedContainsOnly(listOf(1, 2, 3))

        val reason =
            rule
                .violationFactory
                .createViolation(namedCollection)
                .reason

        assertName(rule.validationCheck, name)
        assertEquals(reason, name)
    }

    @Test
    fun namedContainsUsesCorrectCheck() {
        val name = "contains"
        val rule = provider.namedContains(1)

        val reason =
            rule
                .violationFactory
                .createViolation(namedCollection)
                .reason

        assertName(rule.validationCheck, name)
        assertEquals(reason, name)
    }

    @Test
    fun namedDistinctUsesCorrectCheck() {
        val name = "distinct"
        val rule = provider.namedDistinct<Collection<*>>()

        val reason =
            rule
                .violationFactory
                .createViolation(namedCollection)
                .reason

        assertName(rule.validationCheck, name)
        assertEquals(reason, name)
    }

    @Test
    fun namedMaxSizeUsesCorrectCheck() {
        val name = "maxSize"
        val rule = provider.namedMaxSize<Collection<*>>(maxSize = 10)

        val reason =
            rule
                .violationFactory
                .createViolation(namedCollection)
                .reason

        assertName(rule.validationCheck, name)
        assertEquals(reason, name)
    }

    @Test
    fun namedMinSizeUsesCorrectCheck() {
        val name = "minSize"
        val rule = provider.namedMinSize<Collection<*>>(minSize = 1)

        val reason =
            rule
                .violationFactory
                .createViolation(namedCollection)
                .reason

        assertName(rule.validationCheck, name)
        assertEquals(reason, name)
    }

    @Test
    fun namedNotContainsUsesCorrectCheck() {
        val name = "notContains"
        val rule = provider.namedNotContains(4)

        val reason =
            rule
                .violationFactory
                .createViolation(namedCollection)
                .reason

        assertName(rule.validationCheck, name)
        assertEquals(reason, name)
    }

    @Test
    fun namedNotEmptyUsesCorrectCheck() {
        val name = "notEmpty"
        val rule = provider.namedNotEmpty<Collection<*>>()

        val reason =
            rule
                .violationFactory
                .createViolation(namedCollection)
                .reason

        assertName(rule.validationCheck, name)
        assertEquals(reason, name)
    }

    @Test
    fun namedNotOfSizeUsesCorrectCheck() {
        val name = "notOfSize"
        val rule = provider.namedNotOfSize<Collection<*>>(size = 5)

        val reason =
            rule
                .violationFactory
                .createViolation(namedCollection)
                .reason

        assertName(rule.validationCheck, name)
        assertEquals(reason, name)
    }

    @Test
    fun namedOfSizeUsesCorrectCheck() {
        val name = "ofSize"
        val rule = provider.namedOfSize<Collection<*>>(size = 3)

        val reason =
            rule
                .violationFactory
                .createViolation(namedCollection)
                .reason

        assertName(rule.validationCheck, name)
        assertEquals(reason, name)
    }

    @Test
    fun namedSizeBetweenUsesCorrectCheck() {
        val name = "sizeBetween"
        val rule = provider.namedSizeBetween<Collection<*>>(sizeRange = 1..10)

        val reason =
            rule
                .violationFactory
                .createViolation(namedCollection)
                .reason

        assertName(rule.validationCheck, name)
        assertEquals(reason, name)
    }

    @Test
    fun namedSizeNotBetweenUsesCorrectCheck() {
        val name = "sizeNotBetween"
        val rule = provider.namedSizeNotBetween<Collection<*>>(sizeRange = 10..20)

        val reason =
            rule
                .violationFactory
                .createViolation(namedCollection)
                .reason

        assertName(rule.validationCheck, name)
        assertEquals(reason, name)
    }
}
