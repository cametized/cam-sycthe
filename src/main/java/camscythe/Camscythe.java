package camscythe;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;

import java.util.logging.Logger;

public class Camscythe implements ModInitializer {

    @Override
    public void onInitialize() {
        ModItems.initialize();
        ModItemGroups.initialize();

        AttackEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
            if (!(world instanceof ServerWorld serverWorld)) return ActionResult.PASS;
            if (!(entity instanceof LivingEntity livingTarget)) return ActionResult.PASS;

            var heldItem = player.getStackInHand(hand).getItem();
            double x = entity.getX();
            double y = entity.getY() + entity.getHeight() * 0.5;
            double z = entity.getZ();

            if (heldItem == ModItems.EMBERGLAIVE) {
                spawnSlash(serverWorld, livingTarget, true, false, 0xFFFF4400); // orange-red
                if (player.getAttackCooldownProgress(0.5f) > 0.9f) {
                    livingTarget.setOnFireFor(8.0f);
                    serverWorld.playSound(null, x, y, z,
                            SoundEvents.ITEM_FIRECHARGE_USE, SoundCategory.PLAYERS, 1.0f, 1.0f);
                }
            } else if (heldItem == ModItems.PLAYTHING) {
                spawnSlash(serverWorld, livingTarget, true, true, 0xFFFFD700); // gold
                if (!(player.getAttackCooldownProgress(0.5f) > 0.9f)) {
                    player.playSoundToPlayer(SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP,player.getSoundCategory(),0.75f,1.125f);
                }
                Logger.getGlobal().info(String.valueOf(player.getAttributeValue(EntityAttributes.ATTACK_SPEED)));
            } else if (heldItem == ModItems.VINECOG) {
                spawnSlash(serverWorld, livingTarget, true, false, 0xFF33CC00); // green
            }

            return ActionResult.PASS;
        });
    }

    private static void spawnSlash(ServerWorld world, LivingEntity target, boolean dust, boolean sweep, int color) {
        // Sweep arc for the slash shape
        if (sweep) {
            world.spawnParticles(
                    ParticleTypes.SWEEP_ATTACK,
                    target.getX(), target.getY()+target.getHeight()/2, target.getZ(),
                    1,
                    target.getWidth()/2, target.getHeight()/2, target.getWidth()/2,
                    0.0
            );
        }
        // Colored dust cloud for the scythe's color
        if (dust) {
            world.spawnParticles(
                    new DustParticleEffect(color, 1.8f), //particle
                    target.getX(), target.getY()+target.getHeight()/2, target.getZ(), //position
                    Math.round(12*(target.getWidth()*target.getWidth()*target.getHeight())), //count
                    target.getWidth()/2, target.getHeight()/2, target.getWidth()/2, //offset
                    0.0 //speed
            );
        }
    }
}
