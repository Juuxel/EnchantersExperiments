package juuxel.enchanter.enchantment

import net.minecraft.enchantment.EnchantmentTarget
import net.minecraft.entity.EquipmentSlot
import net.minecraft.entity.effect.StatusEffects

class SwiftnessCurseEnchantment : EffectEnchantment(
    Weight.UNCOMMON,
    EnchantmentTarget.WEAPON,
    arrayOf(EquipmentSlot.MAINHAND),
    StatusEffects.SPEED
) {
    override fun isCursed() = true
}
