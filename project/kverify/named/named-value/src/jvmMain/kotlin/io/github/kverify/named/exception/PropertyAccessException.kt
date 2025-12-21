package io.github.kverify.named.exception

/**
 * Thrown when a reflective property access fails during conversion to [io.github.kverify.named.model.NamedValue].
 *
 * This exception is thrown when attempting to read a property value via reflection and the operation fails,
 * such as when accessing a property without an instance or when the property is not accessible.
 *
 * @param message A description of why the property access failed
 * @param cause The underlying exception that caused the property access failure
 * @see io.github.kverify.named.model.toNamed
 */
public class PropertyAccessException(
    override val message: String,
    override val cause: Throwable? = null,
) : Throwable()
