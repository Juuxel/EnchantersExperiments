package juuxel.enchanter.structure

import juuxel.enchanter.Enchanter
import net.minecraft.nbt.CompoundTag
import net.minecraft.structure.SimpleStructurePiece
import net.minecraft.structure.StructureManager
import net.minecraft.structure.StructurePiece
import net.minecraft.structure.StructurePlacementData
import net.minecraft.structure.processor.BlockIgnoreStructureProcessor
import net.minecraft.util.BlockMirror
import net.minecraft.util.BlockRotation
import net.minecraft.util.Identifier
import net.minecraft.util.math.BlockBox
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.ChunkPos
import net.minecraft.world.Heightmap
import net.minecraft.world.IWorld
import net.minecraft.world.gen.chunk.ChunkGenerator
import java.util.Random

object CobwebStructureGenerator {
    private val ID: Identifier = Enchanter.id("cobweb")

    fun addPieces(
        manager: StructureManager,
        pos: BlockPos,
        rotation: BlockRotation,
        pieces: MutableList<StructurePiece>
    ) {
        pieces += Piece(manager, pos, rotation)
    }

    class Piece : SimpleStructurePiece {
        @Suppress("ANNOTATION_TARGETS_NON_EXISTENT_ACCESSOR")
        @get:JvmName("_getRotation") // to suppress a compilation error
        private val rotation: BlockRotation

        constructor(
            manager: StructureManager,
            pos: BlockPos,
            rotation: BlockRotation
        ) : super(EnchanterStructures.COBWEB_PIECE, 0) {
            this.pos = pos
            this.rotation = rotation
            initStructureData(manager)
        }

        constructor(manager: StructureManager, tag: CompoundTag) : super(EnchanterStructures.COBWEB_PIECE, tag) {
            this.rotation = BlockRotation.valueOf(tag.getString("Rotation"))
            initStructureData(manager)
        }

        private fun initStructureData(manager: StructureManager) {
            val structure = manager.getStructureOrBlank(ID)
            val data = StructurePlacementData()
                .setRotation(rotation)
                .setPosition(pos)
                .setMirrored(BlockMirror.NONE)
                .addProcessor(BlockIgnoreStructureProcessor.IGNORE_STRUCTURE_BLOCKS)

            setStructureData(structure, pos, data)
        }

        override fun toNbt(tag: CompoundTag) {
            super.toNbt(tag)
            tag.putString("Rotation", rotation.name)
        }

        override fun handleMetadata(
            metadata: String,
            pos: BlockPos,
            world: IWorld,
            random: Random,
            boundingBox: BlockBox
        ) = Unit

        override fun generate(
            world: IWorld,
            generator: ChunkGenerator<*>,
            random: Random,
            box: BlockBox,
            pos: ChunkPos
        ): Boolean {
            this.pos = this.pos.add(world.getTopPosition(Heightmap.Type.WORLD_SURFACE_WG, this.pos))
            return super.generate(world, generator, random, box, pos)
        }
    }
}
