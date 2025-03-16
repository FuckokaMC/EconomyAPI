package mc.fuckoka.economyapi.infrastructure

import mc.fuckoka.dbconnector.Database
import mc.fuckoka.economyapi.domain.model.Money
import mc.fuckoka.economyapi.domain.model.Wallet
import mc.fuckoka.economyapi.domain.model.WalletID
import java.sql.SQLException
import java.util.*

class ConsistentWalletRepositoryImpl : WalletRepositoryImpl() {
    override fun find(id: WalletID): Wallet? {
        val connection = Database.getConnection() ?: throw SQLException()
        val stmt = connection.prepareStatement(
            """
                |SELECT
                |    wallets.id AS id,
                |    BIN_TO_UUID(wallets.owner) AS owner,
                |    (
                |        (SELECT SUM(mt.amount) FROM money_transactions AS mt WHERE mt.payee = wallets.id) -
                |        (SELECT SUM(mt.amount) FROM money_transactions AS mt WHERE mt.player = wallets.id)
                |    ) AS money
                |FROM wallets
                |WHERE id = ?
            """.trimMargin()
        )
        stmt.use {
            stmt.setInt(1, id.value)

            val resultSet = stmt.executeQuery()
            resultSet.use {
                while (resultSet.next()) {
                    return Wallet(
                        WalletID(resultSet.getInt("id")),
                        UUID.fromString(resultSet.getString("owner")),
                        Money(resultSet.getInt("money"))
                    )
                }
            }
        }

        return null
    }

    override fun findBy(owner: UUID): Wallet? {
        val connection = Database.getConnection() ?: throw SQLException()
        val stmt = connection.prepareStatement(
            """
                |SELECT
                |    wallets.id AS id,
                |    BIN_TO_UUID(wallets.owner) AS owner,
                |    (
                |        (SELECT SUM(mt.amount) FROM money_transactions AS mt WHERE mt.payee = wallets.id) -
                |        (SELECT SUM(mt.amount) FROM money_transactions AS mt WHERE mt.player = wallets.id)
                |    ) AS money
                |FROM wallets
                |WHERE owner = UUID_TO_BIN(?)
            """.trimMargin()
        )
        stmt.use {
            stmt.setString(1, owner.toString())

            val resultSet = stmt.executeQuery()
            resultSet.use {
                while (resultSet.next()) {
                    return Wallet(
                        WalletID(resultSet.getInt("id")),
                        UUID.fromString(resultSet.getString("owner")),
                        Money(resultSet.getInt("money"))
                    )
                }
            }
        }

        return null
    }
}
