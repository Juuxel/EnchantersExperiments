package juuxel.enchanter.structure

import juuxel.enchanter.Enchanter
import net.minecraft.nbt.CompoundTag
import net.minecraft.structure.StructureManager
import net.minecraft.structure.StructurePiece
import net.minecraft.structure.StructurePieceType
import net.minecraft.util.registry.Registry
import java.util.Locale

object EnchanterStructures {
    val COBWEB_PIECE: StructurePieceType = register("CWP", CobwebStructureFeature::Piece)

    // StructureManager structureManager, CompoundTag tag
    private fun register(name: String, piece: (StructureManager, CompoundTag) -> StructurePiece): StructurePieceType =
        Registry.register(
            Registry.STRUCTURE_PIECE,
            Enchanter.id(name.toLowerCase(Locale.ROOT)),
            StructurePieceType(piece)
        )
}
