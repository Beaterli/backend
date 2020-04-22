package com.presisco.backend.controller

import com.presisco.backend.service.DataService
import com.presisco.backend.utils.parseKeywordAndDateParams
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class DataController {

    @Autowired
    open lateinit var dataService: DataService

    @RequestMapping("/blogs")
    fun events(
            @RequestParam("keyword") keyword: String,
            @RequestParam("earliest") earliest: String?,
            @RequestParam("latest") latest: String?,
            @RequestParam("root") root: Boolean?
    ): List<Map<String, *>> {
        val params = parseKeywordAndDateParams(keyword, earliest, latest)

        return dataService.getBlogs(params.first, params.second, params.third, root)
    }

    @RequestMapping("/comments")
    fun comments(
            @RequestParam("keyword") keyword: String,
            @RequestParam("earliest") earliest: String?,
            @RequestParam("latest") latest: String?
    ): List<Map<String, *>> {
        val params = parseKeywordAndDateParams(keyword, earliest, latest)

        return dataService.getComments(params.first, params.second, params.third)
    }

}