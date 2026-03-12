package io.github.kverify.core.scope

import io.github.kverify.core.context.IndexPathElement
import io.github.kverify.core.context.NamePathElement
import io.github.kverify.core.context.validationPath
import io.github.kverify.core.violation.violation
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertSame
import kotlin.test.assertTrue

class ValidationScopeTest {
    @Test
    fun verify_value_setsValue() {
        val expected = "test value"

        validateCollecting {
            val verification = verify(expected)

            assertEquals(expected, verification.value)
        }
    }

    @Test
    fun verify_value_setsScope() {
        validateCollecting {
            val verification = verify("anything")

            assertSame(this, verification.scope)
        }
    }

    @Test
    fun verify_property_setsValue() {
        data class Dto(
            val age: Int,
        )

        val expected = 25
        val dto = Dto(expected)

        validateCollecting {
            val verification = verify(dto::age)

            assertEquals(expected, verification.value)
        }
    }

    @Test
    fun verify_property_addsPropertyNameToPath() {
        data class Dto(
            val name: String,
        )

        val dto = Dto("Alice")

        validateCollecting {
            val verification = verify(dto::name)
            val path = verification.scope.validationContext.validationPath()

            assertEquals(NamePathElement("name"), path.single())
        }
    }

    @Test
    fun plus_appendsContext() {
        val element = NamePathElement("field")

        validateCollecting {
            val extended = this + element
            val path = extended.validationContext.validationPath()

            assertEquals(element, path.single())
        }
    }

    @Test
    fun plus_preservesOrder() {
        val first = NamePathElement("a")
        val second = NamePathElement("b")

        validateCollecting {
            val extended = this + first + second
            val path = extended.validationContext.validationPath()

            assertEquals(listOf(first, second), path)
        }
    }

    @Test
    fun pathName_addsToContext() {
        val name = "user"

        validateCollecting {
            pathName(name) {
                val path = validationContext.validationPath()

                assertEquals(NamePathElement(name), path.single())
            }
        }
    }

    @Test
    fun pathName_doesNotAffectOuterScope() {
        validateCollecting {
            pathName("inner") {}

            val outerPath = validationContext.validationPath()

            assertEquals(emptyList(), outerPath)
        }
    }

    @Test
    fun pathName_nested_buildsCorrectPath() {
        val outer = "user"
        val inner = "address"

        validateCollecting {
            pathName(outer) {
                pathName(inner) {
                    val path = validationContext.validationPath()

                    assertEquals(
                        listOf(NamePathElement(outer), NamePathElement(inner)),
                        path,
                    )
                }
            }
        }
    }

    @Test
    fun pathIndex_addsToContext() {
        val index = 0

        validateCollecting {
            pathIndex(index) {
                val path = validationContext.validationPath()

                assertEquals(IndexPathElement(index), path.single())
            }
        }
    }

    @Test
    fun pathIndex_doesNotAffectOuterScope() {
        validateCollecting {
            pathIndex(3) {}

            val outerPath = validationContext.validationPath()

            assertEquals(emptyList(), outerPath)
        }
    }

    @Test
    fun pathIndex_nested_buildsCorrectPath() {
        val outerIndex = 0
        val innerIndex = 1

        validateCollecting {
            pathIndex(outerIndex) {
                pathIndex(innerIndex) {
                    val path = validationContext.validationPath()

                    assertEquals(
                        listOf(IndexPathElement(outerIndex), IndexPathElement(innerIndex)),
                        path,
                    )
                }
            }
        }
    }

    @Test
    fun pathName_collectsViolationsFromBlock() {
        val result =
            validateCollecting {
                pathName("field") {
                    failIf({ true }) { violation("error inside pathName") }
                }
            }

        assertTrue(result.isInvalid)
    }
}
