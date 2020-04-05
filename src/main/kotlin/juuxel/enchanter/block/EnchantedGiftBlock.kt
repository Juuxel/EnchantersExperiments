package juuxel.enchanter.block

import juuxel.enchanter.enchantment.EnchanterEnchantments
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntity
import net.minecraft.enchantment.Enchantment
import net.minecraft.entity.EntityContext
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.HoeItem
import net.minecraft.item.ItemStack
import net.minecraft.item.SwordItem
import net.minecraft.util.WeightedPicker
import net.minecraft.util.math.BlockPos
import net.minecraft.util.shape.VoxelShape
import net.minecraft.world.BlockView
import net.minecraft.world.World

class EnchantedGiftBlock(settings: Settings) : Block(settings) {
    override fun afterBreak(
        world: World,
        player: PlayerEntity,
        pos: BlockPos,
        state: BlockState,
        blockEntity: BlockEntity?,
        stack: ItemStack
    ) {
        super.afterBreak(world, player, pos, state, blockEntity, stack)
        val realStack = player.mainHandStack
        when (realStack.item) {
            is SwordItem -> {
                val random = world.random
                val (enchantment, _) = WeightedPicker.getRandom(random, SWORD_ENCHANTMENTS)
                val bound = enchantment.maximumLevel - enchantment.minimumLevel
                val randomOffset = if (bound == 0) 0 else random.nextInt(bound)
                val level = randomOffset + enchantment.minimumLevel
                realStack.addEnchantment(enchantment, level)
            }
            is HoeItem -> {
                realStack.addEnchantment(EnchanterEnchantments.EFFICIENT_FARMING, 1)
            }
        }
    }

    override fun calcBlockBreakingDelta(state: BlockState, player: PlayerEntity, world: BlockView, pos: BlockPos) =
        player.mainHandStack.let { stack ->
            if (stack.item is SwordItem || stack.item is HoeItem) super.calcBlockBreakingDelta(state, player, world, pos)
            else 0f
        }

    override fun getOutlineShape(state: BlockState, view: BlockView, pos: BlockPos, context: EntityContext) = SHAPE

    companion object {
        private val SHAPE: VoxelShape = createCuboidShape(2.0, 0.0, 2.0, 14.0, 12.0, 14.0)

        private val SWORD_ENCHANTMENTS: List<EnchantmentEntry> = listOf(
            EnchantmentEntry(EnchanterEnchantments.COBWEB, 15),
            EnchantmentEntry(EnchanterEnchantments.SWIFTNESS, 15),
            EnchantmentEntry(EnchanterEnchantments.FROST, 10),
            EnchantmentEntry(EnchanterEnchantments.SHULKER, 4)
        )
    }

    private data class EnchantmentEntry(val enchantment: Enchantment, val weight: Int) : WeightedPicker.Entry(weight)
}
