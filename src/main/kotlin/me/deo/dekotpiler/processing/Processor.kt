package me.deo.dekotpiler.processing

import me.deo.dekotpiler.matching.Matcher

interface Processor<T : Any> : Matcher<T> {
    val mode: Mode

    fun replace(value: T): Any? = value.also { modify(it) }
    fun modify(value: T) = Unit

    enum class Mode {
        Pre,
        Post
    }
}