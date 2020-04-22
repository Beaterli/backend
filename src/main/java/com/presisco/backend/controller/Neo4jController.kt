package com.presisco.backend.controller

import com.presisco.backend.service.Neo4jService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class Neo4jController {

    @Autowired
    open lateinit var neo4jService: Neo4jService

    @RequestMapping("/neo4j")
    fun events(
            //@RequestParam("keyword") keyword: String
    ): List<String> {
        return neo4jService.getSomething()
    }

}