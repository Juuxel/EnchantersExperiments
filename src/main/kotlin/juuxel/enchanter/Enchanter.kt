package juuxel.enchanter

import juuxel.enchanter.block.EnchanterBlocks
import juuxel.enchanter.effect.EnchanterEffects
import juuxel.enchanter.enchantment.EnchanterEnchantments
import juuxel.enchanter.networking.EnchanterNetworking
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.util.Identifier

object Enchanter {
    const val ID: String = "enchanters_experiments"

    fun id(path: String): Identifier = Identifier(ID, path)

    fun init() {
        EnchanterBlocks.init()
        EnchanterEffects.init()
        EnchanterEnchantments.init()
        EnchanterNetworking.init()
    }

    @Environment(EnvType.CLIENT)
    fun initClient() {
        EnchanterBlocks.initClient()
        EnchanterNetworking.initClient()
    }
}
