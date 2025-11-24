package io.github.kverify.core.rule

import io.github.kverify.core.context.ValidationContext
import io.github.kverify.core.util.assertContainsAllWithOrder
import io.github.kverify.core.violation.Violation
import io.github.kverify.core.violation.violation
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertTrue

class RuleListTest {
    @Test
    fun ruleListIsRule() {
        val ruleList = RuleList<String>(emptyList())

        assertIs<Rule<String>>(ruleList)
    }

    @Test
    fun ruleListWithEmptyListExecutesNoRules() {
        val violations = mutableListOf<Violation>()
        val context = ValidationContext { violations.add(it) }

        val ruleList = RuleList<String>(emptyList())

        ruleList.execute(context, "test")

        assertTrue(violations.isEmpty())
    }

    @Test
    fun ruleListExecutesAllRulesInOrder() {
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

        val ruleList = RuleList(listOf(rule1, rule2, rule3))

        ruleList.execute(context, "test")

        assertEquals(listOf(1, 2, 3), executionOrder)
    }

    @Test
    fun ruleListCollectsViolationsFromAllRules() {
        val violations = mutableListOf<Violation>()
        val context = ValidationContext { violations.add(it) }

        val rule1 =
            object : Rule<String> {
                override fun execute(
                    context: ValidationContext,
                    value: String,
                ) {
                    context.onFailure(violation("error1"))
                }
            }

        val rule2 =
            object : Rule<String> {
                override fun execute(
                    context: ValidationContext,
                    value: String,
                ) {
                    context.onFailure(violation("error2"))
                }
            }

        val ruleList = RuleList(listOf(rule1, rule2))

        ruleList.execute(context, "test")

        assertEquals(2, violations.size)
        assertEquals("error1", violations[0].reason)
        assertEquals("error2", violations[1].reason)
    }

    @Test
    fun ruleListFactoryWithNoArguments() {
        val ruleList = RuleList<String>()

        assertTrue(ruleList.rules.isEmpty())
    }

    @Test
    fun ruleListFactoryWithSingleRule() {
        val rule =
            object : Rule<String> {
                override fun execute(
                    context: ValidationContext,
                    value: String,
                ): Unit = Unit
            }

        val ruleList = RuleList(rule)

        assertEquals(1, ruleList.rules.size)
        assertEquals(rule, ruleList.rules.single())
    }

    @Test
    fun ruleListFactoryWithVarargs() {
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

        val ruleList = RuleList(rule1, rule2, rule3)

        assertEquals(3, ruleList.rules.size)
        assertContainsAllWithOrder(listOf(rule1, rule2, rule3), ruleList.rules)
    }

    @Test
    fun ruleListPlusSimpleRule() {
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

        assertEquals(3, combined.rules.size)
        assertEquals(listOf(rule1, rule2, rule3), combined.rules)
    }

    @Test
    fun ruleListPlusRuleList() {
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

        assertEquals(4, combined.rules.size)
        assertEquals(listOf(rule1, rule2, rule3, rule4), combined.rules)
    }

    @Test
    fun ruleListPlusOperatorChaining() {
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

        val ruleList = RuleList(rule1)
        val combined = ruleList + rule2 + rule3

        assertEquals(3, combined.rules.size)
        assertEquals(listOf(rule1, rule2, rule3), combined.rules)
    }

    @Test
    fun ruleListPlusRuleListChaining() {
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

        val ruleList1 = RuleList(rule1)
        val ruleList2 = RuleList(rule2)
        val ruleList3 = RuleList(rule3)
        val combined = ruleList1 + ruleList2 + ruleList3

        assertEquals(3, combined.rules.size)
        assertEquals(listOf(rule1, rule2, rule3), combined.rules)
    }

    @Test
    fun ruleListPlusHandlesNestedRuleLists() {
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

        val nestedRuleList = RuleList(rule2)
        val ruleList = RuleList(rule1)
        val combined = ruleList + nestedRuleList + rule3

        assertEquals(3, combined.rules.size)
    }

    @Test
    fun ruleListWorksWithAnyType() {
        object : Rule<Any?> {
            override fun execute(
                context: ValidationContext,
                value: Any?,
            ): Unit = Unit
        }
    }

    @Test
    fun ruleListPassesValueToAllRules() {
        val capturedValues = mutableListOf<String>()
        val context = ValidationContext { }

        val rule1 =
            object : Rule<String> {
                override fun execute(
                    context: ValidationContext,
                    value: String,
                ) {
                    capturedValues.add(value)
                }
            }

        val rule2 =
            object : Rule<String> {
                override fun execute(
                    context: ValidationContext,
                    value: String,
                ) {
                    capturedValues.add(value)
                }
            }

        val ruleList = RuleList(rule1, rule2)
        val testValue = "test value"

        ruleList.execute(context, testValue)

        assertEquals(listOf(testValue, testValue), capturedValues)
    }

