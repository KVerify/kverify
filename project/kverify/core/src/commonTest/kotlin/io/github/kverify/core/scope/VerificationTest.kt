package io.github.kverify.core.scope

import io.github.kverify.core.context.IndexPathElement
import io.github.kverify.core.context.validationPath
import io.github.kverify.core.violation.violation
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertSame

class VerificationTest {
    @Test
    fun takeIfNotNull_returnsVerificationWhenNotNull() {
        val expected = "present"

        validateCollecting {
            val verification = verify<String?>(expected)

            val result = verification.takeIfNotNull()

            assertEquals(expected, result?.value)
        }
    }

    @Test
    fun takeIfNotNull_returnsNullWhenNull() {
        validateCollecting {
            val verification = verify<String?>(null)

            assertNull(verification.takeIfNotNull())
        }
    }

    @Test
    fun takeIfNotNull_preservesScope() {
        validateCollecting {
            val verification = verify<String?>("present")

            val result = verification.takeIfNotNull()

            assertSame(verification.scope, result?.scope)
        }
    }

    @Test
    fun each_iteratesAllElements() {
        val source = listOf("x", "y", "z")
        val collected = mutableListOf<String>()

        validateCollecting {
            verify(source).each { element ->
                collected.add(element)
            }
        }

        assertEquals(source, collected)
    }

    @Test
    fun each_withIndex_providesCorrectIndices() {
        val source = listOf("a", "b", "c")
        val collectedIndices = mutableListOf<Int>()

        validateCollecting {
            verify(source).each { idx, _ ->
                collectedIndices.add(idx)
            }
        }

        val expectedIndices = source.indices.toList()
        assertEquals(expectedIndices, collectedIndices)
    }

    @Test
    fun each_addsIndexPathToScope() {
        val source = listOf("first", "second")
        val paths = mutableListOf<IndexPathElement>()

        validateCollecting {
            verify(source).each { _, _ ->
                val indexElement =
                    validationContext
                        .validationPath()
                        .filterIsInstance<IndexPathElement>()
                        .first()
                paths.add(indexElement)
            }
        }

        assertEquals(IndexPathElement(0), paths[0])
        assertEquals(IndexPathElement(1), paths[1])
    }

    @Test
    fun each_collectsViolationsFromBlock() {
        val source = listOf("", "valid", "")
        val expectedViolationCount = source.count { it.isBlank() }

        val result =
            validateCollecting {
                verify(source).each { element ->
                    failIf({ element.isBlank() }) { violation("blank element") }
                }
            }

        assertEquals(expectedViolationCount, result.violations.size)
    }

    @Test
    fun each_emptyList_doesNotIterate() {
        var iterated = false

        validateCollecting {
            verify(emptyList<String>()).each { _ -> iterated = true }
        }

        assertFalse(iterated)
    }

    @Test
    fun each_returnsOriginalVerification() {
        validateCollecting {
            val original = verify(listOf(1, 2, 3))

            val returned = original.each { _ -> }

            assertSame(original, returned)
        }
    }
}
