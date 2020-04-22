package com.presisco.backend.permission

import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Service

@Service
class UserControlService {

    @Bean
    fun passwordEncoder() = BCryptPasswordEncoder()

    fun getUserInfo(username: String, password: String): Map<String, *> {
        val client = PermissionJdbcClient
        val encoder = passwordEncoder()

        val userInfoList = client.buildSelect("*")
                .from("user")
                .where("username", "=", username)
                .execute()
        if (userInfoList.isEmpty()) {
            return mapOf<String, Any?>()
        }

        val info = userInfoList[0]
        val definedPassword = info["password"] as String
        return if (encoder.matches(password, definedPassword)) {
            info
        } else {
            mapOf<String, Any?>()
        }
    }

    fun changePassword(username: String, password: String) {
        val client = PermissionJdbcClient
        val encoder = passwordEncoder()
        client.update("user")
                .set("password" to encoder.encode(password))
                .where("username", "=", username)
                .execute()
    }

    fun deleteUser(username: String) = PermissionJdbcClient.deleteFrom("user")
            .where("username", "=", username)
            .execute()

    fun addUser(userInfo: MutableMap<String, Any?>) {

        val client = PermissionJdbcClient
        val encoder = passwordEncoder()

        userInfo["password"] = encoder.encode(userInfo["password"] as String)

        client.insertInto("user")
                .columns(*userInfo.keys.toTypedArray())
                .values(*userInfo.values.toTypedArray())
                .execute()
    }

    fun users() = PermissionJdbcClient.buildSelect("*")
            .from("user")
            .execute()

}