package juuxel.enchanter.world.feature

import com.mojang.datafixers.Dynamic
import net.minecraft.block.BlockState
import net.minecraft.block.Blocks
import net.minecraft.server.world.ServerWorld
import net.minecraft.structure.StructurePlacementData
import net.minecraft.util.BlockRotation
import net.minecraft.util.Identifier
import net.minecraft.util.math.BlockPos
import net.minecraft.world.Heightmap
import net.minecraft.world.IWorld
import net.minecraft.world.gen.chunk.ChunkGenerator
import net.minecraft.world.gen.chunk.ChunkGeneratorConfig
import net.minecraft.world.gen.feature.Feature
import net.minecraft.world.gen.feature.FeatureConfig
import java.util.Random
import kotlin.math.min

@Suppress("ClassName") // shut up IDEA, this is what I want to name it :yeefrickinhaw:
abstract class `StructureFeature++`<FC : FeatureConfig>(
    configDeserializer: (Dynamic<*>) -> FC
) : Feature<FC>(configDeserializer) {
    protected abstract val structureId: Identifier

    protected open val fillBlock: BlockState = Blocks.DIRT.defaultState

    protected open fun createPlacementData(random: Random): StructurePlacementData =
        StructurePlacementData()
            .setRotation(BlockRotation.random(random))
            .setRandom(random)

    private fun untilOrReverse(a: Int, b: Int): IntRange =
        if (a < b) a until b
        else (b - 1)..a

    override fun generate(
        world: IWorld,
        generator: ChunkGenerator<out ChunkGeneratorConfig>,
        random: Random,
        pos: BlockPos,
        config: FC
    ): Boolean {
        val structureManager = (world.world as? ServerWorld ?: return false).structureManager
        val structure = structureManager.getStructure(structureId) ?: return false
        val placementData = createPlacementData(random)

        var y = pos.y
        val bounds = structure.calculateBoundingBox(placementData, pos)
        for (x in bounds.minX..bounds.maxX) {
            for (z in bounds.minZ..bounds.maxZ) {
                y = min(y, world.getTopY(Heightmap.Type.WORLD_SURFACE_WG, x, z))
            }
        }
        y -= 1 // into the ground

        structure.place(world, BlockPos(pos.x, y, pos.z), placementData)

        return true
    }
}
