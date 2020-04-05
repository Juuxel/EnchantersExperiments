package juuxel.enchanter.structure

import com.mojang.datafixers.Dynamic
import net.minecraft.structure.StructureManager
import net.minecraft.structure.StructureStart
import net.minecraft.util.BlockRotation
import net.minecraft.util.math.BlockBox
import net.minecraft.util.math.BlockPos
import net.minecraft.world.biome.Biome
import net.minecraft.world.gen.chunk.ChunkGenerator
import net.minecraft.world.gen.feature.AbstractTempleFeature
import net.minecraft.world.gen.feature.DefaultFeatureConfig
import net.minecraft.world.gen.feature.StructureFeature

class CobwebFeature(
    configDeserializer: (Dynamic<*>) -> DefaultFeatureConfig
) : AbstractTempleFeature<DefaultFeatureConfig>(configDeserializer) {
    override fun getName() = "Cobweb"

    override fun getRadius(): Nothing = throw UnsupportedOperationException("bad mojang")

    override fun getSeedModifier() = 15000001

    override fun getStructureStartFactory() = StructureStartFactory(::Start)

    class Start(
        feature: StructureFeature<*>,
        chunkX: Int,
        chunkZ: Int,
        box: BlockBox,
        references: Int,
        seed: Long
    ) : StructureStart(feature, chunkX, chunkZ, box, references, seed) {
        override fun initialize(
            chunkGenerator: ChunkGenerator<*>,
            manager: StructureManager,
            x: Int,
            z: Int,
            biome: Biome
        ) {
            CobwebStructureGenerator.addPieces(
                manager,
                BlockPos(x * 16, 0, z * 16),
                BlockRotation.random(random),
                children
            )
            setBoundingBoxFromChildren()
        }
    }
}
