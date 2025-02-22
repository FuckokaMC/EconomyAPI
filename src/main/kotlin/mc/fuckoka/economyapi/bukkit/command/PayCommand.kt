package mc.fuckoka.economyapi.bukkit.command

import mc.fuckoka.commandframework.SubCommandBase
import mc.fuckoka.economyapi.EconomyAPI
import net.milkbowl.vault.economy.EconomyResponse
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class PayCommand(private val plugin: EconomyAPI) : SubCommandBase("pay", "economyapi.commands.money.pay") {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (args.size < 2 || sender !is Player) {
            return false
        }

        val target = Bukkit.getOfflinePlayer(args[0])
        val amount = args[1].toIntOrNull() ?: return false

        if (!plugin.vault.hasAccount(target)) {
            sender.sendMessage(plugin.messages.getString("no-data")!!.format(args[0]))
            return true
        }

        if (!plugin.vault.has(sender, amount.toDouble())) {
            sender.sendMessage(plugin.messages.getString("no-money")!!)
            return true
        }

        val response = plugin.vault.pay(sender, target, amount.toDouble())
        if (response.first.type == EconomyResponse.ResponseType.SUCCESS) {
            sender.sendMessage(
                plugin.messages.getString("pay")!!.format(
                    target.name,
                    plugin.vault.format(response.first.amount),
                    plugin.vault.currencyNameSingular(),
                    plugin.vault.format(response.first.balance),
                    plugin.vault.currencyNameSingular()
                )
            )
        } else {
            sender.sendMessage(plugin.messages.getString("error")!!.format("/money pay ${args[0]} ${args[1]}"))
        }

        return true
    }

    override fun onTabComplete(
        sender: CommandSender, command: Command, label: String, args: Array<out String>
    ): MutableList<String> {
        val list = mutableListOf<String>()
        if (args.size == 1) {
            Bukkit.getOfflinePlayers().filter { it.name?.startsWith(args[0]) ?: false }.forEach {
                list.add(it.name!!)
            }
        }
        return list
    }
}
