package io.github.kverify.rule.set.factory

import io.github.kverify.rule.set.NamedValueViolationGenerator
import io.github.kverify.rule.set.ValueViolationGenerator
import io.github.kverify.rule.set.string.NamedStringContainsAllRule
import io.github.kverify.rule.set.string.NamedStringContainsNoneRule
import io.github.kverify.rule.set.string.NamedStringContainsOnlyRule
import io.github.kverify.rule.set.string.NamedStringContainsRegexRule
import io.github.kverify.rule.set.string.NamedStringContainsRule
import io.github.kverify.rule.set.string.NamedStringEndsWithRule
import io.github.kverify.rule.set.string.NamedStringIsAlphabeticRule
import io.github.kverify.rule.set.string.NamedStringIsAlphanumericRule
import io.github.kverify.rule.set.string.NamedStringIsLowerCaseRule
import io.github.kverify.rule.set.string.NamedStringIsNotBlankRule
import io.github.kverify.rule.set.string.NamedStringIsNotEmptyRule
import io.github.kverify.rule.set.string.NamedStringIsNumericRule
import io.github.kverify.rule.set.string.NamedStringIsUpperCaseRule
import io.github.kverify.rule.set.string.NamedStringLengthBetweenRule
import io.github.kverify.rule.set.string.NamedStringLengthNotBetweenRule
import io.github.kverify.rule.set.string.NamedStringMatchesRule
import io.github.kverify.rule.set.string.NamedStringMaxLengthRule
import io.github.kverify.rule.set.string.NamedStringMinLengthRule
import io.github.kverify.rule.set.string.NamedStringNotContainsRegexRule
import io.github.kverify.rule.set.string.NamedStringNotContainsRule
import io.github.kverify.rule.set.string.NamedStringNotMatchesRule
import io.github.kverify.rule.set.string.NamedStringNotOfLengthRule
import io.github.kverify.rule.set.string.NamedStringOfLengthRule
import io.github.kverify.rule.set.string.NamedStringStartsWithRule
import io.github.kverify.rule.set.string.StringContainsAllRule
import io.github.kverify.rule.set.string.StringContainsNoneRule
import io.github.kverify.rule.set.string.StringContainsOnlyRule
import io.github.kverify.rule.set.string.StringContainsRegexRule
import io.github.kverify.rule.set.string.StringContainsRule
import io.github.kverify.rule.set.string.StringEndsWithRule
import io.github.kverify.rule.set.string.StringIsAlphabeticRule
import io.github.kverify.rule.set.string.StringIsAlphanumericRule
import io.github.kverify.rule.set.string.StringIsLowerCaseRule
import io.github.kverify.rule.set.string.StringIsNotBlankRule
import io.github.kverify.rule.set.string.StringIsNotEmptyRule
import io.github.kverify.rule.set.string.StringIsNumericRule
import io.github.kverify.rule.set.string.StringIsUpperCaseRule
import io.github.kverify.rule.set.string.StringLengthBetweenRule
import io.github.kverify.rule.set.string.StringLengthNotBetweenRule
import io.github.kverify.rule.set.string.StringMatchesRule
import io.github.kverify.rule.set.string.StringMaxLengthRule
import io.github.kverify.rule.set.string.StringMinLengthRule
import io.github.kverify.rule.set.string.StringNotContainsRegexRule
import io.github.kverify.rule.set.string.StringNotContainsRule
import io.github.kverify.rule.set.string.StringNotMatchesRule
import io.github.kverify.rule.set.string.StringNotOfLengthRule
import io.github.kverify.rule.set.string.StringOfLengthRule
import io.github.kverify.rule.set.string.StringStartsWithRule
import io.github.kverify.violation.set.provider.StringViolationProvider

private typealias StringViolationGenerator = ValueViolationGenerator<String>

private typealias NamedStringViolationGenerator = NamedValueViolationGenerator<String>

public interface StringRuleFactory {
    public val stringViolationProvider: StringViolationProvider
        get() = StringViolationProvider.Default

    // region containsAll
    public fun containsAll(
        chars: Iterable<Char>,
        violationGenerator: StringViolationGenerator = { value ->
            StringViolationProvider.Default.containsAll(
                chars = chars,
                value = value,
            )
        },
    ): StringContainsAllRule =
        StringContainsAllRule(
            chars = chars,
            violationGenerator = violationGenerator,
        )

    public fun containsAll(
        chars: Iterable<Char>,
        name: String,
    ): StringContainsAllRule =
        StringContainsAllRule(
            chars = chars,
            name = name,
        )

