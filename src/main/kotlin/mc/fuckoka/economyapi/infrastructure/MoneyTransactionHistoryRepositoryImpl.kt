package mc.fuckoka.economyapi.infrastructure

import mc.fuckoka.dbconnector.Database
import mc.fuckoka.economyapi.domain.model.Money
import mc.fuckoka.economyapi.domain.model.MoneyTransaction
import mc.fuckoka.economyapi.domain.model.Reason
import mc.fuckoka.economyapi.domain.model.WalletID
import mc.fuckoka.economyapi.domain.repository.MoneyTransactionHistoryRepository
import java.sql.SQLException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class MoneyTransactionHistoryRepositoryImpl : MoneyTransactionHistoryRepository {
    override fun find(id: WalletID, start: Int?, offset: Int?): List<MoneyTransaction> {
        val result = mutableListOf<MoneyTransaction>()

        // 両方nullでなければページングする
        val isPaging = start != null && offset != null

        val connection = Database.connection ?: throw SQLException()
        val stmt = connection.prepareStatement(
            """
                    |SELECT
                    |    id,
                    |    player,
                    |    payee,
                    |    amount,
                    |    reason,
                    |    created_at
                    |FROM money_transactions
                    |WHERE player = ? OR payee = ?
                    |ORDER BY id DESC
                 """.trimMargin() + if (isPaging) " LIMIT ?, ?;" else ";"
        )
        stmt.use {
            stmt.setInt(1, id.value)
            stmt.setInt(2, id.value)
            if (isPaging) {
                stmt.setInt(3, start!!)
                stmt.setInt(4, offset!!)
            }

            val resultSet = stmt.executeQuery()
            resultSet.use {
                while (resultSet.next()) {
                    val from = resultSet.getObject("player") as Int?
                    val to = resultSet.getObject("payee") as Int?
                    val reason = resultSet.getObject("reason") as String?
                    result.add(
                        MoneyTransaction(
                            resultSet.getInt("id"),
                            if (from != null) WalletID(from) else null,
                            if (to != null) WalletID(to) else null,
                            Money(resultSet.getInt("amount")),
                            LocalDateTime.parse(
                                resultSet.getString("created_at"), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                            ),
                            if (reason != null) Reason(reason) else null
                        )
                    )
                }
            }
        }

        return result
    }

    override fun count(id: WalletID): Int {
        val connection = Database.connection ?: throw SQLException()

        val stmt = connection.prepareStatement(
            """
                |SELECT
                |    COUNT(*)
                |FROM money_transactions
                |WHERE player = ? OR payee = ?;
            """.trimMargin()
        )

        stmt.use {
            stmt.setInt(1, id.value)
            stmt.setInt(2, id.value)
            val resultSet = stmt.executeQuery()
            resultSet.use {
                return if (resultSet.next()) resultSet.getInt("COUNT(*)") else 0
            }
        }
    }

    override fun store(event: MoneyTransaction) {
        val connection = Database.connection ?: throw SQLException()

        val stmt = connection.prepareStatement(
            """
                |INSERT INTO money_transactions (player, payee, amount, reason, created_at) VALUES
                |(?, ?, ?, ?, ?);
            """.trimMargin()
        )

        stmt.use {
            stmt.setObject(1, if (event.from != null) event.from.value else null)
            stmt.setObject(2, if (event.to != null) event.to.value else null)
            stmt.setInt(3, event.amount.value)
            stmt.setObject(4, if (event.reason != null) event.reason.value else null)
            stmt.setString(5, event.datetime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))

            stmt.executeUpdate()
        }
    }
}
