package io.github.kverify.core.model

public sealed interface ValidationPath {
    public val size: Int
}

public object EmptyValidationPath : ValidationPath {
    override val size: Int = 0
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

public operator fun ValidationPath.plus(element: ValidationPathElement): ValidationPath =
    ValidationPathNode(
        parent = this,
        element = element,
    )

public fun ValidationPath.toList(): List<ValidationPathElement> {
    val result = ArrayList<ValidationPathElement>(size)
    var currentPath = this

    while (currentPath is ValidationPathNode) {
        result.add(currentPath.element)
        currentPath = currentPath.parent
    }

    return result.asReversed()
}
