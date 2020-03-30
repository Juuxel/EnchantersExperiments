package juuxel.enchanter.client

import com.mojang.blaze3d.systems.RenderSystem
import juuxel.enchanter.effect.EnchanterEffects
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.MinecraftClient
import net.minecraft.client.render.Tessellator
import net.minecraft.client.render.VertexFormats
import net.minecraft.entity.Entity
import net.minecraft.entity.LivingEntity
import net.minecraft.util.Identifier

@Environment(EnvType.CLIENT)
object FrostVignette {
    private val TEXTURE = Identifier("minecraft", "textures/misc/vignette.png")

    fun render(entity: Entity?) {
        val client = MinecraftClient.getInstance()
        if (entity == null || entity !is LivingEntity || !entity.hasStatusEffect(EnchanterEffects.FROST)) return
        val width = client.window.scaledWidth.toDouble()
        val height = client.window.scaledHeight.toDouble()

        // 0xABEBF4
        RenderSystem.color4f(1f - 0xAB / 255f, 1f - 0xEB / 255f, 1f - 0xF4 / 255f, 1f)

        client.textureManager.bindTexture(TEXTURE)
        val tessellator = Tessellator.getInstance()
        val buf = tessellator.buffer
        buf.begin(7, VertexFormats.POSITION_TEXTURE)
        buf.vertex(0.0, height, -90.0).texture(0f, 1f).next()
        buf.vertex(width, height, -90.0).texture(1f, 1f).next()
        buf.vertex(width, 0.0, -90.0).texture(1f, 0f).next()
        buf.vertex(0.0, 0.0, -90.0).texture(0f, 0f).next()
        tessellator.draw()
    }
}
