package com.smushytaco.hunger_remover.mixins;
import com.smushytaco.hunger_remover.HungerRemover;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
@Mixin(Item.class)
public abstract class MixinItem {
    @Inject(method = "getMaxUseTime", at = @At("HEAD"), cancellable = true)
    private void hookGetMaxUseTime(ItemStack itemStack, CallbackInfoReturnable<Integer> cir) {
        if (HungerRemover.INSTANCE.getConfig().getDisableMod()) return;
        if (HungerRemover.INSTANCE.getConfig().getEatInstantly()) {
            if (itemStack.getItem().isFood()) {
                // Food items don't work with 0, so 1 is the next best thing.
                cir.setReturnValue(1);
            }
        }
    }
}