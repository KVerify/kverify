package io.github.kverify.core.scope

import de.infix.testBalloon.framework.core.testSuite
import io.github.kverify.core.context.EmptyValidationContext
import io.github.kverify.core.context.pathName
import io.github.kverify.core.context.validationPath
import io.github.kverify.core.model.NamePathSegment
import io.github.kverify.core.model.ValidationPath
import io.github.kverify.core.model.ValidationPathSegment
import io.github.kverify.core.util.RecordingStrategy
import io.github.kverify.core.violation.Violation
import io.github.kverify.core.violation.ViolationReason
import kotlin.test.assertEquals
import kotlin.test.assertSame
import kotlin.test.assertTrue

val ValidationScopeTest by testSuite {
    testSuite("ValidationScope") {
        test("holds the given strategy") {
            val strategy = RecordingStrategy()

            val result = ValidationScope(strategy, EmptyValidationContext)

            assertSame(strategy, result.validationStrategy)
        }

        test("holds the given context") {
            val context = pathName("a")

            val result = ValidationScope(RecordingStrategy(), context)

            assertSame(context, result.validationContext)
        }
    }

    testSuite("plus") {
        test("keeps the strategy") {
            val strategy = RecordingStrategy()
            val scope = ValidationScope(strategy, EmptyValidationContext)

            val result = scope + pathName("b")

            assertSame(strategy, result.validationStrategy)
        }

        test("adds the context to the end of the existing context") {
            val context = pathName("a")
            val scope = ValidationScope(RecordingStrategy(), context)

            val result = scope + pathName("b")

            val expectedPath = path(NamePathSegment("a"), NamePathSegment("b"))
            assertEquals(expectedPath, result.validationContext.validationPath())
        }

        test("does not modify the original scope") {
            val context = pathName("a")
            val scope = ValidationScope(RecordingStrategy(), context)

            scope + pathName("b")

            val expectedPath = path(NamePathSegment("a"))
            assertEquals(expectedPath, scope.validationContext.validationPath())
        }
    }

    testSuite("forValue") {
        test("holds the given value") {
            val scope = ValidationScope(RecordingStrategy(), EmptyValidationContext)

            val result = scope.forValue(5)

            assertEquals(5, result.value)
        }

        test("keeps the strategy") {
            val strategy = RecordingStrategy()
            val scope = ValidationScope(strategy, EmptyValidationContext)

            val result = scope.forValue(5)

            assertSame(strategy, result.validationStrategy)
        }

        test("keeps the context") {
            val context = pathName("a")
            val scope = ValidationScope(RecordingStrategy(), context)

            val result = scope.forValue(5)

            assertSame(context, result.validationContext)
        }
    }

    testSuite("forProperty") {
        test("holds the value of the property") {
            val person = Person("Alice")
            val scope = ValidationScope(RecordingStrategy(), EmptyValidationContext)

            val result = scope.forProperty(person::name)

            assertEquals("Alice", result.value)
        }

        test("adds the name of the property to the path") {
            val person = Person("Alice")
            val scope = ValidationScope(RecordingStrategy(), EmptyValidationContext)

            val result = scope.forProperty(person::name)

            val expectedPath = path(NamePathSegment("name"))
            assertEquals(expectedPath, result.validationContext.validationPath())
        }

        test("adds the name of the property to the end of the existing path") {
            val person = Person("Alice")
            val context = pathName("user")
            val scope = ValidationScope(RecordingStrategy(), context)

            val result = scope.forProperty(person::name)

            val expectedPath = path(NamePathSegment("user"), NamePathSegment("name"))
            assertEquals(expectedPath, result.validationContext.validationPath())
        }
    }

    testSuite("failIf") {
        test("passes the violation to the strategy when the condition is true") {
            val strategy = RecordingStrategy()
            val scope = ValidationScope(strategy, EmptyValidationContext)
            val violation = ViolationReason("a")

            scope.failIf(true) { violation }

            assertEquals(listOf<Violation>(violation), strategy.violations)
        }

        test("creates the violation once when the condition is true") {
            val scope = ValidationScope(RecordingStrategy(), EmptyValidationContext)
            var created = 0

            scope.failIf(true) {
                created++
                ViolationReason("a")
            }

            assertEquals(1, created)
        }

        test("does not create the violation when the condition is false") {
            val scope = ValidationScope(RecordingStrategy(), EmptyValidationContext)
            var created = 0

            scope.failIf(false) {
                created++
                ViolationReason("a")
            }

            assertEquals(0, created)
        }

        test("does not pass a violation to the strategy when the condition is false") {
            val strategy = RecordingStrategy()
            val scope = ValidationScope(strategy, EmptyValidationContext)

            scope.failIf(false) { ViolationReason("a") }

            assertTrue(strategy.violations.isEmpty())
        }
    }

    testSuite("failWith") {
        test("passes the violation to the strategy") {
            val strategy = RecordingStrategy()
            val scope = ValidationScope(strategy, EmptyValidationContext)
            val violation = ViolationReason("a")

            scope.failWith(violation)

            assertEquals(listOf<Violation>(violation), strategy.violations)
        }
    }
}

private class Person(
    val name: String,
)

private fun path(vararg segments: ValidationPathSegment): ValidationPath = ValidationPath(segments.asList())
