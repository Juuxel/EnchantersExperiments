package juuxel.conjuration

import juuxel.conjuration.effect.ConjurationEffects
import juuxel.conjuration.enchantment.ConjurationEnchantments
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.util.Identifier

object Conjuration {
    const val ID: String = "contemporary_conjuration"

    fun id(path: String): Identifier = Identifier(ID, path)

    fun init() {
        ConjurationEffects.init()
        ConjurationEnchantments.init()
    }

    @Environment(EnvType.CLIENT)
    fun initClient() {
    }
}
