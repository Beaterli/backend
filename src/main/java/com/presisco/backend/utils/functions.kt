package com.presisco.backend.utils

fun keywordAndDateWhere(keyword: String?, earliest: String?, latest: String?): String {
    val wheres = arrayListOf<String>()
    if (keyword != null) {
        wheres.add("content like '%$keyword%'")
    }
    if (earliest != null) {
        wheres.add("time >= '$earliest%'")
    }
    if (latest != null) {
        wheres.add("time <= '$latest%'")
    }
    return wheres.andJoin()
}

fun parseKeywordAndDateParams(keyword: String, earliest: String?, latest: String?): Triple<String?, String?, String?> {
    val trimmedKeyword = if (keyword.trim().isEmpty()) {
        null
    } else {
        keyword.trim()
    }

    val earliestCond = if (earliest == null || earliest.isEmpty()) {
        null
    } else {
        earliest
    }

    val latestCond = if (latest == null || latest.isEmpty()) {
        null
    } else {
        latest
    }

    return Triple(trimmedKeyword, earliestCond, latestCond)
}

fun List<String>.andJoin() = this.filter { it.isNotEmpty() }.joinToString(separator = " and ")