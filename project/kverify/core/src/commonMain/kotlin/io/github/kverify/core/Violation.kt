package io.github.kverify.core

/**
 * A failure reported during validation.
 */
public interface Violation {
    /**
     * Describes why the validation failed.
     */
    public val reason: String
}

/**
 * A [Violation] with a [validationPath].
 */
public interface ViolationWithPath : Violation {
    public val validationPath: ValidationPath
}

/**
 * A [Violation] that consists only of a [reason].
 */
public class ViolationReason(
    override val reason: String,
) : Violation {
    override fun toString(): String = "ViolationReason(reason=$reason)"

    override fun equals(other: Any?): Boolean =
        when {
            this === other -> true
            other !is ViolationReason -> false
            else -> reason == other.reason
        }

    override fun hashCode(): Int = reason.hashCode()
}
