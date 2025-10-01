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
        if (!(entity instanceof PlayerEntity player)) return;

        boolean isInHand = slot == EquipmentSlot.MAINHAND || slot == EquipmentSlot.OFFHAND;
        boolean isRidingHappyGhast = player.hasVehicle() && player.getVehicle() instanceof HappyGhastEntity;

        if (isInHand && isRidingHappyGhast) {
            HappyGhastEntity happyGhast = (HappyGhastEntity) player.getVehicle();

            boolean isMoving = player.forwardSpeed != 0 || player.sidewaysSpeed != 0 || player.isJumping();

            if (isMoving) {
                // Consume durability only when player is actively moving the Happy Ghast
                if (world.getTime() % 80 == 0 && stack.getDamage() < stack.getMaxDamage()) {
                    stack.damage(1, player, slot);

                    if (happyGhast.getHealth() < happyGhast.getMaxHealth()) {
                        happyGhast.heal(1.0f);
                    }
                }
            }
        }
    }
}