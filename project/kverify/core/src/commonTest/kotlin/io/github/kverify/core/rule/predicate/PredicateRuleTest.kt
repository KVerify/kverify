package io.github.kverify.core.rule.predicate

import io.github.kverify.core.context.ValidationContext
import io.github.kverify.core.rule.predicate.check.ValidationCheck
import io.github.kverify.core.rule.predicate.check.ValidationCheckList
import io.github.kverify.core.rule.predicate.check.not
import io.github.kverify.core.rule.predicate.check.plus
import io.github.kverify.core.violation.Violation
import io.github.kverify.core.violation.violation
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class PredicateRuleTest {
    private lateinit var violationStorage: MutableList<Violation>
    private lateinit var context: ValidationContext

    @BeforeTest
    fun setUp() {
        violationStorage = mutableListOf()
        context = ValidationContext { violationStorage.add(it) }
    }

    @Test
    fun predicateRuleExecutesCheckAndPassesWhenValid() {
        val check = ValidationCheck<String> { true }
        val violationFactory = ViolationFactory<String> { violation("error") }
        val rule = PredicateRule(check, violationFactory)

        rule.execute(context, "test")

        assertTrue(violationStorage.isEmpty())
    }

    @Test
    fun predicateRuleExecutesCheckAndFailsWhenInvalid() {
        val violation = violation("error")
        val check = ValidationCheck<String> { false }
        val violationFactory = ViolationFactory<String> { violation }
        val rule = PredicateRule(check, violationFactory)

        rule.execute(context, "test")

        assertEquals(1, violationStorage.size)
        assertEquals(violation, violationStorage.single())
    }

    @Test
    fun predicateRulePassesValueToCheck() {
        var capturedValue: String? = null
        val check =
            ValidationCheck<String> { value ->
                capturedValue = value
                true
            }
        val violationFactory = ViolationFactory<String> { violation("error") }
        val rule = PredicateRule(check, violationFactory)

        val testValue = "test value"
        rule.execute(context, testValue)

        assertEquals(testValue, capturedValue)
    }

    @Test
    fun predicateRulePassesValueToViolationFactory() {
        var capturedValue: String? = null
        val check = ValidationCheck<String> { false }
        val violationFactory =
            ViolationFactory<String> { value ->
                capturedValue = value
                violation("error")
            }
        val rule = PredicateRule(check, violationFactory)

        val testValue = "test value"
        rule.execute(context, testValue)

        assertEquals(testValue, capturedValue)
    }

    @Test
    fun predicateRuleWorksWithNullableTypes() {
        val violation = violation("error")
        val check = ValidationCheck<String?> { it == null }
        val violationFactory = ViolationFactory<String?> { violation }
        val rule = PredicateRule(check, violationFactory)

        val value: String? = null
        rule.execute(context, value)

        assertTrue(violationStorage.isEmpty())
    }

    @Test
    fun predicateRuleViolationFactoryIsOnlyCalledWhenCheckFails() {
        var factoryCalled = false
        val check = ValidationCheck<String> { true }
        val violationFactory =
            ViolationFactory<String> {
                factoryCalled = true
                violation("error")
            }
        val rule = PredicateRule(check, violationFactory)

        rule.execute(context, "test")

        assertFalse(factoryCalled)
    }
}
