package io.github.kverify.core.rule.predicate.check

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertIs
import kotlin.test.assertTrue

class AllValidationCheckListTest {
    @Test
    fun emptyAllValidationCheckList() {
        val checkList = AllValidationCheckList<String>()

        assertTrue(checkList.checks.isEmpty())
        assertTrue(checkList.isValid("anything"))
    }

    @Test
    fun singleCheckFactoryFunction() {
        val check = ValidationCheck<String> { it.isNotEmpty() }
        val checkList = AllValidationCheckList(check)

        assertEquals(check, checkList.checks.single())
    }

    @Test
    fun varargCheckFactoryFunction() {
        val listOfChecks =
            listOf(
                ValidationCheck<String> { it.isNotEmpty() },
                ValidationCheck { it.length >= 5 },
            )
        val checkList = AllValidationCheckList(*listOfChecks.toTypedArray())

        assertEquals(listOfChecks, checkList.checks)
    }

    @Test
    fun listConstructor() {
        val listOfChecks =
            listOf(
                ValidationCheck<String> { it.isNotEmpty() },
                ValidationCheck { it.length >= 5 },
            )
        val checkList = AllValidationCheckList(listOfChecks)

        assertEquals(listOfChecks, checkList.checks)
    }

    @Test
    fun isValidReturnsTrueWhenAllChecksPass() {
        val check1 = ValidationCheck<String> { it.isNotEmpty() }
        val check2 = ValidationCheck<String> { it.length >= 5 }
        val checkList = AllValidationCheckList(check1, check2)

        assertTrue(checkList.isValid("hello world"))
    }

    @Test
    fun isValidReturnsFalseWhenOneCheckFails() {
        val check1 = ValidationCheck<String> { it.isNotEmpty() }
        val check2 = ValidationCheck<String> { it.length >= 5 }
        val checkList = AllValidationCheckList(check1, check2)

        assertFalse(checkList.isValid("hi"))
    }

    @Test
    fun isValidReturnsFalseWhenAllChecksFail() {
        val check1 = ValidationCheck<String> { it.isNotEmpty() }
        val check2 = ValidationCheck<String> { it.length >= 5 }
        val checkList = AllValidationCheckList(check1, check2)

        assertFalse(checkList.isValid(""))
    }

    @Test
    fun isValidReturnsTrueForEmptyCheckList() {
        val checkList = AllValidationCheckList<String>()

        assertTrue(checkList.isValid(""))
        assertTrue(checkList.isValid("anything"))
    }

    @Test
    fun allValidationCheckListWithMixedChecks() {
        val checkList =
            AllValidationCheckList<Int>(
                { it > 0 },
                { it < 100 },
                { it % 2 == 0 },
            )

        assertTrue(checkList.isValid(50))
        assertFalse(checkList.isValid(51))
        assertFalse(checkList.isValid(-2))
        assertFalse(checkList.isValid(150))
    }

    @Test
    fun allValidationCheckListExecutesAllChecksWhenAllPass() {
        val executionCount = mutableListOf<Int>()
        val check1 =
            ValidationCheck<String> {
                executionCount.add(1)
                true
            }
        val check2 =
            ValidationCheck<String> {
                executionCount.add(2)
                true
            }
        val check3 =
            ValidationCheck<String> {
                executionCount.add(3)
                true
            }
        val checkList = AllValidationCheckList(check1, check2, check3)

        checkList.isValid("test")

        assertEquals(
            listOf(1, 2, 3),
            executionCount,
        )
    }

    @Test
    fun allValidationCheckListShortCircuitsOnFirstFailure() {
        val executionCount = mutableListOf<Int>()
        val check1 =
            ValidationCheck<String> {
                executionCount.add(1)
                true
            }
        val check2 =
            ValidationCheck<String> {
                executionCount.add(2)
                false
            }
        val check3 =
            ValidationCheck<String> {
                executionCount.add(3)
                true
            }
        val checkList = AllValidationCheckList(check1, check2, check3)

        val result = checkList.isValid("test")

        assertFalse(result)
        assertEquals(
            listOf(1, 2),
            executionCount,
        )
    }

    @Test
    fun implementsValidationCheckListInterface() {
        val checkList = AllValidationCheckList<String> { it.isNotEmpty() }

        assertIs<ValidationCheckList<String>>(checkList)
        assertIs<ValidationCheck<String>>(checkList)
    }
}
