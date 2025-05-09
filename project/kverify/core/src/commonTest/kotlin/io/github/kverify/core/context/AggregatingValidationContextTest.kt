package io.github.kverify.core.context

import io.github.kverify.core.exception.ValidationException
import io.github.kverify.core.util.StubRule
import io.github.kverify.core.util.shouldContainExactly
import io.github.kverify.core.violation.Violation
import io.github.kverify.core.violation.ViolationReason
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class AggregatingValidationContextTest :
    FunSpec({
        val message = "test"
        val violation = ViolationReason(message)
        val failingRule = StubRule<Any>(shouldFail = true, violation)

        test("Adds violations to violationsStorage") {
            val violationsStorage: MutableCollection<Violation> = mutableListOf(violation)
            val context = AggregatingValidationContext(violationsStorage)

            context.onFailure(violation)

            violationsStorage.shouldContainExactly(violation, violation)
        }

        test("validateAll") {
            val violationsStorage: MutableCollection<Violation> = mutableListOf(violation)

            val defaultStoreResult =
                validateAll {
                    onFailure(violation)
                }
            val customStoreResult =
                validateAll(violationsStorage) {
                    onFailure(violation)
                }

            // validateAll must create a copy of violationsStorage on return
            violationsStorage.add(violation)

            defaultStoreResult.violations.shouldContainExactly(violation)
            customStoreResult.violations.shouldContainExactly(violation, violation)
            violationsStorage.shouldContainExactly(violation, violation, violation)
        }

        test("validateAllWithRules") {
            val violationsStorage: MutableCollection<Violation> = mutableListOf(violation)

            val defaultStoreResult = Unit.validateAllWithRules(failingRule)
            val customStoreResult =
                Unit.validateAllWithRules(
                    violationsStorage = violationsStorage,
                    failingRule,
                )

            // validateAll must create a copy of violationsStorage on return
            violationsStorage.add(violation)

            defaultStoreResult.violations.shouldContainExactly(violation)
            customStoreResult.violations.shouldContainExactly(violation, violation)
            violationsStorage.shouldContainExactly(violation, violation, violation)
        }

        test("runValidatingAll") {
            val value = "test"
            val violationsStorage: MutableCollection<Violation> = mutableListOf(violation)

            val defaultStoreResult =
                runValidatingAll {
                    Unit.applyRules(failingRule)
                    value
                }
            val customStoreResult =
                runValidatingAll(violationsStorage) {
                    Unit.applyRules(failingRule)
                    value
                }

            val successResult = runValidatingAll { value }

            // validateAll must create a copy of violationsStorage on return
            violationsStorage.add(violation)

            shouldThrow<ValidationException> {
                defaultStoreResult.getOrThrow()
            }.violations.shouldContainExactly(violation)
            shouldThrow<ValidationException> {
                customStoreResult.getOrThrow()
            }.violations.shouldContainExactly(violation, violation)

            successResult.getOrThrow() shouldBe value
            violationsStorage.shouldContainExactly(violation, violation, violation)
        }
    })
