package io.github.kverify.test.kit.util

import de.infix.testBalloon.framework.core.Test
import de.infix.testBalloon.framework.core.TestConfig
import de.infix.testBalloon.framework.core.TestSuiteScope

inline fun TestSuiteScope.repeatTest(
    attempts: Int,
    name: String,
    testConfig: TestConfig = TestConfig,
    crossinline action: suspend Test.ExecutionScope.() -> Unit,
): Unit =
    test(
        name = name,
        testConfig = testConfig,
    ) {
        repeat(attempts) { action() }
    }
