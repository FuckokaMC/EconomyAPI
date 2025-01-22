package mc.fuckoka.economyapi.config

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import java.sql.Connection

object Database {
    private lateinit var dataSource: HikariDataSource

    /**
     * Database.transaction{}の外だとnullになります
     */
    var connection: Connection? = null
        private set

    fun init(driver: String, url: String, id: String, pw: String) {
        val config = HikariConfig().apply {
            driverClassName = driver
            jdbcUrl = url
            username = id
            password = pw
        }
        dataSource = HikariDataSource(config)
    }

    fun <T> transaction(block: () -> T): T {
        var result: T? = null

        connection = dataSource.connection
        try {
            connection!!.autoCommit = false
            result = block()
            connection!!.commit()
        } catch (e: Exception) {
            connection!!.rollback()
        } finally {
            connection!!.autoCommit = true
            connection!!.close()
        }
        connection = null

        return result!!
    }

    fun close() {
        dataSource.close()
    }
}
