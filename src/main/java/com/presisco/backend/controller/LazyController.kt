package com.presisco.backend.controller

import com.presisco.backend.permission.Definition
import java.text.SimpleDateFormat
import javax.servlet.http.HttpServletRequest

abstract class LazyController {
    val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS")
    fun now() = format.format(System.currentTimeMillis())

    protected fun HttpServletRequest.getUser() = Definition.getSessionName(this)
    //protected fun HttpServletRequest.getRole() = "admin"
    protected fun HttpServletRequest.getRole() = Definition.getSessionRole(this)

    protected fun HttpServletRequest.getVendor() = Definition.getSessionAttr(this, "vendor")

}