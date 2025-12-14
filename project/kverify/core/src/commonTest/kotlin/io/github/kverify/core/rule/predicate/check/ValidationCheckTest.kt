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

    @Test
    fun andOperatorCombinesTwoChecks() {
        val check1 = ValidationCheck<Int> { it > 0 }
        val check2 = ValidationCheck<Int> { it < 10 }

        val combined = check1 and check2

        assertTrue(combined.isValid(5))
        assertFalse(combined.isValid(-5))
        assertFalse(combined.isValid(15))
    }

    @Test
    fun andOperatorReturnsAllValidationCheckList() {
        val check1 = ValidationCheck<String> { it.isNotEmpty() }
        val check2 = ValidationCheck<String> { it.length >= 5 }

        val combined = check1 and check2

        kotlin.test.assertEquals(2, combined.checks.size)
    }

    @Test
    fun andOperatorFlattensAllValidationCheckList() {
        val check1 = ValidationCheck<Int> { it > 0 }
        val check2 = ValidationCheck<Int> { it < 100 }
        val check3 = ValidationCheck<Int> { it % 2 == 0 }

        val combined = check1 and check2 and check3

        kotlin.test.assertEquals(3, combined.checks.size)
        assertTrue(combined.isValid(50))
        assertFalse(combined.isValid(51))
    }

    @Test
    fun andOperatorWithAllValidationCheckListOnLeft() {
        val checkList = AllValidationCheckList<Int>({ it > 0 }, { it < 100 })
        val check = ValidationCheck<Int> { it % 2 == 0 }

        val combined = checkList and check

        kotlin.test.assertEquals(3, combined.checks.size)
        assertTrue(combined.isValid(50))
        assertFalse(combined.isValid(51))
    }

    @Test
    fun andOperatorWithAllValidationCheckListOnRight() {
        val check = ValidationCheck<Int> { it > 0 }
        val checkList = AllValidationCheckList<Int>({ it < 100 }, { it % 2 == 0 })

        val combined = check and checkList

        kotlin.test.assertEquals(3, combined.checks.size)
        assertTrue(combined.isValid(50))
        assertFalse(combined.isValid(51))
    }

    @Test
    fun andOperatorWithTwoAllValidationCheckLists() {
        val checkList1 = AllValidationCheckList<Int>({ it > 0 }, { it < 100 })
        val checkList2 = AllValidationCheckList<Int>({ it % 2 == 0 }, { it % 5 == 0 })

        val combined = checkList1 and checkList2

        kotlin.test.assertEquals(4, combined.checks.size)
        assertTrue(combined.isValid(10))
        assertFalse(combined.isValid(6))
    }

    @Test
    fun orOperatorCombinesTwoChecks() {
        val check1 = ValidationCheck<Int> { it < 0 }
        val check2 = ValidationCheck<Int> { it > 10 }

        val combined = check1 or check2

        assertTrue(combined.isValid(-5))
        assertTrue(combined.isValid(15))
        assertFalse(combined.isValid(5))
    }

    @Test
    fun orOperatorReturnsAnyValidationCheckList() {
        val check1 = ValidationCheck<String> { it.isEmpty() }
        val check2 = ValidationCheck<String> { it.isBlank() }

        val combined = check1 or check2

        kotlin.test.assertEquals(2, combined.checks.size)
    }

    @Test
    fun orOperatorFlattensAnyValidationCheckList() {
        val check1 = ValidationCheck<Int> { it < 0 }
        val check2 = ValidationCheck<Int> { it > 100 }
        val check3 = ValidationCheck<Int> { it == 50 }

        val combined = check1 or check2 or check3

        kotlin.test.assertEquals(3, combined.checks.size)
        assertTrue(combined.isValid(-5))
        assertTrue(combined.isValid(150))
        assertTrue(combined.isValid(50))
        assertFalse(combined.isValid(25))
    }

    @Test
    fun orOperatorWithAnyValidationCheckListOnLeft() {
        val checkList = AnyValidationCheckList<Int>({ it < 0 }, { it > 100 })
        val check = ValidationCheck<Int> { it == 50 }

        val combined = checkList or check

        kotlin.test.assertEquals(3, combined.checks.size)
        assertTrue(combined.isValid(-5))
        assertTrue(combined.isValid(150))
        assertTrue(combined.isValid(50))
    }

    @Test
    fun orOperatorWithAnyValidationCheckListOnRight() {
        val check = ValidationCheck<Int> { it < 0 }
        val checkList = AnyValidationCheckList<Int>({ it > 100 }, { it == 50 })

        val combined = check or checkList

        kotlin.test.assertEquals(3, combined.checks.size)
        assertTrue(combined.isValid(-5))
        assertTrue(combined.isValid(150))
        assertTrue(combined.isValid(50))
    }

    @Test
    fun orOperatorWithTwoAnyValidationCheckLists() {
        val checkList1 = AnyValidationCheckList<Int>({ it < 0 }, { it > 100 })
        val checkList2 = AnyValidationCheckList<Int>({ it == 50 }, { it == 75 })

        val combined = checkList1 or checkList2

        kotlin.test.assertEquals(4, combined.checks.size)
        assertTrue(combined.isValid(-5))
        assertTrue(combined.isValid(150))
        assertTrue(combined.isValid(50))
        assertTrue(combined.isValid(75))
        assertFalse(combined.isValid(25))
    }

    @Test
    fun complexCombinationOfAndOr() {
        val isPositive = ValidationCheck<Int> { it > 0 }
        val isEven = ValidationCheck<Int> { it % 2 == 0 }
        val isNegative = ValidationCheck<Int> { it < 0 }
        val isOdd = ValidationCheck<Int> { it % 2 != 0 }

        // (positive AND even) OR (negative AND odd)
        val combined = (isPositive and isEven) or (isNegative and isOdd)

        assertTrue(combined.isValid(2))   // positive even
        assertTrue(combined.isValid(10))  // positive even
        assertTrue(combined.isValid(-1))  // negative odd
        assertTrue(combined.isValid(-5))  // negative odd
        assertFalse(combined.isValid(1))  // positive odd
        assertFalse(combined.isValid(-2)) // negative even
    }
}
