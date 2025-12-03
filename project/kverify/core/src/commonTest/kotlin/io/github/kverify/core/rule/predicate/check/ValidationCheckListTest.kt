package io.github.kverify.core.rule.predicate.check

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertIs
import kotlin.test.assertTrue

class ValidationCheckListTest {
    @Test
    fun emptyValidationCheckList() {
        val checkList = ValidationCheckList<String>()

        assertTrue(checkList.checks.isEmpty())
        assertTrue(checkList.isValid("anything"))
    }

    @Test
    fun singleCheckFactoryFunction() {
        val check = ValidationCheck<String> { it.isNotEmpty() }
        val checkList = ValidationCheckList(check)

        assertEquals(check, checkList.checks.single())
    }

    @Test
    fun varargCheckFactoryFunction() {
        val listOfChecks =
            listOf(
                ValidationCheck<String> { it.isNotEmpty() },
                ValidationCheck { it.length >= 5 },
            )
        val checkList = ValidationCheckList(*listOfChecks.toTypedArray())

        assertEquals(listOfChecks, checkList.checks)
    }

    @Test
    fun listConstructor() {
        val listOfChecks =
            listOf(
                ValidationCheck<String> { it.isNotEmpty() },
                ValidationCheck { it.length >= 5 },
            )
        val checkList = ValidationCheckList(listOfChecks)

        assertEquals(listOfChecks, checkList.checks)
    }

    @Test
    fun isValidReturnsTrueWhenAllChecksPass() {
        val check1 = ValidationCheck<String> { it.isNotEmpty() }
        val check2 = ValidationCheck<String> { it.length >= 5 }
        val checkList = ValidationCheckList(check1, check2)

        assertTrue(checkList.isValid("hello world"))
    }

    @Test
    fun isValidReturnsFalseWhenOneCheckFails() {
        val check1 = ValidationCheck<String> { it.isNotEmpty() }
        val check2 = ValidationCheck<String> { it.length >= 5 }
        val checkList = ValidationCheckList(check1, check2)

        assertFalse(checkList.isValid("hi"))
    }

    @Test
    fun isValidReturnsFalseWhenAllChecksFail() {
        val check1 = ValidationCheck<String> { it.isNotEmpty() }
        val check2 = ValidationCheck<String> { it.length >= 5 }
        val checkList = ValidationCheckList(check1, check2)

        assertFalse(checkList.isValid(""))
    }

    @Test
    fun isValidReturnsTrueForEmptyCheckList() {
        val checkList = ValidationCheckList<String>()

        assertTrue(checkList.isValid(""))
        assertTrue(checkList.isValid("anything"))
    }

    @Test
    fun plusOperatorWithSingleCheck() {
        val checkList = ValidationCheckList<String> { it.isNotEmpty() }
        val newCheck = ValidationCheck<String> { it.length >= 5 }

        val combined = checkList + newCheck

        assertIs<ValidationCheckList<String>>(combined)
        assertEquals(
            listOf(checkList.checks.first(), newCheck),
            combined.checks,
        )
    }

    @Test
    fun plusOperatorWithAnotherCheckList() {
        val checkList1 = ValidationCheckList<String> { it.isNotEmpty() }
        val checkList2 = ValidationCheckList<String> { it.length >= 5 }

        val combined = checkList1 + checkList2

        assertIs<ValidationCheckList<String>>(combined)
        assertEquals(
            listOf(checkList1.checks.first(), checkList2.checks.first()),
            combined.checks,
        )
    }

    @Test
    fun plusOperatorWhenOtherIsValidationCheckList() {
        val checkList1 = ValidationCheckList<String> { it.isNotEmpty() }
        val checkList2 = ValidationCheckList<String> { it.length >= 5 }

        val combined = checkList1 + checkList2

        assertIs<ValidationCheckList<String>>(combined)
        assertEquals(
            listOf(checkList1.checks.first(), checkList2.checks.first()),
            combined.checks,
        )
    }

    @Test
    fun plusOperatorChaining() {
        val checkList = ValidationCheckList<String> { it.isNotEmpty() }
        val check1 = ValidationCheck<String> { it.length >= 5 }
        val check2 = ValidationCheck<String> { it.startsWith("h") }

        val combined = checkList + check1 + check2

        assertIs<ValidationCheckList<String>>(combined)
        assertEquals(
            listOf(checkList.checks.first(), check1, check2),
            combined.checks,
        )
    }

    @Test
    fun plusOperatorChainingValidation() {
        val checkList = ValidationCheckList<String> { it.isNotEmpty() }
        val check1 = ValidationCheck<String> { it.length >= 5 }
        val check2 = ValidationCheck<String> { it.startsWith("h") }
        val combined = checkList + check1 + check2

        assertTrue(combined.isValid("hello world"))
        assertFalse(combined.isValid("hi"))
        assertFalse(combined.isValid("goodbye"))
    }

    @Test
    fun plusOperatorPreservesOriginalCheckLists() {
        val checkList1 = ValidationCheckList(ValidationCheck<String> { it.isNotEmpty() })
        val checkList2 = ValidationCheckList(ValidationCheck<String> { it.length >= 5 })

        val combined = checkList1 + checkList2

        assertEquals(
            listOf(checkList1.checks.first(), checkList2.checks.first()),
            combined.checks,
        )
    }

    @Test
    fun validationCheckListWithMixedChecks() {
        val checkList =
            ValidationCheckList<Int>(
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
    fun validationCheckListExecutesAllChecks() {
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
        val checkList = ValidationCheckList(check1, check2, check3)

        checkList.isValid("test")

        assertEquals(
            listOf(1, 2, 3),
            executionCount,
        )
    }

    @Test
    fun validationCheckListShortCircuitsOnFirstFailure() {
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
        val checkList = ValidationCheckList(check1, check2, check3)

        val result = checkList.isValid("test")

        assertFalse(result)
        assertEquals(
            listOf(1, 2),
            executionCount,
        )
    }
}
