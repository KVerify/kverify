package io.github.kverify.rule.set

import io.github.kverify.core.context.ValidationContext
import io.github.kverify.core.model.NamedValue
import io.github.kverify.core.rule.runValidation
import io.github.kverify.rule.set.factory.StringRules
import io.github.kverify.violation.set.provider.StringViolationProvider
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
                val rule = StringRules.ofLength(length)
                val namedRule = StringRules.namedOfLength(length)

                val correctValue = "a".repeat(length)
                val incorrectValue = "a".repeat(length + 1)

                val namedCorrectValue = NamedValue("name", correctValue)
                val namedIncorrectValue = NamedValue("name", incorrectValue)

                rule.runValidation(failContext, correctValue)
                namedRule.runValidation(failContext, namedCorrectValue)

                shouldFail {
                    rule.runValidation(failContext, incorrectValue)
                }.message shouldBe
                    StringViolationProvider
                        .ofLength(
                            length,
                            incorrectValue,
                        ).reason

                shouldFail {
                    namedRule.runValidation(failContext, namedIncorrectValue)
                }.message shouldBe
                    StringViolationProvider
                        .ofLength(
                            length,
                            namedIncorrectValue.value,
                            namedIncorrectValue.name,
                        ).reason
            }
        }

        test("not of length") {
            Arb.int(1..10).checkAll { length ->
                val rule = StringRules.notOfLength(length)
                val namedRule = StringRules.namedNotOfLength(length)

                val correctValue = "a".repeat(length + 1)
                val incorrectValue = "a".repeat(length)

                val namedCorrectValue = NamedValue("name", correctValue)
                val namedIncorrectValue = NamedValue("name", incorrectValue)

                rule.runValidation(failContext, correctValue)
                namedRule.runValidation(failContext, namedCorrectValue)

                shouldFail {
                    rule.runValidation(failContext, incorrectValue)
                }.message shouldBe
                    StringViolationProvider
                        .notOfLength(
                            length,
                            incorrectValue,
                        ).reason

                shouldFail {
                    namedRule.runValidation(failContext, namedIncorrectValue)
                }.message shouldBe
                    StringViolationProvider
                        .notOfLength(
                            length,
                            namedIncorrectValue.value,
                            namedIncorrectValue.name,
                        ).reason
            }
        }

        test("max length") {
            Arb.int(5..10).checkAll { max ->
                val rule = StringRules.maxLength(max)
                val namedRule = StringRules.namedMaxLength(max)

                val correctValue = "a".repeat(max)
                val incorrectValue = "a".repeat(max + 1)

                val namedCorrectValue = NamedValue("name", correctValue)
                val namedIncorrectValue = NamedValue("name", incorrectValue)

                rule.runValidation(failContext, correctValue)
                namedRule.runValidation(failContext, namedCorrectValue)

                shouldFail {
                    rule.runValidation(failContext, incorrectValue)
                }.message shouldBe
                    StringViolationProvider
                        .maxLength(
                            max,
                            incorrectValue,
                        ).reason

                shouldFail {
                    namedRule.runValidation(failContext, namedIncorrectValue)
                }.message shouldBe
                    StringViolationProvider
                        .maxLength(
                            max,
                            namedIncorrectValue.value,
                            namedIncorrectValue.name,
                        ).reason
            }
        }

        test("min length") {
            Arb.int(5..10).checkAll { min ->
                val rule = StringRules.minLength(min)
                val namedRule = StringRules.namedMinLength(min)

                val correctValue = "a".repeat(min)
                val incorrectValue = "a".repeat(min - 1)

                val namedCorrectValue = NamedValue("name", correctValue)
                val namedIncorrectValue = NamedValue("name", incorrectValue)

                rule.runValidation(failContext, correctValue)
                namedRule.runValidation(failContext, namedCorrectValue)

                shouldFail {
                    rule.runValidation(failContext, incorrectValue)
                }.message shouldBe
                    StringViolationProvider
                        .minLength(
                            min,
                            incorrectValue,
                        ).reason

                shouldFail {
                    namedRule.runValidation(failContext, namedIncorrectValue)
                }.message shouldBe
                    StringViolationProvider
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
                val rule = StringRules.lengthBetween(range)
                val namedRule = StringRules.namedLengthBetween(range)

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
                    StringViolationProvider
                        .lengthBetween(
                            range,
                            incorrectValueTooShort,
                        ).reason

                shouldFail {
                    rule.runValidation(failContext, incorrectValueTooLong)
                }.message shouldBe
                    StringViolationProvider
                        .lengthBetween(
                            range,
                            incorrectValueTooLong,
                        ).reason

                shouldFail {
                    namedRule.runValidation(failContext, namedIncorrectValueTooShort)
                }.message shouldBe
                    StringViolationProvider
                        .lengthBetween(
                            range,
                            namedIncorrectValueTooShort.value,
                            namedIncorrectValueTooShort.name,
                        ).reason

                shouldFail {
                    namedRule.runValidation(failContext, namedIncorrectValueTooLong)
                }.message shouldBe
                    StringViolationProvider
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
                val rule = StringRules.lengthNotBetween(range)
                val namedRule = StringRules.namedLengthNotBetween(range)

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
                    StringViolationProvider
                        .lengthNotBetween(
                            range,
                            incorrectValue,
                        ).reason

                shouldFail {
                    namedRule.runValidation(failContext, namedIncorrectValue)
                }.message shouldBe
                    StringViolationProvider
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
                val rule = StringRules.contains(substring, ignoreCase)
                val namedRule = StringRules.namedContains(substring, ignoreCase)

                val correctValue = "pre${substring}post"
                val incorrectValue = "123456".replace(substring, "", ignoreCase)

                val namedCorrectValue = NamedValue("name", correctValue)
                val namedIncorrectValue = NamedValue("name", incorrectValue)

                rule.runValidation(failContext, correctValue)
                namedRule.runValidation(failContext, namedCorrectValue)

                shouldFail {
                    rule.runValidation(failContext, incorrectValue)
                }.message shouldBe
                    StringViolationProvider
                        .contains(
                            substring,
                            incorrectValue,
                        ).reason

                shouldFail {
                    namedRule.runValidation(failContext, namedIncorrectValue)
                }.message shouldBe
                    StringViolationProvider
                        .contains(
                            substring,
                            namedIncorrectValue.value,
                            namedIncorrectValue.name,
                        ).reason
            }
        }

        test("contains regex") {
            val regex = Regex("[0-9]+")
            val rule = StringRules.containsRegex(regex)
            val namedRule = StringRules.namedContainsRegex(regex)

            val correctValue = "abc123def"
            val incorrectValue = "abcdef"

            val namedCorrectValue = NamedValue("name", correctValue)
            val namedIncorrectValue = NamedValue("name", incorrectValue)

            rule.runValidation(failContext, correctValue)
            namedRule.runValidation(failContext, namedCorrectValue)

            shouldFail {
                rule.runValidation(failContext, incorrectValue)
            }.message shouldBe
                StringViolationProvider
                    .containsRegex(
                        regex,
                        incorrectValue,
                    ).reason

            shouldFail {
                namedRule.runValidation(failContext, namedIncorrectValue)
            }.message shouldBe
                StringViolationProvider
                    .containsRegex(
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
                val rule = StringRules.notContains(substring, ignoreCase)
                val namedRule = StringRules.namedNotContains(substring, ignoreCase)

                val correctValue = "123456".replace(substring, "", ignoreCase)
                val incorrectValue = "pre${substring}post"

                val namedCorrectValue = NamedValue("name", correctValue)
                val namedIncorrectValue = NamedValue("name", incorrectValue)

                rule.runValidation(failContext, correctValue)
                namedRule.runValidation(failContext, namedCorrectValue)

                shouldFail {
                    rule.runValidation(failContext, incorrectValue)
                }.message shouldBe
                    StringViolationProvider
                        .notContains(
                            substring,
                            incorrectValue,
                        ).reason

                shouldFail {
                    namedRule.runValidation(failContext, namedIncorrectValue)
                }.message shouldBe
                    StringViolationProvider
                        .notContains(
                            substring,
                            namedIncorrectValue.value,
                            namedIncorrectValue.name,
                        ).reason
            }
        }

        test("not contains regex") {
            val regex = Regex("[0-9]+")
            val rule = StringRules.notContainsRegex(regex)
            val namedRule = StringRules.namedNotContainsRegex(regex)

            val correctValue = "abcdef"
            val incorrectValue = "abc123def"

            val namedCorrectValue = NamedValue("name", correctValue)
            val namedIncorrectValue = NamedValue("name", incorrectValue)

            rule.runValidation(failContext, correctValue)
            namedRule.runValidation(failContext, namedCorrectValue)

            shouldFail {
                rule.runValidation(failContext, incorrectValue)
            }.message shouldBe
                StringViolationProvider
                    .notContainsRegex(
                        regex,
                        incorrectValue,
                    ).reason

            shouldFail {
                namedRule.runValidation(failContext, namedIncorrectValue)
            }.message shouldBe
                StringViolationProvider
                    .notContainsRegex(
                        regex,
                        namedIncorrectValue.value,
                        namedIncorrectValue.name,
                    ).reason
        }

        test("contains all") {
            val chars = setOf('a', 'b', 'c')
            val rule = StringRules.containsAll(chars)
            val namedRule = StringRules.namedContainsAll(chars)

            val correctValue = "abcdef"
            val incorrectValue = "abdef"

            val namedCorrectValue = NamedValue("name", correctValue)
            val namedIncorrectValue = NamedValue("name", incorrectValue)

            rule.runValidation(failContext, correctValue)
            namedRule.runValidation(failContext, namedCorrectValue)

            shouldFail {
                rule.runValidation(failContext, incorrectValue)
            }.message shouldBe
                StringViolationProvider
                    .containsAll(
                        chars,
                        incorrectValue,
                    ).reason

            shouldFail {
                namedRule.runValidation(failContext, namedIncorrectValue)
            }.message shouldBe
                StringViolationProvider
                    .containsAll(
                        chars,
                        namedIncorrectValue.value,
                        namedIncorrectValue.name,
                    ).reason
        }

        test("contains only") {
            val chars = setOf('a', 'b', 'c')
            val rule = StringRules.containsOnly(chars)
            val namedRule = StringRules.namedContainsOnly(chars)

            val correctValue = "abcabc"
            val incorrectValue = "abcdef"

            val namedCorrectValue = NamedValue("name", correctValue)
            val namedIncorrectValue = NamedValue("name", incorrectValue)

            rule.runValidation(failContext, correctValue)
            namedRule.runValidation(failContext, namedCorrectValue)

            shouldFail {
                rule.runValidation(failContext, incorrectValue)
            }.message shouldBe
                StringViolationProvider
                    .containsOnly(
                        chars,
                        incorrectValue,
                    ).reason

            shouldFail {
                namedRule.runValidation(failContext, namedIncorrectValue)
            }.message shouldBe
                StringViolationProvider
                    .containsOnly(
                        chars,
                        namedIncorrectValue.value,
                        namedIncorrectValue.name,
                    ).reason
        }

        test("contains none") {
            val chars = setOf('x', 'y', 'z')
            val rule = StringRules.containsNone(chars)
            val namedRule = StringRules.namedContainsNone(chars)

            val correctValue = "abcdef"
            val incorrectValue = "abcxyz"

            val namedCorrectValue = NamedValue("name", correctValue)
            val namedIncorrectValue = NamedValue("name", incorrectValue)

            rule.runValidation(failContext, correctValue)
            namedRule.runValidation(failContext, namedCorrectValue)

            shouldFail {
                rule.runValidation(failContext, incorrectValue)
            }.message shouldBe
                StringViolationProvider
                    .containsNone(
                        chars,
                        incorrectValue,
                    ).reason

            shouldFail {
                namedRule.runValidation(failContext, namedIncorrectValue)
            }.message shouldBe
                StringViolationProvider
                    .containsNone(
                        chars,
                        namedIncorrectValue.value,
                        namedIncorrectValue.name,
                    ).reason
        }

        test("matches") {
            val regex = Regex("^[a-z]{3}$")
            val rule = StringRules.matches(regex)
            val namedRule = StringRules.namedMatches(regex)

            val correctValue = "abc"
            val incorrectValue = "abcd"

            val namedCorrectValue = NamedValue("name", correctValue)
            val namedIncorrectValue = NamedValue("name", incorrectValue)

            rule.runValidation(failContext, correctValue)
            namedRule.runValidation(failContext, namedCorrectValue)

            shouldFail {
                rule.runValidation(failContext, incorrectValue)
            }.message shouldBe
                StringViolationProvider
                    .matches(
                        regex,
                        incorrectValue,
                    ).reason

            shouldFail {
                namedRule.runValidation(failContext, namedIncorrectValue)
            }.message shouldBe
                StringViolationProvider
                    .matches(
                        regex,
                        namedIncorrectValue.value,
                        namedIncorrectValue.name,
                    ).reason
        }

        test("not matches") {
            val regex = Regex("^[a-z]{3}$")
            val rule = StringRules.notMatches(regex)
            val namedRule = StringRules.namedNotMatches(regex)

            val correctValue = "abcd"
            val incorrectValue = "abc"

            val namedCorrectValue = NamedValue("name", correctValue)
            val namedIncorrectValue = NamedValue("name", incorrectValue)

            rule.runValidation(failContext, correctValue)
            namedRule.runValidation(failContext, namedCorrectValue)

            shouldFail {
                rule.runValidation(failContext, incorrectValue)
            }.message shouldBe
                StringViolationProvider
                    .notMatches(
                        regex,
                        incorrectValue,
                    ).reason

            shouldFail {
                namedRule.runValidation(failContext, namedIncorrectValue)
            }.message shouldBe
                StringViolationProvider
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
                val rule = StringRules.startsWith(prefix, ignoreCase)
                val namedRule = StringRules.namedStartsWith(prefix, ignoreCase)

                val correctValue = "${prefix}suffix"
                val incorrectValue = "123456".replace(prefix, "") + prefix

                val namedCorrectValue = NamedValue("name", correctValue)
                val namedIncorrectValue = NamedValue("name", incorrectValue)

                rule.runValidation(failContext, correctValue)
                namedRule.runValidation(failContext, namedCorrectValue)

                shouldFail {
                    rule.runValidation(failContext, incorrectValue)
                }.message shouldBe
                    StringViolationProvider
                        .startsWith(
                            prefix,
                            incorrectValue,
                        ).reason

                shouldFail {
                    namedRule.runValidation(failContext, namedIncorrectValue)
                }.message shouldBe
                    StringViolationProvider
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
                val rule = StringRules.endsWith(suffix, ignoreCase)
                val namedRule = StringRules.namedEndsWith(suffix, ignoreCase)

                val correctValue = "prefix$suffix"
                val incorrectValue = suffix + "123456".replace(suffix, "")

                val namedCorrectValue = NamedValue("name", correctValue)
                val namedIncorrectValue = NamedValue("name", incorrectValue)

                rule.runValidation(failContext, correctValue)
                namedRule.runValidation(failContext, namedCorrectValue)

                shouldFail {
                    rule.runValidation(failContext, incorrectValue)
                }.message shouldBe
                    StringViolationProvider
                        .endsWith(
                            suffix,
                            incorrectValue,
                        ).reason

                shouldFail {
                    namedRule.runValidation(failContext, namedIncorrectValue)
                }.message shouldBe
                    StringViolationProvider
                        .endsWith(
                            suffix,
                            namedIncorrectValue.value,
                            namedIncorrectValue.name,
                        ).reason
            }
        }

        test("is alphabetic") {
            val rule = StringRules.isAlphabetic()
            val namedRule = StringRules.namedIsAlphabetic()

            val correctValue = "abcXYZ"
            val incorrectValue = "abc123"

            val namedCorrectValue = NamedValue("name", correctValue)
            val namedIncorrectValue = NamedValue("name", incorrectValue)

            rule.runValidation(failContext, correctValue)
            namedRule.runValidation(failContext, namedCorrectValue)

            shouldFail {
                rule.runValidation(failContext, incorrectValue)
            }.message shouldBe
                StringViolationProvider
                    .isAlphabetic(
                        incorrectValue,
                    ).reason

            shouldFail {
                namedRule.runValidation(failContext, namedIncorrectValue)
            }.message shouldBe
                StringViolationProvider
                    .isAlphabetic(
                        namedIncorrectValue.value,
                        namedIncorrectValue.name,
                    ).reason
        }

        test("is numeric") {
            val rule = StringRules.isNumeric()
            val namedRule = StringRules.namedIsNumeric()

            val correctValue = "12345"
            val incorrectValue = "123abc"

            val namedCorrectValue = NamedValue("name", correctValue)
            val namedIncorrectValue = NamedValue("name", incorrectValue)

            rule.runValidation(failContext, correctValue)
            namedRule.runValidation(failContext, namedCorrectValue)

            shouldFail {
                rule.runValidation(failContext, incorrectValue)
            }.message shouldBe
                StringViolationProvider
                    .isNumeric(
                        incorrectValue,
                    ).reason

            shouldFail {
                namedRule.runValidation(failContext, namedIncorrectValue)
            }.message shouldBe
                StringViolationProvider
                    .isNumeric(
                        namedIncorrectValue.value,
                        namedIncorrectValue.name,
                    ).reason
        }

        test("is alphanumeric") {
            val rule = StringRules.isAlphanumeric()
            val namedRule = StringRules.namedIsAlphanumeric()

            val correctValue = "abc123"
            val incorrectValue = "abc123!"

            val namedCorrectValue = NamedValue("name", correctValue)
            val namedIncorrectValue = NamedValue("name", incorrectValue)

            rule.runValidation(failContext, correctValue)
            namedRule.runValidation(failContext, namedCorrectValue)

            shouldFail {
                rule.runValidation(failContext, incorrectValue)
            }.message shouldBe
                StringViolationProvider
                    .isAlphanumeric(
                        incorrectValue,
                    ).reason

            shouldFail {
                namedRule.runValidation(failContext, namedIncorrectValue)
            }.message shouldBe
                StringViolationProvider
                    .isAlphanumeric(
                        namedIncorrectValue.value,
                        namedIncorrectValue.name,
                    ).reason
        }

        test("is not blank") {
            val rule = StringRules.isNotBlank()
            val namedRule = StringRules.namedIsNotBlank()

            val correctValue = "abc"
            val incorrectValue = "   "

            val namedCorrectValue = NamedValue("name", correctValue)
            val namedIncorrectValue = NamedValue("name", incorrectValue)

            rule.runValidation(failContext, correctValue)
            namedRule.runValidation(failContext, namedCorrectValue)

            shouldFail {
                rule.runValidation(failContext, incorrectValue)
            }.message shouldBe
                StringViolationProvider
                    .isNotBlank(
                        incorrectValue,
                    ).reason

            shouldFail {
                namedRule.runValidation(failContext, namedIncorrectValue)
            }.message shouldBe
                StringViolationProvider
                    .isNotBlank(
                        namedIncorrectValue.value,
                        namedIncorrectValue.name,
                    ).reason
        }

        test("is not empty") {
            val rule = StringRules.isNotEmpty()
            val namedRule = StringRules.namedIsNotEmpty()

            val correctValue = "abc"
            val incorrectValue = ""

            val namedCorrectValue = NamedValue("name", correctValue)
            val namedIncorrectValue = NamedValue("name", incorrectValue)

            rule.runValidation(failContext, correctValue)
            namedRule.runValidation(failContext, namedCorrectValue)

            shouldFail {
                rule.runValidation(failContext, incorrectValue)
            }.message shouldBe
                StringViolationProvider
                    .isNotEmpty(
                        incorrectValue,
                    ).reason

            shouldFail {
                namedRule.runValidation(failContext, namedIncorrectValue)
            }.message shouldBe
                StringViolationProvider
                    .isNotEmpty(
                        namedIncorrectValue.value,
                        namedIncorrectValue.name,
                    ).reason
        }

        test("is lower case") {
            val rule = StringRules.isLowerCase()
            val namedRule = StringRules.namedIsLowerCase()

            val correctValue = "abc"
            val incorrectValue = "abC"

            val namedCorrectValue = NamedValue("name", correctValue)
            val namedIncorrectValue = NamedValue("name", incorrectValue)

            rule.runValidation(failContext, correctValue)
            namedRule.runValidation(failContext, namedCorrectValue)

            shouldFail {
                rule.runValidation(failContext, incorrectValue)
            }.message shouldBe
                StringViolationProvider
                    .isLowerCase(
                        incorrectValue,
                    ).reason

            shouldFail {
                namedRule.runValidation(failContext, namedIncorrectValue)
            }.message shouldBe
                StringViolationProvider
                    .isLowerCase(
                        namedIncorrectValue.value,
                        namedIncorrectValue.name,
                    ).reason
        }

        test("is upper case") {
            val rule = StringRules.isUpperCase()
            val namedRule = StringRules.namedIsUpperCase()

            val correctValue = "ABC"
            val incorrectValue = "ABc"

            val namedCorrectValue = NamedValue("name", correctValue)
            val namedIncorrectValue = NamedValue("name", incorrectValue)

            rule.runValidation(failContext, correctValue)
            namedRule.runValidation(failContext, namedCorrectValue)

            shouldFail {
                rule.runValidation(failContext, incorrectValue)
            }.message shouldBe
                StringViolationProvider
                    .isUpperCase(
                        incorrectValue,
                    ).reason

            shouldFail {
                namedRule.runValidation(failContext, namedIncorrectValue)
            }.message shouldBe
                StringViolationProvider
                    .isUpperCase(
                        namedIncorrectValue.value,
                        namedIncorrectValue.name,
                    ).reason
        }
    })
