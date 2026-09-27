package io.github.kverify.core.verification

import io.github.kverify.core.model.NamePathElement
import io.github.kverify.core.model.ValidationPath
import io.github.kverify.core.strategy.ValidationStrategy
import io.github.kverify.core.violation.Violation
import kotlin.reflect.KProperty0

public interface Verification<out S : ValidationStrategy> {
    public val validationStrategy: S
    public val validationPath: ValidationPath
}

context(validationStrategy: S, validationPath: ValidationPath)
public inline fun <V, S : ValidationStrategy> verifyValue(
    value: V,
    block: context(S, ValidationPath) ValueVerification<V, S>.() -> Unit,
) {
    val valueVerification =
        ValueVerification(
            value = value,
            validationStrategy = validationStrategy,
            validationPath = validationPath,
        )

    context(validationStrategy, validationPath) { valueVerification.block() }
}

context(validationStrategy: S, validationPath: ValidationPath)
public inline fun <V, S : ValidationStrategy> verifyProperty(
    property: KProperty0<V>,
    block: context(S, ValidationPath) ValueVerification<V, S>.() -> Unit,
) {
    val newPath = validationPath + NamePathElement(property.name)

    val valueVerification =
        ValueVerification(
            value = property.get(),
            validationStrategy = validationStrategy,
            validationPath = newPath,
        )

    context(validationStrategy, newPath) { valueVerification.block() }
}

public fun Verification<*>.failWith(violation: Violation): Unit = validationStrategy.enforce(violation)
