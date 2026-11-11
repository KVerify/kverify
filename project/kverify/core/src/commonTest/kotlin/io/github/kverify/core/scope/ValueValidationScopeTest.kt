package io.github.kverify.core.scope

import de.infix.testBalloon.framework.core.testSuite
import io.github.kverify.core.context.EmptyValidationContext
import io.github.kverify.core.context.pathName
import io.github.kverify.core.context.validationPath
import io.github.kverify.core.model.IndexPathSegment
import io.github.kverify.core.model.NamePathSegment
import io.github.kverify.core.model.ValidationPath
import io.github.kverify.core.model.ValidationPathSegment
import io.github.kverify.core.util.RecordingStrategy
import io.github.kverify.core.violation.Violation
import io.github.kverify.core.violation.ViolationReason
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertSame

val ValueValidationScopeTest by testSuite {
    testSuite("ValueValidationScope") {
        test("holds the given value") {
            val result = ValueValidationScope(5, RecordingStrategy(), EmptyValidationContext)

            assertEquals(5, result.value)
        }

        test("holds the given strategy") {
            val strategy = RecordingStrategy()

            val result = ValueValidationScope(5, strategy, EmptyValidationContext)

            assertSame(strategy, result.validationStrategy)
        }

        test("holds the given context") {
            val context = pathName("a")

            val result = ValueValidationScope(5, RecordingStrategy(), context)

            assertSame(context, result.validationContext)
        }
    }

    testSuite("plus") {
        test("keeps the value") {
            val scope = ValueValidationScope(5, RecordingStrategy(), EmptyValidationContext)

            val result = scope + pathName("a")

            assertEquals(5, result.value)
        }

        test("keeps the strategy") {
            val strategy = RecordingStrategy()
            val scope = ValueValidationScope(5, strategy, EmptyValidationContext)

            val result = scope + pathName("a")

            assertSame(strategy, result.validationStrategy)
        }

        test("adds the context to the end of the existing context") {
            val context = pathName("a")
            val scope = ValueValidationScope(5, RecordingStrategy(), context)

            val result = scope + pathName("b")

            val expectedPath = path(NamePathSegment("a"), NamePathSegment("b"))
            assertEquals(expectedPath, result.validationContext.validationPath())
        }

        test("does not modify the original scope") {
            val context = pathName("a")
            val scope = ValueValidationScope(5, RecordingStrategy(), context)

            scope + pathName("b")

            val expectedPath = path(NamePathSegment("a"))
            assertEquals(expectedPath, scope.validationContext.validationPath())
        }
    }

    testSuite("forValue") {
        test("replaces the value") {
            val scope = ValueValidationScope(5, RecordingStrategy(), EmptyValidationContext)

            val result = scope.forValue("text")

            assertEquals("text", result.value)
        }

        test("keeps the strategy") {
            val strategy = RecordingStrategy()
            val scope = ValueValidationScope(5, strategy, EmptyValidationContext)

            val result = scope.forValue("text")

            assertSame(strategy, result.validationStrategy)
        }

        test("keeps the context") {
            val context = pathName("a")
            val scope = ValueValidationScope(5, RecordingStrategy(), context)

            val result = scope.forValue("text")

            assertSame(context, result.validationContext)
        }
    }

    testSuite("takeIfValueNotNull") {
        test("returns the same scope when the value is not null") {
            val scope = ValueValidationScope<String?>("a", RecordingStrategy(), EmptyValidationContext)

            val result = scope.takeIfValueNotNull()

            assertSame(scope, result!!)
        }

        test("returns null when the value is null") {
            val scope = ValueValidationScope<String?>(null, RecordingStrategy(), EmptyValidationContext)

            val result = scope.takeIfValueNotNull()

            assertNull(result)
        }
    }

    testSuite("each") {
        test("calls the block for every element in order") {
            val scope = ValueValidationScope(listOf("a", "b", "c"), RecordingStrategy(), EmptyValidationContext)
            val values = mutableListOf<String>()

            scope.each { values.add(value) }

            assertEquals(listOf("a", "b", "c"), values)
        }

        test("does not call the block for an empty iterable") {
            val scope = ValueValidationScope(emptyList<String>(), RecordingStrategy(), EmptyValidationContext)
            var calls = 0

            scope.each { calls++ }

            assertEquals(0, calls)
        }

        test("adds the index of the element to the path") {
            val scope = ValueValidationScope(listOf("a", "b"), RecordingStrategy(), EmptyValidationContext)
            val paths = mutableListOf<ValidationPath>()

            scope.each { paths.add(validationContext.validationPath()) }

            val firstPath = path(IndexPathSegment(0))
            val secondPath = path(IndexPathSegment(1))
            assertEquals(listOf(firstPath, secondPath), paths)
        }

        test("adds the index of the element to the end of the existing path") {
            val context = pathName("items")
            val scope = ValueValidationScope(listOf(10, 20), RecordingStrategy(), context)
            val paths = mutableListOf<ValidationPath>()

            scope.each { paths.add(validationContext.validationPath()) }

            val firstPath = path(NamePathSegment("items"), IndexPathSegment(0))
            val secondPath = path(NamePathSegment("items"), IndexPathSegment(1))
            assertEquals(listOf(firstPath, secondPath), paths)
        }

        test("keeps the strategy of the scope") {
            val strategy = RecordingStrategy()
            val scope = ValueValidationScope(listOf("a", "b"), strategy, EmptyValidationContext)

            scope.each { failWith(ViolationReason(value)) }

            val expectedViolations = listOf<Violation>(ViolationReason("a"), ViolationReason("b"))
            assertEquals(expectedViolations, strategy.violations)
        }
    }
}

private fun path(vararg segments: ValidationPathSegment): ValidationPath = ValidationPath(segments.asList())
