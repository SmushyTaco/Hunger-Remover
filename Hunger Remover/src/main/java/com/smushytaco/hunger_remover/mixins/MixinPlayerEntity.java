package com.smushytaco.hunger_remover.mixins;
import com.smushytaco.hunger_remover.HungerManagerPlayerEntityAccess;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
@Mixin(Player.class)
public abstract class MixinPlayerEntity {
    @Shadow
    protected FoodData foodData;
    @Inject(method = "<init>", at = @At("TAIL"))
    private void hookInit(CallbackInfo info) { ((HungerManagerPlayerEntityAccess) foodData).setPlayerEntity((Player) (Object) this); }
}