    public fun containsAll(
        string: String,
        violationGenerator: StringViolationGenerator = { value ->
            StringViolationProvider.Default.containsAll(
                chars = string.asIterable(),
                value = value,
            )
        },
    ): StringContainsAllRule =
        StringContainsAllRule(
            string = string,
            violationGenerator = violationGenerator,
        )

    public fun containsAll(
        string: String,
        name: String,
    ): StringContainsAllRule =
        StringContainsAllRule(
            string = string,
            name = name,
        )
    // endregion

    // region namedContainsAll
    public fun namedContainsAll(
        chars: Iterable<Char>,
        violationGenerator: NamedStringViolationGenerator = { (name, value) ->
            StringViolationProvider.Default.containsAll(
                chars = chars,
                value = value,
                name = name,
            )
        },
    ): NamedStringContainsAllRule =
        NamedStringContainsAllRule(
            chars = chars,
            violationGenerator = violationGenerator,
        )

    public fun namedContainsAll(
        string: String,
        violationGenerator: NamedStringViolationGenerator = { (name, value) ->
            StringViolationProvider.Default.containsAll(
                chars = string.asIterable(),
                value = value,
                name = name,
            )
        },
    ): NamedStringContainsAllRule =
        NamedStringContainsAllRule(
            string = string,
            violationGenerator = violationGenerator,
        )
    // endregion

    // region containsNone
    public fun containsNone(
        chars: Iterable<Char>,
        violationGenerator: StringViolationGenerator = { value ->
            StringViolationProvider.Default.containsNone(
                chars = chars,
                value = value,
            )
        },
    ): StringContainsNoneRule =
        StringContainsNoneRule(
            chars = chars,
            violationGenerator = violationGenerator,
        )

    public fun containsNone(
        string: String,
        violationGenerator: StringViolationGenerator = { value ->
            StringViolationProvider.Default.containsNone(
                chars = string.asIterable(),
                value = value,
            )
        },
    ): StringContainsNoneRule =
        StringContainsNoneRule(
            string = string,
            violationGenerator = violationGenerator,
        )
    // endregion

    // region namedContainsNone
    public fun namedContainsNone(
        chars: Iterable<Char>,
        violationGenerator: NamedStringViolationGenerator = { (name, value) ->
            StringViolationProvider.Default.containsNone(
                chars = chars,
                value = value,
                name = name,
            )
        },
    ): NamedStringContainsNoneRule =
        NamedStringContainsNoneRule(
            chars = chars,
            violationGenerator = violationGenerator,
        )

    public fun namedContainsNone(
        string: String,
        violationGenerator: NamedStringViolationGenerator = { (name, value) ->
            StringViolationProvider.Default.containsNone(
                chars = string.asIterable(),
                value = value,
                name = name,
            )
        },
    ): NamedStringContainsNoneRule =
        NamedStringContainsNoneRule(
            string = string,
            violationGenerator = violationGenerator,
        )
    // endregion

    // region containsOnly
    public fun containsOnly(
        chars: Iterable<Char>,
        violationGenerator: StringViolationGenerator = { value ->
            StringViolationProvider.Default.containsOnly(
                chars = chars,
                value = value,
            )
        },
    ): StringContainsOnlyRule =
        StringContainsOnlyRule(
            chars = chars,
            violationGenerator = violationGenerator,
        )

    public fun containsOnly(
        chars: Iterable<Char>,
        name: String,
    ): StringContainsOnlyRule =
        StringContainsOnlyRule(
            chars = chars,
            name = name,
        )

    public fun containsOnly(
        string: String,
        violationGenerator: StringViolationGenerator = { value ->
            StringViolationProvider.Default.containsOnly(
                chars = string.asIterable(),
                value = value,
            )
        },
    ): StringContainsOnlyRule =
        StringContainsOnlyRule(
            string = string,
            violationGenerator = violationGenerator,
        )

    public fun containsOnly(
        string: String,
        name: String,
    ): StringContainsOnlyRule =
        StringContainsOnlyRule(
            string = string,
            name = name,
        )
    // endregion

    // region namedContainsOnly
    public fun namedContainsOnly(
        chars: Iterable<Char>,
        violationGenerator: NamedStringViolationGenerator = { (name, value) ->
            StringViolationProvider.Default.containsOnly(
                chars = chars,
                value = value,
                name = name,
            )
        },
    ): NamedStringContainsOnlyRule =
        NamedStringContainsOnlyRule(
            chars = chars,
            violationGenerator = violationGenerator,
        )

