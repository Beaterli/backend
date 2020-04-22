package com.presisco.backend.service

import com.presisco.backend.hikari.HikariLoader
import com.presisco.gsonhelper.ListHelper
import com.presisco.lazyjdbc.client.MapJdbcClient
import com.presisco.lazyjdbc.placeHolders
import org.springframework.stereotype.Service

@Service
class EventService {
    private val client = MapJdbcClient(HikariLoader.getDataSource("singleton"))

    fun eventList(types: List<String>, keyword: String): List<Map<String, *>> {
        var sql = "select root.mid mid, keyword, content, time, name, type, depth" +
                " from blog, blog_user, root" +
                " where blog.mid = root.mid" +
                " and blog_user.uid = blog.uid" +
                " and depth > 4"
        return client.select("$sql and type in (${placeHolders(types.size)}) and content like '%$keyword%'", *types.toTypedArray())
    }

    fun treeOfRoot(rootMid: String) = client.select("select blog.mid mid, repost_id, content, blog_user.uid uid, name, time" +
            " from blog, blog_user" +
            " where root_id = ?" +
            " and blog.uid = blog_user.uid", rootMid)

    fun tagsOfRoot(rootMid: String) = client.select("select blog.mid mid, tag.tid tid, tag" +
            " from blog, blog_with_tag, tag" +
            " where root_id = ?" +
            " and blog.mid = blog_with_tag.mid" +
            " and blog_with_tag.tid = tag.tid", rootMid)

    fun commentsOfRoot(rootMid: String) = client.select("select blog.mid mid, comment.cid cid, blog_user.uid uid, name" +
            " from blog, comment, blog_user" +
            " where root_id = ?" +
            " and blog.mid = comment.mid" +
            " and blog_user.uid = comment.uid", rootMid)

    fun analyzePaths(rootMid: String) = ListHelper().fromJson(client.select("select paths" +
            " from root" +
            " where mid = ?", rootMid).first()["paths"] as String)

}