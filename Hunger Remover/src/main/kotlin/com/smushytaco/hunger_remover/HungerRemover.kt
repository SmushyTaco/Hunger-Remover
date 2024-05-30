package com.smushytaco.hunger_remover
import com.smushytaco.hunger_remover.configuration_support.ModConfiguration
import me.shedaniel.autoconfig.AutoConfig
import me.shedaniel.autoconfig.annotation.Config
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer
import net.fabricmc.api.ModInitializer
object HungerRemover : ModInitializer {
    const val MOD_ID = "hunger_remover"
    lateinit var config: ModConfiguration
        private set
    override fun onInitialize() {}
    fun initializeConfig() {
        if (::config.isInitialized) return
        AutoConfig.register(ModConfiguration::class.java) { definition: Config, configClass: Class<ModConfiguration> ->
            GsonConfigSerializer(definition, configClass)
        }
        config = AutoConfig.getConfigHolder(ModConfiguration::class.java).config
    }
}