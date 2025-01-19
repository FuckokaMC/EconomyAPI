package mc.fuckoka.economyapi.config

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import java.sql.Connection

object Database {
    private lateinit var dataSource: HikariDataSource

    fun init(driver: String, url: String, id: String, pw: String) {
        val config = HikariConfig().apply {
            driverClassName = driver
            jdbcUrl = url
            username = id
            password = pw
        }
        dataSource = HikariDataSource(config)
    }

    fun connect(): Connection {
        return dataSource.connection
    }

    fun close() {
        dataSource.close()
    }
}
