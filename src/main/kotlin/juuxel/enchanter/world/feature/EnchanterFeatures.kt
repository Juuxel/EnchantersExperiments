package juuxel.enchanter.world.feature

import juuxel.enchanter.Enchanter
import net.minecraft.util.registry.Registry
import net.minecraft.world.biome.Biome
import net.minecraft.world.gen.GenerationStep
import net.minecraft.world.gen.decorator.ChanceDecoratorConfig
import net.minecraft.world.gen.decorator.Decorator
import net.minecraft.world.gen.feature.DefaultFeatureConfig
import net.minecraft.world.gen.feature.Feature
import net.minecraft.world.gen.feature.FeatureConfig

object EnchanterFeatures {
    val COBWEB: Feature<DefaultFeatureConfig> = CobwebFeature { DefaultFeatureConfig.deserialize(it) }
    val FARM: Feature<DefaultFeatureConfig> = FarmFeature { DefaultFeatureConfig.deserialize(it) }
    val FROST: Feature<DefaultFeatureConfig> = FrostFeature { DefaultFeatureConfig.deserialize(it) }

    fun init() {
        register("cobweb", COBWEB)
        register("farm", FARM)
        register("frost", FROST)
    }

    private fun register(name: String, feature: Feature<DefaultFeatureConfig>) {
        Registry.register(Registry.FEATURE, Enchanter.id(name), feature)
    }

    @JvmStatic
    fun addStructures(biome: Biome) {
        // No water biomes
        if (biome.category == Biome.Category.OCEAN || biome.category == Biome.Category.RIVER) return

        // Cobweb
        biome.addFeature(
            GenerationStep.Feature.SURFACE_STRUCTURES,
            COBWEB.configure(FeatureConfig.DEFAULT)
                .createDecoratedFeature(Decorator.CHANCE_TOP_SOLID_HEIGHTMAP.configure(ChanceDecoratorConfig(500)))
        )

        // Farms
        biome.addFeature(
            GenerationStep.Feature.SURFACE_STRUCTURES,
            FARM.configure(FeatureConfig.DEFAULT)
                .createDecoratedFeature(Decorator.CHANCE_TOP_SOLID_HEIGHTMAP.configure(ChanceDecoratorConfig(500)))
        )

        if (biome.temperatureGroup == Biome.TemperatureGroup.COLD) {
            // Frost
            biome.addFeature(
                GenerationStep.Feature.SURFACE_STRUCTURES,
                FROST.configure(FeatureConfig.DEFAULT)
                    .createDecoratedFeature(Decorator.CHANCE_TOP_SOLID_HEIGHTMAP.configure(ChanceDecoratorConfig(200)))
            )
        }
    }
}
