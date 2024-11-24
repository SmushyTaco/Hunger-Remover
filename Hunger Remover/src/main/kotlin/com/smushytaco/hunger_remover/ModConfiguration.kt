package com.smushytaco.hunger_remover
import io.wispforest.owo.config.Option
import io.wispforest.owo.config.annotation.*
@Modmenu(modId = HungerRemover.MOD_ID)
@Config(name = HungerRemover.MOD_ID, wrapperName = "ModConfig")
@Suppress("UNUSED")
class ModConfiguration {
    @JvmField
    @Sync(Option.SyncMode.OVERRIDE_CLIENT)
    @RestartRequired
    var disableMod = false
    @JvmField
    @Sync(Option.SyncMode.OVERRIDE_CLIENT)
    var eatInstantly = false
    @JvmField
    @Sync(Option.SyncMode.OVERRIDE_CLIENT)
    var modifyMaxFoodStackCount = false
    @JvmField
    @PredicateConstraint(value = "maxFoodStackCountValidation")
    @Sync(Option.SyncMode.OVERRIDE_CLIENT)
    var maxFoodStackCount = 1
    @JvmField
    @Sync(Option.SyncMode.OVERRIDE_CLIENT)
    var cantSprint = true
    @JvmField
    var hideHungerBar = true
    @JvmField
    var moveArmorBarToHungerBar = true
    @JvmField
    @RestartRequired
    var replaceAllHungerWithPoison = true
    @JvmField
    @RestartRequired
    var replaceHungerFromFoodWithPoison = true
    companion object {
        @JvmStatic
        fun maxFoodStackCountValidation(maxFoodStackCount: Int) = maxFoodStackCount > 0
    }
}