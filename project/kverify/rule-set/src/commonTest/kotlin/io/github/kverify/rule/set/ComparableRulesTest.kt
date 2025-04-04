package io.github.kverify.rule.set

import io.github.kverify.core.context.ValidationContext
import io.github.kverify.core.model.NamedValue
import io.github.kverify.core.model.runValidation
import io.github.kverify.rule.set.factory.ComparableViolationFactory
import io.kotest.assertions.shouldFail
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.property.Arb
import io.kotest.property.arbitrary.int
import io.kotest.property.checkAll
import kotlin.test.DefaultAsserter.fail

class ComparableRulesTest :
    FunSpec({
        val failContext = ValidationContext { fail(it.reason) }

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
                    ComparableViolationFactory
                        .equalTo(
                            value,
                            incorrectValue,
                        ).reason

                shouldFail {
                    namedRule.runValidation(failContext, namedIncorrectValue)
                }.message shouldBe
                    ComparableViolationFactory
                        .equalTo(
                            value,
                            namedIncorrectValue.value,
                            namedIncorrectValue.name,
                        ).reason
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
                    ComparableViolationFactory
                        .notEqualTo(
                            value,
                            incorrectValue,
                        ).reason

                shouldFail {
                    namedRule.runValidation(failContext, namedIncorrectValue)
                }.message shouldBe
                    ComparableViolationFactory
                        .notEqualTo(
                            value,
                            namedIncorrectValue.value,
                            namedIncorrectValue.name,
                        ).reason
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
                    ComparableViolationFactory
                        .greaterThan(
                            value,
                            incorrectValue,
                        ).reason

                shouldFail {
                    namedRule.runValidation(failContext, namedIncorrectValue)
                }.message shouldBe
                    ComparableViolationFactory
                        .greaterThan(
                            value,
                            namedIncorrectValue.value,
                            namedIncorrectValue.name,
                        ).reason
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
                    ComparableViolationFactory
                        .greaterThanOrEqualTo(
                            value,
                            incorrectValue,
                        ).reason

                shouldFail {
                    namedRule.runValidation(failContext, namedIncorrectValue)
                }.message shouldBe
                    ComparableViolationFactory
                        .greaterThanOrEqualTo(
                            value,
                            namedIncorrectValue.value,
                            namedIncorrectValue.name,
                        ).reason
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
                    ComparableViolationFactory
                        .lessThan(
                            value,
                            incorrectValue,
                        ).reason

                shouldFail {
                    namedRule.runValidation(failContext, namedIncorrectValue)
                }.message shouldBe
                    ComparableViolationFactory
                        .lessThan(
                            value,
                            namedIncorrectValue.value,
                            namedIncorrectValue.name,
                        ).reason
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
                    ComparableViolationFactory
                        .lessThanOrEqualTo(
                            value,
                            incorrectValue,
                        ).reason

                shouldFail {
                    namedRule.runValidation(failContext, namedIncorrectValue)
                }.message shouldBe
                    ComparableViolationFactory
                        .lessThanOrEqualTo(
                            value,
                            namedIncorrectValue.value,
                            namedIncorrectValue.name,
                        ).reason
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
                    ComparableViolationFactory
                        .between(
                            range,
                            incorrectValueLow,
                        ).reason

                shouldFail {
                    rule.runValidation(failContext, incorrectValueHigh)
                }.message shouldBe
                    ComparableViolationFactory
                        .between(
                            range,
                            incorrectValueHigh,
                        ).reason

                shouldFail {
                    namedRule.runValidation(failContext, namedIncorrectValueLow)
                }.message shouldBe
                    ComparableViolationFactory
                        .between(
                            range,
                            namedIncorrectValueLow.value,
                            namedIncorrectValueLow.name,
                        ).reason

                shouldFail {
                    namedRule.runValidation(failContext, namedIncorrectValueHigh)
                }.message shouldBe
                    ComparableViolationFactory
                        .between(
                            range,
                            namedIncorrectValueHigh.value,
                            namedIncorrectValueHigh.name,
                        ).reason
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
                    ComparableViolationFactory
                        .notBetween(
                            range,
                            incorrectValueMin,
                        ).reason

                shouldFail {
                    rule.runValidation(failContext, incorrectValueMiddle)
                }.message shouldBe
                    ComparableViolationFactory
                        .notBetween(
                            range,
                            incorrectValueMiddle,
                        ).reason

                shouldFail {
                    rule.runValidation(failContext, incorrectValueMax)
                }.message shouldBe
                    ComparableViolationFactory
                        .notBetween(
                            range,
                            incorrectValueMax,
                        ).reason

                shouldFail {
                    namedRule.runValidation(failContext, namedIncorrectValueMin)
                }.message shouldBe
                    ComparableViolationFactory
                        .notBetween(
                            range,
                            namedIncorrectValueMin.value,
                            namedIncorrectValueMin.name,
                        ).reason

                shouldFail {
                    namedRule.runValidation(failContext, namedIncorrectValueMiddle)
                }.message shouldBe
                    ComparableViolationFactory
                        .notBetween(
                            range,
                            namedIncorrectValueMiddle.value,
                            namedIncorrectValueMiddle.name,
                        ).reason

                shouldFail {
                    namedRule.runValidation(failContext, namedIncorrectValueMax)
                }.message shouldBe
                    ComparableViolationFactory
                        .notBetween(
                            range,
                            namedIncorrectValueMax.value,
                            namedIncorrectValueMax.name,
                        ).reason
            }
        }
    })
