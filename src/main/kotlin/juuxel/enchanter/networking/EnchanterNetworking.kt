package juuxel.enchanter.networking

import io.netty.buffer.Unpooled
import juuxel.enchanter.Enchanter
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.fabricmc.fabric.api.network.ClientSidePacketRegistry
import net.fabricmc.fabric.api.network.ServerSidePacketRegistry
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.particle.ParticleTypes
import net.minecraft.util.PacketByteBuf

object EnchanterNetworking {
    // x: Double
    // y: Double
    // z: Double
    private val RESPAWN_WITH_INVULNERABILITY = Enchanter.id("respawn_with_invulnerability")

    fun sendRespawnWithInvulnerability(player: PlayerEntity, x: Double, y: Double, z: Double) {
        val buf = PacketByteBuf(Unpooled.buffer())
        buf.writeDouble(x)
        buf.writeDouble(y)
        buf.writeDouble(z)
        ServerSidePacketRegistry.INSTANCE.sendToPlayer(player, RESPAWN_WITH_INVULNERABILITY, buf)
    }

    fun init() {

    }

    @Environment(EnvType.CLIENT)
    fun initClient() {
        ClientSidePacketRegistry.INSTANCE.register(RESPAWN_WITH_INVULNERABILITY) { ctx, buf ->
            val x = buf.readDouble()
            val y = buf.readDouble()
            val z = buf.readDouble()
            ctx.player.world.addParticle(ParticleTypes.CRIT, x, y, z, 0.0, 0.0, 0.0)
        }
    }
}
