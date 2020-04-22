package com.presisco.backend.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import javax.servlet.http.HttpServletResponse

@Controller
open class CheckAliveController {

    @RequestMapping("/checkalive", produces = ["text/plain;charset=UTF-8"])
    @ResponseBody
    fun checkAlive(response: HttpServletResponse) = "alive"

}