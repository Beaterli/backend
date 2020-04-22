package com.presisco.backend.permission

interface PathMatcher {

    fun isAMatch(path: String): Boolean

}