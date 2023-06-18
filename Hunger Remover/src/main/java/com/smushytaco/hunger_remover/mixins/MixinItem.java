package com.smushytaco.hunger_remover.mixins;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.smushytaco.hunger_remover.HungerRemover;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
@Mixin(Item.class)
public abstract class MixinItem {
    @ModifyReturnValue(method = "getMaxUseTime", at = @At("RETURN"))
    @SuppressWarnings("unused")
    private int hookGetMaxUseTime(int original, ItemStack itemStack) { return HungerRemover.INSTANCE.getConfig().getDisableMod() || !HungerRemover.INSTANCE.getConfig().getEatInstantly() || !itemStack.getItem().isFood() ? original : 1; }
}