package juuxel.enchanter.block

import juuxel.enchanter.Enchanter
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.fabricmc.fabric.api.block.FabricBlockSettings
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap
import net.minecraft.block.Block
import net.minecraft.block.Material
import net.minecraft.client.render.RenderLayer
import net.minecraft.item.BlockItem
import net.minecraft.item.Item
import net.minecraft.item.ItemGroup
import net.minecraft.item.ItemStack
import net.minecraft.sound.BlockSoundGroup
import net.minecraft.util.registry.Registry

object EnchanterBlocks {
    val ENCHANTED_GIFT: Block = EnchantedGiftBlock(
        FabricBlockSettings.of(Material.WOOL)
            .strength(1f, 1f)
            .sounds(BlockSoundGroup.WOOL)
            .build()
    )

    fun init() {
        register("enchanted_gift", ENCHANTED_GIFT) { block ->
            object : BlockItem(block, Settings().group(ItemGroup.DECORATIONS)) {
                override fun hasEnchantmentGlint(stack: ItemStack) = true
            }
        }
    }

    @Environment(EnvType.CLIENT)
    fun initClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(ENCHANTED_GIFT, RenderLayer.getCutout())
    }

    private fun register(name: String, block: Block, itemProvider: (Block) -> Item) {
        val id = Enchanter.id(name)
        Registry.register(Registry.BLOCK, id, block)
        Registry.register(Registry.ITEM, id, itemProvider(block))
    }
}
