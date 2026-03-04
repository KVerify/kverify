package io.github.kverify.core.scope

import io.github.kverify.core.context.EmptyValidationContext
import io.github.kverify.core.context.NamePathElement
import io.github.kverify.core.context.validationPath
import io.github.kverify.core.rule.Rule
import io.github.kverify.core.violation.Violation
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertSame
import kotlin.test.assertTrue

class CollectingValidationScopeTest {
    private data class SimpleViolation(
        override val reason: String,
    ) : Violation

    @Test
    fun emptyValidationReturnsNoViolations() {
        val violations = verifyWithCollecting { }
        assertTrue(violations.isEmpty())
    }

    @Test
    fun collectsSingleViolation() {
        val violations =
            verifyWithCollecting {
                onFailure(SimpleViolation("fail"))
            }

        assertEquals(1, violations.size)
        assertEquals("fail", violations[0].reason)
    }

    @Test
    fun collectsMultipleViolations() {
        val violations =
            verifyWithCollecting {
                onFailure(SimpleViolation("first"))
                onFailure(SimpleViolation("second"))
                onFailure(SimpleViolation("third"))
            }

        assertEquals(3, violations.size)
        assertEquals("first", violations[0].reason)
        assertEquals("second", violations[1].reason)
        assertEquals("third", violations[2].reason)
    }

    @Test
    fun returnsImmutableList() {
        val violations =
            verifyWithCollecting {
                onFailure(SimpleViolation("fail"))
            }

        assertIs<List<Violation>>(violations)
    }

    @Test
    fun defaultContextIsEmpty() {
        val scope = CollectingValidationScope()
        assertSame(EmptyValidationContext, scope.validationContext)
    }

    @Test
    fun acceptsCustomContext() {
        val context = NamePathElement("root")
        val scope = CollectingValidationScope(validationContext = context)
        assertEquals(context, scope.validationContext)
    }

    @Test
    fun enforceCollectsViolationFromFailingRule() {
        val violation = SimpleViolation("rule failed")
        val rule =
            object : Rule {
                override fun check(): Violation? = violation
            }

        val violations =
            verifyWithCollecting {
                enforce(rule)
            }

        assertEquals(1, violations.size)
        assertSame(violation, violations[0])
    }

    @Test
    fun enforceSkipsPassingRule() {
        val rule =
            object : Rule {
                override fun check(): Violation? = null
            }

        val violations =
            verifyWithCollecting {
                enforce(rule)
            }

        assertTrue(violations.isEmpty())
    }

    @Test
    fun verifyWithCollectingPassesContextToScope() {
        val context = NamePathElement("root")

        val violations =
            verifyWithCollecting(validationContext = context) {
                val path = validationContext.validationPath()
                assertEquals(1, path.size)
                assertEquals(context, path[0])
            }

        assertTrue(violations.isEmpty())
    }

    @Test
    fun acceptsCustomViolationStorage() {
        val storage = mutableListOf<Violation>()

        verifyWithCollecting(violationStorage = storage) {
            onFailure(SimpleViolation("stored"))
        }

        assertEquals(1, storage.size)
        assertEquals("stored", storage[0].reason)
    }
}
