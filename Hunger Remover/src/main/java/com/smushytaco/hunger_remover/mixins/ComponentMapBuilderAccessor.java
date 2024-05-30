package com.smushytaco.hunger_remover.mixins;
import it.unimi.dsi.fastutil.objects.Reference2ObjectMap;
import net.minecraft.component.ComponentMap;
import net.minecraft.component.DataComponentType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
@Mixin(ComponentMap.Builder.class)
public interface ComponentMapBuilderAccessor {
    @Accessor
    Reference2ObjectMap<DataComponentType<?>, Object> getComponents();
}