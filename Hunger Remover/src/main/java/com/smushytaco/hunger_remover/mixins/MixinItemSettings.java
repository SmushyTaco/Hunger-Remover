package com.smushytaco.hunger_remover.mixins;
import com.smushytaco.hunger_remover.HungerRemover;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
@Mixin(Item.Settings.class)
public abstract class MixinItemSettings {
    @Shadow
    FoodComponent foodComponent;
    @Inject(method = "food", at = @At("HEAD"))
    private void hookFood(FoodComponent foodComponent, CallbackInfoReturnable<Item.Settings> info) {
        HungerRemover.INSTANCE.initializeConfig();
        if (HungerRemover.INSTANCE.getConfig().getDisableMod()) return;
        if (HungerRemover.INSTANCE.getConfig().getFoodCantStack() && foodComponent != null) ((Item.Settings) (Object) this).maxCount(1);
    }
    @ModifyVariable(method = "maxCount", at = @At("HEAD"), argsOnly = true)
    private int hookMaxCount(int maxCount) {
        HungerRemover.INSTANCE.initializeConfig();
        if (HungerRemover.INSTANCE.getConfig().getDisableMod()) return maxCount;
        if (HungerRemover.INSTANCE.getConfig().getFoodCantStack() && foodComponent != null) return 1;
        return maxCount;
    }
}