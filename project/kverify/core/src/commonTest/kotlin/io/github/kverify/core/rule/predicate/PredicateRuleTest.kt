package io.github.kverify.core.rule.predicate

import io.github.kverify.core.context.ValidationContext
import io.github.kverify.core.rule.predicate.check.ValidationCheck
import io.github.kverify.core.violation.Violation
import io.github.kverify.core.violation.ViolationReason
import io.github.kverify.core.violation.violation
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class PredicateRuleTest {
    private lateinit var storage: MutableList<Violation>
    private lateinit var context: ValidationContext

    @BeforeTest
    fun setUp() {
        storage = mutableListOf()
        context = ValidationContext { storage.add(it) }
    }

    @Test
    fun predicateRulePassesWhenCheckIsValid() {
        val check = ValidationCheck<String> { it.isNotEmpty() }
        val violation = violation("empty")
        val violationFactory = ViolationFactory<String> { violation }
        val rule = PredicateRule(check, violationFactory)

        rule.execute(context, "test")

        assertTrue(storage.isEmpty())
    }

    @Test
    fun predicateRuleFailsWhenCheckIsInvalid() {
        val check = ValidationCheck<String> { it.isNotEmpty() }
        val violation = violation("empty")
        val violationFactory = ViolationFactory<String> { violation }
        val rule = PredicateRule(check, violationFactory)

        rule.execute(context, "")

        assertEquals(violation, storage.single())
    }

    @Test
    fun predicateRuleWithCustomViolationFactory() {
        val check = ValidationCheck<String> { it.length >= 5 }
        val violationFactory =
            ViolationFactory<String> { value ->
                ViolationReason("too short: length=${value.length}")
            }
        val rule = PredicateRule(check, violationFactory)

        rule.execute(context, "hi")

        assertEquals("too short: length=2", storage.single().reason)
    }

    @Test
    fun predicateRuleWithComplexCheck() {
        val check = ValidationCheck<Int> { it in 1..100 }
        val violation = violation("out of range")
        val violationFactory = ViolationFactory<Int> { violation }
        val rule = PredicateRule(check, violationFactory)

        rule.execute(context, 150)

        assertEquals(violation, storage.single())
    }

    @Test
    fun predicateRuleWithComplexCheckPassingCase() {
        val check = ValidationCheck<Int> { it in 1..100 }
        val violation = violation("out of range")
        val violationFactory = ViolationFactory<Int> { violation }
        val rule = PredicateRule(check, violationFactory)

        rule.execute(context, 50)

        assertTrue(storage.isEmpty())
    }

    @Test
    fun predicateRuleViolationFactoryReceivesValue() {
        val capturedValues = mutableListOf<String>()
        val check = ValidationCheck<String> { false }
        val violation = violation("failed")
        val violationFactory =
            ViolationFactory<String> { value ->
                capturedValues.add(value)
                violation
            }
        val rule = PredicateRule(check, violationFactory)

        rule.execute(context, "test value")

        assertEquals("test value", capturedValues.single())
    }

    @Test
    fun predicateRuleWithAlwaysPassingCheck() {
        val check = ValidationCheck<Any?> { true }
        val violation = violation("error")
        val violationFactory = ViolationFactory<Any?> { violation }
        val rule = PredicateRule(check, violationFactory)

        rule.execute(context, "anything")

        assertTrue(storage.isEmpty())
    }

    @Test
    fun predicateRuleWithAlwaysFailingCheck() {
        val check = ValidationCheck<Any?> { false }
        val violation = violation("always fails")
        val violationFactory = ViolationFactory<Any?> { violation }
        val rule = PredicateRule(check, violationFactory)

        rule.execute(context, "anything")

        assertEquals(violation, storage.single())
    }
}
