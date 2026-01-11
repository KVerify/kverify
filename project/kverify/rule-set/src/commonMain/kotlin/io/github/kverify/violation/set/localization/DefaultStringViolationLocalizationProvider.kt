package io.github.kverify.violation.set.localization

@Suppress("TooManyFunctions")
internal object DefaultStringViolationLocalizationProvider : StringViolationLocalizationProvider {
    override fun alphabetic(
        value: String,
        name: String?,
    ): String {
        val displayName = resolveStringName(name)
        val unexpectedChars =
            value
                .asIterable()
                .filterNot { it.isLetter() }
                .joinWithLimitAndBrackets()

        return "$displayName must contain only letters, but also contains: $unexpectedChars."
    }

    override fun alphanumeric(
        value: String,
        name: String?,
    ): String {
        val displayName = resolveStringName(name)
        val unexpectedChars =
            value
                .asIterable()
                .filterNot { it.isLetterOrDigit() }
                .joinWithLimitAndBrackets()

        return "$displayName must contain only letters and digits, but also contains: $unexpectedChars."
    }

    override fun containsAll(
        value: String,
        chars: Iterable<Char>,
        name: String?,
    ): String {
        val displayName = resolveStringName(name)
        val charsAsString = chars.joinWithLimitAndBrackets()
        val missingChars =
            value
                .asIterable()
                .filterNot { it in chars }
                .joinWithLimitAndBrackets()

        return "$displayName must contain all characters: $charsAsString, but missing: $missingChars."
    }

    override fun containsNone(
        value: String,
        chars: Iterable<Char>,
        name: String?,
    ): String {
        val displayName = resolveStringName(name)
        val charsAsString = chars.joinWithLimitAndBrackets()
        val forbiddenChars =
            value
                .asIterable()
                .filter { it in chars }
                .joinWithLimitAndBrackets()

        return "$displayName must not contain characters: $charsAsString, but found: $forbiddenChars."
    }

    override fun containsOnly(
        value: String,
        chars: Iterable<Char>,
        name: String?,
    ): String {
        val displayName = resolveStringName(name)
        val allowedChars = chars.joinWithLimitAndBrackets()
        val unexpectedChars =
            value
                .asIterable()
                .filterNot { it in chars }
                .joinWithLimitAndBrackets()

        return "$displayName must contain only characters: $allowedChars, but also contains: $unexpectedChars."
    }

    override fun containsRegex(
        value: String,
        regex: Regex,
        name: String?,
    ): String {
        val displayName = resolveStringName(name)

        return "$displayName must contain regex pattern: '${regex.pattern}'."
    }

    override fun contains(
        value: String,
        substring: String,
        ignoreCase: Boolean,
        name: String?,
    ): String {
        val displayName = resolveStringName(name)

        return "$displayName must contain substring (ignoreCase=$ignoreCase): '$substring'."
    }

    override fun endsWith(
        value: String,
        suffix: String,
        ignoreCase: Boolean,
        name: String?,
    ): String {
        val displayName = resolveStringName(name)

        return "$displayName must end with suffix (ignoreCase=$ignoreCase): '$suffix'."
    }

    override fun lengthBetween(
        value: String,
        lengthRange: IntRange,
        name: String?,
    ): String {
        val displayName = resolveStringName(name)

        return "$displayName length must be between ${lengthRange.start} and ${lengthRange.endInclusive}, but is ${value.length}."
    }

    override fun lengthNotBetween(
        value: String,
        lengthRange: IntRange,
        name: String?,
    ): String {
        val displayName = resolveStringName(name)

        return "$displayName length must NOT be between ${lengthRange.start} and ${lengthRange.endInclusive}, but is ${value.length}."
    }

    override fun lowerCase(
        value: String,
        name: String?,
    ): String {
        val displayName = resolveStringName(name)
        val upperCaseChars =
            value
                .asIterable()
                .filterNot { it.isLowerCase() }
                .joinWithLimitAndBrackets()

        return "$displayName must be lowercase, but also contains uppercase: $upperCaseChars."
    }

    override fun matches(
        value: String,
        regex: Regex,
        name: String?,
    ): String {
        val displayName = resolveStringName(name)

        return "$displayName must match regex: '${regex.pattern}'."
    }

    override fun maxLength(
        value: String,
        maxLength: Int,
        name: String?,
    ): String {
        val displayName = resolveStringName(name)

        return "$displayName length must be at most $maxLength, but is ${value.length}."
    }

    override fun minLength(
        value: String,
        minLength: Int,
        name: String?,
    ): String {
        val displayName = resolveStringName(name)

        return "$displayName length must be at least $minLength, but is ${value.length}."
    }

    override fun notBlank(
        value: String,
        name: String?,
    ): String {
        val displayName = resolveStringName(name)

        return "$displayName must not be blank."
    }

    override fun notContains(
        value: String,
        substring: String,
        ignoreCase: Boolean,
        name: String?,
    ): String {
        val displayName = resolveStringName(name)

        return "$displayName must NOT contain substring (ignoreCase=$ignoreCase): '$substring'."
    }

    override fun notContainsRegex(
        value: String,
        regex: Regex,
        name: String?,
    ): String {
        val displayName = resolveStringName(name)

        return "$displayName must NOT contain regex pattern: '${regex.pattern}'."
    }

    override fun notEmpty(
        value: String,
        name: String?,
    ): String {
        val displayName = resolveStringName(name)

        return "$displayName must not be empty."
    }

    override fun notMatches(
        value: String,
        regex: Regex,
        name: String?,
    ): String {
        val displayName = resolveStringName(name)

        return "$displayName must NOT match regex: '${regex.pattern}'."
    }

    override fun notOfLength(
        value: String,
        length: Int,
        name: String?,
    ): String {
        val displayName = resolveStringName(name)

        return "$displayName length must NOT be $length."
    }

    override fun numeric(
        value: String,
        name: String?,
    ): String {
        val displayName = resolveStringName(name)
        val unexpectedChars =
            value
                .asIterable()
                .filterNot { it.isDigit() }
                .joinWithLimitAndBrackets()

        return "$displayName must contain only digits, but also contains: $unexpectedChars."
    }

    override fun ofLength(
        value: String,
        length: Int,
        name: String?,
    ): String {
        val displayName = resolveStringName(name)

        return "$displayName length must be exactly $length, but is ${value.length}."
    }

    override fun startsWith(
        value: String,
        prefix: String,
        ignoreCase: Boolean,
        name: String?,
    ): String {
        val displayName = resolveStringName(name)

        return "$displayName must start with prefix (ignoreCase=$ignoreCase): '$prefix'."
    }

    override fun upperCase(
        value: String,
        name: String?,
    ): String {
        val displayName = resolveStringName(name)
        val lowercaseChars =
            value
                .asIterable()
                .filter { it.isLowerCase() }
                .joinWithLimitAndBrackets()

        return "$displayName must be uppercase, but also contains lowercase: $lowercaseChars."
    }
}
