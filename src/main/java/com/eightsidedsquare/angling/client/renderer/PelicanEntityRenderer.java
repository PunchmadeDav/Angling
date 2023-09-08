package com.eightsidedsquare.angling.client.renderer;

import com.eightsidedsquare.angling.client.model.BasicEntityModel;
import com.eightsidedsquare.angling.common.entity.PelicanEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.RotationAxis;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3d;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.util.RenderUtils;

public class PelicanEntityRenderer extends BasicEntityRenderer<PelicanEntity> {

    private final EntityRenderDispatcher entityRenderDispatcher;
    @Nullable
    private PelicanEntity pelicanEntity;
    private VertexConsumerProvider vertexConsumerProvider;

    public PelicanEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new BasicEntityModel<>("pelican", false, "head_joint"));
        entityRenderDispatcher = ctx.getRenderDispatcher();
        this.shadowRadius = 0.35f;
    }

    @Override
    public void preRender(MatrixStack poseStack, PelicanEntity animatable, BakedGeoModel model, VertexConsumerProvider bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        this.vertexConsumerProvider = bufferSource;
        this.pelicanEntity = animatable;
        super.preRender(poseStack, animatable, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
    }

    @Override
    public void renderRecursively(MatrixStack stack, PelicanEntity animatable, GeoBone bone, RenderLayer renderType, VertexConsumerProvider bufferSource, VertexConsumer bufferIn, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        if(bone.getName().equals("beak_bottom") && pelicanEntity != null && pelicanEntity.isBeakOpen() && pelicanEntity.getEntityInBeak().isPresent()) {
            Entity entityInBeak = pelicanEntity.getEntityInBeak().get();
            Vector3d pos = bone.getPositionVector();
            stack.push();
            GeoBone parent = bone;
            while(parent != null) {
                RenderUtils.translateMatrixToBone(stack, bone);
                if(parent.getName().equals("root")) {
                    if(pelicanEntity.isTouchingWater()) {
                        stack.translate(0, 0.5d, 0.25d);
                    }else if(pelicanEntity.isFlying()) {
                        stack.translate(0, 0.6d, 0.5d);
                    }
                }
                RenderUtils.translateToPivotPoint(stack, parent);
                RenderUtils.rotateMatrixAroundBone(stack, parent);
                if(parent.getName().equals("head_joint")) {
                    stack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(pelicanEntity.getPitch()));
                    stack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(pelicanEntity.getHeadYaw() - pelicanEntity.getBodyYaw()));
                }
                RenderUtils.scaleMatrixForBone(stack, parent);
                RenderUtils.translateAwayFromPivotPoint(stack, parent);
                parent = parent.getParent();
            }
            stack.translate(0, 0.75f, -1.35f);
            stack.scale(0.5f, 0.5f, 0.5f);
            stack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180));
            entityRenderDispatcher.render(entityInBeak, pos.x, pos.y, pos.z, 0, 0, stack, vertexConsumerProvider, packedLight);
            stack.pop();
            bufferIn = vertexConsumerProvider.getBuffer(RenderLayer.getEntityTranslucent(getTexture(pelicanEntity)));
        }
        super.renderRecursively(stack, animatable, bone, renderType, bufferSource, bufferIn, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
    }


}
