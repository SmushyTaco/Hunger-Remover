package com.smushytaco.hunger_remover.mixins;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.smushytaco.hunger_remover.HungerManagerPlayerEntityAccess;
import com.smushytaco.hunger_remover.HungerRemover;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.entity.player.HungerManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
@Mixin(HungerManager.class)
public class MixinHungerManager implements HungerManagerPlayerEntityAccess {
    @Unique
    @Nullable
    PlayerEntity playerEntity = null;
    @Override
    public void setPlayerEntity(@NotNull PlayerEntity playerEntity) { this.playerEntity = playerEntity; }
    @Inject(method = "setSaturationLevel", at = @At("HEAD"), cancellable = true)
    private void hookSetSaturationLevel(float saturationLevel, CallbackInfo ci) { if (!HungerRemover.INSTANCE.getConfig().getDisableMod()) ci.cancel(); }
    @Inject(method = "update", at = @At("HEAD"), cancellable = true)
    private void hookUpdate(ServerPlayerEntity player, CallbackInfo ci) { if (!HungerRemover.INSTANCE.getConfig().getDisableMod()) ci.cancel(); }
    @Inject(method = "addExhaustion", at = @At("HEAD"), cancellable = true)
    private void hookAddExhaustion(float exhaustion, CallbackInfo ci) { if (!HungerRemover.INSTANCE.getConfig().getDisableMod()) ci.cancel(); }
    @Inject(method = "setFoodLevel", at = @At("HEAD"), cancellable = true)
    private void hookSetFoodLevel(int foodLevel, CallbackInfo ci) { if (!HungerRemover.INSTANCE.getConfig().getDisableMod()) ci.cancel(); }
    @Inject(method = "readNbt", at = @At("HEAD"), cancellable = true)
    private void hookReadNbt(NbtCompound nbt, CallbackInfo ci) { if (!HungerRemover.INSTANCE.getConfig().getDisableMod()) ci.cancel(); }
    @Inject(method = "writeNbt", at = @At("HEAD"), cancellable = true)
    private void hookWriteNbt(NbtCompound nbt, CallbackInfo ci) { if (!HungerRemover.INSTANCE.getConfig().getDisableMod()) ci.cancel(); }
    @Inject(method = "add", at = @At("HEAD"), cancellable = true)
    private void hookAdd(int food, float saturationModifier, CallbackInfo ci) {
        if (HungerRemover.INSTANCE.getConfig().getDisableMod() || playerEntity == null) return;
        playerEntity.heal(food);
        ci.cancel();
    }
    @Inject(method = "eat", at = @At("HEAD"), cancellable = true)
    private void hookEat(FoodComponent foodComponent, CallbackInfo ci) {
        if (HungerRemover.INSTANCE.getConfig().getDisableMod() || playerEntity == null) return;
        playerEntity.heal(foodComponent.nutrition());
        ci.cancel();
    }
    @ModifyReturnValue(method = "isNotFull", at = @At("RETURN"))
    private boolean hookIsNotFull(boolean original) { return HungerRemover.INSTANCE.getConfig().getDisableMod() || playerEntity == null ? original : playerEntity.getHealth() < playerEntity.getMaxHealth(); }
    @ModifyReturnValue(method = "getFoodLevel", at = @At("RETURN"))
    private int hookGetFoodLevel(int original) { return HungerRemover.INSTANCE.getConfig().getDisableMod() ? original : HungerRemover.MAX_FOOD_LEVEL; }
    @ModifyReturnValue(method = "getSaturationLevel", at = @At("RETURN"))
    private float hookGetSaturationLevel(float original) { return HungerRemover.INSTANCE.getConfig().getDisableMod() ? original : HungerRemover.MAX_SATURATION_LEVEL; }
}