    public fun namedContainsOnly(
        string: String,
        violationGenerator: NamedStringViolationGenerator = { (name, value) ->
            StringViolationProvider.Default.containsOnly(
                chars = string.asIterable(),
                value = value,
                name = name,
            )
        },
    ): NamedStringContainsOnlyRule =
        NamedStringContainsOnlyRule(
            string = string,
            violationGenerator = violationGenerator,
        )
    // endregion

    // region containsRegex
    public fun containsRegex(
        regex: Regex,
        violationGenerator: StringViolationGenerator = { value ->
            StringViolationProvider.Default.containsRegex(
                regex = regex,
                value = value,
            )
        },
    ): StringContainsRegexRule =
        StringContainsRegexRule(
            regex = regex,
            violationGenerator = violationGenerator,
        )

    public fun containsRegex(
        regex: Regex,
        name: String,
    ): StringContainsRegexRule =
        StringContainsRegexRule(
            regex = regex,
            name = name,
        )

    public fun containsRegex(
        stringRegex: String,
        violationGenerator: StringViolationGenerator = { value ->
            StringViolationProvider.Default.containsRegex(
                regex = stringRegex.toRegex(),
                value = value,
            )
        },
    ): StringContainsRegexRule =
        StringContainsRegexRule(
            stringRegex = stringRegex,
            violationGenerator = violationGenerator,
        )

    public fun containsRegex(
        stringRegex: String,
        name: String,
    ): StringContainsRegexRule =
        StringContainsRegexRule(
            regex = stringRegex.toRegex(),
            name = name,
        )
    // endregion

    // region namedContainsRegex
    public fun namedContainsRegex(
        regex: Regex,
        violationGenerator: NamedStringViolationGenerator = { (name, value) ->
            StringViolationProvider.Default.containsRegex(
                regex = regex,
                value = value,
                name = name,
            )
        },
    ): NamedStringContainsRegexRule =
        NamedStringContainsRegexRule(
            regex = regex,
            violationGenerator = violationGenerator,
        )

    public fun namedContainsRegex(
        stringRegex: String,
        violationGenerator: NamedStringViolationGenerator = { (name, value) ->
            StringViolationProvider.Default.containsRegex(
                regex = stringRegex.toRegex(),
                value = value,
                name = name,
            )
        },
    ): NamedStringContainsRegexRule =
        NamedStringContainsRegexRule(
            stringRegex = stringRegex,
            violationGenerator = violationGenerator,
        )
    // endregion

    // region contains
    public fun contains(
        string: String,
        ignoreCase: Boolean = false,
        violationGenerator: StringViolationGenerator = { value ->
            StringViolationProvider.Default.contains(
                value = value,
                string = string,
            )
        },
    ): StringContainsRule =
        StringContainsRule(
            string = string,
            violationGenerator = violationGenerator,
            ignoreCase = ignoreCase,
        )

    public fun contains(
        string: String,
        name: String,
        ignoreCase: Boolean = false,
    ): StringContainsRule =
        StringContainsRule(
            string = string,
            name = name,
            ignoreCase = ignoreCase,
        )
    // endregion

    // region namedContains
    public fun namedContains(
        string: String,
        ignoreCase: Boolean = false,
        violationGenerator: NamedStringViolationGenerator = { (name, value) ->
            StringViolationProvider.Default.contains(
                value = value,
                string = string,
                name = name,
            )
        },
    ): NamedStringContainsRule =
        NamedStringContainsRule(
            string = string,
            violationGenerator = violationGenerator,
            ignoreCase = ignoreCase,
        )
    // endregion

    // region endsWith
    public fun endsWith(
        suffix: String,
        ignoreCase: Boolean = false,
        violationGenerator: StringViolationGenerator = { value ->
            StringViolationProvider.Default.endsWith(
                value = value,
                suffix = suffix,
            )
        },
    ): StringEndsWithRule =
        StringEndsWithRule(
            suffix = suffix,
            violationGenerator = violationGenerator,
            ignoreCase = ignoreCase,
        )

    public fun endsWith(
        suffix: String,
        name: String,
        ignoreCase: Boolean = false,
    ): StringEndsWithRule =
        StringEndsWithRule(
            suffix = suffix,
            name = name,
            ignoreCase = ignoreCase,
        )
    // endregion

    // region namedEndsWith
    public fun namedEndsWith(
        suffix: String,
        ignoreCase: Boolean = false,
        violationGenerator: NamedStringViolationGenerator = { (name, value) ->
            StringViolationProvider.Default.endsWith(
                value = value,
                suffix = suffix,
                name = name,
            )
        },
    ): NamedStringEndsWithRule =
        NamedStringEndsWithRule(
            suffix = suffix,
            violationGenerator = violationGenerator,
            ignoreCase = ignoreCase,
        )
    // endregion

