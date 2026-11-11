package io.github.kverify.core.context

import de.infix.testBalloon.framework.core.testSuite
import io.github.kverify.core.model.IndexPathSegment
import io.github.kverify.core.model.NamePathSegment
import io.github.kverify.core.model.ValidationPath
import io.github.kverify.core.model.ValidationPathSegment
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertNotEquals
import kotlin.test.assertSame

val ValidationPathElementTest by testSuite {
    testSuite("pathName") {
        test("creates a path element with one name segment") {
            val result = pathName("a")

            val expectedPath = path(NamePathSegment("a"))
            assertEquals(expectedPath, result.toValidationPath())
        }
    }

    testSuite("pathIndex") {
        test("creates a path element with one index segment") {
            val result = pathIndex(0)

            val expectedPath = path(IndexPathSegment(0))
            assertEquals(expectedPath, result.toValidationPath())
        }
    }

    testSuite("toValidationPath") {
        test("returns the segments from first to last") {
            val combined =
                pathName("a") +
                    pathIndex(1) +
                    pathName("c")
            val element = assertIs<ValidationPathElement>(combined)

            val result = element.toValidationPath()

            val expectedPath = path(NamePathSegment("a"), IndexPathSegment(1), NamePathSegment("c"))
            assertEquals(expectedPath, result)
        }
    }

    testSuite("key") {
        test("is the Key companion of ValidationPathElement") {
            val element = pathName("a")

            val result = element.key

            assertSame(ValidationPathElement, result)
        }
    }

    testSuite("plus") {
        test("adds the segments of another path element to the end of the path") {
            val result =
                pathName("a") +
                    pathName("b")

            val expectedPath = path(NamePathSegment("a"), NamePathSegment("b"))
            assertEquals(expectedPath, result.validationPath())
        }

        test("keeps the order of the segments of a longer path element") {
            val longer =
                pathName("b") +
                    pathIndex(0)

            val result =
                pathName("a") +
                    longer

            val expectedPath = path(NamePathSegment("a"), NamePathSegment("b"), IndexPathSegment(0))
            assertEquals(expectedPath, result.validationPath())
        }

        test("returns a path element when a path element is added") {
            val result =
                pathName("a") +
                    pathName("b")

            assertIs<ValidationPathElement>(result)
        }

        test("does not modify either path element") {
            val first = pathName("a")
            val second = pathName("b")

            first + second

            val expectedFirstPath = path(NamePathSegment("a"))
            val expectedSecondPath = path(NamePathSegment("b"))
            assertEquals(expectedFirstPath, first.toValidationPath())
            assertEquals(expectedSecondPath, second.toValidationPath())
        }

        test("returns a persistent context with this element first when another element is added") {
            val first = pathName("a")
            val other = UnrelatedElement()

            val result = first + other

            assertIs<PersistentValidationContext>(result)
            assertEquals(listOf(first, other), elementsOf(result))
        }

        test("merges the path element of another context into the path") {
            val otherContext =
                UnrelatedElement() +
                    pathName("b")

            val result =
                pathName("a") +
                    otherContext

            val expectedPath = path(NamePathSegment("a"), NamePathSegment("b"))
            assertEquals(expectedPath, result.validationPath())
            assertEquals(2, elementsOf(result).size)
        }
    }

    testSuite("onCollision") {
        test("returns a path element with the segments of the new element after the old one") {
            val old = pathName("a")
            val new = pathName("b")

            val result = ValidationPathElement.onCollision(old, new)

            val element = assertIs<ValidationPathElement>(result)
            val expectedPath = path(NamePathSegment("a"), NamePathSegment("b"))
            assertEquals(expectedPath, element.toValidationPath())
        }

        test("returns the new element when the old one is not a path element") {
            val old = UnrelatedElement()
            val new = pathName("a")

            val result = ValidationPathElement.onCollision(old, new)

            assertSame(new, result)
        }

        test("returns the old element when the new one is not a path element") {
            val old = pathName("a")
            val new = UnrelatedElement()

            val result = ValidationPathElement.onCollision(old, new)

            assertSame(old, result)
        }

        test("returns the new element when neither is a path element") {
            val old = UnrelatedElement()
            val new = UnrelatedElement()

            val result = ValidationPathElement.onCollision(old, new)

            assertSame(new, result)
        }

        test("concatenates the path elements added to a persistent context") {
            val stored =
                PersistentValidationContext(pathName("a")) +
                    pathName("b")

            val result = stored + pathIndex(0)

            val expectedPath = path(NamePathSegment("a"), NamePathSegment("b"), IndexPathSegment(0))
            assertEquals(expectedPath, result.validationPath())
        }
    }

    testSuite("plusPathName") {
        test("adds a name segment to an empty context") {
            val result = EmptyValidationContext.plusPathName("a")

            val expectedPath = path(NamePathSegment("a"))
            assertEquals(expectedPath, result.validationPath())
        }

        test("adds a name segment to the end of the path") {
            val context = pathName("a")

            val result = context.plusPathName("b")

            val expectedPath = path(NamePathSegment("a"), NamePathSegment("b"))
            assertEquals(expectedPath, result.validationPath())
        }

        test("keeps the other elements of the context") {
            val element = UnrelatedElement()

            val result = element.plusPathName("a")

            val expectedPath = path(NamePathSegment("a"))
            assertEquals(expectedPath, result.validationPath())
            assertSame(element, result[UnrelatedKey])
        }
    }

    testSuite("plusPathIndex") {
        test("adds an index segment to an empty context") {
            val result = EmptyValidationContext.plusPathIndex(2)

            val expectedPath = path(IndexPathSegment(2))
            assertEquals(expectedPath, result.validationPath())
        }

        test("adds an index segment to the end of the path") {
            val context = pathName("a")

            val result = context.plusPathIndex(2)

            val expectedPath = path(NamePathSegment("a"), IndexPathSegment(2))
            assertEquals(expectedPath, result.validationPath())
        }

        test("keeps the other elements of the context") {
            val element = UnrelatedElement()

            val result = element.plusPathIndex(2)

            val expectedPath = path(IndexPathSegment(2))
            assertEquals(expectedPath, result.validationPath())
            assertSame(element, result[UnrelatedKey])
        }
    }

    testSuite("equals") {
        test("path elements with equal segments are equal") {
            val left =
                pathName("a") +
                    pathIndex(1)
            val right =
                pathName("a") +
                    pathIndex(1)

            assertEquals(left, right)
        }

        test("path elements built in different ways are equal") {
            val firstTwo =
                pathName("a") +
                    pathName("b")
            val left = firstTwo + pathName("c")
            val lastTwo =
                pathName("b") +
                    pathName("c")
            val right =
                pathName("a") +
                    lastTwo

            assertEquals(left, right)
        }

        test("path elements that share a parent are equal when their last segments are equal") {
            val base =
                pathName("a") +
                    pathName("b")
            val left = base + pathName("c")
            val right = base + pathName("c")

            assertEquals(left, right)
        }

        test("a path element is equal to itself") {
            val element = pathName("a")

            assertEquals(element, element)
        }

        test("path elements with different last segments are not equal") {
            val left =
                pathName("a") +
                    pathName("b")
            val right =
                pathName("a") +
                    pathName("c")

            assertNotEquals(left, right)
        }

        test("path elements with different first segments are not equal") {
            val left =
                pathName("a") +
                    pathName("c")
            val right =
                pathName("b") +
                    pathName("c")

            assertNotEquals(left, right)
        }

        test("path elements with different lengths are not equal") {
            val longer =
                pathName("a") +
                    pathName("b")
            val shorter = pathName("a")

            assertNotEquals(longer, shorter)
        }

        test("a name segment and an index segment with the same text are not equal") {
            val name = pathName("0")
            val index = pathIndex(0)

            assertNotEquals(name, index)
        }

        test("a path element is not equal to an object of another type") {
            val element = pathName("a")

            assertNotEquals<Any>(element, "a")
        }
    }

    testSuite("hashCode") {
        test("path elements with equal segments have equal hash codes") {
            val left =
                pathName("a") +
                    pathIndex(1)
            val right =
                pathName("a") +
                    pathIndex(1)

            assertEquals(left.hashCode(), right.hashCode())
        }

        test("path elements built in different ways have equal hash codes") {
            val firstTwo =
                pathName("a") +
                    pathName("b")
            val left = firstTwo + pathName("c")
            val lastTwo =
                pathName("b") +
                    pathName("c")
            val right =
                pathName("a") +
                    lastTwo

            assertEquals(left.hashCode(), right.hashCode())
        }
    }
}

private class UnrelatedElement : ValidationContext.Element {
    override val key: ValidationContext.Key<*> = UnrelatedKey
}

private object UnrelatedKey : ValidationContext.Key<UnrelatedElement> {
    override fun onCollision(
        oldElement: ValidationContext.Element,
        newElement: ValidationContext.Element,
    ): ValidationContext.Element = newElement
}

private fun path(vararg segments: ValidationPathSegment): ValidationPath = ValidationPath(segments.asList())

private fun elementsOf(context: ValidationContext): List<ValidationContext.Element> {
    val result = mutableListOf<ValidationContext.Element>()

    for (element in context) {
        result.add(element)
    }

    return result
}
