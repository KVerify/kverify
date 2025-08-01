enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

include(
    ":kverify:core",
    // rule-set
    ":kverify:rule-set",
    // named
    ":kverify:named:named-value",
    ":kverify:named:named-rule-set",
    // test
    ":test-kit",
)