    // region isAlphabetic
    public fun isAlphabetic(
        violationGenerator: StringViolationGenerator = { value ->
            StringViolationProvider.Default.isAlphabetic(value = value)
        },
    ): StringIsAlphabeticRule =
        StringIsAlphabeticRule(
            violationGenerator = violationGenerator,
        )

    public fun isAlphabetic(name: String): StringIsAlphabeticRule =
        StringIsAlphabeticRule(
            name = name,
        )
    // endregion

    // region namedIsAlphabetic
    public fun namedIsAlphabetic(
        violationGenerator: NamedStringViolationGenerator = { (name, value) ->
            StringViolationProvider.Default.isAlphabetic(value = value, name = name)
        },
    ): NamedStringIsAlphabeticRule =
        NamedStringIsAlphabeticRule(
            violationGenerator = violationGenerator,
        )
    // endregion

    // region isAlphanumeric
    public fun isAlphanumeric(
        violationGenerator: StringViolationGenerator = { value ->
            StringViolationProvider.Default.isAlphanumeric(value = value)
        },
    ): StringIsAlphanumericRule =
        StringIsAlphanumericRule(
            violationGenerator = violationGenerator,
        )

    public fun isAlphanumeric(name: String): StringIsAlphanumericRule =
        StringIsAlphanumericRule(
            name = name,
        )
    // endregion

    // region namedIsAlphanumeric
    public fun namedIsAlphanumeric(
        violationGenerator: NamedStringViolationGenerator = { (name, value) ->
            StringViolationProvider.Default.isAlphanumeric(value = value, name = name)
        },
    ): NamedStringIsAlphanumericRule =
        NamedStringIsAlphanumericRule(
            violationGenerator = violationGenerator,
        )
    // endregion

    // region isLowerCase
    public fun isLowerCase(
        violationGenerator: StringViolationGenerator = { value ->
            StringViolationProvider.Default.isLowerCase(value = value)
        },
    ): StringIsLowerCaseRule =
        StringIsLowerCaseRule(
            violationGenerator = violationGenerator,
        )

    public fun isLowerCase(name: String): StringIsLowerCaseRule =
        StringIsLowerCaseRule(
            name = name,
        )
    // endregion

    // region namedIsLowerCase
    public fun namedIsLowerCase(
        violationGenerator: NamedStringViolationGenerator = { (name, value) ->
            StringViolationProvider.Default.isLowerCase(value = value, name = name)
        },
    ): NamedStringIsLowerCaseRule =
        NamedStringIsLowerCaseRule(
            violationGenerator = violationGenerator,
        )
    // endregion

    // region isNotBlank
    public fun isNotBlank(
        violationGenerator: StringViolationGenerator = { value ->
            StringViolationProvider.Default.isNotBlank(
                value = value,
            )
        },
    ): StringIsNotBlankRule =
        StringIsNotBlankRule(
            violationGenerator = violationGenerator,
        )

    public fun isNotBlank(name: String): StringIsNotBlankRule =
        StringIsNotBlankRule(
            name = name,
        )
    // endregion

    // region namedIsNotBlank
    public fun namedIsNotBlank(
        violationGenerator: NamedStringViolationGenerator = { (name, value) ->
            StringViolationProvider.Default.isNotBlank(
                value = value,
                name = name,
            )
        },
    ): NamedStringIsNotBlankRule =
        NamedStringIsNotBlankRule(
            violationGenerator = violationGenerator,
        )
    // endregion

    // region isNotEmpty
    public fun isNotEmpty(
        violationGenerator: StringViolationGenerator = { value ->
            StringViolationProvider.Default.isNotEmpty(
                value = value,
            )
        },
    ): StringIsNotEmptyRule =
        StringIsNotEmptyRule(
            violationGenerator = violationGenerator,
        )

    public fun isNotEmpty(name: String): StringIsNotEmptyRule =
        StringIsNotEmptyRule(
            name = name,
        )
    // endregion

    // region namedIsNotEmpty
    public fun namedIsNotEmpty(
        violationGenerator: NamedStringViolationGenerator = { (name, value) ->
            StringViolationProvider.Default.isNotEmpty(
                value = value,
                name = name,
            )
        },
    ): NamedStringIsNotEmptyRule =
        NamedStringIsNotEmptyRule(
            violationGenerator = violationGenerator,
        )
    // endregion

