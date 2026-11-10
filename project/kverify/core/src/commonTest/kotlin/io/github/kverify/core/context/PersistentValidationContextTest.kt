package io.github.kverify.core.context

import de.infix.testBalloon.framework.core.testSuite
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertSame
import kotlin.test.assertTrue

val PersistentValidationContextTest by testSuite {
    testSuite("plus") {
        test("adds an element as the newest one") {
            val result =
                PersistentValidationContext(replacing(1)) +
                    keeping(2)

            assertEquals(listOf(1, 2), result.values())
        }

        test("adds the elements of another context in its iteration order") {
            val other =
                PersistentValidationContext(keeping(2)) +
                    summing(3)

            val result =
                PersistentValidationContext(replacing(1)) +
                    other

            assertEquals(listOf(1, 2, 3), result.values())
        }

        test("does not modify the original context") {
            val original = PersistentValidationContext(replacing(1))

            val result = original + keeping(2)

            assertEquals(listOf(1), original.values())
            assertEquals(listOf(1, 2), result.values())
        }

        test("keeps the elements when an empty context is added") {
            val original =
                PersistentValidationContext(replacing(1)) +
                    keeping(2)

            val result = original + EmptyValidationContext

            assertEquals(listOf(1, 2), result.values())
        }
    }

    testSuite("collisions") {
        test("a colliding element replaces the stored one when onCollision returns it") {
            val result =
                PersistentValidationContext(replacing(1)) +
                    replacing(2)

            assertEquals(listOf(2), result.values())
        }

        test("a colliding element is merged by onCollision") {
            val result =
                PersistentValidationContext(summing(1)) +
                    summing(2)

            assertEquals(listOf(3), result.values())
        }

        test("a colliding element is dropped when onCollision returns the stored one") {
            val result =
                PersistentValidationContext(keeping(1)) +
                    keeping(2)

            assertEquals(listOf(1), result.values())
        }

        test("the same context is returned when a colliding element is dropped") {
            val original =
                PersistentValidationContext(replacing(1)) +
                    keeping(2)

            val result = original + keeping(3)

            assertSame(original, result)
        }

        test("a stored element in the middle is kept when onCollision returns it") {
            val stored =
                PersistentValidationContext(replacing(1)) +
                    keeping(2) +
                    summing(3)

            val result = stored + keeping(9)

            assertEquals(listOf(1, 2, 3), result.values())
        }

        test("a collision on the newest element keeps the older elements") {
            val stored =
                PersistentValidationContext(replacing(1)) +
                    summing(2)

            val result = stored + summing(3)

            assertEquals(listOf(1, 5), result.values())
        }

        test("the replacement keeps the position of the stored element") {
            val stored =
                PersistentValidationContext(summing(1)) +
                    replacing(10)

            val result = stored + summing(2)

            assertEquals(listOf(3, 10), result.values())
        }

        test("a collision in the middle keeps the order of the newer elements") {
            val stored =
                PersistentValidationContext(replacing(1)) +
                    summing(2) +
                    keeping(3)

            val result = stored + summing(4)

            assertEquals(listOf(1, 6, 3), result.values())
        }

        test("the elements of another context collide with stored elements one by one") {
            val other =
                PersistentValidationContext(replacing(5)) +
                    summing(2)

            val result =
                PersistentValidationContext(summing(1)) +
                    other

            assertEquals(listOf(3, 5), result.values())
        }

        test("onCollision of the stored element's key decides") {
            val storedKey = SlotKey("slot", "stored key")
            val addedKey = SlotKey("slot", "added key")
            val stored = TestElement("stored element", 1, storedKey)
            val added = TestElement("added element", 2, addedKey)

            val result =
                PersistentValidationContext(stored) +
                    added

            assertEquals(listOf("stored key"), result.names())
        }

        test("a merged collision does not modify the original context") {
            val original =
                PersistentValidationContext(summing(1)) +
                    replacing(2)

            val result = original + summing(5)

            assertEquals(listOf(1, 2), original.values())
            assertEquals(listOf(6, 2), result.values())
        }
    }

    testSuite("get") {
        test("returns the element with the given key") {
            val element = summing(5)
            val context =
                PersistentValidationContext(replacing(1)) +
                    element

            val result = context[SummingKey]

            assertSame(element, result)
        }

        test("returns null when there is no element with the given key") {
            val context =
                PersistentValidationContext(replacing(1)) +
                    summing(2)

            val result = context[KeepingKey]

            assertNull(result)
        }

        test("finds an element by an equal key") {
            val storedKey = SlotKey("slot", "stored key")
            val lookupKey = SlotKey("slot", "lookup key")
            val element = TestElement("slot element", 2, storedKey)
            val context =
                PersistentValidationContext(replacing(1)) +
                    element

            val result = context[lookupKey]

            assertSame(element, result)
        }
    }

    testSuite("minusKey") {
        test("removes the newest element") {
            val context =
                PersistentValidationContext(replacing(1)) +
                    summing(2) +
                    keeping(3)

            val result = context.minusKey(KeepingKey)

            assertEquals(listOf(1, 2), result.values())
        }

        test("removes an element in the middle") {
            val context =
                PersistentValidationContext(replacing(1)) +
                    summing(2) +
                    keeping(3)

            val result = context.minusKey(SummingKey)

            assertEquals(listOf(1, 3), result.values())
        }

        test("removes the oldest element") {
            val context =
                PersistentValidationContext(replacing(1)) +
                    summing(2) +
                    keeping(3)

            val result = context.minusKey(ReplacingKey)

            assertEquals(listOf(2, 3), result.values())
        }

        test("removes an element by an equal key") {
            val storedKey = SlotKey("slot", "stored key")
            val lookupKey = SlotKey("slot", "lookup key")
            val element = TestElement("slot element", 2, storedKey)
            val context =
                PersistentValidationContext(replacing(1)) +
                    element

            val result = context.minusKey(lookupKey)

            assertEquals(listOf(1), result.values())
        }

        test("does not modify the original context") {
            val context =
                PersistentValidationContext(replacing(1)) +
                    summing(2) +
                    keeping(3)

            context.minusKey(SummingKey)

            assertEquals(listOf(1, 2, 3), context.values())
        }

        test("returns the same context when there is no element with the given key") {
            val context =
                PersistentValidationContext(replacing(1)) +
                    summing(2)

            val result = context.minusKey(KeepingKey)

            assertSame(context, result)
        }

        test("returns EmptyValidationContext when the only element is removed") {
            val context = PersistentValidationContext(replacing(1))

            val result = context.minusKey(ReplacingKey)

            assertSame(EmptyValidationContext, result)
        }
    }

    testSuite("iterator") {
        test("returns the elements from oldest to newest") {
            val context =
                PersistentValidationContext(replacing(1)) +
                    summing(2) +
                    keeping(3)

            val result = context.values()

            assertEquals(listOf(1, 2, 3), result)
        }

        test("returns only the element when the context has one element") {
            val context = PersistentValidationContext(replacing(1))

            val result = context.values()

            assertEquals(listOf(1), result)
        }

        test("has no more elements after the newest one") {
            val context =
                PersistentValidationContext(replacing(1)) +
                    summing(2)
            val iterator = context.iterator()

            iterator.next()
            iterator.next()

            assertFalse(iterator.hasNext())
        }

        test("hasNext does not consume the element") {
            val context =
                PersistentValidationContext(replacing(1)) +
                    summing(2)
            val iterator = context.iterator()

            assertTrue(iterator.hasNext())
            assertTrue(iterator.hasNext())
        }

        test("next throws NoSuchElementException when there are no more elements") {
            val context =
                PersistentValidationContext(replacing(1)) +
                    summing(2)
            val iterator = context.iterator()

            iterator.next()
            iterator.next()

            assertFailsWith<NoSuchElementException> { iterator.next() }
        }
    }
}

