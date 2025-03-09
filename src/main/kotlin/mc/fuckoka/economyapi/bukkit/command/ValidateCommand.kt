package mc.fuckoka.economyapi.bukkit.command

import mc.fuckoka.commandframework.SubCommandBase
import mc.fuckoka.economyapi.EconomyAPI
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandSender

class ValidateCommand(private val plugin: EconomyAPI) : SubCommandBase("validate", "economyapi.commands.money.validate") {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        return false
    }

    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
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
