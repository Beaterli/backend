package com.presisco.backend.controller

import com.presisco.lazyjdbc.client.MapJdbcClient
import com.presisco.backend.Application
import com.presisco.backend.Preference
import com.presisco.backend.hikari.HikariLoader
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import java.text.SimpleDateFormat

@RunWith(SpringRunner::class)
@SpringBootTest(classes = [Application::class])
class PlatformHistoryControllerTest {
    init {
        Preference.loadFromJson("sample/config.json")
    }

    @Autowired
    private lateinit var wac: WebApplicationContext
    private lateinit var mockMvc: MockMvc
    private lateinit var client: MapJdbcClient

    @Before
    fun prepare() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build()
        client = MapJdbcClient(HikariLoader.getDataSource("singleton"))
        client.insert("tomcat_stat", listOf(
                mapOf("succeed" to 999,
                        "mismatch" to 999,
                        "missing" to 999,
                        "write_failure" to 999,
                        "received" to 999,
                        "parse_failure" to 999,
                        "alive" to "[\"a\"]",
                        "status" to "breakdown",
                        "timestamp" to SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(
                                System.currentTimeMillis()
                        )
                ),
                mapOf("succeed" to 999,
                        "mismatch" to 999,
                        "missing" to 999,
                        "write_failure" to 999,
                        "received" to 999,
                        "parse_failure" to 999,
                        "alive" to "[\"a\"]",
                        "status" to "breakdown",
                        "timestamp" to SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(
                                System.currentTimeMillis() - 1000 * 3600
                        )
                ),
                mapOf("succeed" to 999,
                        "mismatch" to 999,
                        "missing" to 999,
                        "write_failure" to 999,
                        "received" to 999,
                        "parse_failure" to 999,
                        "alive" to "[\"a\"]",
                        "status" to "breakdown",
                        "timestamp" to SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(
                                System.currentTimeMillis() - 1000 * 3600 * 24
                        )
                ),
                mapOf("succeed" to 999,
                        "mismatch" to 999,
                        "missing" to 999,
                        "write_failure" to 999,
                        "received" to 999,
                        "parse_failure" to 999,
                        "alive" to "[\"a\"]",
                        "status" to "breakdown",
                        "timestamp" to SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(
                                System.currentTimeMillis() - 6000 * 3600 * 24
                        )
                ),
                mapOf("succeed" to 999,
                        "mismatch" to 999,
                        "missing" to 999,
                        "write_failure" to 999,
                        "received" to 999,
                        "parse_failure" to 999,
                        "alive" to "[\"a\"]",
                        "status" to "breakdown",
                        "timestamp" to SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(
                                System.currentTimeMillis() - 7000 * 3600 * 24
                        )
                ),
                mapOf("succeed" to 999,
                        "mismatch" to 999,
                        "missing" to 999,
                        "write_failure" to 999,
                        "received" to 999,
                        "parse_failure" to 999,
                        "alive" to "[\"a\"]",
                        "status" to "breakdown",
                        "timestamp" to SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(
                                System.currentTimeMillis() - 8000 * 3600 * 24
                        )
                )
        ))
        client.insert("kafka_stat", listOf(
                mapOf("write" to 999,
                        "read" to 999,
                        "alive" to "[\"a\"]",
                        "status" to "breakdown",
                        "timestamp" to SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(
                                System.currentTimeMillis()
                        )
                ),
                mapOf("write" to 999,
                        "read" to 999,
                        "alive" to "[\"a\"]",
                        "status" to "breakdown",
                        "timestamp" to SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(
                                System.currentTimeMillis() - 1000 * 3600
                        )
                ),
                mapOf("write" to 999,
                        "read" to 999,
                        "alive" to "[\"a\"]",
                        "status" to "breakdown",
                        "timestamp" to SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(
                                System.currentTimeMillis() - 1000 * 3600 * 24
                        )
                ),
                mapOf("write" to 999,
                        "read" to 999,
                        "alive" to "[\"a\"]",
                        "status" to "breakdown",
                        "timestamp" to SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(
                                System.currentTimeMillis() - 6000 * 3600 * 24
                        )
                ),
                mapOf("write" to 999,
                        "read" to 999,
                        "alive" to "[\"a\"]",
                        "status" to "breakdown",
                        "timestamp" to SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(
                                System.currentTimeMillis() - 7000 * 3600 * 24
                        )
                ),
                mapOf("write" to 999,
                        "read" to 999,
                        "alive" to "[\"a\"]",
                        "status" to "breakdown",
                        "timestamp" to SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(
                                System.currentTimeMillis() - 8000 * 3600 * 24
                        )
                )
        ))
    }

    @Test
    fun validate() {
        println(mockMvc.perform(
                MockMvcRequestBuilders.get("/history?type=tomcat&unit=day")
                        .characterEncoding("utf-8"))
                .andReturn().response.contentAsString)
        println(mockMvc.perform(
                MockMvcRequestBuilders.get("/history?type=tomcat&unit=hour")
                        .characterEncoding("utf-8"))
                .andReturn().response.contentAsString)
        println(mockMvc.perform(
                MockMvcRequestBuilders.get("/history?type=kafka&unit=day")
                        .characterEncoding("utf-8"))
                .andReturn().response.contentAsString)
        println(mockMvc.perform(
                MockMvcRequestBuilders.get("/history?type=kafka&unit=hour")
                        .characterEncoding("utf-8"))
                .andReturn().response.contentAsString)
    }

    @After
    fun cleanup() {
        client.delete("DELETE\n" +
                "FROM tomcat_stat\n" +
                "WHERE succeed = 999 AND missing = 999")
        client.delete("DELETE\n" +
                "FROM\n" +
                "kafka_stat\n" +
                "WHERE\n" +
                "`read`= 999 AND `write`=999")
    }
}