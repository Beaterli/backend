package com.presisco.backend.permission

class SimplePathMatcher(paths: List<String>) : PathMatcher {
    private val regexList = paths.map {
        "^$it$".replace("*", "[\\S]+?").toRegex()
    }

    override fun isAMatch(path: String): Boolean {
        regexList.forEach {
            if (it.containsMatchIn(path))
                return true
        }
        return false
    }
}