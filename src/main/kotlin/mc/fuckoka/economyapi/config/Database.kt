package mc.fuckoka.economyapi.config

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import java.sql.Connection
import java.sql.SQLException

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

    inline fun transaction(connection: Connection, block: () -> Unit): Boolean {
        try {
            connection.autoCommit = false
            block()
            connection.commit()
            return true
        } catch (e: SQLException) {
            connection.rollback()
            return false
        } finally {
            connection.autoCommit = true
        }
    }
}
