package io.github.mrkekovich.kvalid.core.strategy

import io.github.mrkekovich.kvalid.core.annotation.KValidDslMarker
import io.github.mrkekovich.kvalid.core.context.KValidContext
import io.github.mrkekovich.kvalid.core.context.ValidationPredicate
import io.github.mrkekovich.kvalid.core.dto.ValidationRule
import io.github.mrkekovich.kvalid.core.exception.ValidationException

class CollectAllContext : KValidContext {
    private val _violations: MutableList<ValidationException> = mutableListOf()

    val violations: List<ValidationException>
        get() = _violations

    override fun <T> T.validate(message: String, predicate: ValidationPredicate<T>): T {
        if (!predicate(this)) _violations.add(
            ValidationException(message)
        )
        return this
    }

    @KValidDslMarker
    override fun rule(message: String, predicate: () -> Boolean): ValidationRule<Unit> =
        ValidationRule(Unit, message) { predicate() }.also {
            if (!predicate()) _violations.add(
                ValidationException(message)
            )
        }

    @KValidDslMarker
    fun violation(message: () -> String) {
        _violations.add(ValidationException(message()))
    }
}