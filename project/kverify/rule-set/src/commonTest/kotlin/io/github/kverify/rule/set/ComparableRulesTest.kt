package io.github.kverify.rule.set

import io.github.kverify.core.context.ValidationContext
import io.github.kverify.core.model.NamedValue
import io.github.kverify.core.model.runValidation
import io.github.kverify.rule.set.violation.ComparableViolations
import io.kotest.assertions.shouldFail
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.property.Arb
import io.kotest.property.arbitrary.int
import io.kotest.property.checkAll
import kotlin.test.DefaultAsserter.fail

class ComparableRulesTest :
    FunSpec({
        val failContext = ValidationContext { fail(it.message) }

        test("equal to") {
            Arb.int(1..10).checkAll { value ->
                val rule = ComparableRules.EqualTo<Int>(value)
                val namedRule = ComparableRules.NamedEqualTo<Int>(value)

                val correctValue = value
                val incorrectValue = value + 1

                val namedCorrectValue = NamedValue("name", correctValue)
                val namedIncorrectValue = NamedValue("name", incorrectValue)

                rule.runValidation(failContext, correctValue)
                namedRule.runValidation(failContext, namedCorrectValue)

                shouldFail {
                    rule.runValidation(failContext, incorrectValue)
                }.message shouldBe
                    ComparableViolations
                        .equalTo(
                            value,
                            incorrectValue,
                        ).message

                shouldFail {
                    namedRule.runValidation(failContext, namedIncorrectValue)
                }.message shouldBe
                    ComparableViolations
                        .equalTo(
                            value,
                            namedIncorrectValue.value,
                            namedIncorrectValue.name,
                        ).message
            }
        }

        test("not equal to") {
            Arb.int(1..10).checkAll { value ->
                val rule = ComparableRules.NotEqualTo<Int>(value)
                val namedRule = ComparableRules.NamedNotEqualTo<Int>(value)

                val correctValue = value + 1
                val incorrectValue = value

                val namedCorrectValue = NamedValue("name", correctValue)
                val namedIncorrectValue = NamedValue("name", incorrectValue)

                rule.runValidation(failContext, correctValue)
                namedRule.runValidation(failContext, namedCorrectValue)

                shouldFail {
                    rule.runValidation(failContext, incorrectValue)
                }.message shouldBe
                    ComparableViolations
                        .notEqualTo(
                            value,
                            incorrectValue,
                        ).message

                shouldFail {
                    namedRule.runValidation(failContext, namedIncorrectValue)
                }.message shouldBe
                    ComparableViolations
                        .notEqualTo(
                            value,
                            namedIncorrectValue.value,
                            namedIncorrectValue.name,
                        ).message
            }
        }

        test("greater than") {
            Arb.int(1..10).checkAll { value ->
                val rule = ComparableRules.GreaterThan<Int>(value)
                val namedRule = ComparableRules.NamedGreaterThan<Int>(value)

                val correctValue = value + 1
                val incorrectValue = value

                val namedCorrectValue = NamedValue("name", correctValue)
                val namedIncorrectValue = NamedValue("name", incorrectValue)

                rule.runValidation(failContext, correctValue)
                namedRule.runValidation(failContext, namedCorrectValue)

                shouldFail {
                    rule.runValidation(failContext, incorrectValue)
                }.message shouldBe
                    ComparableViolations
                        .greaterThan(
                            value,
                            incorrectValue,
                        ).message

                shouldFail {
                    namedRule.runValidation(failContext, namedIncorrectValue)
                }.message shouldBe
                    ComparableViolations
                        .greaterThan(
                            value,
                            namedIncorrectValue.value,
                            namedIncorrectValue.name,
                        ).message
            }
        }

        test("greater than or equal to") {
            Arb.int(1..10).checkAll { value ->
                val rule = ComparableRules.GreaterThanOrEqualTo<Int>(value)
                val namedRule = ComparableRules.NamedGreaterThanOrEqualTo<Int>(value)

                val correctValueEqual = value
                val correctValueGreater = value + 1
                val incorrectValue = value - 1

                val namedCorrectValueEqual = NamedValue("name", correctValueEqual)
                val namedCorrectValueGreater = NamedValue("name", correctValueGreater)
                val namedIncorrectValue = NamedValue("name", incorrectValue)

                rule.runValidation(failContext, correctValueEqual)
                rule.runValidation(failContext, correctValueGreater)
                namedRule.runValidation(failContext, namedCorrectValueEqual)
                namedRule.runValidation(failContext, namedCorrectValueGreater)

                shouldFail {
                    rule.runValidation(failContext, incorrectValue)
                }.message shouldBe
                    ComparableViolations
                        .greaterThanOrEqualTo(
                            value,
                            incorrectValue,
                        ).message

                shouldFail {
                    namedRule.runValidation(failContext, namedIncorrectValue)
                }.message shouldBe
                    ComparableViolations
                        .greaterThanOrEqualTo(
                            value,
                            namedIncorrectValue.value,
                            namedIncorrectValue.name,
                        ).message
            }
        }

        test("less than") {
            Arb.int(1..10).checkAll { value ->
                val rule = ComparableRules.LessThan<Int>(value)
                val namedRule = ComparableRules.NamedLessThan<Int>(value)

                val correctValue = value - 1
                val incorrectValue = value

                val namedCorrectValue = NamedValue("name", correctValue)
                val namedIncorrectValue = NamedValue("name", incorrectValue)

                rule.runValidation(failContext, correctValue)
                namedRule.runValidation(failContext, namedCorrectValue)

                shouldFail {
                    rule.runValidation(failContext, incorrectValue)
                }.message shouldBe
                    ComparableViolations
                        .lessThan(
                            value,
                            incorrectValue,
                        ).message

                shouldFail {
                    namedRule.runValidation(failContext, namedIncorrectValue)
                }.message shouldBe
                    ComparableViolations
                        .lessThan(
                            value,
                            namedIncorrectValue.value,
                            namedIncorrectValue.name,
                        ).message
            }
        }

        test("less than or equal to") {
            Arb.int(1..10).checkAll { value ->
                val rule = ComparableRules.LessThanOrEqualTo<Int>(value)
                val namedRule = ComparableRules.NamedLessThanOrEqualTo<Int>(value)

                val correctValueEqual = value
                val correctValueLess = value - 1
                val incorrectValue = value + 1

                val namedCorrectValueEqual = NamedValue("name", correctValueEqual)
                val namedCorrectValueLess = NamedValue("name", correctValueLess)
                val namedIncorrectValue = NamedValue("name", incorrectValue)

                rule.runValidation(failContext, correctValueEqual)
                rule.runValidation(failContext, correctValueLess)
                namedRule.runValidation(failContext, namedCorrectValueEqual)
                namedRule.runValidation(failContext, namedCorrectValueLess)

                shouldFail {
                    rule.runValidation(failContext, incorrectValue)
                }.message shouldBe
                    ComparableViolations
                        .lessThanOrEqualTo(
                            value,
                            incorrectValue,
                        ).message

                shouldFail {
                    namedRule.runValidation(failContext, namedIncorrectValue)
                }.message shouldBe
                    ComparableViolations
                        .lessThanOrEqualTo(
                            value,
                            namedIncorrectValue.value,
                            namedIncorrectValue.name,
                        ).message
            }
        }

        test("between") {
            Arb.int(1..10).checkAll { min ->
                val max = min + 5
                val range = min..max

                val rule = ComparableRules.Between<Int>(range)
                val namedRule = ComparableRules.NamedBetween<Int>(range)

                val correctValueMin = min
                val correctValueMiddle = min + 2
                val correctValueMax = max
                val incorrectValueLow = min - 1
                val incorrectValueHigh = max + 1

                val namedCorrectValueMin = NamedValue("name", correctValueMin)
                val namedCorrectValueMiddle = NamedValue("name", correctValueMiddle)
                val namedCorrectValueMax = NamedValue("name", correctValueMax)
                val namedIncorrectValueLow = NamedValue("name", incorrectValueLow)
                val namedIncorrectValueHigh = NamedValue("name", incorrectValueHigh)

                rule.runValidation(failContext, correctValueMin)
                rule.runValidation(failContext, correctValueMiddle)
                rule.runValidation(failContext, correctValueMax)

                namedRule.runValidation(failContext, namedCorrectValueMin)
                namedRule.runValidation(failContext, namedCorrectValueMiddle)
                namedRule.runValidation(failContext, namedCorrectValueMax)

                shouldFail {
                    rule.runValidation(failContext, incorrectValueLow)
                }.message shouldBe
                    ComparableViolations
                        .between(
                            range,
                            incorrectValueLow,
                        ).message

                shouldFail {
                    rule.runValidation(failContext, incorrectValueHigh)
                }.message shouldBe
                    ComparableViolations
                        .between(
                            range,
                            incorrectValueHigh,
                        ).message

                shouldFail {
                    namedRule.runValidation(failContext, namedIncorrectValueLow)
                }.message shouldBe
                    ComparableViolations
                        .between(
                            range,
                            namedIncorrectValueLow.value,
                            namedIncorrectValueLow.name,
                        ).message

                shouldFail {
                    namedRule.runValidation(failContext, namedIncorrectValueHigh)
                }.message shouldBe
                    ComparableViolations
                        .between(
                            range,
                            namedIncorrectValueHigh.value,
                            namedIncorrectValueHigh.name,
                        ).message
            }
        }

        test("not between") {
            Arb.int(1..10).checkAll { min ->
                val max = min + 5
                val range = min..max

                val rule = ComparableRules.NotBetween<Int>(range)
                val namedRule = ComparableRules.NamedNotBetween<Int>(range)

                val incorrectValueMin = min
                val incorrectValueMiddle = min + 2
                val incorrectValueMax = max
                val correctValueLow = min - 1
                val correctValueHigh = max + 1

                val namedIncorrectValueMin = NamedValue("name", incorrectValueMin)
                val namedIncorrectValueMiddle = NamedValue("name", incorrectValueMiddle)
                val namedIncorrectValueMax = NamedValue("name", incorrectValueMax)
                val namedCorrectValueLow = NamedValue("name", correctValueLow)
                val namedCorrectValueHigh = NamedValue("name", correctValueHigh)

                rule.runValidation(failContext, correctValueLow)
                rule.runValidation(failContext, correctValueHigh)

                namedRule.runValidation(failContext, namedCorrectValueLow)
                namedRule.runValidation(failContext, namedCorrectValueHigh)

                shouldFail {
                    rule.runValidation(failContext, incorrectValueMin)
                }.message shouldBe
                    ComparableViolations
                        .notBetween(
                            range,
                            incorrectValueMin,
                        ).message

                shouldFail {
                    rule.runValidation(failContext, incorrectValueMiddle)
                }.message shouldBe
                    ComparableViolations
                        .notBetween(
                            range,
                            incorrectValueMiddle,
                        ).message

                shouldFail {
                    rule.runValidation(failContext, incorrectValueMax)
                }.message shouldBe
                    ComparableViolations
                        .notBetween(
                            range,
                            incorrectValueMax,
                        ).message

                shouldFail {
                    namedRule.runValidation(failContext, namedIncorrectValueMin)
                }.message shouldBe
                    ComparableViolations
                        .notBetween(
                            range,
                            namedIncorrectValueMin.value,
                            namedIncorrectValueMin.name,
                        ).message

                shouldFail {
                    namedRule.runValidation(failContext, namedIncorrectValueMiddle)
                }.message shouldBe
                    ComparableViolations
                        .notBetween(
                            range,
                            namedIncorrectValueMiddle.value,
                            namedIncorrectValueMiddle.name,
                        ).message

                shouldFail {
                    namedRule.runValidation(failContext, namedIncorrectValueMax)
                }.message shouldBe
                    ComparableViolations
                        .notBetween(
                            range,
                            namedIncorrectValueMax.value,
                            namedIncorrectValueMax.name,
                        ).message
            }
        }
    })
