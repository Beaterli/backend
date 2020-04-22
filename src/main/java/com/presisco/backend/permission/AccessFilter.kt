package com.presisco.backend.permission

import com.presisco.backend.Preference
import java.io.IOException
import java.util.*
import javax.servlet.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

open class AccessFilter : Filter {
    private lateinit var rolePermittedMatchers: Map<String, PathMatcher>
    private lateinit var unrestrictedTypeMatcher: PathMatcher
    private lateinit var unrestrictedPathMatcher: PathMatcher

    private val logService: AccessLogService by lazy { AccessLogService() }

    @Throws(ServletException::class)
    override fun init(filterConfig: FilterConfig) {
        val permissionConfig = Preference.getMap("permission")["path"] as Map<String, *>

        val typeList = arrayListOf<String>()
        val pathList = arrayListOf<String>()
        (permissionConfig["unrestricted"] as List<String>).forEach {
            if (it.startsWith("*.")) {
                typeList.add(it.replace("*.", ""))
            } else {
                pathList.add(it)
            }
        }
        unrestrictedPathMatcher = SimplePathMatcher(pathList)
        unrestrictedTypeMatcher = ExtensionMatcher(typeList)

        rolePermittedMatchers = permissionConfig.filterKeys { it != "unrestricted" }.mapValues { SimplePathMatcher(it.value as List<String>) }
    }

    @Throws(IOException::class, ServletException::class)
    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
        val httpServletRequest = request as HttpServletRequest
        val httpServletResponse = response as HttpServletResponse

        val path = httpServletRequest.servletPath

        val extension = path.substringAfterLast(".")
        if (httpServletRequest.method.toLowerCase() == "options"
                || unrestrictedTypeMatcher.isAMatch(extension)
                || unrestrictedPathMatcher.isAMatch(path)) {
            chain.doFilter(httpServletRequest, httpServletResponse)
            return
        }

        val sessionRole = Definition.getSessionRole(request)
        if (sessionRole != null && rolePermittedMatchers[sessionRole]!!.isAMatch(path)) {
            logService.writeLog(
                    Definition.getSessionName(request)!!,
                    path,
                    Date(System.currentTimeMillis())
            )
            chain.doFilter(httpServletRequest, httpServletResponse)
        } else {
            httpServletResponse.status = HttpServletResponse.SC_UNAUTHORIZED
        }
    }

    override fun destroy() {}
}