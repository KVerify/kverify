enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

include(
    ":kverify:core",
    // coroutine
    ":kverify:coroutine:coroutine-core",
    // rule-set
    ":kverify:rule-set",
    ":kverify:typed-violation-set",
    // named
    ":kverify:named:named-value",
    ":kverify:named:named-rule-set",
    // test
    ":test-kit",
)
