package juuxel.enchanter.structure

import com.mojang.datafixers.Dynamic
import com.mojang.datafixers.util.Pair
import juuxel.enchanter.Enchanter
import net.minecraft.nbt.CompoundTag
import net.minecraft.structure.PoolStructurePiece
import net.minecraft.structure.StructureManager
import net.minecraft.structure.StructureStart
import net.minecraft.structure.pool.SinglePoolElement
import net.minecraft.structure.pool.StructurePool
import net.minecraft.structure.pool.StructurePoolBasedGenerator
import net.minecraft.structure.pool.StructurePoolElement
import net.minecraft.util.BlockRotation
import net.minecraft.util.Identifier
import net.minecraft.util.math.BlockBox
import net.minecraft.util.math.BlockPos
import net.minecraft.world.biome.Biome
import net.minecraft.world.biome.source.BiomeAccess
import net.minecraft.world.gen.chunk.ChunkGenerator
import net.minecraft.world.gen.feature.DefaultFeatureConfig
import net.minecraft.world.gen.feature.StructureFeature
import java.util.Random

// TODO: eradicate jigsaw helpers from non-jigsaw structures
class CobwebStructureFeature(configDeserializer: (Dynamic<*>) -> DefaultFeatureConfig) : StructureFeature<DefaultFeatureConfig>(configDeserializer) {
    override fun getName() = "Cobweb"

    override fun getRadius(): Nothing = throw UnsupportedOperationException("bad mojang")

    override fun shouldStartAt(
        biomeAccess: BiomeAccess,
        chunkGenerator: ChunkGenerator<*>,
        random: Random,
        chunkX: Int,
        chunkZ: Int,
        biome: Biome
    ) = true

    override fun getStructureStartFactory() = StructureStartFactory(::Start)

    companion object {
        private val POOL_ID: Identifier = Enchanter.id("cobweb_pool")

        init {
            StructurePoolBasedGenerator.REGISTRY.add(
                StructurePool(
                    POOL_ID,
                    Identifier("minecraft", "empty"),
                    listOf<Pair<StructurePoolElement, Int>>(
                        Pair.of(
                            SinglePoolElement(
                                "${Enchanter.ID}:cobweb",
                                emptyList(),
                                StructurePool.Projection.RIGID
                            ),
                            1
                        )
                    ),
                    StructurePool.Projection.RIGID
                )
            )
        }
    }

    class Start(
        feature: StructureFeature<*>,
        chunkX: Int,
        chunkZ: Int,
        box: BlockBox,
        references: Int,
        l: Long
    ) : StructureStart(feature, chunkX, chunkZ, box, references, l) {
        override fun initialize(
            chunkGenerator: ChunkGenerator<*>,
            structureManager: StructureManager,
            x: Int,
            z: Int,
            biome: Biome
        ) {
            StructurePoolBasedGenerator.addPieces(
                POOL_ID, 7,
                StructurePoolBasedGenerator.PieceFactory(::Piece),
                chunkGenerator,
                structureManager,
                BlockPos(16 * x, 150, 16 * z),
                children,
                random
            )
        }

    }
    class Piece : PoolStructurePiece {
        constructor(
            manager: StructureManager,
            poolElement: StructurePoolElement,
            pos: BlockPos,
            groundLevelDelta: Int,
            rotation: BlockRotation,
            boundingBox: BlockBox
        ) : super(EnchanterStructures.COBWEB_PIECE, manager, poolElement, pos, groundLevelDelta, rotation, boundingBox)

        constructor(manager: StructureManager, tag: CompoundTag) : super(manager, tag, EnchanterStructures.COBWEB_PIECE)
    }
}
