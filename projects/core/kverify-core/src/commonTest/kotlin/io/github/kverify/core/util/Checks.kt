package io.github.kverify.core.util

import kotlin.test.assertContains

infix fun <T, C : Iterable<T>> C.shouldContain(element: T): Unit = assertContains(this, element)

infix fun String.shouldContain(element: String): Unit = assertContains(this, element)