    // region isNumeric
    public fun isNumeric(
        violationGenerator: StringViolationGenerator = { value ->
            StringViolationProvider.Default.isNumeric(
                value = value,
            )
        },
    ): StringIsNumericRule =
        StringIsNumericRule(
            violationGenerator = violationGenerator,
        )

    public fun isNumeric(name: String): StringIsNumericRule =
        StringIsNumericRule(
            name = name,
        )
    // endregion

    // region namedIsNumeric
    public fun namedIsNumeric(
        violationGenerator: NamedStringViolationGenerator = { (name, value) ->
            StringViolationProvider.Default.isNumeric(
                value = value,
                name = name,
            )
        },
    ): NamedStringIsNumericRule =
        NamedStringIsNumericRule(
            violationGenerator = violationGenerator,
        )
    // endregion

    // region isUpperCase
    public fun isUpperCase(
        violationGenerator: StringViolationGenerator = { value ->
            StringViolationProvider.Default.isUpperCase(value = value)
        },
    ): StringIsUpperCaseRule =
        StringIsUpperCaseRule(
            violationGenerator = violationGenerator,
        )

    public fun isUpperCase(name: String): StringIsUpperCaseRule =
        StringIsUpperCaseRule(
            name = name,
        )
    // endregion

    // region namedIsUpperCase
    public fun namedIsUpperCase(
        violationGenerator: NamedStringViolationGenerator = { (name, value) ->
            StringViolationProvider.Default.isUpperCase(value = value, name = name)
        },
    ): NamedStringIsUpperCaseRule =
        NamedStringIsUpperCaseRule(
            violationGenerator = violationGenerator,
        )
    // endregion

    // region lengthBetween
    public fun lengthBetween(
        range: IntRange,
        violationGenerator: StringViolationGenerator = { value ->
            StringViolationProvider.Default.lengthBetween(
                value = value,
                range = range,
            )
        },
    ): StringLengthBetweenRule =
        StringLengthBetweenRule(
            range = range,
            violationGenerator = violationGenerator,
        )

    public fun lengthBetween(
        range: IntRange,
        name: String,
    ): StringLengthBetweenRule =
        StringLengthBetweenRule(
            range = range,
            name = name,
        )

    public fun lengthBetween(
        min: Int,
        max: Int,
        violationGenerator: StringViolationGenerator = { value ->
            StringViolationProvider.Default.lengthBetween(
                value = value,
                range = min..max,
            )
        },
    ): StringLengthBetweenRule =
        StringLengthBetweenRule(
            min = min,
            max = max,
            violationGenerator = violationGenerator,
        )

    public fun lengthBetween(
        min: Int,
        max: Int,
        name: String,
    ): StringLengthBetweenRule =
        StringLengthBetweenRule(
            min = min,
            max = max,
            name = name,
        )
    // endregion

    // region namedLengthBetween
    public fun namedLengthBetween(
        range: IntRange,
        violationGenerator: NamedStringViolationGenerator = { (name, value) ->
            StringViolationProvider.Default.lengthBetween(
                value = value,
                range = range,
                name = name,
            )
        },
    ): NamedStringLengthBetweenRule =
        NamedStringLengthBetweenRule(
            range = range,
            violationGenerator = violationGenerator,
        )

    public fun namedLengthBetween(
        min: Int,
        max: Int,
        violationGenerator: NamedStringViolationGenerator = { (name, value) ->
            StringViolationProvider.Default.lengthBetween(
                value = value,
                range = min..max,
                name = name,
            )
        },
    ): NamedStringLengthBetweenRule =
        NamedStringLengthBetweenRule(
            min = min,
            max = max,
            violationGenerator = violationGenerator,
        )
    // endregion

    // region lengthNotBetween
    public fun lengthNotBetween(
        range: IntRange,
        violationGenerator: StringViolationGenerator = { value ->
            StringViolationProvider.Default.lengthNotBetween(
                value = value,
                range = range,
            )
        },
    ): StringLengthNotBetweenRule =
        StringLengthNotBetweenRule(
            range = range,
            violationGenerator = violationGenerator,
        )

    public fun lengthNotBetween(
        range: IntRange,
        name: String,
    ): StringLengthNotBetweenRule =
        StringLengthNotBetweenRule(
            range = range,
            name = name,
        )

    public fun lengthNotBetween(
        min: Int,
        max: Int,
        violationGenerator: StringViolationGenerator = { value ->
            StringViolationProvider.Default.lengthNotBetween(
                value = value,
                range = min..max,
            )
        },
    ): StringLengthNotBetweenRule =
        StringLengthNotBetweenRule(
            min = min,
            max = max,
            violationGenerator = violationGenerator,
        )

