package juuxel.enchanter.mixin;

import juuxel.enchanter.world.feature.EnchanterFeatures;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DefaultBiomeFeatures.class)
class DefaultBiomeFeaturesMixin {
    @Inject(method = "addDefaultStructures", at = @At("RETURN"))
    private static void enchanter_addDefaultStructures(Biome biome, CallbackInfo info) {
        EnchanterFeatures.addStructures(biome);
    }
}
