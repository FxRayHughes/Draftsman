package ray.mintcat.draftsman

import com.sucy.skill.api.util.FlagData
import io.lumine.xikage.mythicmobs.adapters.AbstractEntity
import io.lumine.xikage.mythicmobs.adapters.bukkit.BukkitAdapter
import io.lumine.xikage.mythicmobs.io.MythicLineConfig
import io.lumine.xikage.mythicmobs.skills.ITargetedEntitySkill
import io.lumine.xikage.mythicmobs.skills.SkillMechanic
import io.lumine.xikage.mythicmobs.skills.SkillMetadata
import io.lumine.xikage.mythicmobs.skills.mechanics.CustomMechanic
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player

class MythicMobs(holder: CustomMechanic, mlc: MythicLineConfig) : SkillMechanic(holder.configLine, mlc), ITargetedEntitySkill {
    private val type: String
    private val independent: Boolean
    private val groups: String
    private val time: Int
    override fun castAtEntity(data: SkillMetadata, target: AbstractEntity): Boolean {
        val bukkitTarget = BukkitAdapter.adapt(target) as LivingEntity
        FlagData(bukkitTarget).addFlag("draft-${type}-${independent}-${groups}",time)
        return true
    }

    init {
        this.isAsyncSafe = false
        type = mlc.getString(arrayOf("t", "type"), "")
        independent = mlc.getBoolean(arrayOf("ind", "independent"), false)
        groups = mlc.getString(arrayOf("g","group"),"")
        time = mlc.getInteger(arrayOf("ti", "time"), 200)
    }
}