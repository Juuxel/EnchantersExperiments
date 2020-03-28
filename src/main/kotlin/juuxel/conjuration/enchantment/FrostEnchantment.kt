package juuxel.conjuration.enchantment

import juuxel.conjuration.effect.ConjurationEffects
import net.minecraft.enchantment.Enchantment
import net.minecraft.enchantment.EnchantmentTarget
import net.minecraft.entity.EquipmentSlot

class FrostEnchantment : EffectEnchantment(
    Enchantment.Weight.UNCOMMON,
    EnchantmentTarget.WEAPON,
    arrayOf(EquipmentSlot.MAINHAND),
    ConjurationEffects.FROST
) {
    override fun isTreasure() = true
}
