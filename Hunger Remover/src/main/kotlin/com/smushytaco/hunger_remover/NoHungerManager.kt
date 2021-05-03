package com.smushytaco.hunger_remover
import net.minecraft.entity.player.HungerManager
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.nbt.CompoundTag
class NoHungerManager(private val player: PlayerEntity) : HungerManager() {
    override fun setSaturationLevelClient(saturationLevel: Float) {}
    override fun isNotFull() = player.health < player.maxHealth
    override fun update(playerEntity: PlayerEntity) {}
    override fun addExhaustion(exhaustion: Float) {}
    override fun getFoodLevel() = MAX_FOOD_LEVEL
    override fun setFoodLevel(foodLevel: Int) {}
    override fun fromTag(tag: CompoundTag) {}
    override fun toTag(tag: CompoundTag) {}
    private fun add(health: Int) {
        player.heal(health.toFloat())
    }
    override fun add(hunger: Int, saturation: Float) {
        add(hunger)
    }
    override fun eat(item: Item, itemStack: ItemStack) {
        if (item.isFood) {
            val foodComponent = item.foodComponent
            if (foodComponent != null) {
                add(foodComponent.hunger)
            }
        }
    }
    override fun getSaturationLevel() = MAX_SATURATION_LEVEL
    companion object {
        private const val MAX_FOOD_LEVEL = 20
        private const val MAX_SATURATION_LEVEL = MAX_FOOD_LEVEL.toFloat()
    }
}