package io.github.kverify.core.rule

import io.github.kverify.core.context.ValidationContext
import io.github.kverify.core.context.failIf
import io.github.kverify.core.context.failIfNot
import io.github.kverify.core.violation.Violation
import io.github.kverify.core.violation.violation
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertTrue

class ScopedRuleTest {
    @Test
    fun scopedRuleExecuteScopedIsCalledWithValueAndContext() {
        var capturedContext: ValidationContext? = null
        var capturedValue: String? = null

        val rule =
            object : ScopedRule<String> {
                override fun ValidationContext.executeScoped(value: String) {
                    capturedContext = this
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
    fun scopedRuleExecuteDelegatesToExecuteScoped() {
        val violations = mutableListOf<Violation>()
        val context = ValidationContext { violations.add(it) }
        val violation = violation("error")

        val rule =
            object : ScopedRule<String> {
                override fun ValidationContext.executeScoped(value: String) {
                    onFailure(violation)
                }
            }

        rule.execute(context, "test")

        assertEquals(violation, violations.single())
    }

    @Test
    fun scopedRuleHasAccessToContextMethods() {
        val violations = mutableListOf<Violation>()
        val context = ValidationContext { violations.add(it) }

        val rule =
            object : ScopedRule<String> {
                override fun ValidationContext.executeScoped(value: String) {
                    failIf(value.isEmpty()) { violation("empty") }
                    failIfNot(value.length > 3) { violation("too short") }
                }
            }

        rule.execute(context, "ab")

        assertEquals(1, violations.size)
        assertEquals("too short", violations[0].reason)
    }

    @Test
    fun ruleFactoryFunctionCreatesRule() {
        var executed = false

        @Suppress("RemoveExplicitTypeArguments")
        val rule =
            Rule<String> { _: String ->
                executed = true
            }

        val context = ValidationContext { }
        rule.execute(context, "test")

        assertTrue(executed)
        assertIs<ScopedRule<String>>(rule)
    }

    @Test
    fun ruleFactoryFunctionWithViolation() {
        val violations = mutableListOf<Violation>()
        val context = ValidationContext { violations.add(it) }

        val rule =
            Rule<String> { value ->
                if (value.isEmpty()) {
                    onFailure(violation("empty"))
                }
            }

        rule.execute(context, "")

        assertEquals(1, violations.size)
        assertEquals("empty", violations[0].reason)
    }

    @Test
    fun ruleFactoryFunctionHasContextScope() {
        val violations = mutableListOf<Violation>()
        val context = ValidationContext { violations.add(it) }

        val rule =
            Rule<String> { value ->
                failIf(value.length < 5) { violation("too short") }
            }

        rule.execute(context, "test")

        assertEquals(1, violations.size)
    }

    @Test
    fun asScopedConvertsRuleToScopedRule() {
        val originalRule =
            object : Rule<String> {
                override fun execute(
                    context: ValidationContext,
                    value: String,
                ) {
                    context.onFailure(violation("error"))
                }
            }

        val scopedRule = originalRule.asScoped()

        assertIs<ScopedRule<String>>(scopedRule)
    }

    @Test
    fun asScopedPreservesRuleBehavior() {
        val violations = mutableListOf<Violation>()
        val context = ValidationContext { violations.add(it) }

        val originalRule =
            object : Rule<String> {
                override fun execute(
                    context: ValidationContext,
                    value: String,
                ) {
                    context.onFailure(violation("error"))
                }
            }

        val scopedRule = originalRule.asScoped()

        scopedRule.execute(context, "test")

        assertEquals(1, violations.size)
        assertEquals("error", violations[0].reason)
    }

    @Test
    fun scopedRuleWorksWithAnyType() {
        val rule =
            object : ScopedRule<Any?> {
                override fun ValidationContext.executeScoped(value: Any?) {
                    if (value == null) {
                        onFailure(violation("null"))
                    }
                }
            }

        val violations = mutableListOf<Violation>()
        val context = ValidationContext { violations.add(it) }

        rule.execute(context, null)

        assertEquals(1, violations.size)
        assertEquals("null", violations[0].reason)
    }

    @Test
    fun scopedRuleWithComplexType() {
        val violations = mutableListOf<Violation>()
        val context = ValidationContext { violations.add(it) }

        val rule =
            object : ScopedRule<List<String>> {
                override fun ValidationContext.executeScoped(value: List<String>) {
                    if (value.isEmpty()) {
                        onFailure(violation("empty list"))
                    }
                    if (value.any { it.isEmpty() }) {
                        onFailure(violation("contains empty string"))
                    }
                }
            }

        rule.execute(context, listOf(""))

        assertEquals(1, violations.size)
        assertEquals("contains empty string", violations[0].reason)
    }

    @Test
    fun ruleFactoryWorksWithComplexLogic() {
        val violations = mutableListOf<Violation>()
        val context = ValidationContext { violations.add(it) }

        val rule =
            Rule<Int> { value ->
                when {
                    value < 0 -> onFailure(violation("negative"))
                    value == 0 -> onFailure(violation("zero"))
                    value > 100 -> onFailure(violation("too large"))
                }
            }

        rule.execute(context, 150)

        assertEquals(1, violations.size)
        assertEquals("too large", violations[0].reason)
    }

    @Test
    fun scopedRuleCanBeUsedWithRuleOperators() {
        val violations = mutableListOf<Violation>()
        val context = ValidationContext { violations.add(it) }

        val rule1 =
            Rule<String> { value ->
                if (value.isEmpty()) onFailure(violation("empty"))
            }

        val rule2 =
            Rule<String> { value ->
                if (value.length < 3) onFailure(violation("too short"))
            }

        val combined = rule1 + rule2

        combined.execute(context, "ab")

        assertEquals(1, violations.size)
        assertEquals("too short", violations[0].reason)
    }

    @Test
    fun asScopedCanBeCalledMultipleTimes() {
        val originalRule =
            object : Rule<String> {
                override fun execute(
                    context: ValidationContext,
                    value: String,
                ) {
                    context.onFailure(violation("error"))
                }
            }

        val scoped1 = originalRule.asScoped()
        val scoped2 = originalRule.asScoped()

        val violations1 = mutableListOf<Violation>()
        val violations2 = mutableListOf<Violation>()
        val context1 = ValidationContext { violations1.add(it) }
        val context2 = ValidationContext { violations2.add(it) }

        scoped1.execute(context1, "test")
        scoped2.execute(context2, "test")

        assertEquals(1, violations1.size)
        assertEquals(1, violations2.size)
    }

    @Test
    fun scopedRuleAdapterIsValueClass() {
        val originalRule =
            object : Rule<String> {
                override fun execute(
                    context: ValidationContext,
                    value: String,
                ): Unit = Unit
            }

        val scoped = originalRule.asScoped()

        assertIs<ScopedRule<String>>(scoped)
    }

    @Test
    fun ruleFactoryWithReceiverScope() {
        val violations = mutableListOf<Violation>()
        val context = ValidationContext { violations.add(it) }

        val rule =
            Rule<String> { value ->
                this.onFailure(violation("error from $value"))
            }

        rule.execute(context, "test")

        assertEquals(1, violations.size)
        assertEquals("error from test", violations[0].reason)
    }

    @Test
    fun scopedRuleWithDataClass() {
        data class Person(
            val name: String,
            val age: Int,
        )

        val violations = mutableListOf<Violation>()
        val context = ValidationContext { violations.add(it) }

        val rule =
            Rule<Person> { person ->
                if (person.name.isEmpty()) {
                    onFailure(violation("name is empty"))
                }
                if (person.age < 0) {
                    onFailure(violation("age is negative"))
                }
            }

        rule.execute(context, Person("", -1))

        assertEquals(2, violations.size)
        assertEquals("name is empty", violations[0].reason)
        assertEquals("age is negative", violations[1].reason)
    }

    @Test
    fun multipleScopedRulesExecuteIndependently() {
        val violations1 = mutableListOf<Violation>()
        val violations2 = mutableListOf<Violation>()
        val context1 = ValidationContext { violations1.add(it) }
        val context2 = ValidationContext { violations2.add(it) }

        val rule =
            Rule<String> { value ->
                if (value == "fail") onFailure(violation("failed"))
            }

        rule.execute(context1, "fail")
        rule.execute(context2, "pass")

        assertEquals(1, violations1.size)
        assertEquals(0, violations2.size)
    }
}

@Suppress("UnusedPrivateClass", "Unused")
private class ScopedRuleCanBeImplemented : ScopedRule<String> {
    override fun ValidationContext.executeScoped(value: String): Unit = Unit
}
