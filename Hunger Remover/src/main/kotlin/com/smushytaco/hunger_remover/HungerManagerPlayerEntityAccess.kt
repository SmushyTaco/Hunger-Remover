package com.smushytaco.hunger_remover
import net.minecraft.entity.player.PlayerEntity
fun interface HungerManagerPlayerEntityAccess {
    fun setPlayerEntity(playerEntity: PlayerEntity)
}