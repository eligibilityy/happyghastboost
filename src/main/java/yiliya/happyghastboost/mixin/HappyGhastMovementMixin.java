package yiliya.happyghastboost.mixin;

import net.minecraft.entity.passive.HappyGhastEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import yiliya.happyghastboost.Boostable;

@Mixin(HappyGhastEntity.class)
public class HappyGhastMovementMixin {

    @Inject(
            method = "getControlledMovementInput",
            at = @At("RETURN"),
            cancellable = true
    )
    private void modifyControlledMovementInput(PlayerEntity controllingPlayer, Vec3d movementInput, CallbackInfoReturnable<Vec3d> cir) {
        HappyGhastEntity ghast = (HappyGhastEntity) (Object) this;
        Boostable boostable = (Boostable) ghast;

        float multiplier = boostable.happyghastboost$getMovementSpeedMultiplier();

        if (multiplier > 1.0F) {
            Vec3d originalInput = cir.getReturnValue();
            Vec3d boostedInput = originalInput.multiply(multiplier);
            cir.setReturnValue(boostedInput);
        }
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void onTick(CallbackInfo ci) {
        HappyGhastEntity ghast = (HappyGhastEntity) (Object) this;
        Boostable boostable = (Boostable) ghast;

        // Debug output to console
        // if (boostable.happyghastboost$getMovementSpeedMultiplier() > 1.0F) {
        //      System.out.println("BOOST ACTIVE: Happy Ghast is moving at 3x speed!");
        // }
    }
}