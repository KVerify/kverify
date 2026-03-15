package io.github.kverify.core.scope

import io.github.kverify.core.context.EmptyValidationContext
import io.github.kverify.core.context.NamePathElement
import io.github.kverify.core.context.ValidationContext
import io.github.kverify.core.violation.Violation
import io.github.kverify.core.violation.violation
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class CollectingValidationScopeTest {
    private fun scopeWithStorage(): Pair<MutableList<Violation>, CollectingValidationScope> {
        val storage = mutableListOf<Violation>()
        return storage to CollectingValidationScope(storage, EmptyValidationContext)
    }

    @Test
    fun enforceDoesNotAddViolationWhenRulePasses() {
        val (storage, scope) = scopeWithStorage()

        scope.enforce { null }

        assertTrue(storage.isEmpty())
    }

    @Test
    fun enforceCollectsAllViolationsFromMultipleFailingRules() {
        val (storage, scope) = scopeWithStorage()
        val v1 = violation("first")
        val v2 = violation("second")
        val v3 = violation("third")

        scope.enforce { v1 }
        scope.enforce { v2 }
        scope.enforce { v3 }

        assertEquals(listOf(v1, v2, v3), storage)
    }

    @Test
    fun providedValidationContextIsStored() {
        val storage = mutableListOf<Violation>()
        val context = NamePathElement("field")

        val scope = CollectingValidationScope(storage, context)

        assertEquals(listOf(context), scope.validationContext.toList())
    }

    @Test
    fun validateCollectingReturnsValidResultWhenNoRulesFail() {
        val result =
            validateCollecting {
                enforce { null }
                enforce { null }
            }

        assertTrue(result.isValid)
    }

    @Test
    fun validateCollectingCollectsAllViolations() {
        val v1 = violation("first")
        val v2 = violation("second")
        val v3 = violation("third")

        val result =
            validateCollecting {
                enforce { v1 }
                enforce { null }
                enforce { v2 }
                enforce { v3 }
            }

        assertEquals(listOf(v1, v2, v3), result.violations)
    }

    @Test
    fun validateCollectingWithEmptyBlockReturnsValidResult() {
        val result = validateCollecting {}

        assertTrue(result.isValid)
    }

    @Test
    fun validateCollectingPassesContextToScope() {
        val context = NamePathElement("root")
        var capturedContext: ValidationContext? = null

        validateCollecting(context) {
            capturedContext = validationContext
        }

        assertEquals(listOf(context), capturedContext?.toList())
    }

    @Test
    fun validateCollectingDefaultContextIsEmpty() {
        var capturedContext: ValidationContext? = null

        validateCollecting {
            capturedContext = validationContext
        }

        assertEquals(EmptyValidationContext, capturedContext)
    }

    @Test
    fun validateCollectingEvaluatesAllRulesUnconditionally() {
        var secondRuleEvaluated = false

        validateCollecting {
            enforce { violation("first") }
            enforce {
                secondRuleEvaluated = true
                null
            }
        }

        assertTrue(secondRuleEvaluated)
    }

    @Test
    fun worksWithDequeAsStorage() {
        val storage = ArrayDeque<Violation>()
        val scope = CollectingValidationScope(storage, EmptyValidationContext)
        val v = violation("reason")

        scope.enforce { v }

        assertEquals(listOf(v), storage.toList())
    }
}
