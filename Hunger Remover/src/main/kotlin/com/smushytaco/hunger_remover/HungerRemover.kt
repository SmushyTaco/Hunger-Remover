package com.smushytaco.hunger_remover
import com.smushytaco.hunger_remover.configuration_support.ModConfiguration
import me.shedaniel.autoconfig.AutoConfig
import me.shedaniel.autoconfig.annotation.Config
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer
import net.fabricmc.api.ModInitializer
object HungerRemover : ModInitializer {
    const val MAX_FOOD_LEVEL = 20
    const val MAX_SATURATION_LEVEL = MAX_FOOD_LEVEL.toFloat()
    const val MOD_ID = "hunger_remover"
    lateinit var config: ModConfiguration
        private set
    override fun onInitialize() {
        AutoConfig.register(ModConfiguration::class.java) { definition: Config, configClass: Class<ModConfiguration> ->
            GsonConfigSerializer(definition, configClass)
        }
        config = AutoConfig.getConfigHolder(ModConfiguration::class.java).config
    }
}