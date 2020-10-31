package ray.mintcat.draftsman

import io.izzel.taboolib.module.inject.THook
import me.clip.placeholderapi.expansion.PlaceholderExpansion
import org.bukkit.entity.Player
import ray.mintcat.draftsman.Feeds

@THook
class PAPI: PlaceholderExpansion(){

    override fun getIdentifier(): String {
        return "draftsman"
    }

    override fun getAuthor(): String {
        return "Ray_Hughes"
    }

    override fun getVersion(): String {
        return "Last"
    }

    override fun persist(): Boolean {
        return true
    }

    override fun onPlaceholderRequest(player: Player, params: String): String {
        if (!player.isOnline) {
            return "N/A"
        }
        val param = params.split("_".toRegex())
        return when (param[0]) {
            "pitch" -> player.location.pitch.toString()
            "-yaw" -> (-player.location.yaw).toString()
            "yaw" -> (player.location.yaw).toString()
            "X" -> player.location.x.toString()
            "Y" -> player.location.y.toString()
            "Z" -> player.location.z.toString()
            else -> {
                "N/A"
            }
        }
    }

}