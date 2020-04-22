package com.presisco.backend.permission

import org.junit.Test
import kotlin.test.expect

class SimplePathMatcherTest {
    private val matcher = SimplePathMatcher(listOf("/home", "/api/*"))

    @Test
    fun accept() {
        with(matcher) {
            expect(true) { isAMatch("/home") }
            expect(true) { isAMatch("/api/get") }
            expect(true) { isAMatch("/api/get/some") }
        }
    }

    @Test
    fun reject() {
        with(matcher) {
            expect(false) { isAMatch("/api") }
            expect(false) { isAMatch("/login") }
            expect(false) { isAMatch("/home/data") }
        }
    }

    @Test
    fun urlWithParams() {
        with(matcher) {
            expect(true) { isAMatch("/api/get?length=15&size=blablabla") }
            expect(false) { isAMatch("/home/data?length=15&size=blablabla") }
        }
    }
}