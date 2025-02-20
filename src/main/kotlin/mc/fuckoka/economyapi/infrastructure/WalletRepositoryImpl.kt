package mc.fuckoka.economyapi.infrastructure

import mc.fuckoka.dbconnector.Database
import mc.fuckoka.economyapi.domain.model.Wallet
import mc.fuckoka.economyapi.domain.repository.WalletRepository
import java.sql.SQLException

abstract class WalletRepositoryImpl : WalletRepository {
    override fun store(wallet: Wallet) {
        val connection = Database.connection ?: throw SQLException()
        val stmt = connection.prepareStatement(
            """
                |SELECT
                |    1
                |FROM wallets
                |WHERE wallets.owner = UUID_TO_BIN(?)
                |LIMIT 1
            """.trimMargin()
        )
        val exists = stmt.use {
            stmt.setString(1, wallet.owner.toString())
            val resultSet = stmt.executeQuery()
            resultSet.use {
                resultSet.next()
            }
        }
        if (exists) {
            val stmt1 = connection.prepareStatement("UPDATE wallets SET money = ? WHERE id = ?")
            stmt1.use {
                stmt1.setInt(1, wallet.money.value)
                stmt1.setLong(2, wallet.id.value)
                stmt.executeUpdate()
            }
        } else {
            val stmt1 = connection.prepareStatement("INSERT INTO wallets (owner, money) VALUES (UUID_TO_BIN(?), ?)")
            stmt1.use {
                stmt1.setString(1, wallet.owner.toString())
                stmt1.setInt(2, wallet.money.value)
                stmt.executeUpdate()
            }
        }
    }
}
