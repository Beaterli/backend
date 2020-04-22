package com.presisco.backend

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer


@SpringBootApplication
open class Application {

    @Bean
    open fun corsConfigurer(): WebMvcConfigurer {
        return object : WebMvcConfigurer {
            override fun addCorsMappings(registry: CorsRegistry) {
                registry.addMapping("/**")
                        .allowedOrigins(Preference.getMap("permission")["allow_origin"] as String)
                        .allowCredentials(true)
            }
        }
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val config = if (args.isNotEmpty()) {
                args[0]
            } else {
                "sample/config.json"
            }
            try {
                Preference.loadFromJson(config)
            } catch (e: Exception) {
                println("first parameter must be config file path!")
                throw e
            }
            SpringApplication.run(Application::class.java, *args)
        }
    }
}
