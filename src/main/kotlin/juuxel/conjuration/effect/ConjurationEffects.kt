package juuxel.conjuration.effect

import juuxel.conjuration.Conjuration
import net.minecraft.entity.attribute.EntityAttributeModifier
import net.minecraft.entity.attribute.EntityAttributes
import net.minecraft.entity.effect.StatusEffect
import net.minecraft.util.registry.Registry

object ConjurationEffects {
    @JvmField val FROST: StatusEffect = FrostEffect().addAttributeModifier(
        EntityAttributes.MOVEMENT_SPEED,
        "22F011E5-A693-481D-9C6A-AA7C6DB79CFE", // seems to be unique for each effect so I generated one randomly
        -0.15,
        EntityAttributeModifier.Operation.MULTIPLY_TOTAL
    )

    @JvmField val INVULNERABLE: StatusEffect = InvulnerableEffect()

    fun init() {
        register("frost", FROST)
        register("invulnerable", INVULNERABLE)
    }

    private fun register(name: String, effect: StatusEffect) =
        Registry.register(Registry.STATUS_EFFECT, Conjuration.id(name), effect)
}
