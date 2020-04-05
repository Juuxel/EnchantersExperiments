package juuxel.enchanter.mixin;

import net.minecraft.server.command.LocateCommand;
import net.minecraft.server.command.ServerCommandSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(LocateCommand.class)
public interface LocateCommandAccessor {
    @SuppressWarnings("PublicStaticMixinMember")
    @Invoker
    static int callExecute(ServerCommandSource source, String structure) {
        throw new AssertionError("Invoker dummy body called!");
    }
}