private class TestElement(
    val name: String,
    val value: Int,
    override val key: ValidationContext.Key<*>,
) : ValidationContext.Element

private object ReplacingKey : ValidationContext.Key<TestElement> {
    override fun onCollision(
        oldElement: ValidationContext.Element,
        newElement: ValidationContext.Element,
    ): ValidationContext.Element = newElement
}

private object KeepingKey : ValidationContext.Key<TestElement> {
    override fun onCollision(
        oldElement: ValidationContext.Element,
        newElement: ValidationContext.Element,
    ): ValidationContext.Element = oldElement
}

private object SummingKey : ValidationContext.Key<TestElement> {
    override fun onCollision(
        oldElement: ValidationContext.Element,
        newElement: ValidationContext.Element,
    ): ValidationContext.Element {
        val old = oldElement as TestElement
        val new = newElement as TestElement

        return TestElement("sum", old.value + new.value, this)
    }
}

private class SlotKey(
    private val id: String,
    private val label: String,
) : ValidationContext.Key<TestElement> {
    override fun onCollision(
        oldElement: ValidationContext.Element,
        newElement: ValidationContext.Element,
    ): ValidationContext.Element = TestElement(label, 0, this)

    override fun equals(other: Any?): Boolean = other is SlotKey && other.id == id

    override fun hashCode(): Int = id.hashCode()
}

private fun replacing(value: Int): TestElement = TestElement("replacing", value, ReplacingKey)

private fun keeping(value: Int): TestElement = TestElement("keeping", value, KeepingKey)

private fun summing(value: Int): TestElement = TestElement("summing", value, SummingKey)

private fun ValidationContext.values(): List<Int> {
    val result = mutableListOf<Int>()

    for (element in this) {
        result.add((element as TestElement).value)
    }

    return result
}

private fun ValidationContext.names(): List<String> {
    val result = mutableListOf<String>()

    for (element in this) {
        result.add((element as TestElement).name)
    }

    return result
}
