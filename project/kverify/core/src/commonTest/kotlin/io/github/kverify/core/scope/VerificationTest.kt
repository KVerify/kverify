package io.github.kverify.core.scope

import io.github.kverify.core.context.IndexPathElement
import io.github.kverify.core.context.PropertyPathElement
import io.github.kverify.core.context.ValidationPathElement
import io.github.kverify.core.context.validationPath
import io.github.kverify.core.violation.Violation
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertSame
import kotlin.test.assertTrue

class VerificationTest {
    private data class SimpleViolation(
        override val reason: String,
    ) : Violation

    @Test
    fun verifyCreatesVerificationWithValue() {
        verifyWithCollecting {
            val verification = verify("hello")
            assertEquals("hello", verification.value)
        }
    }

    @Test
    fun verifyCreatesVerificationWithScope() {
        verifyWithCollecting {
            val verification = verify("hello")
            assertSame(this, verification.scope)
        }
    }

    @Test
    fun verifyPropertyExtractsValueAndAddsPath() {
        data class User(
            val name: String,
        )

        val user = User("Alice")

        verifyWithCollecting {
            val verification = verify(user::name)
            assertEquals("Alice", verification.value)

            val path = verification.scope.validationContext.validationPath()
            assertEquals(1, path.size)
            assertEquals(PropertyPathElement("name"), path[0])
        }
    }

    @Test
    fun takeIfNotNullReturnsVerificationForNonNull() {
        verifyWithCollecting {
            val value: String? = "hello"
            val verification = verify(value).takeIfNotNull()
            assertNotNull(verification)
            assertEquals("hello", verification.value)
        }
    }

    @Test
    fun takeIfNotNullReturnsNullForNull() {
        verifyWithCollecting {
            val value: String? = null
            val verification = verify(value).takeIfNotNull()
            assertNull(verification)
        }
    }

    @Test
    fun chainingMultipleVerifications() {
        val violations =
            verifyWithCollecting {
                verify("").apply {
                    if (value.isBlank()) scope.onFailure(SimpleViolation("blank"))
                    if (value.length < 3) scope.onFailure(SimpleViolation("too short"))
                }
            }

        assertEquals(2, violations.size)
    }

    @Test
    fun nestedPropertyPathsStack() {
        data class Address(
            val city: String,
        )

        data class User(
            val address: Address,
        )

        val user = User(Address(""))

        val violations =
            verifyWithCollecting {
                val addressVerification = verify(user::address)
                val cityScope = addressVerification.scope + PropertyPathElement("city")

                val path = cityScope.validationContext.validationPath()
                assertEquals(2, path.size)
                assertEquals(PropertyPathElement("address"), path[0])
                assertEquals(PropertyPathElement("city"), path[1])
            }

        assertTrue(violations.isEmpty())
    }
}
