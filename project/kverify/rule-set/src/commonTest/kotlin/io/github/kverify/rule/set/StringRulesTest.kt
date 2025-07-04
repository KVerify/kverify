package io.github.kverify.rule.set

import io.github.kverify.core.context.ValidationContext
import io.github.kverify.core.model.NamedValue
import io.github.kverify.core.rule.runValidation
import io.github.kverify.rule.set.factory.StringViolationFactory
import io.kotest.assertions.shouldFail
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.property.Arb
import io.kotest.property.arbitrary.boolean
import io.kotest.property.arbitrary.int
import io.kotest.property.arbitrary.string
import io.kotest.property.checkAll
import kotlin.test.DefaultAsserter.fail

class StringRulesTest :
    FunSpec({
        val failContext = ValidationContext { fail(it.reason) }

        test("of length") {
            Arb.int(1..10).checkAll { length ->
                val rule = StringRules.OfLength(length)
                val namedRule = StringRules.NamedOfLength(length)

                val correctValue = "a".repeat(length)
                val incorrectValue = "a".repeat(length + 1)

                val namedCorrectValue = NamedValue("name", correctValue)
                val namedIncorrectValue = NamedValue("name", incorrectValue)

                rule.runValidation(failContext, correctValue)
                namedRule.runValidation(failContext, namedCorrectValue)

                shouldFail {
                    rule.runValidation(failContext, incorrectValue)
                }.message shouldBe
                    StringViolationFactory
                        .ofLength(
                            length,
                            incorrectValue,
                        ).reason

                shouldFail {
                    namedRule.runValidation(failContext, namedIncorrectValue)
                }.message shouldBe
                    StringViolationFactory
                        .ofLength(
                            length,
                            namedIncorrectValue.value,
                            namedIncorrectValue.name,
                        ).reason
            }
        }

        test("not of length") {
            Arb.int(1..10).checkAll { length ->
                val rule = StringRules.NotOfLength(length)
                val namedRule = StringRules.NamedNotOfLength(length)

                val correctValue = "a".repeat(length + 1)
                val incorrectValue = "a".repeat(length)

                val namedCorrectValue = NamedValue("name", correctValue)
                val namedIncorrectValue = NamedValue("name", incorrectValue)

                rule.runValidation(failContext, correctValue)
                namedRule.runValidation(failContext, namedCorrectValue)

                shouldFail {
                    rule.runValidation(failContext, incorrectValue)
                }.message shouldBe
                    StringViolationFactory
                        .notOfLength(
                            length,
                            incorrectValue,
                        ).reason

                shouldFail {
                    namedRule.runValidation(failContext, namedIncorrectValue)
                }.message shouldBe
                    StringViolationFactory
                        .notOfLength(
                            length,
                            namedIncorrectValue.value,
                            namedIncorrectValue.name,
                        ).reason
            }
        }

        test("max length") {
            Arb.int(5..10).checkAll { max ->
                val rule = StringRules.MaxLength(max)
                val namedRule = StringRules.NamedMaxLength(max)

                val correctValue = "a".repeat(max)
                val incorrectValue = "a".repeat(max + 1)

                val namedCorrectValue = NamedValue("name", correctValue)
                val namedIncorrectValue = NamedValue("name", incorrectValue)

                rule.runValidation(failContext, correctValue)
                namedRule.runValidation(failContext, namedCorrectValue)

                shouldFail {
                    rule.runValidation(failContext, incorrectValue)
                }.message shouldBe
                    StringViolationFactory
                        .maxLength(
                            max,
                            incorrectValue,
                        ).reason

                shouldFail {
                    namedRule.runValidation(failContext, namedIncorrectValue)
                }.message shouldBe
                    StringViolationFactory
                        .maxLength(
                            max,
                            namedIncorrectValue.value,
                            namedIncorrectValue.name,
                        ).reason
            }
        }

        test("min length") {
            Arb.int(5..10).checkAll { min ->
                val rule = StringRules.MinLength(min)
                val namedRule = StringRules.MinLength(min).NamedMinLength(min)

                val correctValue = "a".repeat(min)
                val incorrectValue = "a".repeat(min - 1)

                val namedCorrectValue = NamedValue("name", correctValue)
                val namedIncorrectValue = NamedValue("name", incorrectValue)

                rule.runValidation(failContext, correctValue)
                namedRule.runValidation(failContext, namedCorrectValue)

                shouldFail {
                    rule.runValidation(failContext, incorrectValue)
                }.message shouldBe
                    StringViolationFactory
                        .minLength(
                            min,
                            incorrectValue,
                        ).reason

                shouldFail {
                    namedRule.runValidation(failContext, namedIncorrectValue)
                }.message shouldBe
                    StringViolationFactory
                        .minLength(
                            min,
                            namedIncorrectValue.value,
                            namedIncorrectValue.name,
                        ).reason
            }
        }

        test("length between") {
            checkAll(
                Arb.int(1..5),
                Arb.int(6..10),
            ) { min, max ->
                val range = min..max
                val rule = StringRules.LengthBetween(range)
                val namedRule = StringRules.NamedLengthBetween(range)

                val correctValue = "a".repeat((min + max) / 2)
                val incorrectValueTooShort = "a".repeat(min - 1)
                val incorrectValueTooLong = "a".repeat(max + 1)

                val namedCorrectValue = NamedValue("name", correctValue)
                val namedIncorrectValueTooShort = NamedValue("name", incorrectValueTooShort)
                val namedIncorrectValueTooLong = NamedValue("name", incorrectValueTooLong)

                rule.runValidation(failContext, correctValue)
                namedRule.runValidation(failContext, namedCorrectValue)

                shouldFail {
                    rule.runValidation(failContext, incorrectValueTooShort)
                }.message shouldBe
                    StringViolationFactory
                        .lengthBetween(
                            range,
                            incorrectValueTooShort,
                        ).reason

                shouldFail {
                    rule.runValidation(failContext, incorrectValueTooLong)
                }.message shouldBe
                    StringViolationFactory
                        .lengthBetween(
                            range,
                            incorrectValueTooLong,
                        ).reason

                shouldFail {
                    namedRule.runValidation(failContext, namedIncorrectValueTooShort)
                }.message shouldBe
                    StringViolationFactory
                        .lengthBetween(
                            range,
                            namedIncorrectValueTooShort.value,
                            namedIncorrectValueTooShort.name,
                        ).reason

                shouldFail {
                    namedRule.runValidation(failContext, namedIncorrectValueTooLong)
                }.message shouldBe
                    StringViolationFactory
                        .lengthBetween(
                            range,
                            namedIncorrectValueTooLong.value,
                            namedIncorrectValueTooLong.name,
                        ).reason
            }
        }

        test("length not between") {
            checkAll(
                Arb.int(1..5),
                Arb.int(6..10),
            ) { min, max ->
                val range = min..max
                val rule = StringRules.LengthNotBetween(range)
                val namedRule = StringRules.NamedLengthNotBetween(range)

                val correctValueTooShort = "a".repeat(min - 1)
                val correctValueTooLong = "a".repeat(max + 1)
                val incorrectValue = "a".repeat((min + max) / 2)

                val namedCorrectValueTooShort = NamedValue("name", correctValueTooShort)
                val namedCorrectValueTooLong = NamedValue("name", correctValueTooLong)
                val namedIncorrectValue = NamedValue("name", incorrectValue)

                rule.runValidation(failContext, correctValueTooShort)
                rule.runValidation(failContext, correctValueTooLong)
                namedRule.runValidation(failContext, namedCorrectValueTooShort)
                namedRule.runValidation(failContext, namedCorrectValueTooLong)

                shouldFail {
                    rule.runValidation(failContext, incorrectValue)
                }.message shouldBe
                    StringViolationFactory
                        .lengthNotBetween(
                            range,
                            incorrectValue,
                        ).reason

                shouldFail {
                    namedRule.runValidation(failContext, namedIncorrectValue)
                }.message shouldBe
                    StringViolationFactory
                        .lengthNotBetween(
                            range,
                            namedIncorrectValue.value,
                            namedIncorrectValue.name,
                        ).reason
            }
        }

        test("contains") {
            checkAll(
                Arb.string(1..5),
                Arb.boolean(),
            ) { substring, ignoreCase ->
                val rule = StringRules.Contains(substring, ignoreCase)
                val namedRule = StringRules.NamedContains(substring, ignoreCase)

                val correctValue = "pre${substring}post"
                val incorrectValue = "123456".replace(substring, "", ignoreCase)

                val namedCorrectValue = NamedValue("name", correctValue)
                val namedIncorrectValue = NamedValue("name", incorrectValue)

                rule.runValidation(failContext, correctValue)
                namedRule.runValidation(failContext, namedCorrectValue)

                shouldFail {
                    rule.runValidation(failContext, incorrectValue)
                }.message shouldBe
                    StringViolationFactory
                        .contains(
                            substring,
                            incorrectValue,
                        ).reason

                shouldFail {
                    namedRule.runValidation(failContext, namedIncorrectValue)
                }.message shouldBe
                    StringViolationFactory
                        .contains(
                            substring,
                            namedIncorrectValue.value,
                            namedIncorrectValue.name,
                        ).reason
            }
        }

        test("contains regex") {
            val regex = Regex("[0-9]+")
            val rule = StringRules.ContainsRegex(regex)
            val namedRule = StringRules.NamedContainsRegex(regex)

            val correctValue = "abc123def"
            val incorrectValue = "abcdef"

            val namedCorrectValue = NamedValue("name", correctValue)
            val namedIncorrectValue = NamedValue("name", incorrectValue)

            rule.runValidation(failContext, correctValue)
            namedRule.runValidation(failContext, namedCorrectValue)

            shouldFail {
                rule.runValidation(failContext, incorrectValue)
            }.message shouldBe
                StringViolationFactory
                    .contains(
                        regex,
                        incorrectValue,
                    ).reason

            shouldFail {
                namedRule.runValidation(failContext, namedIncorrectValue)
            }.message shouldBe
                StringViolationFactory
                    .contains(
                        regex,
                        namedIncorrectValue.value,
                        namedIncorrectValue.name,
                    ).reason
        }

        test("not contains") {
            checkAll(
                Arb.string(1..5),
                Arb.boolean(),
            ) { substring, ignoreCase ->
                val rule = StringRules.NotContains(substring, ignoreCase)
                val namedRule = StringRules.NamedNotContains(substring, ignoreCase)

                val correctValue = "123456".replace(substring, "", ignoreCase)
                val incorrectValue = "pre${substring}post"

                val namedCorrectValue = NamedValue("name", correctValue)
                val namedIncorrectValue = NamedValue("name", incorrectValue)

                rule.runValidation(failContext, correctValue)
                namedRule.runValidation(failContext, namedCorrectValue)

                shouldFail {
                    rule.runValidation(failContext, incorrectValue)
                }.message shouldBe
                    StringViolationFactory
                        .notContains(
                            substring,
                            incorrectValue,
                        ).reason

                shouldFail {
                    namedRule.runValidation(failContext, namedIncorrectValue)
                }.message shouldBe
                    StringViolationFactory
                        .notContains(
                            substring,
                            namedIncorrectValue.value,
                            namedIncorrectValue.name,
                        ).reason
            }
        }

        test("not contains regex") {
            val regex = Regex("[0-9]+")
            val rule = StringRules.NotContainsRegex(regex)
            val namedRule = StringRules.NamedNotContainsRegex(regex)

            val correctValue = "abcdef"
            val incorrectValue = "abc123def"

            val namedCorrectValue = NamedValue("name", correctValue)
            val namedIncorrectValue = NamedValue("name", incorrectValue)

            rule.runValidation(failContext, correctValue)
            namedRule.runValidation(failContext, namedCorrectValue)

            shouldFail {
                rule.runValidation(failContext, incorrectValue)
            }.message shouldBe
                StringViolationFactory
                    .notContains(
                        regex,
                        incorrectValue,
                    ).reason

            shouldFail {
                namedRule.runValidation(failContext, namedIncorrectValue)
            }.message shouldBe
                StringViolationFactory
                    .notContains(
                        regex,
                        namedIncorrectValue.value,
                        namedIncorrectValue.name,
                    ).reason
        }

        test("contains all") {
            val chars = setOf('a', 'b', 'c')
            val rule = StringRules.ContainsAll(chars)
            val namedRule = StringRules.NamedContainsAll(chars)

            val correctValue = "abcdef"
            val incorrectValue = "abdef"

            val namedCorrectValue = NamedValue("name", correctValue)
            val namedIncorrectValue = NamedValue("name", incorrectValue)

            rule.runValidation(failContext, correctValue)
            namedRule.runValidation(failContext, namedCorrectValue)

            shouldFail {
                rule.runValidation(failContext, incorrectValue)
            }.message shouldBe
                StringViolationFactory
                    .containsAll(
                        chars,
                        incorrectValue,
                    ).reason

            shouldFail {
                namedRule.runValidation(failContext, namedIncorrectValue)
            }.message shouldBe
                StringViolationFactory
                    .containsAll(
                        chars,
                        namedIncorrectValue.value,
                        namedIncorrectValue.name,
                    ).reason
        }

        test("contains only") {
            val chars = setOf('a', 'b', 'c')
            val rule = StringRules.ContainsOnly(chars)
            val namedRule = StringRules.NamedContainsOnly(chars)

            val correctValue = "abcabc"
            val incorrectValue = "abcdef"

            val namedCorrectValue = NamedValue("name", correctValue)
            val namedIncorrectValue = NamedValue("name", incorrectValue)

            rule.runValidation(failContext, correctValue)
            namedRule.runValidation(failContext, namedCorrectValue)

            shouldFail {
                rule.runValidation(failContext, incorrectValue)
            }.message shouldBe
                StringViolationFactory
                    .containsOnly(
                        chars,
                        incorrectValue,
                    ).reason

            shouldFail {
                namedRule.runValidation(failContext, namedIncorrectValue)
            }.message shouldBe
                StringViolationFactory
                    .containsOnly(
                        chars,
                        namedIncorrectValue.value,
                        namedIncorrectValue.name,
                    ).reason
        }

        test("contains none") {
            val chars = setOf('x', 'y', 'z')
            val rule = StringRules.ContainsNone(chars)
            val namedRule = StringRules.NamedContainsNone(chars)

            val correctValue = "abcdef"
            val incorrectValue = "abcxyz"

            val namedCorrectValue = NamedValue("name", correctValue)
            val namedIncorrectValue = NamedValue("name", incorrectValue)

            rule.runValidation(failContext, correctValue)
            namedRule.runValidation(failContext, namedCorrectValue)

            shouldFail {
                rule.runValidation(failContext, incorrectValue)
            }.message shouldBe
                StringViolationFactory
                    .containsNone(
                        chars,
                        incorrectValue,
                    ).reason

            shouldFail {
                namedRule.runValidation(failContext, namedIncorrectValue)
            }.message shouldBe
                StringViolationFactory
                    .containsNone(
                        chars,
                        namedIncorrectValue.value,
                        namedIncorrectValue.name,
                    ).reason
        }

        test("matches") {
            val regex = Regex("^[a-z]{3}$")
            val rule = StringRules.Matches(regex)
            val namedRule = StringRules.NamedMatches(regex)

            val correctValue = "abc"
            val incorrectValue = "abcd"

            val namedCorrectValue = NamedValue("name", correctValue)
            val namedIncorrectValue = NamedValue("name", incorrectValue)

            rule.runValidation(failContext, correctValue)
            namedRule.runValidation(failContext, namedCorrectValue)

            shouldFail {
                rule.runValidation(failContext, incorrectValue)
            }.message shouldBe
                StringViolationFactory
                    .matches(
                        regex,
                        incorrectValue,
                    ).reason

            shouldFail {
                namedRule.runValidation(failContext, namedIncorrectValue)
            }.message shouldBe
                StringViolationFactory
                    .matches(
                        regex,
                        namedIncorrectValue.value,
                        namedIncorrectValue.name,
                    ).reason
        }

        test("not matches") {
            val regex = Regex("^[a-z]{3}$")
            val rule = StringRules.NotMatches(regex)
            val namedRule = StringRules.NamedNotMatches(regex)

            val correctValue = "abcd"
            val incorrectValue = "abc"

            val namedCorrectValue = NamedValue("name", correctValue)
            val namedIncorrectValue = NamedValue("name", incorrectValue)

            rule.runValidation(failContext, correctValue)
            namedRule.runValidation(failContext, namedCorrectValue)

            shouldFail {
                rule.runValidation(failContext, incorrectValue)
            }.message shouldBe
                StringViolationFactory
                    .notMatches(
                        regex,
                        incorrectValue,
                    ).reason

            shouldFail {
                namedRule.runValidation(failContext, namedIncorrectValue)
            }.message shouldBe
                StringViolationFactory
                    .notMatches(
                        regex,
                        namedIncorrectValue.value,
                        namedIncorrectValue.name,
                    ).reason
        }

        test("starts with") {
            checkAll(
                Arb.string(1..5),
                Arb.boolean(),
            ) { prefix, ignoreCase ->
                val rule = StringRules.StartsWith(prefix, ignoreCase)
                val namedRule = StringRules.NamedStartsWith(prefix, ignoreCase)

                val correctValue = "${prefix}suffix"
                val incorrectValue = "123456".replace(prefix, "") + prefix

                val namedCorrectValue = NamedValue("name", correctValue)
                val namedIncorrectValue = NamedValue("name", incorrectValue)

                rule.runValidation(failContext, correctValue)
                namedRule.runValidation(failContext, namedCorrectValue)

                shouldFail {
                    rule.runValidation(failContext, incorrectValue)
                }.message shouldBe
                    StringViolationFactory
                        .startsWith(
                            prefix,
                            incorrectValue,
                        ).reason

                shouldFail {
                    namedRule.runValidation(failContext, namedIncorrectValue)
                }.message shouldBe
                    StringViolationFactory
                        .startsWith(
                            prefix,
                            namedIncorrectValue.value,
                            namedIncorrectValue.name,
                        ).reason
            }
        }

        test("ends with") {
            checkAll(
                Arb.string(1..5),
                Arb.boolean(),
            ) { suffix, ignoreCase ->
                val rule = StringRules.EndsWith(suffix, ignoreCase)
                val namedRule = StringRules.NamedEndsWith(suffix, ignoreCase)

                val correctValue = "prefix$suffix"
                val incorrectValue = suffix + "123456".replace(suffix, "")

                val namedCorrectValue = NamedValue("name", correctValue)
                val namedIncorrectValue = NamedValue("name", incorrectValue)

                rule.runValidation(failContext, correctValue)
                namedRule.runValidation(failContext, namedCorrectValue)

                shouldFail {
                    rule.runValidation(failContext, incorrectValue)
                }.message shouldBe
                    StringViolationFactory
                        .endsWith(
                            suffix,
                            incorrectValue,
                        ).reason

                shouldFail {
                    namedRule.runValidation(failContext, namedIncorrectValue)
                }.message shouldBe
                    StringViolationFactory
                        .endsWith(
                            suffix,
                            namedIncorrectValue.value,
                            namedIncorrectValue.name,
                        ).reason
            }
        }

        test("alphabetic") {
            val rule = StringRules.Alphabetic()
            val namedRule = StringRules.NamedAlphabetic()

            val correctValue = "abcXYZ"
            val incorrectValue = "abc123"

            val namedCorrectValue = NamedValue("name", correctValue)
            val namedIncorrectValue = NamedValue("name", incorrectValue)

            rule.runValidation(failContext, correctValue)
            namedRule.runValidation(failContext, namedCorrectValue)

            shouldFail {
                rule.runValidation(failContext, incorrectValue)
            }.message shouldBe
                StringViolationFactory
                    .alphabetic(
                        incorrectValue,
                    ).reason

            shouldFail {
                namedRule.runValidation(failContext, namedIncorrectValue)
            }.message shouldBe
                StringViolationFactory
                    .alphabetic(
                        namedIncorrectValue.value,
                        namedIncorrectValue.name,
                    ).reason
        }

        test("numeric") {
            val rule = StringRules.Numeric()
            val namedRule = StringRules.NamedNumeric()

            val correctValue = "12345"
            val incorrectValue = "123abc"

            val namedCorrectValue = NamedValue("name", correctValue)
            val namedIncorrectValue = NamedValue("name", incorrectValue)

            rule.runValidation(failContext, correctValue)
            namedRule.runValidation(failContext, namedCorrectValue)

            shouldFail {
                rule.runValidation(failContext, incorrectValue)
            }.message shouldBe
                StringViolationFactory
                    .numeric(
                        incorrectValue,
                    ).reason

            shouldFail {
                namedRule.runValidation(failContext, namedIncorrectValue)
            }.message shouldBe
                StringViolationFactory
                    .numeric(
                        namedIncorrectValue.value,
                        namedIncorrectValue.name,
                    ).reason
        }

        test("alphanumeric") {
            val rule = StringRules.Alphanumeric()
            val namedRule = StringRules.NamedAlphanumeric()

            val correctValue = "abc123"
            val incorrectValue = "abc123!"

            val namedCorrectValue = NamedValue("name", correctValue)
            val namedIncorrectValue = NamedValue("name", incorrectValue)

            rule.runValidation(failContext, correctValue)
            namedRule.runValidation(failContext, namedCorrectValue)

            shouldFail {
                rule.runValidation(failContext, incorrectValue)
            }.message shouldBe
                StringViolationFactory
                    .alphanumeric(
                        incorrectValue,
                    ).reason

            shouldFail {
                namedRule.runValidation(failContext, namedIncorrectValue)
            }.message shouldBe
                StringViolationFactory
                    .alphanumeric(
                        namedIncorrectValue.value,
                        namedIncorrectValue.name,
                    ).reason
        }

        test("not blank") {
            val rule = StringRules.NotBlank()
            val namedRule = StringRules.NamedNotBlank()

            val correctValue = "abc"
            val incorrectValue = "   "

            val namedCorrectValue = NamedValue("name", correctValue)
            val namedIncorrectValue = NamedValue("name", incorrectValue)

            rule.runValidation(failContext, correctValue)
            namedRule.runValidation(failContext, namedCorrectValue)

            shouldFail {
                rule.runValidation(failContext, incorrectValue)
            }.message shouldBe
                StringViolationFactory
                    .notBlank(
                        incorrectValue,
                    ).reason

            shouldFail {
                namedRule.runValidation(failContext, namedIncorrectValue)
            }.message shouldBe
                StringViolationFactory
                    .notBlank(
                        namedIncorrectValue.value,
                        namedIncorrectValue.name,
                    ).reason
        }

        test("not empty") {
            val rule = StringRules.NotEmpty()
            val namedRule = StringRules.NamedNotEmpty()

            val correctValue = "abc"
            val incorrectValue = ""

            val namedCorrectValue = NamedValue("name", correctValue)
            val namedIncorrectValue = NamedValue("name", incorrectValue)

            rule.runValidation(failContext, correctValue)
            namedRule.runValidation(failContext, namedCorrectValue)

            shouldFail {
                rule.runValidation(failContext, incorrectValue)
            }.message shouldBe
                StringViolationFactory
                    .notEmpty(
                        incorrectValue,
                    ).reason

            shouldFail {
                namedRule.runValidation(failContext, namedIncorrectValue)
            }.message shouldBe
                StringViolationFactory
                    .notEmpty(
                        namedIncorrectValue.value,
                        namedIncorrectValue.name,
                    ).reason
        }

        test("lower case") {
            val rule = StringRules.LowerCase()
            val namedRule = StringRules.NamedLowerCase()

            val correctValue = "abc"
            val incorrectValue = "abC"

            val namedCorrectValue = NamedValue("name", correctValue)
            val namedIncorrectValue = NamedValue("name", incorrectValue)

            rule.runValidation(failContext, correctValue)
            namedRule.runValidation(failContext, namedCorrectValue)

            shouldFail {
                rule.runValidation(failContext, incorrectValue)
            }.message shouldBe
                StringViolationFactory
                    .lowerCase(
                        incorrectValue,
                    ).reason

            shouldFail {
                namedRule.runValidation(failContext, namedIncorrectValue)
            }.message shouldBe
                StringViolationFactory
                    .lowerCase(
                        namedIncorrectValue.value,
                        namedIncorrectValue.name,
                    ).reason
        }

        test("upper case") {
            val rule = StringRules.UpperCase()
            val namedRule = StringRules.NamedUpperCase()

            val correctValue = "ABC"
            val incorrectValue = "ABc"

            val namedCorrectValue = NamedValue("name", correctValue)
            val namedIncorrectValue = NamedValue("name", incorrectValue)

            rule.runValidation(failContext, correctValue)
            namedRule.runValidation(failContext, namedCorrectValue)

            shouldFail {
                rule.runValidation(failContext, incorrectValue)
            }.message shouldBe
                StringViolationFactory
                    .upperCase(
                        incorrectValue,
                    ).reason

            shouldFail {
                namedRule.runValidation(failContext, namedIncorrectValue)
            }.message shouldBe
                StringViolationFactory
                    .upperCase(
                        namedIncorrectValue.value,
                        namedIncorrectValue.name,
                    ).reason
        }
    })
