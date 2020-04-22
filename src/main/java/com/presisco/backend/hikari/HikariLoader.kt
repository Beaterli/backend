package com.presisco.backend.hikari

import com.presisco.backend.Preference
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import java.util.*

object HikariLoader {
    private val poolMap = HashMap<String, HikariDataSource>()

    init {
        val hikariConfMap = Preference.getMap("hikari")
        for ((name, value) in hikariConfMap) {
            val config = value as Map<String, String>
            val prop = Properties()
            prop.putAll(config)
            poolMap[name] = HikariDataSource(HikariConfig(prop))
        }
    }

    fun getDataSource(name: String) = poolMap[name]!!

}