    public fun lengthNotBetween(
        min: Int,
        max: Int,
        name: String,
    ): StringLengthNotBetweenRule =
        StringLengthNotBetweenRule(
            min = min,
            max = max,
            name = name,
        )
    // endregion

    // region namedLengthNotBetween
    public fun namedLengthNotBetween(
        range: IntRange,
        violationGenerator: NamedStringViolationGenerator = { (name, value) ->
            StringViolationProvider.Default.lengthNotBetween(
                value = value,
                range = range,
                name = name,
            )
        },
    ): NamedStringLengthNotBetweenRule =
        NamedStringLengthNotBetweenRule(
            range = range,
            violationGenerator = violationGenerator,
        )

    public fun namedLengthNotBetween(
        min: Int,
        max: Int,
        violationGenerator: NamedStringViolationGenerator = { (name, value) ->
            StringViolationProvider.Default.lengthNotBetween(
                value = value,
                range = min..max,
                name = name,
            )
        },
    ): NamedStringLengthNotBetweenRule =
        NamedStringLengthNotBetweenRule(
            min = min,
            max = max,
            violationGenerator = violationGenerator,
        )
    // endregion

    // region matches
    public fun matches(
        regex: Regex,
        violationGenerator: StringViolationGenerator = { value ->
            StringViolationProvider.Default.matches(
                value = value,
                regex = regex,
            )
        },
    ): StringMatchesRule =
        StringMatchesRule(
            regex = regex,
            violationGenerator = violationGenerator,
        )

    public fun matches(
        regex: Regex,
        name: String,
    ): StringMatchesRule =
        StringMatchesRule(
            regex = regex,
            name = name,
        )

    public fun matches(
        stringRegex: String,
        violationGenerator: StringViolationGenerator = { value ->
            StringViolationProvider.Default.matches(
                value = value,
                regex = stringRegex.toRegex(),
            )
        },
    ): StringMatchesRule =
        StringMatchesRule(
            stringRegex = stringRegex,
            violationGenerator = violationGenerator,
        )

    public fun matches(
        stringRegex: String,
        name: String,
    ): StringMatchesRule =
        StringMatchesRule(
            stringRegex = stringRegex,
            name = name,
        )
    // endregion

    // region namedMatches
    public fun namedMatches(
        regex: Regex,
        violationGenerator: NamedStringViolationGenerator = { (name, value) ->
            StringViolationProvider.Default.matches(
                value = value,
                regex = regex,
                name = name,
            )
        },
    ): NamedStringMatchesRule =
        NamedStringMatchesRule(
            regex = regex,
            violationGenerator = violationGenerator,
        )

    public fun namedMatches(
        stringRegex: String,
        violationGenerator: NamedStringViolationGenerator = { (name, value) ->
            StringViolationProvider.Default.matches(
                value = value,
                regex = stringRegex.toRegex(),
                name = name,
            )
        },
    ): NamedStringMatchesRule =
        NamedStringMatchesRule(
            stringRegex = stringRegex,
            violationGenerator = violationGenerator,
        )
    // endregion

    // region maxLength
    public fun maxLength(
        max: Int,
        violationGenerator: StringViolationGenerator = { value ->
            StringViolationProvider.Default.maxLength(
                value = value,
                max = max,
            )
        },
    ): StringMaxLengthRule =
        StringMaxLengthRule(
            max = max,
            violationGenerator = violationGenerator,
        )

    public fun maxLength(
        max: Int,
        name: String,
    ): StringMaxLengthRule =
        StringMaxLengthRule(
            max = max,
            name = name,
        )
    // endregion

    // region namedMaxLength
    public fun namedMaxLength(
        max: Int,
        violationGenerator: NamedStringViolationGenerator = { (name, value) ->
            StringViolationProvider.Default.maxLength(
                value = value,
                max = max,
                name = name,
            )
        },
    ): NamedStringMaxLengthRule =
        NamedStringMaxLengthRule(
            max = max,
            violationGenerator = violationGenerator,
        )
    // endregion

    // region minLength
    public fun minLength(
        min: Int,
        violationGenerator: StringViolationGenerator = { value ->
            StringViolationProvider.Default.minLength(
                value = value,
                min = min,
            )
        },
    ): StringMinLengthRule =
        StringMinLengthRule(
            min = min,
            violationGenerator = violationGenerator,
        )

    public fun minLength(
        min: Int,
        name: String,
    ): StringMinLengthRule =
        StringMinLengthRule(
            min = min,
            name = name,
        )
    // endregion

