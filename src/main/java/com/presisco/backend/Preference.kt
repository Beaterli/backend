package com.presisco.backend

import com.presisco.gsonhelper.ConfigMapHelper
import java.util.*

object Preference {
    lateinit var preferenceMap: Map<String, *>
    private val configMapHelper = ConfigMapHelper()

    init {
    }

    fun loadFromJson(filePath: String) {
        preferenceMap = configMapHelper.readConfigMap(filePath)
    }

    private fun map2prop(map: Map<String, String>): Properties {
        val prop = Properties()
        prop.putAll(map)
        return prop
    }

    fun getMap(name: String) = preferenceMap[name] as Map<String, *>

    fun getList(name: String) = preferenceMap[name] as List<*>

}