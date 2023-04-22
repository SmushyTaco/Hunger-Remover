package com.smushytaco.hunger_remover.configuration_support
import com.smushytaco.hunger_remover.HungerRemover
import me.shedaniel.autoconfig.ConfigData
import me.shedaniel.autoconfig.annotation.Config
import me.shedaniel.autoconfig.annotation.ConfigEntry.Gui.RequiresRestart
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment
@Config(name = HungerRemover.MOD_ID)
class ModConfiguration: ConfigData {
    @RequiresRestart
    @Comment("Default value is no. If set to yes the mod will be disabled. If set to no it won't. A restart is required for this to take effect.")
    val disableMod = false
    @Comment("Default value is no. If set to yes you'll be able eat food instantly. If set to no you won't be able to.")
    val eatInstantly = false
    @RequiresRestart
    @Comment("Default value is no. If set to yes you won't be able to stack food. If set to no you will be able to. A restart is required for this to take effect.")
    val foodCantStack = false
    @Comment("Default value is no. If set to yes you won't be able to sprint. If set to no you will be able to.")
    val cantSprint = false
}