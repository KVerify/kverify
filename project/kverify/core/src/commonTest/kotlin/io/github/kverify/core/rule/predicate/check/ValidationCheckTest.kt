package io.github.kverify.core.rule.predicate.check

import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class ValidationCheckTest {
    @Test
    fun validationCheckIsFunctionalInterface() {
        val check = ValidationCheck<String> { it.isNotEmpty() }

        assertTrue(check.isValid("test"))
        assertFalse(check.isValid(""))
    }

    @Test
    fun validationCheckNotOperatorInvertsResult() {
        val check = ValidationCheck<String> { it.isNotEmpty() }
        val inverted = !check

        assertFalse(inverted.isValid("test"))
        assertTrue(inverted.isValid(""))
    }

    @Test
    fun validationCheckNotOperatorCanBeChained() {
        val check = ValidationCheck<String> { it.isNotEmpty() }
        val doubleInverted = !!check

        assertTrue(doubleInverted.isValid("test"))
        assertFalse(doubleInverted.isValid(""))
    }

    @Test
    fun validationCheckWorksWithNullableTypes() {
        val check = ValidationCheck<String?> { it != null }

        assertTrue(check.isValid("test"))
        assertFalse(check.isValid(null))
    }

    @Test
    fun validationCheckWorksWithComplexPredicates() {
        val check = ValidationCheck<Int> { it in 1..<100 }

        assertTrue(check.isValid(50))
        assertFalse(check.isValid(0))
        assertFalse(check.isValid(100))
        assertFalse(check.isValid(-1))
    }
}
