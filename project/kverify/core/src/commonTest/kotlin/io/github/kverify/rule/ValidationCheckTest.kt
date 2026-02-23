package io.github.kverify.rule

import io.github.kverify.scope.CollectingValidationScope
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class ValidationCheckTest {
    private val scope = CollectingValidationScope()

    @Test
    fun notInvertsResult() {
        val check = ValidationCheck<String> { _, value -> value.isNotEmpty() }
        val inverted = !check

        assertTrue(check.isValid(scope, "hello"))
        assertFalse(inverted.isValid(scope, "hello"))

        assertFalse(check.isValid(scope, ""))
        assertTrue(inverted.isValid(scope, ""))
    }

    @Test
    fun andReturnsTrueWhenBothPass() {
        val check1 = ValidationCheck<String> { _, value -> value.isNotEmpty() }
        val check2 = ValidationCheck<String> { _, value -> value.length > 2 }
        val combined = check1 and check2

        assertTrue(combined.isValid(scope, "hello"))
    }

    @Test
    fun andReturnsFalseWhenFirstFails() {
        val check1 = ValidationCheck<String> { _, value -> value.isNotEmpty() }
        val check2 = ValidationCheck<String> { _, value -> value.length > 2 }
        val combined = check1 and check2

        assertFalse(combined.isValid(scope, ""))
    }

    @Test
    fun andReturnsFalseWhenSecondFails() {
        val check1 = ValidationCheck<String> { _, value -> value.isNotEmpty() }
        val check2 = ValidationCheck<String> { _, value -> value.length > 2 }
        val combined = check1 and check2

        assertFalse(combined.isValid(scope, "hi"))
    }

    @Test
    fun orReturnsTrueWhenFirstPasses() {
        val check1 = ValidationCheck<String> { _, value -> value.isNotEmpty() }
        val check2 = ValidationCheck<String> { _, value -> value.length > 10 }
        val combined = check1 or check2

        assertTrue(combined.isValid(scope, "hi"))
    }

    @Test
    fun orReturnsTrueWhenSecondPasses() {
        val check1 = ValidationCheck<String> { _, value -> value.isEmpty() }
        val check2 = ValidationCheck<String> { _, value -> value.length > 2 }
        val combined = check1 or check2

        assertTrue(combined.isValid(scope, "hello"))
    }

    @Test
    fun orReturnsFalseWhenBothFail() {
        val check1 = ValidationCheck<String> { _, value -> value.isEmpty() }
        val check2 = ValidationCheck<String> { _, value -> value.length > 10 }
        val combined = check1 or check2

        assertFalse(combined.isValid(scope, "hello"))
    }

    @Test
    fun andChainingFlattens() {
        val check1 = ValidationCheck<String> { _, _ -> true }
        val check2 = ValidationCheck<String> { _, _ -> true }
        val check3 = ValidationCheck<String> { _, _ -> true }

        val combined = check1 and check2 and check3

        assertEquals(3, combined.checks.size)
    }

    @Test
    fun orChainingFlattens() {
        val check1 = ValidationCheck<String> { _, _ -> false }
        val check2 = ValidationCheck<String> { _, _ -> false }
        val check3 = ValidationCheck<String> { _, _ -> true }

        val combined = check1 or check2 or check3

        assertEquals(3, combined.checks.size)
        assertTrue(combined.isValid(scope, ""))
    }

    @Test
    fun allValidationCheckListFactoryEmpty() {
        val list = AllValidationCheckList<String>()

        assertTrue(list.checks.isEmpty())
        assertTrue(list.isValid(scope, "any"))
    }

    @Test
    fun allValidationCheckListFactorySingle() {
        val check = ValidationCheck<String> { _, value -> value.isNotEmpty() }
        val list = AllValidationCheckList(check)

        assertTrue(list.checks.size == 1)
    }

    @Test
    fun anyValidationCheckListFactoryEmpty() {
        val list = AnyValidationCheckList<String>()

        assertTrue(list.checks.isEmpty())
        assertFalse(list.isValid(scope, "any"))
    }

    @Test
    fun anyValidationCheckListFactorySingle() {
        val check = ValidationCheck<String> { _, value -> value.isNotEmpty() }
        val list = AnyValidationCheckList(check)

        assertTrue(list.checks.size == 1)
    }
}
