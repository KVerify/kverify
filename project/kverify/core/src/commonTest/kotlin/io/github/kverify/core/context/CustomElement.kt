package io.github.kverify.core.context

data class CustomElement(
    val id: Int,
) : ValidationContext.Element {
    companion object : ValidationContext.Element
}
