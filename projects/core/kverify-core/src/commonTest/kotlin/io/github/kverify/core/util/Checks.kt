package io.github.kverify.core.util

import kotlin.test.assertContains
import kotlin.test.assertTrue

infix fun <T, I : Iterable<T>> I.shouldContain(element: T): Unit = assertContains(this, element)

fun <T, I : Iterable<T>> I.shouldContainAll(vararg elements: T): Unit =
    assertTrue {
        this.all { it in elements }
    }

fun <T, C : Collection<T>> C.shouldContainExactly(vararg elements: T): Unit =
    assertTrue {
        this.size == elements.size && this.all { it in elements }
    }

infix fun CharSequence.shouldContain(element: String): Unit = assertContains(this, element)
