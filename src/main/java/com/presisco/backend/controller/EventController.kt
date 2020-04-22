package com.presisco.backend.controller

import com.presisco.backend.service.EventService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class EventController {

    @Autowired
    open lateinit var eventService: EventService

    @RequestMapping("/events")
    fun events(
            @RequestParam("type") type: String,
            @RequestParam("keyword") keyword: String

    ): List<Map<String, *>> {
        if (type.isEmpty()) {
            return listOf()
        }
        val types = type.split(",")
        return eventService.eventList(types, keyword.trim())
    }

    @RequestMapping("/tree")
    fun tree(
            @RequestParam("root_mid") rootMid: String
    ) = mapOf<String, Any>(
            "repost" to eventService.treeOfRoot(rootMid),
            "comment" to eventService.commentsOfRoot(rootMid),
            "tag" to eventService.tagsOfRoot(rootMid),
            "paths" to eventService.analyzePaths(rootMid)
    )

}