    // region namedMinLength
    public fun namedMinLength(
        min: Int,
        violationGenerator: NamedStringViolationGenerator = { (name, value) ->
            StringViolationProvider.Default.minLength(
                value = value,
                min = min,
                name = name,
            )
        },
    ): NamedStringMinLengthRule =
        NamedStringMinLengthRule(
            min = min,
            violationGenerator = violationGenerator,
        )
    // endregion

    // region notContainsRegex
    public fun notContainsRegex(
        regex: Regex,
        violationGenerator: StringViolationGenerator = { value ->
            StringViolationProvider.Default.notContainsRegex(
                value = value,
                regex = regex,
            )
        },
    ): StringNotContainsRegexRule =
        StringNotContainsRegexRule(
            regex = regex,
            violationGenerator = violationGenerator,
        )

    public fun notContainsRegex(
        regex: Regex,
        name: String,
    ): StringNotContainsRegexRule =
        StringNotContainsRegexRule(
            regex = regex,
            name = name,
        )

    public fun notContainsRegex(
        stringRegex: String,
        violationGenerator: StringViolationGenerator = { value ->
            StringViolationProvider.Default.notContainsRegex(
                value = value,
                regex = stringRegex.toRegex(),
            )
        },
    ): StringNotContainsRegexRule =
        StringNotContainsRegexRule(
            stringRegex = stringRegex,
            violationGenerator = violationGenerator,
        )

    public fun notContainsRegex(
        stringRegex: String,
        name: String,
    ): StringNotContainsRegexRule =
        StringNotContainsRegexRule(
            stringRegex = stringRegex,
            name = name,
        )
    // endregion

    // region namedNotContainsRegex
    public fun namedNotContainsRegex(
        regex: Regex,
        violationGenerator: NamedStringViolationGenerator = { (name, value) ->
            StringViolationProvider.Default.notContainsRegex(
                value = value,
                regex = regex,
                name = name,
            )
        },
    ): NamedStringNotContainsRegexRule =
        NamedStringNotContainsRegexRule(
            regex = regex,
            violationGenerator = violationGenerator,
        )

    public fun namedNotContainsRegex(
        stringRegex: String,
        violationGenerator: NamedStringViolationGenerator = { (name, value) ->
            StringViolationProvider.Default.notContainsRegex(
                value = value,
                regex = stringRegex.toRegex(),
                name = name,
            )
        },
    ): NamedStringNotContainsRegexRule =
        NamedStringNotContainsRegexRule(
            stringRegex = stringRegex,
            violationGenerator = violationGenerator,
        )
    // endregion

    // region notContains
    public fun notContains(
        subString: String,
        ignoreCase: Boolean = false,
        violationGenerator: StringViolationGenerator = { value ->
            StringViolationProvider.Default.notContains(
                value = value,
                subString = subString,
            )
        },
    ): StringNotContainsRule =
        StringNotContainsRule(
            subString = subString,
            ignoreCase = ignoreCase,
            violationGenerator = violationGenerator,
        )

    public fun notContains(
        subString: String,
        name: String,
        ignoreCase: Boolean = false,
    ): StringNotContainsRule =
        StringNotContainsRule(
            subString = subString,
            name = name,
            ignoreCase = ignoreCase,
        )
    // endregion

    // region namedNotContains
    public fun namedNotContains(
        subString: String,
        ignoreCase: Boolean = false,
        violationGenerator: NamedStringViolationGenerator = { (name, value) ->
            StringViolationProvider.Default.notContains(
                value = value,
                subString = subString,
                name = name,
            )
        },
    ): NamedStringNotContainsRule =
        NamedStringNotContainsRule(
            subString = subString,
            ignoreCase = ignoreCase,
            violationGenerator = violationGenerator,
        )
    // endregion

    // region notMatches
    public fun notMatches(
        regex: Regex,
        violationGenerator: StringViolationGenerator = { value ->
            StringViolationProvider.Default.notMatches(
                value = value,
                regex = regex,
            )
        },
    ): StringNotMatchesRule =
        StringNotMatchesRule(
            regex = regex,
            violationGenerator = violationGenerator,
        )

    public fun notMatches(
        regex: Regex,
        name: String,
    ): StringNotMatchesRule =
        StringNotMatchesRule(
            regex = regex,
            name = name,
        )

    public fun notMatches(
        stringRegex: String,
        violationGenerator: StringViolationGenerator = { value ->
            StringViolationProvider.Default.notMatches(
                value = value,
                regex = stringRegex.toRegex(),
            )
        },
    ): StringNotMatchesRule =
        StringNotMatchesRule(
            stringRegex = stringRegex,
            violationGenerator = violationGenerator,
        )

