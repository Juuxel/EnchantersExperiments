package juuxel.enchanter.enchantment

import juuxel.enchanter.util.seconds
import net.minecraft.enchantment.Enchantment
import net.minecraft.enchantment.EnchantmentTarget
import net.minecraft.entity.Entity
import net.minecraft.entity.EquipmentSlot
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.effect.StatusEffect
import net.minecraft.entity.effect.StatusEffectInstance

/**
 * An enchantment that applies a status effect.
 *
 * @property effect the applied effect
 */
open class EffectEnchantment(
    weight: Weight,
    target: EnchantmentTarget,
    slotTypes: Array<out EquipmentSlot>,
    private val effect: StatusEffect,
    private val maximumLevel: Int = 1 // TODO: Bump to 3 when I figure out a better way to get these enchantments
) : BaseEnchantment(weight, target, slotTypes) {
    override fun getMaximumLevel() = maximumLevel

    override fun differs(other: Enchantment): Boolean {
        return other !is EffectEnchantment
    }

    override fun onTargetDamaged(user: LivingEntity, target: Entity, level: Int) {
        if (target is LivingEntity) {
            target.addStatusEffect(StatusEffectInstance(effect, 3.seconds + 3.seconds * (level - 1), 1))
        }
    }
}
