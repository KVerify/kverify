package io.github.kverify.core.context

import de.infix.testBalloon.framework.core.testSuite
import io.github.kverify.core.model.NamePathSegment
import io.github.kverify.core.model.ValidationPath
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertIs
import kotlin.test.assertNull
import kotlin.test.assertSame
import kotlin.test.assertTrue

val ValidationContextTest by testSuite {
    testSuite("validationPath") {
        test("returns an empty path when the context has no path element") {
            val result = EmptyValidationContext.validationPath()

            assertEquals(ValidationPath.Empty, result)
        }

        test("returns an empty path when the context has other elements only") {
            val context = FirstElement()

            val result = context.validationPath()

            assertEquals(ValidationPath.Empty, result)
        }

        test("returns the path of the path element") {
            val context =
                FirstElement() +
                    pathName("a") +
                    SecondElement()

            val result = context.validationPath()

            val expectedPath = ValidationPath(listOf(NamePathSegment("a")))
            assertEquals(expectedPath, result)
        }
    }
}

val ValidationContextElementTest by testSuite {
    testSuite("get") {
        test("returns the element for its own key") {
            val element = FirstElement()

            val result = element[FirstKey]

            assertSame(element, result)
        }

        test("returns null for another key") {
            val element = FirstElement()

            val result = element[SecondKey]

            assertNull(result)
        }
    }

    testSuite("minusKey") {
        test("with its own key returns EmptyValidationContext") {
            val element = FirstElement()

            val result = element.minusKey(FirstKey)

            assertSame(EmptyValidationContext, result)
        }

        test("with another key returns the element itself") {
            val element = FirstElement()

            val result = element.minusKey(SecondKey)

            assertSame(element, result)
        }
    }

    testSuite("iterator") {
        test("returns only the element") {
            val element = FirstElement()

            val result = elementsOf(element)

            assertEquals(listOf(element), result)
        }

        test("hasNext does not consume the element") {
            val element = FirstElement()
            val iterator = element.iterator()

            assertTrue(iterator.hasNext())
            assertTrue(iterator.hasNext())
        }

        test("has no more elements after next") {
            val element = FirstElement()
            val iterator = element.iterator()

            iterator.next()

            assertFalse(iterator.hasNext())
        }

        test("next throws NoSuchElementException when there are no more elements") {
            val element = FirstElement()
            val iterator = element.iterator()

            iterator.next()

            assertFailsWith<NoSuchElementException> { iterator.next() }
        }
    }

    testSuite("plus") {
        test("returns a persistent context with both elements in order") {
            val first = FirstElement()
            val second = SecondElement()

            val result = first + second

            assertIs<PersistentValidationContext>(result)
            assertEquals(listOf(first, second), elementsOf(result))
        }

        test("returns a persistent context with only the element when an empty context is added") {
            val element = FirstElement()

            val result = element + EmptyValidationContext

            assertIs<PersistentValidationContext>(result)
            assertEquals(listOf(element), elementsOf(result))
        }

        test("does not modify the element") {
            val first = FirstElement()
            val second = SecondElement()

            first + second

            assertEquals(listOf(first), elementsOf(first))
        }
    }
}

private class FirstElement : ValidationContext.Element {
    override val key: ValidationContext.Key<*> = FirstKey
}

private object FirstKey : ValidationContext.Key<FirstElement> {
    override fun onCollision(
        oldElement: ValidationContext.Element,
        newElement: ValidationContext.Element,
    ): ValidationContext.Element = newElement
}

private class SecondElement : ValidationContext.Element {
    override val key: ValidationContext.Key<*> = SecondKey
}

private object SecondKey : ValidationContext.Key<SecondElement> {
    override fun onCollision(
        oldElement: ValidationContext.Element,
        newElement: ValidationContext.Element,
    ): ValidationContext.Element = newElement
}

private fun elementsOf(context: ValidationContext): List<ValidationContext.Element> {
    val result = mutableListOf<ValidationContext.Element>()

    for (element in context) {
        result.add(element)
    }

    return result
}
