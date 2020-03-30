package juuxel.enchanter.mixin;

import juuxel.enchanter.enchantment.EfficientFarmingEnchantment;
import juuxel.enchanter.enchantment.EnchanterEnchantments;
import juuxel.enchanter.util.Extensions;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.HoeItem;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;

@Mixin(HoeItem.class)
public class HoeItemMixin {
    @Shadow
    @Final
    protected static Map<Block, BlockState> TILLED_BLOCKS;

    @Inject(method = "useOnBlock", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;I)Z"))
    private void enchanter_useOnBlock(ItemUsageContext context, CallbackInfoReturnable<ActionResult> info) {
        EfficientFarmingEnchantment.handleTilling(context.getWorld(), context.getBlockPos(), context.getStack(), TILLED_BLOCKS);
    }

    @ModifyConstant(method = "useOnBlock", constant = @Constant(intValue = 1))
    private int enchanter_replaceDamage(int damage, ItemUsageContext context) {
        boolean hasEnchantment = Extensions.checkEnchantments(
            context.getStack(),
            (enchantment, level) -> enchantment == EnchanterEnchantments.EFFICIENT_FARMING
        );

        return hasEnchantment ? 4 : 1;
    }
}
