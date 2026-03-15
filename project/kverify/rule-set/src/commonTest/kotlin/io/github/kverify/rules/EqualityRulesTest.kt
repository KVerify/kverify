package io.github.kverify.rules

import io.github.kverify.core.context.EmptyValidationContext
import io.github.kverify.core.context.NamePathElement
import io.github.kverify.core.scope.CollectingValidationScope
import io.github.kverify.core.scope.ThrowingValidationScope
import io.github.kverify.core.scope.Verification
import io.github.kverify.core.violation.Violation
import io.github.kverify.violations.EqualToViolation
import io.github.kverify.violations.NoneOfViolation
import io.github.kverify.violations.NotEqualToViolation
import io.github.kverify.violations.NotNullViolation
import io.github.kverify.violations.OneOfViolation
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertSame
import kotlin.test.assertTrue

class EqualityRulesTest {
    @Test
    fun notNullPassesWhenValueIsNotNull() {
        throwing<String?>("hello").notNull()
    }

    @Test
    fun notNullFailsWhenValueIsNull() {
        val (storage, verification) = collecting<String?>(null)

        verification.notNull()

        assertEquals(1, storage.size)
        assertIs<NotNullViolation>(storage[0])
    }

    @Test
    fun notNullViolationHasDefaultReason() {
        val (storage, verification) = collecting<String?>(null)

        verification.notNull()

        assertEquals("Value must not be null", storage[0].reason)
    }

    @Test
    fun notNullViolationHasCustomReason() {
        val customReason = "name is required"
        val (storage, verification) = collecting<String?>(null)

        verification.notNull(reason = customReason)

        assertEquals(customReason, storage[0].reason)
    }

    @Test
    fun notNullViolationCarriesCorrectPath() {
        val pathElement = NamePathElement("userId")
        val (storage, verification) = collecting<String?>(null, pathElement)

        verification.notNull()

        assertEquals(listOf(pathElement), (storage[0] as NotNullViolation).validationPath.elements)
    }

    @Test
    fun notNullReturnsSameVerification() {
        val (_, verification) = collecting<String?>("value")

        val returned = verification.notNull()

        assertSame(verification, returned)
    }

    @Test
    fun equalToPassesWhenValueEqualsExpected() {
        throwing(42).equalTo(42)
    }

    @Test
    fun equalToFailsWhenValueDiffersFromExpected() {
        val (storage, verification) = collecting(43)

        verification.equalTo(42)

        assertEquals(1, storage.size)
        assertIs<EqualToViolation<*>>(storage[0])
    }

    @Test
    fun equalToViolationStoresExpectedAndActual() {
        val expected = 10
        val actual = 99
        val (storage, verification) = collecting(actual)

        verification.equalTo(expected)

        val violation = storage[0] as EqualToViolation<*>
        assertEquals(expected, violation.expected)
        assertEquals(actual, violation.actual)
    }

    @Test
    fun equalToViolationHasDefaultReason() {
        val expected = 5
        val actual = 6
        val (storage, verification) = collecting(actual)

        verification.equalTo(expected)

        assertEquals("Value must be equal to $expected. Actual: $actual", storage[0].reason)
    }

    @Test
    fun equalToViolationHasCustomReason() {
        val customReason = "token mismatch"
        val (storage, verification) = collecting("abc")

        verification.equalTo("xyz", reason = customReason)

        assertEquals(customReason, storage[0].reason)
    }

    @Test
    fun equalToViolationCarriesCorrectPath() {
        val pathElement = NamePathElement("code")
        val (storage, verification) = collecting("wrong", pathElement)

        verification.equalTo("right")

        assertEquals(listOf(pathElement), (storage[0] as EqualToViolation<*>).validationPath.elements)
    }

    @Test
    fun notEqualToPassesWhenValueDiffersFromForbidden() {
        throwing("user").notEqualTo("admin")
    }

    @Test
    fun notEqualToFailsWhenValueEqualsForbidden() {
        val forbidden = "admin"
        val (storage, verification) = collecting(forbidden)

        verification.notEqualTo(forbidden)

        assertEquals(1, storage.size)
        assertIs<NotEqualToViolation<*>>(storage[0])
    }

    @Test
    fun notEqualToViolationStoresForbiddenValue() {
        val forbidden = "root"
        val (storage, verification) = collecting(forbidden)

        verification.notEqualTo(forbidden)

        assertEquals(forbidden, (storage[0] as NotEqualToViolation<*>).forbidden)
    }

    @Test
    fun notEqualToViolationHasDefaultReason() {
        val forbidden = "banned"
        val (storage, verification) = collecting(forbidden)

        verification.notEqualTo(forbidden)

        assertEquals("Value must not be equal to $forbidden", storage[0].reason)
    }

    @Test
    fun notEqualToViolationHasCustomReason() {
        val customReason = "this value is reserved"
        val forbidden = "system"
        val (storage, verification) = collecting(forbidden)

        verification.notEqualTo(forbidden, reason = customReason)

        assertEquals(customReason, storage[0].reason)
    }

