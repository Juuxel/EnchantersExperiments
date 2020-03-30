package juuxel.enchanter.enchantment

import juuxel.enchanter.effect.EnchanterEffects
import net.minecraft.enchantment.Enchantment
import net.minecraft.enchantment.EnchantmentTarget
import net.minecraft.entity.EquipmentSlot

class FrostEnchantment : EffectEnchantment(
    Enchantment.Weight.UNCOMMON,
    EnchantmentTarget.WEAPON,
    arrayOf(EquipmentSlot.MAINHAND),
    EnchanterEffects.FROST
) {
    override fun isTreasure() = true
}
