package io.github.kverify.rule

import io.github.kverify.result.ValidationResult
import io.github.kverify.scope.CollectingValidationScope
import io.github.kverify.scope.ValidationScope
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

class RuleTest {
    private fun <T> failingRule(reason: String): Rule<T> =
        object : Rule<T> {
            override fun execute(
                scope: ValidationScope,
                value: T,
            ) {
                scope.onFailure(ViolationReason(reason))
            }
        }

    @Test
    fun ruleListExecutesAllRules() {
        val scope = CollectingValidationScope()
        val ruleList =
            RuleList<String>(
                failingRule("rule1"),
                failingRule("rule2"),
            )

        ruleList.execute(scope, "value")

        val result = scope.build()
        assertIs<ValidationResult.Invalid>(result)
        assertEquals(2, result.violations.size)
    }

    @Test
    fun emptyRuleListDoesNothing() {
        val scope = CollectingValidationScope()
        val ruleList = RuleList<String>()

        ruleList.execute(scope, "value")

        assertIs<ValidationResult.Valid>(scope.build())
    }

    @Test
    fun ruleListFactoryWithSingleRule() {
        val rule = failingRule<String>("error")
        val ruleList = RuleList(rule)

        assertEquals(1, ruleList.rules.size)
    }

    @Test
    fun rulePlusRuleCreatesRuleList() {
        val rule1 = failingRule<String>("rule1")
        val rule2 = failingRule<String>("rule2")

        val combined = rule1 + rule2

        assertIs<RuleList<String>>(combined)
        assertEquals(2, (combined as RuleList).rules.size)
    }

    @Test
    fun ruleListPlusRuleAppendsRule() {
        val ruleList = RuleList(failingRule<String>("rule1"))
        val rule = failingRule<String>("rule2")

        val combined = ruleList + rule

        assertEquals(2, combined.rules.size)
    }

    @Test
    fun ruleListPlusRuleListCombinesRules() {
        val ruleList1 = RuleList(failingRule<String>("rule1"))
        val ruleList2 = RuleList(failingRule<String>("rule2"))

        val combined = ruleList1 + ruleList2

        assertEquals(2, combined.rules.size)
    }

    @Test
    fun rulePlusRuleListFlattens() {
        val rule = failingRule<String>("rule1")
        val ruleList =
            RuleList(
                failingRule<String>("rule2"),
                failingRule<String>("rule3"),
            )

        val combined = rule + ruleList

        assertIs<RuleList<String>>(combined)
        assertEquals(3, (combined as RuleList).rules.size)
    }

    @Test
    fun ruleListPlusRuleCombinesCorrectly() {
        val ruleList =
            RuleList(
                failingRule<String>("rule1"),
                failingRule<String>("rule2"),
            )
        val rule = failingRule<String>("rule3")

        val combined = ruleList + rule

        assertIs<RuleList<String>>(combined)
        assertEquals(3, combined.rules.size)
    }
}
