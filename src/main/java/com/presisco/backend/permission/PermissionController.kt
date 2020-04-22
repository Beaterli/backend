package com.presisco.backend.permission

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.servlet.http.HttpServletRequest

@RestController
class PermissionController {

    @Autowired
    lateinit var userControlService: UserControlService

    @Autowired
    lateinit var accessLogService: AccessLogService

    @RequestMapping("/login")
    fun login(request: HttpServletRequest, @RequestBody loginParams: Map<String, String>): Map<String, *> {
        val info = userControlService.getUserInfo(loginParams["username"]!!, loginParams["password"]!!)
        return if (info.isNotEmpty()) {
            info.forEach { key, value -> request.session.setAttribute(key, value) }
            mapOf("role" to info["role"])
        } else {
            mapOf("result" to false)
        }
    }

    @RequestMapping("/logout")
    fun logout(request: HttpServletRequest): Map<String, *> {
        request.session.invalidate()
        return mapOf("result" to true)
    }

    @RequestMapping("/register")
    fun register(request: HttpServletRequest, @RequestBody userInfo: MutableMap<String, Any?>): Map<String, *> {
        return mapOf("result" to try {
            userControlService.addUser(userInfo)
            "succeed"
        } catch (e: Exception) {
            "failed"
        })
    }

    @RequestMapping("/changePassword")
    fun changePassword(request: HttpServletRequest, @RequestBody userInfo: MutableMap<String, Any?>): Map<String, *> {
        return mapOf("result" to try {
            userControlService.changePassword(Definition.getSessionName(request)!!, userInfo["password"] as String)
            "succeed"
        } catch (e: Exception) {
            "failed"
        })
    }

    @RequestMapping("/addUser")
    fun addUser(request: HttpServletRequest, @RequestBody userInfo: MutableMap<String, Any?>): Map<String, *> {
        return mapOf("result" to try {
            userControlService.addUser(userInfo)
            "succeed"
        } catch (e: Exception) {
            "failed"
        })
    }

    @RequestMapping("/users")
    fun users(request: HttpServletRequest) = userControlService.users()

    @RequestMapping("/deleteUser")
    fun deleteUser(request: HttpServletRequest, @RequestParam("username") username: String): Map<String, *> {
        return mapOf("result" to try {
            userControlService.deleteUser(username)
            "succeed"
        } catch (e: Exception) {
            "failed"
        })
    }

    @RequestMapping("/accessLog")
    fun accessLog(request: HttpServletRequest) =
            accessLogService.getLogs(7)
                    .map {
                        hashMapOf(
                                "username" to it["username"],
                                "url" to it["url"],
                                "timestamp" to accessLogDateFormat.format(it["timestamp"] as LocalDateTime)
                        )
                    }

    @RequestMapping("/myRole")
    fun myRole(request: HttpServletRequest) = mapOf(
            "username" to Definition.getSessionName(request),
            "role" to Definition.getSessionRole(request)
    )

    companion object {
        val accessLogDateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")
    }
}