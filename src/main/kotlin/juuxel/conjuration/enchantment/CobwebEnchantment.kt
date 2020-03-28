package juuxel.conjuration.enchantment

import juuxel.conjuration.util.seconds
import net.minecraft.enchantment.Enchantment
import net.minecraft.enchantment.EnchantmentTarget
import net.minecraft.entity.Entity
import net.minecraft.entity.EquipmentSlot
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.effect.StatusEffectInstance
import net.minecraft.entity.effect.StatusEffects

class CobwebEnchantment : Enchantment(Weight.UNCOMMON, EnchantmentTarget.WEAPON, arrayOf(EquipmentSlot.MAINHAND)) {
    override fun getMaximumLevel() = 3

    override fun differs(other: Enchantment): Boolean {
        return super.differs(other) // TODO
    }

    override fun onTargetDamaged(user: LivingEntity, target: Entity, level: Int) {
        if (target is LivingEntity) {
            target.addStatusEffect(StatusEffectInstance(StatusEffects.SLOWNESS, 3.seconds + 3.seconds * (level - 1), 1))
        }
    }
}
