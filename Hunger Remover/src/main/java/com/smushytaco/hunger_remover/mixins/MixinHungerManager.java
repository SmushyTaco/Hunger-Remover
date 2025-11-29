package com.smushytaco.hunger_remover.mixins;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.smushytaco.hunger_remover.HungerManagerPlayerEntityAccess;
import com.smushytaco.hunger_remover.HungerRemover;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
@Mixin(FoodData.class)
public abstract class MixinHungerManager implements HungerManagerPlayerEntityAccess {
    @Unique
    @Nullable
    Player playerEntity = null;
    @Override
    @SuppressWarnings("AddedMixinMembersNamePattern")
    public void setPlayerEntity(@NotNull Player playerEntity) { this.playerEntity = playerEntity; }
    @Inject(method = "setSaturation", at = @At("HEAD"), cancellable = true)
    private void hookSetSaturationLevel(float saturationLevel, CallbackInfo ci) { if (!HungerRemover.INSTANCE.getConfig().getDisableMod()) ci.cancel(); }
    @Inject(method = "tick", at = @At("HEAD"), cancellable = true)
    private void hookUpdate(ServerPlayer player, CallbackInfo ci) { if (!HungerRemover.INSTANCE.getConfig().getDisableMod()) ci.cancel(); }
    @Inject(method = "addExhaustion", at = @At("HEAD"), cancellable = true)
    private void hookAddExhaustion(float exhaustion, CallbackInfo ci) { if (!HungerRemover.INSTANCE.getConfig().getDisableMod()) ci.cancel(); }
    @Inject(method = "setFoodLevel", at = @At("HEAD"), cancellable = true)
    private void hookSetFoodLevel(int foodLevel, CallbackInfo ci) { if (!HungerRemover.INSTANCE.getConfig().getDisableMod()) ci.cancel(); }
    @Inject(method = "readAdditionalSaveData", at = @At("HEAD"), cancellable = true)
    private void hookReadNbt(ValueInput view, CallbackInfo ci) { if (!HungerRemover.INSTANCE.getConfig().getDisableMod()) ci.cancel(); }
    @Inject(method = "addAdditionalSaveData", at = @At("HEAD"), cancellable = true)
    private void hookWriteNbt(ValueOutput view, CallbackInfo ci) { if (!HungerRemover.INSTANCE.getConfig().getDisableMod()) ci.cancel(); }
    @Inject(method = "eat(IF)V", at = @At("HEAD"), cancellable = true)
    private void hookAdd(int food, float saturationModifier, CallbackInfo ci) {
        if (HungerRemover.INSTANCE.getConfig().getDisableMod() || playerEntity == null) return;
        playerEntity.heal(food);
        ci.cancel();
    }
    @Inject(method = "eat(Lnet/minecraft/world/food/FoodProperties;)V", at = @At("HEAD"), cancellable = true)
    private void hookEat(FoodProperties foodComponent, CallbackInfo ci) {
        if (HungerRemover.INSTANCE.getConfig().getDisableMod() || playerEntity == null) return;
        playerEntity.heal(foodComponent.nutrition());
        ci.cancel();
    }
    @ModifyReturnValue(method = "needsFood", at = @At("RETURN"))
    private boolean hookIsNotFull(boolean original) { return HungerRemover.INSTANCE.getConfig().getDisableMod() || playerEntity == null ? original : playerEntity.getHealth() < playerEntity.getMaxHealth(); }
    @ModifyReturnValue(method = "getFoodLevel", at = @At("RETURN"))
    private int hookGetFoodLevel(int original) { return HungerRemover.INSTANCE.getConfig().getDisableMod() ? original : HungerRemover.MAX_FOOD_LEVEL; }
    @ModifyReturnValue(method = "getSaturationLevel", at = @At("RETURN"))
    private float hookGetSaturationLevel(float original) { return HungerRemover.INSTANCE.getConfig().getDisableMod() ? original : HungerRemover.MAX_SATURATION_LEVEL; }
}