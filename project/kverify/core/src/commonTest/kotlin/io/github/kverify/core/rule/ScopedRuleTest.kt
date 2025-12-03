package io.github.kverify.core.rule

import io.github.kverify.core.context.ValidationContext
import io.github.kverify.core.context.failIf
import io.github.kverify.core.context.failIfNot
import io.github.kverify.core.util.FailingRule
import io.github.kverify.core.util.PassingRule
import io.github.kverify.core.violation.Violation
import io.github.kverify.core.violation.violation
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

class ScopedRuleTest {
    private lateinit var storage: MutableList<Violation>
    private lateinit var context: ValidationContext

    @BeforeTest
    fun setUp() {
        storage = mutableListOf()
        context = ValidationContext { storage.add(it) }
    }

    @Test
    fun scopedRuleFactoryFunction() {
        val violation = violation("empty")
        val rule =
            Rule<String> { value ->
                failIf(value.isEmpty()) { violation }
            }

        assertIs<ScopedRule<String>>(rule)

        rule.execute(context, "")

        assertEquals(violation, storage.single())
    }

    @Test
    fun scopedRuleFactoryFunctionPassingCase() {
        val violation = violation("empty")
        val rule =
            Rule<String> { value ->
                failIf(value.isEmpty()) { violation }
            }

        rule.execute(context, "not empty")

        assertEquals(emptyList(), storage)
    }

    @Test
    fun scopedRuleCanUseContextFunctions() {
        val violation = violation("empty")
        val rule =
            Rule<String> { value ->
                failIfNot(value.isNotEmpty()) { violation }
            }

        rule.execute(context, "")

        assertEquals(violation, storage.single())
    }

    @Test
    fun scopedRuleCanAccessValue() {
        val value = "hi"
        val violation = violation("too short: $value")
        val rule =
            Rule<String> { v ->
                failIf(v.length < 5) { violation }
            }

        rule.execute(context, value)

        assertEquals(violation, storage.single())
    }

    @Test
    fun asScopedConvertsRuleToScopedRule() {
        val violation = violation("error")
        val originalRule = FailingRule<String>(violation)

        val scopedRule = originalRule.asScoped()

        assertIs<ScopedRule<String>>(scopedRule)
    }

    @Test
    fun asScopedExecutesOriginalRule() {
        val violation = violation("error")
        val originalRule = FailingRule<String>(violation)
        val scopedRule = originalRule.asScoped()

        scopedRule.execute(context, "test")

        assertEquals(violation, storage.single())
    }

    @Test
    fun asScopedWithPassingRule() {
        val originalRule = PassingRule as Rule<String>
        val scopedRule = originalRule.asScoped()

        scopedRule.execute(context, "test")

        assertEquals(emptyList(), storage)
    }

    @Test
    fun scopedRuleInterfaceExecuteCallsExecuteScoped() {
        val violation = violation("failed")
        val scopedRule =
            object : ScopedRule<String> {
                override fun ValidationContext.executeScoped(value: String) {
                    failIf(value == "fail") { violation }
                }
            }

        scopedRule.execute(context, "fail")

        assertEquals(violation, storage.single())
    }

    @Test
    fun scopedRuleCanVerifyNestedRules() {
        val violation = violation("nested error")
        val nestedRule = FailingRule<String>(violation)
        val scopedRule =
            Rule<String> { value ->
                value verifyWith nestedRule
            }

        scopedRule.execute(context, "test")

        assertEquals(violation, storage.single())
    }

    @Test
    fun scopedRuleWithComplexLogic() {
        val negativeViolation = violation("negative")
        val zeroViolation = violation("zero")
        val tooLargeViolation = violation("too large")

        val rule =
            Rule<Int> { value ->
                if (value < 0) {
                    onFailure(negativeViolation)
                } else if (value == 0) {
                    onFailure(zeroViolation)
                } else if (value > 100) {
                    onFailure(tooLargeViolation)
                }
            }

        rule.execute(context, -5)

        assertEquals(negativeViolation, storage.single())
    }
}
