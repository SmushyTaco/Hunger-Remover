package com.smushytaco.hunger_remover.configuration_support
import com.smushytaco.hunger_remover.HungerRemover
import me.shedaniel.autoconfig.ConfigData
import me.shedaniel.autoconfig.annotation.Config
@Config(name = HungerRemover.MOD_ID)
class ModConfiguration: ConfigData {
    val disableMod = false
    val eatInstantly = false
    val modifyMaxFoodStackCount = false
    val maxFoodStackCount = 1
    val cantSprint = false
    val hideHungerBar = true
    val moveArmorBarToHungerBar = true
}