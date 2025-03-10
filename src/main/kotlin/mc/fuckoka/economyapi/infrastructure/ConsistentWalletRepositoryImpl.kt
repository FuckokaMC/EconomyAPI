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
                |    currents.money AS money
                |FROM (
                |    SELECT
                |        player,
                |        (SELECT SUM(amount) FROM money_transactions AS mt2 WHERE mt2.payee = mt1.player) - SUM(amount) AS money
                |    FROM money_transactions AS mt1
                |    WHERE player = ?
                |    GROUP BY player
                |) AS currents
                |INNER JOIN wallets ON currents.player = wallets.id
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
                |    mt1.player AS id,
                |    BIN_TO_UUID(wallets.owner) AS owner,
                |    (SELECT SUM(amount) FROM money_transactions AS mt2 WHERE mt2.payee = mt1.player) - SUM(amount) AS money
                |FROM money_transactions AS mt1
                |INNER JOIN wallets ON mt1.player = wallets.id
                |WHERE wallets.owner = UUID_TO_BIN(?)
                |GROUP BY mt1.player, wallets.owner
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
