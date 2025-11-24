package io.github.kverify.core.rule

import io.github.kverify.core.context.ValidationContext
import io.github.kverify.core.util.assertContainsAllWithOrder
import io.github.kverify.core.util.violations
import io.github.kverify.core.violation.Violation
import io.github.kverify.core.violation.violation
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

class RuleTest {
    @Test
    fun ruleCanBeImplemented() {
        object : Rule<String> {
            override fun execute(
                context: ValidationContext,
                value: String,
            ): Unit = Unit
        }
    }

    @Test
    fun ruleExecuteIsCalledWithContextAndValue() {
        var capturedContext: ValidationContext? = null
        var capturedValue: String? = null

        val rule =
            object : Rule<String> {
                override fun execute(
                    context: ValidationContext,
                    value: String,
                ) {
                    capturedContext = context
                    capturedValue = value
                }
            }

        val context = ValidationContext { }
        val value = "test"

        rule.execute(context, value)

        assertEquals(context, capturedContext)
        assertEquals(value, capturedValue)
    }

    @Test
    fun ruleCanReportViolations() {
        val violations = mutableListOf<Violation>()
        val context = ValidationContext { violations.add(it) }
        val violation = violation("error")

        val rule =
            object : Rule<String> {
                override fun execute(
                    context: ValidationContext,
                    value: String,
                ) {
                    context.onFailure(violation)
                }
            }

        rule.execute(context, "test")

        assertEquals(violation, violations.single())
    }

    @Test
    fun plusOperatorCombinesTwoSimpleRules() {
        val violations = violations("error1", "error2")

        val violationStorage = mutableListOf<Violation>()
        val context = ValidationContext { violationStorage.add(it) }

        val rule1 =
            object : Rule<String> {
                override fun execute(
                    context: ValidationContext,
                    value: String,
                ) {
                    context.onFailure(violations[0])
                }
            }

        val rule2 =
            object : Rule<String> {
                override fun execute(
                    context: ValidationContext,
                    value: String,
                ) {
                    context.onFailure(violations[1])
                }
            }

        val combined = rule1 + rule2

        combined.execute(context, "test")

        assertIs<RuleList<String>>(combined)
        assertContainsAllWithOrder(violations, violationStorage)
    }

    @Test
    fun plusOperatorWithRuleListAndSimpleRule() {
        val rule1 =
            object : Rule<String> {
                override fun execute(
                    context: ValidationContext,
                    value: String,
                ): Unit = Unit
            }

        val rule2 =
            object : Rule<String> {
                override fun execute(
                    context: ValidationContext,
                    value: String,
                ): Unit = Unit
            }

        val rule3 =
            object : Rule<String> {
                override fun execute(
                    context: ValidationContext,
                    value: String,
                ): Unit = Unit
            }

        val ruleList = RuleList(rule1, rule2)
        val combined = ruleList + rule3

        assertIs<RuleList<String>>(combined)
        assertEquals(3, combined.rules.size)
        assertEquals(listOf(rule1, rule2, rule3), combined.rules)
    }

    @Test
    fun plusOperatorWithTwoRuleLists() {
        val rule1 =
            object : Rule<String> {
                override fun execute(
                    context: ValidationContext,
                    value: String,
                ): Unit = Unit
            }

        val rule2 =
            object : Rule<String> {
                override fun execute(
                    context: ValidationContext,
                    value: String,
                ): Unit = Unit
            }

        val rule3 =
            object : Rule<String> {
                override fun execute(
                    context: ValidationContext,
                    value: String,
                ): Unit = Unit
            }

        val rule4 =
            object : Rule<String> {
                override fun execute(
                    context: ValidationContext,
                    value: String,
                ): Unit = Unit
            }

        val ruleList1 = RuleList(rule1, rule2)
        val ruleList2 = RuleList(rule3, rule4)
        val combined = ruleList1 + ruleList2

        assertIs<RuleList<String>>(combined)
        assertEquals(4, combined.rules.size)
        assertEquals(listOf(rule1, rule2, rule3, rule4), combined.rules)
    }

