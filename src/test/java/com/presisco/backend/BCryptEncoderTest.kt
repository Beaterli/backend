package com.presisco.backend

import com.presisco.backend.permission.BCryptPasswordEncoder
import org.junit.Test

class BCryptEncoderTest {

    @Test
    fun encode() {
        val encoder = BCryptPasswordEncoder()
        println(encoder.encode("admin"))
        println(encoder.encode("plain"))
    }
}