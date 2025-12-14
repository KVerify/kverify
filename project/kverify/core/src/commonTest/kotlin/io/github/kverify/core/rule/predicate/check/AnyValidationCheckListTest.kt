package io.github.kverify.core.rule.predicate.check

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertIs
import kotlin.test.assertTrue

class AnyValidationCheckListTest {
    @Test
    fun emptyAnyValidationCheckList() {
        val checkList = AnyValidationCheckList<String>()

        assertTrue(checkList.checks.isEmpty())
        assertFalse(checkList.isValid("anything"))
    }

    @Test
    fun singleCheckFactoryFunction() {
        val check = ValidationCheck<String> { it.isNotEmpty() }
        val checkList = AnyValidationCheckList(check)

        assertEquals(check, checkList.checks.single())
    }

    @Test
    fun varargCheckFactoryFunction() {
        val listOfChecks =
            listOf(
                ValidationCheck<String> { it.isNotEmpty() },
                ValidationCheck { it.length >= 5 },
            )
        val checkList = AnyValidationCheckList(*listOfChecks.toTypedArray())

        assertEquals(listOfChecks, checkList.checks)
    }

    @Test
    fun listConstructor() {
        val listOfChecks =
            listOf(
                ValidationCheck<String> { it.isNotEmpty() },
                ValidationCheck { it.length >= 5 },
            )
        val checkList = AnyValidationCheckList(listOfChecks)

        assertEquals(listOfChecks, checkList.checks)
    }

    @Test
    fun isValidReturnsTrueWhenAllChecksPass() {
        val check1 = ValidationCheck<String> { it.isNotEmpty() }
        val check2 = ValidationCheck<String> { it.length >= 5 }
        val checkList = AnyValidationCheckList(check1, check2)

        assertTrue(checkList.isValid("hello world"))
    }

    @Test
    fun isValidReturnsTrueWhenOneCheckPasses() {
        val check1 = ValidationCheck<String> { it.isNotEmpty() }
        val check2 = ValidationCheck<String> { it.length >= 10 }
        val checkList = AnyValidationCheckList(check1, check2)

        assertTrue(checkList.isValid("hi"))
    }

    @Test
    fun isValidReturnsFalseWhenAllChecksFail() {
        val check1 = ValidationCheck<String> { it.isNotEmpty() }
        val check2 = ValidationCheck<String> { it.length >= 5 }
        val checkList = AnyValidationCheckList(check1, check2)

        assertFalse(checkList.isValid(""))
    }

    @Test
    fun isValidReturnsFalseForEmptyCheckList() {
        val checkList = AnyValidationCheckList<String>()

        assertFalse(checkList.isValid(""))
        assertFalse(checkList.isValid("anything"))
    }

    @Test
    fun anyValidationCheckListWithMixedChecks() {
        val checkList =
            AnyValidationCheckList<Int>(
                { it < 0 },
                { it > 100 },
            )

        assertTrue(checkList.isValid(-5))
        assertTrue(checkList.isValid(150))
        assertFalse(checkList.isValid(50))
    }

    @Test
    fun anyValidationCheckListShortCircuitsOnFirstPass() {
        val executionCount = mutableListOf<Int>()
        val check1 =
            ValidationCheck<String> {
                executionCount.add(1)
                false
            }
        val check2 =
            ValidationCheck<String> {
                executionCount.add(2)
                true
            }
        val check3 =
            ValidationCheck<String> {
                executionCount.add(3)
                false
            }
        val checkList = AnyValidationCheckList(check1, check2, check3)

        val result = checkList.isValid("test")

        assertTrue(result)
        assertEquals(
            listOf(1, 2),
            executionCount,
        )
    }

    @Test
    fun anyValidationCheckListExecutesAllChecksWhenAllFail() {
        val executionCount = mutableListOf<Int>()
        val check1 =
            ValidationCheck<String> {
                executionCount.add(1)
                false
            }
        val check2 =
            ValidationCheck<String> {
                executionCount.add(2)
                false
            }
        val check3 =
            ValidationCheck<String> {
                executionCount.add(3)
                false
            }
        val checkList = AnyValidationCheckList(check1, check2, check3)

        checkList.isValid("test")

        assertEquals(
            listOf(1, 2, 3),
            executionCount,
        )
    }

    @Test
    fun implementsValidationCheckListInterface() {
        val checkList = AnyValidationCheckList<String> { it.isNotEmpty() }

        assertIs<ValidationCheckList<String>>(checkList)
        assertIs<ValidationCheck<String>>(checkList)
    }
}
