package io.github.kverify.core.model

import io.github.kverify.core.util.executionCountCheck
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class NamedValueTest :
    FunSpec({
        val name = "name"
        val value = 1

        test("constructor") {
            val namedValue = NamedValue(name, value)
            val namedValue2 =
                NamedValue(
                    name = name,
                    value = value,
                )

            namedValue shouldBe namedValue2
            namedValue.name shouldBe name
            namedValue.value shouldBe value
        }

        test("withName") {
            val namedValue = value withName name
            namedValue.name shouldBe name
            namedValue.value shouldBe value
        }

        test("withValue") {
            val namedValue = name withValue value
            namedValue.name shouldBe name
            namedValue.value shouldBe value
        }

        test("KProperty.toNamed") {
            val namedValue = TestClass::property.toNamed(value)

            namedValue.name shouldBe TestClass::property.name
            namedValue.value shouldBe value
        }

        test("Pair.toNamed") {
            val namedValue = Pair(name, value).toNamed()

            namedValue.name shouldBe name
            namedValue.value shouldBe value
        }

        test("ifValueNotNull") {
            val notNullNamedValue: NamedValue<Int?> =
                NamedValue(
                    name = name,
                    value = value,
                )
            val nullNamedValue: NamedValue<Int?> =
                NamedValue(
                    name = name,
                    value = null,
                )

            executionCountCheck(
                expectedCount = 1,
                message = "Only notNullNamedValue.ifValueNotNull should be executed",
            ) {
                notNullNamedValue.ifValueNotNull { execute() }
                nullNamedValue.ifValueNotNull { execute() }
            }
        }

        test("unwrapOrNull") {
            val notNullNamedValue: NamedValue<Int?> =
                NamedValue(
                    name = name,
                    value = value,
                )
            val nullNamedValue: NamedValue<Int?> =
                NamedValue(
                    name = name,
                    value = null,
                )

            notNullNamedValue.unwrapOrNull() shouldBe notNullNamedValue
            nullNamedValue.unwrapOrNull() shouldBe null
        }
    })

private class TestClass(
    val property: Int,
)
