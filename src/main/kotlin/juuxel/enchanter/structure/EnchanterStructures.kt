package juuxel.enchanter.structure

import com.mojang.datafixers.Dynamic
import juuxel.enchanter.Enchanter
import juuxel.enchanter.mixin.LocateCommandAccessor
import net.fabricmc.fabric.api.registry.CommandRegistry
import net.minecraft.nbt.CompoundTag
import net.minecraft.server.command.CommandManager
import net.minecraft.structure.StructureManager
import net.minecraft.structure.StructurePiece
import net.minecraft.structure.StructurePieceType
import net.minecraft.util.registry.Registry
import net.minecraft.world.biome.Biome
import net.minecraft.world.gen.GenerationStep
import net.minecraft.world.gen.decorator.ChanceDecoratorConfig
import net.minecraft.world.gen.decorator.Decorator
import net.minecraft.world.gen.decorator.DecoratorConfig
import net.minecraft.world.gen.feature.ConfiguredFeature
import net.minecraft.world.gen.feature.DefaultFeatureConfig
import net.minecraft.world.gen.feature.FeatureConfig
import net.minecraft.world.gen.feature.StructureFeature
import java.util.Locale

object EnchanterStructures {
    private val DEFAULT_CONFIG_DESERIALIZER: (Dynamic<*>) -> DefaultFeatureConfig = {
        DefaultFeatureConfig.deserialize(it)
    }

    @JvmField
    val COBWEB: StructureFeature<DefaultFeatureConfig> = register("cobweb", CobwebFeature(DEFAULT_CONFIG_DESERIALIZER))
    val COBWEB_PIECE: StructurePieceType = register("CWP", CobwebStructureGenerator::Piece)

    fun init() {
        val structures = listOf("cobweb")

        CommandRegistry.INSTANCE.register(false) { dispatcher ->
            val locate = CommandManager.literal("locate").requires { it.hasPermissionLevel(2) }
            for (structure in structures) {
                locate.then(
                    CommandManager.literal("${Enchanter.ID}:$structure").executes {
                        LocateCommandAccessor.callExecute(it.source, structure)
                    }
                )
            }
            dispatcher.register(locate)
        }
    }

    @JvmStatic
    fun addTo(biome: Biome) {
        println("Registering for $biome")
        addStructure(biome, COBWEB.configure(FeatureConfig.DEFAULT))
    }

    private fun <C : FeatureConfig> addStructure(
        biome: Biome,
        structure: ConfiguredFeature<C, out StructureFeature<C>>
    ) {
        biome.addStructureFeature(structure)
        biome.addFeature(
            GenerationStep.Feature.SURFACE_STRUCTURES,
            structure.createDecoratedFeature(
                Decorator.NOPE.configure(DecoratorConfig.DEFAULT)
            )
        )
    }

    private fun register(name: String, piece: (StructureManager, CompoundTag) -> StructurePiece): StructurePieceType =
        Registry.register(
            Registry.STRUCTURE_PIECE,
            Enchanter.id(name.toLowerCase(Locale.ROOT)),
            StructurePieceType(piece)
        )

    private fun <C : FeatureConfig> register(name: String, structure: StructureFeature<C>): StructureFeature<C> {
        val id = Enchanter.id(name)

        Registry.register(
            Registry.FEATURE,
            id,
            structure
        )

        return Registry.register(
            Registry.STRUCTURE_FEATURE,
            id,
            structure
        )
    }
}
