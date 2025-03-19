package io.github.kverify.rule.set

import io.github.kverify.core.context.ValidationContext
import io.github.kverify.core.model.NamedValue
import io.github.kverify.core.model.runValidation
import io.github.kverify.rule.set.violation.StringViolations
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
        val failContext = ValidationContext { fail(it.message) }

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
                    StringViolations
                        .ofLength(
                            length,
                            incorrectValue,
                        ).message

                shouldFail {
                    namedRule.runValidation(failContext, namedIncorrectValue)
                }.message shouldBe
                    StringViolations
                        .ofLength(
                            length,
                            namedIncorrectValue.value,
                            namedIncorrectValue.name,
                        ).message
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
                    StringViolations
                        .notOfLength(
                            length,
                            incorrectValue,
                        ).message

                shouldFail {
                    namedRule.runValidation(failContext, namedIncorrectValue)
                }.message shouldBe
                    StringViolations
                        .notOfLength(
                            length,
                            namedIncorrectValue.value,
                            namedIncorrectValue.name,
                        ).message
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
                    StringViolations
                        .maxLength(
                            max,
                            incorrectValue,
                        ).message

                shouldFail {
                    namedRule.runValidation(failContext, namedIncorrectValue)
                }.message shouldBe
                    StringViolations
                        .maxLength(
                            max,
                            namedIncorrectValue.value,
                            namedIncorrectValue.name,
                        ).message
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
                    StringViolations
                        .minLength(
                            min,
                            incorrectValue,
                        ).message

                shouldFail {
                    namedRule.runValidation(failContext, namedIncorrectValue)
                }.message shouldBe
                    StringViolations
                        .minLength(
                            min,
                            namedIncorrectValue.value,
                            namedIncorrectValue.name,
                        ).message
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
                    StringViolations
                        .lengthBetween(
                            range,
                            incorrectValueTooShort,
                        ).message

                shouldFail {
                    rule.runValidation(failContext, incorrectValueTooLong)
                }.message shouldBe
                    StringViolations
                        .lengthBetween(
                            range,
                            incorrectValueTooLong,
                        ).message

                shouldFail {
                    namedRule.runValidation(failContext, namedIncorrectValueTooShort)
                }.message shouldBe
                    StringViolations
                        .lengthBetween(
                            range,
                            namedIncorrectValueTooShort.value,
                            namedIncorrectValueTooShort.name,
                        ).message

                shouldFail {
                    namedRule.runValidation(failContext, namedIncorrectValueTooLong)
                }.message shouldBe
                    StringViolations
                        .lengthBetween(
                            range,
                            namedIncorrectValueTooLong.value,
                            namedIncorrectValueTooLong.name,
                        ).message
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
                    StringViolations
                        .lengthNotBetween(
                            range,
                            incorrectValue,
                        ).message

                shouldFail {
                    namedRule.runValidation(failContext, namedIncorrectValue)
                }.message shouldBe
                    StringViolations
                        .lengthNotBetween(
                            range,
                            namedIncorrectValue.value,
                            namedIncorrectValue.name,
                        ).message
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
                    StringViolations
                        .contains(
                            substring,
                            incorrectValue,
                        ).message

                shouldFail {
                    namedRule.runValidation(failContext, namedIncorrectValue)
                }.message shouldBe
                    StringViolations
                        .contains(
                            substring,
                            namedIncorrectValue.value,
                            namedIncorrectValue.name,
                        ).message
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
                StringViolations
                    .contains(
                        regex,
                        incorrectValue,
                    ).message

            shouldFail {
                namedRule.runValidation(failContext, namedIncorrectValue)
            }.message shouldBe
                StringViolations
                    .contains(
                        regex,
                        namedIncorrectValue.value,
                        namedIncorrectValue.name,
                    ).message
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
                    StringViolations
                        .notContains(
                            substring,
                            incorrectValue,
                        ).message

                shouldFail {
                    namedRule.runValidation(failContext, namedIncorrectValue)
                }.message shouldBe
                    StringViolations
                        .notContains(
                            substring,
                            namedIncorrectValue.value,
                            namedIncorrectValue.name,
                        ).message
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
                StringViolations
                    .notContains(
                        regex,
                        incorrectValue,
                    ).message

            shouldFail {
                namedRule.runValidation(failContext, namedIncorrectValue)
            }.message shouldBe
                StringViolations
                    .notContains(
                        regex,
                        namedIncorrectValue.value,
                        namedIncorrectValue.name,
                    ).message
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
                StringViolations
                    .containsAll(
                        chars,
                        incorrectValue,
                    ).message

            shouldFail {
                namedRule.runValidation(failContext, namedIncorrectValue)
            }.message shouldBe
                StringViolations
                    .containsAll(
                        chars,
                        namedIncorrectValue.value,
                        namedIncorrectValue.name,
                    ).message
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
                StringViolations
                    .containsOnly(
                        chars,
                        incorrectValue,
                    ).message

            shouldFail {
                namedRule.runValidation(failContext, namedIncorrectValue)
            }.message shouldBe
                StringViolations
                    .containsOnly(
                        chars,
                        namedIncorrectValue.value,
                        namedIncorrectValue.name,
                    ).message
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
                StringViolations
                    .containsNone(
                        chars,
                        incorrectValue,
                    ).message

            shouldFail {
                namedRule.runValidation(failContext, namedIncorrectValue)
            }.message shouldBe
                StringViolations
                    .containsNone(
                        chars,
                        namedIncorrectValue.value,
                        namedIncorrectValue.name,
                    ).message
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
                StringViolations
                    .matches(
                        regex,
                        incorrectValue,
                    ).message

            shouldFail {
                namedRule.runValidation(failContext, namedIncorrectValue)
            }.message shouldBe
                StringViolations
                    .matches(
                        regex,
                        namedIncorrectValue.value,
                        namedIncorrectValue.name,
                    ).message
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
                StringViolations
                    .notMatches(
                        regex,
                        incorrectValue,
                    ).message

            shouldFail {
                namedRule.runValidation(failContext, namedIncorrectValue)
            }.message shouldBe
                StringViolations
                    .notMatches(
                        regex,
                        namedIncorrectValue.value,
                        namedIncorrectValue.name,
                    ).message
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
                    StringViolations
                        .startsWith(
                            prefix,
                            incorrectValue,
                        ).message

                shouldFail {
                    namedRule.runValidation(failContext, namedIncorrectValue)
                }.message shouldBe
                    StringViolations
                        .startsWith(
                            prefix,
                            namedIncorrectValue.value,
                            namedIncorrectValue.name,
                        ).message
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
                    StringViolations
                        .endsWith(
                            suffix,
                            incorrectValue,
                        ).message

                shouldFail {
                    namedRule.runValidation(failContext, namedIncorrectValue)
                }.message shouldBe
                    StringViolations
                        .endsWith(
                            suffix,
                            namedIncorrectValue.value,
                            namedIncorrectValue.name,
                        ).message
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
                StringViolations
                    .alphabetic(
                        incorrectValue,
                    ).message

            shouldFail {
                namedRule.runValidation(failContext, namedIncorrectValue)
            }.message shouldBe
                StringViolations
                    .alphabetic(
                        namedIncorrectValue.value,
                        namedIncorrectValue.name,
                    ).message
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
                StringViolations
                    .numeric(
                        incorrectValue,
                    ).message

            shouldFail {
                namedRule.runValidation(failContext, namedIncorrectValue)
            }.message shouldBe
                StringViolations
                    .numeric(
                        namedIncorrectValue.value,
                        namedIncorrectValue.name,
                    ).message
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
                StringViolations
                    .alphanumeric(
                        incorrectValue,
                    ).message

            shouldFail {
                namedRule.runValidation(failContext, namedIncorrectValue)
            }.message shouldBe
                StringViolations
                    .alphanumeric(
                        namedIncorrectValue.value,
                        namedIncorrectValue.name,
                    ).message
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
                StringViolations
                    .notBlank(
                        incorrectValue,
                    ).message

            shouldFail {
                namedRule.runValidation(failContext, namedIncorrectValue)
            }.message shouldBe
                StringViolations
                    .notBlank(
                        namedIncorrectValue.value,
                        namedIncorrectValue.name,
                    ).message
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
                StringViolations
                    .notEmpty(
                        incorrectValue,
                    ).message

            shouldFail {
                namedRule.runValidation(failContext, namedIncorrectValue)
            }.message shouldBe
                StringViolations
                    .notEmpty(
                        namedIncorrectValue.value,
                        namedIncorrectValue.name,
                    ).message
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
                StringViolations
                    .lowerCase(
                        incorrectValue,
                    ).message

            shouldFail {
                namedRule.runValidation(failContext, namedIncorrectValue)
            }.message shouldBe
                StringViolations
                    .lowerCase(
                        namedIncorrectValue.value,
                        namedIncorrectValue.name,
                    ).message
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
                StringViolations
                    .upperCase(
                        incorrectValue,
                    ).message

            shouldFail {
                namedRule.runValidation(failContext, namedIncorrectValue)
            }.message shouldBe
                StringViolations
                    .upperCase(
                        namedIncorrectValue.value,
                        namedIncorrectValue.name,
                    ).message
        }
    })
