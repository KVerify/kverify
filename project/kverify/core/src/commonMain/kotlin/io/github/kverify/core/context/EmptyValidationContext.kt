package io.github.kverify.core.context

public object EmptyValidationContext : ValidationContext by DefaultValidationContext(null)
