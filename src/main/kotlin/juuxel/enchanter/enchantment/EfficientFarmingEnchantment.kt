package juuxel.enchanter.enchantment

import juuxel.enchanter.util.checkEnchantments
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.enchantment.Enchantment
import net.minecraft.enchantment.EnchantmentTarget
import net.minecraft.entity.EquipmentSlot
import net.minecraft.item.HoeItem
import net.minecraft.item.ItemStack
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

class EfficientFarmingEnchantment : Enchantment(
    Weight.RARE,
    EnchantmentTarget.FISHING_ROD, // irritatered
    arrayOf(EquipmentSlot.MAINHAND)
) {
    override fun isAcceptableItem(stack: ItemStack) = stack.item is HoeItem

    companion object {
        @JvmStatic
        fun handleTilling(world: World, pos: BlockPos, stack: ItemStack, tilledBlocks: Map<Block, BlockState>) {
            if (world.isClient) return
            val hasEnchantment = stack.checkEnchantments { enchantment, _ -> enchantment === EnchanterEnchantments.EFFICIENT_FARMING }
            if (!hasEnchantment) return

            fun BlockPos.Mutable.move(x: Int, y: Int, z: Int) = setOffset(x, y, z)

            val mutable = BlockPos.Mutable(pos)
            mutable.move(-1, 0, -1)
            for (x in -1..1) {
                for (z in -1..1) {
                    if (x == 0 && z == 0) {
                        mutable.move(0, 0, 1)
                        continue
                    }

                    mutable.move(0, 1, 0)
                    val hasAir = world.getBlockState(mutable).isAir
                    mutable.move(0, -1, 0)

                    val current = world.getBlockState(mutable).block
                    val newState = tilledBlocks[current]

                    if (hasAir && newState != null) {
                        world.setBlockState(mutable, newState, 11)
                    }

                    mutable.move(0, 0, 1)
                }
                mutable.move(1, 0, -3)
            }
        }
    }
}