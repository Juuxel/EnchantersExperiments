package juuxel.conjuration.effect

import net.minecraft.entity.LivingEntity
import net.minecraft.entity.damage.DamageSource
import net.minecraft.entity.effect.StatusEffect
import net.minecraft.entity.effect.StatusEffectType

class FrostEffect : StatusEffect(StatusEffectType.HARMFUL, 0xABEBF4) {
    override fun canApplyUpdateEffect(duration: Int, amplifier: Int): Boolean {
        val k = 75 shr amplifier
        return k <= 0 || duration % k == 0
    }

    override fun applyUpdateEffect(entity: LivingEntity, amplifier: Int) {
        if (entity.health > 1.0f) {
            entity.damage(DamageSource.MAGIC, 1.0f)
        }
    }
}
