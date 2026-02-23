package io.github.kverify.rule.set.collection

import io.github.kverify.context.ListValidationContext
import io.github.kverify.context.ValidationPathElement
import io.github.kverify.scope.CollectingValidationScope
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertIs
import kotlin.test.assertTrue

class ExactSizeRuleTest {
    // region Check tests

    @Test
    fun checkReturnsTrueWhenSizeMatchesExactly() {
        val scope = CollectingValidationScope()
        val check = ExactSizeCheck(size = 3)

        assertTrue(check.isValid(scope, listOf(1, 2, 3)))
    }

    @Test
    fun checkReturnsFalseWhenSizeIsBelowExpected() {
        val scope = CollectingValidationScope()
        val check = ExactSizeCheck(size = 3)

        assertFalse(check.isValid(scope, listOf(1, 2)))
    }

    @Test
    fun checkReturnsFalseWhenSizeIsAboveExpected() {
        val scope = CollectingValidationScope()
        val check = ExactSizeCheck(size = 3)

        assertFalse(check.isValid(scope, listOf(1, 2, 3, 4)))
    }

    @Test
    fun checkReturnsTrueForEmptyCollectionWhenSizeIsZero() {
        val scope = CollectingValidationScope()
        val check = ExactSizeCheck(size = 0)

        assertTrue(check.isValid(scope, emptyList<Int>()))
    }

    @Test
    fun checkReturnsFalseForEmptyCollectionWhenSizeIsOne() {
        val scope = CollectingValidationScope()
        val check = ExactSizeCheck(size = 1)

        assertFalse(check.isValid(scope, emptyList<Int>()))
    }

    @Test
    fun checkStoresSizeParameter() {
        val check = ExactSizeCheck(size = 7)

        assertEquals(7, check.size)
    }

    // endregion

    // region ViolationFactory tests

    @Test
    fun factoryCreatesExactSizeViolationWithDefaultReason() {
        val scope = CollectingValidationScope()
        val factory = ExactSizeViolationFactory(size = 3)

        val violation = factory.createViolation(scope, listOf(1, 2))

        assertIs<ExactSizeViolation>(violation)
        assertEquals(3, violation.expectedSize)
        assertEquals(2, violation.actualSize)
        assertEquals("Collection must have exactly 3 elements. Actual size: 2", violation.reason)
    }

    @Test
    fun factoryCreatesExactSizeViolationWithCustomReason() {
        val scope = CollectingValidationScope()
        val factory = ExactSizeViolationFactory(size = 3, reason = "Wrong count")

        val violation = factory.createViolation(scope, listOf(1, 2))

        assertIs<ExactSizeViolation>(violation)
        assertEquals("Wrong count", violation.reason)
    }

    @Test
    fun factoryCreatesViolationWithEmptyPathForDefaultScope() {
        val scope = CollectingValidationScope()
        val factory = ExactSizeViolationFactory(size = 3)

        val violation = factory.createViolation(scope, listOf(1, 2))

        assertIs<ExactSizeViolation>(violation)
        assertTrue(violation.validationPath.isEmpty())
    }

    @Test
    fun factoryCreatesViolationWithPathFromContext() {
        val scope =
            CollectingValidationScope(
                validationContext =
                    ListValidationContext(
                        ValidationPathElement.Property("field"),
                    ),
            )
        val factory = ExactSizeViolationFactory(size = 3)

        val violation = factory.createViolation(scope, listOf(1, 2))

        assertIs<ExactSizeViolation>(violation)
        assertEquals(
            listOf(ValidationPathElement.Property("field")),
            violation.validationPath,
        )
    }

    // endregion

    // region Rule tests

    @Test
    fun ruleStoresSizeParameter() {
        val rule = ExactSizeRule<List<Int>>(size = 5)

        assertEquals(5, rule.size)
    }

    @Test
    fun ruleHasExactSizeCheckWithMatchingSize() {
        val rule = ExactSizeRule<List<Int>>(size = 5)

        val check = rule.validationCheck
        assertIs<ExactSizeCheck>(check)
        assertEquals(5, check.size)
    }

    @Test
    fun ruleHasExactSizeViolationFactoryByDefault() {
        val rule = ExactSizeRule<List<Int>>(size = 5)

        val factory = rule.violationFactory
        assertIs<ExactSizeViolationFactory>(factory)
        assertEquals(5, factory.size)
    }

    @Test
    fun ruleWithCustomViolationFactory() {
        val factory = ExactSizeViolationFactory(size = 5, reason = "custom")
        val rule = ExactSizeRule<List<Int>>(size = 5, violationFactory = factory)

        assertIs<ExactSizeViolationFactory>(rule.violationFactory)
    }

    // endregion
}
