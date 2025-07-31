package io.github.kverify.rule.set

import io.github.kverify.core.context.ValidationContext
import io.github.kverify.core.rule.runValidation
import io.github.kverify.named.model.NamedValue
import io.github.kverify.rule.set.provider.StringRules
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
                    StringViolationProvider.Default
                        .ofLength(
                            incorrectValue,
                            length,
                        ).reason

                shouldFail {
                    namedRule.runValidation(failContext, namedIncorrectValue)
                }.message shouldBe
                    StringViolationProvider.Default
                        .ofLength(
                            namedIncorrectValue.value,
                            length,
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
                    StringViolationProvider.Default
                        .notOfLength(
                            incorrectValue,
                            length,
                        ).reason

                shouldFail {
                    namedRule.runValidation(failContext, namedIncorrectValue)
                }.message shouldBe
                    StringViolationProvider.Default
                        .notOfLength(
                            namedIncorrectValue.value,
                            length,
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
                    StringViolationProvider.Default
                        .maxLength(
                            incorrectValue,
                            max,
                        ).reason

                shouldFail {
                    namedRule.runValidation(failContext, namedIncorrectValue)
                }.message shouldBe
                    StringViolationProvider.Default
                        .maxLength(
                            namedIncorrectValue.value,
                            max,
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
                    StringViolationProvider.Default
                        .minLength(
                            incorrectValue,
                            min,
                        ).reason

                shouldFail {
                    namedRule.runValidation(failContext, namedIncorrectValue)
                }.message shouldBe
                    StringViolationProvider.Default
                        .minLength(
                            namedIncorrectValue.value,
                            min,
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
                    StringViolationProvider.Default
                        .lengthBetween(
                            incorrectValueTooShort,
                            range,
                        ).reason

                shouldFail {
                    rule.runValidation(failContext, incorrectValueTooLong)
                }.message shouldBe
                    StringViolationProvider.Default
                        .lengthBetween(
                            incorrectValueTooLong,
                            range,
                        ).reason

                shouldFail {
                    namedRule.runValidation(failContext, namedIncorrectValueTooShort)
                }.message shouldBe
                    StringViolationProvider.Default
                        .lengthBetween(
                            namedIncorrectValueTooShort.value,
                            range,
                            namedIncorrectValueTooShort.name,
                        ).reason

                shouldFail {
                    namedRule.runValidation(failContext, namedIncorrectValueTooLong)
                }.message shouldBe
                    StringViolationProvider.Default
                        .lengthBetween(
                            namedIncorrectValueTooLong.value,
                            range,
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
                    StringViolationProvider.Default
                        .lengthNotBetween(
                            incorrectValue,
                            range,
                        ).reason

                shouldFail {
                    namedRule.runValidation(failContext, namedIncorrectValue)
                }.message shouldBe
                    StringViolationProvider.Default
                        .lengthNotBetween(
                            namedIncorrectValue.value,
                            range,
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
                    StringViolationProvider.Default
                        .contains(
                            incorrectValue,
                            substring,
                        ).reason

                shouldFail {
                    namedRule.runValidation(failContext, namedIncorrectValue)
                }.message shouldBe
                    StringViolationProvider.Default
                        .contains(
                            namedIncorrectValue.value,
                            substring,
                            name = namedIncorrectValue.name,
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
                StringViolationProvider.Default
                    .containsRegex(
                        incorrectValue,
                        regex,
                    ).reason

            shouldFail {
                namedRule.runValidation(failContext, namedIncorrectValue)
            }.message shouldBe
                StringViolationProvider.Default
                    .containsRegex(
                        namedIncorrectValue.value,
                        regex,
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
                    StringViolationProvider.Default
                        .notContains(
                            incorrectValue,
                            substring,
                        ).reason

                shouldFail {
                    namedRule.runValidation(failContext, namedIncorrectValue)
                }.message shouldBe
                    StringViolationProvider.Default
                        .notContains(
                            namedIncorrectValue.value,
                            substring,
                            name = namedIncorrectValue.name,
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
                StringViolationProvider.Default
                    .notContainsRegex(
                        incorrectValue,
                        regex,
                    ).reason

            shouldFail {
                namedRule.runValidation(failContext, namedIncorrectValue)
            }.message shouldBe
                StringViolationProvider.Default
                    .notContainsRegex(
                        namedIncorrectValue.value,
                        regex,
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
                StringViolationProvider.Default
                    .containsAll(
                        incorrectValue,
                        chars,
                    ).reason

            shouldFail {
                namedRule.runValidation(failContext, namedIncorrectValue)
            }.message shouldBe
                StringViolationProvider.Default
                    .containsAll(
                        namedIncorrectValue.value,
                        chars,
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
                StringViolationProvider.Default
                    .containsOnly(
                        incorrectValue,
                        chars,
                    ).reason

            shouldFail {
                namedRule.runValidation(failContext, namedIncorrectValue)
            }.message shouldBe
                StringViolationProvider.Default
                    .containsOnly(
                        namedIncorrectValue.value,
                        chars,
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
                StringViolationProvider.Default
                    .containsNone(
                        incorrectValue,
                        chars,
                    ).reason

            shouldFail {
                namedRule.runValidation(failContext, namedIncorrectValue)
            }.message shouldBe
                StringViolationProvider.Default
                    .containsNone(
                        namedIncorrectValue.value,
                        chars,
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
                StringViolationProvider.Default
                    .matches(
                        incorrectValue,
                        regex,
                    ).reason

            shouldFail {
                namedRule.runValidation(failContext, namedIncorrectValue)
            }.message shouldBe
                StringViolationProvider.Default
                    .matches(
                        namedIncorrectValue.value,
                        regex,
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
                StringViolationProvider.Default
                    .notMatches(
                        incorrectValue,
                        regex,
                    ).reason

            shouldFail {
                namedRule.runValidation(failContext, namedIncorrectValue)
            }.message shouldBe
                StringViolationProvider.Default
                    .notMatches(
                        namedIncorrectValue.value,
                        regex,
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
                    StringViolationProvider.Default
                        .startsWith(
                            incorrectValue,
                            prefix,
                        ).reason

                shouldFail {
                    namedRule.runValidation(failContext, namedIncorrectValue)
                }.message shouldBe
                    StringViolationProvider.Default
                        .startsWith(
                            namedIncorrectValue.value,
                            prefix,
                            name = namedIncorrectValue.name,
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
                    StringViolationProvider.Default
                        .endsWith(
                            suffix,
                            incorrectValue,
                        ).reason

                shouldFail {
                    namedRule.runValidation(failContext, namedIncorrectValue)
                }.message shouldBe
                    StringViolationProvider.Default
                        .endsWith(
                            namedIncorrectValue.value,
                            suffix,
                            name = namedIncorrectValue.name,
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
                StringViolationProvider.Default
                    .alphabetic(
                        incorrectValue,
                    ).reason

            shouldFail {
                namedRule.runValidation(failContext, namedIncorrectValue)
            }.message shouldBe
                StringViolationProvider.Default
                    .alphabetic(
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
                StringViolationProvider.Default
                    .numeric(
                        incorrectValue,
                    ).reason

            shouldFail {
                namedRule.runValidation(failContext, namedIncorrectValue)
            }.message shouldBe
                StringViolationProvider.Default
                    .numeric(
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
                StringViolationProvider.Default
                    .alphanumeric(
                        incorrectValue,
                    ).reason

            shouldFail {
                namedRule.runValidation(failContext, namedIncorrectValue)
            }.message shouldBe
                StringViolationProvider.Default
                    .alphanumeric(
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
                StringViolationProvider.Default
                    .notBlank(
                        incorrectValue,
                    ).reason

            shouldFail {
                namedRule.runValidation(failContext, namedIncorrectValue)
            }.message shouldBe
                StringViolationProvider.Default
                    .notBlank(
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
                StringViolationProvider.Default
                    .notEmpty(
                        incorrectValue,
                    ).reason

            shouldFail {
                namedRule.runValidation(failContext, namedIncorrectValue)
            }.message shouldBe
                StringViolationProvider.Default
                    .notEmpty(
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
                StringViolationProvider.Default
                    .lowerCase(
                        incorrectValue,
                    ).reason

            shouldFail {
                namedRule.runValidation(failContext, namedIncorrectValue)
            }.message shouldBe
                StringViolationProvider.Default
                    .lowerCase(
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
                StringViolationProvider.Default
                    .upperCase(
                        incorrectValue,
                    ).reason

            shouldFail {
                namedRule.runValidation(failContext, namedIncorrectValue)
            }.message shouldBe
                StringViolationProvider.Default
                    .upperCase(
                        namedIncorrectValue.value,
                        namedIncorrectValue.name,
                    ).reason
        }
    })
