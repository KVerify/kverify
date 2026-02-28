package io.github.kverify.rule.set

import io.github.kverify.core.context.ValidationContext
import io.github.kverify.core.context.ValidationPathElement
import io.github.kverify.core.scope.using
import io.github.kverify.core.scope.verify
import io.github.kverify.core.scope.verifyWithCollecting
import io.github.kverify.rule.set.collection.MinSizeRule
import io.github.kverify.rule.set.string.MinLengthRule

internal fun ValidationContext.validationPath(): List<ValidationPathElement> {
    val context = this

    return buildList {
        context.fold(this) { acc, element ->
            if (element is ValidationPathElement) acc.add(element)
            acc
        }
    }
}
