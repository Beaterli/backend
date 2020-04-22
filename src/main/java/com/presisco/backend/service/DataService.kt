package com.presisco.backend.service

import com.presisco.backend.hikari.HikariLoader
import com.presisco.backend.utils.andJoin
import com.presisco.backend.utils.keywordAndDateWhere
import com.presisco.lazyjdbc.client.MapJdbcClient
import org.springframework.stereotype.Service

@Service
class DataService {
    private val client = MapJdbcClient(HikariLoader.getDataSource("singleton"))

    fun getBlogs(keyword: String? = null, earliest: String? = null, latest: String? = null, root: Boolean? = null): List<Map<String, *>> {
        var sql = "select mid, blog.url, content, blog.time, repost, blog_user.name from blog, blog_user where "
        val rootCond = if (root != null) {
            "repost_id is ${if (root) "null" else "not null"}"
        } else {
            ""
        }
        val finalWheres = listOf(
                keywordAndDateWhere(keyword, earliest, latest),
                rootCond,
                "blog_user.uid = blog.uid"
        ).andJoin()

        sql = "$sql\n $finalWheres\n limit 0, 1000"
        return client.select(sql)
    }

    fun getComments(keyword: String? = null, earliest: String? = null, latest: String? = null): List<Map<String, *>> {
        var sql = "select cid, content, time, `like`, uid from comment"
        val rootWheres = keywordAndDateWhere(keyword, earliest, latest)
        if (rootWheres.isNotEmpty()) {
            sql += " where $rootWheres"
        }
        sql += " limit 0, 1000"
        return client.select(sql)
    }

}