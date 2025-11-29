package com.smushytaco.hunger_remover.mixins;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.smushytaco.hunger_remover.HungerRemover;
import net.minecraft.core.component.DataComponentHolder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
@Mixin(ItemStack.class)
public abstract class MixinItemStack implements DataComponentHolder {
    @ModifyReturnValue(method = "getMaxStackSize", at = @At("RETURN"))
    private int hookGetMaxCount(int original) {
        if (HungerRemover.INSTANCE.getConfig().getDisableMod() || !HungerRemover.INSTANCE.getConfig().getModifyMaxFoodStackCount() || get(DataComponents.CONSUMABLE) == null || original <= HungerRemover.INSTANCE.getConfig().getMaxFoodStackCount()) return original;
        return HungerRemover.INSTANCE.getConfig().getMaxFoodStackCount();
    }
}