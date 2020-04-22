package com.presisco.backend.permission

import com.presisco.lazyjdbc.client.MapJdbcClient
import com.presisco.backend.Preference
import com.presisco.backend.hikari.HikariLoader

object PermissionJdbcClient : MapJdbcClient(
        HikariLoader.getDataSource(Preference.getMap("permission")["data_source"] as String),
        5,
        true
)