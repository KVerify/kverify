package io.github.kverify.core

/**
 * Marks DSL elements in the Kverify API to prevent scope leakage.
 *
 * This annotation prevents implicit receiver mixing in nested DSL blocks.
 *
 * @see DslMarker
 */
@DslMarker
@Target(AnnotationTarget.CLASS, AnnotationTarget.TYPEALIAS, AnnotationTarget.TYPE, AnnotationTarget.FUNCTION)
public annotation class KverifyDsl
