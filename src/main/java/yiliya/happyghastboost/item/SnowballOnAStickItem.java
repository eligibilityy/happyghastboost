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
        boolean isRidingHappyGhast = player.hasVehicle() && player.getVehicle() instanceof HappyGhastEntity;

        if (isInHand && isRidingHappyGhast) {
            if (world.getTime() % 80 == 0) {
                if (stack.getDamage() < stack.getMaxDamage()) {
                    // Damage the item
                    stack.damage(1, player, slot);

                    // Only heal if the Happy Ghast is actually damaged
                    net.minecraft.entity.passive.HappyGhastEntity happyGhast =
                            (net.minecraft.entity.passive.HappyGhastEntity) player.getVehicle();

                    if (happyGhast.getHealth() < happyGhast.getMaxHealth()) {
                        happyGhast.heal(1.0f);

                        if (world.getRandom().nextFloat() < 0.3f) { // 30% chance for particles
                            world.spawnParticles(
                                    net.minecraft.particle.ParticleTypes.HAPPY_VILLAGER,
                                    happyGhast.getX(),
                                    happyGhast.getY() + 1.5,
                                    happyGhast.getZ(),
                                    1,
                                    0.3, 0.3, 0.3,
                                    0.05
                            );
                        }
                    }
                }
            }
        }
    }
}