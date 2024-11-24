package com.smushytaco.hunger_remover.mixins;
import com.smushytaco.hunger_remover.HungerRemover;
import net.minecraft.component.ComponentMap;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ConsumableComponent;
import net.minecraft.component.type.ConsumableComponents;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.item.Item;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
@Mixin(Item.Settings.class)
public abstract class MixinItemSettings {
    @Final
    @Shadow
    @Nullable
    private ComponentMap.Builder components;
    @Inject(method = "food(Lnet/minecraft/component/type/FoodComponent;Lnet/minecraft/component/type/ConsumableComponent;)Lnet/minecraft/item/Item$Settings;", at = @At("HEAD"))
    private void hookFood(FoodComponent foodComponent, ConsumableComponent consumableComponent, CallbackInfoReturnable<Item.Settings> cir) {
        if (components == null || HungerRemover.INSTANCE.getConfig().getDisableMod() || !HungerRemover.INSTANCE.getConfig().getModifyMaxFoodStackCount() || consumableComponent != ConsumableComponents.FOOD) return;
        if ((int) ((ComponentMapBuilderAccessor) components).getComponents().getOrDefault(DataComponentTypes.MAX_STACK_SIZE, 64) > HungerRemover.INSTANCE.getConfig().getMaxFoodStackCount()) ((Item.Settings) (Object) this).maxCount(HungerRemover.INSTANCE.getConfig().getMaxFoodStackCount());
    }
    @ModifyVariable(method = "maxCount", at = @At("HEAD"), argsOnly = true)
    private int hookMaxCount(int maxCount) {
        HungerRemover.INSTANCE.initializeConfig();
        return HungerRemover.INSTANCE.getConfig().getDisableMod() || !HungerRemover.INSTANCE.getConfig().getModifyMaxFoodStackCount() || components == null || (int) ((ComponentMapBuilderAccessor) components).getComponents().getOrDefault(DataComponentTypes.MAX_STACK_SIZE, 64) <= HungerRemover.INSTANCE.getConfig().getMaxFoodStackCount() || !((ComponentMapBuilderAccessor) components).getComponents().containsKey(DataComponentTypes.FOOD) ? maxCount : HungerRemover.INSTANCE.getConfig().getMaxFoodStackCount();
    }
}