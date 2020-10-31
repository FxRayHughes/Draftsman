package ray.mintcat.draftsman

import eos.moe.dragoncore.api.CoreAPI
import io.izzel.taboolib.module.command.base.*
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Entity
import org.bukkit.permissions.PermissionDefault

@BaseCommand(name = "draftsman", aliases = ["draf"], permissionDefault = PermissionDefault.OP)
class Command:BaseMainCommand() {

    @SubCommand
    var run: BaseSubCommand = object : BaseSubCommand() {
        override fun getDescription(): String {
            return "播放技能"
        }

        override fun getArguments(): Array<Argument> {
            return arrayOf(Argument("目标"), Argument("策略组"),Argument("持续时间"))
        }

        override fun onCommand(sender: CommandSender, command: Command, s: String, args: Array<String>) {
            val player = Bukkit.getPlayerExact(args[0])
            if (player == null) {
                sender.sendMessage("§7§l[§f§lWizard§7§l] §7目标 §f${args[0]} §7离线.")
                return
            }
            Feeds.sendImpPlace(player,player as Entity,args[1],args[2].toLong(),false)
        }
    }

    @SubCommand
    var runF: BaseSubCommand = object : BaseSubCommand() {
        override fun getDescription(): String {
            return "播放技能跟随玩家"
        }

        override fun getArguments(): Array<Argument> {
            return arrayOf(Argument("目标"), Argument("策略组"),Argument("持续时间"))
        }

        override fun onCommand(sender: CommandSender, command: Command, s: String, args: Array<String>) {
            val player = Bukkit.getPlayerExact(args[0])
            if (player == null) {
                sender.sendMessage("§7§l[§f§lWizard§7§l] §7目标 §f${args[0]} §7离线.")
                return
            }
            CoreAPI.setPlayerWorldTexture(player,"1",player.location,0F,0F,0F,"skill/1/0.png"
                    ,5F,5F,1F,false,false,player.uniqueId,false, 0F,0F,0F)
        }
    }

}