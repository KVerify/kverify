package io.github.kverify.violations

import io.github.kverify.core.model.ValidationPath

public class AtLeastViolation<T : Comparable<T>>(
    public val minAllowed: T,
    public val actual: T,
    override val validationPath: ValidationPath,
    override val reason: String,
) : PathAwareViolation {
    override fun toString(): String =
        "AtLeastViolation(minAllowed=$minAllowed, actual=$actual, validationPath=$validationPath, reason=$reason)"
}

public class AtMostViolation<T : Comparable<T>>(
    public val maxAllowed: T,
    public val actual: T,
    override val validationPath: ValidationPath,
    override val reason: String,
) : PathAwareViolation {
    override fun toString(): String =
        "AtMostViolation(maxAllowed=$maxAllowed, actual=$actual, validationPath=$validationPath, reason=$reason)"
}

public class BetweenViolation<T : Comparable<T>>(
    public val min: T,
    public val max: T,
    public val actual: T,
    override val validationPath: ValidationPath,
    override val reason: String,
) : PathAwareViolation {
    override fun toString(): String = "BetweenViolation(min=$min, max=$max, actual=$actual, validationPath=$validationPath, reason=$reason)"
}

public class GreaterThanViolation<T : Comparable<T>>(
    public val minExclusive: T,
    public val actual: T,
    override val validationPath: ValidationPath,
    override val reason: String,
) : PathAwareViolation {
    override fun toString(): String =
        "GreaterThanViolation(minExclusive=$minExclusive, actual=$actual, validationPath=$validationPath, reason=$reason)"
}

public class LessThanViolation<T : Comparable<T>>(
    public val maxExclusive: T,
    public val actual: T,
    override val validationPath: ValidationPath,
    override val reason: String,
) : PathAwareViolation {
    override fun toString(): String =
        "LessThanViolation(maxExclusive=$maxExclusive, actual=$actual, validationPath=$validationPath, reason=$reason)"
}