    @Test
    fun ruleListPassesContextToAllRules() {
        val capturedContexts = mutableListOf<ValidationContext>()
        val context = ValidationContext { }

        val rule1 =
            object : Rule<String> {
                override fun execute(
                    context: ValidationContext,
                    value: String,
                ) {
                    capturedContexts.add(context)
                }
            }

        val rule2 =
            object : Rule<String> {
                override fun execute(
                    context: ValidationContext,
                    value: String,
                ) {
                    capturedContexts.add(context)
                }
            }

        val ruleList = RuleList(rule1, rule2)

        ruleList.execute(context, "test")

        assertEquals(2, capturedContexts.size)
        assertTrue(capturedContexts.all { it === context })
    }

    @Test
    fun ruleListWithSingleRuleExecutesOnce() {
        var executionCount = 0
        val context = ValidationContext { }

        val rule =
            object : Rule<String> {
                override fun execute(
                    context: ValidationContext,
                    value: String,
                ) {
                    executionCount++
                }
            }

        val ruleList = RuleList(rule)

        ruleList.execute(context, "test")

        assertEquals(1, executionCount)
    }

    @Test
    fun emptyRuleListFactoryCreatesEmptyList() {
        val ruleList = RuleList<Int>()

        assertTrue(ruleList.rules.isEmpty())
    }

    @Test
    fun ruleListContinuesExecutionAfterViolation() {
        val violations = mutableListOf<Violation>()
        val executionOrder = mutableListOf<Int>()
        val context = ValidationContext { violations.add(it) }

        val rule1 =
            object : Rule<String> {
                override fun execute(
                    context: ValidationContext,
                    value: String,
                ) {
                    executionOrder.add(1)
                    context.onFailure(violation("error1"))
                }
            }

        val rule2 =
            object : Rule<String> {
                override fun execute(
                    context: ValidationContext,
                    value: String,
                ) {
                    executionOrder.add(2)
                    context.onFailure(violation("error2"))
                }
            }

        val ruleList = RuleList(rule1, rule2)

        ruleList.execute(context, "test")

        assertEquals(listOf(1, 2), executionOrder)
        assertEquals(2, violations.size)
    }

    @Test
    fun ruleListWithComplexTypes() {
        data class Person(
            val name: String,
            val age: Int,
        )

        val violations = mutableListOf<Violation>()
        val context = ValidationContext { violations.add(it) }

        val rule =
            object : Rule<Person> {
                override fun execute(
                    context: ValidationContext,
                    value: Person,
                ) {
                    if (value.age < 0) {
                        context.onFailure(violation("negative age"))
                    }
                }
            }

        val ruleList = RuleList(rule)

        ruleList.execute(context, Person("John", -5))

        assertEquals(1, violations.size)
        assertEquals("negative age", violations[0].reason)
    }

    @Test
    fun ruleListPlusPreservesOriginalRuleList() {
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

        val originalRuleList = RuleList(rule1)
        val newRuleList = originalRuleList + rule2

        assertEquals(1, originalRuleList.rules.size)
        assertEquals(2, newRuleList.rules.size)
    }

    @Test
    fun multipleRuleListsCombined() {
        val violations = mutableListOf<Violation>()
        val context = ValidationContext { violations.add(it) }

        val rule1 =
            object : Rule<String> {
                override fun execute(
                    context: ValidationContext,
                    value: String,
                ) {
                    context.onFailure(violation("error1"))
                }
            }

        val rule2 =
            object : Rule<String> {
                override fun execute(
                    context: ValidationContext,
                    value: String,
                ) {
                    context.onFailure(violation("error2"))
                }
            }

        val rule3 =
            object : Rule<String> {
                override fun execute(
                    context: ValidationContext,
                    value: String,
                ) {
                    context.onFailure(violation("error3"))
                }
            }

        val ruleList1 = RuleList(rule1)
        val ruleList2 = RuleList(rule2)
        val ruleList3 = RuleList(rule3)

        val combined = ruleList1 + ruleList2 + ruleList3

        combined.execute(context, "test")

        assertEquals(3, violations.size)
        assertEquals("error1", violations[0].reason)
        assertEquals("error2", violations[1].reason)
        assertEquals("error3", violations[2].reason)
    }
}
