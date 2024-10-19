package io.github.kverify.dsl.validator

import io.github.kverify.core.exception.ValidationException
import io.github.kverify.dsl.extension.violation
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import kotlin.test.fail

class ImmediateTest :
    FunSpec({
        test("validateOrThrow") {
            shouldThrow<ValidationException> {
                validateOrThrow { violation("fail") }
            }.message shouldBe "fail"
        }

        test("validateFirst") {
            val message = "fail"

            val result =
                validateFirst {
                    validate(message) { false }
                    validate("should not be executed") { false }
                }.onValid { fail("Validation should fail") }
                    .violations
                    .also { it.size shouldBe 1 }
                    .first()

            result.message shouldBe message
        }
    })