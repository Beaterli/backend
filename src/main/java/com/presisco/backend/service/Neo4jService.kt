package com.presisco.backend.service

import org.neo4j.driver.*
import org.springframework.stereotype.Service
import java.util.*


@Service
class Neo4jService {
    val neo4jConf = mapOf(
            "uri" to "bolt://localhost:7687",
            "username" to "neo4j",
            "password" to "root"
    )
    val neo4jDriver = GraphDatabase.driver(
            neo4jConf["uri"],
            AuthTokens.basic(neo4jConf["username"], neo4jConf["password"]),
            Config.defaultConfig()
    )!!
    val session = neo4jDriver.session()!!

    fun getSomething(): List<String> {
        val cql = "MATCH (:Travel{word:'旅游'})-[r:`旅游交通方式`]->(p) RETURN p.word as word"
        val result = session.run(cql)
        val resultList = ArrayList<String>()

        while (result.hasNext()) {
            val record: Record = result.next()
            resultList.add(record.get("word").toString())
        }

        session.close()
        neo4jDriver.close()
        return resultList
    }

}