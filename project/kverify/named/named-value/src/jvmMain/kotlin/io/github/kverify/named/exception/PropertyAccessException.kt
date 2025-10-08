package io.github.kverify.named.exception

/**
 * Thrown when a reflective property access fails during conversion to [io.github.kverify.named.model.NamedValue].
 */
public class PropertyAccessException(
    override val message: String,
    override val cause: Throwable? = null,
) : Throwable()
