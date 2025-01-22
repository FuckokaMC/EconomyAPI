package mc.fuckoka.economyapi.bukkit.command

import mc.fuckoka.commandframework.CommandBase
import org.bukkit.command.Command
import org.bukkit.command.CommandSender

class MoneyCommand : CommandBase() {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: List<String>): Boolean {
        TODO("Not yet implemented")
    }

    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        label: String,
        args: List<String>
    ): MutableList<String>? {
        TODO("Not yet implemented")
    }
}
