package io.github.kverify.rule.set

import io.github.kverify.core.context.ValidationContext
import io.github.kverify.core.model.NamedValue
import io.github.kverify.core.rule.runValidation
import io.github.kverify.rule.set.collection.CollectionRuleFactory
import io.github.kverify.rule.set.factory.CollectionViolationFactory
import io.kotest.assertions.shouldFail
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.property.Arb
import io.kotest.property.arbitrary.int
import io.kotest.property.checkAll
import kotlin.test.DefaultAsserter.fail

private typealias IntList = List<Int>

class CollectionRulesTest :
    FunSpec({
        val failContext = ValidationContext { fail(it.reason) }

        test("of size") {
            Arb.int(1..10).checkAll { size ->
                val rule = CollectionRuleFactory.ofSize<IntList>(size)
                val namedRule = CollectionRuleFactory.namedOfSize<IntList>(size)

                val correctCollection = List(size) { it }
                val incorrectCollection = List(size - 1) { it }

                val namedCorrectCollection = NamedValue("name", correctCollection)
                val namedIncorrectCollection = NamedValue("name", incorrectCollection)

                rule.runValidation(failContext, correctCollection)
                namedRule.runValidation(failContext, namedCorrectCollection)

                shouldFail {
                    rule.runValidation(failContext, incorrectCollection)
                }.message shouldBe
                    CollectionViolationFactory
                        .ofSize(
                            size,
                            incorrectCollection,
                        ).reason

                shouldFail {
                    namedRule.runValidation(failContext, namedIncorrectCollection)
                }.message shouldBe
                    CollectionViolationFactory
                        .ofSize(
                            size,
                            namedIncorrectCollection.value,
                            namedIncorrectCollection.name,
                        ).reason
            }
        }

        test("not of size") {
            Arb.int(1..10).checkAll { size ->
                val rule = CollectionRuleFactory.notOfSize<IntList>(size)
                val namedRule = CollectionRuleFactory.namedNotOfSize<IntList>(size)

                val correctCollection = List(size + 1) { it }
                val incorrectCollection = List(size) { it }

                val namedCorrectCollection = NamedValue("name", correctCollection)
                val namedIncorrectCollection = NamedValue("name", incorrectCollection)

                rule.runValidation(failContext, correctCollection)
                namedRule.runValidation(failContext, namedCorrectCollection)

                shouldFail {
                    rule.runValidation(failContext, incorrectCollection)
                }.message shouldBe
                    CollectionViolationFactory
                        .notOfSize(
                            size,
                            incorrectCollection,
                        ).reason

                shouldFail {
                    namedRule.runValidation(failContext, namedIncorrectCollection)
                }.message shouldBe
                    CollectionViolationFactory
                        .notOfSize(
                            size,
                            namedIncorrectCollection.value,
                            namedIncorrectCollection.name,
                        ).reason
            }
        }

        test("max size") {
            Arb.int(1..10).checkAll { size ->
                val rule = CollectionRuleFactory.maxSize<IntList>(size)
                val namedRule = CollectionRuleFactory.namedMaxSize<IntList>(size)

                val correctCollection = List(size) { it }
                val incorrectCollection = List(size + 1) { it }

                val namedCorrectCollection = NamedValue("name", correctCollection)
                val namedIncorrectCollection = NamedValue("name", incorrectCollection)

                rule.runValidation(failContext, correctCollection)
                namedRule.runValidation(failContext, namedCorrectCollection)

                shouldFail {
                    rule.runValidation(failContext, incorrectCollection)
                }.message shouldBe
                    CollectionViolationFactory
                        .maxSize(
                            size,
                            incorrectCollection,
                        ).reason

                shouldFail {
                    namedRule.runValidation(failContext, namedIncorrectCollection)
                }.message shouldBe
                    CollectionViolationFactory
                        .maxSize(
                            size,
                            namedIncorrectCollection.value,
                            namedIncorrectCollection.name,
                        ).reason
            }
        }

        test("min size") {
            Arb.int(1..10).checkAll { size ->
                val rule = CollectionRuleFactory.minSize<IntList>(size)
                val namedRule = CollectionRuleFactory.namedMinSize<IntList>(size)

                val correctCollection = List(size) { it }
                val incorrectCollection = List(size - 1) { it }

                val namedCorrectCollection = NamedValue("name", correctCollection)
                val namedIncorrectCollection = NamedValue("name", incorrectCollection)

                rule.runValidation(failContext, correctCollection)
                namedRule.runValidation(failContext, namedCorrectCollection)

                shouldFail {
                    rule.runValidation(failContext, incorrectCollection)
                }.message shouldBe
                    CollectionViolationFactory
                        .minSize(
                            size,
                            incorrectCollection,
                        ).reason

                shouldFail {
                    namedRule.runValidation(failContext, namedIncorrectCollection)
                }.message shouldBe
                    CollectionViolationFactory
                        .minSize(
                            size,
                            namedIncorrectCollection.value,
                            namedIncorrectCollection.name,
                        ).reason
            }
        }

        test("size between") {
            Arb.int(1..8).checkAll { min ->
                Arb.int(min + 2..10).checkAll { max ->
                    val range = min..max
                    val rule = CollectionRuleFactory.sizeBetween<IntList>(range)
                    val namedRule = CollectionRuleFactory.namedSizeBetween<IntList>(range)

                    val correctCollection = List(min + 1) { it }
                    val incorrectCollection = List(max + 1) { it }

                    val namedCorrectCollection = NamedValue("name", correctCollection)
                    val namedIncorrectCollection = NamedValue("name", incorrectCollection)

                    rule.runValidation(failContext, correctCollection)
                    namedRule.runValidation(failContext, namedCorrectCollection)

                    shouldFail {
                        rule.runValidation(failContext, incorrectCollection)
                    }.message shouldBe
                        CollectionViolationFactory
                            .sizeBetween(
                                range,
                                incorrectCollection,
                            ).reason

                    shouldFail {
                        namedRule.runValidation(failContext, namedIncorrectCollection)
                    }.message shouldBe
                        CollectionViolationFactory
                            .sizeBetween(
                                range,
                                namedIncorrectCollection.value,
                                namedIncorrectCollection.name,
                            ).reason
                }
            }
        }

        test("size not between") {
            Arb.int(1..8).checkAll { min ->
                Arb.int(min + 2..10).checkAll { max ->
                    val range = min..max
                    val rule = CollectionRuleFactory.sizeNotBetween<IntList>(range)
                    val namedRule = CollectionRuleFactory.namedSizeNotBetween<IntList>(range)

                    val correctCollection = List(max + 1) { it }
                    val incorrectCollection = List(min + 1) { it }

                    val namedCorrectCollection = NamedValue("name", correctCollection)
                    val namedIncorrectCollection = NamedValue("name", incorrectCollection)

                    rule.runValidation(failContext, correctCollection)
                    namedRule.runValidation(failContext, namedCorrectCollection)

                    shouldFail {
                        rule.runValidation(failContext, incorrectCollection)
                    }.message shouldBe
                        CollectionViolationFactory
                            .sizeNotBetween(
                                range,
                                incorrectCollection,
                            ).reason

                    shouldFail {
                        namedRule.runValidation(failContext, namedIncorrectCollection)
                    }.message shouldBe
                        CollectionViolationFactory
                            .sizeNotBetween(
                                range,
                                namedIncorrectCollection.value,
                                namedIncorrectCollection.name,
                            ).reason
                }
            }
        }

        test("contains all") {
            val elements = listOf(1, 2, 3)
            val rule = CollectionRuleFactory.containsAll<Int, IntList>(elements)
            val namedRule = CollectionRuleFactory.namedContainsAll<Int, IntList>(elements)

            val correctCollection = listOf(1, 2, 3, 4, 5)
            val incorrectCollection = listOf(1, 2, 4, 5)

            val namedCorrectCollection = NamedValue("name", correctCollection)
            val namedIncorrectCollection = NamedValue("name", incorrectCollection)

            rule.runValidation(failContext, correctCollection)
            namedRule.runValidation(failContext, namedCorrectCollection)

            shouldFail {
                rule.runValidation(failContext, incorrectCollection)
            }.message shouldBe
                CollectionViolationFactory
                    .containsAll(
                        elements,
                        incorrectCollection,
                    ).reason

            shouldFail {
                namedRule.runValidation(failContext, namedIncorrectCollection)
            }.message shouldBe
                CollectionViolationFactory
                    .containsAll(
                        elements,
                        namedIncorrectCollection.value,
                        namedIncorrectCollection.name,
                    ).reason
        }

        test("contains none") {
            val elements = listOf(1, 2, 3)
            val rule = CollectionRuleFactory.containsNone<Int, IntList>(elements)
            val namedRule = CollectionRuleFactory.namedContainsNone<Int, IntList>(elements)

            val correctCollection = listOf(4, 5, 6)
            val incorrectCollection = listOf(1, 4, 5)

            val namedCorrectCollection = NamedValue("name", correctCollection)
            val namedIncorrectCollection = NamedValue("name", incorrectCollection)

            rule.runValidation(failContext, correctCollection)
            namedRule.runValidation(failContext, namedCorrectCollection)

            shouldFail {
                rule.runValidation(failContext, incorrectCollection)
            }.message shouldBe
                CollectionViolationFactory
                    .containsNone(
                        elements,
                        incorrectCollection,
                    ).reason

            shouldFail {
                namedRule.runValidation(failContext, namedIncorrectCollection)
            }.message shouldBe
                CollectionViolationFactory
                    .containsNone(
                        elements,
                        namedIncorrectCollection.value,
                        namedIncorrectCollection.name,
                    ).reason
        }

        test("contains") {
            val element = 1
            val rule = CollectionRuleFactory.contains<Int, IntList>(element)
            val namedRule = CollectionRuleFactory.namedContains<Int, IntList>(element)

            val correctCollection = listOf(1, 2, 3)
            val incorrectCollection = listOf(2, 3, 4)

            val namedCorrectCollection = NamedValue("name", correctCollection)
            val namedIncorrectCollection = NamedValue("name", incorrectCollection)

            rule.runValidation(failContext, correctCollection)
            namedRule.runValidation(failContext, namedCorrectCollection)

            shouldFail {
                rule.runValidation(failContext, incorrectCollection)
            }.message shouldBe
                CollectionViolationFactory
                    .contains(
                        element,
                        incorrectCollection,
                    ).reason

            shouldFail {
                namedRule.runValidation(failContext, namedIncorrectCollection)
            }.message shouldBe
                CollectionViolationFactory
                    .contains(
                        element,
                        namedIncorrectCollection.value,
                        namedIncorrectCollection.name,
                    ).reason
        }

        test("not contains") {
            val element = 1
            val rule = CollectionRuleFactory.notContains<Int, IntList>(element)
            val namedRule = CollectionRuleFactory.namedNotContains<Int, IntList>(element)

            val correctCollection = listOf(2, 3, 4)
            val incorrectCollection = listOf(1, 2, 3)

            val namedCorrectCollection = NamedValue("name", correctCollection)
            val namedIncorrectCollection = NamedValue("name", incorrectCollection)

            rule.runValidation(failContext, correctCollection)
            namedRule.runValidation(failContext, namedCorrectCollection)

            shouldFail {
                rule.runValidation(failContext, incorrectCollection)
            }.message shouldBe
                CollectionViolationFactory
                    .notContains(
                        element,
                        incorrectCollection,
                    ).reason

            shouldFail {
                namedRule.runValidation(failContext, namedIncorrectCollection)
            }.message shouldBe
                CollectionViolationFactory
                    .notContains(
                        element,
                        namedIncorrectCollection.value,
                        namedIncorrectCollection.name,
                    ).reason
        }

        test("contains only") {
            val elements = listOf(1, 2, 3, 4, 5)
            val rule = CollectionRuleFactory.containsOnly<Int, IntList>(elements)
            val namedRule = CollectionRuleFactory.namedContainsOnly<Int, IntList>(elements)

            val correctCollection = listOf(1, 2, 3)
            val incorrectCollection = listOf(1, 2, 6)

            val namedCorrectCollection = NamedValue("name", correctCollection)
            val namedIncorrectCollection = NamedValue("name", incorrectCollection)

            rule.runValidation(failContext, correctCollection)
            namedRule.runValidation(failContext, namedCorrectCollection)

            shouldFail {
                rule.runValidation(failContext, incorrectCollection)
            }.message shouldBe
                CollectionViolationFactory
                    .containsOnly(
                        elements,
                        incorrectCollection,
                    ).reason

            shouldFail {
                namedRule.runValidation(failContext, namedIncorrectCollection)
            }.message shouldBe
                CollectionViolationFactory
                    .containsOnly(
                        elements,
                        namedIncorrectCollection.value,
                        namedIncorrectCollection.name,
                    ).reason
        }

        test("not empty") {
            val rule = CollectionRuleFactory.notEmpty<IntList>()
            val namedRule = CollectionRuleFactory.namedNotEmpty<IntList>()

            val correctCollection = listOf(1, 2, 3)
            val incorrectCollection = emptyList<Int>()

            val namedCorrectCollection = NamedValue("name", correctCollection)
            val namedIncorrectCollection = NamedValue("name", incorrectCollection)

            rule.runValidation(failContext, correctCollection)
            namedRule.runValidation(failContext, namedCorrectCollection)

            shouldFail {
                rule.runValidation(failContext, incorrectCollection)
            }.message shouldBe
                CollectionViolationFactory
                    .notEmpty(
                        incorrectCollection,
                    ).reason

            shouldFail {
                namedRule.runValidation(failContext, namedIncorrectCollection)
            }.message shouldBe
                CollectionViolationFactory
                    .notEmpty(
                        namedIncorrectCollection.value,
                        namedIncorrectCollection.name,
                    ).reason
        }

        test("distinct") {
            val rule = CollectionRuleFactory.distinct<IntList>()
            val namedRule = CollectionRuleFactory.namedDistinct<IntList>()

            val correctCollection = listOf(1, 2, 3)
            val incorrectCollection = listOf(1, 2, 2, 3)

            val namedCorrectCollection = NamedValue("name", correctCollection)
            val namedIncorrectCollection = NamedValue("name", incorrectCollection)

            rule.runValidation(failContext, correctCollection)
            namedRule.runValidation(failContext, namedCorrectCollection)

            shouldFail {
                rule.runValidation(failContext, incorrectCollection)
            }.message shouldBe
                CollectionViolationFactory
                    .distinct(
                        incorrectCollection,
                    ).reason

            shouldFail {
                namedRule.runValidation(failContext, namedIncorrectCollection)
            }.message shouldBe
                CollectionViolationFactory
                    .distinct(
                        namedIncorrectCollection.value,
                        namedIncorrectCollection.name,
                    ).reason
        }
    })
