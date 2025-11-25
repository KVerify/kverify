package io.github.kverify.core.rule.predicate.check

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class ValidationCheckListTest {
    @Test
    fun validationCheckListEmptyConstructorCreatesEmptyList() {
        val checkList = ValidationCheckList<String>()

        assertEquals(0, checkList.checks.size)
        assertTrue(checkList.isValid("test"))
    }

    @Test
    fun validationCheckListSingleCheckConstructorCreatesListWithOneCheck() {
        val check = ValidationCheck<String> { it.isNotEmpty() }
        val checkList = ValidationCheckList(check)

        assertEquals(check, checkList.checks.single())
    }

    @Test
    fun validationCheckListVarargConstructorCreatesListWithAllChecks() {
        val check1 = ValidationCheck<String> { it.isNotEmpty() }
        val check2 = ValidationCheck<String> { it.length > 3 }
        val checkList = ValidationCheckList(check1, check2)

        assertEquals(listOf(check1, check2), checkList.checks)
    }

    @Test
    fun validationCheckListFromListConstructorCreatesListWithAllChecks() {
        val check1 = ValidationCheck<String> { it.isNotEmpty() }
        val check2 = ValidationCheck<String> { it.length > 3 }
        val checks = listOf(check1, check2)
        val checkList = ValidationCheckList(checks)

        assertEquals(checks, checkList.checks)
    }

    @Test
    fun validationCheckListIsValidReturnsTrueWhenAllChecksPass() {
        val check1 = ValidationCheck<String> { it.isNotEmpty() }
        val check2 = ValidationCheck<String> { it.length > 3 }
        val checkList = ValidationCheckList(check1, check2)

        assertTrue(checkList.isValid("test"))
    }

    @Test
    fun validationCheckListIsValidReturnsFalseWhenAnyCheckFails() {
        val check1 = ValidationCheck<String> { it.isNotEmpty() }
        val check2 = ValidationCheck<String> { it.length > 10 }
        val checkList = ValidationCheckList(check1, check2)

        assertFalse(checkList.isValid("test"))
    }

    @Test
    fun validationCheckListIsValidReturnsFalseWhenFirstCheckFails() {
        val check1 = ValidationCheck<String> { it.isNotEmpty() }
        val check2 = ValidationCheck<String> { it.length > 3 }
        val checkList = ValidationCheckList(check1, check2)

        assertFalse(checkList.isValid(""))
    }

    @Test
    fun validationCheckListEmptyListReturnsTrueForAnyValue() {
        val checkList = ValidationCheckList<String>()

        assertTrue(checkList.isValid(""))
        assertTrue(checkList.isValid("test"))
        assertTrue(checkList.isValid("any value"))
    }

    @Test
    fun validationCheckListPlusCheckAddsCheckToEnd() {
        val check1 = ValidationCheck<String> { it.isNotEmpty() }
        val check2 = ValidationCheck<String> { it.length > 3 }
        val checkList = ValidationCheckList(check1)

        val result = checkList + check2

        assertEquals(listOf(check1, check2), result.checks)
    }

    @Test
    fun validationCheckListPlusCheckListCombinesAllChecks() {
        val check1 = ValidationCheck<String> { it.isNotEmpty() }
        val check2 = ValidationCheck<String> { it.length > 3 }
        val check3 = ValidationCheck<String> { it.startsWith("t") }
        val checkList1 = ValidationCheckList(check1, check2)
        val checkList2 = ValidationCheckList(check3)

        val result = checkList1 + checkList2

        assertEquals(listOf(check1, check2, check3), result.checks)
    }

    @Test
    fun validationCheckListPlusCheckListWhenOtherIsCheckListFlattens() {
        val check1 = ValidationCheck<String> { it.isNotEmpty() }
        val check2 = ValidationCheck<String> { it.length > 3 }
        val check3 = ValidationCheck<String> { it.startsWith("t") }
        val checkList1 = ValidationCheckList(check1)
        val checkList2 = ValidationCheckList(check2, check3)

        val result = checkList1 + checkList2

        assertEquals(listOf(check1, check2, check3), result.checks)
    }

    @Test
    fun validationCheckListPlusPreservesOriginalList() {
        val check1 = ValidationCheck<String> { it.isNotEmpty() }
        val check2 = ValidationCheck<String> { it.length > 3 }
        val original = ValidationCheckList(check1)

        val result = original + check2

        assertEquals(check1, original.checks.single())
        assertEquals(listOf(check1, check2), result.checks)
    }

    @Test
    fun validationCheckListPlusCheckListPreservesBothOriginals() {
        val check1 = ValidationCheck<String> { it.isNotEmpty() }
        val check2 = ValidationCheck<String> { it.length > 3 }
        val original1 = ValidationCheckList(check1)
        val original2 = ValidationCheckList(check2)

        val result = original1 + original2

        assertEquals(1, original1.checks.size)
        assertEquals(1, original2.checks.size)
        assertEquals(2, result.checks.size)
    }

    @Test
    fun validationCheckListWorksWithAnyType() {
        val check = ValidationCheck<Any?> { it != null }
        val checkList = ValidationCheckList(check)

        assertTrue(checkList.isValid("test"))
        assertFalse(checkList.isValid(null))
    }

    @Test
    fun validationCheckListWithComplexChecks() {
        val check1 = ValidationCheck<Int> { it > 0 }
        val check2 = ValidationCheck<Int> { it < 100 }
        val check3 = ValidationCheck<Int> { it % 2 == 0 }
        val checkList = ValidationCheckList(check1, check2, check3)

        assertTrue(checkList.isValid(50))
        assertFalse(checkList.isValid(51)) // odd number
        assertFalse(checkList.isValid(0)) // not > 0
        assertFalse(checkList.isValid(100)) // not < 100
    }

    @Test
    fun validationCheckListShortCircuitsOnFirstFailure() {
        var check2Called = false
        val check1 = ValidationCheck<String> { false }
        val check2 =
            ValidationCheck<String> {
                check2Called = true
                true
            }
        val checkList = ValidationCheckList(check1, check2)

        val result = checkList.isValid("test")

        assertFalse(result)
        assertFalse(check2Called)
    }
}
