package com.presisco.backend.controller

import com.presisco.backend.service.StatisticService
import com.presisco.backend.utils.parseKeywordAndDateParams
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class StatisticController {

    @Autowired
    open lateinit var statisticService: StatisticService


    @RequestMapping("/stageList")
    fun stageList(
            @RequestParam("keyword") keyword: String,
            @RequestParam("earliest") earliest: String?,
            @RequestParam("latest") latest: String?
    ): List<Map<String, *>> {
        val params = parseKeywordAndDateParams(keyword, earliest, latest)

        return statisticService.getStageList(params.first, params.second, params.third)
    }

    @RequestMapping("/stages")
    fun stages(
            @RequestParam("root_mid") root: String
    ): List<Int> {
        return statisticService.getRepostStage(root)
    }

    @RequestMapping("/heatList")
    fun heatList(
            @RequestParam("keyword") keyword: String,
            @RequestParam("earliest") earliest: String?,
            @RequestParam("latest") latest: String?
    ): List<Map<String, *>> {
        val params = parseKeywordAndDateParams(keyword, earliest, latest)

        return statisticService.getHeatList(params.first, params.second, params.third)
    }

    @RequestMapping("/heat")
    fun heat(
            @RequestParam("root_mid") root: String
    ): List<Map<String, *>> {
        return statisticService.getHeatStage(root)
    }

}