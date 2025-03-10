package mc.fuckoka.economyapi.infrastructure

import mc.fuckoka.dbconnector.Database
import mc.fuckoka.economyapi.domain.model.Money
import mc.fuckoka.economyapi.domain.model.Wallet
import mc.fuckoka.economyapi.domain.model.WalletID
import mc.fuckoka.economyapi.domain.repository.WalletRepository
import java.sql.SQLException
import java.util.*

open class WalletRepositoryImpl : WalletRepository {
    override fun find(id: WalletID): Wallet? {
        val connection = Database.getConnection() ?: throw SQLException()
        val stmt = connection.prepareStatement(
            """
                |SELECT
                |    id,
                |    BIN_TO_UUID(owner) AS owner,
                |    money
                |FROM wallets
                |WHERE id = ?
            """.trimMargin()
        )
        stmt.use {
            stmt.setInt(1, id.value)

            val result = stmt.executeQuery()
            result.use {
                if (result.next()) {
                    return Wallet(
                        WalletID(result.getInt("id")),
                        UUID.fromString(result.getString("owner")),
                        Money(result.getInt("money"))
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
                |    id,
                |    BIN_TO_UUID(owner) AS owner,
                |    money
                |FROM wallets
                |WHERE owner = UUID_TO_BIN(?)
            """.trimMargin()
        )
        stmt.use {
            stmt.setString(1, owner.toString())

            val result = stmt.executeQuery()
            result.use {
                if (result.next()) {
                    return Wallet(
                        WalletID(result.getInt("id")),
                        UUID.fromString(result.getString("owner")),
                        Money(result.getInt("money"))
                    )
                }
            }
        }
        return null
    }

    override fun store(wallet: Wallet) {
        val connection = Database.getConnection() ?: throw SQLException()
        if (wallet is Wallet.NewWallet) {
            val stmt = connection.prepareStatement("INSERT INTO wallets (owner, money) VALUES (UUID_TO_BIN(?), ?)")
            stmt.use {
                stmt.setString(1, wallet.owner.toString())
                stmt.setInt(2, wallet.money.value)
                stmt.executeUpdate()
            }
        } else {
            val stmt = connection.prepareStatement("UPDATE wallets SET money = ? WHERE id = ?")
            stmt.use {
                stmt.setInt(1, wallet.money.value)
                stmt.setInt(2, wallet.id.value)
                stmt.executeUpdate()
            }
        }
    }
}
