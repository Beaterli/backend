package com.presisco.backend.service

import com.presisco.backend.hikari.HikariLoader
import com.presisco.backend.utils.keywordAndDateWhere
import com.presisco.lazyjdbc.client.MapJdbcClient
import org.springframework.stereotype.Service

@Service
class StatisticService {
    private val client = MapJdbcClient(HikariLoader.getDataSource("singleton"))

    fun getStageList(keyword: String? = null, earliest: String? = null, latest: String? = null): List<Map<String, *>> {
        val rootWheres = keywordAndDateWhere(keyword, earliest, latest)

        val sql = "select blog.mid, root.keyword, content, time, depth\n" +
                "from blog, root\n" +
                "where root.mid = blog.mid\n" +
                "\tand depth > 3\n" +
                "\t${if (rootWheres.isNotEmpty()) "and $rootWheres" else ""}\n" +
                "ORDER BY depth DESC\n" +
                "LIMIT 0, 1000"

        return client.select(sql)
    }

    fun getRepostStage(root: String): List<Int> {
        return client.select("select stage, count(mid) count from blog " +
                " where root_id = '$root' " +
                " group by stage" +
                " order by stage asc")
                .map { (it["count"] as Long).toInt() }
    }

    fun getHeatList(keyword: String? = null, earliest: String? = null, latest: String? = null): List<Map<String, *>> {
        val rootWheres = keywordAndDateWhere(keyword, earliest, latest)

        val sql = "select mid, group_count.keyword, content, repost, time, count\n" +
                "from blog, (\n" +
                "\tSELECT root_id, roots.keyword, COUNT(root_id) count FROM blog, (\n" +
                "\t\tselect blog.mid mid, keyword FROM blog, root\n" +
                "\t\twhere root.mid = blog.mid\n" +
                "\t\t${if (rootWheres.isNotEmpty()) "and $rootWheres" else ""}\n" +
                "\t) roots\n" +
                "\tWHERE blog.root_id = roots.mid\n" +
                "\tGROUP BY blog.root_id\n" +
                ") group_count\n" +
                "WHERE mid = group_count.root_id\n" +
                "\tand group_count.count > 100\n" +
                "ORDER BY count DESC"

        return client.select(sql)
    }

    fun getHeatStage(root: String): List<Map<String, *>> {
        return client.select("select substr(time, 1, 10) day, count(mid) repost from blog " +
                " where root_id = '$root'" +
                " group by day" +
                " order by time asc")
    }

}