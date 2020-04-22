package com.presisco.backend.permission

import org.junit.Test
import kotlin.test.expect

class ExtensionMatcherTest {
    private val matcher = ExtensionMatcher(listOf("html", "js"))

    @Test
    fun accept() {
        with(matcher) {
            expect(true) { isAMatch("/home/index.html") }
            expect(true) { isAMatch("/index.js") }
        }
    }

    @Test
    fun reject() {
        with(matcher) {
            expect(false) { isAMatch("/home/style.css") }
            expect(false) { isAMatch("/login/access") }
        }
    }
}