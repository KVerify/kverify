package io.github.kverify.named.exception

/**
 * Exception thrown when there is an issue accessing a property.
 *
 * @param message a message describing the error.
 * @param cause an optional cause of the exception.
 */
public class PropertyAccessException(
    override val message: String,
    override val cause: Throwable? = null,
) : Throwable()
