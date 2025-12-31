package io.github.kverify.rule.set.provider

import io.github.kverify.util.MockCollectionCheckProvider
import io.github.kverify.util.MockCollectionViolationProvider
import io.github.kverify.util.assertName
import kotlin.test.Test
import kotlin.test.assertEquals

class DefaultCollectionRuleProviderTest {
    private val mockCheckProvider = MockCollectionCheckProvider()
    private val mockViolationProvider = MockCollectionViolationProvider()
    private val provider =
        DefaultCollectionRuleProvider(
            collectionCheckProvider = mockCheckProvider,
            collectionViolationProvider = mockViolationProvider,
        )
    private val collection = listOf(1, 2, 3)

    @Test
    fun containsAllUsesCorrectCheck() {
        val name = "containsAll"
        val rule = provider.containsAll(collection)

        val reason =
            rule
                .violationFactory
                .createViolation(collection)
                .reason

        assertName(rule.validationCheck, name)
        assertEquals(reason, name)
    }

    @Test
    fun containsNoneUsesCorrectCheck() {
        val name = "containsNone"
        val rule = provider.containsNone(collection)

        val reason =
            rule
                .violationFactory
                .createViolation(collection)
                .reason

        assertName(rule.validationCheck, name)
        assertEquals(reason, name)
    }

    @Test
    fun containsOnlyUsesCorrectCheck() {
        val name = "containsOnly"
        val rule = provider.containsOnly(collection)

        val reason =
            rule
                .violationFactory
                .createViolation(collection)
                .reason

        assertName(rule.validationCheck, name)
        assertEquals(reason, name)
    }

    @Test
    fun containsUsesCorrectCheck() {
        val name = "contains"
        val rule = provider.contains(1)

        val reason =
            rule
                .violationFactory
                .createViolation(collection)
                .reason

        assertName(rule.validationCheck, name)
        assertEquals(reason, name)
    }

    @Test
    fun distinctUsesCorrectCheck() {
        val name = "distinct"
        val rule = provider.distinct<Collection<*>>()

        val reason =
            rule
                .violationFactory
                .createViolation(collection)
                .reason

        assertName(rule.validationCheck, name)
        assertEquals(reason, name)
    }

    @Test
    fun maxSizeUsesCorrectCheck() {
        val name = "maxSize"
        val rule = provider.maxSize<Collection<*>>(maxSize = 10)

        val reason =
            rule
                .violationFactory
                .createViolation(collection)
                .reason

        assertName(rule.validationCheck, name)
        assertEquals(reason, name)
    }

    @Test
    fun minSizeUsesCorrectCheck() {
        val name = "minSize"
        val rule = provider.minSize<Collection<*>>(minSize = 5)

        val reason =
            rule
                .violationFactory
                .createViolation(collection)
                .reason

        assertName(rule.validationCheck, name)
        assertEquals(reason, name)
    }

    @Test
    fun notContainsUsesCorrectCheck() {
        val name = "notContains"
        val rule = provider.notContains(1)

        val reason =
            rule
                .violationFactory
                .createViolation(collection)
                .reason

        assertName(rule.validationCheck, name)
        assertEquals(reason, name)
    }

    @Test
    fun notEmptyUsesCorrectCheck() {
        val name = "notEmpty"
        val rule = provider.notEmpty<Collection<*>>()

        val reason =
            rule
                .violationFactory
                .createViolation(collection)
                .reason

        assertName(rule.validationCheck, name)
        assertEquals(reason, name)
    }

    @Test
    fun notOfSizeUsesCorrectCheck() {
        val name = "notOfSize"
        val rule = provider.notOfSize<Collection<*>>(size = 3)

        val reason =
            rule
                .violationFactory
                .createViolation(collection)
                .reason

        assertName(rule.validationCheck, name)
        assertEquals(reason, name)
    }

    @Test
    fun ofSizeUsesCorrectCheck() {
        val name = "ofSize"
        val rule = provider.ofSize<Collection<*>>(size = 3)

        val reason =
            rule
                .violationFactory
                .createViolation(collection)
                .reason

        assertName(rule.validationCheck, name)
        assertEquals(reason, name)
    }

    @Test
    fun sizeBetweenUsesCorrectCheck() {
        val name = "sizeBetween"
        val rule = provider.sizeBetween<Collection<*>>(sizeRange = 1..10)

        val reason =
            rule
                .violationFactory
                .createViolation(collection)
                .reason

        assertName(rule.validationCheck, name)
        assertEquals(reason, name)
    }

    @Test
    fun sizeNotBetweenUsesCorrectCheck() {
        val name = "sizeNotBetween"
        val rule = provider.sizeNotBetween<Collection<*>>(sizeRange = 1..10)

        val reason =
            rule
                .violationFactory
                .createViolation(collection)
                .reason

        assertName(rule.validationCheck, name)
        assertEquals(reason, name)
    }
}
