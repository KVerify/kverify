package io.github.kverify.core.model

import io.github.kverify.core.model.ifValueNotNull
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import kotlin.test.fail

class NamedValueTest :
    FunSpec({
        val name = "name"
        val value = "value"
        val anyValue: Any = Unit

        test("NamedValue primary constructor") {
            val namedValue = NamedValue(name, value)
            val anyNamedValue = NamedValue<Any>(name, anyValue)

            // Parameters
            NamedValue(
                name = name,
                value = value,
            )

            namedValue.check(
                name = name,
                value = value,
            )

            anyNamedValue.check(
                name = name,
                value = anyValue,
            )
        }

        test("withName") {
            val namedValue = value withName name

            namedValue.check(
                name = name,
                value = value,
            )
        }

        test("withValue") {
            val namedValue = name withValue value

            namedValue.check(
                name = name,
                value = value,
            )
        }

        test("KProperty.toNamed") {
            val exampleInstance = ExampleClass(value)
            val namedValue =
                exampleInstance::value.toNamed(
                    value = exampleInstance.value,
                )

            namedValue.check(
                name = exampleInstance::value.name,
                value = exampleInstance.value,
            )
        }

        test("Pair.toNamed") {
            val pair = name to value
            val anyPair = name to anyValue

            val namedValue = pair.toNamed()
            val anyNamedValue = anyPair.toNamed()

            namedValue.check(
                name = pair.first,
                value = pair.second,
            )

            anyNamedValue.check(
                name = anyPair.first,
                value = anyPair.second,
            )
        }

        test("ifValueNotNull") {
            val namedValue: NamedValue<String?> = NamedValue(name, value)
            val nullNamedValue: NamedValue<Any?> = NamedValue(name, null)
            var isExecuted = false

            namedValue.ifValueNotNull {
                isExecuted = true
            }

            nullNamedValue.ifValueNotNull {
                fail("ifValueNotNull lambda should not be executed if the value is null")
            }

            isExecuted shouldBe true
        }

        test("unwrapOrNull") {
            val namedValue: NamedValue<String?> = NamedValue(name, value)
            val nullNamedValue: NamedValue<Any?> = NamedValue(name, null)

            namedValue.unwrapOrNull() shouldNotBe null
            nullNamedValue.unwrapOrNull() shouldBe null
        }
    })

private class ExampleClass(
    val value: String,
)

private fun <T> NamedValue<T>.check(
    name: String,
    value: T,
) {
    this.name shouldBe name
    this.value shouldBe value
}
