package juuxel.enchanter.enchantment

import juuxel.enchanter.Enchanter
import net.minecraft.enchantment.Enchantment
import net.minecraft.enchantment.EnchantmentTarget
import net.minecraft.entity.EquipmentSlot
import net.minecraft.entity.effect.StatusEffects
import net.minecraft.util.registry.Registry

object EnchanterEnchantments {
    @JvmField val COBWEB: Enchantment = EffectEnchantment(
        Enchantment.Weight.UNCOMMON,
        EnchantmentTarget.WEAPON,
        arrayOf(EquipmentSlot.MAINHAND),
        StatusEffects.SLOWNESS
    )

    @JvmField val SWIFTNESS: Enchantment = SwiftnessCurseEnchantment()
    @JvmField val FROST: Enchantment = FrostEnchantment()
    @JvmField val INVULNERABILITY: Enchantment = InvulnerabilityCurseEnchantment()
    @JvmField val EFFICIENT_FARMING: Enchantment = EfficientFarmingEnchantment()

    fun init() {
        register("cobweb", COBWEB)
        register("swiftness", SWIFTNESS)
        register("frost", FROST)
        register("invulnerability", INVULNERABILITY)
        register("efficient_farming", EFFICIENT_FARMING)
    }

    private fun register(name: String, enchantment: Enchantment) =
        Registry.register(Registry.ENCHANTMENT, Enchanter.id(name), enchantment)
}
