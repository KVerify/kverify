package io.github.kverify.core.context

import io.github.kverify.core.util.FailingRule
import io.github.kverify.core.util.PassingRule
import io.github.kverify.core.util.violations
import io.github.kverify.core.violation.Violation
import io.github.kverify.core.violation.violation
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertSame
import kotlin.test.assertTrue

class ValidationContextTest {
    private lateinit var storage: MutableList<Violation>
    private lateinit var context: ValidationContext

    private val value = "test"
    private val violation = violation("error")
    private val rule = FailingRule<String>(violation)

    private val violationList = violations("error1", "error2")
    private val rules = violationList.map { FailingRule<String>(it) }

    @BeforeTest
    fun setUp() {
        storage = mutableListOf()
        context = ValidationContext { storage.add(it) }
    }

    @Test
    fun verifyWithRuleInfixNotation() {
        with(context) {
            value verifyWith rule
        }

        assertEquals(violation, storage.single())
    }

    @Test
    fun verifyWithMultipleRules() {
        with(context) {
            value verifyWith rules
        }

        assertEquals(violationList, storage)
    }

    @Test
    fun verifyWithVarargRules() {
        with(context) {
            value.verifyWith(rules[0], rules[1])
        }

        assertEquals(violationList, storage)
    }

    @Test
    fun verifyFunctionWithValue() {
        val value = "test"

        val result = context.verify(value)

        assertSame(value, result)
        assertTrue(storage.isEmpty())
    }

    @Test
    fun verifyFunctionWithValueAndRule() {
        val result = context.verify(value, rule)

        assertSame(value, result)
        assertEquals(violation, storage.single())
    }

    @Test
    fun verifyFunctionWithValueAndVarargRules() {
        val result = context.verify(value, rules[0], rules[1])

        assertSame(value, result)
        assertEquals(violationList, storage)
    }

    @Test
    fun verifyFunctionWithValueAndIterableRules() {
        val result = context.verify(value, rules)

        assertSame(value, result)
        assertEquals(violationList, storage)
    }

    @Test
    fun valueVerifyWithContextOnly() {
        val value = "test"

        val result = value.verifyWith(context)

        assertSame(value, result)
        assertTrue(storage.isEmpty())
    }

    @Test
    fun valueVerifyWithContextAndRule() {
        val result = value.verifyWith(context, rule)

        assertSame(value, result)
        assertEquals(violation, storage.single())
    }

    @Test
    fun valueVerifyWithContextAndVarargRules() {
        val result = value.verifyWith(context, rules[0], rules[1])

        assertSame(value, result)
        assertEquals(violationList, storage)
    }

    @Test
    fun valueVerifyWithContextAndIterableRules() {
        val result = value.verifyWith(context, rules)

        assertSame(value, result)
        assertEquals(violationList, storage)
    }

    @Test
    fun failIfWithTrueCondition() {
        context.failIf(true) { violation }

        assertEquals(violation, storage.single())
    }

    @Test
    fun failIfWithFalseCondition() {
        context.failIf(false) { violation }

        assertTrue(storage.isEmpty())
    }

    @Test
    fun failIfNotWithTrueCondition() {
        context.failIfNot(true) { violation }

        assertTrue(storage.isEmpty())
    }

    @Test
    fun failIfNotWithFalseCondition() {
        context.failIfNot(false) { violation }

        assertEquals(violation, storage.single())
    }

    @Test
    fun verifyWithPassingRule() {
        with(context) {
            value verifyWith PassingRule
        }

        assertTrue(storage.isEmpty())
    }
}
