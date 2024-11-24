package com.smushytaco.hunger_remover
import net.fabricmc.api.ModInitializer
object HungerRemover : ModInitializer {
    const val MAX_FOOD_LEVEL = 20
    const val MAX_SATURATION_LEVEL = MAX_FOOD_LEVEL.toFloat()
    const val MOD_ID = "hunger_remover"
    val config = ModConfig.createAndLoad()
    override fun onInitialize() {}
}