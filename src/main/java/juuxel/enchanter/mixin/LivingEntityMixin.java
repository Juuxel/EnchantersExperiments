package juuxel.enchanter.mixin;

import juuxel.enchanter.effect.EnchanterEffects;
import juuxel.enchanter.networking.EnchanterNetworking;
import net.fabricmc.fabric.api.server.PlayerStream;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
    private LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Shadow
    public abstract boolean hasStatusEffect(StatusEffect effect);

    @Shadow
    public abstract boolean clearStatusEffects();

    @Shadow
    public abstract boolean addStatusEffect(StatusEffectInstance effect);

    @Shadow
    public abstract void setHealth(float health);

    @Inject(method = "tryUseTotem", at = @At("HEAD"), cancellable = true)
    private void enchanter_onTryUseTotem(DamageSource source, CallbackInfoReturnable<Boolean> info) {
        if (source.isOutOfWorld()) return;

        if (hasStatusEffect(EnchanterEffects.INVULNERABLE)) {
            setHealth(1.0f);
            clearStatusEffects();
            addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 900, 1));
            addStatusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, 100, 1));

            world.playSound(
                null,
                getX(), getEyeY(), getZ(),
                SoundEvents.ENTITY_ZOMBIE_VILLAGER_CURE,
                getSoundCategory(),
                random.nextFloat() * 0.7F,
                random.nextFloat() * 0.7F + 0.3F
            );

            PlayerStream.watching(this).forEach(player -> {
                EnchanterNetworking.INSTANCE.sendRespawnWithInvulnerability(player, getX(), getY(), getZ());
            });

            // TODO: Sounds/particles
            info.setReturnValue(true);
        }
    }
}
