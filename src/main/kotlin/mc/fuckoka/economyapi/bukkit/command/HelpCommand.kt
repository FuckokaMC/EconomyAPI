package mc.fuckoka.economyapi.bukkit.command

import mc.fuckoka.commandframework.SubCommandBase
import mc.fuckoka.economyapi.EconomyAPI
import org.bukkit.command.Command
import org.bukkit.command.CommandSender

class HelpCommand(private val plugin: EconomyAPI) : SubCommandBase("help", "economyapi.commands.money.help") {
    companion object {
        private val messages = mapOf(
            "economyapi.commands.money.help" to "help.header",
            "economyapi.commands.money.show" to "help.money",
            "economyapi.commands.money.show.other" to "help.money.other",
            "economyapi.commands.money.pay" to "help.pay",
            "economyapi.commands.money.give" to "help.give",
            "economyapi.commands.money.take" to "help.take",
            "economyapi.commands.money.log" to "help.log",
            "economyapi.commands.money.log.other" to "help.log.other"
        )
    }

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        messages.filter { sender.hasPermission(it.key) }.values.forEach {
            sender.sendMessage(plugin.messages.getString(it)!!)
        }

        return true
    }

    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): MutableList<String>? {
        return null
    }
}
