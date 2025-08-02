enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

include(
    ":kverify:core",
    // rule-set
    ":kverify:rule-set",
    ":kverify:typed-violation-set",
    ":kverify:class-based-violation-factory-set",
    // named
    ":kverify:named:named-value",
    ":kverify:named:named-rule-set",
    ":kverify:named:named-class-based-violation-factory-set",
    // test
    ":test-kit",
)
