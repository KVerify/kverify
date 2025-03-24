package io.github.kverify.core.util

import kotlin.test.assertTrue

fun interface ExecutionCheck {
    fun execute()
}

class BooleanExecutionCheck : ExecutionCheck {
    var isExecuted = false

    override fun execute() {
        isExecuted = true
    }
}

class ExecutionCountCheck : ExecutionCheck {
    var count = 0

    override fun execute() {
        count++
    }
}

inline fun executionCheck(
    message: String = "block was not executed",
    block: ExecutionCheck.() -> Unit,
) {
    val wasExecuted = BooleanExecutionCheck().apply(block).isExecuted
    assertTrue(message) { wasExecuted }
}

inline fun executionCountCheck(
    expectedCount: Int,
    message: String = "block was not executed $expectedCount times",
    block: ExecutionCountCheck.() -> Unit,
) {
    val count = ExecutionCountCheck().apply(block).count
    assertTrue(message) { count == expectedCount }
}
