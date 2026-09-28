package io.github.kverify.core.model

import kotlin.jvm.JvmName

public sealed interface ValidationPath {
    public val size: Int

    public operator fun plus(element: ValidationPathElement): ValidationPath =
        ValidationPathNode(
            parent = this,
            element = element,
        )
}

public object EmptyValidationPath : ValidationPath {
    override val size: Int = 0

    override fun plus(element: ValidationPathElement): ValidationPath = element
}

public class ValidationPathNode(
    public val parent: ValidationPath,
    public val element: ValidationPathElement,
) : ValidationPath {
    override val size: Int = parent.size + 1
}

public sealed interface ValidationPathElement : ValidationPath

public class NamePathElement(
    public val name: String,
) : ValidationPathElement {
    override val size: Int = 1
}

public class IndexPathElement(
    public val index: Int,
) : ValidationPathElement {
    override val size: Int = 1
}

public fun ValidationPath(vararg elements: ValidationPathElement): ValidationPath {
    if (elements.isEmpty()) return EmptyValidationPath

    return elements.reduce<ValidationPath, ValidationPathElement> { acc, element ->
        acc + element
    }
}

public fun ValidationPath.toList(): List<ValidationPathElement> {
    val result = ArrayList<ValidationPathElement>(size)
    var currentPath = this

    while (currentPath is ValidationPathNode) {
        result.add(currentPath.element)
        currentPath = currentPath.parent
    }

    return result.asReversed()
}

@JvmName("pathNameExtension")
public inline fun ValidationPath.pathName(
    name: String,
    block: context(ValidationPath) () -> Unit = {},
): ValidationPath {
    val newPath = this + NamePathElement(name)

    context(newPath) { block() }

    return newPath
}

context(validationPath: ValidationPath)
public inline fun pathName(
    name: String,
    block: context(ValidationPath) () -> Unit = {},
): ValidationPath = validationPath.pathName(name, block)

@JvmName("pathIndexExtension")
public inline fun ValidationPath.pathIndex(
    index: Int,
    block: context(ValidationPath) () -> Unit = {},
): ValidationPath {
    val newPath = this + IndexPathElement(index)

    context(newPath) { block() }

    return newPath
}

context(validationPath: ValidationPath)
public inline fun pathIndex(
    index: Int,
    block: context(ValidationPath) () -> Unit = {},
): ValidationPath = validationPath.pathIndex(index, block)
