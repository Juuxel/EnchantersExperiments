package juuxel.enchanter.enchantment

import juuxel.enchanter.Enchanter
import net.minecraft.enchantment.Enchantment
import net.minecraft.enchantment.EnchantmentTarget
import net.minecraft.entity.EquipmentSlot
import net.minecraft.entity.effect.StatusEffects
import net.minecraft.util.registry.Registry

object EnchanterEnchantments {
    val COBWEB: Enchantment = EffectEnchantment(
        Enchantment.Weight.UNCOMMON,
        EnchantmentTarget.WEAPON,
        arrayOf(EquipmentSlot.MAINHAND),
        StatusEffects.SLOWNESS
    )

    val SWIFTNESS: Enchantment = SwiftnessCurseEnchantment()
    val FROST: Enchantment = FrostEnchantment()
    val INVULNERABILITY: Enchantment = InvulnerabilityCurseEnchantment()

    fun init() {
        register("cobweb", COBWEB)
        register("swiftness", SWIFTNESS)
        register("frost", FROST)
        register("invulnerability", INVULNERABILITY)
    }

    private fun register(name: String, enchantment: Enchantment) =
        Registry.register(Registry.ENCHANTMENT, Enchanter.id(name), enchantment)
}
