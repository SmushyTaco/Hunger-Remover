package com.smushytaco.hunger_remover
import net.minecraft.world.entity.player.Player
fun interface HungerManagerPlayerEntityAccess {
    fun setPlayerEntity(playerEntity: Player)
}