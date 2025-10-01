package yiliya.happyghastboost.mixin;

import net.minecraft.entity.passive.HappyGhastEntity;
import net.minecraft.entity.ItemSteerable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import yiliya.happyghastboost.Happyghastboost;
import yiliya.happyghastboost.Boostable;

@Mixin(HappyGhastEntity.class)
public class HappyGhastEntityMixin implements ItemSteerable, Boostable {

    @Unique
    private boolean happyghastboost$isBoosted = false;

    @Inject(method = "getControllingPassenger", at = @At("HEAD"))
    private void modifyControllingPassenger(CallbackInfoReturnable<LivingEntity> cir) {
        HappyGhastEntity ghast = (HappyGhastEntity) (Object) this;

        // Check if player is holding Snowball on a Stick in any hand
        // method_72227 = isOnStillTimeout()
        if (ghast.isWearingBodyArmor() && !ghast.method_72227() && ghast.getFirstPassenger() instanceof PlayerEntity player) {
            if (player.getMainHandStack().isOf(Happyghastboost.SNOWBALL_ON_A_STICK) ||
                    player.getOffHandStack().isOf(Happyghastboost.SNOWBALL_ON_A_STICK)) {
                this.happyghastboost$isBoosted = true;
                return;
            }
        }
        this.happyghastboost$isBoosted = false;
    }

    @Override
    public boolean consumeOnAStickItem() {
        // Not used for our automatic boost, but required by ItemSteerable
        return true;
    }

    @Override
    @Unique
    public boolean happyghastboost$consumeOnAStickItem() {
        return true;
    }

    @Override
    @Unique
    public void happyghastboost$tickBoost() {
    }

    @Override
    @Unique
    public float happyghastboost$getMovementSpeedMultiplier() {
        return this.happyghastboost$isBoosted ? 2.0F : 1.0F;
    }
}