    @Test
    fun notEqualToViolationCarriesCorrectPath() {
        val pathElement = NamePathElement("role")
        val forbidden = "superuser"
        val (storage, verification) = collecting(forbidden, pathElement)

        verification.notEqualTo(forbidden)

        assertEquals(listOf(pathElement), (storage[0] as NotEqualToViolation<*>).validationPath.elements)
    }

    @Test
    fun oneOfPassesWhenValueIsInAllowedSet() {
        throwing("read").oneOf(setOf("read", "write", "execute"))
    }

    @Test
    fun oneOfFailsWhenValueIsNotInAllowedSet() {
        val (storage, verification) = collecting("delete")

        verification.oneOf(setOf("read", "write"))

        assertEquals(1, storage.size)
        assertIs<OneOfViolation<*>>(storage[0])
    }

    @Test
    fun oneOfViolationStoresAllowedAndActual() {
        val allowed = setOf("a", "b")
        val actual = "c"
        val (storage, verification) = collecting(actual)

        verification.oneOf(allowed)

        val violation = storage[0] as OneOfViolation<*>
        assertEquals(allowed, violation.allowed)
        assertEquals(actual, violation.actual)
    }

    @Test
    fun oneOfViolationHasDefaultReason() {
        val allowed = setOf(1, 2, 3)
        val actual = 5
        val (storage, verification) = collecting(actual)

        verification.oneOf(allowed)

        assertEquals("Value must be one of $allowed. Actual: $actual", storage[0].reason)
    }

    @Test
    fun oneOfViolationHasCustomReason() {
        val customReason = "invalid status"
        val (storage, verification) = collecting("unknown")

        verification.oneOf(setOf("active", "inactive"), reason = customReason)

        assertEquals(customReason, storage[0].reason)
    }

    @Test
    fun oneOfViolationCarriesCorrectPath() {
        val pathElement = NamePathElement("status")
        val (storage, verification) = collecting("unknown", pathElement)

        verification.oneOf(setOf("active", "inactive"))

        assertEquals(listOf(pathElement), (storage[0] as OneOfViolation<*>).validationPath.elements)
    }

    @Test
    fun noneOfPassesWhenValueIsNotInForbiddenSet() {
        throwing("select").noneOf(setOf("delete", "drop"))
    }

    @Test
    fun noneOfFailsWhenValueIsInForbiddenSet() {
        val (storage, verification) = collecting("drop")

        verification.noneOf(setOf("drop", "truncate"))

        assertEquals(1, storage.size)
        assertIs<NoneOfViolation<*>>(storage[0])
    }

    @Test
    fun noneOfViolationStoresForbiddenAndActual() {
        val forbidden = setOf("x", "y")
        val actual = "x"
        val (storage, verification) = collecting(actual)

        verification.noneOf(forbidden)

        val violation = storage[0] as NoneOfViolation<*>
        assertEquals(forbidden, violation.forbidden)
        assertEquals(actual, violation.actual)
    }

    @Test
    fun noneOfViolationHasDefaultReason() {
        val forbidden = setOf("banned1", "banned2")
        val actual = "banned1"
        val (storage, verification) = collecting(actual)

        verification.noneOf(forbidden)

        assertEquals("Value must not be one of $forbidden. Actual: $actual", storage[0].reason)
    }

    @Test
    fun noneOfViolationHasCustomReason() {
        val customReason = "keyword is reserved"
        val (storage, verification) = collecting("null")

        verification.noneOf(setOf("null", "undefined"), reason = customReason)

        assertEquals(customReason, storage[0].reason)
    }

    @Test
    fun noneOfViolationCarriesCorrectPath() {
        val pathElement = NamePathElement("command")
        val (storage, verification) = collecting("drop", pathElement)

        verification.noneOf(setOf("drop", "truncate"))

        assertEquals(listOf(pathElement), (storage[0] as NoneOfViolation<*>).validationPath.elements)
    }

    @Test
    fun notNullReturnsSameVerificationOnFail() {
        val (_, verification) = collecting<String?>(null)

        val returned = verification.notNull()

        assertSame(verification, returned)
    }

    @Test
    fun equalToReturnsSameVerification() {
        val (_, verification) = collecting(42)

        val returned = verification.equalTo(42)

        assertSame(verification, returned)
    }

    @Test
    fun notEqualToReturnsSameVerification() {
        val (_, verification) = collecting("user")

        val returned = verification.notEqualTo("admin")

        assertSame(verification, returned)
    }

    @Test
    fun oneOfReturnsSameVerification() {
        val (_, verification) = collecting("read")

        val returned = verification.oneOf(setOf("read", "write"))

        assertSame(verification, returned)
    }

    @Test
    fun noneOfReturnsSameVerification() {
        val (_, verification) = collecting("select")

        val returned = verification.noneOf(setOf("drop", "truncate"))

        assertSame(verification, returned)
    }
}