    @Test
    fun plusOperatorChaining() {
        val rule1 =
            object : Rule<String> {
                override fun execute(
                    context: ValidationContext,
                    value: String,
                ): Unit = Unit
            }

        val rule2 =
            object : Rule<String> {
                override fun execute(
                    context: ValidationContext,
                    value: String,
                ): Unit = Unit
            }

        val rule3 =
            object : Rule<String> {
                override fun execute(
                    context: ValidationContext,
                    value: String,
                ): Unit = Unit
            }

        val combined = rule1 + rule2 + rule3

        assertIs<RuleList<String>>(combined)
        assertEquals(listOf(rule1, rule2, rule3), combined.rules)
    }

    @Test
    fun ruleWorksWithAnyType() {
        object : Rule<Any?> {
            override fun execute(
                context: ValidationContext,
                value: Any?,
            ): Unit = Unit
        }
    }

    @Test
    fun ruleWithGenericType() {
        val rule =
            object : Rule<List<String>> {
                override fun execute(
                    context: ValidationContext,
                    value: List<String>,
                ) {
                    if (value.isEmpty()) {
                        context.onFailure(violation("list is empty"))
                    }
                }
            }

        val violations = mutableListOf<Violation>()
        val context = ValidationContext { violations.add(it) }

        rule.execute(context, emptyList())

        assertEquals(1, violations.size)
        assertEquals("list is empty", violations[0].reason)
    }

    @Test
    fun multiplePlusOperationsPreserveOrder() {
        val executionOrder = mutableListOf<Int>()
        val context = ValidationContext { }

        val rule1 =
            object : Rule<String> {
                override fun execute(
                    context: ValidationContext,
                    value: String,
                ) {
                    executionOrder.add(1)
                }
            }

        val rule2 =
            object : Rule<String> {
                override fun execute(
                    context: ValidationContext,
                    value: String,
                ) {
                    executionOrder.add(2)
                }
            }

        val rule3 =
            object : Rule<String> {
                override fun execute(
                    context: ValidationContext,
                    value: String,
                ) {
                    executionOrder.add(3)
                }
            }

        val combined = rule1 + rule2 + rule3

        combined.execute(context, "test")

        assertEquals(listOf(1, 2, 3), executionOrder)
    }

    @Test
    fun plusOperatorWithSimpleRuleAndRuleList() {
        val rule1 =
            object : Rule<String> {
                override fun execute(
                    context: ValidationContext,
                    value: String,
                ): Unit = Unit
            }

        val rule2 =
            object : Rule<String> {
                override fun execute(
                    context: ValidationContext,
                    value: String,
                ): Unit = Unit
            }

        val rule3 =
            object : Rule<String> {
                override fun execute(
                    context: ValidationContext,
                    value: String,
                ): Unit = Unit
            }

        val ruleList = RuleList(rule2, rule3)
        val combined = rule1 + ruleList

        assertIs<RuleList<String>>(combined)
        assertEquals(2, combined.rules.size)
    }

    @Test
    fun ruleCanAccessValueProperties() {
        val violations = mutableListOf<Violation>()
        val context = ValidationContext { violations.add(it) }

        val rule =
            object : Rule<String> {
                override fun execute(
                    context: ValidationContext,
                    value: String,
                ) {
                    if (value.length < 3) {
                        context.onFailure(violation("too short"))
                    }
                }
            }

        rule.execute(context, "ab")

        assertEquals(1, violations.size)
        assertEquals("too short", violations[0].reason)
    }
}

@Suppress("UnusedPrivateClass", "Unused")
private class RuleCanBeImplemented : Rule<String> {
    override fun execute(
        context: ValidationContext,
        value: String,
    ): Unit = Unit
}
