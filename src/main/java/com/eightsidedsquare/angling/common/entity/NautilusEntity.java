package com.eightsidedsquare.angling.common.entity;

import com.eightsidedsquare.angling.core.AnglingItems;
import com.eightsidedsquare.angling.core.AnglingSounds;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.WaterCreatureEntity;
import net.minecraft.entity.passive.FishEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.InstancedAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;

public class NautilusEntity extends FishEntity implements GeoEntity {
    private static final RawAnimation MOVING = RawAnimation.begin().thenLoop("animation.nautilus.moving");
    private static final RawAnimation IDLE = RawAnimation.begin().thenLoop("animation.nautilus.idle");

    AnimatableInstanceCache factory = new InstancedAnimatableInstanceCache(this);

    public NautilusEntity(EntityType<? extends FishEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected SoundEvent getFlopSound() {
        return SoundEvents.ENTITY_COD_FLOP;
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return AnglingSounds.ENTITY_NAUTILUS_HURT;
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return AnglingSounds.ENTITY_NAUTILUS_DEATH;
    }

    @Override
    public ItemStack getBucketItem() {
        return new ItemStack(AnglingItems.NAUTILUS_BUCKET);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar registrar) {
        registrar.add(new AnimationController<>(this, "controller", 0, this::controller));
    }

    @Override
    public void tickMovement() {
        if(!this.isTouchingWater() && this.isOnGround() && this.verticalCollision) {
            this.verticalCollision = false;
        }else if(this.isAlive() && this.isTouchingWater() && getWorld().isClient && this.getVelocity().length() > 0.025f) {
            getWorld().addParticle(ParticleTypes.BUBBLE, this.getX(), this.getEyeY(), this.getZ(), 0, 0, 0);
        }
        super.tickMovement();
    }

    private PlayState controller(AnimationState<NautilusEntity> event) {
        if(event.isMoving() && isTouchingWater()) {
            event.getController().setAnimation(MOVING);
        }else {
            event.getController().setAnimation(IDLE);
        }
        return PlayState.CONTINUE;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return factory;
    }

    @SuppressWarnings("deprecation")
    public static boolean canSpawn(EntityType<? extends WaterCreatureEntity> type, WorldAccess world, SpawnReason reason, BlockPos pos, Random random) {
        int seaLevel = world.getSeaLevel();
        return pos.getY() >= seaLevel - 40 && pos.getY() <= seaLevel - 16 && world.getFluidState(pos.down()).isIn(FluidTags.WATER) && world.getBlockState(pos.up()).isOf(Blocks.WATER);
    }
}
