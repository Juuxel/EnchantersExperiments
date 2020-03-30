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
 * @property treasure whether this enchantment is a treasure and does not appear in enchanting tables
 */
open class EffectEnchantment(
    weight: Weight,
    target: EnchantmentTarget,
    slotTypes: Array<out EquipmentSlot>,
    private val effect: StatusEffect,
    private val treasure: Boolean = false
) : Enchantment(weight, target, slotTypes) {
    override fun getMaximumLevel() = 3

    override fun differs(other: Enchantment): Boolean {
        return other is EffectEnchantment
    }

    override fun onTargetDamaged(user: LivingEntity, target: Entity, level: Int) {
        if (target is LivingEntity) {
            target.addStatusEffect(StatusEffectInstance(effect, 3.seconds + 3.seconds * (level - 1), 1))
        }
    }

    override fun isTreasure() = treasure
}
