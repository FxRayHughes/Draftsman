package ray.mintcat.draftsman

import com.sucy.skill.api.event.FlagApplyEvent
import com.sucy.skill.api.event.FlagExpireEvent
import eos.moe.dragoncore.api.CoreAPI
import io.izzel.taboolib.module.inject.TListener
import io.lumine.xikage.mythicmobs.api.bukkit.events.MythicMechanicLoadEvent
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener


@TListener
class Listener : Listener {
    //被添加上Flag运行的事件
    //Flag解析格式: {draft-[Type]-[Independent]-[Groups]}
    //Type: place [放置在实体位置上]
    //Type: follow [跟随实体移动]
    //Independent: 图片是否唯一 false 是不唯一 true是唯一
    @EventHandler
    fun onFlagApplyEvent(event: FlagApplyEvent) {
        val flag = event.flag.split("-")
        if (!flag[0].equals("draft")){
            return
        }
        //Map格式:
        when(flag[1]){
            "place" -> {
                if (event.entity is Player) {
                    Feeds.sendImpPlace(event.entity as Player, event.entity, flag[3], event.ticks.toLong(), flag[2].toBoolean())
                }
                for (player in event.entity.getNearbyEntities(25.0, 25.0, 25.0)) {
                    if (player !is Player) {
                        return
                    }
                    Feeds.sendImpPlace(player, event.entity, flag[3], event.ticks.toLong(), flag[2].toBoolean())
                }
            }
            "follow" -> {
                if (event.entity is Player) {
                    Feeds.sendImpFollow(event.entity as Player, event.entity, flag[3], event.ticks.toLong(), flag[2].toBoolean())
                }
                for (player in event.entity.getNearbyEntities(25.0, 25.0, 25.0)) {
                    if (player !is Player) {
                        return
                    }
                    Feeds.sendImpFollow(player, event.entity, flag[3], event.ticks.toLong(), flag[2].toBoolean())
                }
            }
        }
    }

    @EventHandler
    fun onFlagExpireEvent(event: FlagExpireEvent){
        val flag = event.flag.split("-")
        if (!flag[0].equals("draft") || !flag[2].equals("true")){
            return
        }
        val listMap = Feeds.listMapKey[event.entity] ?: return
        if (!listMap.contains(flag[3])){
            return
        }
        //获取player
        val listPlayer = Feeds.listMapPlayer.get(event.entity) ?: return
        for (i in listPlayer){
            CoreAPI.removePlayerWorldTexture(i, flag[3])
            listPlayer.remove(i)
        }
        listMap.remove(flag[3])
    }

    @EventHandler
    fun onMythicMechanicLoad(e: MythicMechanicLoadEvent) {
        if (e.mechanicName.equals("draftsman", ignoreCase = true)) {
            e.register(MythicMobs(e.container, e.config))
        }
    }
}