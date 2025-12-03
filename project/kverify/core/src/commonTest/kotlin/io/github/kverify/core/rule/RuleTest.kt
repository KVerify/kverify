package io.github.kverify.core.rule

import io.github.kverify.core.context.ValidationContext
import io.github.kverify.core.util.FailingRule
import io.github.kverify.core.util.PassingRule
import io.github.kverify.core.util.violations
import io.github.kverify.core.violation.Violation
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

class RuleTest {
    private val violations = mutableListOf<Violation>()
    private val context = ValidationContext { violations.add(it) }

    @Test
    fun plusOperatorWithTwoSimpleRules() {
        val rule1 = FailingRule<String>("error1")
        val rule2 = FailingRule<String>("error2")

        val combined = rule1 + rule2

        assertIs<RuleList<String>>(combined)
        assertEquals(
            listOf(rule1, rule2),
            combined.rules,
        )
    }

    @Test
    fun plusOperatorExecutesBothRules() {
        val rule1 = FailingRule<String>("error1")
        val rule2 = FailingRule<String>("error2")
        val combined = rule1 + rule2

        combined.execute(context, "test")

        assertEquals(violations("error1", "error2"), violations)
    }

    @Test
    fun plusOperatorWithRuleListAndSimpleRule() {
        val ruleList = RuleList(FailingRule<String>("error1"))
        val rule = FailingRule<String>("error2")

        val combined = ruleList + rule

        assertIs<RuleList<String>>(combined)
        assertEquals(
            listOf(ruleList.rules.first(), rule),
            combined.rules,
        )
    }

    @Test
    fun plusOperatorWithSimpleRuleAndRuleList() {
        val rule = FailingRule<String>("error1")
        val ruleList = RuleList(FailingRule<String>("error2"))

        val combined = rule + ruleList

        assertIs<RuleList<String>>(combined)
        assertEquals(
            listOf(rule, ruleList.rules.first()),
            combined.rules,
        )
    }

    @Test
    fun plusOperatorWithTwoRuleLists() {
        val ruleList1 = RuleList(FailingRule<String>("error1"))
        val ruleList2 = RuleList(FailingRule<String>("error2"))

        val combined = ruleList1 + ruleList2

        assertIs<RuleList<String>>(combined)
        assertEquals(
            listOf(ruleList1.rules.first(), ruleList2.rules.first()),
            combined.rules,
        )
    }

    @Test
    fun plusOperatorChaining() {
        val rule1 = FailingRule<String>("error1")
        val rule2 = FailingRule<String>("error2")
        val rule3 = FailingRule<String>("error3")

        val combined = rule1 + rule2 + rule3

        assertIs<RuleList<String>>(combined)
        assertEquals(
            listOf(rule1, rule2, rule3),
            combined.rules,
        )
    }

    @Test
    fun plusOperatorChainingExecutesAllRules() {
        val rule1 = FailingRule<String>("error1")
        val rule2 = FailingRule<String>("error2")
        val rule3 = FailingRule<String>("error3")
        val combined = rule1 + rule2 + rule3

        combined.execute(context, "test")

        assertEquals(violations("error1", "error2", "error3"), violations)
    }

    @Test
    fun plusOperatorWithPassingAndFailingRules() {
        val rule1 = PassingRule as Rule<String>
        val rule2 = FailingRule<String>("error")
        val combined = rule1 + rule2

        combined.execute(context, "test")

        assertEquals("error", violations.single().reason)
    }

    @Test
    fun plusOperatorPreservesRuleOrder() {
        val executionOrder = mutableListOf<String>()
        val rule1 =
            object : Rule<String> {
                override fun execute(
                    context: ValidationContext,
                    value: String,
                ) {
                    executionOrder.add("rule1")
                }
            }
        val rule2 =
            object : Rule<String> {
                override fun execute(
                    context: ValidationContext,
                    value: String,
                ) {
                    executionOrder.add("rule2")
                }
            }
        val rule3 =
            object : Rule<String> {
                override fun execute(
                    context: ValidationContext,
                    value: String,
                ) {
                    executionOrder.add("rule3")
                }
            }
        val combined = rule1 + rule2 + rule3
        val context = ValidationContext { }

        combined.execute(context, "test")

        assertEquals(
            listOf("rule1", "rule2", "rule3"),
            executionOrder,
        )
    }
}
