package com.presisco.backend.permission

import javax.servlet.http.HttpServletRequest

object Definition {
    const val SESSION_UID = "uid"
    const val SESSION_NAME = "username"
    const val SESSION_ROLE = "role"

    fun getSessionAttr(request: HttpServletRequest, name: String) = request.session.getAttribute(name)
    fun getSessionName(request: HttpServletRequest) = getSessionAttr(request, SESSION_NAME) as String?
    fun getSessionRole(request: HttpServletRequest) = getSessionAttr(request, SESSION_ROLE) as String?
    fun getSessionUID(request: HttpServletRequest) = getSessionAttr(request, SESSION_UID) as Number?

}