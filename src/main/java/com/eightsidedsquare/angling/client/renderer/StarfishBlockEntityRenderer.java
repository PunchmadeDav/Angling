package com.eightsidedsquare.angling.client.renderer;

import com.eightsidedsquare.angling.client.model.StarfishBlockEntityModel;
import com.eightsidedsquare.angling.common.entity.StarfishBlockEntity;
import com.eightsidedsquare.angling.core.AnglingBlocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.object.Color;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class StarfishBlockEntityRenderer extends GeoBlockRenderer<StarfishBlockEntity> {
    private final GeoModel<StarfishBlockEntity> modelProvider;

    public StarfishBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {
        super(new StarfishBlockEntityModel());
        this.modelProvider = new StarfishBlockEntityModel();
    }

    @Override
    public RenderLayer getRenderType(StarfishBlockEntity animatable, float partialTicks, MatrixStack stack, VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn, Identifier textureLocation) {
        return RenderLayer.getEntityTranslucent(getTextureLocation(animatable));
    }

    @Override
    public Color getRenderColor(StarfishBlockEntity entity, float partialTick, int packedLight) {
        return entity.getCachedState().isOf(AnglingBlocks.DEAD_STARFISH) ? Color.WHITE : Color.ofOpaque(entity.isRainbow() ? StarfishBlockEntity.getRainbowColor() : entity.getColor());
    }

    @Override
    public void render(StarfishBlockEntity entity, float tickDelta, MatrixStack stack, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        if(!entity.isRemoved()){
            BakedGeoModel model = modelProvider.getBakedModel(modelProvider.getModelResource(entity));
            AnimationState<StarfishBlockEntity> animationState = new AnimationState<>(animatable, 0, 0, tickDelta, false);

            animationState.setData(DataTickets.TICK, animatable.getTick(animatable));
            animationState.setData(DataTickets.BLOCK_ENTITY, animatable);
            modelProvider.setCustomAnimations(entity, this.getInstanceId(entity), animationState);
            stack.push();
            stack.translate(0.5, 0.01, 0.5);

            MinecraftClient.getInstance().getTextureManager().bindTexture(getTextureLocation(entity));
            Color renderColor = getRenderColor(entity, tickDelta, light);
            RenderLayer renderType = getRenderType(entity, tickDelta, stack, vertexConsumers, null, light,
                    getTextureLocation(entity));
            actuallyRender(stack, entity, model, renderType,  vertexConsumers, null, false, tickDelta, light, OverlayTexture.DEFAULT_UV,
                    (float) renderColor.getRed() / 255f, (float) renderColor.getGreen() / 255f,
                    (float) renderColor.getBlue() / 255f, (float) renderColor.getAlpha() / 255);
            stack.pop();
        }
    }
}
