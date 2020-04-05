package juuxel.enchanter.enchantment

import net.minecraft.enchantment.Enchantment
import net.minecraft.enchantment.EnchantmentTarget
import net.minecraft.entity.EquipmentSlot

abstract class BaseEnchantment(
    weight: Weight,
    target: EnchantmentTarget,
    slotTypes: Array<out EquipmentSlot>
) : Enchantment(weight, target, slotTypes) {
    final override fun isTreasure() = false // TODO: set to true
}
