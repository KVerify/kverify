package io.github.kverify.core.rule.predicate.check

import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class ValidationCheckTest {
    @Test
    fun validationCheckWithPassingCondition() {
        val check = ValidationCheck<String> { it.isNotEmpty() }

        assertTrue(check.isValid("test"))
    }

    @Test
    fun validationCheckWithFailingCondition() {
        val check = ValidationCheck<String> { it.isNotEmpty() }

        assertFalse(check.isValid(""))
    }

    @Test
    fun validationCheckWithComplexLogic() {
        val check = ValidationCheck<Int> { it in 1..<100 }

        assertTrue(check.isValid(50))
        assertFalse(check.isValid(150))
        assertFalse(check.isValid(-10))
    }

    @Test
    fun notOperatorInvertsCheck() {
        val check = ValidationCheck<String> { it.isNotEmpty() }
        val inverted = !check

        assertFalse(inverted.isValid("test"))
        assertTrue(inverted.isValid(""))
    }

    @Test
    fun notOperatorDoubleInversion() {
        val check = ValidationCheck<String> { it.isNotEmpty() }
        val doubleInverted = !!check

        assertTrue(doubleInverted.isValid("test"))
        assertFalse(doubleInverted.isValid(""))
    }

    @Test
    fun notOperatorWithAlwaysTrueCheck() {
        val check = ValidationCheck<Any?> { true }
        val inverted = !check

        assertFalse(inverted.isValid("anything"))
        assertFalse(inverted.isValid(null))
    }

    @Test
    fun notOperatorWithAlwaysFalseCheck() {
        val check = ValidationCheck<Any?> { false }
        val inverted = !check

        assertTrue(inverted.isValid("anything"))
        assertTrue(inverted.isValid(null))
    }

    @Test
    fun validationCheckWithNullValue() {
        val check = ValidationCheck<String?> { it != null && it.isNotEmpty() }

        assertFalse(check.isValid(null))
        assertTrue(check.isValid("test"))
    }

    @Test
    fun validationCheckWithCustomType() {
        data class Person(
            val name: String,
            val age: Int,
        )

        val check = ValidationCheck<Person> { it.age >= 18 }

        assertTrue(check.isValid(Person("John", 25)))
        assertFalse(check.isValid(Person("Jane", 15)))
    }

    @Test
    fun validationCheckWithCollections() {
        val check =
            ValidationCheck<List<Int>> { intList ->
                intList.isNotEmpty() && intList.all { it > 0 }
            }

        assertTrue(check.isValid(listOf(1, 2, 3)))
        assertFalse(check.isValid(emptyList()))
        assertFalse(check.isValid(listOf(1, -2, 3)))
    }
}
