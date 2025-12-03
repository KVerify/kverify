package io.github.kverify.core.rule

import io.github.kverify.core.context.ValidationContext
import io.github.kverify.core.util.FailingRule
import io.github.kverify.core.util.PassingRule
import io.github.kverify.core.util.violations
import io.github.kverify.core.violation.Violation
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertTrue

class RuleListTest {
    @Test
    fun emptyRuleListFactoryFunction() {
        val ruleList = RuleList<String>()

        assertTrue(ruleList.rules.isEmpty())
    }

    @Test
    fun singleRuleFactoryFunction() {
        val rule = FailingRule<String>("error")
        val ruleList = RuleList(rule)

        assertEquals(rule, ruleList.rules.single())
    }

    @Test
    fun varargRuleFactoryFunction() {
        val listOfRules =
            listOf<Rule<String>>(
                FailingRule("error1"),
                FailingRule("error2"),
            )
        val ruleList = RuleList(*listOfRules.toTypedArray())

        assertEquals(listOfRules, ruleList.rules)
    }

    @Test
    fun listConstructor() {
        val listOfRules = listOf(FailingRule<String>("error1"), FailingRule("error2"))
        val ruleList = RuleList(listOfRules)

        assertEquals(listOfRules, ruleList.rules)
    }

    @Test
    fun executeRunsAllRules() {
        val rule1 = FailingRule<String>("error1")
        val rule2 = FailingRule<String>("error2")
        val ruleList = RuleList(rule1, rule2)
        val violations = mutableListOf<Violation>()
        val context = ValidationContext { violations.add(it) }

        ruleList.execute(context, "test")

        assertEquals(violations("error1", "error2"), violations)
    }

    @Test
    fun executeWithEmptyRuleList() {
        val ruleList = RuleList<String>()
        val violations = mutableListOf<Violation>()
        val context = ValidationContext { violations.add(it) }

        ruleList.execute(context, "test")

        assertTrue(violations.isEmpty())
    }

    @Test
    fun executePreservesRuleOrder() {
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
        val ruleList = RuleList(rule1, rule2, rule3)
        val context = ValidationContext { }

        ruleList.execute(context, "test")

        assertEquals(
            listOf("rule1", "rule2", "rule3"),
            executionOrder,
        )
    }

    @Test
    fun plusOperatorWithSimpleRule() {
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
    fun plusOperatorWithAnotherRuleList() {
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
    fun plusOperatorWhenOtherIsRuleList() {
        val ruleList1 = RuleList(FailingRule<String>("error1"))
        val ruleList2 = RuleList(FailingRule<String>("error2"))

        val combined = ruleList1 + (ruleList2 as Rule<String>)

        assertIs<RuleList<String>>(combined)
        assertEquals(
            listOf(ruleList1.rules.first(), ruleList2.rules.first()),
            combined.rules,
        )
    }

    @Test
    fun plusOperatorChaining() {
        val ruleList = RuleList(FailingRule<String>("error1"))
        val rule1 = FailingRule<String>("error2")
        val rule2 = FailingRule<String>("error3")

        val combined = ruleList + rule1 + rule2

        assertIs<RuleList<String>>(combined)
        assertEquals(
            listOf(ruleList.rules.first(), rule1, rule2),
            combined.rules,
        )
    }

    @Test
    fun plusOperatorChainingExecutesAllRules() {
        val ruleList = RuleList(FailingRule<String>("error1"))
        val rule1 = FailingRule<String>("error2")
        val rule2 = FailingRule<String>("error3")
        val combined = ruleList + rule1 + rule2
        val violations = mutableListOf<Violation>()
        val context = ValidationContext { violations.add(it) }

        combined.execute(context, "test")

        assertEquals(violations("error1", "error2", "error3"), violations)
    }

    @Test
    fun ruleListWithMixedPassingAndFailingRules() {
        val ruleList =
            RuleList(
                PassingRule as Rule<String>,
                FailingRule("error1"),
                PassingRule,
                FailingRule("error2"),
            )
        val violations = mutableListOf<Violation>()
        val context = ValidationContext { violations.add(it) }

        ruleList.execute(context, "test")

        assertEquals(violations("error1", "error2"), violations)
    }

    @Test
    fun plusOperatorPreservesOriginalRuleLists() {
        val ruleList1 = RuleList(FailingRule<String>("error1"))
        val ruleList2 = RuleList(FailingRule<String>("error2"))

        val combined = ruleList1 + ruleList2

        assertEquals(
            listOf(ruleList1.rules.first(), ruleList2.rules.first()),
            combined.rules,
        )
    }
}
