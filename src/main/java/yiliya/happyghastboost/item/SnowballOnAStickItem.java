package yiliya.happyghastboost.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.passive.HappyGhastEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;

public class SnowballOnAStickItem extends Item {

    public SnowballOnAStickItem(Settings settings) {
        super(settings);
    }

    @Override
    public void inventoryTick(ItemStack stack, ServerWorld world, Entity entity, EquipmentSlot slot) {
        if (!(entity instanceof PlayerEntity player)) {
            return;
        }

        boolean isInHand = slot == EquipmentSlot.MAINHAND || slot == EquipmentSlot.OFFHAND;

        if (isInHand && player.hasVehicle() && player.getVehicle() instanceof HappyGhastEntity) {
            // Consume 1 durability every second (20 ticks)
            if (world.getTime() % 20 == 0) {
                if (stack.getDamage() < stack.getMaxDamage()) {
                    stack.damage(1, player, slot);
                }
            }
        }
    }
}