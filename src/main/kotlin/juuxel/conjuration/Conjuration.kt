package juuxel.conjuration

import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.util.Identifier

object Conjuration {
    const val ID: String = "contemporary_conjuration"

    fun id(path: String): Identifier = Identifier(ID, path)

    fun init() {

    }

    @Environment(EnvType.CLIENT)
    fun initClient() {

    }
}
