package juuxel.conjuration.enchantment

import juuxel.conjuration.Conjuration
import net.minecraft.enchantment.Enchantment
import net.minecraft.util.registry.Registry

object ConjurationEnchantments {
    val COBWEB: Enchantment = CobwebEnchantment()

    fun init() {
        register("cobweb", COBWEB)
    }

    private fun register(name: String, enchantment: Enchantment) =
        Registry.register(Registry.ENCHANTMENT, Conjuration.id(name), enchantment)
}
