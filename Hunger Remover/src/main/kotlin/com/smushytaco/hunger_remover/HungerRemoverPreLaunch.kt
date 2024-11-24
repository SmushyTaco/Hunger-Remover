package com.smushytaco.hunger_remover
import net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint
object HungerRemoverPreLaunch: PreLaunchEntrypoint { override fun onPreLaunch() { HungerRemover.initializeConfig() } }