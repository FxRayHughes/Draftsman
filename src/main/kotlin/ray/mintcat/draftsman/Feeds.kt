package ray.mintcat.draftsman

import eos.moe.dragoncore.api.CoreAPI
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.entity.Entity
import org.bukkit.entity.Player
import java.util.*
import java.util.concurrent.ConcurrentHashMap

object Feeds {

    val listMapPlayer = ConcurrentHashMap<Entity,MutableList<Player>>()
    val listMapKey = ConcurrentHashMap<Entity,MutableList<String>>()

    fun sendImpPlace(player: Player, entity:Entity, key:String, tick:Long,independent:Boolean){
        val uuid = if (independent){
            //注册进key
            val getKey = listMapKey[entity] ?: mutableListOf()
            getKey.add(key)
            listMapKey[entity] = getKey

            val getPlayer = listMapPlayer[entity] ?: mutableListOf()
            getPlayer.add(player)
            listMapPlayer[entity] = getPlayer

            key
        }else{
            UUID.randomUUID().toString()
        }
        CoreAPI.setPlayerWorldTexture(player
                , uuid
                , getLocation(entity.location,key)
                , Draftsman.settings.get("Groups.${key}.rotateX").toString().toFloat()
                , Draftsman.settings.get("Groups.${key}.rotateY").toString().toFloat()
                , if (Draftsman.settings.getBoolean("Groups.${key}.lock",false)){
            Draftsman.settings.get("Groups.${key}.rotateZ").toString().toFloat() + entity.location.yaw
        }else{
            Draftsman.settings.get("Groups.${key}.rotateZ").toString().toFloat()
        }
                , Draftsman.settings.getString("Groups.${key}.path")
                , Draftsman.settings.getInt("Groups.${key}.width").toString().toFloat()
                , Draftsman.settings.getInt("Groups.${key}.hight").toString().toFloat()
                , Draftsman.settings.getInt("Groups.${key}.alpha").toString().toFloat()
                , Draftsman.settings.getBoolean("Groups.${key}.follow",false)
                , Draftsman.settings.getBoolean("Groups.${key}.glow",false)
        )
        if (!independent){
            Bukkit.getScheduler().runTaskLater(Draftsman.plugin, Runnable {
                CoreAPI.removePlayerWorldTexture(player,uuid)
            },tick)
        }
    }

    fun sendImpFollow(player: Player, entity:Entity, key:String, tick:Long,independent:Boolean){
        val uuid = if (independent){
            //注册进key
            val getKey = listMapKey[entity] ?: mutableListOf()
            getKey.add(key)
            listMapKey[entity] = getKey

            val getPlayer = listMapPlayer[entity] ?: mutableListOf()
            getPlayer.add(player)
            listMapPlayer[entity] = getPlayer

            key
        }else{
            UUID.randomUUID().toString()
        }
        CoreAPI.setPlayerWorldTexture(player
                , uuid
                , getLocation(Location(entity.world,0.0,0.0, 0.0),key)
                , Draftsman.settings.get("Groups.${key}.rotateX",0).toString().toFloat()
                , Draftsman.settings.get("Groups.${key}.rotateY",0).toString().toFloat()
                , Draftsman.settings.get("Groups.${key}.rotateZ",0).toString().toFloat()
                , Draftsman.settings.getString("Groups.${key}.path")
                , Draftsman.settings.getInt("Groups.${key}.width",0).toString().toFloat()
                , Draftsman.settings.getInt("Groups.${key}.hight",0).toString().toFloat()
                , Draftsman.settings.getInt("Groups.${key}.alpha",0).toString().toFloat()
                , Draftsman.settings.getBoolean("Groups.${key}.follow",false)
                , Draftsman.settings.getBoolean("Groups.${key}.glow",false)
                , entity.uniqueId
                , Draftsman.settings.getBoolean("Groups.${key}.lock",false)
                , Draftsman.settings.get("Groups.${key}.extraX",0).toString().toFloat()
                , Draftsman.settings.get("Groups.${key}.extraY",0).toString().toFloat()
                , Draftsman.settings.get("Groups.${key}.extraZ",0).toString().toFloat()
        )
        if (!independent){
            Bukkit.getScheduler().runTaskLater(Draftsman.plugin, Runnable {
                CoreAPI.removePlayerWorldTexture(player,uuid)
            },tick)
        }
    }

    fun getLocation(location: Location, key: String): Location {
        return location.add(Draftsman.settings.getDouble("Groups.${key}.changeX")
                , Draftsman.settings.getDouble("Groups.${key}.changeY")
                , Draftsman.settings.getDouble("Groups.${key}.changeZ"))
    }
}
