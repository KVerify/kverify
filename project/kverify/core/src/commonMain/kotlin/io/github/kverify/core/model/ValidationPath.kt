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

public fun ValidationPath(vararg elements: ValidationPathElement): ValidationPath {
    if (elements.isEmpty()) return EmptyValidationPath

    var result: ValidationPath = EmptyValidationPath

    for (element in elements) {
        result += element
    }

    return result
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
