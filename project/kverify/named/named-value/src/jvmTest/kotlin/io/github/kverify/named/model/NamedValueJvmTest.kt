package io.github.kverify.named.model

import io.github.kverify.named.exception.PropertyAccessException
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class NamedValueJvmTest :
    FunSpec({
        test("KProperty.toNamed") {
            val testInstance = TestJvmClass(1)

            val namedValue = testInstance::property.toNamed()
            val thrownException =
                shouldThrow<PropertyAccessException> {
                    TestJvmClass::property.toNamed()
                }

            namedValue.name shouldBe "property"
            namedValue.value shouldBe 1

            thrownException.message shouldBe
                """
                Cannot access value of the property 'property' without an instance.
                Hint: Use `instance::prop` instead of `Class::prop`
                """.trimIndent()
        }
    })

private class TestJvmClass(
    val property: Int,
)
