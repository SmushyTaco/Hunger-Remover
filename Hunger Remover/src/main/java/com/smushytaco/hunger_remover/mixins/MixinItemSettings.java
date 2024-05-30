package com.smushytaco.hunger_remover.mixins;
import com.smushytaco.hunger_remover.HungerRemover;
import net.minecraft.component.ComponentMap;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.item.Item;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
@Mixin(Item.Settings.class)
public abstract class MixinItemSettings {
    @Shadow
    @Nullable
    private ComponentMap.Builder components;
    @Inject(method = "food", at = @At("HEAD"))
    private void hookFood(FoodComponent foodComponent, CallbackInfoReturnable<Item.Settings> info) {
        if (HungerRemover.INSTANCE.getConfig().getDisableMod() || !HungerRemover.INSTANCE.getConfig().getFoodCantStack() || foodComponent == null) return;
        ((Item.Settings) (Object) this).maxCount(1);
    }
    @ModifyVariable(method = "maxCount", at = @At("HEAD"), argsOnly = true)
    private int hookMaxCount(int maxCount) {
        HungerRemover.INSTANCE.initializeConfig();
        return HungerRemover.INSTANCE.getConfig().getDisableMod() || !HungerRemover.INSTANCE.getConfig().getFoodCantStack() || components == null || !((ComponentMapBuilderAccessor) components).getComponents().containsKey(DataComponentTypes.FOOD) ? maxCount : 1;
    }
}