package org.kvalid.dsl.extension

import org.kvalid.core.model.NamedValue
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import org.kvalid.dsl.extension.toNamed

internal class KPropertyExtensionTest : FunSpec({
    test("toNamed(value)") {
        val instance = Class("test")

        instance.run {
            val named = ::prop.toNamed(prop)

            named shouldBe NamedValue("prop", prop)
        }
    }
})

private data class Class(val prop: String)