package com.smushytaco.hunger_remover
import com.llamalad7.mixinextras.MixinExtrasBootstrap
import net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint
@Suppress("UNUSED")
object HungerRemoverPreLaunch: PreLaunchEntrypoint { override fun onPreLaunch() { MixinExtrasBootstrap.init() } }