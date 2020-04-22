package com.presisco.backend.config

import com.presisco.backend.permission.AccessFilter
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order
import org.springframework.web.filter.CharacterEncodingFilter
import javax.servlet.Filter

@Configuration
open class WebXMLConfig {
    /**
     * encode filter
     */
    @Bean
    @Order(1)
    open fun registerEncodingFilter(): FilterRegistrationBean<Filter> {
        val registration = FilterRegistrationBean<Filter>()
        registration.filter = CharacterEncodingFilter()
        registration.addInitParameter("encoding", "UTF-8")
        registration.addInitParameter("forceEncoding", "true")
        registration.addUrlPatterns("/*")

        return registration
    }

    @Bean
    @Order(2)
    open fun registerAccessFilter(): FilterRegistrationBean<Filter> {
        val registration = FilterRegistrationBean<Filter>()
        registration.filter = AccessFilter()
        registration.addUrlPatterns("/*")

        return registration
    }

}