package io.github.kverify.core.model

import io.github.kverify.core.context.ValidationContext
import io.github.kverify.core.util.executionCountCheck
import io.github.kverify.core.violation.asViolation
import io.kotest.core.spec.style.FunSpec

class RuleTest :
    FunSpec({
        test("runValidation") {
            executionCountCheck(
                expectedCount = 2,
            ) {
                val context = ValidationContext { execute() }

                val rule = Rule<Any> { onFailure("".asViolation()) }

                rule.runValidation(context, Unit)

                rule.run { context.runValidation(Unit) }
            }
        }

        test("plus") {
            executionCountCheck(
                expectedCount = 2,
            ) {
                val context = ValidationContext { execute() }

                val rule = Rule<Any> { onFailure("".asViolation()) }

                (rule + rule).runValidation(context, Unit)
            }
        }

        test("factory function Rule((T) -> Boolean, (T) -> Violation)") {
            val context = ValidationContext {}

            executionCountCheck(
                expectedCount = 2,
            ) {
                val rule =
                    Rule<Any>(
                        predicate = {
                            execute()
                            false
                        },
                        violationGenerator = {
                            execute()
                            "".asViolation()
                        },
                    )

                rule.runValidation(context, Unit)
            }

            executionCountCheck(
                expectedCount = 1,
                message = "violationGenerator was executed with predicate==true",
            ) {
                val rule =
                    Rule<Any>(
                        predicate = {
                            execute()
                            true
                        },
                        violationGenerator = {
                            execute()
                            "".asViolation()
                        },
                    )

                rule.runValidation(context, Unit)
            }
        }
    })
