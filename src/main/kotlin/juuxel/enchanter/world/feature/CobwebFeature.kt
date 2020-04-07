package juuxel.enchanter.world.feature

import com.mojang.datafixers.Dynamic
import juuxel.enchanter.Enchanter
import net.minecraft.world.gen.feature.DefaultFeatureConfig

class CobwebFeature(
    configDeserializer: (Dynamic<*>) -> DefaultFeatureConfig
) : `StructureFeature++`<DefaultFeatureConfig>(configDeserializer) {
    override val structureId = Enchanter.id("cobweb")
}
