package com.presisco.backend.permission

class ExtensionMatcher(private val extensions: List<String>) : PathMatcher {

    override fun isAMatch(path: String): Boolean {
        val ext = path.substringAfterLast(".")
        return extensions.contains(ext)
    }
}