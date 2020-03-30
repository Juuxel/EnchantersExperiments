@file:JvmName("Extensions")
package juuxel.enchanter.util

import net.minecraft.enchantment.Enchantment
import net.minecraft.item.ItemStack
import net.minecraft.nbt.CompoundTag
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry

/** Converts seconds to ticks. */
val Int.seconds: Int
    get() = this * 20

/**
 * Runs the [predicate] on all enchantments on this stack, and returns true if it matches any enchantment.
 */
fun ItemStack.checkEnchantments(predicate: (Enchantment, level: Int) -> Boolean): Boolean {
    if (isEmpty) return false
    for (tag in enchantments) {
        val id = (tag as CompoundTag).getString("id")
        val level = tag.getInt("lvl")

        val result = Registry.ENCHANTMENT
            .getOrEmpty(Identifier.tryParse(id))
            .map {
                predicate(it, level)
            }
            .orElse(false)

        if (result) return true
    }

    return false
}