    public fun notMatches(
        stringRegex: String,
        name: String,
    ): StringNotMatchesRule =
        StringNotMatchesRule(
            stringRegex = stringRegex,
            name = name,
        )
    // endregion

    // region namedNotMatches
    public fun namedNotMatches(
        regex: Regex,
        violationGenerator: NamedStringViolationGenerator = { (name, value) ->
            StringViolationProvider.Default.notMatches(
                value = value,
                regex = regex,
                name = name,
            )
        },
    ): NamedStringNotMatchesRule =
        NamedStringNotMatchesRule(
            regex = regex,
            violationGenerator = violationGenerator,
        )

    public fun namedNotMatches(
        stringRegex: String,
        violationGenerator: NamedStringViolationGenerator = { (name, value) ->
            StringViolationProvider.Default.notMatches(
                value = value,
                regex = stringRegex.toRegex(),
                name = name,
            )
        },
    ): NamedStringNotMatchesRule =
        NamedStringNotMatchesRule(
            stringRegex = stringRegex,
            violationGenerator = violationGenerator,
        )
    // endregion

    // region notOfLength
    public fun notOfLength(
        length: Int,
        violationGenerator: StringViolationGenerator = { value ->
            StringViolationProvider.Default.notOfLength(
                value = value,
                length = length,
            )
        },
    ): StringNotOfLengthRule =
        StringNotOfLengthRule(
            length = length,
            violationGenerator = violationGenerator,
        )

    public fun notOfLength(
        length: Int,
        name: String,
    ): StringNotOfLengthRule =
        StringNotOfLengthRule(
            length = length,
            name = name,
        )
    // endregion

    // region namedNotOfLength
    public fun namedNotOfLength(
        length: Int,
        violationGenerator: NamedStringViolationGenerator = { (name, value) ->
            StringViolationProvider.Default.notOfLength(
                value = value,
                length = length,
                name = name,
            )
        },
    ): NamedStringNotOfLengthRule =
        NamedStringNotOfLengthRule(
            length = length,
            violationGenerator = violationGenerator,
        )
    // endregion

    // region ofLength
    public fun ofLength(
        length: Int,
        violationGenerator: StringViolationGenerator = { value ->
            StringViolationProvider.Default.ofLength(
                value = value,
                length = length,
            )
        },
    ): StringOfLengthRule =
        StringOfLengthRule(
            length = length,
            violationGenerator = violationGenerator,
        )

    public fun ofLength(
        length: Int,
        name: String,
    ): StringOfLengthRule =
        StringOfLengthRule(
            length = length,
            name = name,
        )
    // endregion

    // region namedOfLength
    public fun namedOfLength(
        length: Int,
        violationGenerator: NamedStringViolationGenerator = { (name, value) ->
            StringViolationProvider.Default.ofLength(
                value = value,
                length = length,
                name = name,
            )
        },
    ): NamedStringOfLengthRule =
        NamedStringOfLengthRule(
            length = length,
            violationGenerator = violationGenerator,
        )
    // endregion

    // region startsWith
    public fun startsWith(
        prefix: String,
        ignoreCase: Boolean = false,
        violationGenerator: StringViolationGenerator = { value ->
            StringViolationProvider.Default.startsWith(
                value = value,
                prefix = prefix,
            )
        },
    ): StringStartsWithRule =
        StringStartsWithRule(
            prefix = prefix,
            ignoreCase = ignoreCase,
            violationGenerator = violationGenerator,
        )

    public fun startsWith(
        prefix: String,
        name: String,
        ignoreCase: Boolean = false,
    ): StringStartsWithRule =
        StringStartsWithRule(
            prefix = prefix,
            name = name,
            ignoreCase = ignoreCase,
        )
    // endregion

    // region namedStartsWith
    public fun namedStartsWith(
        prefix: String,
        ignoreCase: Boolean = false,
        violationGenerator: NamedStringViolationGenerator = { (name, value) ->
            StringViolationProvider.Default.startsWith(
                value = value,
                prefix = prefix,
                name = name,
            )
        },
    ): NamedStringStartsWithRule =
        NamedStringStartsWithRule(
            prefix = prefix,
            ignoreCase = ignoreCase,
            violationGenerator = violationGenerator,
        )
    // endregion
}

public open class StringRules(
    public override val stringViolationProvider: StringViolationProvider,
) : StringRuleFactory {
    public companion object : StringRules(
        stringViolationProvider = StringViolationProvider.Default,
    )
}
