package com.presisco.backend.permission

import org.springframework.stereotype.Service
import java.util.*

@Service
class AccessLogService {

    fun writeLog(username: String, path: String, time: Date) =
            PermissionJdbcClient.insertInto("access_log")
                    .values(username, path, time)
                    .execute()

    fun getLogs(daySpan: Int = 7) = PermissionJdbcClient
            .select("SELECT * FROM access_log " +
                    " ORDER BY \"timestamp\" DESC" +
                    " LIMIT 0, 1000")

}